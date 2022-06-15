package com.example.shoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class shoppingRecyclerViewAdapter extends RecyclerView.Adapter<shoppingRecyclerViewAdapter.shoppingListViewHOlder>{
    ArrayList<ShoppingList> lists;
    IshopListRecycler listener;
    private FirebaseAuth mAuth;

    public shoppingRecyclerViewAdapter(ArrayList<ShoppingList> data, IshopListRecycler listener){
        this.lists = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public shoppingListViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoppinglist_item, parent, false);
        shoppingListViewHOlder shoppingListViewHOlder = new shoppingListViewHOlder(view, listener);
        return shoppingListViewHOlder;
    }

    @Override
    public void onBindViewHolder(@NonNull shoppingListViewHOlder holder, int position) {
        ShoppingList shoppingList = lists.get(position);
        holder.shpList = shoppingList;
        holder.title.setText(shoppingList.title);
        holder.author.setText(shoppingList.creatorName);

        mAuth = FirebaseAuth.getInstance();

/*        if(!mAuth.getCurrentUser().getUid().equals(shoppingList.userId)){
            holder.trashButton.setVisibility(View.INVISIBLE);
        }*/

        holder.trashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add delete forum code
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                if (mAuth.getUid().equals(shoppingList.userId)){
                    db.collection("shoppingLists")
                            .document(shoppingList.id).delete();

                    Toast.makeText(view.getContext(),"Shopping List Deleted!",Toast.LENGTH_LONG).show();
                }else{
                    shoppingList.guests.remove(mAuth.getUid());
                    db.collection("shoppingLists")
                            .document(shoppingList.id).update("guests", shoppingList.guests)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(view.getContext(),"Shopping List Exited!",Toast.LENGTH_LONG).show();
                                }
                            });
                }


                listener.updateShoppingList();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    public static class shoppingListViewHOlder extends RecyclerView.ViewHolder{
        IshopListRecycler listener;
        TextView author;
        TextView title;
        TextView description;
        ImageButton trashButton;
        ShoppingList shpList;
        

        public shoppingListViewHOlder(@NonNull View itemView, IshopListRecycler listener) {
            super(itemView);

            this.listener = listener;
            this.author = itemView.findViewById(R.id.textViewAuthor);
            this.title = itemView.findViewById(R.id.textViewTitle);
            this.description = itemView.findViewById(R.id.textViewDescription);
            this.trashButton = itemView.findViewById(R.id.imageButtonTrash);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.openShoppingList(shpList.id);
                }
            });
        }
    }

    interface IshopListRecycler{

        void updateShoppingList();

        void openShoppingList(String id);
    }
}
