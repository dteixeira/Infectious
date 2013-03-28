package edu.infectious.gui.containers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import edu.infectious.gui.utilities.Button;
import edu.infectious.script.trait.Trait;

public class TraitPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final Color BUTTON_COLOR = new Color(0.149f, 0.451f, 0.925f);
	private static final Color BUTTON_LINE_COLOR = Color.WHITE;
	private static final int BUTTON_LINE_WIDTH = 3;
	private static final int BUTTON_WIDTH = 260;
	private static final int BUTTON_HEIGHT = 30;
	
	private JTextArea descriptionArea;
	private Trait currentTrait;
	private Color backgroundColor;
	
	public TraitPanel(Color backgroundColor) {
		super(true);
		this.backgroundColor = backgroundColor;
		setupPanel();
		setupTextArea();
	}

	private void setupTextArea() {
		setLayout(null);
		descriptionArea = new JTextArea("asdadasd asdasdasdas dasdasd asdasdasd asdasd asdasdasdasd asd asdasd asd asdasdsdsdsd sdasd asdasds\nadasdadsads");
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);
		descriptionArea.setFont(new Font("sans-serif", Font.PLAIN, 16));
		descriptionArea.setBackground(backgroundColor);
		descriptionArea.setForeground(Color.WHITE);
		descriptionArea.setDisabledTextColor(Color.WHITE);
		descriptionArea.setEditable(false);
		descriptionArea.setEnabled(false);
		descriptionArea.setFocusable(false);
		descriptionArea.setBounds(20, 100, 460, 200);
		add(descriptionArea);
	}

	private void setupPanel() {
		setBackground(backgroundColor);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(0f, 0f, 0f, 0f));
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Color.WHITE);
		g2d.drawRect(5, 5, getWidth() - 10, getHeight() - 10);
		super.paintComponents(g);
		g2d.dispose();
	}

}
