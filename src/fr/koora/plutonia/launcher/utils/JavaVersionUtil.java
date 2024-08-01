package fr.koora.plutonia.launcher.utils;

public class JavaVersionUtil {

	public static int getJavaVersion() {
		String javaVersion = System.getProperty("java.version");

		return javaVersion.startsWith("1.") ? Integer.parseInt(javaVersion.split("\\.")[1]) : Integer.parseInt(javaVersion.split("\\.")[0]);
	}

}