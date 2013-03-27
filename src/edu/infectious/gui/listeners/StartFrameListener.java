package edu.infectious.gui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import edu.infectious.gui.windows.StartFrame;

public class StartFrameListener implements MouseListener, MouseMotionListener {

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		StartFrame frame = (StartFrame) e.getComponent();
		frame.handleHover(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		StartFrame frame = (StartFrame) e.getComponent();
		frame.handleClick(e);
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
