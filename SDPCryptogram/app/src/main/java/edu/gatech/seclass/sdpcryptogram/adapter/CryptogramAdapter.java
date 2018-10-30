package edu.gatech.seclass.sdpcryptogram.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.gatech.seclass.sdpcryptogram.CryptogramStatsActivity;
import edu.gatech.seclass.sdpcryptogram.R;
import edu.gatech.seclass.sdpcryptogram.dao.Cryptogram;
import edu.gatech.seclass.sdpcryptogram.utility.Constants;

public class CryptogramAdapter extends RecyclerView.Adapter<CryptogramAdapter.CryptogramViewHolder> {
    private List<Cryptogram> statsList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Cryptogram item);
    }

    public CryptogramAdapter() {
    }

    public CryptogramAdapter(Context context, List<Cryptogram> statsList) {
        this.statsList = statsList;
        this.context = context;
    }

    public CryptogramAdapter(List<Cryptogram> statsList, OnItemClickListener listener) {
        this.statsList = statsList;
        this.listener = listener;
    }

    public CryptogramAdapter(Context context, List<Cryptogram> statsList, OnItemClickListener listener) {
        this.context = context;
        this.statsList = statsList;
        this.listener = listener;
    }

    @Override
    public CryptogramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cryptogram_overall_stats_message_layout, parent, false);
        return new CryptogramViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptogramViewHolder holder, int position) {
        Cryptogram crypto = statsList.get(position);
        String puzzleText;
        String dateCreated;
        puzzleText = String.format("%-12s %s %-20s", "Puzzle Name", ":", crypto.getPuzzlename());
        dateCreated = String.format("%-13s %s %-20s", "Date Created", ":", crypto.getCreateDateString());
        holder.puzzleName.setText(puzzleText);
        holder.createDate.setText(dateCreated);
        View.OnClickListener itemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CryptogramStatsActivity.class);
                intent.putExtra(Constants.STATS_MODE, Constants.CRYPTOGRAM_STATS);
                intent.putExtra(Constants.SELECTED_PUZZLE, crypto.getPuzzlename());
                intent.putExtra(Constants.SELECTED_PUZZLE_CREATED, crypto.getCreateDateString());
                context.startActivity(intent);
            }
        };
        holder.puzzleName.setOnClickListener(itemClickListener);
        holder.createDate.setOnClickListener(itemClickListener);
    }

    @Override
    public int getItemCount() {
        return statsList.size();
    }

    class CryptogramViewHolder extends RecyclerView.ViewHolder {
        public TextView puzzleName;
        public TextView createDate;

        public CryptogramViewHolder(View itemView) {
            super(itemView);
            puzzleName = (TextView) itemView.findViewById(R.id.overallCryptoMessageLayoutPuzzleName);
            createDate = (TextView) itemView.findViewById(R.id.overallCryptoMessageLayoutCreateDate);
        }
    }
}
