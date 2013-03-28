package edu.infectious.gui.windows;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
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
		transmissionPanel = new TransmissionPanel(backgroundColor);
		transmissionPanel.setBounds(0, 0, 300, 240);
		panel.add(transmissionPanel);
		temperaturePanel = new TemperaturePanel(backgroundColor);
		temperaturePanel.setBounds(0, 240, 300, 180);
		panel.add(temperaturePanel);
		humidityPanel = new HumidityPanel(backgroundColor);
		humidityPanel.setBounds(0, 420, 300, 180);
		panel.add(humidityPanel);
		traitPanel = new TraitPanel(backgroundColor);
		traitPanel.setBounds(300, 0, 500, 600);
		panel.add(traitPanel);
	}

	private void setupMainPanel() {
		panel = new JPanel(null, true);
		panel.setBackground(backgroundColor);
		getContentPane().add(panel);
	}

	private void setupDialog() {
		setOpacity(0.9f);
	}
	
	public static void main(String[] args) {
		StandardDialog.setupDialogs();
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new TraitsDialog().setVisible(true);
			}
		});
	}

}
