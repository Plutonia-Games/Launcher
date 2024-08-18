package fr.koora.plutonia.launcher.workers;

import java.awt.Color;

import fr.koora.plutonia.launcher.Frame;
import fr.koora.plutonia.launcher.managers.SettingsManager;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.integrated.FileDeleter;

public class UpdateWorker {

	private Frame frame;

	public UpdateWorker(Frame frame) {
		this.frame = frame;
	}

	private Thread updateThread;

	public void update() throws Exception {
		SUpdate supdate = new SUpdate(SettingsManager.UPDATE_URL, SettingsManager.GAME_INFOS.getGameDir());
		supdate.addApplication(new FileDeleter());

		this.updateThread = new Thread() {
			private int val;
			private int max;

			@Override
			public void run() {
				while (!this.isInterrupted()) {
					if (BarAPI.getNumberOfTotalBytesToDownload() == 0) {
						UpdateWorker.this.frame.getPanel().setMessage("Vérification des fichiers...", Color.WHITE);
						continue;
					}

					this.val = (int) (BarAPI.getNumberOfTotalDownloadedBytes() / 1000);
					this.max = (int) (BarAPI.getNumberOfTotalBytesToDownload() / 1000);

					UpdateWorker.this.frame.getPanel().getProgressBar().setMaximum(this.max);
					UpdateWorker.this.frame.getPanel().getProgressBar().setValue(this.val);

					UpdateWorker.this.frame.getPanel().setMessage("Téléchargement en cours... (" + BarAPI.getNumberOfDownloadedFiles() + "/" + BarAPI.getNumberOfFileToDownload() + ")", Color.WHITE);
				}

			}
		};

		this.updateThread.start();
		supdate.start();

		this.interrupt();
	}

	public void interrupt() {
		this.updateThread.interrupt();
	}
}