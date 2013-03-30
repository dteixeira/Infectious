package edu.infectious.gui.utilities;

import java.awt.Polygon;
import java.io.Serializable;

import edu.infectious.script.country.Country;

public class Hexagon implements Serializable {

	private static final long serialVersionUID = 1L;
	private Polygon hexagon = null;
	private int x = 0;
	private int y = 0;
	private Country country = null;

	public Polygon getHexagon() {
		return hexagon;
	}

	public void setHexagon(Polygon hexagon) {
		this.hexagon = hexagon;
	}

	public Hexagon(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}
