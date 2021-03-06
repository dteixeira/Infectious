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
import edu.infectious.script.country.CountryClimateTemperature;
import edu.infectious.script.trait.TraitType;

public class TemperaturePanel extends JPanel {

	/*
	 * Constants
	 */
	private static final Color	BUTTON_BUY_COLOR	= new Color(0.149f, 0.451f, 0.925f);
	private static final Color	BUTTON_COLOR		= new Color(0.302f, 0.302f, 0.302f);
	private static final int	BUTTON_HEIGHT		= 30;
	private static final Color	BUTTON_HOVER_COLOR	= new Color(0.478f, 0.478f, 0.478f);
	private static final Color	BUTTON_LINE_COLOR	= Color.WHITE;
	private static final int	BUTTON_LINE_WIDTH	= 3;
	private static final int	BUTTON_WIDTH		= 260;
	private static final long	serialVersionUID	= 1L;

	/*
	 * Instance fields
	 */
	private boolean				buttonHover			= false;
	private Button				coldButton;
	private Button				hotButton;
	private Button				temperateButton;

	/*
	 * Constructor
	 */
	public TemperaturePanel() {
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
		g2d.drawString("Temperature resistances", 15, 30);

		// Draw buttons
		g2d.setFont(new Font("sans-serif", Font.PLAIN, 16));
		hotButton.paintButton(g2d);
		coldButton.paintButton(g2d);
		temperateButton.paintButton(g2d);

		g2d.dispose();
	}

