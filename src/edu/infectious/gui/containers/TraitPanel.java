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
import java.util.ListIterator;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import edu.infectious.gui.utilities.Button;
import edu.infectious.gui.utilities.SoundEffect;
import edu.infectious.gui.utilities.SoundManager;
import edu.infectious.gui.windows.TraitsDialog;
import edu.infectious.logic.Game;
import edu.infectious.script.trait.Trait;

public class TraitPanel extends JPanel {

	/*
	 * Constants
	 */
	private static final Color	BUTTON_COLOR		= new Color(0.149f, 0.451f, 0.925f);
	private static final int	BUTTON_HEIGHT		= 60;
	private static final Color	BUTTON_HOVER_COLOR	= new Color(0.349f, 0.573f, 0.925f);
	private static final Color	BUTTON_LINE_COLOR	= Color.WHITE;
	private static final int	BUTTON_LINE_WIDTH	= 3;
	private static final int	BUTTON_WIDTH		= 110;
	private static final long	serialVersionUID	= 1L;

	private boolean				buttonHover			= false;
	private Button				buyButton;
	private Button				closeButton;
	private Trait				currentTrait;
	private JTextArea			descriptionArea;
	private Button				nextButton;
	private Button				previousButton;

	/*
	 * Constructor
	 */
	public TraitPanel() {
		super(true);
		setupPanel();
		setupTextArea();
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
		g2d.drawRect(5, 5, getWidth() - 10, 100);
		g2d.drawRect(5, 115, getWidth() - 10, 390);
		g2d.drawRect(5, 515, getWidth() - 10, 80);

		// Draw upper statistics
		g2d.setFont(new Font("sans-serif", Font.PLAIN, 20));
		g2d.drawString("Available points: " + Game.getPlayer().getPlayerPoints(), 20, 35);
		g2d.drawString("Number of turns: " + Game.getTurnNumber(), 20, 65);
		g2d.drawString("Turns to cure found: " + Game.getTurnsToCure(), 20, 95);

		// Draw buttons
		g2d.setFont(new Font("sans-serif", Font.PLAIN, 20));
		if (currentTrait.isInEffect())
			buyButton.setText("Sell (" + currentTrait.getDeactivationCost() + ")");
		else
			buyButton.setText("Buy (" + currentTrait.getActivationCost() + ")");
		buyButton.paintButton(g2d);
		previousButton.paintButton(g2d);
		nextButton.paintButton(g2d);
		closeButton.paintButton(g2d);

		// Draw trait info
		g2d.setFont(new Font("sans-serif", Font.BOLD, 40));
		g2d.drawString(currentTrait.getName(), 15, 160);
		g2d.setFont(new Font("sans-serif", Font.PLAIN, 25));
		g2d.drawString(currentTrait.getType().toString(), 15, 190);

		// Draw text box with description
		descriptionArea.setText(currentTrait.getDescription());
		super.paintComponents(g);

		g2d.dispose();
	}

	private void handleBuy() {
		if (currentTrait.isInEffect()) {
			if (Game.getPlayer().isAffordable(currentTrait.getDeactivationCost())) {
				Game.getPlayer().spendPoints(currentTrait.getDeactivationCost());
				currentTrait.setInEffect(false);
				currentTrait.removeEffect();
			}
		} else {
			if (Game.getPlayer().isAffordable(currentTrait.getActivationCost())) {
				Game.getPlayer().spendPoints(currentTrait.getActivationCost());
				currentTrait.setInEffect(true);
				currentTrait.applyEffect();
			}
		}
		TraitPanel.this.getParent().getParent().repaint();
	}

	private void handleClose() {
		((TraitsDialog) (TraitPanel.this.getParent().getParent().getParent().getParent()
				.getParent())).dispose();
	}

	private void handleNext() {
		int index = Trait.getTraitList().indexOf(currentTrait);
		ListIterator<Trait> iterator = Trait.getTraitList().listIterator(index + 1);
		boolean found = false;
		while (iterator.hasNext()) {
			Trait t = iterator.next();
			if (t.getType() == Game.getPlayer().getPlayerType()) {
				found = true;
				currentTrait = t;
				break;
			}
		}
		if (!found) {
			iterator = Trait.getTraitList().listIterator(0);
			while (iterator.hasNext()) {
				Trait t = iterator.next();
				if (t.getType() == Game.getPlayer().getPlayerType()) {
					currentTrait = t;
					break;
				}
			}
		}
		TraitPanel.this.getParent().getParent().repaint();
	}

