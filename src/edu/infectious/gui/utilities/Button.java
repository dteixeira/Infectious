package edu.infectious.gui.utilities;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

public class Button {
	
	private final int x;
	private final int y;
	private final int width;
	private final int height;
	private final int strokeWidth;
	private final Polygon hitBox;
	private final Stroke lineStroke;
	private String text = "";
	private Color fillColor;
	private Color lineColor;
	private int textX;
	private int textY;
	
	public Button(int x, int y, int width, int height, String text) {
		this(x, y, width, height, 3, text, new Color(0f, 0f, 0f), new Color(1f, 1f, 1f));
	}
	
	public Button(int x, int y, int width, int height, int strokeWidth, String text, Color fillColor, Color lineColor) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.strokeWidth = strokeWidth;
		this.fillColor = fillColor;
		this.lineColor = lineColor;
		this.lineStroke = new BasicStroke(strokeWidth);
		
		// Create polygon for hit box
		this.hitBox = new Polygon();
		hitBox.addPoint(x, y);
		hitBox.addPoint(x + width, y);
		hitBox.addPoint(x + width, y + height);
		hitBox.addPoint(x, y + height);
	}
	
	private void updateTextOffsets(Graphics g) {
		Rectangle2D bounds = g.getFontMetrics().getStringBounds(text, g);
		textX = (int)(width / 2.0) + x - (int)(bounds.getWidth() / 2.0);
		textY = (int)(height / 2.0) + y - (int)(bounds.getHeight() / 2.0) + g.getFontMetrics().getAscent();
	}
	
	public void paintButton(Graphics g) {
		updateTextOffsets(g);
		g.setColor(fillColor);
		g.fillPolygon(hitBox);
		g.setColor(lineColor);
		((Graphics2D) g).setStroke(lineStroke);
		g.drawPolygon(hitBox);
		g.drawString(text, textX, textY);
	}
	
	public Polygon getHitBox() {
		return this.hitBox;
	}
	
	public boolean isHit(Point p) {
		return hitBox.contains(p);
	}

	public Color getFillcolor() {
		return fillColor;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public int getTextX() {
		return textX;
	}

	public void setTextX(int textX) {
		this.textX = textX;
	}

	public int getTextY() {
		return textY;
	}

	public void setTextY(int textY) {
		this.textY = textY;
	}

	public int getStrokeWidth() {
		return strokeWidth;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

}
