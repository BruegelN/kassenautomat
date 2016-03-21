package de.dhbw.kassenautomat;

import org.junit.Test;

import java.lang.Exception;

import de.dhbw.kassenautomat.TestingDemo;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    TestingDemo myObject = new TestingDemo();

    @Test
    public void fourIsRandom(){
        assertTrue(myObject.getRandomNumber() == 4);
    }


    @Test (expected = Exception.class)
    public void isVeryEvil() throws Exception
    {
        myObject.throwAnEvilExcepttion();
    }
}