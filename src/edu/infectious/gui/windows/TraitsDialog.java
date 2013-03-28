package edu.infectious.gui.windows;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import edu.infectious.gui.containers.HumidityPanel;
import edu.infectious.gui.containers.TemperaturePanel;
import edu.infectious.gui.containers.TraitPanel;
import edu.infectious.gui.containers.TransmissionPanel;

public class TraitsDialog extends StandardDialog {

	private static final long serialVersionUID = 1L;
	private static final int DIALOG_WIDTH = 800;
	private static final int DIALOG_HEIGHT = 600;
	
	private TransmissionPanel transmissionPanel;
	private TemperaturePanel temperaturePanel;
	private HumidityPanel humidityPanel;
	private TraitPanel traitPanel;
	private JPanel panel;
	
	public TraitsDialog() {
		super(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT));
		setupDialog();
		setupMainPanel();
		setupPanels();
	}

	private void setupPanels() {
		GridBagConstraints c;
		
		// Transmission panel
		transmissionPanel = new TransmissionPanel();
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.375;
		c.weighty = 0.4;
		panel.add(transmissionPanel, c);
		
		// Temperature panel
		temperaturePanel = new TemperaturePanel();
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.375;
		c.weighty = 0.3;
		panel.add(temperaturePanel, c);
		
		// Humidity panel
		humidityPanel = new HumidityPanel();
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.375;
		c.weighty = 0.3;
		panel.add(humidityPanel, c);
		
		// Traits panel
		traitPanel = new TraitPanel();
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 3;
		c.weightx = 0.625;
		c.weighty = 1.0;
		panel.add(traitPanel, c);
	}

	private void setupMainPanel() {
		panel = new JPanel(new GridBagLayout(), true);
		panel.setBackground(backgroundColor);
		getContentPane().add(panel);
	}

	private void setupDialog() {
		setOpacity(0.9f);
	}
	
	public static void main(String[] args) {
		StandardDialog.setupDialogs();
		new TraitsDialog().setVisible(true);
	}

}
