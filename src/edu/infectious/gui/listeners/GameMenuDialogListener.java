package edu.infectious.gui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import edu.infectious.gui.windows.GameMenuDialog;

public class GameMenuDialogListener implements MouseListener, MouseMotionListener {

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		GameMenuDialog dialog = (GameMenuDialog) e.getComponent();
		dialog.handleHover(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		GameMenuDialog dialog = (GameMenuDialog) e.getComponent();
		dialog.handleClick(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
