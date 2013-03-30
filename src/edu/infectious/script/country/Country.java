package edu.infectious.script.country;

import java.awt.Point;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import edu.infectious.gui.utilities.Hexagon;
import edu.infectious.gui.windows.MapPanel;

public class Country implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String COUNTRY_PATH = "script/country/rb/";
	private static final String COUNTRY_UTILS_PATH = "script/country/utils.rb";
	private static ArrayList<Country> countryList = new ArrayList<Country>();
	
	private ArrayList<Hexagon> cells;
	private String name;
	private CountryState state;
	private CountryClimateHumidity humidity;
	private CountryClimateTemperature temperature;
	private CountryType type;
	private int totalPeople;
	private int alivePeople;
	private int infectedPeople;
	private int healtyPeople;
	private int deadPeople;
	private int nPorts;
	private int nAirports;
	private int nHospitals;
	private boolean closedPorts;
	private boolean closedAirports;
	private boolean closedBorders;
	private boolean closedHospitals;
	private CountryThreshold thresholds;
	private String[] neighbourNames;
	private Country[] neighbourCountries;
	
	public Country() {
		name = "";
		state = CountryState.OK;
		humidity = CountryClimateHumidity.MEDITERRANEAN;
		temperature = CountryClimateTemperature.TEMPERATE;
		type = CountryType.INDUSTRIAL;
		alivePeople = 0;
		totalPeople = 0;
		infectedPeople = 0;
		healtyPeople = 0;
		deadPeople = 0;
		nPorts = 0;
		nAirports = 0;
		nHospitals = 0;
		closedPorts = false;
		closedAirports = false;
		closedBorders = false;
		closedHospitals = false;
		thresholds = new CountryThreshold(0, 0);
	}
	
	private void resetCountry() {
		state = CountryState.OK;
		alivePeople = totalPeople;
		infectedPeople = 0;
		healtyPeople = totalPeople;
		deadPeople = 0;
		closedPorts = false;
		closedAirports = false;
		closedBorders = false;
		closedHospitals = false;
	}
	
	public static void resetCountries() {
		for(Country c : countryList) {
			c.resetCountry();
		}
	}
	
	public static void randomizeCaseZero() {
		Country zero = countryList.get((int)(Math.random() * (countryList.size() - 1)));
		zero.setInfectedPeople(1);
		zero.setHealtyPeople(zero.getHealtyPeople() - 1);
		zero.setState(CountryState.INFECTED);
	}
	
	public static void initCountry() {
		// Load script engine
		ScriptEngine jruby = new ScriptEngineManager().getEngineByName("jruby");
		
		// Compile scripts
		try {
			jruby.eval(new FileReader(new File(COUNTRY_UTILS_PATH)));
			File folder = new File(COUNTRY_PATH);
			System.err.println("Loading countries...");
			for(File file : folder.listFiles()) {
				if(file.getName().endsWith(".rb")) {
					FileReader reader = new FileReader(file);
					jruby.eval(reader);
					reader.close();
					System.err.println(" * " + countryList.get(countryList.size() - 1).getName());
				}
			}
			findNeighbours();
		} catch (ScriptException | IOException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
	}
	
	private static void findNeighbours() {
		for(Country country : countryList) {
			country.neighbourCountries = new Country[country.neighbourNames.length];
			for(int i = 0; i < country.neighbourNames.length; ++i) {
				country.neighbourCountries[i] = findCountryByName(country.neighbourNames[i]);
				if(country.neighbourCountries[i] == null) {
					System.err.println(country.name + " has no neighbour called " + country.neighbourNames[i]);
					System.exit(0);
				}
			}
		}
	}
	
	private static Country findCountryByName(String name) {
		for(Country c : countryList)
			if(c.name.equals(name))
				return c;
		return null;
	}
	
	public void bindCells(ArrayList<Point> points) {
		cells = new ArrayList<Hexagon>();
		ArrayList<Hexagon> gridMap = MapPanel.getInstance().getGridMap();
		for(Point p : points) {
			for(Hexagon hex : gridMap) {
				if(hex.getX() == p.x && hex.getY() == p.y) {
					hex.setCountry(this);
					cells.add(hex);
					break;
				}
			}
		}
	}

	public ArrayList<Hexagon> getCells() {
		return cells;
	}

	public void setCells(ArrayList<Hexagon> cells) {
		this.cells = cells;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static ArrayList<Country> getCountryList() {
		return countryList;
	}

	public CountryState getState() {
		return state;
	}

	public void setState(CountryState state) {
		this.state = state;
	}

	public CountryClimateHumidity getHumidity() {
		return humidity;
	}

	public void setHumidity(CountryClimateHumidity humidity) {
		this.humidity = humidity;
	}

	public CountryClimateTemperature getTemperature() {
		return temperature;
	}

	public void setTemperature(CountryClimateTemperature temperature) {
		this.temperature = temperature;
	}

	public CountryType getType() {
		return type;
	}

	public void setType(CountryType type) {
		this.type = type;
	}

	public int getAlivePeople() {
		return alivePeople;
	}

	public void setAlivePeople(int alivePeople) {
		this.alivePeople = alivePeople;
	}

	public int getInfectedPeople() {
		return infectedPeople;
	}

	public void setInfectedPeople(int infectedPeople) {
		this.infectedPeople = infectedPeople;
	}

	public int getHealtyPeople() {
		return healtyPeople;
	}

	public void setHealtyPeople(int healtyPeople) {
		this.healtyPeople = healtyPeople;
	}

	public int getDeadPeople() {
		return deadPeople;
	}

	public void setDeadPeople(int deadPeople) {
		this.deadPeople = deadPeople;
	}

	public int getnPorts() {
		return nPorts;
	}

	public void setnPorts(int nPorts) {
		this.nPorts = nPorts;
	}

	public int getnAirports() {
		return nAirports;
	}

	public void setnAirports(int nAirports) {
		this.nAirports = nAirports;
	}

	public int getnHospitals() {
		return nHospitals;
	}

	public void setnHospitals(int nHospitals) {
		this.nHospitals = nHospitals;
	}

	public int getTotalPeople() {
		return totalPeople;
	}

	public void setTotalPeople(int totalPeople) {
		this.totalPeople = totalPeople;
	}
	
	public CountryThreshold getThresholds() {
		return thresholds;
	}

	public void setThresholds(CountryThreshold thresholds) {
		this.thresholds = thresholds;
	}

	public String[] getNeighbourNames() {
		return neighbourNames;
	}

	public void setNeighbourNames(String[] neighbourNames) {
		this.neighbourNames = neighbourNames;
	}

	public boolean isClosedPorts() {
		return closedPorts;
	}

	public void setClosedPorts(boolean closedPorts) {
		this.closedPorts = closedPorts;
	}

	public boolean isClosedAirports() {
		return closedAirports;
	}

	public void setClosedAirports(boolean closedAirports) {
		this.closedAirports = closedAirports;
	}

	public boolean isClosedBorders() {
		return closedBorders;
	}

	public void setClosedBorders(boolean closedBorders) {
		this.closedBorders = closedBorders;
	}

	public boolean isClosedHospitals() {
		return closedHospitals;
	}

	public void setClosedHospitals(boolean closedHospitals) {
		this.closedHospitals = closedHospitals;
	}

	public Country[] getNeighbourCountries() {
		return neighbourCountries;
	}

	public void setNeighbourCountries(Country[] neighbourCountries) {
		this.neighbourCountries = neighbourCountries;
	}

}