	private void handleCold() {
		if (!VirusStatistics.isMaxTemperatureLevel(CountryClimateTemperature.COLD)) {
			switch (VirusStatistics.getTemperatureLevel(CountryClimateTemperature.COLD)) {
			case 0:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel1Cost())) {
					VirusStatistics.incrementTemperatureBonus(CountryClimateTemperature.COLD);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel1Cost());
					coldButton.setText("Cold level 2 (" + VirusStatistics.getLevel2Cost() + ")");
				}
				break;
			case 1:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel2Cost())) {
					VirusStatistics.incrementTemperatureBonus(CountryClimateTemperature.COLD);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel2Cost());
					coldButton.setText("Cold level 3 (" + VirusStatistics.getLevel3Cost() + ")");
				}
				break;
			case 2:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel3Cost())) {
					VirusStatistics.incrementTemperatureBonus(CountryClimateTemperature.COLD);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel3Cost());
					coldButton.setText("Cold level 4 (" + VirusStatistics.getLevel4Cost() + ")");
				}
				break;
			case 3:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel4Cost())) {
					VirusStatistics.incrementTemperatureBonus(CountryClimateTemperature.COLD);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel4Cost());
					coldButton.setText("Cold max level");
					coldButton.setFillColor(BUTTON_BUY_COLOR);
				}
				break;
			default:
				break;
			}
		}
	}

	private void handleHot() {
		if (!VirusStatistics.isMaxTemperatureLevel(CountryClimateTemperature.HOT)) {
			switch (VirusStatistics.getTemperatureLevel(CountryClimateTemperature.HOT)) {
			case 0:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel1Cost())) {
					VirusStatistics.incrementTemperatureBonus(CountryClimateTemperature.HOT);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel1Cost());
					hotButton.setText("Hot level 2 (" + VirusStatistics.getLevel2Cost() + ")");
				}
				break;
			case 1:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel2Cost())) {
					VirusStatistics.incrementTemperatureBonus(CountryClimateTemperature.HOT);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel2Cost());
					hotButton.setText("Hot level 3 (" + VirusStatistics.getLevel3Cost() + ")");
				}
				break;
			case 2:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel3Cost())) {
					VirusStatistics.incrementTemperatureBonus(CountryClimateTemperature.HOT);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel3Cost());
					hotButton.setText("Hot level 4 (" + VirusStatistics.getLevel4Cost() + ")");
				}
				break;
			case 3:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel4Cost())) {
					VirusStatistics.incrementTemperatureBonus(CountryClimateTemperature.HOT);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel4Cost());
					hotButton.setText("Hot max level");
					hotButton.setFillColor(BUTTON_BUY_COLOR);
				}
				break;
			default:
				break;
			}
		}
	}

	private void handleTemperate() {
		if (!VirusStatistics.isMaxTemperatureLevel(CountryClimateTemperature.TEMPERATE)) {
			switch (VirusStatistics.getTemperatureLevel(CountryClimateTemperature.TEMPERATE)) {
			case 0:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel1Cost())) {
					VirusStatistics.incrementTemperatureBonus(CountryClimateTemperature.TEMPERATE);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel1Cost());
					temperateButton.setText("Temperate level 2 (" + VirusStatistics.getLevel2Cost()
							+ ")");
				}
				break;
			case 1:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel2Cost())) {
					VirusStatistics.incrementTemperatureBonus(CountryClimateTemperature.TEMPERATE);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel2Cost());
					temperateButton.setText("Temperate level 3 (" + VirusStatistics.getLevel3Cost()
							+ ")");
				}
				break;
			case 2:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel3Cost())) {
					VirusStatistics.incrementTemperatureBonus(CountryClimateTemperature.TEMPERATE);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel3Cost());
					temperateButton.setText("Temperate level 4 (" + VirusStatistics.getLevel4Cost()
							+ ")");
				}
				break;
			case 3:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel4Cost())) {
					VirusStatistics.incrementTemperatureBonus(CountryClimateTemperature.TEMPERATE);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel4Cost());
					temperateButton.setText("Temperate max level");
					temperateButton.setFillColor(BUTTON_BUY_COLOR);
				}
				break;
			default:
				break;
			}
		}
	}

	private Color makeButtonColor(CountryClimateTemperature temperature) {
		if (VirusStatistics.isMaxTemperatureLevel(temperature))
			return BUTTON_BUY_COLOR;
		else
			return BUTTON_COLOR;
	}

	private String makeButtonLabel(CountryClimateTemperature temperature) {
		String name = temperature.toString().substring(0, 1)
				+ temperature.toString().toLowerCase().substring(1);
		switch (VirusStatistics.getTemperatureLevel(temperature)) {
		case 0:
			return name + " level 1 (" + VirusStatistics.getLevel1Cost() + ")";
		case 1:
			return name + " level 2 (" + VirusStatistics.getLevel2Cost() + ")";
		case 2:
			return name + " level 3 (" + VirusStatistics.getLevel3Cost() + ")";
		case 3:
			return name + " level 4 (" + VirusStatistics.getLevel4Cost() + ")";
		case 4:
			return name + " max level";
		default:
			return "";
		}
	}

	private void setupButtons() {
		hotButton = new Button(20, 50, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_LINE_WIDTH,
				makeButtonLabel(CountryClimateTemperature.HOT),
				makeButtonColor(CountryClimateTemperature.HOT), BUTTON_LINE_COLOR);
		coldButton = new Button(20, 90, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_LINE_WIDTH,
				makeButtonLabel(CountryClimateTemperature.COLD),
				makeButtonColor(CountryClimateTemperature.COLD), BUTTON_LINE_COLOR);
		temperateButton = new Button(20, 130, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_LINE_WIDTH,
				makeButtonLabel(CountryClimateTemperature.TEMPERATE),
				makeButtonColor(CountryClimateTemperature.TEMPERATE), BUTTON_LINE_COLOR);
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
				if (hotButton.isHit(p)) {
					if (!VirusStatistics.isMaxTemperatureLevel(CountryClimateTemperature.HOT)) {
						hotButton.setFillColor(BUTTON_HOVER_COLOR);
						hovered = true;
					}
				} else if (coldButton.isHit(p)) {
					if (!VirusStatistics.isMaxTemperatureLevel(CountryClimateTemperature.COLD)) {
						coldButton.setFillColor(BUTTON_HOVER_COLOR);
						hovered = true;
					}
				} else if (temperateButton.isHit(p)) {
					if (!VirusStatistics.isMaxTemperatureLevel(CountryClimateTemperature.TEMPERATE)) {
						temperateButton.setFillColor(BUTTON_HOVER_COLOR);
						hovered = true;
					}
				}
				if (hovered && !buttonHover) {
					SoundManager.playSoundEffect(SoundEffect.BUTTON_HOVER);
					buttonHover = true;
				} else if (!hovered) {
					buttonHover = false;
				}
				TemperaturePanel.this.getParent().getParent().repaint();
			}

			private void resetButtonColor() {
				if (VirusStatistics.isMaxTemperatureLevel(CountryClimateTemperature.HOT))
					hotButton.setFillColor(BUTTON_BUY_COLOR);
				else
					hotButton.setFillColor(BUTTON_COLOR);
				if (VirusStatistics.isMaxTemperatureLevel(CountryClimateTemperature.COLD))
					coldButton.setFillColor(BUTTON_BUY_COLOR);
				else
					coldButton.setFillColor(BUTTON_COLOR);
				if (VirusStatistics.isMaxTemperatureLevel(CountryClimateTemperature.TEMPERATE))
					temperateButton.setFillColor(BUTTON_BUY_COLOR);
				else
					temperateButton.setFillColor(BUTTON_COLOR);
			}
		});
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// Return unless virus player
				if (Game.getPlayer().getPlayerType() == TraitType.CURE)
					return;
				Point p = e.getPoint();
				if (hotButton.isHit(p)) {
					handleHot();
				} else if (coldButton.isHit(p)) {
					handleCold();
				} else if (temperateButton.isHit(p)) {
					handleTemperate();
				}
				TemperaturePanel.this.getParent().getParent().repaint();
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
