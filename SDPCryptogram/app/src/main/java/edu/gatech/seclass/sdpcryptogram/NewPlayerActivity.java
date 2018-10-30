package edu.gatech.seclass.sdpcryptogram;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import java.util.List;
import java.util.regex.Pattern;

import edu.gatech.seclass.sdpcryptogram.dao.Player;
import edu.gatech.seclass.sdpcryptogram.model.DataViewModel;

public class NewPlayerActivity extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtFirstname;
    private EditText txtLastname;
    private EditText txtEmail;

    private DataViewModel mDataViewModel;

    private List<String> playerNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtFirstname = (EditText) findViewById(R.id.txtFirstname);
        txtLastname = (EditText) findViewById(R.id.txtLastname);
        txtEmail = (EditText) findViewById(R.id.txtEmail);

        mDataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);

        mDataViewModel.getAllPlayerNames().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable final List<String> playerNamesFromDB) {
                playerNames = playerNamesFromDB;
            }
        });
    }

    public void handleClick(View view) {

        boolean userNameValid = false;
        boolean firstNameValid = false;
        boolean lastNameValid = false;
        boolean emailValid = false;

        if (view.getId() == R.id.btnCancel) {
            finish();
        } else if (view.getId() == R.id.btnCreate) {
            String userName = txtUsername.getText().toString();
            txtUsername.setError(null);
            if (TextUtils.isEmpty(userName)) {
                txtUsername.setError(getResources().getString(R.string.new_user_username_empty_err));
            } else if (playerNames.contains(userName)) {
                txtUsername.setError(getResources().getString(R.string.new_user_username_used_err));
            } else {
                userNameValid = true;
            }

            String email = txtEmail.getText().toString();
            txtEmail.setError(null);
            if (TextUtils.isEmpty(email)) {
                txtEmail.setError(getResources().getString(R.string.new_user_email_empty_err));
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                txtEmail.setError(getResources().getString(R.string.new_user_email_invalid_err));
            } else {
                emailValid = true;
            }

            String firstName = txtFirstname.getText().toString();
            txtFirstname.setError(null);
            if (TextUtils.isEmpty(firstName)) {
                txtFirstname.setError(getResources().getString(R.string.new_user_fname_empty_err));
            } else {
                firstNameValid = true;
            }

            String lastName = txtLastname.getText().toString();
            txtLastname.setError(null);
            if (TextUtils.isEmpty(lastName)) {
                txtLastname.setError(getResources().getString(R.string.new_user_lname_empty_err));
            } else {
                lastNameValid = true;
            }

            if (userNameValid && firstNameValid && lastNameValid && emailValid) {
                Player player = new Player(userName, firstName, lastName, email);
                mDataViewModel.insert(player);
                finish();
            }
        }
    }
}
