package edu.infectious.gui.utilities;

public class SoundManager {
	
	private static final String BACKGROUND_MUSIC_FILENAME = "sounds/background.mp3";

	public static void startBackgroundMusic() {
		new MP3Player(BACKGROUND_MUSIC_FILENAME, true).start();
	}

}
