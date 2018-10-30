package edu.gatech.seclass.sdpcryptogram.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Player player);

    @Query("DELETE FROM player_table")
    void deleteAll();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Player player);

    @Query("SELECT * from player_table ORDER BY p_username ASC")
    LiveData<List<Player>> getAllPlayers();

    @Query("SELECT COUNT(*) from player_table WHERE p_username = :username")
    LiveData<Integer> getPlayerCount(String username);

    @Query("SELECT * from player_table WHERE p_username = :username")
    LiveData<Player> getPlayer(String username);

    @Query("SELECT p_username from player_table")
    LiveData<List<String>> getAllPlayerNames();
}

