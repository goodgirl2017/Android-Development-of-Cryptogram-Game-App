package edu.gatech.seclass.sdpcryptogram.dao;

import android.arch.persistence.room.Embedded;

public class UserCryptogramStats {

    @Embedded
    Player player;

    @Embedded
    Cryptogram cryptogram;

    @Embedded
    Statistic statistic;

    public UserCryptogramStats(Player player, Cryptogram cryptogram, Statistic statistic) {
        this.player = player;
        this.cryptogram = cryptogram;
        this.statistic = statistic;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Cryptogram getCryptogram() {
        return cryptogram;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public boolean isSuccessful(){
        if (this.getStatistic().getSolved() && this.getStatistic().getAttempts() <= this.getCryptogram().getAllowed()){
            return true;
        }else if (this.getStatistic().getSolved() && this.getStatistic().getAttempts() > this.getCryptogram().getAllowed()){
            return false;
        }
        return false;
    }
}
