package fr.koora.plutonia.launcher;

import javax.swing.JOptionPane;

import fr.koora.plutonia.launcher.utils.JavaUtils;
import fr.koora.plutonia.launcher.workers.ServerWorker;
import fr.theshark34.swinger.Swinger;

public class Main {

	public static Frame INSTANCE;
	public static ServerWorker SERVER_WORKER;
	
	public static void main(String[] args) {
		SERVER_WORKER = new ServerWorker();

		if (SERVER_WORKER.isRunning()) {
			JOptionPane.showMessageDialog(null,
				"Une instance du launcher est déjà lancée !" +
				"\nLe multi compte est interdit.",
				"Plutonia - Multi compte",
				JOptionPane.ERROR_MESSAGE
			);

			System.exit(0);
		}

		Swinger.setSystemLookNFeel();
		Swinger.setResourcePath("/fr/koora/plutonia/launcher/resources");

		System.out.println("Current Java version: " + JavaUtils.getJavaVersion());
		INSTANCE = new Frame();
	}

}