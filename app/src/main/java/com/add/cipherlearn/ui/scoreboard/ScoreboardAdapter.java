package com.add.cipherlearn.ui.scoreboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.add.cipherlearn.R;

import java.util.List;

public class ScoreboardAdapter extends RecyclerView.Adapter<com.add.cipherlearn.ui.scoreboard.ScoreboardAdapter.ExampleViewHolder> {

    private List<User> users;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView nicknamed;
        public TextView scored;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            nicknamed = (TextView) itemView.findViewById(R.id.nickname);
            scored = (TextView) itemView.findViewById(R.id.score);
        }
    }
    public ScoreboardAdapter(List<User> users) {
        this.users = users;
    }
    @Override
    public com.add.cipherlearn.ui.scoreboard.ScoreboardAdapter.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_score, parent, false);
        com.add.cipherlearn.ui.scoreboard.ScoreboardAdapter.ExampleViewHolder evh = new com.add.cipherlearn.ui.scoreboard.ScoreboardAdapter.ExampleViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(com.add.cipherlearn.ui.scoreboard.ScoreboardAdapter.ExampleViewHolder holder, int position) {
        User currentItem = users.get(position);
        holder.nicknamed.setText(currentItem.nickname);
        holder.scored.setText(currentItem.score + " points");
    }
    @Override
    public int getItemCount() {
        return users.size();
    }
}
