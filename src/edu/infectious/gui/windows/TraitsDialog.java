package edu.infectious.gui.windows;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import edu.infectious.gui.containers.HumidityPanel;
import edu.infectious.gui.containers.TemperaturePanel;
import edu.infectious.gui.containers.TraitPanel;
import edu.infectious.gui.containers.TransmissionPanel;

public class TraitsDialog extends StandardDialog {

	/*
	 * Constants
	 */
	private static final int	DIALOG_HEIGHT		= 600;
	private static final int	DIALOG_WIDTH		= 800;
	private static final long	serialVersionUID	= 1L;

	/*
	 * Instance fields
	 */
	private HumidityPanel		humidityPanel;
	private JPanel				panel;
	private TemperaturePanel	temperaturePanel;
	private TraitPanel			traitPanel;
	private TransmissionPanel	transmissionPanel;

	/*
	 * Constructor
	 */
	public TraitsDialog() {
		super(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT));
		setupDialog();
		setupMainPanel();
		setupPanels();
	}

	/*
	 * Instance methods
	 */
	private void setupDialog() {
	}

	private void setupMainPanel() {
		panel = new JPanel(null, true);
		panel.setBackground(new Color(0f, 0f, 0f, 0f));
		getContentPane().add(panel);
	}

	private void setupPanels() {
		transmissionPanel = new TransmissionPanel();
		transmissionPanel.setBounds(0, 0, 300, 240);
		panel.add(transmissionPanel);
		temperaturePanel = new TemperaturePanel();
		temperaturePanel.setBounds(0, 240, 300, 180);
		panel.add(temperaturePanel);
		humidityPanel = new HumidityPanel();
		humidityPanel.setBounds(0, 420, 300, 180);
		panel.add(humidityPanel);
		traitPanel = new TraitPanel();
		traitPanel.setBounds(300, 0, 500, 600);
		panel.add(traitPanel);
	}

}
