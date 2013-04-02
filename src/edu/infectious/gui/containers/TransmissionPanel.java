package edu.infectious.gui.containers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import edu.infectious.gui.utilities.Button;
import edu.infectious.gui.utilities.SoundEffect;
import edu.infectious.gui.utilities.SoundManager;
import edu.infectious.logic.Game;
import edu.infectious.logic.VirusStatistics;
import edu.infectious.script.trait.TraitType;

public class TransmissionPanel extends JPanel {

	/*
	 * Constants
	 */
	private static final Color	BUTTON_BUY_COLOR	= new Color(0.149f, 0.451f, 0.925f);
	private static final Color	BUTTON_COLOR		= new Color(0.302f, 0.302f, 0.302f);
	private static final int	BUTTON_HEIGHT		= 35;
	private static final Color	BUTTON_HOVER_COLOR	= new Color(0.478f, 0.478f, 0.478f);
	private static final Color	BUTTON_LINE_COLOR	= Color.WHITE;
	private static final int	BUTTON_LINE_WIDTH	= 3;
	private static final int	BUTTON_WIDTH		= 260;
	private static final long	serialVersionUID	= 1L;

	/*
	 * Instance fields
	 */
	private Button				airButton;
	private boolean				buttonHover			= false;
	private Button				livestockButton;
	private Button				plagueButton;
	private Button				waterButton;

	/*
	 * Constructor
	 */
	public TransmissionPanel() {
		super(true);
		setupPanel();
		setupButtons();
		setupListeners();
	}

	/*
	 * Instance methods
	 */
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

	private void handleAir() {
		if (!VirusStatistics.isAirTransmission()) {
			if (Game.getPlayer().isAffordable(VirusStatistics.getAirTransmissionCost())) {
				VirusStatistics.setAirTransmission(true);
				Game.getPlayer().spendPoints(VirusStatistics.getAirTransmissionCost());
				airButton.setText("Air");
				airButton.setFillColor(BUTTON_BUY_COLOR);
			}
		}
	}

	private void handleLivestock() {
		if (!VirusStatistics.isLivestockTransmission()) {
			if (Game.getPlayer().isAffordable(VirusStatistics.getLivestockTransmissionCost())) {
				VirusStatistics.setLivestockTransmission(true);
				Game.getPlayer().spendPoints(VirusStatistics.getLivestockTransmissionCost());
				livestockButton.setText("Livestock");
				livestockButton.setFillColor(BUTTON_BUY_COLOR);
			}
		}
	}

	private void handlePlague() {
		if (!VirusStatistics.isPlagueTransmission()) {
			if (Game.getPlayer().isAffordable(VirusStatistics.getPlagueTransmissionCost())) {
				VirusStatistics.setPlagueTransmission(true);
				Game.getPlayer().spendPoints(VirusStatistics.getPlagueTransmissionCost());
				plagueButton.setText("Plague");
				plagueButton.setFillColor(BUTTON_BUY_COLOR);
			}
		}
	}

	private void handleWater() {
		if (!VirusStatistics.isWaterTransmission()) {
			if (Game.getPlayer().isAffordable(VirusStatistics.getWaterTransmissionCost())) {
				VirusStatistics.setWaterTransmission(true);
				Game.getPlayer().spendPoints(VirusStatistics.getWaterTransmissionCost());
				waterButton.setText("Water");
				waterButton.setFillColor(BUTTON_BUY_COLOR);
			}
		}
	}

