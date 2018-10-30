package edu.gatech.seclass.sdpcryptogram.dao;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.gatech.seclass.sdpcryptogram.utility.Constants;

@Entity(tableName = Constants.STATISTIC_TABLE, indices = {@Index(value = {Constants.STATISTIC_USERNAME_COLUMN, Constants.STATISTIC_PUZZLENAME_COLUMN}, unique = true)})
public class Statistic {

    @PrimaryKey(autoGenerate = true)
    public int _id;

    @NonNull
    @ColumnInfo(name = Constants.STATISTIC_USERNAME_COLUMN)
    private String username;

    @NonNull
    @ColumnInfo(name = Constants.STATISTIC_PUZZLENAME_COLUMN)
    private String puzzlename;

    @NonNull
    @ColumnInfo(name = Constants.STATISTIC_ATTEMPTS_COLUMN)
    private Integer attempts;

    @NonNull
    @ColumnInfo(name = Constants.STATISTIC_SOLVED_COLUMN)
    private Boolean solved;

    @ColumnInfo(name = Constants.STATISTIC_COMPLETEDATE_COLUMN)
    private Long completedate;

    public Statistic(String username, String puzzlename, Integer attempts, Boolean solved, Long completedate) {
        this.username = username;
        this.puzzlename = puzzlename;
        this.attempts = attempts;
        this.solved = solved;
        this.completedate = completedate;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPuzzlename() {
        return this.puzzlename;
    }

    public Integer getAttempts() {
        return this.attempts;
    }

    public void setAttempts(@NonNull Integer attempts) {
        this.attempts = attempts;
    }

    public Boolean getSolved() {
        return this.solved;
    }

    public void setSolved(@NonNull Boolean solved) {
        this.solved = solved;
    }

    public Long getCompletedate() {
        return this.completedate;
    }

    public void setCompletedate(Long completedate) {
        this.completedate = completedate;
    }

    public String getCompletedateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        if (this.completedate != null) {
            Date date = new Date(this.completedate);
            return (dateFormat.format(date));
        }
        return "";
    }
}
