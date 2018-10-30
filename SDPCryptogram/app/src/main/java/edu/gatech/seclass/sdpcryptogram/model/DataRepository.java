package edu.gatech.seclass.sdpcryptogram.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

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

public class DataRepository {

    private PlayerDao mPlayerDao;
    private CryptogramDao mCryptogramDao;
    private StatisticDao mStatisticDao;
    private UserCryptogramStatsDao mUserCryptogramStatsDao;

    private LiveData<List<Player>> mAllPlayers;
    private LiveData<List<Cryptogram>> mAllCryptograms;
    private LiveData<List<Statistic>> mAllStatistics;

    DataRepository(Application application) {
        CryptogramRoomDatabase db = CryptogramRoomDatabase.getDatabase(application);
        mPlayerDao = db.playerDao();
        mCryptogramDao = db.cryptogramDao();
        mStatisticDao = db.statisticDao();
        mUserCryptogramStatsDao = db.userCryptogramStatsDao();
        mAllPlayers = mPlayerDao.getAllPlayers();
        mAllCryptograms = mCryptogramDao.getAllCryptograms();
        mAllStatistics = mStatisticDao.getAllStatistics();
    }

    LiveData<List<Player>> getAllPlayers() {
        return mAllPlayers;
    }

    LiveData<Integer> getPlayerCount(String username) {
        return mPlayerDao.getPlayerCount(username);
    }

    public LiveData<Player> getPlayer(String username) { return mPlayerDao.getPlayer(username); }

    LiveData<List<Statistic>> getUserPuzzles(String username, Boolean solved) {
        return mStatisticDao.getUserPuzzles(username, solved);
    }

    public LiveData<List<String>> getAllPlayerNames() {
        return mPlayerDao.getAllPlayerNames();
    }

    LiveData<List<Cryptogram>> getAllCryptograms() {
        return mAllCryptograms;
    }

    public LiveData<List<Cryptogram>> getAllCryptogramsByCreated() { return mCryptogramDao.getAllCryptogramsByCreated(); }

    public LiveData<Cryptogram> getCryptogramDetails(String puzzlename) { return mCryptogramDao.getCryptogramDetails(puzzlename); }

    public LiveData<List<String>> getAllCryptogramNames() { return mCryptogramDao.getAllCryptogramNames(); }

    public LiveData<List<String>> getCryptogramPlayersSolved(String puzzlename, Integer numPlayers) { return mCryptogramDao.getCryptogramPlayersSolved(puzzlename, numPlayers); }

    public LiveData<List<Cryptogram>> getCryptogramSolved(String puzzleName) { return mCryptogramDao.getCryptogramSolved(puzzleName); }

    LiveData<List<Statistic>> getAllStatistics() {
        return mAllStatistics;
    }

    public LiveData<Statistic> getStatisticByUserPuzzle(String username, String puzzlename) { return mStatisticDao.getStatisticByUserPuzzle(username, puzzlename); }

    public LiveData<List<UserCryptogramStats>> getUnsolvedCryptograms(String username) { return mUserCryptogramStatsDao.getUnsolvedCryptograms(username); }

    public LiveData<List<UserCryptogramStats>> getCompletedCryptograms(String username) { return mUserCryptogramStatsDao.getCompletedCryptograms(username); }

    public LiveData<List<UserCryptogramStats>> getCryptogramStatistics(String puzzlename) { return mUserCryptogramStatsDao.getCryptogramStatistics(puzzlename, true); }

    public void insert (Player player) {
        new insertPlayerAsyncTask(mPlayerDao).execute(player);
    }

    public LiveData<List<Statistic>> getUserPuzzlesArray(String username, Boolean solved) {return mStatisticDao.getUserPuzzlesArray(username,solved);}

    public void insert (Cryptogram cryptogram) {
        new insertCryptogramAsyncTask(mCryptogramDao).execute(cryptogram);
    }

    public void insert (Statistic statistic) {
        new insertStatisticAsyncTask(mStatisticDao).execute(statistic);
    }

    private static class insertPlayerAsyncTask extends AsyncTask<Player, Void, Void> {

        private PlayerDao mAsyncTaskDao;

        insertPlayerAsyncTask(PlayerDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Player... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class insertCryptogramAsyncTask extends AsyncTask<Cryptogram, Void, Void> {

        private CryptogramDao mAsyncTaskDao;

        insertCryptogramAsyncTask(CryptogramDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Cryptogram... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class insertStatisticAsyncTask extends AsyncTask<Statistic, Void, Void> {

        private StatisticDao mAsyncTaskDao;

        insertStatisticAsyncTask(StatisticDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Statistic... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
