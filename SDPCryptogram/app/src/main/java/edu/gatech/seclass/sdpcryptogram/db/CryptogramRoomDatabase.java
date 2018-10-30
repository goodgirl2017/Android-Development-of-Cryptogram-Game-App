package edu.gatech.seclass.sdpcryptogram.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.Date;

import edu.gatech.seclass.sdpcryptogram.dao.Cryptogram;
import edu.gatech.seclass.sdpcryptogram.dao.CryptogramDao;
import edu.gatech.seclass.sdpcryptogram.dao.Player;
import edu.gatech.seclass.sdpcryptogram.dao.PlayerDao;
import edu.gatech.seclass.sdpcryptogram.dao.Statistic;
import edu.gatech.seclass.sdpcryptogram.dao.StatisticDao;
import edu.gatech.seclass.sdpcryptogram.dao.UserCryptogramStatsDao;
import edu.gatech.seclass.sdpcryptogram.utility.Constants;

@Database(entities = {Player.class, Cryptogram.class, Statistic.class}, version = 1, exportSchema = false)
public abstract class CryptogramRoomDatabase extends RoomDatabase {

    public abstract PlayerDao playerDao();

    public abstract CryptogramDao cryptogramDao();

    public abstract StatisticDao statisticDao();

    public abstract UserCryptogramStatsDao userCryptogramStatsDao();

    private static CryptogramRoomDatabase INSTANCE;

    public static CryptogramRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CryptogramRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CryptogramRoomDatabase.class, Constants.CRYPTOGRAM_DB)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final PlayerDao playerDao;
        private final CryptogramDao cryptogramDao;
        private final StatisticDao statisticDao;
        private final UserCryptogramStatsDao userCryptogramStatsDao;

        PopulateDbAsync(CryptogramRoomDatabase db) {
            playerDao = db.playerDao();
            cryptogramDao = db.cryptogramDao();
            statisticDao = db.statisticDao();
            userCryptogramStatsDao = db.userCryptogramStatsDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // TODO: This code block needs to be commented out before final application submission.
            // TODO: This is only for testing during development
            //playerDao.deleteAll();
            //cryptogramDao.deleteAll();
            //statisticDao.deleteAll();
            Player player = new Player("defaultuser1", "Default1", "User1", "defaultuser1@domain.com");
            playerDao.insert(player);
            player = new Player("defaultuser2", "Default2", "User2", "defaultuser2@domain.com");
            playerDao.insert(player);
            player = new Player("defaultuser3", "Default3", "User3", "defaultuser3@domain.com");
            playerDao.insert(player);
            player = new Player("defaultuser4", "Default4", "User4", "defaultuser4@domain.com");
            playerDao.insert(player);
            Cryptogram cryptogram = new Cryptogram("defaultpuzzle1", "ABC1", "DEF1", 5, "ABC", "ABC1", (new Date()).getTime());
            cryptogramDao.insert(cryptogram);
            cryptogram = new Cryptogram("defaultpuzzle2", "ABC2", "DEF2", 5, "ABC", "ABC2", (new Date()).getTime());
            cryptogramDao.insert(cryptogram);
            cryptogram = new Cryptogram("defaultpuzzle3", "ABC3", "DEF3", 5, "ABC", "ABC3", (new Date()).getTime());
            cryptogramDao.insert(cryptogram);
            Statistic statistic = new Statistic("defaultuser1", "defaultpuzzle3", 5, true, (new Date()).getTime());
            statisticDao.insert(statistic);
            statistic = new Statistic("defaultuser2", "defaultpuzzle2", 5, false, (new Date()).getTime());
            statisticDao.insert(statistic);
            statistic = new Statistic("defaultuser2", "defaultpuzzle3", 2, false, null);
            statisticDao.insert(statistic);
            return null;
        }
    }
}
