package org.apache.jackrabbit.j2ee;

import javax.servlet.ServletException;

public class ServletExceptionWithCause extends ServletException {
	
	 /**
     * Serial version UID
     */
    private static final long serialVersionUID = -7201954529718775444L;

    /**
     * Creates a servlet exception with the given message and cause.
     *
     * @param message exception message
     * @param cause cause of the exception
     */
    public ServletExceptionWithCause(String message, Throwable cause) {
        super(message, cause);
        if (getCause() == null) {
            initCause(cause);
        }
    }
	
}
