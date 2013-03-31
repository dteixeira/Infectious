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

	/*
	 * Constants
	 */
	private static final Color	BUTTON_COLOR				= new Color(0.149f, 0.451f, 0.925f);
	private static final int	BUTTON_HEIGHT				= 60;
	private static final Color	BUTTON_HOVER_COLOR			= new Color(0.349f, 0.573f, 0.925f);
	private static final Color	BUTTON_LINE_COLOR			= Color.WHITE;
	private static final int	BUTTON_LINE_WIDTH			= 3;
	private static final String	BUTTON_TEXT					= "Close";
	private static final int	BUTTON_WIDTH				= 180;
	private static final int	BUTTON_X					= 600;
	private static final int	BUTTON_Y					= 520;
	private static final Color	COUNTRY_GRID_COLOR			= new Color(.7f, .7f, .7f, 0.3f);
	private static final Color	COUNTRY_GRID_LINE_COLOR		= new Color(.7f, .7f, .7f, 0.8f);
	private static final double	COUNTRY_GRID_SCALE_FACTOR	= 0.35;
	private static final int	COUNTRY_GRID_STROKE_WIDTH	= 5;
	private static final int	COUNTRY_HEIGHT				= 40;
	private static final int	COUNTRY_WIDTH				= 280;
	private static final int	COUNTRY_X					= 500;
	private static final int	COUNTRY_Y					= 20;
	private static final long	serialVersionUID			= 1L;
	private static final Color	TEXT_AREA_COLOR				= new Color(.3f, .3f, .3f, .8f);
	private static final Color	TEXT_AREA_LINE_COLOR		= Color.WHITE;
	private static final int	TEXT_LINE_WIDTH				= 2;

	/*
	 * Instance fields
	 */
	private boolean				buttonHover					= false;
	private Button				closeButton;
	private Country				country;
	private BufferedImage		countryGrid;
	private Button				countryHumidity;
	private Button				countryName;
	private Button				countryTemperature;
	private Button				countryType;
	private JPanel				panel;
	private double				translateX					= 0.0;
	private double				translateY					= 0.0;

	/*
	 * Constructor
	 */
	public CountryInfoDialog(Country country) {
		this.country = country;
		setupDialog();
		setupButton();
		setupPanel();
		setupListeners();
		setupBackground();
	}

	/*
	 * Instance methods
	 */
	public boolean isButtonHover() {
		return buttonHover;
	}

	public void setButtonHover(boolean buttonHover) {
		this.buttonHover = buttonHover;
	}

	private void drawInstitution(Graphics2D g2d) {
		g2d.setStroke(new BasicStroke(TEXT_LINE_WIDTH));
		g2d.setFont(new Font("Sans-serif", Font.PLAIN, 18));
		g2d.setColor(TEXT_AREA_COLOR);
		g2d.fillRect(20, 355, 200, 110);
		g2d.setColor(TEXT_AREA_LINE_COLOR);
		g2d.drawRect(20, 355, 200, 110);
		g2d.setColor(Color.WHITE);
		g2d.drawString("Airports", 30, 380);
		g2d.drawString("Ports", 30, 405);
		g2d.drawString("Hospitals", 30, 430);
		g2d.drawString("Borders", 30, 455);
		g2d.setFont(new Font("Sans-serif", Font.BOLD, 18));
		drawOpenClose(g2d, country.isClosedAirports(), 380);
		drawOpenClose(g2d, country.isClosedPorts(), 405);
		drawOpenClose(g2d, country.isClosedHospitals(), 430);
		drawOpenClose(g2d, country.isClosedBorders(), 455);
	}

	private void drawOpenClose(Graphics2D g2d, boolean closed, int y) {
		if (closed) {
			g2d.setColor(Color.GRAY);
			g2d.drawString("Closed", 152, y);
		} else {
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.drawString("Open", 165, y);
		}
	}

	private void drawPeople(Graphics2D g2d) {
		g2d.setStroke(new BasicStroke(TEXT_LINE_WIDTH));
		g2d.setFont(new Font("Sans-serif", Font.PLAIN, 18));
		g2d.setColor(TEXT_AREA_COLOR);
		g2d.fillRect(20, 470, 200, 110);
		g2d.setColor(TEXT_AREA_LINE_COLOR);
		g2d.drawRect(20, 470, 200, 110);
		g2d.drawString("Alive", 30, 495);
		g2d.drawString("Healthy", 30, 520);
		g2d.drawString("Infected", 30, 545);
		g2d.drawString("Dead", 30, 570);
		g2d.drawString("" + country.getAlivePeople(), 110, 495);
		g2d.drawString("" + country.getHealtyPeople(), 110, 520);
		g2d.drawString("" + country.getInfectedPeople(), 110, 545);
		g2d.drawString("" + country.getDeadPeople(), 110, 570);
	}

	private String getCountryHumidity() {
		switch (country.getHumidity()) {
		case ARID:
			return "Arid";
		case MEDITERRANEAN:
			return "Mediterranean";
		case TROPICAL:
			return "Tropical";
		default:
			return "";
		}
	}

	private String getCountryTemperature() {
		switch (country.getTemperature()) {
		case HOT:
			return "Hot";
		case COLD:
			return "Cold";
		case TEMPERATE:
			return "Temperate";
		default:
			return "";
		}
	}

	private String getCountryType() {
		switch (country.getType()) {
		case INDUSTRIAL:
			return "Industrial";
		case RURAL:
			return "Rural";
		default:
			return "";
		}
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
		countryGrid = new BufferedImage(background.getWidth(), background.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
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
		closeButton = new Button(BUTTON_X, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
				BUTTON_LINE_WIDTH, BUTTON_TEXT, BUTTON_COLOR, BUTTON_LINE_COLOR);
		countryName = new Button(COUNTRY_X, COUNTRY_Y, COUNTRY_WIDTH, COUNTRY_HEIGHT,
				TEXT_LINE_WIDTH, country.getName(), TEXT_AREA_COLOR, TEXT_AREA_LINE_COLOR);
		countryType = new Button(COUNTRY_X, COUNTRY_Y + COUNTRY_HEIGHT + 5, 100, 60,
				TEXT_LINE_WIDTH, getCountryType(), TEXT_AREA_COLOR, TEXT_AREA_LINE_COLOR);
		countryTemperature = new Button(COUNTRY_X + 100 + 5, COUNTRY_Y + COUNTRY_HEIGHT + 5, 175,
				27, TEXT_LINE_WIDTH, getCountryTemperature(), TEXT_AREA_COLOR, TEXT_AREA_LINE_COLOR);
		countryHumidity = new Button(COUNTRY_X + 100 + 5, COUNTRY_Y + COUNTRY_HEIGHT + 37, 175, 28,
				TEXT_LINE_WIDTH, getCountryHumidity(), TEXT_AREA_COLOR, TEXT_AREA_LINE_COLOR);
	}

	private void setupDialog() {
		setOpacity(0.9f);
	}

	private void setupListeners() {
		CountryInfoDialogListener listener = new CountryInfoDialogListener(closeButton);
		addMouseListener(listener);
		addMouseMotionListener(listener);
	}

	private void setupPanel() {
		panel = new JPanel(true) {

			private static final long	serialVersionUID	= 1L;

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
				g2d.setFont(new Font("Sans-serif", Font.BOLD, 18));
				countryType.paintButton(g2d);
				g2d.setFont(new Font("Sans-serif", Font.BOLD, 16));
				countryTemperature.paintButton(g2d);
				countryHumidity.paintButton(g2d);

				// Draw institution info
				drawInstitution(g2d);

				// Draw people info
				drawPeople(g2d);

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

}
