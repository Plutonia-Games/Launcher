package fr.koora.plutonia.launcher.managers;

import java.io.File;

import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.GameTweak;
import fr.theshark34.openlauncherlib.minecraft.GameType;
import fr.theshark34.openlauncherlib.minecraft.GameVersion;

public class SettingsManager {

	public static final GameVersion GAME_VERSION = new GameVersion("1.8.9", GameType.V1_8_HIGHER);
	public static final GameInfos GAME_INFOS = new GameInfos("plutonia", GAME_VERSION, new GameTweak[] {});

	private final static String operatingSystem = System.getProperty("os.name").toLowerCase();
	private final static String arch = System.getProperty("os.arch").toLowerCase();

	public static GameFolder getGameFolder() {
		String bin = "bin";

		if (operatingSystem.contains("nux") || operatingSystem.contains("nix"))
			bin += File.separator + "unix";
		else if (operatingSystem.contains("mac")) {
			bin += File.separator + "osx";

			if (arch.equals("aarch64"))
				bin += File.separator + "silicon";
		}

		return new GameFolder("assets", "libs", bin, "minecraft.jar");
	}

	public static String AUTH_URL = "https://api.plutonia-mc.fr/auth";
	public static String UPDATE_URL = "https://launcher.plutonia-mc.fr/";

	public static final String HTML_BASE_TEXT = "<html><center>Mot de passe oublié <span style='color:orange;'>ou</span> pas encore inscrit ?</center></html>";
	public static final String HTML_HOVERED_TEXT = "<html><center><u>Mot de passe oublié <span style='color:orange;'>ou</span> pas encore inscrit ?</u></center></html>";

}