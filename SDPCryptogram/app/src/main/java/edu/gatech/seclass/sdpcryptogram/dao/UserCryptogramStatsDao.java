package edu.gatech.seclass.sdpcryptogram.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserCryptogramStatsDao {

    @Query("select * from (\n" +
            "select * from player_table a inner join cryptogram_table b where a.p_username = :username ) c \n" +
            "left join statistic_table d on c.c_puzzlename = d.s_puzzlename and c.p_username = d.s_username \n" +
            "where d.completedate is null")
    LiveData<List<UserCryptogramStats>> getUnsolvedCryptograms(String username);

    @Query("select * from (\n" +
            "select * from player_table a inner join cryptogram_table b where a.p_username = :username ) c \n" +
            "inner join statistic_table d on c.c_puzzlename = d.s_puzzlename and c.p_username = d.s_username \n" +
            "where d.completedate is not null \n" +
            "order by d.completedate asc")
    LiveData<List<UserCryptogramStats>> getCompletedCryptograms(String username);

    @Query("select s.s_puzzlename, s.s_username, s.solved, s.completedate, p.createdate from statistic_table s left join cryptogram_table p on s.s_puzzlename = p.c_puzzlename where s.s_puzzlename=:puzzlename and s.solved=:solved order by s.completedate asc")
    LiveData<List<UserCryptogramStats>> getCryptogramStatistics(String puzzlename, Boolean solved);
}
