package edu.infectious.logic;

import edu.infectious.gui.utilities.MatchRequest;
import edu.infectious.gui.utilities.WindowManager;
import edu.infectious.gui.windows.StandardDialog;
import edu.infectious.script.country.Country;
import edu.infectious.script.trait.Trait;

public class Game {
	
	private static final int CURE_N_TURNS = 50;
	private static Player player;
	private static int turnNumber;
	private static int turnsToCure;
	
	public static void initGame(MatchRequest request) {
		
		// Init script dependencies
		Trait.initTrait();
		Country.initCountry();
		
		// Init virus statistics
		VirusStatistics.initVirusStatistics();
		
		// Init window dependencies
		WindowManager.initWindows();
		StandardDialog.setupDialogs();
		
		// Create player
		if(request.isMaster())
			player = new Player(PlayerType.VIRUS);
		else
			player = new Player(PlayerType.CURE);
		
		// Reset turns to cure
		turnsToCure = CURE_N_TURNS;
		turnNumber = 0;
	}

	public static Player getPlayer() {
		return player;
	}

	public static int getTurnsToCure() {
		return turnsToCure;
	}

	public static int getTurnNumber() {
		return turnNumber;
	}

}
