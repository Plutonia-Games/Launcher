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

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import fr.theshark34.supdate.BarAPI;

import static fr.theshark34.supdate.SUpdate.logger;


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
		dest.getParentFile().mkdirs();

		logger.info("Downloading file %s", fileUrl);

		HttpURLConnection connection = null;
		DataInputStream dis = null;
		FileOutputStream fos = null;

		try {
			connection = (HttpURLConnection) fileUrl.openConnection();
			connection.addRequestProperty("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");

			dis = new DataInputStream(connection.getInputStream());
			fos = new FileOutputStream(dest);

			byte[] buffer = new byte[4096];
			int bytesRead;

			while ((bytesRead = dis.read(buffer)) != -1) {
				fos.write(buffer, 0, bytesRead);
				BarAPI.incrementNumberOfTotalDownloadedBytes();
			}

			BarAPI.setNumberOfDownloadedFiles(BarAPI.getNumberOfDownloadedFiles() + 1);
		} catch (IOException e) {
			logger.warning("File " + fileUrl + " wasn't downloaded, error: ", e);
		} finally {
			try {
				if (dis != null)
					dis.close();

				if (fos != null)
					fos.close();
			} catch (IOException ex) {
				logger.warning("Error closing streams: ", ex);
			}
		}
	}
}