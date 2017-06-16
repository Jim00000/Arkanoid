package arkanoid.ball;

import arkanoid.GameObject;
import arkanoid.GameScene;
import arkanoid.Movable;
import arkanoid.sound.SoundManager;
import arkanoid.sound.Vocal;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball implements Vocal, Movable, GameObject {

	private Circle ball;
	private Point2D velocity;
	private SoundManager soundManager;

	public synchronized final Point2D getVelocity() {
		return velocity;
	}

	public synchronized final double getVelocityX() {
		return velocity.getX();
	}

	public synchronized final double getVelocityY() {
		return velocity.getY();
	}

	public synchronized final void setVelocityX(double vx) {
		setVelocity(new Point2D(vx, velocity.getY()));
	}

	public synchronized final void setVelocityY(double vy) {
		setVelocity(new Point2D(velocity.getX(), vy));
	}

	public synchronized final void setVelocity(double vx, double vy) {
		setVelocity(new Point2D(vx, vy));
	}

	public synchronized final void setVelocity(Point2D velocity) {
		this.velocity = velocity;
	}

	public synchronized final Circle getBall() {
		return ball;
	}

	public Ball(double centerX, double centerY, double radius, SoundManager soundManager) {
		ball = new Circle(centerX, centerY, radius);
		ball.setFill(Color.AQUA);
		setVelocity(0, 0);
		this.soundManager = soundManager;
	}

	public Ball(Ball ball) {
		this.ball = new Circle(ball.getBall().getCenterX(), ball.getBall().getCenterY(), ball.getBall().getRadius());
		this.ball.setFill(Color.AQUA);
		this.velocity = ball.velocity;
		this.soundManager = ball.soundManager;
	}

	/**
	 * update the position of this ball
	 */
	@Override
	public void update() {

		// Update the position of this ball
		ball.setCenterX(ball.getCenterX() + velocity.getX());
		ball.setCenterY(ball.getCenterY() + velocity.getY());

		// If this ball reaches the leftmost or rightmost edge of the game
		// window
		// then changes its horizontal velocity
		if (ball.getCenterX() - ball.getRadius() < 0) { // leftmost
			setVelocity(velocity.getX() * -1, velocity.getY());
			ball.setCenterX(0 + ball.getRadius());
			// Play SFX
			this.playSound("ColoredBlock");
		} else if (ball.getCenterX() + ball.getRadius() > GameScene.WIDTH) { // rightmost
			setVelocity(velocity.getX() * -1, velocity.getY());
			ball.setCenterX(GameScene.WIDTH - ball.getRadius());
			// Play SFX
			this.playSound("ColoredBlock");
		}

		// If this ball reaches the top edge of the game window
		// then changes its vertical velocity
		if (ball.getCenterY() - ball.getRadius() < GameScene.STATUSHEIGHT) {
			setVelocity(velocity.getX(), velocity.getY() * -1);
			ball.setCenterY(GameScene.STATUSHEIGHT + ball.getRadius());
			// Play SFX
			this.playSound("ColoredBlock");
		}

	}

	/**
	 * Get the center point of this circle (ball with 2D)
	 * 
	 * @return
	 */
	@Override
	public Point2D getCenterPoint() {
		return new Point2D(ball.getCenterX(), ball.getCenterY());
	}

	/**
	 * Speed up the ball for both x direction and y direction one unit
	 */
	public void speedUp() {
		double xsign = Math.signum(velocity.getX());
		double ysign = Math.signum(velocity.getY());
		double xmagnitude = Math.abs(velocity.getX());
		double ymagnitude = Math.abs(velocity.getY());
		double newx = xsign * (xmagnitude + 0.2);
		double newy = ysign * (ymagnitude + 0.2);
		this.setVelocity(newx, newy);
	}

	/**
	 * Stop the ball,that is, make velocity = (0,0)
	 */
	public void stop() {
		setVelocity(0, 0);
	}

	@Override
	public void playSound(String name) {
		soundManager.playSound(name);
	}

	@Override
	public double getPositionX() {
		return ball.getCenterX();
	}

	@Override
	public double getPositionY() {
		return ball.getCenterY();
	}

}
