/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.lab.caesarcipher.model ;

/**
 * Class that implements the solution for Caesar Cipher.
 *
 * @author Piotr Kapski
 * @version 5.0
 */
public class CaesarModel {

    /**
     * Number of letters that are possible to encode/decode
     */
    private final int NUMBER_OF_LETTERS = 'z' - 'a' + 1;
    /**
     * ASCII number of first letter in alphabet
     */
    private final int FIRST_LETTER = 'a';

    /**
     * Encodes the given message with given key using Caesar Cipher.
     *
     * @param message - the sentence an user wants to encode
     * @param key - number of positions that each number will be shifted down
     * the alphabet
     * @return String containing encoded message
     * @throws InvalidInputException when illegal characters are used
     */
    public String encode(String message, int key) throws InvalidInputException {

        if (message == null) {
            throw new InvalidInputException("Illegal message value");
        }
        if (key < 0) {
            throw new InvalidInputException("Illegal key value");
        }
        key %= NUMBER_OF_LETTERS;
        //Changes every capital letter lowercase
        message = message.toLowerCase();
        /**
         * result of encoding, will be returned at the end of the method
         */
        StringBuilder result = new StringBuilder(message);
        /**
         * An additional variable that will store a single char of the message
         */
        char buff;
        //Loop that goes through every single character of the string and changes it
        for (int i = 0; i < message.length(); i++) {
            buff = message.charAt(i);
            if (buff == 32) {
                continue;
            }
            //Checking if character is valid
            if (buff >= FIRST_LETTER && buff <= FIRST_LETTER + NUMBER_OF_LETTERS) {
                buff = (char) ((message.charAt(i) - FIRST_LETTER + key) % NUMBER_OF_LETTERS + FIRST_LETTER);
                result.setCharAt(i, buff);
            } else {
                throw new InvalidInputException("Illegal characters in message");
            }

        }
        return result.toString();
    }

    /**
     * Decodes the given message with given key using Caesar Cipher.
     *
     * @param message - the sentence an user wants to decode
     * @param key - number of positions that each number will be shifted up the
     * alphabet
     * @return String containing decoded message
     * @throws InvalidInputException when illegal characters are used
     */
    public String decode(String message, int key) throws InvalidInputException {

        if (message == null) {
            throw new InvalidInputException("Illegal message value");
        }
        if (key < 0) {
            throw new InvalidInputException("Illegal key value");
        }
        key %= NUMBER_OF_LETTERS;
        //Changes every capital letter lowercase
        message = message.toLowerCase();
        /**
         * result of decoding, will be returned at the end of the method
         */
        StringBuilder result = new StringBuilder(message);
        /**
         * An additional variable that will store a single char of the message
         */
        char buff;
        //Loop that goes through every single character of the string and changes it
        for (int i = 0; i < message.length(); i++) {
            buff = message.charAt(i);
            if (buff == 32) {
                continue;
            }
            //Checking if character is valid
            if (buff >= FIRST_LETTER && buff <= FIRST_LETTER + NUMBER_OF_LETTERS) {
                buff = (char) ((message.charAt(i) - FIRST_LETTER - key) % NUMBER_OF_LETTERS + FIRST_LETTER);
                if (buff < FIRST_LETTER) {
                    buff += NUMBER_OF_LETTERS;
                }
                result.setCharAt(i, buff);
            } else {
                throw new InvalidInputException("Illegal characters in message");
            }
        }
        return result.toString();
    }
}
