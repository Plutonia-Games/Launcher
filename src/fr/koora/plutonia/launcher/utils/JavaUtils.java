package fr.koora.plutonia.launcher.utils;

public class JavaUtils {

	public static int getJavaVersion() {
		String javaVersion = System.getProperty("java.version");
		String[] versionParts = javaVersion.split("\\.");

		if (versionParts[0].equals("1")) {
			return Integer.parseInt(versionParts[1]);
		} else {
			return Integer.parseInt(versionParts[0]);
		}
	}
}