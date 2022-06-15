package com.example.listusers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
/*
HW04
Patel_HW04.zip
Harsh Patel
 */
public class UserAdapter extends ArrayAdapter<DataServices.User> {

    public UserAdapter(@NonNull Context context, int resource, @NonNull List<DataServices.User> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_row_item, parent, false);
        }

        DataServices.User user = getItem(position);

        TextView TextViewName = convertView.findViewById(R.id.textViewName);
        TextView TextViewState = convertView.findViewById(R.id.textViewState);
        TextView TextViewAge = convertView.findViewById(R.id.textViewAge);
        TextView TextViewRelation = convertView.findViewById(R.id.textViewRelation);
        ImageView img = convertView.findViewById(R.id.imageView);

        TextViewName.setText(user.name);
        TextViewState.setText(user.state);
        TextViewAge.setText(String.format("%d Years Old", user.age));
        TextViewRelation.setText(user.group);

        if (user.gender.equals("Male")){
            img.setImageResource(R.drawable.avatar_male);
        }else if (user.gender.equals("Female")){
            img.setImageResource(R.drawable.avatar_female);
        }

        return convertView;
    }
}
