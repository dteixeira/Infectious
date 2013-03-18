package edu.infectious.gui.windows;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import edu.infectious.gui.listeners.CountryInfoDialogListener;
import edu.infectious.gui.utilities.Button;
import edu.infectious.gui.utilities.Hexagon;
import edu.infectious.script.country.Country;

public class CountryInfoDialog extends StandardDialog {

	private static final long serialVersionUID = 1L;
	private static final Color BUTTON_COLOR = new Color(0.149f, 0.451f, 0.925f);
	private static final Color BUTTON_LINE_COLOR = Color.WHITE;
	private static final Color BUTTON_HOVER_COLOR = new Color(0.349f, 0.573f,
			0.925f);
	private static final Color COUNTRY_GRID_COLOR = new Color(.7f, .7f, .7f,
			0.3f);
	private static final Color COUNTRY_GRID_LINE_COLOR = new Color(.7f, .7f,
			.7f, 0.8f);
	private static final Color TEXT_AREA_COLOR = new Color(.3f, .3f, .3f);
	private static final Color TEXT_AREA_LINE_COLOR = Color.WHITE;
	private static final double COUNTRY_GRID_SCALE_FACTOR = 0.35;
	private static final int BUTTON_X = 600;
	private static final int BUTTON_Y = 520;
	private static final int BUTTON_WIDTH = 180;
	private static final int BUTTON_HEIGHT = 60;
	private static final int BUTTON_LINE_WIDTH = 3;
	private static final int COUNTRY_GRID_STROKE_WIDTH = 5;
	private static final int COUNTRY_X = 600;
	private static final int COUNTRY_Y = 20;
	private static final int COUNTRY_WIDTH = 180;
	private static final int COUNTRY_HEIGHT = 60;
	private static final String BUTTON_TEXT = "Close";

	private Country country;
	private JPanel panel;
	private Button closeButton;
	private Button countryName;
	private boolean buttonHover = false;
	private double translateX = 0.0;
	private double translateY = 0.0;
	private BufferedImage countryGrid;

	public CountryInfoDialog(Country country) {
		this.country = country;
		setupButton();
		setupPanel();
		setupListeners();
		setupBackground();
	}

	private void setupBackground() {
		Rectangle2D rect = country.getCells().get(0).getHexagon().getBounds2D();
		double minX = rect.getMinX();
		double minY = rect.getMinY();
		double maxX = rect.getMaxX();
		double maxY = rect.getMaxY();
		for (Hexagon hex : country.getCells()) {
			rect = hex.getHexagon().getBounds2D();
			if (rect.getMinX() < minX)
				minX = rect.getMinX();
			if (rect.getMinY() < minY)
				minY = rect.getMinY();
			if (rect.getMaxX() > maxX)
				maxX = rect.getMaxX();
			if (rect.getMaxY() > maxY)
				maxY = rect.getMaxY();
		}
		translateX = (getWidth() - (maxX - minX) * COUNTRY_GRID_SCALE_FACTOR) / 2.0;
		translateY = (getHeight() - (maxY - minY) * COUNTRY_GRID_SCALE_FACTOR) / 2.0;

		// Create country grid
		countryGrid = new BufferedImage(background.getWidth(),
				background.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = countryGrid.createGraphics();
		g2d.setBackground(new Color(0f, 0f, 0f, 0f));
		g2d.clearRect(0, 0, countryGrid.getWidth(), countryGrid.getHeight());
		g2d.scale(COUNTRY_GRID_SCALE_FACTOR, COUNTRY_GRID_SCALE_FACTOR);
		g2d.translate(-minX, -minY);
		g2d.setStroke(new BasicStroke(COUNTRY_GRID_STROKE_WIDTH));
		for (Hexagon hex : country.getCells()) {
			g2d.setColor(COUNTRY_GRID_COLOR);
			g2d.fillPolygon(hex.getHexagon());
			g2d.setColor(COUNTRY_GRID_LINE_COLOR);
			g2d.drawPolygon(hex.getHexagon());
		}
		g2d.dispose();
	}

	private void setupButton() {
		closeButton = new Button(BUTTON_X, BUTTON_Y, BUTTON_WIDTH,
				BUTTON_HEIGHT, BUTTON_LINE_WIDTH, BUTTON_TEXT, BUTTON_COLOR,
				BUTTON_LINE_COLOR);
		countryName = new Button(COUNTRY_X, COUNTRY_Y, COUNTRY_WIDTH,
				COUNTRY_HEIGHT, BUTTON_LINE_WIDTH, country.getName(),
				TEXT_AREA_COLOR, TEXT_AREA_LINE_COLOR);
	}

	private void setupPanel() {
		panel = new JPanel(true) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				// Draw static background
				g2d.drawImage(background, 0, 0, null);

				// Draw country grid
				g2d.translate(translateX, translateY);
				g2d.drawImage(countryGrid, 0, 0, null);
				g2d.translate(-translateX, -translateY);
				
				// Draw country name
				g2d.setFont(new Font("Sans-serif", Font.BOLD, 20));
				countryName.paintButton(g2d);

				// Draw close button
				g2d.setFont(new Font("Sans-serif", Font.BOLD, 20));
				if (buttonHover)
					closeButton.setFillColor(BUTTON_HOVER_COLOR);
				else
					closeButton.setFillColor(BUTTON_COLOR);
				closeButton.paintButton(g2d);
				g2d.dispose();
			}
		};
		getContentPane().add(panel);
	}

	private void setupListeners() {
		CountryInfoDialogListener listener = new CountryInfoDialogListener(
				closeButton);
		addMouseListener(listener);
		addMouseMotionListener(listener);
	}

	public boolean isButtonHover() {
		return buttonHover;
	}

	public void setButtonHover(boolean buttonHover) {
		this.buttonHover = buttonHover;
	}

}
