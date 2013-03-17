package edu.infectious.script.country;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import edu.infectious.gui.utilities.Hexagon;
import edu.infectious.gui.windows.MapPanel;

public class Country {
	
	private static final String COUNTRY_PATH = "script/country/";
	private static final String COUNTRY_UTILS_NAME = "utils.rb";
	private static ArrayList<Country> countryList = new ArrayList<Country>();
	
	private ArrayList<Hexagon> cells;
	private Color countryColor;
	private String name;
	private CountryState state;
	
	public Country() {
		state = CountryState.OK;
	}
	
	public static void initCountry() {
		// Load script engine
		ScriptEngine jruby = new ScriptEngineManager().getEngineByName("jruby");
		
		// Compile scripts
		try {
			jruby.eval(new FileReader(new File(COUNTRY_PATH + COUNTRY_UTILS_NAME)));
			File folder = new File(COUNTRY_PATH);
			for(File file : folder.listFiles()) {
				if(file.getName().endsWith(".rb") && !file.getName().toLowerCase().equals(COUNTRY_UTILS_NAME)) {
					jruby.eval(new FileReader(file));
					System.out.println(countryList.get(0).getName());
				}
			}
		} catch (FileNotFoundException | ScriptException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
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

	public Color getCountryColor() {
		return countryColor;
	}

	public void setCountryColor(Color countryColor) {
		this.countryColor = countryColor;
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

}
