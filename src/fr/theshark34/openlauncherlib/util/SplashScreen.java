/*
 * Copyright 2015-2016 Adrien "Litarvan" Navratil
 *
 * This file is part of the OpenLauncherLib.

 * The OpenLauncherLib is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The OpenLauncherLib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the OpenLauncherLib.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.theshark34.openlauncherlib.util;

import fr.flowarg.openlauncherlib.ModifiedByFlow;

import javax.swing.*;
import java.awt.*;

/**
 * The Splash Screen
 *
 * <p>
 * This class cans create a splash screen with an image.
 * </p>
 *
 * @author Litarvan
 * @version 3.0.2-BETA
 * @since 3.0.0-BETA
 */
public class SplashScreen extends JFrame
{
	private static final long serialVersionUID = 1L;

	/**
     * Basic Constructor
     *
     * @param title The Window title
     * @param image The splash image
     */
    public SplashScreen(String title, Image image)
    {
        this.setTitle(title);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(image.getWidth(this), image.getHeight(this));
        this.setLocationRelativeTo(null);
        this.setContentPane(new SplashPanel(image));
    }

    /**
     * Display the splash, wait a given time, then hide the splash.
     *
     * @param time The time to wait before hiding the splash
     * @return The created Thread
     */
    @ModifiedByFlow
    public Thread displayFor(final long time)
    {
        Thread thread = new Thread(() -> {
            setVisible(true);

            try
            {
                Thread.sleep(time);
            } catch (InterruptedException e)
            {
                LogUtil.err("warn", " : ", "splash-interrupted");
            }

            setVisible(false);
        });
        thread.start();

        return thread;
    }

    /**
     * Displays the splash (same as setVisible(true))
     */
    public void display()
    {
        this.setVisible(true);
    }

    /**
     * Hide the splash (same as setVisible(false))
     */
    public void stop()
    {
        this.setVisible(false);
    }

    /**
     * Set the background transparent
     * <p>
     * Warning : Works only for Java 7+ (but just doesn't do anything with Java 6 or less)
     */
    public void setTransparent()
    {
        this.setBackground(new Color(0, 0, 0, 0));
        this.getContentPane().setBackground(new Color(0, 0, 0, 0));
    }

    /**
     * Return the content panel
     *
     * @return The current SplashPanel instance
     */
    @Override
    public SplashPanel getContentPane()
    {
        return (SplashPanel)super.getContentPane();
    }

}

/**
 * The Splash Panel
 *
 * <p>
 * The container of a SplashScreen with an image in background
 * </p>
 *
 * @author Litarvan
 * @version 3.0.2-BETA
 * @since 3.0.0-BETA
 */
class SplashPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	/**
     * The splash image
     */
    private final Image image;

    /**
     * Basic constructor
     *
     * @param image The splash image
     */
    public SplashPanel(Image image)
    {
        this.image = image;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }

}
