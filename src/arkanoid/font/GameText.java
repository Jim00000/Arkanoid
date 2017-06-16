package arkanoid.font;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GameText {

	private Text text;

	public synchronized void setText(String text) {
		this.text.setText(text);
	}

	public synchronized final String getText() {
		return text.getText();
	}
	
	public synchronized final Text getTextObject() {
		return text;
	}

	public GameText(double x, double y, String msg, FontWeight weight, FontPosture posture, double size, Color color) {
		text = new Text(x, y, msg);
		text.setCache(true);
		text.setFill(color);
		text.setFont(Font.font(null, weight, posture, size));
	}
	
	public GameText(double x, double y, String msg, double size, Color color) {
		this(x,y,msg,FontWeight.NORMAL,FontPosture.REGULAR,size,color);
	}
	
	public GameText(double x, double y, String msg, Color color) {
		this(x,y,msg,12,color);
	}
	
	public GameText(double x, double y, String msg, double size) {
		this(x,y,msg,size,Color.BLACK);
	}
	
	public GameText(double x, double y, String msg) {
		this(x,y,msg,Color.BLACK);
	}

	/**
	 * Set font color
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		if (text != null) {
			text.setFill(color);
		}
	}

	public void setPosition(double x,double y) {
		setX(x);
		setY(y);
	}
	
	public void setX(double x) {
		text.setX(x);
	}
	
	public void setY(double y) {
		text.setY(y);
	}

}
