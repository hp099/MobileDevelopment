package com.example.forumapp;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class commentRecyclerViewAdapter extends RecyclerView.Adapter<commentRecyclerViewAdapter.CommentViewHolder>{
    List<Comments> comments;
    ICommentRecycler listener;
    private FirebaseAuth mAuth;

    // change String to Comment class
    public commentRecyclerViewAdapter(List<Comments> data, ICommentRecycler listener){
        this.comments = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent,false);
        CommentViewHolder commentViewHolder = new CommentViewHolder(view, listener);
        return commentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comments comment = comments.get(position);
        //holder.author.setText();
        holder.commentText.setText(comment.comment);
        holder.dateTime.setText(comment.dateTime);
        mAuth = FirebaseAuth.getInstance();


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("forums").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (QueryDocumentSnapshot document: value){
                    Forum forum = document.toObject(Forum.class);
                    if (forum.userId.equals(comment.userId)){
                        holder.author.setText(forum.creatorName);
                        break;
                    }
                }
            }
        });

        if(!mAuth.getCurrentUser().getUid().equals(comment.userId)){
            holder.trashButton.setVisibility(View.INVISIBLE);
        }

        holder.trashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("comments").document(comment.id).delete();
                Toast.makeText(view.getContext(),"Comment Deleted!",Toast.LENGTH_LONG).show();
                listener.updateComments();
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder{
        ICommentRecycler listener;
        TextView author;
        TextView commentText;
        TextView dateTime;
        ImageButton trashButton;
        TextView noOfComments;

        public CommentViewHolder(@NonNull View itemView, ICommentRecycler listener) {
            super(itemView);
            this.listener = listener;
            this.author = itemView.findViewById(R.id.textViewCommentBy);
            this.commentText = itemView.findViewById(R.id.textViewCommentDescp);
            this.dateTime = itemView.findViewById(R.id.textViewCommentDateTime);
            this.trashButton = itemView.findViewById(R.id.imageButtonTrashComment);
        }
    }

    interface ICommentRecycler{

        void updateComments();
    }
}
