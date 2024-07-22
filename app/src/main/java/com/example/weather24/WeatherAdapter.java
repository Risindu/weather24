package com.example.weather24;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherAdapter extends ArrayAdapter<String> {

    private final Activity context;

    private final String[] days;

    private final String[] day_temperatures;

    private final Integer[] day_icons;

    public WeatherAdapter(Activity context, String[] days, String[] day_temperatures, Integer[] day_icons) {
        super(context, R.layout.this_week, days);
        this.context = context;
        this.days = days;
        this.day_temperatures = day_temperatures;
        this.day_icons = day_icons;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.this_week, null, false);
        TextView day = (TextView) rowView.findViewById(R.id.day);
        TextView temperature = (TextView) rowView.findViewById(R.id.day_temperature);
        ImageView icon = (ImageView) rowView.findViewById(R.id.day_icon);
        day.setText(days[position]);
        temperature.setText(day_temperatures[position]);
        icon.setImageResource(day_icons[position]);
        return rowView;


    }
}
