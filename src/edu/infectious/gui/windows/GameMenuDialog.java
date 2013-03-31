package edu.infectious.gui.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.SwingWorker;

import edu.infectious.gui.listeners.GameMenuDialogListener;
import edu.infectious.gui.utilities.Button;
import edu.infectious.gui.utilities.SoundEffect;
import edu.infectious.gui.utilities.SoundManager;
import edu.infectious.logic.Game;

public class GameMenuDialog extends StandardDialog {

	/*
	 * Constants
	 */
	private static final Color	BUTTON_COLOR		= new Color(0.149f, 0.451f, 0.925f);
	private static final int	BUTTON_HEIGHT		= 50;
	private static final Color	BUTTON_HOVER_COLOR	= new Color(0.349f, 0.573f, 0.925f);
	private static final Color	BUTTON_LINE_COLOR	= Color.WHITE;
	private static final int	BUTTON_LINE_WIDTH	= 3;
	private static final int	BUTTON_WIDTH		= 160;
	private static final int	MENU_HEIGHT			= 150;
	private static final int	MENU_WIDTH			= 650;
	private static final long	serialVersionUID	= 1L;

	/*
	 * Instance fields
	 */
	private Button				closeButton			= null;
	private Button				endTurnButton		= null;
	private boolean				hoverButton			= false;
	private JPanel				panel				= null;
	private Button				quitButton			= null;
	private Button				traitsButton		= null;

	/*
	 * Constructor
	 */
	public GameMenuDialog() {
		super(new Dimension(MENU_WIDTH, MENU_HEIGHT));
		setupDialog();
		setupPanel();
		setupButtons();
		setupListeners();
	}

	/*
	 * Instance methods
	 */
	public void handleClick(MouseEvent e) {
		Point p = e.getPoint();
		if (traitsButton.isHit(p))
			handleTraits();
		else if (endTurnButton.isHit(p))
			handleEndTurn();
		else if (quitButton.isHit(p))
			handleQuit();
		else if (closeButton.isHit(p))
			handleClose();
	}

	public void handleHover(MouseEvent e) {
		Point p = e.getPoint();
		traitsButton.setFillColor(BUTTON_COLOR);
		endTurnButton.setFillColor(BUTTON_COLOR);
		quitButton.setFillColor(BUTTON_COLOR);
		closeButton.setFillColor(BUTTON_COLOR);
		boolean hovering = false;
		if (traitsButton.isHit(p)) {
			traitsButton.setFillColor(BUTTON_HOVER_COLOR);
			hovering = true;
		} else if (endTurnButton.isHit(p)) {
			endTurnButton.setFillColor(BUTTON_HOVER_COLOR);
			hovering = true;
		} else if (quitButton.isHit(p)) {
			quitButton.setFillColor(BUTTON_HOVER_COLOR);
			hovering = true;
		} else if (closeButton.isHit(p)) {
			closeButton.setFillColor(BUTTON_HOVER_COLOR);
			hovering = true;
		} else
			hoverButton = false;
		if (hovering && !hoverButton) {
			SoundManager.playSoundEffect(SoundEffect.BUTTON_HOVER);
			hoverButton = true;
		}
		repaint();
	}

	private void handleClose() {
		dispose();
	}

	private void handleEndTurn() {
		final StandardMessageDialog message = new StandardMessageDialog("Waiting for opponent...",
				30);
		new SwingWorker<Integer, String>() {
			@Override
			protected Integer doInBackground() throws Exception {
				Game.endTurn();
				message.dispose();
				handleGameOver();
				return null;
			}
		}.execute();
		dispose();
		message.setVisible(true);
	}

	private void handleGameOver() {
		final StandardMessageDialog message = new StandardMessageDialog("", 30);
		if (Game.isDisconnected()) {
			message.setMessage("You opponent has disconnected. Exiting...");
		} else if (Game.isGameOver()) {
			if (Game.getWinner() == Game.getPlayer().getPlayerType())
				message.setMessage("You won! Congratulations!");
			else
				message.setMessage("You lost! Better luck next time...");
		} else
			return;
		new SwingWorker<Integer, String>() {
			@Override
			protected Integer doInBackground() throws Exception {
				Thread.sleep(5000);
				message.dispose();
				Game.quitGame();
				return null;
			}
		}.execute();
		message.setVisible(true);
	}

	private void handleQuit() {
		dispose();
		Game.quitGame();
	}

	private void handleTraits() {
		dispose();
		SoundManager.playSoundEffect(SoundEffect.DIALOG_OPEN);
		new TraitsDialog().setVisible(true);
	}

	private void setupButtons() {
		int margin = 20;
		int leftMargin = 300;
		traitsButton = new Button(leftMargin, margin, BUTTON_WIDTH, BUTTON_HEIGHT,
				BUTTON_LINE_WIDTH, "Traits", BUTTON_COLOR, BUTTON_LINE_COLOR);
		endTurnButton = new Button(MENU_WIDTH - margin - BUTTON_WIDTH, margin, BUTTON_WIDTH,
				BUTTON_HEIGHT, BUTTON_LINE_WIDTH, "End Turn", BUTTON_COLOR, BUTTON_LINE_COLOR);
		quitButton = new Button(leftMargin, MENU_HEIGHT - margin - BUTTON_HEIGHT, BUTTON_WIDTH,
				BUTTON_HEIGHT, BUTTON_LINE_WIDTH, "Quit", BUTTON_COLOR, BUTTON_LINE_COLOR);
		closeButton = new Button(MENU_WIDTH - margin - BUTTON_WIDTH, MENU_HEIGHT - margin
				- BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_LINE_WIDTH, "Close",
				BUTTON_COLOR, BUTTON_LINE_COLOR);
	}

	private void setupDialog() {
		setOpacity(0.9f);
	}

	private void setupListeners() {
		GameMenuDialogListener listener = new GameMenuDialogListener();
		addMouseListener(listener);
		addMouseMotionListener(listener);
	}

	private void setupPanel() {
		panel = new JPanel(true) {

			private static final long	serialVersionUID	= 1L;

			@Override
			protected void paintComponent(Graphics g) {
				// Draw background
				g.drawImage(background, 0, 0, null);

				// Draw buttons
				g.setFont(new Font("sans-serif", Font.BOLD, 20));
				traitsButton.paintButton(g);
				endTurnButton.paintButton(g);
				quitButton.paintButton(g);
				closeButton.paintButton(g);

				g.dispose();
			}
		};
		getContentPane().add(panel);
	}

}
