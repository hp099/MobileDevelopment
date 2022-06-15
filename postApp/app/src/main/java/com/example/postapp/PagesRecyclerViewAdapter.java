package com.example.postapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PagesRecyclerViewAdapter extends RecyclerView.Adapter<PagesRecyclerViewAdapter.PagesViewHolder>{

    ArrayList<Integer> pages;
    IPageRecycler listener;

    public PagesRecyclerViewAdapter(ArrayList<Integer> data, IPageRecycler listener){
        this.pages = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_item, parent, false);
        PagesViewHolder pagesViewHolder = new PagesViewHolder(view, listener);
        return pagesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PagesViewHolder holder, int position) {
        Integer page = pages.get(position);
        holder.button.setText(page.toString());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.selectPage(page);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pages.size();
    }


    public static class PagesViewHolder extends RecyclerView.ViewHolder{

        Button button;
        IPageRecycler listener;

        public PagesViewHolder(@NonNull View itemView, IPageRecycler listener) {
            super(itemView);
            button = itemView.findViewById(R.id.buttonPage);
            this.listener = listener;
        }
    }

    interface IPageRecycler{
        void selectPage(int page);
    }
}
