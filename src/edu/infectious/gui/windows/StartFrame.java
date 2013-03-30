package edu.infectious.gui.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import edu.infectious.gui.listeners.StartFrameListener;
import edu.infectious.gui.utilities.Button;
import edu.infectious.gui.utilities.SoundEffect;
import edu.infectious.gui.utilities.SoundManager;
import edu.infectious.logic.Game;

public class StartFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String BACKGROUND_FILENAME = "images/start.png";
	private static final int BUTTON_WIDTH = 180;
	private static final int BUTTON_HEIGHT = 60;
	private static final int BUTTON_LINE_WIDTH = 3;
	private static final Color BUTTON_COLOR = new Color(0.149f, 0.451f, 0.925f);
	private static final Color BUTTON_LINE_COLOR = Color.WHITE;
	private static final Color BUTTON_HOVER_COLOR = new Color(0.349f, 0.573f, 0.925f);

	private BufferedImage background = null;
	private Dimension screenSize = null;
	private Button startButton = null;
	private Button quitButton = null;
	private JPanel panel = null;
	private boolean quitButtonHover = false;
	private boolean startButtonHover = false;

	public StartFrame() {
		super();
		setupFrame();
		setupButtons();
		setupBackground();
		setupPanel();
		setupListeners();
	}

	private void setupBackground() {
		try {
			BufferedImage toResize = ImageIO
					.read(new File(BACKGROUND_FILENAME));
			background = new BufferedImage(screenSize.width, screenSize.height,
					toResize.getType());
			Graphics2D g2d = background.createGraphics();
			g2d.drawImage(toResize, 0, 0, screenSize.width, screenSize.height,
					null);
			g2d.dispose();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void setupPanel() {
		panel = new JPanel(true) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(background, 0, 0, null);
				g.setFont(new Font("sans-serif", Font.BOLD, 20));
				startButton.paintButton(g);
				quitButton.paintButton(g);
				g.dispose();
			}
		};
		getContentPane().add(panel);
	}

	private void setupButtons() {
		int marginY = (int) ((screenSize.height - BUTTON_HEIGHT) / 2.0) + 100;
		int marginX = (int)((screenSize.width - 2 * BUTTON_WIDTH - 20) / 2.0);
		startButton = new Button(marginX, marginY, BUTTON_WIDTH, BUTTON_HEIGHT,
				BUTTON_LINE_WIDTH, "Find Opponent", BUTTON_COLOR,
				BUTTON_LINE_COLOR);
		quitButton = new Button(marginX + 20 + BUTTON_WIDTH, marginY, BUTTON_WIDTH, BUTTON_HEIGHT,
				BUTTON_LINE_WIDTH, "Exit", BUTTON_COLOR,
				BUTTON_LINE_COLOR);
	}

	private void setupListeners() {
		StartFrameListener listener = new StartFrameListener();
		addMouseListener(listener);
		addMouseMotionListener(listener);
	}
	
	public void handleHover(MouseEvent e) {
		startButton.setFillColor(BUTTON_COLOR);
		quitButton.setFillColor(BUTTON_COLOR);
		if(startButton.isHit(e.getPoint())) {
			startButton.setFillColor(BUTTON_HOVER_COLOR);
			if(!startButtonHover) {
				SoundManager.playSoundEffect(SoundEffect.BUTTON_HOVER);
				startButtonHover = true;
			}
		} else if(quitButton.isHit(e.getPoint())) {
			quitButton.setFillColor(BUTTON_HOVER_COLOR);
			if(!quitButtonHover) {
				SoundManager.playSoundEffect(SoundEffect.BUTTON_HOVER);
				quitButtonHover = true;
			}
		} else {
			quitButtonHover = false;
			startButtonHover = false;
		}
		repaint();
	}
	
	public void handleClick(MouseEvent e) {
		if(startButton.isHit(e.getPoint()))
			handleStart();
		else if(quitButton.isHit(e.getPoint()))
			handleQuit();
	}
	
	private void handleQuit() {
		System.exit(0);
	}
	
	private void handleStart() {
		Game.searchForOpponent();
	}

	private void setupFrame() {
		screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
	}

}