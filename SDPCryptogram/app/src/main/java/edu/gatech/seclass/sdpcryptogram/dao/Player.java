package edu.gatech.seclass.sdpcryptogram.dao;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import edu.gatech.seclass.sdpcryptogram.utility.Constants;

@Entity(tableName = Constants.PLAYER_TABLE)
public class Player {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = Constants.PLAYER_USERNAME_COLUMN)
    private String username;

    @NonNull
    @ColumnInfo(name = Constants.PLAYER_FIRSTNAME_COLUMN)
    private String firstname;

    @NonNull
    @ColumnInfo(name = Constants.PLAYER_LASTNAME_COLUMN)
    private String lastname;

    @NonNull
    @ColumnInfo(name = Constants.PLAYER_EMAIL_COLUMN)
    private String email;

    public Player(String username, String firstname, String lastname, String email) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public String getEmail() {
        return this.email;
    }
}
