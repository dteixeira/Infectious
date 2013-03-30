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

	private static final long serialVersionUID = 1L;
	private static final int MENU_WIDTH = 650;
	private static final int MENU_HEIGHT = 150;
	private static final int BUTTON_WIDTH = 160;
	private static final int BUTTON_HEIGHT = 50;
	private static final Color BUTTON_COLOR = new Color(0.149f, 0.451f, 0.925f);
	private static final Color BUTTON_LINE_COLOR = Color.WHITE;
	private static final Color BUTTON_HOVER_COLOR = new Color(0.349f, 0.573f, 0.925f);
	private static final int BUTTON_LINE_WIDTH = 3;

	private JPanel panel = null;
	private Button closeButton = null;
	private Button quitButton = null;
	private Button endTurnButton = null;
	private Button traitsButton = null;
	private boolean hoverButton = false;

	public GameMenuDialog() {
		super(new Dimension(MENU_WIDTH, MENU_HEIGHT));
		setupDialog();
		setupPanel();
		setupButtons();
		setupListeners();
	}

	private void setupButtons() {
		int margin = 20;
		int leftMargin = 300;
		traitsButton = new Button(leftMargin, margin, BUTTON_WIDTH,
				BUTTON_HEIGHT, BUTTON_LINE_WIDTH, "Traits", BUTTON_COLOR,
				BUTTON_LINE_COLOR);
		endTurnButton = new Button(MENU_WIDTH - margin - BUTTON_WIDTH, margin,
				BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_LINE_WIDTH, "End Turn",
				BUTTON_COLOR, BUTTON_LINE_COLOR);
		quitButton = new Button(leftMargin, MENU_HEIGHT - margin
				- BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT,
				BUTTON_LINE_WIDTH, "Quit", BUTTON_COLOR, BUTTON_LINE_COLOR);
		closeButton = new Button(MENU_WIDTH - margin - BUTTON_WIDTH,
				MENU_HEIGHT - margin - BUTTON_HEIGHT, BUTTON_WIDTH,
				BUTTON_HEIGHT, BUTTON_LINE_WIDTH, "Close", BUTTON_COLOR,
				BUTTON_LINE_COLOR);
	}

	private void setupListeners() {
		GameMenuDialogListener listener = new GameMenuDialogListener();
		addMouseListener(listener);
		addMouseMotionListener(listener);
	}

	private void setupDialog() {
		setOpacity(0.9f);
	}

	public void handleHover(MouseEvent e) {
		Point p = e.getPoint();
		traitsButton.setFillColor(BUTTON_COLOR);
		endTurnButton.setFillColor(BUTTON_COLOR);
		quitButton.setFillColor(BUTTON_COLOR);
		closeButton.setFillColor(BUTTON_COLOR);
		boolean hovering = false;
		if(traitsButton.isHit(p)) {
			traitsButton.setFillColor(BUTTON_HOVER_COLOR);
			hovering = true;
		}
		else if(endTurnButton.isHit(p)) {
			endTurnButton.setFillColor(BUTTON_HOVER_COLOR);
			hovering = true;
		}
		else if(quitButton.isHit(p)) {
			quitButton.setFillColor(BUTTON_HOVER_COLOR);
			hovering = true;
		}
		else if(closeButton.isHit(p)) {
			closeButton.setFillColor(BUTTON_HOVER_COLOR);
			hovering = true;
		}
		else
			hoverButton = false;
		if(hovering && !hoverButton) {
			SoundManager.playSoundEffect(SoundEffect.BUTTON_HOVER);
			hoverButton = true;
		}
		repaint();
	}
	
	public void handleClick(MouseEvent e) {
		Point p = e.getPoint();
		if(traitsButton.isHit(p))
			handleTraits();
		else if(endTurnButton.isHit(p))
			handleEndTurn();
		else if(quitButton.isHit(p))
			handleQuit();
		else if(closeButton.isHit(p))
			handleClose();
	}

	private void handleClose() {
		dispose();
	}

	private void handleQuit() {
		// TODO Auto-generated method stub
	}

	private void handleEndTurn() {
		final StandardMessageDialog message = new StandardMessageDialog("Waiting for opponent...", 30);
		new SwingWorker<Integer, String> () {
			@Override
			protected Integer doInBackground() throws Exception {
				Game.endTurn();
				message.setVisible(false);
				return null;
			}
		}.execute();
		dispose();
		message.setVisible(true);
	}

	private void handleTraits() {
		dispose();
		SoundManager.playSoundEffect(SoundEffect.DIALOG_OPEN);
		new TraitsDialog().setVisible(true);
	}

	private void setupPanel() {
		panel = new JPanel(true) {

			private static final long serialVersionUID = 1L;

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
