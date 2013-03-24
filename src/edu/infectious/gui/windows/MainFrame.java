package edu.infectious.gui.windows;

import javax.swing.JFrame;
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

}
