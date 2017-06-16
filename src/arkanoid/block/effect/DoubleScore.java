package arkanoid.block.effect;

import arkanoid.font.ScoreBoard;

public class DoubleScore implements DoubleScoreEffect {

	@Override
	public void getDoubleScore(ScoreBoard scoreBoard) {
		scoreBoard.addToScore(2.0);
	}

	@Override
	public double getEffectPeriod() {
		return 5.0;
	}

}
