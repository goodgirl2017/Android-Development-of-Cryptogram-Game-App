package edu.gatech.seclass.sdpcryptogram;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import edu.gatech.seclass.sdpcryptogram.dao.Cryptogram;
import edu.gatech.seclass.sdpcryptogram.dao.Statistic;
import edu.gatech.seclass.sdpcryptogram.model.DataViewModel;
import edu.gatech.seclass.sdpcryptogram.utility.CommonUtils;
import edu.gatech.seclass.sdpcryptogram.utility.Constants;
import edu.gatech.seclass.sdpcryptogram.R;

public class CryptogramActivity extends AppCompatActivity {

    private DataViewModel mDataViewModel;

    private String currentUser;
    private String selectedPuzzle;

    private Integer attemptsMade;

    private Statistic statisticToBeUpdated;
    private Cryptogram cryptogramToBeReferenced;

    private TextView lblEncodedPhrase;
    private TextView lblAttemptsMade;
    private TextView lblPlayAlphabet;
    private TextView lblPlayVerifySolution;
    private EditText txtPlayCipher;

    private boolean cryptogramViewValid;
    private boolean cipherApplied = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cryptogram_play);

        Intent myIntent = getIntent(); // gets the previously created intent for this activity
        currentUser = myIntent.getStringExtra(Constants.CURRENT_USER);
        selectedPuzzle = myIntent.getStringExtra(Constants.CRYPTOGRAM_TO_SOLVE);

        lblEncodedPhrase = (TextView) findViewById(R.id.lblEncodedPhrase);
        lblAttemptsMade = (TextView) findViewById(R.id.lblAttemptsMade);
        lblPlayAlphabet = (TextView) findViewById(R.id.lblPlayAlphabet);
        lblPlayVerifySolution = (TextView) findViewById(R.id.lblPlayVerifySolution);
        txtPlayCipher = (EditText) findViewById(R.id.txtPlayCipher);

        attemptsMade = 0;

        mDataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        mDataViewModel.getCryptogramDetails(selectedPuzzle).observe(this, new Observer<Cryptogram>() {
            @Override
            public void onChanged(@Nullable final Cryptogram cryptogram) {
                if (cryptogram != null) {
                    cryptogramToBeReferenced = cryptogram;
                    lblEncodedPhrase.setText(cryptogramToBeReferenced.getEncrypted());
                    lblPlayAlphabet.setText(CommonUtils.getUniqueAlphabets(lblEncodedPhrase.getText().toString()));
                }
            }
        });

        mDataViewModel.getStatisticByUserPuzzle(currentUser, selectedPuzzle).observe(this, new Observer<Statistic>() {
            @Override
            public void onChanged(@Nullable final Statistic statistic) {
                if (statistic != null) {
                    statisticToBeUpdated = statistic;
                    attemptsMade = statisticToBeUpdated.getAttempts();
                } else {
                    statisticToBeUpdated = new Statistic(currentUser, selectedPuzzle, 0, false, null);
                }
                lblAttemptsMade.setText(getResources().getString(R.string.play_puzzle_incorrect_submission) + attemptsMade.toString());
            }
        });

        InputFilter[] editFilters = txtPlayCipher.getFilters();
        InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
        newFilters[editFilters.length] = new InputFilter.AllCaps();
        txtPlayCipher.setFilters(newFilters);

        txtPlayCipher.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Logic to highlight the background
                String alphabets = lblPlayAlphabet.getText().toString();
                String cipher = txtPlayCipher.getText().toString();
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(alphabets);
                int startPosition = 0;
                int endPosition = cipher.length() <= alphabets.length() ? cipher.length() : alphabets.length();
                spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.rgb(0, 255, 0)), startPosition, endPosition, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                lblPlayAlphabet.setText(spannableStringBuilder);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });
    }

    public void handlePlayPuzzleClick(View view) {

        if (view.getId() == R.id.btnPlayPuzzleHomeMenu) {
            // Clear layout stack and launch the home screen
            CommonUtils.startLauncherActivity(getApplicationContext());
        } else if (view.getId() == R.id.btnPlayCancel) {
            finish();
        } else if (view.getId() == R.id.btnPlayTest) {

            this.cipherApplied = true;
            String encodedPhrase = lblEncodedPhrase.getText().toString();
            String alphabets = lblPlayAlphabet.getText().toString();
            String cipherText = txtPlayCipher.getText().toString().toUpperCase();
            txtPlayCipher.setText(cipherText);
            cryptogramViewValid = validateInputs();
            if (cryptogramViewValid) {
                if (cipherText.length() < alphabets.length()) {
                    alphabets = alphabets.substring(0, cipherText.length());
                }
                lblPlayVerifySolution.setText(CommonUtils.decode(encodedPhrase, cipherText, alphabets));
                //this.cipherApplied = true;
            }else{
                this.cipherApplied = false;
            }
        } else if (view.getId() == R.id.btnPlaySolve) {

            String decodedPhrase = lblPlayVerifySolution.getText().toString();
            String originalPhrase = cryptogramToBeReferenced.getPhrase();
            cryptogramViewValid = validateInputs();
            if (cryptogramViewValid) {
                if (attemptsMade < cryptogramToBeReferenced.getAllowed()) {

                    if (decodedPhrase.equals(originalPhrase)) {
                        // Check the logic for the cryptogram solution
                        statisticToBeUpdated.setSolved(true);
                        statisticToBeUpdated.setAttempts(attemptsMade);
                        statisticToBeUpdated.setCompletedate((new Date()).getTime());
                        // And save the updated statistic
                        mDataViewModel.insert(statisticToBeUpdated);
                        Toast.makeText(getApplicationContext(), R.string.solved_puzzle, Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        // Increment only if incorrect submission
                        attemptsMade += 1;
                        statisticToBeUpdated.setAttempts(attemptsMade);
                        statisticToBeUpdated.setSolved(false);
                        // Update the stats data in DB
                        mDataViewModel.insert(statisticToBeUpdated);
                        lblAttemptsMade.setText(getResources().getString(R.string.play_puzzle_incorrect_submission) + attemptsMade.toString());
                    }
                } else {

                    Toast.makeText(getApplicationContext(), R.string.max_attempts_over, Toast.LENGTH_LONG).show();
                    statisticToBeUpdated.setAttempts(attemptsMade);
                    statisticToBeUpdated.setSolved(false);
                    statisticToBeUpdated.setCompletedate((new Date()).getTime());
                    // And save the updated statistic
                    mDataViewModel.insert(statisticToBeUpdated);
                    finish();
                }
            }
        }
    }

    private boolean validateInputs() {

        boolean cipherValid = false;

        String alphabets = lblPlayAlphabet.getText().toString();
        String cipherText = txtPlayCipher.getText().toString();
        String decodedPhrase = lblPlayVerifySolution.getText().toString();

        txtPlayCipher.setError(null);
        if (cipherText.length() > alphabets.length()) {
            txtPlayCipher.setError(getResources().getString(R.string.play_puzzle_cipher_alpha_err));
        } else if (CommonUtils.getUniqueAlphabets(cipherText).length() != cipherText.length()) {
            txtPlayCipher.setError(getResources().getString(R.string.play_puzzle_cipher_dups_err));
        } else if (CommonUtils.getUniqueAlphabets(cipherText).length() < alphabets.length()) {
            txtPlayCipher.setError(getResources().getString(R.string.play_puzzle_cipher_err_not_enough));
        } else if (CommonUtils.getUniqueAlphabets(cipherText).length() == alphabets.length() && !this.cipherApplied) {
            //The user has mapped the characters but forgot to hit play button; let's remind him
            //instead of silently failing and giving him a loss in case he actually mapped correctly
            txtPlayCipher.setError(getResources().getString(R.string.forgot_to_hit_play));
        }
        else {
            cipherValid = true;
        }
        return cipherValid;
    }
}
