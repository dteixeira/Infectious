package edu.infectious.gui.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class StandardMessageDialog extends StandardDialog {

	/*
	 * Constants
	 */
	private static final int	DIALOG_HEIGHT		= 150;
	private static final int	DIALOG_WIDTH		= 800;
	private static final long	serialVersionUID	= 1L;

	/*
	 * Instance fields
	 */
	private int					fontSize			= 35;
	private String				message				= "";
	private JPanel				panel				= null;

	/*
	 * Constructor
	 */
	public StandardMessageDialog(String message, int fontSize) {
		super(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT));
		this.message = message;
		this.fontSize = fontSize;
		setupDialog();
		setupPanel();
	}

	/*
	 * Instance methods
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private void setupDialog() {
	}

	private void setupPanel() {
		panel = new JPanel(true) {

			private static final long	serialVersionUID	= 1L;

			@Override
			protected void paintComponent(Graphics g) {
				// Draw background
				g.drawImage(background, 0, 0, null);

				// Draw message
				g.setColor(Color.WHITE);
				g.setFont(new Font("sans-serif", Font.PLAIN, fontSize));
				int messageX = DIALOG_WIDTH - g.getFontMetrics().stringWidth(message) - 20;
				int messageY = (int) ((DIALOG_HEIGHT - g.getFontMetrics()
						.getStringBounds(message, g).getHeight()) / 2.0)
						+ g.getFontMetrics().getAscent();
				g.drawString(message, messageX, messageY);

				g.dispose();
			}
		};
		getContentPane().add(panel);
	}

}
