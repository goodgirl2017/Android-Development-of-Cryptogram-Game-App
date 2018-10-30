package edu.gatech.seclass.sdpcryptogram.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.gatech.seclass.sdpcryptogram.dao.Cryptogram;
import edu.gatech.seclass.sdpcryptogram.dao.Player;
import edu.gatech.seclass.sdpcryptogram.dao.Statistic;
import edu.gatech.seclass.sdpcryptogram.dao.UserCryptogramStats;

public class DataViewModel extends AndroidViewModel {

    private DataRepository mRepository;

    private LiveData<List<Player>> mAllPlayers;
    private LiveData<List<Cryptogram>> mAllCryptograms;
    private LiveData<List<Statistic>> mAllStatistics;

    public DataViewModel(Application application) {
        super(application);
        mRepository = new DataRepository(application);
        mAllPlayers = mRepository.getAllPlayers();
        mAllCryptograms = mRepository.getAllCryptograms();
        mAllStatistics = mRepository.getAllStatistics();
    }

    public LiveData<List<Player>> getAllPlayers() { return mAllPlayers; }

    public LiveData<Integer> getPlayerCount(String username) { return mRepository.getPlayerCount(username); }

    public LiveData<Player> getPlayer(String username) { return mRepository.getPlayer(username); }

    public LiveData<List<String>> getAllPlayerNames() { return mRepository.getAllPlayerNames(); }

    public LiveData<List<Cryptogram>> getAllCryptograms() { return mAllCryptograms; }

    public LiveData<Cryptogram> getCryptogramDetails(String puzzlename) { return mRepository.getCryptogramDetails(puzzlename); }

    public LiveData<List<Cryptogram>> getAllCryptogramsByCreated() { return mRepository.getAllCryptogramsByCreated(); }

    public LiveData<List<String>> getAllCryptogramNames() { return mRepository.getAllCryptogramNames(); }

    public LiveData<List<Statistic>> getAllStatistics() { return mAllStatistics; }

    public LiveData<Statistic> getStatisticByUserPuzzle(String username, String puzzlename) { return mRepository.getStatisticByUserPuzzle(username, puzzlename); }

    public LiveData<List<Statistic>>  getUserPuzzles(String username, Boolean solved) { return mRepository.getUserPuzzles(username, solved); }

    public LiveData<List<UserCryptogramStats>> getUnsolvedCryptograms(String username) { return mRepository.getUnsolvedCryptograms(username); }

    public LiveData<List<Statistic>> getUserPuzzlesArray(String username, boolean solved) {return mRepository.getUserPuzzlesArray(username,solved);}

    public LiveData<List<Cryptogram>> getCryptogramSolved(String puzzleName) { return mRepository.getCryptogramSolved(puzzleName); }

    public LiveData<List<String>> getAllCryptogramPlayersSolved(String puzzlename, Integer numPlayers) { return mRepository.getCryptogramPlayersSolved(puzzlename, numPlayers); }

    public LiveData<List<UserCryptogramStats>> getCompletedCryptograms(String username) { return mRepository.getCompletedCryptograms(username); }

    public LiveData<List<UserCryptogramStats>> getCryptogramStatistics(String puzzlename) { return mRepository.getCryptogramStatistics(puzzlename); }

    public void insert(Player player) { mRepository.insert(player); }

    public void insert(Cryptogram cryptogram) { mRepository.insert(cryptogram); }

    public void insert(Statistic statistic) { mRepository.insert(statistic); }
}
