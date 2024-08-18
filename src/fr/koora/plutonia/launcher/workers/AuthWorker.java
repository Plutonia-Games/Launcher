package fr.koora.plutonia.launcher.workers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import fr.koora.plutonia.launcher.managers.SettingsManager;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;

public class AuthWorker {

	public AuthInfos auth(String username, String password) throws IOException {
		return auth(username, password, "");
	}

	private AuthInfos auth(String username, String password, String tfaCode) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) new URL(SettingsManager.AUTH_URL).openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);

		String encodedPassword = URLEncoder.encode(password, "UTF-8");
		String postData = "username=" + username + "&password=" + encodedPassword + (!tfaCode.isEmpty() ? "&tfa=" + tfaCode : "");

		try (OutputStream outputStream = conn.getOutputStream()) {
			outputStream.write(postData.getBytes("UTF-8"));
		}

		JsonObject response;

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
			response = new Gson().fromJson(reader.readLine(), JsonObject.class);
		}

		String status = response.get("status").getAsString();

		if (status.equals("200")) {
			String session = response.get("session").getAsString();
			String uuid = response.get("uuid").getAsString();
			return new AuthInfos(username, session, uuid);
		}

		if (status.equals("400")) {
			String requestedCode = JOptionPane.showInputDialog(null, "Veuillez saisir votre code d'authentification (2FA) :", "Double authentification", JOptionPane.QUESTION_MESSAGE);

			if (requestedCode == null || requestedCode.isEmpty()) {
				throw new IOException("Votre code est vide, veuillez le vérifier.");
			}

			return auth(username, password, requestedCode);
		}

		throw new IOException(response.get("message").getAsString());
	}
}