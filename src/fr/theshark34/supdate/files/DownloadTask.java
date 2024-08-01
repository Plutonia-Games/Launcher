/*
 * Copyright 2015 TheShark34
 *
 * This file is part of S-Update.

 * S-Update is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * S-Update is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with S-Update.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.theshark34.supdate.files;

import static fr.theshark34.supdate.SUpdate.logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import fr.theshark34.supdate.BarAPI;


/**
 * The Download Task
 *
 * <p>
 *     A Task that downloads a file to a destination.
 * </p>
 *
 * @version 3.0.0-BETA
 * @author TheShark34
 */
public class DownloadTask implements Runnable {

    /**
     * The URL of the file to download
     */
    private URL fileUrl;

    /**
     * The destination file
     */
    private File dest;

    /**
     * Simple constructor
     *
     * @param fileUrl
     *            The URL of the file to download
     * @param dest
     *            The destination file
     */
    public DownloadTask(URL fileUrl, File dest) {
        this.fileUrl = fileUrl;
        this.dest = dest;
    }

    @Override
    public void run() {
        // Making the parent folders of the destination file
        dest.getParentFile().mkdirs();

        // Printing a message
        logger.info("Downloading file %s", fileUrl);

        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) fileUrl.openConnection();
            // Adding some user agents
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");

            try (DataInputStream dis = new DataInputStream(connection.getInputStream())) {
                // Check if the downloaded file is JSON
                if (fileUrl.toString().endsWith(".json") || fileUrl.toString().endsWith(".xml")) {
					// Writing the file to the destination
					try (BufferedWriter bw = new BufferedWriter(new FileWriter(dest))) {
						InputStreamReader isr = new InputStreamReader(dis);
						BufferedReader br = new BufferedReader(isr);
						String line;
						while ((line = br.readLine()) != null) {
							bw.write(line);
							bw.newLine();
						}
					}
				} else {
                    // If it's not a JSON file, continue downloading and writing it to the destination file
                    byte[] fileData = new byte[connection.getContentLength()];
                    int x;
                    for (x = 0; x < fileData.length; x++) {
                        BarAPI.incrementNumberOfTotalDownloadedBytes();
						fileData[x] = dis.readByte();
					}

					// If it's not a JSON file, write it to the destination file
					try (FileOutputStream fos = new FileOutputStream(dest)) {
						fos.write(fileData);
					}

                    // Incrementing the BarAPI 'numberOfDownloadedFiles' variable
                    BarAPI.setNumberOfDownloadedFiles(BarAPI.getNumberOfDownloadedFiles() + 1);
                }
            }
        } catch (IOException e) {
            // If it failed printing a warning message
            logger.warning("File " + fileUrl + " wasn't downloaded, error: ", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}