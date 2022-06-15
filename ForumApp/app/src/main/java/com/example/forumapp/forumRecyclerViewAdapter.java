package com.example.forumapp;

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

public class forumRecyclerViewAdapter extends RecyclerView.Adapter<forumRecyclerViewAdapter.ForumViewHolder>{
    ArrayList<Forum> forums;
    IForumRecycler listener;
    private FirebaseAuth mAuth;

    public forumRecyclerViewAdapter(ArrayList<Forum> data, IForumRecycler listener){
        this.forums = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_item, parent, false);
        ForumViewHolder forumViewHolder = new ForumViewHolder(view, listener);
        return forumViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
        Forum forum = forums.get(position);
        holder.forum = forum;
        holder.title.setText(forum.title);
        holder.author.setText(forum.creatorName);
        holder.description.setText(forum.description);
        holder.dateTime.setText(forum.dateTime);
        holder.likes.setText(forum.getNoOfLikes() + " Likes |");
        mAuth = FirebaseAuth.getInstance();

        if(!mAuth.getCurrentUser().getUid().equals(forum.userId)){
            holder.trashButton.setVisibility(View.INVISIBLE);
        }
        if(forum.likes.contains(mAuth.getCurrentUser().getUid())){
            holder.likeButton.setImageResource(R.drawable.like_favorite);
        }else{
            holder.likeButton.setImageResource(R.drawable.like_not_favorite);
        }

        holder.trashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add delete forum code
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("forums")
                        .document(forum.id).delete();

                Toast.makeText(view.getContext(),"Forum Deleted!",Toast.LENGTH_LONG).show();

                listener.updateForums();
            }
        });

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (forum.likes.contains(mAuth.getCurrentUser().getUid())){
                    forum.likes.remove(forum.likes.indexOf(mAuth.getCurrentUser().getUid()));
                    Toast.makeText(view.getContext(), "Forum Unliked!", Toast.LENGTH_LONG).show();
                }else{
                    forum.likes.add(mAuth.getCurrentUser().getUid());
                    Toast.makeText(view.getContext(), "Forum Liked!", Toast.LENGTH_LONG).show();
                }

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("forums").document(forum.id)
                        .update("likes", forum.likes).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        listener.updateForums();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.forums.size();
    }

    public static class ForumViewHolder extends RecyclerView.ViewHolder{
        IForumRecycler listener;
        TextView author;
        TextView title;
        TextView description;
        TextView dateTime;
        ImageButton trashButton;
        ImageButton likeButton;
        TextView likes;
        Forum forum;

        public ForumViewHolder(@NonNull View itemView, IForumRecycler listener){
            super(itemView);
            this.listener = listener;
            this.author = itemView.findViewById(R.id.textViewAuthor);
            this.title = itemView.findViewById(R.id.textViewTitle);
            this.description = itemView.findViewById(R.id.textViewDescription);
            this.dateTime = itemView.findViewById(R.id.textViewDate);
            this.trashButton = itemView.findViewById(R.id.imageButtonTrash);
            this.likes = itemView.findViewById(R.id.textViewLikes);
            this.likeButton = itemView.findViewById(R.id.imageButtonLike);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.detailForum(forum.id);
                }
            });
        }
    }

    interface IForumRecycler{
        void updateForums();
        void detailForum(String forumId);
    }
}
