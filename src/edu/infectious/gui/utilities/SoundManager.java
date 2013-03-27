package edu.infectious.gui.utilities;

public class SoundManager {
	
	private static final String BACKGROUND_MUSIC_FILENAME = "sounds/background.mp3";
	private static final String BUTTON_HOVER_EFFECT = "sounds/button_hover.mp3";
	private static final String DIALOG_OPEN_EFFECT = "sounds/dialog_open.mp3";

	public static void startBackgroundMusic() {
		new MP3Player(BACKGROUND_MUSIC_FILENAME, true).start();
	}
	
	public static void playSoundEffect(SoundEffect effect) {
		switch(effect) {
		case BUTTON_HOVER:
			new MP3Player(BUTTON_HOVER_EFFECT, false).start();
			break;
		case DIALOG_OPEN:
			new MP3Player(DIALOG_OPEN_EFFECT, false).start();
			break;
		default:
			break;
		}
	}

}
