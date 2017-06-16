package arkanoid;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import arkanoid.scene.LeveLJava;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GameApp extends Application {
	
	private static Logger logger = LogManager.getLogger(GameApp.class.getName());
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		GameScene gs = new LeveLJava();
		logger.info("Start the game for level 1");
		// Start the timer for this game
		gs.getAnimationTimer().start();
		logger.debug("Start the timer");
		primaryStage.setTitle(GameScene.TITLE);
		primaryStage.setScene(gs.getScene());
		// Show the window
		primaryStage.show();
		logger.debug("Start game window");
		
		// Set up window close event handler
		primaryStage.setOnCloseRequest((WindowEvent we)->{
			try {
				logger.debug("Release the data of game scene");
				gs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
	}

	/**
	 * The game starts here
	 * @param args
	 */
	public static void main(String[] args) {
		logger.debug("launch program");
		Application.launch(args);
	}

}
