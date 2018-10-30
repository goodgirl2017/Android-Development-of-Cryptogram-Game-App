package edu.gatech.seclass.sdpcryptogram.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.TextView;

import edu.gatech.seclass.sdpcryptogram.R;
import edu.gatech.seclass.sdpcryptogram.dao.UserCryptogramStats;

import java.util.List;

public class UserUnSolvedCryptogramAdapter  extends RecyclerView.Adapter<UserUnSolvedCryptogramAdapter.UserUnSolvedViewHolder> {
    private List<UserCryptogramStats> statsList;
    private Context context;

    public UserUnSolvedCryptogramAdapter(Context context, List<UserCryptogramStats> statsList) {
        this.statsList = statsList;
        this.context = context;
    }

    @Override
    public UserUnSolvedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.unsolved_cryptogram_message_layout, parent, false);
        return new UserUnSolvedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserUnSolvedViewHolder holder, int position) {

        UserCryptogramStats stat = statsList.get(position);
        String puzzleText;
        String numAttempts;
        String numAttemptsRemaining;
        if (stat.getStatistic() == null) {
            numAttempts = "0";
        } else {
            numAttempts = stat.getStatistic().getAttempts().toString();
        }
        Integer chancesRemaining = stat.getCryptogram().getAllowed() - Integer.parseInt(numAttempts);
        puzzleText = String.format("%-12s %s %-20s", "Puzzle Name", ":", stat.getCryptogram().getPuzzlename());
        numAttempts = String.format("%-16s %s %-20s", "Attempts", ":", numAttempts);
        numAttemptsRemaining = String.format("%-15s %s %-20s", "Remaining", ":", chancesRemaining.toString());
        holder.puzzleName.setText(puzzleText);
        holder.incorrectAttempts.setText(numAttempts);
        holder.attemptsRemaining.setText(numAttemptsRemaining);
    }{}

    @Override
    public int getItemCount() {
        return statsList.size();
    }

    public class UserUnSolvedViewHolder extends RecyclerView.ViewHolder {
        public TextView puzzleName;
        public TextView incorrectAttempts;
        public TextView attemptsRemaining;

        public UserUnSolvedViewHolder(View itemView) {
            super(itemView);
            puzzleName = (TextView) itemView.findViewById(R.id.unsolvedMessageLayoutPuzzleName);
            incorrectAttempts = (TextView) itemView.findViewById(R.id.unsolvedMessageLayoutIncorrectAttempts);
            attemptsRemaining = (TextView) itemView.findViewById(R.id.unsolvedMessageLayoutAttemptsRemaining);
        }
    }
}
