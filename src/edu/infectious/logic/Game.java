package edu.infectious.logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.SwingWorker;

import edu.infectious.gui.utilities.MatchRequest;
import edu.infectious.gui.utilities.SoundManager;
import edu.infectious.gui.utilities.WindowManager;
import edu.infectious.gui.windows.MapPanel;
import edu.infectious.gui.windows.StandardDialog;
import edu.infectious.gui.windows.StandardMessageDialog;
import edu.infectious.script.country.Country;
import edu.infectious.script.country.CountryClimateHumidity;
import edu.infectious.script.country.CountryClimateTemperature;
import edu.infectious.script.trait.Trait;
import edu.infectious.script.trait.TraitType;

public class Game {
	
	private static final int CURE_N_TURNS = 50;
	private static Player player;
	private static MatchRequest request;
	private static int turnNumber;
	private static int turnsToCure;
	
	public static void initGame() {
		
		// Init script dependencies
		Trait.initTrait();
		Country.initCountry();
		
		// Init virus statistics
		VirusStatistics.initVirusStatistics();
		
		// Init window dependencies
		WindowManager.initWindows();
		StandardDialog.setupDialogs();
	}
	
	public static void searchForOpponent() {
		// Waiting dialog
		final StandardMessageDialog message = new StandardMessageDialog("Searching for an opponent...", 30);
		new SwingWorker<Integer, String> () {
			@Override
			protected Integer doInBackground() throws Exception {
				// Request for opponent
				request = new MatchRequest();
				if(!request.makeRequest()) {
					message.setMessage("Sorry, the game server couldn't be reached");
					message.setFontSize(25);
					message.repaint();
					Thread.sleep(5000);
					message.dispose();
					return null;
				}
				
				// Connect to opponent
				if(!request.connectToOpponent()) {
					message.setMessage("Sorry, your opponent couldn't be reached");
					message.setFontSize(25);
					message.repaint();
					Thread.sleep(5000);
					message.dispose();
					return null;
				}
				message.dispose();
				return null;
			}
		}.execute();
		message.setVisible(true);
		
		// Start new game
		if(request.getSocket() != null)
			startNewGame();
	}
	
	public static void startNewGame() {
		// Create player
		if(request.isMaster())
			player = new Player(TraitType.VIRUS);
		else
			player = new Player(TraitType.CURE);
		
		// Reset turns to cure
		turnsToCure = CURE_N_TURNS;
		turnNumber = 0;
		
		// Reset country, trait and virus info
		Country.resetCountries();
		Trait.resetTraits();
		VirusStatistics.initVirusStatistics();
		
		// Create case zero
		if(request.isMaster())
			Country.randomizeCaseZero();
		
		// Calculate next turn
		endTurn();
		
		// Show game window
		WindowManager.showGame();
	}
	
	public static void endTurn() {
		if(request.isMaster())
			endTurnMaster();
		else
			endTurnSlave();
	}
	
