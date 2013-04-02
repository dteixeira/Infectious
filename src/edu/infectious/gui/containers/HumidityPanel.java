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
import edu.infectious.script.country.CountryClimateHumidity;
import edu.infectious.script.trait.TraitType;

public class HumidityPanel extends JPanel {

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
	private Button				aridButton;
	private boolean				buttonHover			= false;
	private Button				mediterraneanButton;
	private Button				tropicalButton;

	/*
	 * Constructor
	 */
	public HumidityPanel() {
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
		g2d.drawString("Humidity resistances", 15, 30);

		// Draw buttons
		g2d.setFont(new Font("sans-serif", Font.PLAIN, 16));
		aridButton.paintButton(g2d);
		tropicalButton.paintButton(g2d);
		mediterraneanButton.paintButton(g2d);

		g2d.dispose();
	}

	private void handleArid() {
		if (!VirusStatistics.isMaxHumidityLevel(CountryClimateHumidity.ARID)) {
			switch (VirusStatistics.getHumidityLevel(CountryClimateHumidity.ARID)) {
			case 0:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel1Cost())) {
					VirusStatistics.incrementHumidityBonus(CountryClimateHumidity.ARID);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel1Cost());
					aridButton.setText("Arid level 2 (" + VirusStatistics.getLevel2Cost() + ")");
				}
				break;
			case 1:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel2Cost())) {
					VirusStatistics.incrementHumidityBonus(CountryClimateHumidity.ARID);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel2Cost());
					aridButton.setText("Arid level 3 (" + VirusStatistics.getLevel3Cost() + ")");
				}
				break;
			case 2:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel3Cost())) {
					VirusStatistics.incrementHumidityBonus(CountryClimateHumidity.ARID);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel3Cost());
					aridButton.setText("Arid level 4 (" + VirusStatistics.getLevel4Cost() + ")");
				}
				break;
			case 3:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel4Cost())) {
					VirusStatistics.incrementHumidityBonus(CountryClimateHumidity.ARID);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel4Cost());
					aridButton.setText("Arid max level");
					aridButton.setFillColor(BUTTON_BUY_COLOR);
				}
				break;
			default:
				break;
			}
		}
	}

	private void handleMediterranean() {
		if (!VirusStatistics.isMaxHumidityLevel(CountryClimateHumidity.MEDITERRANEAN)) {
			switch (VirusStatistics.getHumidityLevel(CountryClimateHumidity.MEDITERRANEAN)) {
			case 0:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel1Cost())) {
					VirusStatistics.incrementHumidityBonus(CountryClimateHumidity.MEDITERRANEAN);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel1Cost());
					mediterraneanButton.setText("Mediterranean level 2 ("
							+ VirusStatistics.getLevel2Cost() + ")");
				}
				break;
			case 1:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel2Cost())) {
					VirusStatistics.incrementHumidityBonus(CountryClimateHumidity.MEDITERRANEAN);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel2Cost());
					mediterraneanButton.setText("Mediterranean level 3 ("
							+ VirusStatistics.getLevel3Cost() + ")");
				}
				break;
			case 2:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel3Cost())) {
					VirusStatistics.incrementHumidityBonus(CountryClimateHumidity.MEDITERRANEAN);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel3Cost());
					mediterraneanButton.setText("Mediterranean level 4 ("
							+ VirusStatistics.getLevel4Cost() + ")");
				}
				break;
			case 3:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel4Cost())) {
					VirusStatistics.incrementHumidityBonus(CountryClimateHumidity.MEDITERRANEAN);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel4Cost());
					mediterraneanButton.setText("Mediterranean max level");
					mediterraneanButton.setFillColor(BUTTON_BUY_COLOR);
				}
				break;
			default:
				break;
			}
		}
	}

	private void handleTropical() {
		if (!VirusStatistics.isMaxHumidityLevel(CountryClimateHumidity.TROPICAL)) {
			switch (VirusStatistics.getHumidityLevel(CountryClimateHumidity.TROPICAL)) {
			case 0:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel1Cost())) {
					VirusStatistics.incrementHumidityBonus(CountryClimateHumidity.TROPICAL);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel1Cost());
					tropicalButton.setText("Tropical level 2 (" + VirusStatistics.getLevel2Cost()
							+ ")");
				}
				break;
			case 1:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel2Cost())) {
					VirusStatistics.incrementHumidityBonus(CountryClimateHumidity.TROPICAL);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel2Cost());
					tropicalButton.setText("Tropical level 3 (" + VirusStatistics.getLevel3Cost()
							+ ")");
				}
				break;
			case 2:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel3Cost())) {
					VirusStatistics.incrementHumidityBonus(CountryClimateHumidity.TROPICAL);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel3Cost());
					tropicalButton.setText("Tropical level 4 (" + VirusStatistics.getLevel4Cost()
							+ ")");
				}
				break;
			case 3:
				if (Game.getPlayer().isAffordable(VirusStatistics.getLevel4Cost())) {
					VirusStatistics.incrementHumidityBonus(CountryClimateHumidity.TROPICAL);
					Game.getPlayer().spendPoints(VirusStatistics.getLevel4Cost());
					tropicalButton.setText("Tropical max level");
					tropicalButton.setFillColor(BUTTON_BUY_COLOR);
				}
				break;
			default:
				break;
			}
		}
	}

	private Color makeButtonColor(CountryClimateHumidity humidity) {
		if (VirusStatistics.isMaxHumidityLevel(humidity))
			return BUTTON_BUY_COLOR;
		else
			return BUTTON_COLOR;
	}

	private String makeButtonLabel(CountryClimateHumidity humidity) {
		String name = humidity.toString().substring(0, 1)
				+ humidity.toString().toLowerCase().substring(1);
		switch (VirusStatistics.getHumidityLevel(humidity)) {
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
		aridButton = new Button(20, 50, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_LINE_WIDTH,
				makeButtonLabel(CountryClimateHumidity.ARID),
				makeButtonColor(CountryClimateHumidity.ARID), BUTTON_LINE_COLOR);
		tropicalButton = new Button(20, 90, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_LINE_WIDTH,
				makeButtonLabel(CountryClimateHumidity.TROPICAL),
				makeButtonColor(CountryClimateHumidity.TROPICAL), BUTTON_LINE_COLOR);
		mediterraneanButton = new Button(20, 130, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_LINE_WIDTH,
				makeButtonLabel(CountryClimateHumidity.MEDITERRANEAN),
				makeButtonColor(CountryClimateHumidity.MEDITERRANEAN), BUTTON_LINE_COLOR);
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
				if (aridButton.isHit(p)) {
					if (!VirusStatistics.isMaxHumidityLevel(CountryClimateHumidity.ARID)) {
						aridButton.setFillColor(BUTTON_HOVER_COLOR);
						hovered = true;
					}
				} else if (tropicalButton.isHit(p)) {
					if (!VirusStatistics.isMaxHumidityLevel(CountryClimateHumidity.TROPICAL)) {
						tropicalButton.setFillColor(BUTTON_HOVER_COLOR);
						hovered = true;
					}
				} else if (mediterraneanButton.isHit(p)) {
					if (!VirusStatistics.isMaxHumidityLevel(CountryClimateHumidity.MEDITERRANEAN)) {
						mediterraneanButton.setFillColor(BUTTON_HOVER_COLOR);
						hovered = true;
					}
				}
				if (hovered && !buttonHover) {
					SoundManager.playSoundEffect(SoundEffect.BUTTON_HOVER);
					buttonHover = true;
				} else if (!hovered) {
					buttonHover = false;
				}
				HumidityPanel.this.getParent().getParent().repaint();
			}

			private void resetButtonColor() {
				if (VirusStatistics.isMaxHumidityLevel(CountryClimateHumidity.ARID))
					aridButton.setFillColor(BUTTON_BUY_COLOR);
				else
					aridButton.setFillColor(BUTTON_COLOR);
				if (VirusStatistics.isMaxHumidityLevel(CountryClimateHumidity.TROPICAL))
					tropicalButton.setFillColor(BUTTON_BUY_COLOR);
				else
					tropicalButton.setFillColor(BUTTON_COLOR);
				if (VirusStatistics.isMaxHumidityLevel(CountryClimateHumidity.MEDITERRANEAN))
					mediterraneanButton.setFillColor(BUTTON_BUY_COLOR);
				else
					mediterraneanButton.setFillColor(BUTTON_COLOR);
			}
		});
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// Return unless virus player
				if (Game.getPlayer().getPlayerType() == TraitType.CURE)
					return;
				Point p = e.getPoint();
				if (aridButton.isHit(p)) {
					handleArid();
				} else if (tropicalButton.isHit(p)) {
					handleTropical();
				} else if (mediterraneanButton.isHit(p)) {
					handleMediterranean();
				}
				HumidityPanel.this.getParent().getParent().repaint();
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
