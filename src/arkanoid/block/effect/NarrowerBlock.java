package arkanoid.block.effect;

public class NarrowerBlock implements Effective, NarrowerEffect {

	@Override
	public double getEffectPeriod() {
		return 3.0;
	}
	
	@Override
	public double getNarrowerWidth(double width) {
		return width * 0.5;
	}
	
}
