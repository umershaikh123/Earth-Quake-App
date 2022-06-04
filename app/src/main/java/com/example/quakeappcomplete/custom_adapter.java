package com.example.quakeappcomplete;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class custom_adapter extends ArrayAdapter<Earthquake>  {


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if ( listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list2, parent, false);
        }

        Earthquake currentWord = getItem(position);

        TextView Mag = (TextView)  listItemView.findViewById(R.id.magnitude);
        Mag.setText(currentWord.getStringMagnitude());

        TextView Location = (TextView)  listItemView.findViewById(R.id.location);
        Location.setText(currentWord.getLocation());

        TextView City = (TextView)  listItemView.findViewById(R.id.City);
        City.setText(currentWord.getCity());

        TextView date = (TextView)  listItemView.findViewById(R.id.date1);
        date.setText(currentWord.getDate());

        TextView time = (TextView)  listItemView.findViewById(R.id.time);
        time.setText(currentWord.getTime());


        GradientDrawable magnitudeCircle = (GradientDrawable) Mag.getBackground();
        int magnitudeColor = getMagnitudeColor(currentWord.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);


        return  listItemView;

 }


    public custom_adapter(Activity context, ArrayList<Earthquake>   earthquakes) {
        super(context, 0,   earthquakes);

    }//Constructor






    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }



}//class
