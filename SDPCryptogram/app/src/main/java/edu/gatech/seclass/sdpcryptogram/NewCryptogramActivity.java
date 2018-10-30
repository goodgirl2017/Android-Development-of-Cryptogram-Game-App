package edu.gatech.seclass.sdpcryptogram;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.gatech.seclass.sdpcryptogram.dao.Cryptogram;
import edu.gatech.seclass.sdpcryptogram.model.DataViewModel;
import edu.gatech.seclass.sdpcryptogram.utility.CommonUtils;
import edu.gatech.seclass.sdpcryptogram.utility.Constants;

public class NewCryptogramActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DataViewModel mDataViewModel;

    private String currentUser;

    private EditText txtPuzzleName;
    private EditText txtOriginalPhrase;
    private EditText txtCipher;
    private TextView lblVerifyCryptogram;
    private TextView lblAlphabet;
    private Spinner spinnerAttempts;
    private int selectedAttempts;
    private boolean cryptogramViewValid;

    private List<String> puzzleNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cryptogram_create);

        Intent myIntent = getIntent(); // gets the previously created intent for this activity
        currentUser = myIntent.getStringExtra(Constants.CURRENT_USER);

        txtPuzzleName = (EditText) findViewById(R.id.txtPuzzleName);
        txtOriginalPhrase = (EditText) findViewById(R.id.txtOriginalPhrase);
        txtCipher = (EditText) findViewById(R.id.txtCipher);
        lblVerifyCryptogram = (TextView) findViewById(R.id.lblVerifyCryptogram);
        lblAlphabet = (TextView) findViewById(R.id.lblAlphabet);
        spinnerAttempts = (Spinner) findViewById(R.id.spinnerAttempts);
        spinnerAttempts.setOnItemSelectedListener(this);

        cryptogramViewValid = false;

        mDataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);

        mDataViewModel.getAllCryptogramNames().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable final List<String> puzzleNamesFromDB) {
                puzzleNames = puzzleNamesFromDB;
            }
        });

        txtOriginalPhrase.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    lblAlphabet.setText(CommonUtils.getUniqueAlphabets(txtOriginalPhrase.getText().toString()));
                }
            }
        });

        InputFilter[] editFilters = txtCipher.getFilters();
        InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
        newFilters[editFilters.length] = new InputFilter.AllCaps();
        txtCipher.setFilters(newFilters);

        txtCipher.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Logic to highlight the background
                String alphabets = lblAlphabet.getText().toString();
                String cipher = txtCipher.getText().toString();
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(alphabets);
                int startPosition = 0;
                int endPosition = cipher.length() <= alphabets.length() ? cipher.length() : alphabets.length();
                spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.rgb(0, 255, 0)), startPosition, endPosition, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                lblAlphabet.setText(spannableStringBuilder);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });
    }

    public void handleClick(View view) {

        if (view.getId() == R.id.btnNewPuzzleHomeMenu) {
            // Clear layout stack and launch the home screen
            CommonUtils.startLauncherActivity(getApplicationContext());
        } else if (view.getId() == R.id.btnTestCipher) {
            String originalPhrase = txtOriginalPhrase.getText().toString();
            String alphabets = lblAlphabet.getText().toString();
            String cipherText = txtCipher.getText().toString().toUpperCase();
            txtCipher.setText(cipherText);
            cryptogramViewValid = validateInputs();
            if (cryptogramViewValid) {
                lblVerifyCryptogram.setText(CommonUtils.encode(originalPhrase, alphabets, cipherText));
            }
        } else if (view.getId() == R.id.btnSavePuzzle) {
            cryptogramViewValid = validateInputs();
            if (cryptogramViewValid) {
                String puzzleName = txtPuzzleName.getText().toString();
                String originalPhrase = txtOriginalPhrase.getText().toString();
                String cipherText = txtCipher.getText().toString();
                String alphabets = lblAlphabet.getText().toString();
                String encryptedPhrase = CommonUtils.encode(originalPhrase, alphabets, cipherText);

                Cryptogram cryptogram = new Cryptogram(puzzleName, originalPhrase, encryptedPhrase, selectedAttempts, alphabets, cipherText, (new Date()).getTime());
                // Save logic
                mDataViewModel.insert(cryptogram);
                finish();
            }
        } else if (view.getId() == R.id.btnBackOrCancel) {
            finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        selectedAttempts = Integer.parseInt(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Nothing to put in here as the selection is non-empty for the spinner
    }

    private boolean validateInputs() {

        boolean puzzleNameValid = false;
        boolean phraseValid = false;
        boolean cipherValid = false;

        String puzzleName = txtPuzzleName.getText().toString();
        String originalPhrase = txtOriginalPhrase.getText().toString();
        String cipherText = txtCipher.getText().toString();
        String alphabets = lblAlphabet.getText().toString();
        // selectedAttempts is already set

        txtPuzzleName.setError(null);
        if (TextUtils.isEmpty(puzzleName)) {
            txtPuzzleName.setError(getResources().getString(R.string.new_puzzle_name_empty_err));
        } else if (puzzleNames.contains(puzzleName)) {
            txtPuzzleName.setError(getResources().getString(R.string.new_puzzle_name_taken_err));
        } else {
            puzzleNameValid = true;
        }

        txtOriginalPhrase.setError(null);
        if (TextUtils.isEmpty(originalPhrase)) {
            txtOriginalPhrase.setError(getResources().getString(R.string.new_puzzle_phrase_empty_err));
        } else {
            phraseValid = true;
        }

        txtCipher.setError(null);
        if (TextUtils.isEmpty(cipherText)) {
            txtCipher.setError(getResources().getString(R.string.new_puzzle_cipher_empty_err));
        } else if (alphabets.length() != cipherText.length()) {
            txtCipher.setError(getResources().getString(R.string.new_puzzle_cipher_mismatch_err));
        } else if (CommonUtils.getUniqueAlphabets(cipherText).length() != cipherText.length()) {
            txtCipher.setError(getResources().getString(R.string.new_puzzle_cipher_mismatch2_err));
        } else {
            cipherValid = true;
        }
        return (puzzleNameValid && phraseValid && cipherValid);
    }
}
