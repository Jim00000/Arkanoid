package arkanoid.block;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import arkanoid.Collidable;
import arkanoid.GameObject;
import arkanoid.Scoreable;
import arkanoid.ball.Ball;
import arkanoid.font.ScoreBoard;
import arkanoid.sound.SoundManager;
import arkanoid.sound.Vocal;
import javafx.scene.paint.Color;

public class ColoredBlock extends Block implements Scoreable,Collidable<GameObject>, Vocal {

	private static Logger logger = LogManager.getLogger(ColoredBlock.class.getName());
	private SoundManager soundManager;

	public ColoredBlock(double x, double y, double width, double height, Color color, SoundManager soundManager) {
		super(x, y, width, height, color);
		this.soundManager = soundManager;
	}

	@Override
	public boolean isCollided(GameObject collider) {
		// If the object is type of Ball
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

		// Default : false
		return false;
	}

	@Override
	public void onCollision(GameObject collider) {
		logger.debug("Collision! Get score");

		// Check the relative position and set the velocity of the ball
		// If the object is type of Ball
		if (collider instanceof Ball) {
			Ball ball = (Ball)collider;
			if ((this.getBlock().getX() > ball.getBall().getCenterX())
					|| (ball.getBall().getCenterX() > this.getBlock().getX() + this.getBlock().getWidth())) {
				ball.setVelocity(ball.getVelocity().getX() * -1, ball.getVelocity().getY());
			} else {
				ball.setVelocity(ball.getVelocity().getX(), ball.getVelocity().getY() * -1);
			}

			// Play the SFX
			playSound("ColoredBlock");
		}
	}

	@Override
	public void getScore(ScoreBoard scoreBoard) {
		/// TODO:implement it
		logger.info("Get scores !");

		scoreBoard.addToScore(1.0);
	}

	@Override
	public void playSound(String name) {
		soundManager.playSound(name);
	}

}
