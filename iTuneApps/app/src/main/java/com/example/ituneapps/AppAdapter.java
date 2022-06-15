package com.example.ituneapps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/*
Assignment InClass05
Group11_InClass05.zip
Harsh Patel
 */

public class AppAdapter extends ArrayAdapter<DataServices.App> {
    public AppAdapter(@NonNull Context context, int resource, @NonNull List<DataServices.App> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_row_item, parent, false);
        }

        DataServices.App app = getItem(position);

        TextView name = convertView.findViewById(R.id.textViewName);
        TextView artist = convertView.findViewById(R.id.textViewArtist);
        TextView date = convertView.findViewById(R.id.textViewReleaseDate);

        name.setText(app.name);
        artist.setText(app.artistName);
        date.setText(app.releaseDate);

        return convertView;
    }
}
