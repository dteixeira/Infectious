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

	/*
	 * Constants
	 */
	private static final String			COUNTRY_PATH		= "script/country/rb/";
	private static final String			COUNTRY_UTILS_PATH	= "script/country/utils.rb";

	/*
	 * Class fields
	 */
	private static ArrayList<Country>	countryList			= new ArrayList<Country>();
	private static final long			serialVersionUID	= 1L;
	
	/*
	 * Instance fields
	 */
	private int							alivePeople;
	private ArrayList<Hexagon>			cells;
	private boolean						closedAirports;
	private boolean						closedBorders;
	private boolean						closedHospitals;
	private boolean						closedPorts;
	private int							deadPeople;
	private int							healtyPeople;
	private CountryClimateHumidity		humidity;
	private int							infectedPeople;
	private int							nAirports;
	private String						name;
	private Country[]					neighbourCountries;
	private String[]					neighbourNames;
	private int							nHospitals;
	private int							nPorts;
	private CountryState				state;
	private CountryClimateTemperature	temperature;
	private CountryThreshold			thresholds;
	private int							totalPeople;
	private CountryType					type;

	/*
	 * Class methods
	 */
	public static ArrayList<Country> getCountryList() {
		return countryList;
	}

	public static void initCountry() {
		// Load script engine
		ScriptEngine jruby = new ScriptEngineManager().getEngineByName("jruby");

		// Compile scripts
		try {
			jruby.eval(new FileReader(new File(COUNTRY_UTILS_PATH)));
			File folder = new File(COUNTRY_PATH);
			System.err.println("Loading countries...");
			for (File file : folder.listFiles()) {
				if (file.getName().endsWith(".rb")) {
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

	public static void randomizeCaseZero() {
		Country zero = countryList.get((int) (Math.random() * (countryList.size() - 1)));
		zero.setInfectedPeople(1);
		zero.setHealtyPeople(zero.getHealtyPeople() - 1);
		zero.setState(CountryState.INFECTED);
	}

	public static void resetCountries() {
		for (Country c : countryList) {
			c.resetCountry();
		}
	}

	private static Country findCountryByName(String name) {
		for (Country c : countryList)
			if (c.name.equals(name))
				return c;
		return null;
	}

	private static void findNeighbours() {
		for (Country country : countryList) {
			country.neighbourCountries = new Country[country.neighbourNames.length];
			for (int i = 0; i < country.neighbourNames.length; ++i) {
				country.neighbourCountries[i] = findCountryByName(country.neighbourNames[i]);
				if (country.neighbourCountries[i] == null) {
					System.err.println(country.name + " has no neighbour called "
							+ country.neighbourNames[i]);
					System.exit(0);
				}
			}
		}
	}

	/*
	 * Constructor
	 */
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

	/*
	 * Instance methods
	 */
	public void bindCells(ArrayList<Point> points) {
		cells = new ArrayList<Hexagon>();
		ArrayList<Hexagon> gridMap = MapPanel.getInstance().getGridMap();
		for (Point p : points) {
			for (Hexagon hex : gridMap) {
				if (hex.getX() == p.x && hex.getY() == p.y) {
					hex.setCountry(this);
					cells.add(hex);
					break;
				}
			}
		}
	}

	public int getAlivePeople() {
		return alivePeople;
	}

	public ArrayList<Hexagon> getCells() {
		return cells;
	}

	public int getDeadPeople() {
		return deadPeople;
	}

	public int getHealtyPeople() {
		return healtyPeople;
	}

	public CountryClimateHumidity getHumidity() {
		return humidity;
	}

	public int getInfectedPeople() {
		return infectedPeople;
	}

	public int getnAirports() {
		return nAirports;
	}

	public String getName() {
		return name;
	}

	public Country[] getNeighbourCountries() {
		return neighbourCountries;
	}

	public String[] getNeighbourNames() {
		return neighbourNames;
	}

	public int getnHospitals() {
		return nHospitals;
	}

	public int getnPorts() {
		return nPorts;
	}

	public CountryState getState() {
		return state;
	}

	public CountryClimateTemperature getTemperature() {
		return temperature;
	}

	public CountryThreshold getThresholds() {
		return thresholds;
	}

	public int getTotalPeople() {
		return totalPeople;
	}

	public CountryType getType() {
		return type;
	}

	public boolean isClosedAirports() {
		return closedAirports;
	}

	public boolean isClosedBorders() {
		return closedBorders;
	}

	public boolean isClosedHospitals() {
		return closedHospitals;
	}

	public boolean isClosedPorts() {
		return closedPorts;
	}

	public void setAlivePeople(int alivePeople) {
		this.alivePeople = alivePeople;
	}

	public void setCells(ArrayList<Hexagon> cells) {
		this.cells = cells;
	}

	public void setClosedAirports(boolean closedAirports) {
		this.closedAirports = closedAirports;
	}

	public void setClosedBorders(boolean closedBorders) {
		this.closedBorders = closedBorders;
	}

	public void setClosedHospitals(boolean closedHospitals) {
		this.closedHospitals = closedHospitals;
	}

	public void setClosedPorts(boolean closedPorts) {
		this.closedPorts = closedPorts;
	}

	public void setDeadPeople(int deadPeople) {
		this.deadPeople = deadPeople;
	}

	public void setHealtyPeople(int healtyPeople) {
		this.healtyPeople = healtyPeople;
	}

	public void setHumidity(CountryClimateHumidity humidity) {
		this.humidity = humidity;
	}

	public void setInfectedPeople(int infectedPeople) {
		this.infectedPeople = infectedPeople;
	}

	public void setnAirports(int nAirports) {
		this.nAirports = nAirports;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNeighbourCountries(Country[] neighbourCountries) {
		this.neighbourCountries = neighbourCountries;
	}

	public void setNeighbourNames(String[] neighbourNames) {
		this.neighbourNames = neighbourNames;
	}

	public void setnHospitals(int nHospitals) {
		this.nHospitals = nHospitals;
	}

	public void setnPorts(int nPorts) {
		this.nPorts = nPorts;
	}

	public void setState(CountryState state) {
		this.state = state;
	}

	public void setTemperature(CountryClimateTemperature temperature) {
		this.temperature = temperature;
	}

	public void setThresholds(CountryThreshold thresholds) {
		this.thresholds = thresholds;
	}

	public void setTotalPeople(int totalPeople) {
		this.totalPeople = totalPeople;
	}

	public void setType(CountryType type) {
		this.type = type;
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

}
