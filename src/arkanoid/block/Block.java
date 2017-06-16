package arkanoid.block;

import arkanoid.GameObject;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Block implements GameObject {

	private Rectangle block;

	public synchronized final Rectangle getBlock() {
		return block;
	}
		
	public synchronized final double getHeight() {
		return block.getHeight();
	}

	public synchronized final double getWidth() {
		return block.getWidth();
	}

	protected Block(double x, double y, double width, double height) {
		this(x,y,width,height,Color.WHITE);
	}
	
	protected Block(double x, double y, double width, double height,Color color) {
		block = new Rectangle(x, y, width, height);
		setColor(color);
	}

	/**
	 * Assign the color to this block
	 * @param color
	 */
	public void setColor(Color color) {
		if (block != null) {
			block.setFill(color);
		}
	}

	/**
	 * Get the center point for this block
	 * BlockX = the upper left point of this block for x-axis,BlockY = the upper left point of this block for y-axis
	 * Center Point(BlockX + WIDTH / 2,BLOCKY + HEIGHT / 2)
	 * @return
	 */
	@Override
	public Point2D getCenterPoint() {
		return new Point2D(block.getX() + block.getWidth() / 2, block.getY() + block.getHeight() / 2);
	}

	@Override
	public double getPositionX() {
		return block.getX();
	}

	@Override
	public double getPositionY() {
		return block.getY();
	}
	
	

}