	private void setupButtons() {
		// Air button
		String airText = VirusStatistics.isAirTransmission() ? "Air" : "Air ("
				+ VirusStatistics.getAirTransmissionCost() + ")";
		Color airColor = VirusStatistics.isAirTransmission() ? BUTTON_BUY_COLOR : BUTTON_COLOR;
		airButton = new Button(20, 50, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_LINE_WIDTH, airText,
				airColor, BUTTON_LINE_COLOR);

		// Water button
		String waterText = VirusStatistics.isWaterTransmission() ? "Water" : "Water ("
				+ VirusStatistics.getWaterTransmissionCost() + ")";
		Color waterColor = VirusStatistics.isWaterTransmission() ? BUTTON_BUY_COLOR : BUTTON_COLOR;
		waterButton = new Button(20, 95, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_LINE_WIDTH, waterText,
				waterColor, BUTTON_LINE_COLOR);

		// Plague button
		String plagueText = VirusStatistics.isPlagueTransmission() ? "Plague" : "Plague ("
				+ VirusStatistics.getPlagueTransmissionCost() + ")";
		Color plagueColor = VirusStatistics.isPlagueTransmission() ? BUTTON_BUY_COLOR
				: BUTTON_COLOR;
		plagueButton = new Button(20, 140, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_LINE_WIDTH,
				plagueText, plagueColor, BUTTON_LINE_COLOR);

		// Livestock button
		String livestockText = VirusStatistics.isLivestockTransmission() ? "Livestock"
				: "Livestock (" + VirusStatistics.getPlagueTransmissionCost() + ")";
		Color livestockColor = VirusStatistics.isLivestockTransmission() ? BUTTON_BUY_COLOR
				: BUTTON_COLOR;
		livestockButton = new Button(20, 185, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_LINE_WIDTH,
				livestockText, livestockColor, BUTTON_LINE_COLOR);
	}

	private void setupListeners() {
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {}

			@Override
			public void mouseMoved(MouseEvent e) {
				// Return unless virus player
				if (Game.getPlayer().getPlayerType() == TraitType.CURE)
					return;
				resetButtonColor();
				boolean hovered = false;
				Point p = e.getPoint();
				if (airButton.isHit(p)) {
					if (!VirusStatistics.isAirTransmission()) {
						airButton.setFillColor(BUTTON_HOVER_COLOR);
						hovered = true;
					}
				} else if (waterButton.isHit(p)) {
					if (!VirusStatistics.isWaterTransmission()) {
						waterButton.setFillColor(BUTTON_HOVER_COLOR);
						hovered = true;
					}
				} else if (plagueButton.isHit(p)) {
					if (!VirusStatistics.isPlagueTransmission()) {
						plagueButton.setFillColor(BUTTON_HOVER_COLOR);
						hovered = true;
					}
				} else if (livestockButton.isHit(p)) {
					if (!VirusStatistics.isLivestockTransmission()) {
						livestockButton.setFillColor(BUTTON_HOVER_COLOR);
						hovered = true;
					}
				}
				if (hovered && !buttonHover) {
					SoundManager.playSoundEffect(SoundEffect.BUTTON_HOVER);
					buttonHover = true;
				} else if (!hovered) {
					buttonHover = false;
				}
				TransmissionPanel.this.getParent().getParent().repaint();
			}

			private void resetButtonColor() {
				if (VirusStatistics.isAirTransmission())
					airButton.setFillColor(BUTTON_BUY_COLOR);
				else
					airButton.setFillColor(BUTTON_COLOR);
				if (VirusStatistics.isWaterTransmission())
					waterButton.setFillColor(BUTTON_BUY_COLOR);
				else
					waterButton.setFillColor(BUTTON_COLOR);
				if (VirusStatistics.isPlagueTransmission())
					plagueButton.setFillColor(BUTTON_BUY_COLOR);
				else
					plagueButton.setFillColor(BUTTON_COLOR);
				if (VirusStatistics.isLivestockTransmission())
					livestockButton.setFillColor(BUTTON_BUY_COLOR);
				else
					livestockButton.setFillColor(BUTTON_COLOR);
			}
		});
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// Return unless virus player
				if (Game.getPlayer().getPlayerType() == TraitType.CURE)
					return;
				Point p = e.getPoint();
				if (airButton.isHit(p)) {
					handleAir();
				} else if (waterButton.isHit(p)) {
					handleWater();
				} else if (plagueButton.isHit(p)) {
					handlePlague();
				} else if (livestockButton.isHit(p)) {
					handleLivestock();
				}
				TransmissionPanel.this.getParent().getParent().repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}
		});
	}

	private void setupPanel() {
		setBackground(new Color(0f, 0f, 0f, 0f));
	}

}
