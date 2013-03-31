package edu.infectious.gui.utilities;

import java.awt.Polygon;
import java.io.Serializable;

import edu.infectious.script.country.Country;

public class Hexagon implements Serializable {

	/*
	 * Constants
	 */
	private static final long	serialVersionUID	= 1L;
	
	/*
	 * Instance fields
	 */
	private Country				country				= null;
	private Polygon				hexagon				= null;
	private int					x					= 0;
	private int					y					= 0;

	/*
	 * Constructor
	 */
	public Hexagon(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/*
	 * Instance methods
	 */
	public Country getCountry() {
		return country;
	}

	public Polygon getHexagon() {
		return hexagon;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public void setHexagon(Polygon hexagon) {
		this.hexagon = hexagon;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

}
