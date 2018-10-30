package edu.gatech.seclass.sdpcryptogram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.gatech.seclass.sdpcryptogram.utility.Constants;

public class SDPCryptogramActivity extends AppCompatActivity {

    private static String currentUser = "";

    private LinearLayout layoutUserNotSelected;
    private LinearLayout layoutUserSelected;
    private TextView lblUsername;

    private String selectedPuzzle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdpcryptogram);
    }

    public void onWindowFocusChanged(boolean hasFocus) {

        layoutUserNotSelected = (LinearLayout) findViewById(R.id.layoutUserNotSelected);
        layoutUserSelected = (LinearLayout) findViewById(R.id.layoutUserSelected);
        lblUsername = (TextView) findViewById(R.id.lblUsername);

        if (layoutUserNotSelected != null) {
            if (TextUtils.isEmpty(currentUser)) {
                layoutUserSelected.setVisibility(View.INVISIBLE);
                layoutUserNotSelected.setVisibility(View.VISIBLE);
            } else {
                lblUsername.setText("Hello " + currentUser + "!!");
                layoutUserSelected.setVisibility(View.VISIBLE);
                layoutUserNotSelected.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void handleClickPlayButton(View view) {

        currentUser = "";
        if (TextUtils.isEmpty(currentUser)) {
            Intent intent = new Intent(SDPCryptogramActivity.this, PlayerActivity.class);
            startActivityForResult(intent, Constants.SELECT_PLAYER_ACTIVITY_REQUEST_CODE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SELECT_PLAYER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            currentUser = (data.getStringExtra(Constants.SELECTED_USER));
        } else if (requestCode == Constants.CREATE_CRYPTOGRAM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            setContentView(R.layout.activity_cryptogram_view);
            onWindowFocusChanged(true);
        }
    }

    public void handleClickHomeButtons(View view) {

        switch (view.getId()) {

            case R.id.btnLogout:
                currentUser = "";
                Intent intentLogout = new Intent(SDPCryptogramActivity.this, PlayerActivity.class);
                intentLogout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentLogout);
                break;

            case R.id.btnHomeCreatePuzzle:
            case R.id.btnCreatePuzzle:
                Intent intentNewCryptogram = new Intent(SDPCryptogramActivity.this, NewCryptogramActivity.class);
                startActivityForResult(intentNewCryptogram, Constants.CREATE_CRYPTOGRAM_ACTIVITY_REQUEST_CODE);
                break;

            case R.id.btnNewPuzzleHomeMenu:
                onWindowFocusChanged(true);
                setContentView(R.layout.activity_sdpcryptogram);
                onWindowFocusChanged(true);
                break;

            case R.id.btnHomeChoosePuzzle:
                selectedPuzzle = "";
                Intent intentSelectCryptogram = new Intent(SDPCryptogramActivity.this, SelectCryptogramActivity.class);
                intentSelectCryptogram.putExtra(Constants.CURRENT_USER, currentUser);
                startActivityForResult(intentSelectCryptogram, Constants.SELECT_CRYPTOGRAM_ACTIVITY_REQUEST_CODE);
                break;

            case R.id.btnHomeUnsolvedPuzzles:
                Intent intentUnsolvedCryptogram = new Intent(SDPCryptogramActivity.this, CryptogramStatsActivity.class);
                intentUnsolvedCryptogram.putExtra(Constants.CURRENT_USER, currentUser);
                intentUnsolvedCryptogram.putExtra(Constants.STATS_MODE, Constants.UNSOLVED_STATS);
                startActivityForResult(intentUnsolvedCryptogram, Constants.CRYPTOGRAM_STATS_ACTIVITY_REQUEST_CODE);
                break;

            case R.id.btnHomeCompletedPuzzles:
                Intent intentCompletedCryptogram = new Intent(SDPCryptogramActivity.this, CryptogramStatsActivity.class);
                intentCompletedCryptogram.putExtra(Constants.CURRENT_USER, currentUser);
                intentCompletedCryptogram.putExtra(Constants.STATS_MODE, Constants.COMPLETED_STATS);
                startActivityForResult(intentCompletedCryptogram, Constants.CRYPTOGRAM_STATS_ACTIVITY_REQUEST_CODE);
                break;

            case R.id.btnHomeStatistics:
                Intent intentStatsCryptogram = new Intent(SDPCryptogramActivity.this, CryptogramStatsActivity.class);
                intentStatsCryptogram.putExtra(Constants.CURRENT_USER, currentUser);
                intentStatsCryptogram.putExtra(Constants.STATS_MODE, Constants.OVERALL_STATS);
                startActivityForResult(intentStatsCryptogram, Constants.CRYPTOGRAM_STATS_ACTIVITY_REQUEST_CODE);
                break;
        }
    }
}
