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
import edu.gatech.seclass.sdpcryptogram.model.DataViewModel;
import edu.gatech.seclass.sdpcryptogram.utility.Constants;

public class CryptogramDetailAdapter extends RecyclerView.Adapter<CryptogramDetailAdapter.CryptogramDetailViewHolder> {
    private List<Cryptogram> statsList;
    private Context context;
    private OnItemClickListener listener;
    private DataViewModel mDataViewModel;

    public interface OnItemClickListener {
        void onItemClick(Cryptogram item);
    }

    public CryptogramDetailAdapter() {
    }

    public CryptogramDetailAdapter(Context context, List<Cryptogram> statsList) {
        this.statsList = statsList;
        this.context = context;
    }

    public CryptogramDetailAdapter(List<Cryptogram> statsList, OnItemClickListener listener) {
        this.statsList = statsList;
        this.listener = listener;
    }

    public CryptogramDetailAdapter(Context context, List<Cryptogram> statsList, OnItemClickListener listener) {
        this.context = context;
        this.statsList = statsList;
        this.listener = listener;
    }

    @Override
    public CryptogramDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cryptogram_detail_stats_message_layout, parent, false);
        return new CryptogramDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptogramDetailViewHolder holder, int position) {
        Cryptogram crypto = statsList.get(position);
        String puzzleText;
        String dateCreated;
        String playersSolved;
        String playersSolvedNames;

        View.OnClickListener itemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CryptogramStatsActivity.class);
                intent.putExtra(Constants.STATS_MODE, Constants.OVERALL_STATS);
                context.startActivity(intent);
            }
        };

        puzzleText = String.format("%-24s %s %-20s", "Puzzle Name", ":", crypto.getPuzzlename());
        dateCreated = String.format("%-25s %s %-20s", "Date Created", ":", crypto.getCreateDateString());
        playersSolved = String.format("%-13s %s %-20s", "Num Players Solved", ":", statsList.size());
        playersSolvedNames = String.format("%-23s %s %-20s", "Player Names", ":", statsList.size());

        holder.puzzleName.setText(puzzleText);
        holder.createDate.setText(dateCreated);
        holder.numPlayersSolved.setText(playersSolved);
        holder.playersSolved.setText(playersSolvedNames);

        holder.puzzleName.setOnClickListener(itemClickListener);
        holder.createDate.setOnClickListener(itemClickListener);
        holder.numPlayersSolved.setOnClickListener(itemClickListener);
        holder.playersSolved.setOnClickListener(itemClickListener);
    }

    @Override
    public int getItemCount() {
        return statsList.size();
    }

    class CryptogramDetailViewHolder extends RecyclerView.ViewHolder {
        public TextView puzzleName;
        public TextView createDate;
        public TextView numPlayersSolved;
        public TextView playersSolved;

        public CryptogramDetailViewHolder(View itemView) {
            super(itemView);
            puzzleName = (TextView) itemView.findViewById(R.id.detailCryptoMessageLayoutPuzzleName);
            createDate = (TextView) itemView.findViewById(R.id.detailCryptoMessageLayoutCreateDate);
            numPlayersSolved = (TextView) itemView.findViewById(R.id.detailCryptoMessageLayoutPlayersSolvedCount);
            playersSolved = (TextView) itemView.findViewById(R.id.detailCryptoMessageLayoutPlayersSolvedNames);
        }
    }
}
