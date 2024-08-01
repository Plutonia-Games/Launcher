package fr.koora.plutonia.launcher.workers;

import java.io.File;
import java.util.List;

import fr.koora.plutonia.launcher.Frame;
import fr.koora.plutonia.launcher.managers.SettingsManager;
import fr.koora.plutonia.launcher.utils.JavaVersionUtil;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.MinecraftLauncher;
import fr.theshark34.openlauncherlib.util.ProcessLogManager;

public class LaunchWorker {

	private Frame frame;

	public LaunchWorker(Frame frame) {
		this.frame = frame;
	}

	public void launch(AuthInfos authInfos) throws LaunchException, InterruptedException {
		ExternalLaunchProfile externalLaunchProfile = MinecraftLauncher.createExternalProfile(SettingsManager.GAME_INFOS, SettingsManager.getGameFolder(), authInfos);

		List<String> args = externalLaunchProfile.getVmArgs();

		args.add("-Djava.net.preferIPv4Stack=true");

		if (JavaVersionUtil.getJavaVersion() <= 8)
			args.add("-Xincgc");

		externalLaunchProfile.setVmArgs(args);

		ExternalLauncher launcher = new ExternalLauncher(externalLaunchProfile);

		Process process = launcher.launch();

		ProcessLogManager processLogManager = new ProcessLogManager(process.getInputStream(), new File(SettingsManager.GAME_INFOS.getGameDir(), "logs.txt"));
		processLogManager.start();

		try {
			this.frame.setVisible(false);

			Thread.sleep(5000L);

			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.exit(0);
	}

}