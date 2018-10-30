package edu.gatech.seclass.sdpcryptogram;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.sdpcryptogram.dao.UserCryptogramStats;
import edu.gatech.seclass.sdpcryptogram.model.DataViewModel;
import edu.gatech.seclass.sdpcryptogram.utility.CommonUtils;
import edu.gatech.seclass.sdpcryptogram.utility.Constants;

public class SelectCryptogramActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DataViewModel mDataViewModel;

    private Spinner spinnerPuzzles;
    private String selectedPuzzle;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cryptogram_view);

        Intent myIntent = getIntent(); // gets the previously created intent for this activity
        currentUser = myIntent.getStringExtra(Constants.CURRENT_USER);

        spinnerPuzzles = (Spinner) findViewById(R.id.spinnerPuzzles);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        spinnerPuzzles.setOnItemSelectedListener(this);

        mDataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        mDataViewModel.getUnsolvedCryptograms(currentUser).observe(this, new Observer<List<UserCryptogramStats>>() {
            @Override
            public void onChanged(@Nullable final List<UserCryptogramStats> cryptograms) {

                List<String> cryptogramNames = new ArrayList<>();
                for (UserCryptogramStats uCryprogram : cryptograms) {
                    cryptogramNames.add(uCryprogram.getCryptogram().getPuzzlename());
                }
                adapter.clear();
                adapter.addAll(cryptogramNames);
                // Update the cached copy of the words in the adapter.
                spinnerPuzzles.setAdapter(adapter);
            }
        });
    }

    public void handleSelectPuzzleClick(View view) {

        if (view.getId() == R.id.btnSelectPuzzle) {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(selectedPuzzle)) {
                setResult(RESULT_CANCELED, replyIntent);
                finish();
            } else {
                //replyIntent.putExtra(Constants.SELECTED_PUZZLE, selectedPuzzle);
                //setResult(RESULT_OK, replyIntent);
                // REQUIREMENT 6(f): We need to go back to the select puzzle screen from the play view
                Intent intentSolveCryptogram = new Intent(SelectCryptogramActivity.this, CryptogramActivity.class);
                intentSolveCryptogram.putExtra(Constants.CURRENT_USER, currentUser);
                intentSolveCryptogram.putExtra(Constants.CRYPTOGRAM_TO_SOLVE, selectedPuzzle);
                startActivityForResult(intentSolveCryptogram, Constants.SOLVE_CRYPTOGRAM_ACTIVITY_REQUEST_CODE);
            }
        } else if (view.getId() == R.id.btnCreatePuzzle) {
            Intent intent = new Intent(SelectCryptogramActivity.this, NewCryptogramActivity.class);
            startActivityForResult(intent, Constants.CREATE_CRYPTOGRAM_ACTIVITY_REQUEST_CODE);
        } else if (view.getId() == R.id.btnViewPuzzleHomeMenu) {
            // Clear layout stack and launch the home screen
            CommonUtils.startLauncherActivity(getApplicationContext());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        selectedPuzzle = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Nothing to put in here as the selection is non-empty for the spinner
    }
}
