package fr.koora.plutonia.launcher.workers;

import java.io.IOException;
import java.net.ServerSocket;

import fr.koora.plutonia.launcher.managers.SettingsManager;

public class ServerWorker {

	private ServerSocket lockSocket;

	public boolean isRunning() {
		try {
			this.lockSocket = new ServerSocket(SettingsManager.LOCK_PORT);
			return false;
		} catch (IOException e) {
			return true;
		}
	}

	public void releaseLock() {
		if (this.lockSocket != null) {
			try {
				this.lockSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
