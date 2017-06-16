package arkanoid.font;

import javafx.scene.paint.Color;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class ScoreBoard extends GameText {

	private double score = 0.0;
	
	public synchronized final double getScore() {
		return score;
	}

	public synchronized final void setScore(double score) {
		this.score = score;
		super.setText("Score : " + score);
	}
	
	public synchronized final void addToScore(double value) {
		this.score += value;
		super.setText("Score : " + score);
	}

	public ScoreBoard(double x, double y, String msg) {
		super(x, y, "Score : " + msg);
	}

	public ScoreBoard(double x, double y, String msg, Color color) {
		super(x, y, "Score : " + msg, color);
	}

	public ScoreBoard(double x, double y, String msg, double size, Color color) {
		super(x, y, "Score : " + msg, size, color);
	}

	public ScoreBoard(double x, double y, String msg, double size) {
		super(x, y, "Score : " + msg, size);
	}

	public ScoreBoard(double x, double y, String msg, FontWeight weight, FontPosture posture, double size,
			Color color) {
		super(x, y, "Score : " + msg, weight, posture, size, color);
	}
			
}
