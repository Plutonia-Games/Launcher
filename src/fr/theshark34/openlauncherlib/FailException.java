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
package fr.theshark34.openlauncherlib;

/**
 * The Fail Exception
 *
 * <p>
 * Best exception ever made
 * </p>
 *
 * @author Litarvan
 * @version 3.0.2-BETA
 * @since 3.0.0-BETA
 */
public class FailException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	/**
     * Normal constructor
     *
     * @param message The message
     */
    public FailException(String message)
    {
        super("Ups ! Looks like you failed : " + message);
    }

    /**
     * Constructor with a cause
     *
     * @param message The message
     * @param cause   The cause
     */
    public FailException(String message, Throwable cause)
    {
        super("Ups ! Looks like you failed : " + message, cause);
    }
}
