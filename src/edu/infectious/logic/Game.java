package edu.infectious.logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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
import edu.infectious.script.country.CountryState;
import edu.infectious.script.country.CountryType;
import edu.infectious.script.trait.Trait;
import edu.infectious.script.trait.TraitType;

public class Game {
	
	private static final int CURE_N_TURNS = 50;
	private static final int TURNS_TO_DEATH = 10;
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
	
	private static void updateTurn(SlavePackage slave) {
		// Calculate base values
		VirusStatistics.setInfectiousness(VirusStatistics.getInfectiousnessDelta() + slave.getInfectiousnessDelta());
		VirusStatistics.setDeadliness(VirusStatistics.getDeadlinessDelta() + slave.getDeadlinessDelta());
		VirusStatistics.setNotoriety(VirusStatistics.getNotorietyDelta() + slave.getNotorietyDelta());
		VirusStatistics.normalizeValues();
		
		// Infect new people
		for(Country c : Country.getCountryList()) {
			if(c.getState() == CountryState.INFECTED) {
				double water = VirusStatistics.isWaterTransmission() ? 0.1 : 0.0;
				double air = VirusStatistics.isAirTransmission() ? 0.1 : 0.0;
				double specific = (VirusStatistics.isLivestockTransmission() && c.getType() == CountryType.RURAL) ||
						(VirusStatistics.isPlagueTransmission() && c.getType() == CountryType.INDUSTRIAL) ? 0.05 : 0.0;
				double bonus = 1.0 + water + air + specific;
				double penalty = Math.sqrt(VirusStatistics.getInfectiousness() * 
						VirusStatistics.getTemperatureBonus(c.getTemperature()) *
						VirusStatistics.getHumidityBonus(c.getHumidity()));
				int newInfected = Math.min(
						c.getHealtyPeople(),
						(int) Math.ceil(c.getInfectedPeople() * bonus
								* penalty) * 2);
				c.setHealtyPeople(c.getHealtyPeople() - newInfected);
				c.setInfectedPeople(c.getInfectedPeople() + newInfected);
			}
		}
		
		// Kill people
		if (turnNumber >= TURNS_TO_DEATH) {
			for (Country c : Country.getCountryList()) {
				int newDead = Math.min(
						c.getInfectedPeople(),
						(int) Math.ceil(c.getInfectedPeople()
								* VirusStatistics.getDeadliness()));
				c.setDeadPeople(c.getDeadPeople() + newDead);
				c.setInfectedPeople(c.getInfectedPeople() - newDead);
			}
		}

		// Infect new countries
		ArrayList<Country> toInfect = new ArrayList<Country>();
		for (Country c : Country.getCountryList()) {
			if (c.getInfectedPeople() == 0 && c.getAlivePeople() > 0) {
				double penalty = VirusStatistics.getTemperatureBonus(c
						.getTemperature())
						* VirusStatistics.getHumidityBonus(c.getHumidity());
				if (penalty == 0.0)
					continue;
				double prob = 0.0;
				if (!c.isClosedAirports())
					prob += 0.05;
				if (!c.isClosedBorders())
					prob += 0.05;
				for (Country nbr : c.getNeighbourCountries()) {
					if (nbr.getInfectedPeople() > 0) {
						prob += 0.1;
						break;
					}
				}
				if (Math.random() < prob) {
					toInfect.add(c);
				}
			}
		}
		for (Country c : toInfect) {
			c.setHealtyPeople(c.getHealtyPeople() - 1);
			c.setInfectedPeople(1);
		}
		
		// Update turns to cure
		turnsToCure--;
		
		// Update country states
		for(Country c : Country.getCountryList()) {
			if(c.getDeadPeople() > 0)
				c.setState(CountryState.DEAD);
			else if(c.getInfectedPeople() > 0 && c.getAlivePeople() > 0)
				c.setState(CountryState.INFECTED);
			else if(c.getInfectedPeople() == 0 && c.getAlivePeople() > 0)
				c.setState(CountryState.OK);
			if (c.getDeadPeople() / (double) c.getTotalPeople() > c.getThresholds()
					.getDeadThreshold()
					|| c.getInfectedPeople() / (double) c.getTotalPeople() > c
							.getThresholds().getInfectedThreshold()) {
				c.setClosedAirports(true);
				c.setClosedBorders(true);
				c.setClosedHospitals(true);
				c.setClosedPorts(true);
			}
		}
	}
	
	private static void endTurnMaster() {
		try {
			// Receive slave package
			ObjectInputStream in = new ObjectInputStream(request.getSocket().getInputStream());
			SlavePackage response = (SlavePackage) in.readObject();

			// Update info
			updateTurn(response);
			
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
			// TODO
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
			
			// Update base values
			VirusStatistics.setInfectiousness(response.getInfectiousness());
			VirusStatistics.setDeadliness(response.getDeadliness());
			VirusStatistics.setNotoriety(response.getNotoriety());
			
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
