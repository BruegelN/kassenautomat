package de.dhbw.kassenautomat;

/**
 * Created by nicob on 21.03.16.
 *
 * Just to show the possibilities of unit testing with JUnit.
 */
public class TestingDemo {

    /**
     * Will return a completely random number!
     * @return 4
     */
    public int getRandomNumber()
    {
        return 4; // thanks to xkcd for the advice
    }

    /**
     * Just to test exception handling as well.
     *
     * @throws Exception
     */
    public void throwAnEvilExcepttion() throws Exception
    {
        int truth = 42;
        truth/=2;
        truth*=2;

        throw new Exception("I'm a evil exception.");
    }


}
