package edu.infectious.gui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import edu.infectious.gui.utilities.Button;
import edu.infectious.gui.utilities.SoundEffect;
import edu.infectious.gui.utilities.SoundManager;
import edu.infectious.gui.windows.CountryInfoDialog;

public class CountryInfoDialogListener implements MouseListener, MouseMotionListener {
	
	private Button button;
	
	public CountryInfoDialogListener(Button button) {
		super();
		this.button = button;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		CountryInfoDialog dialog = (CountryInfoDialog) e.getComponent();
		if(dialog.isButtonHover() && !button.isHit(e.getPoint())) {
			dialog.setButtonHover(false);
			dialog.repaint();
		} else if(!dialog.isButtonHover() && button.isHit(e.getPoint())) {
			dialog.setButtonHover(true);
			dialog.repaint();
			SoundManager.playSoundEffect(SoundEffect.BUTTON_HOVER);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(button.isHit(e.getPoint()))
			((CountryInfoDialog) e.getComponent()).dispose();
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
