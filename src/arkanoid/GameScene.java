package arkanoid;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import arkanoid.ball.Ball;
import arkanoid.block.Block;
import arkanoid.block.ColoredBlock;
import arkanoid.block.ControlledBlock;
import arkanoid.block.EffectBlock;
import arkanoid.block.effect.DoubleScore;
import arkanoid.font.GameText;
import arkanoid.font.LifeText;
import arkanoid.font.ScoreBoard;
import arkanoid.font.SpeedText;
import arkanoid.sound.SoundManager;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class GameScene implements Closeable {

	private static Logger logger = LogManager.getLogger(GameScene.class.getName());

	private Scene scene;
	private Pane root;
	private ControlledBlock controller;
	private AnimationTimer animationTimer;
	protected SoundManager soundManager;
	private ScoreBoard scoreBoard;
	private SpeedText speedStatus;
	private ImageView heartImage;
	private LifeText lifeStatus;
	private GameText statusBar;
	private boolean isMoveLeft = false;
	private boolean isMoveRight = false;
	private boolean isBallStick = true;
	// When the game is start
	private boolean isInitial = true;
	// Whether the game is over
	private boolean isGameOver = false;
	// For Debugging only, make program play the game automatically
	private boolean isAutomaticPlayed = false;

	// Balls sets
	Set<Ball> ballSets = new HashSet<>();
	// Blocks that can get scores
	Set<Block> blockSets = new HashSet<>();
	// Blocks that can make good effects
	Set<EffectBlock> effectBlockSets = new HashSet<>();

	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	public static final int INITLIFECOUNT = 3;
	public static final int CONTROLLEDBLOCKMOVINGUNIT = 5;
	public static final int SPEEDUPMAXIMUM = 25;
	public static final int BALLCOUNTMAXIMUM = 5;
	public static final double PADDING = 30.0;
	public static final double STATUSHEIGHT = 60.0;
	public static final double BLOCKWIDTH = 80.0;
	public static final double BLOCKHEIGHT = 15.0;
	public static final double BALLRADIUS = 5.0;
	public static final double BALLVELOCITYUNIT = 3.0;
	public static final String TITLE = "Arkanoid";
	public static final double FONTSIZE = 16.0;
	public static final double EFFECT_BLOCK_PROBABILITY = 0.05;

	public synchronized final Scene getScene() {
		return scene;
	}

	public synchronized final AnimationTimer getAnimationTimer() {
		return animationTimer;
	}

	public GameScene() {

		// Initialize the pane
		root = new AnchorPane();

		// Initialize the sound manager
		soundManager = new SoundManager();

		// Initialize controlledBlock for the player to control
		controller = new ControlledBlock(WIDTH / 2 - BLOCKWIDTH / 2, HEIGHT * 0.9, BLOCKWIDTH, BLOCKHEIGHT,
				soundManager, ballSets, root);
		logger.debug("Initialize controlledBlock for player");

		// Initialize the ball
		Ball defaultBall = new Ball(controller.getCenterPoint().getX(),
				controller.getCenterPoint().getY() - BLOCKHEIGHT / 2 - BALLRADIUS, BALLRADIUS, soundManager);
		// Put the ball into ballSets which is sticked to the controller
		ballSets.add(defaultBall);
		logger.debug("Initialize the ball");

		// Initialize all scored blocks
		setUpBlock(blockSets);
		logger.debug("Initialize all scored blocks");

		// Initialize the score board
		scoreBoard = new ScoreBoard(PADDING, STATUSHEIGHT / 2, "0", FONTSIZE, Color.YELLOW);
		logger.debug("Initialize all scored blocks");

		// Initialize the speed status text
		speedStatus = new SpeedText(150, STATUSHEIGHT / 2, 1);

		logger.debug("Initialize the speed status text");

		// Initialize the heart image
		Image image = null;
		try {
			// Read the image file
			image = new Image(new FileInputStream("resources/heart.png"));
		} catch (FileNotFoundException e) {
			logger.fatal(e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(1);
		}

		logger.debug("Load the data of the heart image successfully");

		// Set properties of the 'heart' image
		heartImage = new ImageView(image);
		heartImage.setX(350);
		heartImage.setY(STATUSHEIGHT / 2 - 18);
		heartImage.setFitHeight(25);
		heartImage.setFitWidth(25);

		// Initialize the life count status
		lifeStatus = new LifeText(380, STATUSHEIGHT / 2, INITLIFECOUNT);

		logger.debug("Initialize life count status");

		// Initialize the statusBar
		statusBar = new GameText(WIDTH / 15.0, STATUSHEIGHT / 2 + 25,
				"[LEFT][RIGHT][A][D] : Move      [SPACE][UP][Z][L] : Launch", 20, Color.YELLOW);

		logger.debug("Initialize status bar");

		// Append the child elements
		root.getChildren().add(controller.getBlock());
		root.getChildren().add(defaultBall.getBall());
		root.getChildren().add(scoreBoard.getTextObject());
		root.getChildren().add(speedStatus.getTextObject());
		root.getChildren().add(heartImage);
		root.getChildren().add(lifeStatus.getTextObject());
		root.getChildren().add(statusBar.getTextObject());

		// Append all blocks inside the sets 'blockSets'
		for (Block block : blockSets) {
			root.getChildren().add(block.getBlock());
		}

		logger.debug("Append all children elements");

		scene = new Scene(root, WIDTH, HEIGHT, Color.BLACK);
		// Initialize the keyboard input
		setKeyBoardInput(scene);
		logger.debug("Initialize the keyboard input");

		// Initialize the animation timer
		animationTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {

				// Check moving left
				if (isMoveLeft) {
					if (controller.getBlock().getX() > 0) {
						controller.getBlock().setX(controller.getBlock().getX() - CONTROLLEDBLOCKMOVINGUNIT);
						// If the ball is sticked to the controlled block
						if (isBallStick) {
							// For all balls
							for (Ball ball : ballSets)
								ball.getBall().setCenterX(ball.getBall().getCenterX() - CONTROLLEDBLOCKMOVINGUNIT);
						}
					}
				}

				// Check moving right
				if (isMoveRight) {
					if (controller.getBlock().getX() + controller.getWidth() < GameScene.WIDTH) {
						controller.getBlock().setX(controller.getBlock().getX() + CONTROLLEDBLOCKMOVINGUNIT);
						// If the ball is sticked to the controlled block
						if (isBallStick) {
							// For all balls
							for (Ball ball : ballSets)
								ball.getBall().setCenterX(ball.getBall().getCenterX() + CONTROLLEDBLOCKMOVINGUNIT);
						}
					}
				}

				// If the ball does not stick to the controller(controlled
				// block)
				if (!isBallStick) {
					// For all balls
					for (Ball ball : ballSets) {
						// Update all balls
						ball.update();

						// Collision between balls and the controlled block
						if (controller.isCollided(ball)) {
							controller.onCollision(ball);
						}

					} // !for (Ball ball : ballSets)

					// Prepare deletion buffer
					List<Block> deletionBuffer = new LinkedList<>();
					// Prepare effect buffer
					List<EffectBlock> effectBuffer = new LinkedList<>();

					// Collision between balls and the colored block
					Iterator<Block> iters = blockSets.iterator();
					// For each block inside blockSets
					while (iters.hasNext()) {
						Block block = iters.next();
						// If this block is a ColoredBlock
						if (block instanceof ColoredBlock) {
							ColoredBlock coloredBlock = (ColoredBlock) block;
							// Check each ball
							for (Ball ball : ballSets) {
								if (coloredBlock.isCollided(ball)) {
									// Call collision method
									coloredBlock.onCollision(ball);
									// Remove this block
									deletionBuffer.add(coloredBlock);
									// Check whether player has the double score
									// effect
									if (controller.getEffect() != null
											&& controller.getEffect() instanceof DoubleScore) {
										DoubleScore ds = (DoubleScore) controller.getEffect();
										// Get double score
										ds.getDoubleScore(scoreBoard);
									} else {
										// Update the score board
										coloredBlock.getScore(scoreBoard);
									}
									// Check whether the speed reach the maximum
									if (speedStatus.getSpeed() <= SPEEDUPMAXIMUM) {
										// Speed up the ball and update the
										// speed status
										speedStatus.incrementSpeed();
										ball.speedUp();
									}
									// Take a dice
									if (giveBoolRandomly(EFFECT_BLOCK_PROBABILITY)) {
										// Create an effectBlock
										logger.debug("create an effectBlock");
										// Add the effectBlock into buffer
										effectBuffer.add(new EffectBlock(coloredBlock, controller));
									}
								} // !if (coloredBlock.isCollided(ball))
							} // !for (Ball ball : ballSets)
						} // !if (block instanceof ColoredBlock)
					} // !while (iters.hasNext())

					// Get the iterator from effectBlockSets
					Iterator<EffectBlock> effectBlockIters = effectBlockSets.iterator();
					// For each effectBlock inside effectBlockIters
					while (effectBlockIters.hasNext()) {
						EffectBlock eb = effectBlockIters.next();
						// Check whether the controlledBlock collides with the
						// effect block
						if (controller.isCollided(eb)) {
							controller.onCollision(eb);
							// Add this effect block into deletion buffer
							deletionBuffer.add(eb);
						} else {
							// If the effect block is outside the window
							if (eb.getBlock().getY() >= HEIGHT) {
								// Add this effect block into deletion buffer
								deletionBuffer.add(eb);
							} else {
								// Update the location of the effectBlock
								eb.update();
							}

						}
					}

					// For each block from deletion buffer
					for (Block block : deletionBuffer) {
						// If the type is ColoredBlock
						if (block instanceof ColoredBlock) {
							if (!blockSets.remove(block)) {
								logger.error("Remove blocks from block sets error !");
							} else {
								logger.debug("Remove blocks from block sets successfully !");
								if (!root.getChildren().remove(block.getBlock())) {
									logger.error("Remove blocks from root children error !");
								} else {
									logger.debug("Remove blocks from root children successfully !");
								}
							}
						} // !if (block instanceof ColoredBlock)

						// If the type is effectBlock
						if (block instanceof EffectBlock) {
							if (!effectBlockSets.remove(block)) {
								logger.error("Remove blocks from block sets error !");
							} else {
								logger.debug("Remove blocks from block sets successfully !");
								if (!root.getChildren().remove(block.getBlock())) {
									logger.error("Remove blocks from root children error !");
								} else {
									logger.debug("Remove blocks from root children successfully !");
								}
							}
						}
					}
					// Remove all items inside deletion buffer
					deletionBuffer.clear();

					// For each block from effect buffer
					for (EffectBlock effectedBlock : effectBuffer) {
						// Add to the root (or pane)
						root.getChildren().add(effectedBlock.getBlock());
						// Add to the blockSets
						effectBlockSets.add(effectedBlock);
					}
					// Remove all items inside effect buffer
					effectBuffer.clear();

				} // !if(!isBallStick)

				// Verify Whether the player has broken down all blocks
				if (blockSets.size() == 0) {
					logger.info("Player wins. All blocks are broken down.");
					// Add new block
					insertBlock();
				}

				// Initialize a deletionBallBuffer
				Set<Ball> deletionBallBuffer = new HashSet<>();

				// Check whether the ball is out of the field, that is, below
				// the bottom edge of the game window
				for (Ball ball : ballSets) {
					if (ball.getCenterPoint().getY() > HEIGHT
							&& ball.getCenterPoint().getY() > controller.getCenterPoint().getY()) {
						// Stop the ball
						ball.stop();
						// Play the sound
						soundManager.playSound("BallBreak");
						// Add this ball into deletionBallBuffer
						deletionBallBuffer.add(ball);
					}
				} // !for (Ball ball : ballSets)

				// Delete the ball from deletionBallBuffer
				for (Ball ball : deletionBallBuffer) {
					root.getChildren().remove(ball.getBall());
					ballSets.remove(ball);
				}
				// Clear deletionBallBuffer
				deletionBallBuffer.clear();

				// Check whether the player loses
				if (ballSets.isEmpty()) {
					// The player loses one heart (life)
					if (lifeStatus.getLifeCount() > 0) {
						lifeStatus.decrementLifeCount();
						// Remove all items inside the effectBlockSets
						Iterator<EffectBlock> iters = effectBlockSets.iterator();
						while (iters.hasNext()) {
							EffectBlock eb = iters.next();
							root.getChildren().remove(eb.getBlock());
						}
						effectBlockSets.clear();
						// Reset the game scene
						reset();
					} else {
						if (isGameOver == false) {
							// The player loses
							statusBar = new GameText(WIDTH / 2.5, STATUSHEIGHT / 2 + 25, "Game Over", 20, Color.YELLOW);
							root.getChildren().add(statusBar.getTextObject());
							isGameOver = true;
							// Remove all items inside the effectBlockSets
							Iterator<EffectBlock> iters = effectBlockSets.iterator();
							while (iters.hasNext()) {
								EffectBlock eb = iters.next();
								root.getChildren().remove(eb.getBlock());
							}
							effectBlockSets.clear();

							logger.debug("Remove all blocks in current effectblockSet");
						}
					}
				} // !if (ballSets.isEmpty())

				// Make controlled block targets to the ball if automatic
				// function is turned on
				if (isAutomaticPlayed == true && isBallStick == false) {
					// Reset moving direction flag
					controller.setLeftMoving(false);
					controller.setRightMoving(false);
					// Build a priority queue (maxHeap)
					Queue<GameObject> queue = new PriorityQueue<>(
							(b1, b2) -> (b1.getCenterPoint().getY() >= b2.getCenterPoint().getY()) ? -1 : 1);
					// Put all balls into the priority queue
					queue.addAll(ballSets);
					queue.addAll(effectBlockSets);
					// Get the ball which is closest to the controller
					GameObject obj = queue.remove();
					// Clear the priority queue
					queue.clear();

					// Set moving direction flag
					if (obj.getCenterPoint().getX() - controller.getCenterPoint().getX() > 0) {
						controller.setLeftMoving(true);
					} else if (obj.getCenterPoint().getX() - controller.getCenterPoint().getX() < 0) {
						controller.setRightMoving(true);
					}

					// Control and adjust the position of the controller
					if (obj.getCenterPoint().getX() - controller.getBlock().getWidth() / 2 < 0) {
						controller.getBlock().setX(0);
					} else if (obj.getCenterPoint().getX() + controller.getBlock().getWidth() > WIDTH) {
						controller.getBlock().setX(WIDTH - controller.getBlock().getWidth());
					} else {
						controller.getBlock().setX(obj.getCenterPoint().getX() - controller.getBlock().getWidth() / 2);
					}
				}

			} // !public void handle(long now)
		};

		logger.debug("Initialize the timer for this game");

	}

	/**
	 * Set Up the blocks prepared for this game scene
	 */
	protected abstract void setUpBlock(Set<Block> blocks);

	/**
	 * Set up the input of keyBoard
	 * 
	 * @param scene
	 */
	private void setKeyBoardInput(Scene scene) {
		// When the player pushes down a key
		scene.setOnKeyPressed((event) -> {

			if (isGameOver == true) {
				return;
			}

			switch (event.getCode()) {
			case LEFT: // Move left
			case A:
				isMoveLeft = true;
				controller.setLeftMoving(true);
				break;
			case RIGHT: // Move right
			case D:
				isMoveRight = true;
				controller.setRightMoving(true);
				break;
			case SPACE: // Shoot the ball
			case UP:
			case L:
			case Z:
				if (isInitial == true) {
					isInitial = false;
					root.getChildren().remove(statusBar.getTextObject());
					logger.debug("Remove statusBar text");
				}

				if (isBallStick == true) {
					isBallStick = false;
					// If the left arrow key is pressed or it is not pressed
					for (Ball ball : ballSets)
						ball.setVelocity(-BALLVELOCITYUNIT, -BALLVELOCITYUNIT);
					// If the right arrow key is pressed
					if (isMoveRight == true) {
						for (Ball ball : ballSets)
							ball.setVelocity(BALLVELOCITYUNIT, -BALLVELOCITYUNIT);
					}
				}
				break;
			default: // Default : do nothing
				break;
			}
		});

		// When the player pushes up a key
		scene.setOnKeyReleased((event) -> {

			if (isGameOver == true) {
				isGameOver = false;
				root.getChildren().remove(statusBar.getTextObject());
				logger.debug("Remove statusBar text");

				reset();
				replay();
				return;
			}

			switch (event.getCode()) {
			case LEFT:
			case A:
				isMoveLeft = false;
				controller.setLeftMoving(false);
				break;
			case RIGHT:
			case D:
				isMoveRight = false;
				controller.setRightMoving(false);
				break;
			case T: // Master key
				isAutomaticPlayed = ((isAutomaticPlayed == true) ? false : true);
				break;
			default: // Default : do nothing
				break;
			}
		});
	}

	/**
	 * Reset the scene of this game
	 */
	private void reset() {
		isBallStick = true;
		isMoveRight = false;
		isMoveLeft = false;
		controller.reset();
		controller.getBlock().setX(WIDTH / 2 - BLOCKWIDTH / 2);
		// Remove all balls inside ballSets
		for (Ball ball : ballSets) {
			root.getChildren().remove(ball.getBall());
		}
		// Clear ballSets
		ballSets.clear();
		// Generate a new ball
		Ball newBall = new Ball(controller.getCenterPoint().getX(),
				controller.getCenterPoint().getY() - BLOCKHEIGHT / 2 - BALLRADIUS, BALLRADIUS, soundManager);
		// Add the new ball into ballSets
		ballSets.add(newBall);
		// Add the new ball into AnchorPane (root)
		root.getChildren().add(newBall.getBall());
		speedStatus.setSpeed(1);
	}

	/**
	 * Get the true or false by probability
	 * 
	 * @param probability
	 * @return
	 */
	private final boolean giveBoolRandomly(double probability) {
		return Math.random() >= 1.0 - probability;
	}

	@Override
	public void close() throws IOException {
		soundManager.close();
	}

	/**
	 * Reset the scene to initial status
	 */
	private void replay() {
		// Remove all blocks inside the blockSets
		for (Block block : blockSets) {
			root.getChildren().remove(block.getBlock());
		}
		// Clear blockSets
		blockSets.clear();

		logger.debug("Remove all blocks in current blockSet");

		// Reset the numerical data
		lifeStatus.setLifeCount(INITLIFECOUNT);
		scoreBoard.setScore(0.0);

		logger.debug("Reset numerical data");

		// Reinsert the ColoredBlock
		insertBlock();
	}

	/**
	 * Insert new ColoredBlock into the level
	 */
	private void insertBlock() {
		// Initialize all scored blocks
		blockSets.clear();
		setUpBlock(blockSets);
		logger.debug("Initialize all scored blocks");

		// Append all blocks inside the sets 'blockSets'
		for (Block block : blockSets) {
			root.getChildren().add(block.getBlock());
		}

		logger.debug("Append all children blocks");
	}
}
