/*
 * Created on 17 oct. 2004
 * 
 * EasyMolException.java - Standard Exception class for EasyMol errors
 * Copyright (c) 2004 Alexandre Vaughan
 * avaughan@altern.org
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package net.sf.easymol.core;

/**
 * The standard exception class for throwing EasyMol run-time errors.
 * <p>
 * This class is used by every EasyMol class to send EasyMol-related exceptions.
 * These exceptions are defined by an error code number, the name (string) of
 * the module/class that issued the exception, and the exception message.
 * </p>
 * 
 * @author Alexandre Vaughan
 *  
 */
public class EasyMolException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String module = ""; // the sender of this exception

    private String errorText = ""; // the error message

    private int errorCode = 0; // the error code

    /**
     * Creates a new EasyMol exception
     * 
     * @param code
     *            Error code of the exception
     * @param module
     *            The sender of the exception
     * @param message
     *            The message of the exception
     */
    public EasyMolException(int code, String module, String message) {
        this.module = module;
        this.errorText = message;
        this.errorCode = code;
    }

    /**
     * Gets the fully filled (with all attributes) message of this easymol
     * exception.
     */
    public String getMessage() {
        return "EM-" + errorCode + " : " + module + " => " + errorText;
    }

    /**
     * 
     * @return
     */
    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}