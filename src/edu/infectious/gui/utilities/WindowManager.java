package edu.infectious.gui.utilities;

import javax.swing.SwingUtilities;
import edu.infectious.gui.windows.MainFrame;
import edu.infectious.gui.windows.MapPanel;
import edu.infectious.gui.windows.StartFrame;

public abstract class WindowManager {
	
	private static final double FPS_LIMIT = 30.0;

	private static StartFrame startFrame = null;
	private static MainFrame mainFrame = null;
	private static Thread refreshThread = null;
	private static boolean gameRefresh = false;

	public static void initGame() {
		startFrame = new StartFrame();
		mainFrame = new MainFrame();
	}
	
	public static void showMainMenu() {
		stopGameRefresh();
		mainFrame.setVisible(false);
		startFrame.setVisible(true);
	}
	
	public static void showGame() {
		startFrame.setVisible(false);
		mainFrame.setVisible(true);
		startGameRefresh();
	}
	
	private static void stopGameRefresh() {
		gameRefresh = false;
		if(refreshThread == null)
			return;
		while(refreshThread.isAlive()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		refreshThread = null;
	}
	
	private static void startGameRefresh() {
		stopGameRefresh();
		gameRefresh = true;
		refreshThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Runnable update = new Runnable() {
					@Override
					public void run() {
						MapPanel.getInstance().repaint();
					}
				};
				while(gameRefresh) {
					try {
						SwingUtilities.invokeLater(update);
						Thread.sleep((int)(1000 / FPS_LIMIT));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		refreshThread.start();
	}
	
	public static void main(String[] args) {
		initGame();
		showMainMenu();
	}
}
