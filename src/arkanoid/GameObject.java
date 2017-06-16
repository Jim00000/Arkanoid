package arkanoid;

import javafx.geometry.Point2D;

/**
 * A GameObject must contain the information about its position
 *
 */
public interface GameObject {
		
	public double getPositionX();
	
	public double getPositionY();
	
	public Point2D getCenterPoint();
	
}
