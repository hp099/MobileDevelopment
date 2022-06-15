package com.example.shoppinglist;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class usersViewHolder extends RecyclerView.ViewHolder {
    TextView userName;
    ImageButton inviteCheck;
    public usersViewHolder(@NonNull View itemView) {
        super(itemView);
        this.userName = itemView.findViewById(R.id.textViewUserName);
        this.inviteCheck = itemView.findViewById(R.id.imageButtonCheck);
    }
}
