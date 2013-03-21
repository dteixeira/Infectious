package edu.infectious.gui.windows;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import edu.infectious.gui.listeners.MapManipulationListener;
import edu.infectious.gui.utilities.Button;
import edu.infectious.gui.utilities.Hexagon;
import edu.infectious.gui.utilities.HexagonFactory;
import edu.infectious.logic.VirusStatistics;
import edu.infectious.script.country.Country;
import edu.infectious.script.country.CountryState;

public class MapPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final double FPS_LIMIT = 30;
	private static final int HORIZONTAL_CELLS = 150;
	private static final Color GRID_COLOR = new Color(.8f, .8f, .8f, .1f);
	private static final Color POINTER_LINE_COLOR = new Color(0f, .5f, 1f, 1f);
	private static final Color POINTER_FILL_COLOR = new Color(0f, .5f, 1f, .5f);
	private static final Color OK_COLOR = new Color(0f, 0.459f, 0.063f, .3f);
	private static final Color INFECTED_COLOR = new Color(0.831f, 0.804f, 0f, .3f);
	private static final Color DEAD_COLOR = new Color(0.659f, 0.122f, 0f, .3f);
	private static final Color LOWER_BAR_COLOR = new Color(0.123f, 0.123f, 0.123f, 0.85f);
	private static final Color BUTTON_COLOR = new Color(0.149f, 0.451f, 0.925f);
	private static final Color BUTTON_LINE_COLOR = Color.WHITE;
	private static final Color BUTTON_HOVER_COLOR = new Color(0.349f, 0.573f, 0.925f);
	private static final Color STAT_BAR_COLOR = new Color(0.149f, 0.451f, 0.925f, .5f);
	private static final int GRID_STROKE_WIDTH = 5;
	private static final int POINTER_STROKE_WIDTH = 3;
	private static final String MAP_FILENAME = "images/world2.jpg";
	private static MapPanel instance = null;

	private HexagonFactory hexagonFactory = null;
	private ArrayList<Hexagon> gridMap = null;
	private BufferedImage background = null;
	private BufferedImage turnBackground = null;
	private Button menuButton = null;
	private Button pointsCounter = null;
	private Dimension screenSize = null;
	private Point underMousePoint = new Point(0, 0);
	private Hexagon pointer = null;
	private AffineTransform transform = null;
	private boolean zoomed = false;
	private int scrollX = 0;
	private int scrollY = 0;
	private double widthFactor = 0.0;
	private double heightFactor = 0.0;
	private double screenRatioCorrectionFactor = 0.0;
	private Polygon lowerBar = null;
	private boolean hoverBar = false;
	private boolean hoverMenuButton = false;
	private Cursor cursor = null;

	public static MapPanel getInstance() {
		if (instance == null)
			instance = new MapPanel(true);
		return instance;
	}

	private MapPanel(boolean db) {
		super(db);
		setupCursor();
		setupBackground();
		setupTransform();
		setupListeners();
		setupLowerBar();
	}
	
	public static void startUpdate() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Runnable update = new Runnable() {
					@Override
					public void run() {
						MapPanel.getInstance().repaint();
					}
				};
				while(true) {
					try {
						SwingUtilities.invokeLater(update);
						Thread.sleep((int)(1000 / FPS_LIMIT));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).run();
	}
	
	private void setupCursor() {
		cursor = getToolkit().createCustomCursor(
				new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB),
				new Point(0, 0), "null");
		setCursor(cursor);
	}

	private void setupLowerBar() {
		// Bar
		lowerBar = new Polygon();
		lowerBar.addPoint(0, screenSize.height - 40);
		lowerBar.addPoint(screenSize.width, screenSize.height - 40);
		lowerBar.addPoint(screenSize.width, screenSize.height);
		lowerBar.addPoint(0, screenSize.height);

		// Menu button
		menuButton = new Button(screenSize.width - 110, screenSize.height - 35,
				100, 30, 2, "Menu", BUTTON_COLOR, BUTTON_LINE_COLOR);

		// Points counter
		pointsCounter = new Button(10, screenSize.height - 35, 100, 30, 2, "0",
				new Color(0f, 0f, 0f, 0f), Color.WHITE);
	}

	private void setupBackground() {
		try {
			BufferedImage toResize = ImageIO.read(new File(MAP_FILENAME));
			screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

			// Adjust background image ratio
			double adjustedHeight = toResize.getWidth()
					* screenSize.getHeight() / screenSize.getWidth();
			GraphicsEnvironment env = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			GraphicsDevice device = env.getDefaultScreenDevice();
			GraphicsConfiguration config = device.getDefaultConfiguration();
			background = config.createCompatibleImage(
					(int) toResize.getWidth(), (int) adjustedHeight,
					Transparency.TRANSLUCENT);
			Graphics2D g2d = background.createGraphics();
			screenRatioCorrectionFactor = adjustedHeight / toResize.getHeight();
			g2d.scale(1.0, screenRatioCorrectionFactor);
			g2d.drawImage(toResize, 0, 0, null);
			g2d.dispose();
			widthFactor = screenSize.getWidth() / background.getWidth();
			heightFactor = screenSize.getHeight() / background.getHeight();
			g2d.dispose();

			// Build hexagonGrid
			setupHexagonGrid(toResize.getWidth(), toResize.getHeight(),
					adjustedHeight);

			// Setup turn background
			createTurnBackground();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void setupHexagonGrid(int width, int height, double adjustedHeight) {
		hexagonFactory = new HexagonFactory((width / (double) HORIZONTAL_CELLS));
		gridMap = new ArrayList<Hexagon>();
		BufferedImage toResize = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = toResize.createGraphics();
		g2d.setBackground(new Color(1f, 1f, 1f, 0f));
		g2d.clearRect(0, 0, toResize.getWidth(), toResize.getHeight());
		g2d.setColor(GRID_COLOR);
		BasicStroke bs = new BasicStroke(GRID_STROKE_WIDTH);
		g2d.setStroke(bs);
		for (int x = 0; x <= HORIZONTAL_CELLS; ++x) {
			for (int y = 0; y <= toResize.getHeight()
					/ (toResize.getWidth() / (double) HORIZONTAL_CELLS); ++y) {
				Hexagon hex = hexagonFactory.buildHexagon(x, y);
				gridMap.add(hex);
				g2d.drawPolygon(hex.getHexagon());
			}
		}
		g2d.dispose();
		pointer = gridMap.get(0);
		g2d = background.createGraphics();
		g2d.scale(1.0, adjustedHeight / height);
		g2d.drawImage(toResize, 0, 0, null);
		g2d.dispose();
	}

	private void setupTransform() {
		transform = new AffineTransform();
		transform.scale(background.getWidth() / screenSize.getWidth(),
				background.getHeight() / screenSize.getHeight());
	}

	private void setupListeners() {
		MapManipulationListener mouse = new MapManipulationListener();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		addMouseWheelListener(mouse);
	}

	@Override
	protected void paintComponent(Graphics g) {
		// Enforce volatile canvas creation
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform oldTransform = g2d.getTransform();

		// Clear background
		g2d.setColor(getBackground());
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setColor(getForeground());

		// Zoomed map
		if (zoomed) {
			// Correct scaled dimensions and maintain point
			// under the mouse cursor in the same position
			Point p = new Point();
			transform.transform(underMousePoint, p);
			g2d.translate(-p.getX(), -p.getY());
			g2d.translate(underMousePoint.getX(), underMousePoint.getY());

			// Simulate scroll
			g2d.translate(scrollX, scrollY);

			// Draw full sized map
			g2d.drawImage(turnBackground, 0, 0, null);
		}
		// Normal sized map
		else {
			g2d.scale(widthFactor, heightFactor);
			g2d.drawImage(turnBackground, 0, 0, null);
		}

		// Correct screen ratio for drawing hexagons
		g2d.scale(1.0, screenRatioCorrectionFactor);

		// Draw selected country, if any
		Hexagon hex = pointer;
		Country country = hex.getCountry();
		if (country != null) {
			for (Hexagon h : country.getCells()) {
				g2d.setColor(POINTER_FILL_COLOR);
				g2d.fillPolygon(h.getHexagon());
				g2d.setStroke(new BasicStroke(POINTER_STROKE_WIDTH));
				g2d.setColor(POINTER_LINE_COLOR);
				g2d.drawPolygon(h.getHexagon());
			}
		}

		// Draw mouse pointer
		if(!hoverBar) {
			g2d.setColor(POINTER_FILL_COLOR);
			g2d.fillPolygon(hex.getHexagon());
			g2d.setStroke(new BasicStroke(POINTER_STROKE_WIDTH));
			if (country == null)
				g2d.setColor(POINTER_LINE_COLOR);
			else
				g2d.setColor(Color.WHITE);
			g2d.drawPolygon(hex.getHexagon());
		}

		// Draw lower bar
		g2d.setTransform(oldTransform);
		g2d.setColor(LOWER_BAR_COLOR);
		g2d.fillPolygon(lowerBar);

		// Draw menu button
		if(hoverMenuButton)
			menuButton.setFillColor(BUTTON_HOVER_COLOR);
		else
			menuButton.setFillColor(BUTTON_COLOR);
		g2d.setFont(new Font("Sans-serif", Font.PLAIN, 20));
		menuButton.paintButton(g2d);

		// Draw point counter
		pointsCounter.paintButton(g2d);

		// Draw stat bars
		drawStatBars(g2d);

		// Dispose of graphics context
		g2d.dispose();
	}

	private void drawStatBars(Graphics2D g2d) {
		int totalWidth = (int) ((screenSize.width - menuButton.getWidth()
				- pointsCounter.getWidth() - 60) / 3.0);
		int leftSpace = 10 + pointsCounter.getWidth();
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.WHITE);
		
		// Infectiousness bar
		g2d.setColor(STAT_BAR_COLOR);
		g2d.fillRect(leftSpace + 10, screenSize.height - 35, (int) (totalWidth * VirusStatistics.getInfectiousness()), 30);
		g2d.setColor(Color.WHITE);
		g2d.drawRect(leftSpace + 10, screenSize.height - 35, totalWidth, 30);
		drawStatText(g2d, "Infectiousness", leftSpace + 10, screenSize.height - 35, totalWidth, 30);
		
		// Notoriety bar
		g2d.setColor(STAT_BAR_COLOR);
		g2d.fillRect(leftSpace + 20 + totalWidth, screenSize.height - 35, (int) (totalWidth * VirusStatistics.getNotoriety()), 30);
		g2d.setColor(Color.WHITE);
		g2d.drawRect(leftSpace + 20 + totalWidth, screenSize.height - 35, totalWidth, 30);
		drawStatText(g2d, "Notoriety", leftSpace + 20 + totalWidth, screenSize.height - 35, totalWidth, 30);
		
		// Deadliness bar
		g2d.setColor(STAT_BAR_COLOR);
		g2d.fillRect(leftSpace + 30 + totalWidth * 2, screenSize.height - 35, (int) (totalWidth * VirusStatistics.getDeadliness()), 30);
		g2d.setColor(Color.WHITE);
		g2d.drawRect(leftSpace + 30 + totalWidth * 2, screenSize.height - 35, totalWidth, 30);
		drawStatText(g2d, "Deadliness", leftSpace + 30 + totalWidth * 2, screenSize.height - 35, totalWidth, 30);
	}
	
	private void drawStatText(Graphics2D g2d, String text, int x, int y, int width, int height) {
		Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(text, g2d);
		int textX = (int)(width / 2.0) + x - (int)(bounds.getWidth() / 2.0);
		int textY = (int)(height / 2.0) + y - (int)(bounds.getHeight() / 2.0) + g2d.getFontMetrics().getAscent();
		g2d.setColor(new Color(.8f, .8f, .8f));
		g2d.setFont(new Font("Sans-serif", Font.PLAIN, 18));
		g2d.drawString(text, textX, textY);
	}

	private void createTurnBackground() {
		if (turnBackground == null)
			turnBackground = new BufferedImage(background.getWidth(),
					background.getHeight(), background.getType());
		Graphics2D g = turnBackground.createGraphics();
		g.drawImage(background, 0, 0, null);
		g.setStroke(new BasicStroke(POINTER_STROKE_WIDTH));
		g.scale(1.0, screenRatioCorrectionFactor);
		for (Country c : Country.getCountryList()) {
			for (Hexagon hex : c.getCells()) {
				setStateColor(g, c.getState());
				g.fillPolygon(hex.getHexagon());
			}
		}
	}

	private void setStateColor(Graphics2D g, CountryState state) {
		switch (state) {
		case OK:
			g.setColor(OK_COLOR);
			break;
		case INFECTED:
			g.setColor(INFECTED_COLOR);
			break;
		case DEAD:
			g.setColor(DEAD_COLOR);
			break;
		default:
			break;
		}
	}

	public void refreshTurn() {
		createTurnBackground();
		repaint();
	}

	public boolean isZoomed() {
		return zoomed;
	}

	public void setZoomed(boolean zoomed) {
		this.zoomed = zoomed;
	}

	public int getScrollX() {
		return scrollX;
	}

	public void setScrollX(int scrollX) {
		this.scrollX = scrollX;
	}

	public int getScrollY() {
		return scrollY;
	}

	public void setScrollY(int scrollY) {
		this.scrollY = scrollY;
	}

	public Point getUnderMousePoint() {
		return underMousePoint;
	}

	public void setUnderMousePoint(Point underMousePoint) {
		this.underMousePoint = underMousePoint;
	}

	public AffineTransform getTransform() {
		return transform;
	}

	public void setTransform(AffineTransform transform) {
		this.transform = transform;
	}

	public Dimension getScreenSize() {
		return screenSize;
	}

	public void setScreenSize(Dimension screenSize) {
		this.screenSize = screenSize;
	}

	public Dimension getBackgroundDimensions() {
		return new Dimension(background.getWidth(), background.getHeight());
	}

	public Hexagon getPointer() {
		return pointer;
	}

	public void setPointer(Hexagon pointer) {
		this.pointer = pointer;
	}

	public ArrayList<Hexagon> getGridMap() {
		return gridMap;
	}

	public void setGridMap(ArrayList<Hexagon> gridMap) {
		this.gridMap = gridMap;
	}

	public double getScreenRatioCorrectionFactor() {
		return screenRatioCorrectionFactor;
	}

	public void setScreenRatioCorrectionFactor(
			double screenRatioCorrectionFactor) {
		this.screenRatioCorrectionFactor = screenRatioCorrectionFactor;
	}

	public boolean isHoverBar() {
		return hoverBar;
	}

	public void setHoverBar(boolean hoverBar) {
		this.hoverBar = hoverBar;
	}

	public boolean isHoverMenuButton() {
		return hoverMenuButton;
	}

	public void setHoverMenuButton(boolean hoverMenuButton) {
		this.hoverMenuButton = hoverMenuButton;
	}

	public Button getMenuButton() {
		return menuButton;
	}

	public Polygon getLowerBar() {
		return lowerBar;
	}

	public Cursor getCursor() {
		return cursor;
	}

}
