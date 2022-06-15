package com.example.shoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class itemRecyclerViewAdapter extends RecyclerView.Adapter<itemRecyclerViewAdapter.itemViewHolder>{
    HashMap<String, Boolean> items;
    IItemsRecycler listener;
    private FirebaseAuth mAuth;

    public itemRecyclerViewAdapter(HashMap<String, Boolean> data, IItemsRecycler listener){
        this.items = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent,false);
        itemViewHolder itemVH = new itemViewHolder(view, listener);
        return itemVH;
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        Set<String> keySet = items.keySet();
        List<String> listKeys = new ArrayList<String>(keySet);
        String key = listKeys.get(position - 1);

        holder.itemm = new item(key, items.get(key));
        holder.itemName.setText(key);

        holder.trashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // implement
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class itemViewHolder extends RecyclerView.ViewHolder{
        TextView itemName;
        ImageButton trashButton;
        IItemsRecycler listener;
        item itemm;

        public itemViewHolder(@NonNull View itemView, IItemsRecycler listener) {
            super(itemView);
            this.listener = listener;
            this.itemName = itemView.findViewById(R.id.textViewItemName);
            this.trashButton = itemView.findViewById(R.id.imageButtonTrashItem);

        }
    }

    interface IItemsRecycler{
        void updateItems();
    }
}
