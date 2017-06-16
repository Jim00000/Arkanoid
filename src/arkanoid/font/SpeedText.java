package arkanoid.font;

import arkanoid.GameScene;
import javafx.scene.paint.Color;

public class SpeedText extends GameText {

	private int speed = 0;
	private static final String SPEEDSIGN = "|";

	public synchronized final int getSpeed() {
		return speed;
	}

	public synchronized final void setSpeed(int speed) {
		this.speed = speed;
		super.setText("Speed : " + convertToSpeedSign(speed));
	}
	
	public synchronized final void incrementSpeed() {
		setSpeed(speed + 1);
	}

	public SpeedText(double x, double y, int speed) {
		super(x, y, "", GameScene.FONTSIZE, Color.YELLOW);
		setSpeed(speed);
	}

	private final String convertToSpeedSign(int speed) {
		String signs = "";
		for (int i = 0; i < speed; i++)
			signs += SPEEDSIGN;
		return signs;
	}

}
