/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.lab.caesar.web.model;

/**
 * Exception thrown when user enters invalid symbols
 *
 * @author Piotr Kapski
 * @version 5.0
 */
public class InvalidInputException extends Exception {

    /**
     * Constructor that changes the default Message.
     *
     * @param msg - message of the exception
     */
    public InvalidInputException(String msg) {
        //Constructor of the Exception class.
        super(msg);
    }

}
