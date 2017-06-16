package arkanoid.block.effect;



public class WiderBlock implements Effective,WiderEffect {

	@Override
	public double getEffectPeriod() {
		return 3.0;
	}

	@Override
	public double getWiderWidth(double width) {
		return width * 2.0;
	}



}
