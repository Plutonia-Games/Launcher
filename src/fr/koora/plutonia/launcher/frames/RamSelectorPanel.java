package fr.koora.plutonia.launcher.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import fr.theshark34.openlauncherlib.util.ramselector.AbstractOptionFrame;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.swinger.Swinger;

public class RamSelectorPanel extends AbstractOptionFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JLabel ramLabel;
	private JComboBox<String> ramBox;

	private JButton confirmButton;

	public RamSelectorPanel(RamSelector selector) {
		super(selector);

		this.setTitle("Plutonia - Options");
		this.setResizable(false);
		this.setSize(275, 125);
		this.setIconImage(Swinger.getResource("icon.png"));
		this.setLocationRelativeTo(null);
		this.setLayout(null);

		this.ramLabel = new JLabel("Ram: ");
		this.ramLabel.setBounds(15, 20, 45, 25);
		this.add(this.ramLabel);

		this.ramBox = new JComboBox<String>(RamSelector.RAM_ARRAY);
		this.ramBox.setBounds(65, 20, 155, 25);
		this.add(this.ramBox);

		this.confirmButton = new JButton("Confirmer");
		this.confirmButton.addActionListener(this);
		this.confirmButton.setBounds(88, 60, 100, 25);
		this.add(this.confirmButton);
	}

	@Override
	public int getSelectedIndex() {
		return this.ramBox.getSelectedIndex();
	}

	@Override
	public void setSelectedIndex(int index) {
		this.ramBox.setSelectedIndex(index);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.getSelector().save();
		this.setVisible(false);
	}

}