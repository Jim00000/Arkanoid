package arkanoid.block;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import arkanoid.Collidable;
import arkanoid.GameObject;
import arkanoid.GameScene;
import arkanoid.ball.Ball;
import arkanoid.block.effect.Effective;
import arkanoid.block.effect.MoreBall;
import arkanoid.block.effect.NarrowerBlock;
import arkanoid.block.effect.WiderBlock;
import arkanoid.sound.SoundManager;
import arkanoid.sound.Vocal;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ControlledBlock extends Block implements Collidable<GameObject>, Vocal {

	private static Logger logger = LogManager.getLogger(ControlledBlock.class.getName());
	private SoundManager soundManager;
	private Effective effect;
	private Timeline effectTimer;
	Set<Ball> ballSets = new HashSet<>();
	private Pane root;
	private double elapsedtime;
	private boolean isLeftMoving, isRightMoving;

	public synchronized final boolean isLeftMoving() {
		return isLeftMoving;
	}

	public synchronized final void setLeftMoving(boolean value) {
		this.isLeftMoving = value;
	}

	public synchronized final boolean isRightMoving() {
		return isRightMoving;
	}

	public synchronized final void setRightMoving(boolean value) {
		this.isRightMoving = value;
	}

	public synchronized final Effective getEffect() {
		return effect;
	}

	public synchronized final void clearEffect() {
		effect = null;
	}

	public ControlledBlock(double x, double y, double width, double height, SoundManager soundManager,
			Set<Ball> ballSets, Pane root) {
		super(x, y, width, height);
		setColor(Color.GRAY);
		this.soundManager = soundManager;
		this.ballSets = ballSets;
		this.root = root;
		isLeftMoving = false;
		isRightMoving = false;
		effect = null;
		elapsedtime = 0.0;
		effectTimer = new Timeline(new KeyFrame(Duration.seconds(1.0), (e) -> {
			if (elapsedtime >= effect.getEffectPeriod()) {
				// Clear effect
				this.clearEffect();
				// Stop this timer
				stopTimer();
				// Reset the properties
				reset();
			} else {
				elapsedtime += 1.0;
			}
		}));

		effectTimer.setCycleCount(Timeline.INDEFINITE);
	}

	private void startTimer() {
		elapsedtime = 0.0;
		effectTimer.play();
	}

	private void stopTimer() {
		elapsedtime = 0.0;
		effectTimer.stop();
	}

	/**
	 * Reset this controller
	 */
	public void reset() {
		this.stopTimer();
		this.clearEffect();
		super.getBlock().setWidth(GameScene.BLOCKWIDTH);
		super.getBlock().setHeight(GameScene.BLOCKHEIGHT);
	}

	@Override
	public boolean isCollided(GameObject collider) {
		// If the object is the type of Ball
		if (collider instanceof Ball) {
			Ball ball = (Ball) collider;
			if ((this.getBlock().getX() > ball.getBall().getCenterX() + ball.getBall().getRadius())
					|| (ball.getBall().getCenterX() - ball.getBall().getRadius() > this.getBlock().getX()
							+ this.getBlock().getWidth())
					|| (ball.getBall().getCenterY() + ball.getBall().getRadius() < this.getBlock().getY())
					|| (this.getBlock().getY() + this.getBlock().getHeight() < ball.getBall().getCenterY()
							- ball.getBall().getRadius())) {
				return false;
			} else {
				return true;
			}
		}

		// If the object is the type of EffectBlock
		if (collider instanceof EffectBlock) {
			EffectBlock effectBlock = (EffectBlock) collider;
			// Check intersection
			return this.getBlock().getBoundsInParent().intersects(effectBlock.getBlock().getBoundsInParent());
		}

		// Default : false
		return false;
	}

	@Override
	public void onCollision(GameObject collider) {
		logger.debug("Collision!");

		// If the object is type of Ball
		if (collider instanceof Ball) {
			Ball ball = (Ball) collider;
			// make a random noise
			double sign = new Random().nextBoolean() == true ? 1 : -1;
			double noise = new Random().nextDouble() / 10.0 * sign + 1.0;

			// Change the position and velocity of the ball
			if (this.getCenterPoint().getX() > ball.getCenterPoint().getX()) {
				ball.setVelocity((Math.abs(ball.getVelocity().getX()) * noise) * -1, ball.getVelocity().getY() * -1);
			} else {
				ball.setVelocity(Math.abs(ball.getVelocity().getX() * noise), ball.getVelocity().getY() * -1);
			}

			ball.getBall().setCenterY(this.getCenterPoint().getY() - GameScene.BLOCKHEIGHT / 2 - GameScene.BALLRADIUS);
			// Play the SFX
			playSound("ControlledBlock");
		}

		// If the object is type of EffectBlock
		if (collider instanceof EffectBlock) {
			EffectBlock eb = (EffectBlock) collider;
			// If there exists an effect which is still running
			if (this.effect != null) {
				// Stop the timer for effect
				this.stopTimer();
				// Clear the effect
				this.clearEffect();
				// Reset the properties
				reset();
			}

			// Retrieve the effect
			this.effect = eb.getEffect();
			// If the type is WiderBlock
			if (effect instanceof WiderBlock) {
				logger.debug("Controller get wider block effect");
				WiderBlock wb = (WiderBlock) effect;
				// Make width wider
				super.getBlock().setWidth(wb.getWiderWidth(super.getBlock().getWidth()));
				// Start the timer for effect
				this.startTimer();
			}
			// If the type is NarrowerBlock
			if (effect instanceof NarrowerBlock) {
				logger.debug("Controller get narrower block effect");
				NarrowerBlock nb = (NarrowerBlock) effect;
				// Make width narrower
				super.getBlock().setWidth(nb.getNarrowerWidth(super.getBlock().getWidth()));
				// Start the timer for effect
				this.startTimer();
			}
			// If the type is MoreBall
			if (effect instanceof MoreBall) {
				logger.debug("Controller get more balls effect");
				// Skip If the number of balls reaches its maximum
				if (ballSets.size() < GameScene.BALLCOUNTMAXIMUM) {
					// Pick up a ball arbitrarily
					Ball ball = ballSets.iterator().next();
					// Build a ball whose direction with additional 20 angle
					// counterclockwise
					Ball newBallOne = new Ball(ball);
					// Make a rotation matrix M and the velocity V and V' = MV
					// Thus, V'x = cos@ * Vx - sin@ * Vy and V'y = sin@ * Vx +
					// cos@ * Vy
					newBallOne.setVelocity(
							Math.cos(20.0 / 180.0) * ball.getVelocityX() - Math.sin(20.0 / 180.0) * ball.getVelocityY(),
							Math.sin(20.0 / 180.0) * ball.getVelocityX()
									+ Math.cos(20.0 / 180.0) * ball.getVelocityY());
					// Add newBallOne into ballSets
					ballSets.add(newBallOne);
					// Add newBallOne into the pane (root)
					root.getChildren().add(newBallOne.getBall());

					// Skip If the number of balls reaches its maximum
					if (ballSets.size() < GameScene.BALLCOUNTMAXIMUM) {
						// Build a ball whose direction with additional 20 angle
						// clockwise
						Ball newBallTwo = new Ball(ball);
						// Make a rotation matrix M and the velocity V and V' =
						// MV. Thus, V'x = cos@ * Vx - sin@ * Vy and V'y = sin@
						// * Vx + cos@ * Vy
						newBallTwo.setVelocity(
								Math.cos(-20.0 / 180.0) * ball.getVelocityX()
										- Math.sin(-20.0 / 180.0) * ball.getVelocityY(),
								Math.sin(-20.0 / 180.0) * ball.getVelocityX()
										+ Math.cos(-20.0 / 180.0) * ball.getVelocityY());
						// Add newBallTwo into ballSets
						ballSets.add(newBallTwo);
						// Add newBallTwo into the pane (root)
						root.getChildren().add(newBallTwo.getBall());
					}
				}
				// Clear the effect
				this.clearEffect();
			}
			// Play the SFX
			playSound("EffectBlock");
		}
	}

	@Override
	public void playSound(String name) {
		soundManager.playSound(name);
	}

}
