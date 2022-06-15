package com.example.postapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostsRecyclerViewAdapter extends RecyclerView.Adapter<PostsRecyclerViewAdapter.PostsViewHolder> {
    ArrayList<Posts> posts;
    IPostRecycler listener;

    public PostsRecyclerViewAdapter(ArrayList<Posts> data, IPostRecycler listener){
        this.posts = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        PostsViewHolder postsViewHolder = new PostsViewHolder(view, listener);

        return postsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        Posts post = posts.get(position);
        holder.postText.setText(post.post_text);
        holder.author.setText(post.created_by_name);
        holder.date.setText(post.created_at);

        if (!Integer.valueOf(post.created_by_uid).equals(listener.getUser().user_id)){
            holder.button.setVisibility(View.INVISIBLE);
        }

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Alert!");
                builder.setMessage("Do you want to delete the selected post?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.delete(post);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.posts.size();
    }

    public static class PostsViewHolder extends RecyclerView.ViewHolder{

        TextView author;
        TextView postText;
        TextView date;
        ImageButton button;
        IPostRecycler listener;

        public PostsViewHolder(@NonNull View itemView, IPostRecycler listener) {
            super(itemView);
            this.listener = listener;

            author = itemView.findViewById(R.id.textViewAuthor);
            postText = itemView.findViewById(R.id.textViewText);
            date = itemView.findViewById(R.id.textViewDate);
            button = itemView.findViewById(R.id.imageButtonTrash);


        }
    }

    interface IPostRecycler{
        void delete(Posts pst);

        Person getUser();
    }
}
