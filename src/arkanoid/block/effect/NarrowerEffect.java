package arkanoid.block.effect;

public interface NarrowerEffect extends Effective {

	/**
	 * Calculate and get a new and narrower value of the width
	 * @param width
	 * @return
	 */
	public double getNarrowerWidth(double width);
}
