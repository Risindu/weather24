package com.example.weather24;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailsDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details_display);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();

        ImageView icon = findViewById(R.id.detailsIcon);
        TextView detailsClimate = findViewById(R.id.detailsClimate);
        TextView temperature = findViewById(R.id.temperatureDetails);
        TextView humidity = findViewById(R.id.humidityDetails);
        TextView day = findViewById(R.id.detailDate);
        TextView feelsLike = findViewById(R.id.feelsLikeDetails);



        icon.setImageResource(intent.getIntExtra("icon", 0));
        temperature.setText(intent.getStringExtra("temperature"));
        humidity.setText(intent.getStringExtra("humidity"));
        feelsLike.setText(intent.getStringExtra("feelsLike"));
        detailsClimate.setText(intent.getStringExtra("climate"));
        day.setText(intent.getStringExtra("day"));
    }
}