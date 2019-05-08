/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.lab.caesar.web;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pl.polsl.lab.caesar.web.model.CaesarModel;
import pl.polsl.lab.caesar.web.model.InvalidInputException;

/**
 * Tests the model
 *
 * @author Piotr Kapski
 * @version 5.0
 */
public class CaesarModelTest {

    CaesarModel instance;

    public CaesarModelTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        instance = new CaesarModel();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of encode method, of class CaesarModel.
     */
    @Test
    public void testEncode() throws Exception {
        String message = "abc";
        int key = 1;
        String expResult = "bcd";

        String result = instance.encode(message, key);
        assertEquals(expResult, result);
    }

    /**
     * Test of decode method, of class CaesarModel.
     */
    @Test
    public void testDecode() throws Exception {
        String message = "bcd";
        int key = 1;
        String expResult = "abc";
        String result = instance.decode(message, key);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Testing of Exceptions.
     */
    @Test
    public void testOfEncodingException() {
        try {
            instance.encode("abc5e", 1);
            fail("An exception should be thrown when the amount is non-positive");
        } catch (InvalidInputException e) {
        }
    }

    @Test
    public void testOfEncodingException2() {
        try {
            instance.encode("abcd", -1);
            fail("An exception should be thrown when the amount is non-positive");
        } catch (InvalidInputException e) {
        }
    }

    @Test
    public void testOfDecodingException() {
        try {
            instance.decode("abc5e", 1);
            fail("An exception should be thrown when the amount is non-positive");
        } catch (InvalidInputException e) {
        }
    }

    @Test
    public void testOfDecodingException2() {
        try {
            instance.decode("abcd", -1);
            fail("An exception should be thrown when the amount is non-positive");
        } catch (InvalidInputException e) {
        }
    }

    /**
     * Testing msg=null
     *
     * @throws InvalidInputException
     */
    @Test(expected = InvalidInputException.class)
    public void TestNullEncode() throws InvalidInputException {
        String msg = null;
        instance.encode(msg, 5);
    }

    @Test(expected = InvalidInputException.class)
    public void TestNullDecode() throws InvalidInputException {
        String msg = null;
        instance.decode(msg, 5);
    }

    /**
     * Testing of key=0 and message=""
     *
     * @throws Exception
     */
    @Test
    public void testEncodeZero() throws Exception {
        String msg = "abcd";
        int key = 0;
        String expectedString = "abcd";
        String result = instance.encode(msg, key);
        assertEquals(expectedString, result);
        msg = "";
        key = 5;
        expectedString = "";
        result = instance.encode(msg, key);
        assertEquals(expectedString, result);

    }

    @Test
    public void testDecodeZero() throws Exception {
        String msg = "abcd";
        int key = 0;
        String expectedString = "abcd";
        String result = instance.decode(msg, key);
        assertEquals(expectedString, result);
        msg = "";
        key = 5;
        expectedString = "";
        result = instance.decode(msg, key);
        assertEquals(expectedString, result);
    }

    /**
     * Checking passing by the ends of alphabet
     *
     * @throws Exception
     */
    @Test
    public void testEncodeSpecific() throws Exception {
        String msg = "xyz";
        int key = 3;
        String expectedString = "abc";
        String result = instance.encode(msg, key);
        assertEquals(expectedString, result);
    }

    @Test
    public void testDecodeSpecific() throws Exception {
        String msg = "abc";
        int key = 3;
        String expectedString = "xyz";
        String result = instance.decode(msg, key);
        assertEquals(expectedString, result);
    }
}
