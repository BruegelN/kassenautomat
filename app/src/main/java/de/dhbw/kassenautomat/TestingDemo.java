package de.dhbw.kassenautomat;

/**
 * Created by nicob on 21.03.16.
 */
public class TestingDemo {

    public int getRandomNumber()
    {
        return 4; // thanks to xkcd for the advice
    }

    public void throwAnEvilExcepttion() throws Exception
    {
        int truth = 42;
        truth/=2;
        truth*=2;

        throw new Exception("I'm a evil exception.");
    }


}
