package arkanoid.block.effect;

/**
 * When a class implements this interface means that it has a effect on
 * itself,such as get double score, slower ball , has more balls or wider block
 */
public interface Effective {

	public double getEffectPeriod();

}
