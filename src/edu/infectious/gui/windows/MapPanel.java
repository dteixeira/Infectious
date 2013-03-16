package edu.infectious.gui.windows;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import edu.infectious.gui.listeners.MapManipulationListener;
import edu.infectious.gui.utilities.Hexagon;
import edu.infectious.gui.utilities.HexagonFactory;

public class MapPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int HORIZONTAL_CELLS = 150;
	private static final Color GRID_COLOR = new Color(.8f, .8f, .8f, .1f);
	private static final Color POINTER_LINE_COLOR = new Color(0f, .5f, 1f, 1f);
	private static final Color POINTER_FILL_COLOR = new Color(0f, .5f, 1f, .5f);
	private static final int GRID_STROKE_WIDTH = 5;
	private static final int POINTER_STROKE_WIDTH = 3;
	private static final String MAP_FILENAME = "images/world.jpg";

	private HexagonFactory hexagonFactory = null;
	private ArrayList<Hexagon> gridMap = null;
	private BufferedImage background = null;
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

	public MapPanel(boolean db) {
		super(db);
		setupBackground();
		setupTransform();
		setupListeners();
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
		Graphics2D g2d1 = (Graphics2D) g;

		// Clear background
		g2d1.setColor(getBackground());
		g2d1.fillRect(0, 0, getWidth(), getHeight());
		g2d1.setColor(getForeground());

		// Zoomed map
		if (zoomed) {
			// Correct scaled dimensions and maintain point
			// under the mouse cursor in the same position
			Point p = new Point();
			transform.transform(underMousePoint, p);
			g2d1.translate(-p.getX(), -p.getY());
			g2d1.translate(underMousePoint.getX(), underMousePoint.getY());

			// Simulate scroll
			g2d1.translate(scrollX, scrollY);

			// Draw full sized map
			g2d1.drawImage(background, 0, 0, null);
		}
		// Normal sized map
		else {
			g2d1.scale(widthFactor, heightFactor);
			g2d1.drawImage(background, 0, 0, null);
		}

		// Correct screen ratio for drawing hexagons
		g2d1.scale(1.0, screenRatioCorrectionFactor);

		// Draw mouse pointer
		Hexagon hex = pointer;
		g2d1.setColor(POINTER_FILL_COLOR);
		g2d1.fillPolygon(hex.getHexagon());
		g2d1.setStroke(new BasicStroke(POINTER_STROKE_WIDTH));
		g2d1.setColor(POINTER_LINE_COLOR);
		g2d1.drawPolygon(hex.getHexagon());

		// Dispose of graphics context
		g2d1.dispose();
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

}
