package com.example.listusers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
/*
HW04
Patel_HW04.zip
Harsh Patel
 */
public class SortRecyclerViewAdapter extends RecyclerView.Adapter<SortRecyclerViewAdapter.SortViewHolder> {

    ArrayList<String> sortOptions;
    ISortRecycler listener;

    public SortRecyclerViewAdapter(ArrayList<String> data, ISortRecycler listener){
        this.listener = listener;
        this.sortOptions = data;
    }

    @NonNull
    @Override
    public SortViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_item, parent, false);
        SortViewHolder sortViewHolder = new SortViewHolder(view, listener);
        return sortViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SortViewHolder holder, int position) {
        String sortOpt = sortOptions.get(position);
        holder.TextViewSortName.setText(sortOpt);
    }

    @Override
    public int getItemCount() {
        return this.sortOptions.size();
    }

    public static class SortViewHolder extends RecyclerView.ViewHolder{
        TextView TextViewSortName;
        ISortRecycler listener;

        public SortViewHolder(@NonNull View itemView, ISortRecycler listener){
            super(itemView);
            this.listener = listener;
            TextViewSortName = itemView.findViewById(R.id.textViewSortName);

            itemView.findViewById(R.id.imageButtonAscend).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.ascend(TextViewSortName.getText().toString());
                }
            });

            itemView.findViewById(R.id.imageButtonDescend).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.descend(TextViewSortName.getText().toString());
                }
            });
        }

    }

    interface ISortRecycler{
        void ascend(String sortOpt);
        void descend(String sortOpt);
    }
}
