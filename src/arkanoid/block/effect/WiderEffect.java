package arkanoid.block.effect;

public interface WiderEffect extends Effective {

	/**
	 * Calculate and get a new and wider value of the width
	 * @param width
	 * @return
	 */
	public double getWiderWidth(double width);

}
