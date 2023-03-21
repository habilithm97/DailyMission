package com.example.simpletodolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<MainModel> items = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView contentTv;
        private TextView priorityTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contentTv = itemView.findViewById(R.id.contentTv);
            priorityTv = itemView.findViewById(R.id.priorityTv);
        }
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        MainModel item = items.get(position);
        holder.contentTv.setText(item.getContent());
        holder.priorityTv.setText(item.getPriority());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<MainModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
