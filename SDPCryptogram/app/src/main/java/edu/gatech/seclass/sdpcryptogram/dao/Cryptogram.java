package edu.gatech.seclass.sdpcryptogram.dao;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.gatech.seclass.sdpcryptogram.utility.Constants;

@Entity(tableName = Constants.CRYPTOGRAM_TABLE)
public class Cryptogram {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = Constants.CRYPTOGRAM_PUZZLENAME_COLUMN)
    private String puzzlename;

    @NonNull
    @ColumnInfo(name = Constants.CRYPTOGRAM_PHRASE_COLUMN)
    private String phrase;

    @NonNull
    @ColumnInfo(name = Constants.CRYPTOGRAM_ENCRYPTED_COLUMN)
    private String encrypted;

    @NonNull
    @ColumnInfo(name = Constants.CRYPTOGRAM_ALLOWED_COLUMN)
    private Integer allowed;

    @NonNull
    @ColumnInfo(name = Constants.CRYPTOGRAM_ALPHABETS_COLUMN)
    private String alphabets;

    @NonNull
    @ColumnInfo(name = Constants.CRYPTOGRAM_CIPHER_COLUMN)
    private String cipher;

    @ColumnInfo(name = Constants.CRYPTOGRAM_CREATEDATE_COLUMN)
    private Long createdate;

    public Cryptogram(String puzzlename, String phrase, String encrypted, Integer allowed, String alphabets, String cipher, Long createdate) {
        this.puzzlename = puzzlename;
        this.phrase = phrase;
        this.encrypted = encrypted;
        this.allowed = allowed;
        this.alphabets = alphabets;
        this.cipher = cipher;
        this.createdate = createdate;
    }

    public String getPuzzlename() {
        return this.puzzlename;
    }

    public String getPhrase() {
        return this.phrase;
    }

    public String getEncrypted() {
        return this.encrypted;
    }

    public Integer getAllowed() {
        return this.allowed;
    }

    public String getAlphabets() {
        return alphabets;
    }

    public String getCipher() {
        return this.cipher;
    }

    public Long getCreatedate() {
        return createdate;
    }

    public String getCreateDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        if (this.createdate != null) {
            Date date = new Date(this.createdate);
            return (dateFormat.format(date));
        }
        return "";
    }
}
