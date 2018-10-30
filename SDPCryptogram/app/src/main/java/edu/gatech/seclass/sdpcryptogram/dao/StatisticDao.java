package edu.gatech.seclass.sdpcryptogram.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

@Dao
public interface StatisticDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Statistic statistic);

    @Query("DELETE FROM statistic_table")
    void deleteAll();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Statistic statistic);

    @Query("SELECT * from statistic_table ORDER BY s_username ASC, s_puzzlename ASC")
    LiveData<List<Statistic>> getAllStatistics();

    @Query("SELECT * from statistic_table WHERE s_username=:username AND s_puzzlename=:puzzlename")
    LiveData<Statistic> getStatisticByUserPuzzle(String username, String puzzlename);

    @Query("SELECT * FROM statistic_table WHERE s_username LIKE :name AND solved = :solved")
    LiveData<List<Statistic>> getUserPuzzles(String name, Boolean solved);

    @Query("SELECT * FROM statistic_table WHERE s_username LIKE :name AND solved = :solved")
    LiveData<List<Statistic>> getUserPuzzlesArray(String name, Boolean solved);
}