	private void handlePrevious() {
		int index = Trait.getTraitList().indexOf(currentTrait);
		ListIterator<Trait> iterator = Trait.getTraitList().listIterator(index);
		boolean found = false;
		while (iterator.hasPrevious()) {
			Trait t = iterator.previous();
			if (t.getType() == Game.getPlayer().getPlayerType()) {
				found = true;
				currentTrait = t;
				break;
			}
		}
		if (!found) {
			iterator = Trait.getTraitList().listIterator(Trait.getTraitList().size());
			while (iterator.hasPrevious()) {
				Trait t = iterator.previous();
				if (t.getType() == Game.getPlayer().getPlayerType()) {
					currentTrait = t;
					break;
				}
			}
		}
		TraitPanel.this.getParent().getParent().repaint();
	}

	private void setupButtons() {
		buyButton = new Button(15, 525, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_LINE_WIDTH, "",
				BUTTON_COLOR, BUTTON_LINE_COLOR);
		previousButton = new Button(25 + BUTTON_WIDTH, 525, BUTTON_WIDTH, BUTTON_HEIGHT,
				BUTTON_LINE_WIDTH, "< Previous", BUTTON_COLOR, BUTTON_LINE_COLOR);
		nextButton = new Button(35 + BUTTON_WIDTH * 2, 525, BUTTON_WIDTH, BUTTON_HEIGHT,
				BUTTON_LINE_WIDTH, "Next >", BUTTON_COLOR, BUTTON_LINE_COLOR);
		closeButton = new Button(45 + BUTTON_WIDTH * 3, 525, BUTTON_WIDTH, BUTTON_HEIGHT,
				BUTTON_LINE_WIDTH, "Close", BUTTON_COLOR, BUTTON_LINE_COLOR);
	}

	private void setupListeners() {
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {}

			@Override
			public void mouseMoved(MouseEvent e) {
				nextButton.setFillColor(BUTTON_COLOR);
				previousButton.setFillColor(BUTTON_COLOR);
				buyButton.setFillColor(BUTTON_COLOR);
				closeButton.setFillColor(BUTTON_COLOR);
				boolean hovered = false;
				Point p = e.getPoint();
				if (nextButton.isHit(p)) {
					nextButton.setFillColor(BUTTON_HOVER_COLOR);
					hovered = true;
				} else if (previousButton.isHit(p)) {
					previousButton.setFillColor(BUTTON_HOVER_COLOR);
					hovered = true;
				} else if (buyButton.isHit(p)) {
					buyButton.setFillColor(BUTTON_HOVER_COLOR);
					hovered = true;
				} else if (closeButton.isHit(p)) {
					closeButton.setFillColor(BUTTON_HOVER_COLOR);
					hovered = true;
				}
				if (hovered && !buttonHover) {
					SoundManager.playSoundEffect(SoundEffect.BUTTON_HOVER);
					buttonHover = true;
				} else if (!hovered) {
					buttonHover = false;
				}
				TraitPanel.this.getParent().getParent().repaint();
			}
		});
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Point p = e.getPoint();
				if (nextButton.isHit(p)) {
					handleNext();
				} else if (previousButton.isHit(p)) {
					handlePrevious();
				} else if (buyButton.isHit(p)) {
					handleBuy();
				} else if (closeButton.isHit(p)) {
					handleClose();
				}
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
		for (Trait t : Trait.getTraitList()) {
			if (t.getType() == Game.getPlayer().getPlayerType()) {
				currentTrait = t;
				break;
			}
		}
	}

	private void setupTextArea() {
		setLayout(null);
		descriptionArea = new JTextArea("");
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);
		descriptionArea.setFont(new Font("sans-serif", Font.PLAIN, 20));
		descriptionArea.setBackground(new Color(0f, 0f, 0f, 0f));
		descriptionArea.setForeground(Color.WHITE);
		descriptionArea.setDisabledTextColor(Color.WHITE);
		descriptionArea.setEditable(false);
		descriptionArea.setEnabled(false);
		descriptionArea.setFocusable(false);
		descriptionArea.setBounds(20, 250, 460, 230);
		add(descriptionArea);
	}

}
