package edu.infectious.gui.containers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import edu.infectious.gui.utilities.Button;

public class TransmissionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final Color BUTTON_COLOR = new Color(0.149f, 0.451f, 0.925f);
	private static final Color BUTTON_LINE_COLOR = Color.WHITE;
	private static final int BUTTON_LINE_WIDTH = 3;
	private static final int BUTTON_WIDTH = 260;
	private static final int BUTTON_HEIGHT = 35;
	
	private Button airButton;
	private Button waterButton;
	private Button plagueButton;
	private Button livestockButton;
	private Color backgroundColor;
	
	public TransmissionPanel(Color backgroundColor) {
		super(true);
		this.backgroundColor = backgroundColor;
		setupPanel();
		setupButtons();
	}
	
	private void setupButtons() {
		airButton = new Button(20, 50, BUTTON_WIDTH, BUTTON_HEIGHT,
				BUTTON_LINE_WIDTH, "Buy", BUTTON_COLOR, BUTTON_LINE_COLOR);
		waterButton = new Button(20, 95, BUTTON_WIDTH, BUTTON_HEIGHT,
				BUTTON_LINE_WIDTH, "Buy", BUTTON_COLOR, BUTTON_LINE_COLOR);
		plagueButton = new Button(20, 140, BUTTON_WIDTH, BUTTON_HEIGHT,
				BUTTON_LINE_WIDTH, "Buy", BUTTON_COLOR, BUTTON_LINE_COLOR);
		livestockButton = new Button(20, 185, BUTTON_WIDTH, BUTTON_HEIGHT,
				BUTTON_LINE_WIDTH, "Buy", BUTTON_COLOR, BUTTON_LINE_COLOR);
	}

	private void setupPanel() {
		setBackground(backgroundColor);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(0f, 0f, 0f, 0f));
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Color.WHITE);
		g2d.drawRect(5, 5, getWidth() - 10, getHeight() - 10);
		
		// Draw title
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("sans-serif", Font.BOLD, 20));
		g2d.drawString("Transmission bonus", 15, 30);
		
		// Draw buttons
		g2d.setFont(new Font("sans-serif", Font.PLAIN, 16));
		airButton.paintButton(g2d);
		waterButton.paintButton(g2d);
		plagueButton.paintButton(g2d);
		livestockButton.paintButton(g2d);

		g2d.dispose();
	}

}
