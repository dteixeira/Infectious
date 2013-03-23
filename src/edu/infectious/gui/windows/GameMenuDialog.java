package edu.infectious.gui.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import edu.infectious.gui.utilities.Button;

public class GameMenuDialog extends StandardDialog {

	private static final long serialVersionUID = 1L;
	private static final int MENU_WIDTH = 800;
	private static final int MENU_HEIGHT = 150;
	private static final int BUTTON_WIDTH = 180;
	private static final int BUTTON_HEIGHT = 60;
	private static final Color BUTTON_COLOR = new Color(0.149f, 0.451f, 0.925f);
	private static final Color BUTTON_LINE_COLOR = Color.WHITE;
	private static final Color BUTTON_HOVER_COLOR = new Color(0.349f, 0.573f, 0.925f);
	private static final int BUTTON_LINE_WIDTH = 3;
	
	private JPanel panel = null;
	private Button closeButton = null;
	private Button quitButton = null;
	private Button endTurnButton = null;
	private Button traitsButton = null;
	
	public GameMenuDialog() {
		super(new Dimension(MENU_WIDTH, MENU_HEIGHT));
		setupDialog();
		setupPanel();
		setupButtons();
		setupListeners();
	}
	
	private void setupButtons() {
		// TODO
	}
	
	private void setupListeners() {
		// TODO
	}
	
	private void setupDialog() {
		setOpacity(0.9f);
	}
	
	private void setupPanel() {
		panel = new JPanel(true) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(background, 0, 0, null);
				g.dispose();
			}
		};
		getContentPane().add(panel);
	}

}
