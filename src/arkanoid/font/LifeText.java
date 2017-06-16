package arkanoid.font;

import arkanoid.GameScene;
import javafx.scene.paint.Color;

public class LifeText extends GameText {

	private int lifeCount;

	public synchronized final int getLifeCount() {
		return lifeCount;
	}

	public synchronized final void setLifeCount(int lifeCount) {
		this.lifeCount = lifeCount;
		super.setText("x " + lifeCount);
	}

	public synchronized final void incrementLifeCount() {
		setLifeCount(lifeCount + 1);
	}

	public synchronized final void decrementLifeCount() {
		if (lifeCount >= 1)
			setLifeCount(lifeCount - 1);
	}

	public LifeText(double x, double y, int lifeCount) {
		super(x, y, "", GameScene.FONTSIZE, Color.YELLOW);
		setLifeCount(lifeCount);
	}

}
