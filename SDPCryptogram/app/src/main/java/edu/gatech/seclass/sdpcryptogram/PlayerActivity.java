package edu.gatech.seclass.sdpcryptogram;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import edu.gatech.seclass.sdpcryptogram.model.DataViewModel;
import edu.gatech.seclass.sdpcryptogram.utility.Constants;

public class PlayerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DataViewModel mDataViewModel;
    private Spinner spinnerUsernames;
    private String selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);

        selectedUser = "";
        spinnerUsernames = (Spinner) findViewById(R.id.spinnerUsernames);
        spinnerUsernames.setOnItemSelectedListener(this);

        mDataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        mDataViewModel.getAllPlayerNames().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable final List<String> usernames) {

                adapter.clear();
                adapter.addAll(usernames);
                // Update the cached copy of the words in the adapter.
                spinnerUsernames.setAdapter(adapter);
            }
        });
    }

    public void handleClickTextView(View view) {

        if (view.getId() == R.id.btnSelectPlayer) {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(selectedUser)) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                replyIntent.putExtra(Constants.SELECTED_USER, selectedUser);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        } else if (view.getId() == R.id.btnCreatePlayer) {
            Intent intent = new Intent(PlayerActivity.this, NewPlayerActivity.class);
            startActivityForResult(intent, Constants.NEW_PLAYER_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        selectedUser = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Nothing to put in here as the selection is non-empty for the spinner
    }
}
