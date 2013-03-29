package edu.infectious.gui.windows;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainFrame() {
		super();
		setupFrame();
		setupMapPanel();
		setupCountry();
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
		MapPanel.getInstance().refreshTurn();
	}

}
