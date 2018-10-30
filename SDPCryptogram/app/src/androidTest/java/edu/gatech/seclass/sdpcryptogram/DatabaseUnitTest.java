package edu.gatech.seclass.sdpcryptogram;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import edu.gatech.seclass.sdpcryptogram.dao.Cryptogram;
import edu.gatech.seclass.sdpcryptogram.dao.CryptogramDao;
import edu.gatech.seclass.sdpcryptogram.dao.Player;
import edu.gatech.seclass.sdpcryptogram.dao.PlayerDao;
import edu.gatech.seclass.sdpcryptogram.dao.Statistic;
import edu.gatech.seclass.sdpcryptogram.dao.StatisticDao;
import edu.gatech.seclass.sdpcryptogram.dao.UserCryptogramStats;
import edu.gatech.seclass.sdpcryptogram.dao.UserCryptogramStatsDao;
import edu.gatech.seclass.sdpcryptogram.db.CryptogramRoomDatabase;
import edu.gatech.seclass.sdpcryptogram.model.DataViewModel;
import edu.gatech.seclass.sdpcryptogram.utility.CommonUtils;


// test database
@RunWith(AndroidJUnit4.class)
public class DatabaseUnitTest {
    private PlayerDao mPlayerDao;
    private CryptogramRoomDatabase mDb;
    private CryptogramDao mCryptogramDao;
    private Player outputPlayer;
    private Cryptogram outputCryptogram;
    private UserCryptogramStatsDao mUserCryptogramStatsDao;
    private StatisticDao mStatisticDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, CryptogramRoomDatabase.class).build();
        mPlayerDao = mDb.playerDao();
        mCryptogramDao = mDb.cryptogramDao();
        mUserCryptogramStatsDao = mDb.userCryptogramStatsDao();
        mStatisticDao = mDb.statisticDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    // test creating a new Player, inserting a new Player into database and select a Player
    @Test
    public void insertPlayer() throws Exception {
        Player currentPlayer = new Player("nancy", "min", "ni", "mni37@gatech.edu");

        mPlayerDao.insert(currentPlayer);
        LiveData<Player> queryResult = mPlayerDao.getPlayer("nancy");

        outputPlayer = CommonUtils.getValue(queryResult);
        Assert.assertEquals(outputPlayer.getUsername(), currentPlayer.getUsername());
        Assert.assertEquals(outputPlayer.getEmail(), currentPlayer.getEmail());
        Assert.assertEquals(outputPlayer.getFirstname(), currentPlayer.getFirstname());
        Assert.assertEquals(outputPlayer.getLastname(), currentPlayer.getLastname());
    }

    // test creating Cyptogram, insert into database and choose a Cryptogram
    @Test
    public void createCryptogramTest() throws Exception {
        Cryptogram cryptogram = new Cryptogram("testInsert1", "Abc! D", "Cde! F", 2, "ABCD", "CDEF", (new Date()).getTime());
        mCryptogramDao.insert(cryptogram);
        LiveData<Cryptogram> queryResult = mCryptogramDao.getCryptogramDetails("testInsert1");

        outputCryptogram = CommonUtils.getValue(queryResult);
        Assert.assertEquals(outputCryptogram.getPuzzlename(), cryptogram.getPuzzlename());
        Assert.assertEquals(outputCryptogram.getPhrase(), cryptogram.getPhrase());
        Assert.assertEquals(outputCryptogram.getEncrypted(), cryptogram.getEncrypted());
        Assert.assertEquals(outputCryptogram.getAllowed(), cryptogram.getAllowed());
        Assert.assertEquals(outputCryptogram.getAlphabets(), cryptogram.getAlphabets());
        Assert.assertEquals(outputCryptogram.getCipher(), cryptogram.getCipher());
        Assert.assertEquals(outputCryptogram.getCreatedate(), cryptogram.getCreatedate());
    }

    //test Get Cyptogram Statistics List
    // after inserting a new Cryptogram, there should be one more element to the Cyptogram Statistics List
    @Test
    public void cryptogramStatisticListTest() throws Exception {
        LiveData<List<Cryptogram>> originalData = mCryptogramDao.getAllCryptogramsByCreated();
        List<Cryptogram> originalList = CommonUtils.getValue(originalData);
        int originalSize = originalList.size();

        Cryptogram cryptogram = new Cryptogram("testInsert2", "Abc! D", "Cde! F", 2, "ABCD", "CDEF", (new Date()).getTime());
        mCryptogramDao.insert(cryptogram);

        LiveData<List<Cryptogram>> newData = mCryptogramDao.getAllCryptogramsByCreated();
        List<Cryptogram> newList = CommonUtils.getValue(newData);
        int newSize = newList.size();
        Assert.assertEquals(1, newSize - originalSize);
    }

    //test Get Cyptogram Statistics Details
    @Test
    public void getCryptogramStatisticTest() throws Exception {
        Cryptogram cryptogram = new Cryptogram("testInsert2", "Abc! D", "Cde! F", 2, "ABCD", "CDEF", (new Date()).getTime());
        mCryptogramDao.insert(cryptogram);

        LiveData<List<UserCryptogramStats>> statisticsData = mUserCryptogramStatsDao.getCryptogramStatistics("testInsert2", true);
        List<UserCryptogramStats> statistics = CommonUtils.getValue(statisticsData);
        Assert.assertEquals(0, statistics.size());  // Nobody has solved the new Test

    }

    // test completed Cyptogram List and also test completing a cyptogram for a specific user
    // after inserting a new Cryptogram and setting it to completed, there should be one more element to the Cyptogram Statistics List
    @Test
    public void completedCryptogramListTest() throws Exception {
        // insert a new username to make sure this username exists for this test case
        Player currentPlayer = new Player("nancy", "min", "ni", "mni37@gatech.edu");

        mPlayerDao.insert(currentPlayer);
        LiveData<List<UserCryptogramStats>> originalData = mUserCryptogramStatsDao.getCompletedCryptograms("nancy");
        List<UserCryptogramStats> originalList = CommonUtils.getValue(originalData);
        int originalSize = originalList.size(); // now it should be 0
        // for a new username, the completed Cyptogram List size should be zero
        Assert.assertEquals(0, originalSize);

        // test completing a cyptogram for specific username

        //insert a new cyptogram first for solving
        Cryptogram cryptogram = new Cryptogram("testInsert3", "Abc! D", "Cde! F", 1, "ABCD", "CDEF", (new Date()).getTime());
        mCryptogramDao.insert(cryptogram);

        // //Solve the puzzle

        // insert a statistic to the database
        Statistic statistic = new Statistic("nancy", "testInsert3", 0, false, null);
        mStatisticDao.insert(statistic);

        LiveData<Statistic> statisticsData = mStatisticDao.getStatisticByUserPuzzle("nancy", "testInsert3");
        Statistic outputStatistics = CommonUtils.getValue(statisticsData);

        //test Statistics
        Assert.assertEquals(outputStatistics.getPuzzlename(), statistic.getPuzzlename());
        Assert.assertEquals(outputStatistics.getUsername(), statistic.getUsername());
        Assert.assertEquals(outputStatistics.getAttempts(), statistic.getAttempts());
        Assert.assertEquals(outputStatistics.getSolved(), statistic.getSolved());
        Assert.assertEquals(outputStatistics.getCompletedate(), statistic.getCompletedate());

        //set it solved
        statistic.setSolved(true);
        statistic.setAttempts(1);
        statistic.setCompletedate((new Date()).getTime());
        mStatisticDao.insert(statistic);

        //test whether the completed list is increased or not.
        LiveData<List<UserCryptogramStats>> newData = mUserCryptogramStatsDao.getCompletedCryptograms("nancy");
        List<UserCryptogramStats> newList = CommonUtils.getValue(newData);
        int newSize = newList.size();
        Assert.assertEquals(1, newSize - originalSize);
    }


    // test Unsolved CryptogramList
    @Test
    public void unsolvedCryptogramListTest() throws Exception {
        // insert a new username to make sure this username exists for this test case
        Player currentPlayer = new Player("nancy", "min", "ni", "mni37@gatech.edu");
        mPlayerDao.insert(currentPlayer);

        LiveData<List<UserCryptogramStats>> originalData = mUserCryptogramStatsDao.getUnsolvedCryptograms("nancy");
        List<UserCryptogramStats> originalList = CommonUtils.getValue(originalData);
        int originalSize = originalList.size(); // now it should be 0

        // test increasing of unsolved cryptogram after adding another cyptogram

        Cryptogram cryptogram = new Cryptogram("testInsert3", "Abc! D", "Cde! F", 1, "ABCD", "CDEF", (new Date()).getTime());
        mCryptogramDao.insert(cryptogram);

        LiveData<List<UserCryptogramStats>> newData = mUserCryptogramStatsDao.getUnsolvedCryptograms("nancy");
        List<UserCryptogramStats> newList = CommonUtils.getValue(newData);
        int newSize = newList.size();
        Assert.assertEquals(1, newSize - originalSize);
    }

}