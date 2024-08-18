package fr.koora.plutonia.launcher;

import fr.koora.plutonia.launcher.utils.JavaUtils;
import fr.theshark34.swinger.Swinger;

public class Main {

	public static void main(String[] args) {
		Swinger.setSystemLookNFeel();
		Swinger.setResourcePath("/fr/koora/plutonia/launcher/resources");

		System.out.println("Current Java version: " + JavaUtils.getJavaVersion());
		new Frame();
	}

}