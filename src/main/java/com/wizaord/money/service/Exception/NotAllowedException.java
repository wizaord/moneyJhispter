package com.wizaord.money.service.Exception;

/**
 * This class is raised when a security exception is occured.
 */
public class NotAllowedException extends Exception {

    /**
     * Default constructor
     * @param message : the message detail
     */
    public NotAllowedException(String message) {
        super(message);
    }
}
