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

public class UserCompletedCryptogramAdapter  extends RecyclerView.Adapter<UserCompletedCryptogramAdapter.UserCompletedCryptogramViewHolder> {
    private List<UserCryptogramStats> statsList;
    private Context context;

    public UserCompletedCryptogramAdapter(Context context, List<UserCryptogramStats> statsList) {
        this.statsList = statsList;
        this.context = context;
    }

    @Override
    public UserCompletedCryptogramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_cryptogram_message_layout, parent, false);
        return new UserCompletedCryptogramViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserCompletedCryptogramViewHolder holder, int position) {

        UserCryptogramStats stat = statsList.get(position);
        String solveSuccess;
        String puzzleText;
        String dateFinished;
        puzzleText = String.format("%-12s %s %-20s", "Puzzle Name",":", stat.getStatistic().getPuzzlename());
        solveSuccess = String.format("%-15s %s %-20s", "Successful", ":", Boolean.toString(stat.isSuccessful()));
        dateFinished = String.format("%-14s %s %s", "Date Ended", ":",stat.getStatistic().getCompletedateString());
        holder.puzzleName.setText(puzzleText);
        holder.solveSuccess.setText(solveSuccess);
        holder.solveDate.setText(dateFinished);
    }

    @Override
    public int getItemCount() {
        return statsList.size();
    }
    public class UserCompletedCryptogramViewHolder extends RecyclerView.ViewHolder {
        public TextView puzzleName;
        public TextView solveDate;
        public TextView solveSuccess;

        public UserCompletedCryptogramViewHolder(View itemView) {
            super(itemView);
            puzzleName = (TextView) itemView.findViewById(R.id.messageLayoutPuzzleName);
            solveDate = (TextView) itemView.findViewById(R.id.messageLayoutSolveDate);
            solveSuccess = (TextView) itemView.findViewById(R.id.messageLayoutSolveSuccess);
        }
    }
}


