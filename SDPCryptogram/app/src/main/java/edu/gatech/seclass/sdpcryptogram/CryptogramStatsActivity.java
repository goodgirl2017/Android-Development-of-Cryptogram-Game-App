package edu.gatech.seclass.sdpcryptogram;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.util.StringUtil;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.gatech.seclass.sdpcryptogram.adapter.CryptogramAdapter;
import edu.gatech.seclass.sdpcryptogram.adapter.CryptogramDetailAdapter;
import edu.gatech.seclass.sdpcryptogram.adapter.UserCompletedCryptogramAdapter;
import edu.gatech.seclass.sdpcryptogram.adapter.UserUnSolvedCryptogramAdapter;
import edu.gatech.seclass.sdpcryptogram.dao.Cryptogram;
import edu.gatech.seclass.sdpcryptogram.dao.UserCryptogramStats;
import edu.gatech.seclass.sdpcryptogram.model.DataViewModel;
import edu.gatech.seclass.sdpcryptogram.utility.CommonUtils;
import edu.gatech.seclass.sdpcryptogram.utility.Constants;

public class CryptogramStatsActivity extends AppCompatActivity {


    private DataViewModel mDataViewModel;

    private String currentUser;
    private String statsMode;
    private String thePuzzle;
    private String thePuzzleCreated;
    private UserCompletedCryptogramAdapter completedCryptogramAdapter;
    private UserUnSolvedCryptogramAdapter unSolvedCryptogramAdapter;
    private CryptogramAdapter cryptogramAdapter;
    private CryptogramDetailAdapter cryptogramDetailAdapter;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private TextView lblPuzzleName;
    private TextView lblPuzzleCreated;
    private TextView lblPuzzleSolved;
    private TextView lblPuzzleSolvedBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent myIntent = getIntent(); // gets the previously created intent for this activity
        currentUser = myIntent.getStringExtra(Constants.CURRENT_USER);
        statsMode = myIntent.getStringExtra(Constants.STATS_MODE);

        mDataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);

        if (statsMode.equalsIgnoreCase(Constants.UNSOLVED_STATS)) {
            setContentView(R.layout.activity_cryptogram_unsolved_stats);

            // The list of unsolved cryptograms will show the cryptogram’s name and the number of
            // incorrect solution attempts (if any) for that player.
            mDataViewModel.getUnsolvedCryptograms(currentUser).observe(this, new Observer<List<UserCryptogramStats>>() {
                @Override
                public void onChanged(@Nullable final List<UserCryptogramStats> unsolvedCryptograms) {
                    System.out.println(unsolvedCryptograms.size());
                    recyclerView = (RecyclerView) findViewById(R.id.unsolvedCryptograms);
                    linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    unSolvedCryptogramAdapter = new UserUnSolvedCryptogramAdapter(CryptogramStatsActivity.this, unsolvedCryptograms);
                    recyclerView.setAdapter(unSolvedCryptogramAdapter);

                }
            });
        } else if (statsMode.equalsIgnoreCase(Constants.COMPLETED_STATS)) {
            setContentView(R.layout.activity_cryptogram_completed_stats);

            // The list of completed cryptograms for that player will show the cryptogram’s name,
            // whether it was successfully solved, and the date it was completed.
            mDataViewModel.getCompletedCryptograms(currentUser).observe(this, new Observer<List<UserCryptogramStats>>() {
                @Override
                public void onChanged(@Nullable final List<UserCryptogramStats> completedCryptograms) {
                    System.out.println(completedCryptograms.size());

                    recyclerView = (RecyclerView) findViewById(R.id.completedCryptograms);
                    linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    completedCryptogramAdapter = new UserCompletedCryptogramAdapter(CryptogramStatsActivity.this, completedCryptograms);
                    recyclerView.setAdapter(completedCryptogramAdapter);

                }
            });
        } else if (statsMode.equalsIgnoreCase(Constants.OVERALL_STATS)) {
            setContentView(R.layout.activity_cryptogram_overall_stats);

            // The list of cryptogram statistics will display a list of cryptograms in order of creation.
            // Choosing each cryptogram will display the date it was created, the number of players
            // who have solved the cryptogram, and the username of the first three players to solve the
            // cryptogram.
            mDataViewModel.getAllCryptogramsByCreated().observe(this, new Observer<List<Cryptogram>>() {
                @Override
                public void onChanged(@Nullable final List<Cryptogram> cryptograms) {
                    System.out.println(cryptograms.size());
                    recyclerView = (RecyclerView) findViewById(R.id.cryptogramOverallStats);
                    linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    cryptogramAdapter = new CryptogramAdapter(CryptogramStatsActivity.this, cryptograms);
                    recyclerView.setAdapter(cryptogramAdapter);
                }
            });

        } else if (statsMode.equalsIgnoreCase(Constants.CRYPTOGRAM_STATS)) {
            setContentView(R.layout.activity_cryptogram_details);

            lblPuzzleName = (TextView) findViewById(R.id.lblPuzzleName);
            lblPuzzleCreated = (TextView) findViewById(R.id.lblPuzzleCreated);
            lblPuzzleSolved = (TextView) findViewById(R.id.lblPuzzleSolved);
            lblPuzzleSolvedBy = (TextView) findViewById(R.id.lblPuzzleSolvedBy);

            // The list of cryptogram statistics will display a list of cryptograms in order of creation.
            // Choosing each cryptogram will display the date it was created, the number of players
            // who have solved the cryptogram, and the username of the first three players to solve the
            // cryptogram.
            thePuzzle = myIntent.getStringExtra(Constants.SELECTED_PUZZLE);
            thePuzzleCreated = myIntent.getStringExtra(Constants.SELECTED_PUZZLE_CREATED);
            mDataViewModel.getCryptogramStatistics(thePuzzle).observe(this, new Observer<List<UserCryptogramStats>>() {
                @Override
                public void onChanged(@Nullable final List<UserCryptogramStats> cryptogramStats) {
                    lblPuzzleName.setText(thePuzzle);
                    lblPuzzleCreated.setText(thePuzzleCreated);
                    if (cryptogramStats != null) {
                        lblPuzzleSolved.setText(String.valueOf(cryptogramStats.size()));
                        StringBuilder usersWhoSolved = new StringBuilder();
                        if (cryptogramStats.size() > 0) {
                            for (int index = 0; index < cryptogramStats.size(); index++) {
                                UserCryptogramStats playerStats = cryptogramStats.get(index);
                                if (index < 3) {
                                    usersWhoSolved.append(playerStats.getStatistic().getUsername()).append(",");
                                }
                            }
                            lblPuzzleSolvedBy.setText(usersWhoSolved.deleteCharAt(usersWhoSolved.length() - 1).toString());
                        } else {
                            lblPuzzleSolvedBy.setText("Nobody!!");
                        }
                    }
                }
            });


        } else {
            finish();
        }
    }

    public void handlePuzzleStatsClick(View view) {

        if (view.getId() == R.id.btnPuzzleStatsHomeMenu) {
            // Clear layout stack and launch the home screen
            CommonUtils.startLauncherActivity(getApplicationContext());
        } else if (view.getId() == R.id.btnStatsDetailBack) {
            finish();
        }
    }
}
