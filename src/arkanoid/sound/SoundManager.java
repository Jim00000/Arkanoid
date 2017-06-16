package arkanoid.sound;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager implements Closeable,Vocal {

	private static Logger logger = LogManager.getLogger(SoundManager.class.getName());
	private Map<String, MediaPlayer> soundDictionary;

	public synchronized final Map<String, MediaPlayer> getSoundDictionary() {
		return soundDictionary;
	}

	public SoundManager() {
		soundDictionary = new HashMap<>();
		// Put the SFX into the map
		putMediaToDict(soundDictionary, "ControlledBlock", "resources/Arkanoid SFX/Arkanoid SFX (6).wav");
		putMediaToDict(soundDictionary, "ColoredBlock", "resources/Arkanoid SFX/Arkanoid SFX (7).wav");
		putMediaToDict(soundDictionary, "BallBreak", "resources/Arkanoid SFX/Arkanoid SFX (10).wav");
		putMediaToDict(soundDictionary, "EffectBlock", "resources/Arkanoid SFX/Arkanoid SFX (3).wav");
	}

	/**
	 * Play the sound effect by names
	 * 
	 * @param name
	 */
	public void playSound(String name) {
		MediaPlayer player = soundDictionary.get(name);
		// If the instance is null
		if (player != null) {
			// Stop the player
			player.stop();
			// Play the player
			player.play();
		} else {
			// Show error message
			logger.error("Sound " + name + " has not been found !");
		}
	}

	/**
	 * Initialize the SFX and media player and then put inside the map
	 * 
	 * @param dict
	 * @param soundName
	 * @param filepath
	 */
	private void putMediaToDict(Map<String, MediaPlayer> dict, String soundName, String filepath) {
		Media media = new Media(new File(filepath).toURI().toString());
		dict.put(soundName, new MediaPlayer(media));
	}

	@Override
	public void close() throws IOException {
		// Release the data for all media player
		for (String key : soundDictionary.keySet()) {
			soundDictionary.get(key).dispose();
		}
		// Remove the item inside the sound dictionary
		soundDictionary.clear();
	}

}
