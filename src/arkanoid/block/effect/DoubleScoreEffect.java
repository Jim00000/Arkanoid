package arkanoid.block.effect;

import arkanoid.font.ScoreBoard;

/**
 * Make score becomes double when Player gets
 */
public interface DoubleScoreEffect extends Effective {
	
	public void getDoubleScore(ScoreBoard scoreBoard);
}
