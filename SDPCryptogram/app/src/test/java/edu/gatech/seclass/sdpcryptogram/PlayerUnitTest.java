package edu.gatech.seclass.sdpcryptogram;

import org.junit.Assert;
import org.junit.Test;

import edu.gatech.seclass.sdpcryptogram.dao.Player;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PlayerUnitTest {

    // test registration of a new player
    @Test
    public void createPlayerValidation() {
        Player newPlayer = new Player("nancy", "min", "ni", "mni37@gatech.edu");
        Assert.assertEquals(newPlayer.getUsername(), "nancy");
        Assert.assertEquals(newPlayer.getFirstname(), "min");
        Assert.assertEquals(newPlayer.getLastname(), "ni");
        Assert.assertEquals(newPlayer.getEmail(), "mni37@gatech.edu");
    }
}