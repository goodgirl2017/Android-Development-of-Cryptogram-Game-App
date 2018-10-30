package edu.gatech.seclass.sdpcryptogram.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CryptogramDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cryptogram cryptogram);

    @Query("DELETE FROM cryptogram_table")
    void deleteAll();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Cryptogram cryptogram);

    @Query("SELECT * from cryptogram_table ORDER BY c_puzzlename ASC")
    LiveData<List<Cryptogram>> getAllCryptograms();

    @Query("SELECT * from cryptogram_table WHERE c_puzzlename=:puzzlename")
    LiveData<Cryptogram> getCryptogramDetails(String puzzlename);

    @Query("select\n" +
            "c.*\n" +
            "from\n" +
            "\tcryptogram_table c\n" +
            "inner join statistic_table s on\n" +
            "\tc.c_puzzlename = s.s_puzzlename\n" +
            "\tand s.solved = 1\n" +
            "\tWHERE 1=1 \n" +
            "\tAND c.c_puzzlename=:puzzlename \n" +
            "\torder by s.completedate asc;")
    LiveData<List<Cryptogram>> getCryptogramSolved(String puzzlename);

    @Query("select\n" +
            "s.s_puzzlename\n" +
            "from\n" +
            "\tcryptogram_table c\n" +
            "inner join statistic_table s on\n" +
            "\tc.c_puzzlename = s.s_puzzlename\n" +
            "\tand s.solved = 1\n" +
            "\tWHERE 1=1 \n" +
            "\tAND c.c_puzzlename=:puzzlename \n" +
            "\torder by s.completedate asc\n" +
            " limit :numPlayers;")
    LiveData<List<String>> getCryptogramPlayersSolved(String puzzlename, Integer numPlayers);

    @Query("SELECT * from cryptogram_table ORDER BY createdate ASC")
    LiveData<List<Cryptogram>> getAllCryptogramsByCreated();

    @Query("SELECT c_puzzlename from cryptogram_table ORDER BY c_puzzlename ASC")
    LiveData<List<String>> getAllCryptogramNames();
}
