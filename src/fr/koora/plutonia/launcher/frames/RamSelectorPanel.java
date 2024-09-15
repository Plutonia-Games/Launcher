package fr.koora.plutonia.launcher.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import fr.koora.plutonia.launcher.Main;
import fr.theshark34.openlauncherlib.util.ramselector.AbstractOptionFrame;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.swinger.Swinger;

public class RamSelectorPanel extends AbstractOptionFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JLabel ramLabel;
	private JComboBox<String> ramBox;

	private JButton confirmButton;

	private JLabel modsTitleLabel;
	private JCheckBox tabbychatCheckBox;
	private JCheckBox simpleVoiceChatCheckBox;

	public RamSelectorPanel(RamSelector selector) {
		super(selector);

		this.setTitle("Plutonia - Options");
		this.setResizable(false);
		this.setSize(275, 225);
		this.setIconImage(Swinger.getResource("icon.png"));
		this.setLocationRelativeTo(null);
		this.setLayout(null);

		this.ramLabel = new JLabel("Ram: ");
		this.ramLabel.setBounds(15, 20, 45, 25);
		this.add(this.ramLabel);

		this.ramBox = new JComboBox<String>(RamSelector.RAM_ARRAY);
		this.ramBox.setBounds(65, 20, 155, 25);
		this.add(this.ramBox);

		this.modsTitleLabel = new JLabel("Mods:");
		this.modsTitleLabel.setBounds(15, 60, 100, 25);
		this.add(this.modsTitleLabel);

		this.tabbychatCheckBox = new JCheckBox("Activer TabbyChat");
		this.tabbychatCheckBox.setBounds(15, 85, 150, 25);
		this.add(this.tabbychatCheckBox);

		this.simpleVoiceChatCheckBox = new JCheckBox("Activer Simple Voice Chat");
		this.simpleVoiceChatCheckBox.setBounds(15, 110, 200, 25);
		this.add(this.simpleVoiceChatCheckBox);

		this.confirmButton = new JButton("Confirmer");
		this.confirmButton.addActionListener(this);
		this.confirmButton.setBounds(88, 145, 100, 25);
		this.add(this.confirmButton);

		this.loadConfig();
	}

	private void loadConfig() {
		String modList = Main.INSTANCE.getPanel().getSaver().get("addons", "");

		this.tabbychatCheckBox.setSelected(modList.contains("tabbychat"));
		this.simpleVoiceChatCheckBox.setSelected(modList.contains("svc"));
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
		String modList = String.join(",",
			this.tabbychatCheckBox.isSelected() ? "tabbychat" : "",
			this.simpleVoiceChatCheckBox.isSelected() ? "svc" : "")
		.replaceAll(",+$", "");

		Main.INSTANCE.getPanel().getSaver().set("addons", modList);
		this.getSelector().save();

		this.setVisible(false);
	}
}