	private static void endTurnMaster() {
		try {
			// Receive slave package
			ObjectInputStream in = new ObjectInputStream(request.getSocket().getInputStream());
			SlavePackage response = (SlavePackage) in.readObject();
			
			// TODO
			// Update info
			// updateTurn(response);
			
			// Build master package
			MasterPackage pkg = new MasterPackage();
			pkg.setCountryList(Country.getCountryList());
			pkg.setDeadliness(VirusStatistics.getDeadliness());
			pkg.setInfectiousness(VirusStatistics.getInfectiousness());
			pkg.setNotoriety(VirusStatistics.getNotoriety());
			pkg.setAir(VirusStatistics.isAirTransmission());
			pkg.setWater(VirusStatistics.isWaterTransmission());
			pkg.setPlague(VirusStatistics.isPlagueTransmission());
			pkg.setLivestock(VirusStatistics.isLivestockTransmission());
			pkg.setHotLevel(VirusStatistics.getTemperatureLevel(CountryClimateTemperature.HOT));
			pkg.setColdLevel(VirusStatistics.getTemperatureLevel(CountryClimateTemperature.COLD));
			pkg.setTemperateLevel(VirusStatistics.getTemperatureLevel(CountryClimateTemperature.TEMPERATE));
			pkg.setAridLevel(VirusStatistics.getHumidityLevel(CountryClimateHumidity.ARID));
			pkg.setTropicalLevel(VirusStatistics.getHumidityLevel(CountryClimateHumidity.TROPICAL));
			pkg.setMediterraneanLevel(VirusStatistics.getHumidityLevel(CountryClimateHumidity.MEDITERRANEAN));
			
			// End game
			// checkGameOver(pkg);
			
			// Send package to slave
			ObjectOutputStream out = new ObjectOutputStream(request.getSocket().getOutputStream());
			out.writeObject(pkg);
			
			// Update map background and player points
			player.nextTurnPoints();
			turnNumber++;
			MapPanel.getInstance().createTurnBackground();
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void endTurnSlave() {
		try {
			// Build slave package
			SlavePackage pkg = new SlavePackage();
			pkg.setDeadlinessDelta(VirusStatistics.getDeadlinessDelta());
			pkg.setNotorietyDelta(VirusStatistics.getNotorietyDelta());
			pkg.setInfectiousnessDelta(VirusStatistics.getInfectiousnessDelta());
			
			// Send package to server
			ObjectOutputStream out = new ObjectOutputStream(request.getSocket().getOutputStream());
			out.writeObject(pkg);
			
			// Receive master package
			ObjectInputStream in = new ObjectInputStream(request.getSocket().getInputStream());
			MasterPackage response = (MasterPackage) in.readObject();
			
			// Update bonus data
			VirusStatistics.setAirTransmission(response.isAir());
			VirusStatistics.setWaterTransmission(response.isWater());
			VirusStatistics.setPlagueTransmission(response.isPlague());
			VirusStatistics.setLivestockTransmission(response.isLivestock());
			
			// Update temperature data
			VirusStatistics.setTemperatureLevel(CountryClimateTemperature.HOT, response.getHotLevel());
			VirusStatistics.setTemperatureLevel(CountryClimateTemperature.COLD, response.getColdLevel());
			VirusStatistics.setTemperatureLevel(CountryClimateTemperature.TEMPERATE, response.getTemperateLevel());
			
			// Update humidity data
			VirusStatistics.setHumidityLevel(CountryClimateHumidity.ARID, response.getAridLevel());
			VirusStatistics.setHumidityLevel(CountryClimateHumidity.TROPICAL, response.getTropicalLevel());
			VirusStatistics.setHumidityLevel(CountryClimateHumidity.MEDITERRANEAN, response.getMediterraneanLevel());
			
			// Update country list
			for(Country newC : response.getCountryList()) {
				for(Country oldC : Country.getCountryList()) {
					if(newC.getName().equals(oldC.getName())) {
						oldC.setAlivePeople(newC.getAlivePeople());
						oldC.setHealtyPeople(newC.getHealtyPeople());
						oldC.setDeadPeople(newC.getDeadPeople());
						oldC.setInfectedPeople(newC.getInfectedPeople());
						oldC.setClosedAirports(newC.isClosedAirports());
						oldC.setClosedBorders(newC.isClosedBorders());
						oldC.setClosedHospitals(newC.isClosedHospitals());
						oldC.setClosedPorts(newC.isClosedPorts());
						oldC.setState(newC.getState());
						break;
					}
				}
			}
			
			// Update map background and player points
			player.nextTurnPoints();
			turnNumber++;
			MapPanel.getInstance().createTurnBackground();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void startInterface() {
		WindowManager.showMainMenu();
		SoundManager.startBackgroundMusic();
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
	
	public static void main(String[] args) {
		Game.initGame();
		Game.startInterface();
	}

}
