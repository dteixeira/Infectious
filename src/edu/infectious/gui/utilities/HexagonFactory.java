package edu.infectious.gui.utilities;

import java.awt.Polygon;

public class HexagonFactory {

	/*
	 * Instance fields
	 */
	private double	height	= 0;
	private double	radius	= 0;
	private double	side	= 0;
	private double	width	= 0;

	/*
	 * Constructor
	 */
	public HexagonFactory(double radius) {
		setupHexagonFactory(radius);
	}

	public HexagonFactory(int radius) {
		setupHexagonFactory((double) radius);
	}

	/*
	 * Instance methods
	 */
	public Hexagon buildHexagon(int x, int y) {
		Hexagon hex = new Hexagon(x, y);
		Polygon p = new Polygon();
		double mX = x * side;
		double mY = height * (2 * y + (x % 2)) / 2.0;
		p.addPoint((int) (mX + radius / 2.0), (int) (mY));
		p.addPoint((int) (mX + side), (int) (mY));
		p.addPoint((int) (mX + width), (int) (mY + height / 2.0));
		p.addPoint((int) (mX + side), (int) (mY + height));
		p.addPoint((int) (mX + radius / 2.0), (int) (mY + height));
		p.addPoint((int) (mX), (int) (mY + height / 2.0));
		hex.setHexagon(p);
		return hex;
	}

	public double getSide() {
		return side;
	}

	private void setupHexagonFactory(double r) {
		radius = r;
		width = radius * 2.0;
		height = radius * Math.sqrt(3.0);
		side = radius * 3 / 2.0;
	}

}
