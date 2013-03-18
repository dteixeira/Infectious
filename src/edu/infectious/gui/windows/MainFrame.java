package edu.infectious.gui.windows;

import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import edu.infectious.script.country.Country;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainFrame() {
		super();
		setupFrame();
		setupMapPanel();
		setupCountry();
		StandardDialog.setupDialogs();
	}

	private void setupFrame() {
		setCursor(getToolkit().createCustomCursor(
				new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB),
				new Point(0, 0), "null"));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
	}

	private void setupMapPanel() {
		getContentPane().add(MapPanel.getInstance());
	}
	
	private void setupCountry() {
		Country.initCountry();
		MapPanel.getInstance().refreshTurn();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainFrame().setVisible(true);
			}
		});
	}

}
