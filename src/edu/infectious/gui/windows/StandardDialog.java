package edu.infectious.gui.windows;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JPanel;

import edu.infectious.gui.utilities.Button;
import edu.infectious.gui.utilities.HexagonFactory;

public abstract class StandardDialog extends JDialog {

	/*
	 * Constants
	 */
	private static final long		serialVersionUID	= 1L;

	/*
	 * Class fields
	 */
	private static BufferedImage	standardBackground;
	private static Color			standardBackgroundColor;
	private static Dimension		standardDimensions;
	
	/*
	 * Instance fields
	 */
	protected BufferedImage		background;
	protected Color				backgroundColor;
	protected ArrayList<Button>	buttons;
	protected Dimension			dimensions;
	protected JPanel			panel;

	/*
	 * Class instances
	 */
	public static void setupDialogs() {
		setupDialogs(new Color(0.137f, 0.137f, 0.137f), new Dimension(800, 600));
	}

	public static void setupDialogs(Color color, Dimension dimension) {
		standardDimensions = dimension;
		standardBackgroundColor = color;
		setupStandardBackground();
	}

	public static void setupDialogs(Dimension dimension) {
		setupDialogs(new Color(0.137f, 0.137f, 0.137f), dimension);
	}

	private static void drawHexagon(Graphics g, Color c, Polygon p) {
		g.setColor(c);
		g.fillPolygon(p);
		g.setColor(Color.WHITE);
		g.drawPolygon(p);
	}

	private static void setupStandardBackground() {
		standardBackground = new BufferedImage(300, 200, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = standardBackground.createGraphics();
		g.setBackground(new Color(0f, 0f, 0f, 0f));
		g.clearRect(0, 0, 300, 200);
		g.setStroke(new BasicStroke(2));
		g.translate(-120.0, -60.0);
		Color c1 = new Color(0.149f, 0.451f, 0.925f);
		Color c2 = new Color(0.157f, 0.278f, 0.471f);
		Color c3 = new Color(0.227f, 0.4f, 0.671f);
		HexagonFactory factory = new HexagonFactory(30.0);
		drawHexagon(g, c3, factory.buildHexagon(2, 2).getHexagon());
		drawHexagon(g, c2, factory.buildHexagon(3, 2).getHexagon());
		drawHexagon(g, c1, factory.buildHexagon(3, 1).getHexagon());
		drawHexagon(g, c2, factory.buildHexagon(4, 1).getHexagon());
		drawHexagon(g, c3, factory.buildHexagon(5, 1).getHexagon());
		drawHexagon(g, c2, factory.buildHexagon(6, 2).getHexagon());
		drawHexagon(g, c1, factory.buildHexagon(7, 1).getHexagon());
		drawHexagon(g, c3, factory.buildHexagon(7, 2).getHexagon());
		g.dispose();
	}

	/*
	 * Constructor
	 */
	protected StandardDialog() {
		this(standardDimensions, standardBackgroundColor);
	}

	protected StandardDialog(Dimension dimension) {
		this(dimension, standardBackgroundColor);
	}

	protected StandardDialog(Dimension dimension, Color color) {
		// Standard setup for dialogs
		backgroundColor = color;
		dimensions = dimension;
		setPreferredSize(dimensions);
		setSize(dimensions);
		setResizable(false);
		setUndecorated(true);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(new Point((int) ((screen.width - dimensions.width) / 2.0),
				(int) ((screen.height - dimensions.height) / 2.0)));

		// Initialise buttons list
		buttons = new ArrayList<Button>();
		setupBackground();
	}

	/*
	 * Instance methods
	 */
	private void setupBackground() {
		background = new BufferedImage(dimensions.width, dimensions.height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = background.createGraphics();
		g.setBackground(backgroundColor);
		g.clearRect(0, 0, dimensions.width, dimensions.height);
		Polygon p = new Polygon();
		p.addPoint(5, 5);
		p.addPoint(dimensions.width - 5, 5);
		p.addPoint(dimensions.width - 5, dimensions.height - 5);
		p.addPoint(5, dimensions.height - 5);
		g.setStroke(new BasicStroke(3));
		g.setColor(Color.WHITE);
		g.drawPolygon(p);
		g.drawImage(standardBackground, 0, 0, null);
		g.dispose();
	}

}
