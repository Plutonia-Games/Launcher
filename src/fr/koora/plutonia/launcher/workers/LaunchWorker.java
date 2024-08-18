package fr.koora.plutonia.launcher.workers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.koora.plutonia.launcher.Frame;
import fr.koora.plutonia.launcher.managers.SettingsManager;
import fr.koora.plutonia.launcher.utils.JavaUtils;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.MinecraftLauncher;
import fr.theshark34.openlauncherlib.util.ProcessLogManager;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LaunchWorker {

	private Frame frame;

	public void launch(AuthInfos authInfos) throws LaunchException, InterruptedException {
		ExternalLaunchProfile launchProfile = MinecraftLauncher.createExternalProfile(SettingsManager.GAME_INFOS, SettingsManager.getGameFolder(), authInfos);

		this.configureVmArgs(launchProfile);

		Process process = startProcess(launchProfile);

		this.initLogsProcess(process);
		this.handleProcessCompletion(process);
	}

	private void configureVmArgs(ExternalLaunchProfile launchProfile) {
		List<String> args = new ArrayList<>(launchProfile.getVmArgs());

		args.add("-Djava.net.preferIPv4Stack=true");
		args.addAll(Arrays.asList(this.frame.getPanel().getRamSelector().getRamArguments()));

		if (JavaUtils.getJavaVersion() <= 8) {
			args.add("-Xincgc");
		}

		launchProfile.setVmArgs(args);
	}

	private Process startProcess(ExternalLaunchProfile launchProfile) throws LaunchException {
		return new ExternalLauncher(launchProfile).launch();
	}

	private void initLogsProcess(Process process) {
		ProcessLogManager logManager = new ProcessLogManager(process.getInputStream(), new File(SettingsManager.GAME_INFOS.getGameDir(), "logs.txt"));
		logManager.start();
	}

	private void handleProcessCompletion(Process process) {
		try {
			this.frame.setVisible(false);

			Thread.sleep(5000);
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
}