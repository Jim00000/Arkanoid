package arkanoid;

import arkanoid.font.ScoreBoard;

public interface Scoreable {

	/**
	 * All objects that implement this interface means that the object in
	 * games will make the effect of getting scores
	 * 
	 * @param scoreBoard
	 */
	public void getScore(ScoreBoard scoreBoard);

}
