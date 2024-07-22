package com.example.weather24;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.text.ParseException;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.Date;


public class Home extends AppCompatActivity {

    String[] days = new String[4];
    Integer[] climateIcons = new Integer[4];

    String[] temperatures = new String[4];

    String[] feelsLikeData = new String[4];

    String[] humidity = new String[4];

    String[] description = new String[4];

    String city;




    WeatherAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        fetchData fetch = new fetchData();
        fetch.execute();

        Intent intent = getIntent();
        city = intent.getStringExtra("city");

        String titleText = city + " Today";

        TextView cityName = (TextView) findViewById(R.id.titleText);
        cityName.setText(titleText);

    }



    public class fetchData extends AsyncTask<String, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJsonStr = "";



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                final String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&units=metric&appid=25d6501e980cf684f47e23bbb4395412";
                URL url = new URL(BASE_URL);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();

                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                forecastJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e("fetchData", "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("fetchData", "Error closing stream", e);
                    }
                }
            }
            return forecastJsonStr;
        }

        @Override
        protected void onPostExecute(String forecastJsonStr) {
            super.onPostExecute(forecastJsonStr);


            TextView climate = (TextView) findViewById(R.id.climate);
            TextView temperature = (TextView) findViewById(R.id.temperature);
            TextView feelsLike = (TextView) findViewById(R.id.feelsLike);
            ImageView icon = (ImageView) findViewById(R.id.icon);

            if (forecastJsonStr != null) {
                try {
                    JSONObject forecastJson = new JSONObject(forecastJsonStr);
                    JSONArray list = forecastJson.getJSONArray("list");
                    JSONObject day1_listItem = list.getJSONObject(0); //For today
                    JSONObject day2_listItem = list.getJSONObject(8); //For tomorrow
                    JSONObject day3_listItem = list.getJSONObject(16); //For after tomorrow
                    JSONObject day4_listItem = list.getJSONObject(24); //For after after tomorrow
                    JSONObject day5_listItem = list.getJSONObject(32); //For after after after tomorrow

                    //Day 01

                    JSONArray weather = day1_listItem.getJSONArray("weather");
                    JSONObject weatherItem = weather.getJSONObject(0);
                    String main = weatherItem.getString("main");
                    String ico = weatherItem.getString("icon");
                    String date1 = day1_listItem.getString("dt_txt"); // date for the first day
                    String day1 = getDayOfWeek(date1);
                    String fullIcon1 = "weather" + ico;
                    int climateIcon1 = getResources().getIdentifier(fullIcon1, "drawable", getPackageName());


                    JSONObject mainObject = day1_listItem.getJSONObject("main");
                    Double temp1 = mainObject.getDouble("temp");
                    Double feels1 = mainObject.getDouble("feels_like");
                    int humidity1 = mainObject.getInt("humidity");


                    String temp_1 = String.valueOf(temp1)+" °C";
                    String feels_1 = String.valueOf(feels1)+" °C";
                    String humidity_1 = String.valueOf(humidity1)+" %";

                    climate.setText(main);
                    temperature.setText(temp_1);
                    feelsLike.setText(feels_1);
                    icon.setImageResource(climateIcon1);

                    //Day 02

                    JSONArray wather2 = day2_listItem.getJSONArray("weather");
                    JSONObject weatherItem2 = wather2.getJSONObject(0);
                    String main2 = weatherItem2.getString("main");
                    String ico2 = weatherItem2.getString("icon");
                    String date2 = day2_listItem.getString("dt_txt"); // date for the second day
                    String day2 = getDayOfWeek(date2);
                    String fullIcon2 = "weather" + ico2;
                    int climateIcon2 = getResources().getIdentifier(fullIcon2, "drawable", getPackageName());

                    JSONObject mainObject2 = day2_listItem.getJSONObject("main");
                    Double temp2 = mainObject2.getDouble("temp");
                    Double feels2 = mainObject2.getDouble("feels_like");
                    int humidity2 = mainObject2.getInt("humidity");

                    String temp_2 = String.valueOf(temp2)+" °C";
                    String feels_2 = String.valueOf(feels2)+" °C";
                    String humidity_2 = String.valueOf(humidity2)+" %";



                    //Day 03
                    JSONArray wather3 = day3_listItem.getJSONArray("weather");
                    JSONObject weatherItem3 = wather3.getJSONObject(0);
                    String main3 = weatherItem3.getString("main");
                    String ico3 = weatherItem3.getString("icon");
                    String date3 = day3_listItem.getString("dt_txt"); // date for the third day
                    String day3 = getDayOfWeek(date3);
                    String fullIcon3 = "weather" + ico3;
                    int climateIcon3 = getResources().getIdentifier(fullIcon3, "drawable", getPackageName());


                    JSONObject mainObject3 = day3_listItem.getJSONObject("main");
                    Double temp3 = mainObject3.getDouble("temp");
                    Double feels3 = mainObject3.getDouble("feels_like");
                    int humidity3 = mainObject3.getInt("humidity");

                    String temp_3 = String.valueOf(temp3)+" °C";
                    String feels_3 = String.valueOf(feels3)+" °C";
                    String humidity_3 = String.valueOf(humidity3)+" %";

                    //Day 04
                    JSONArray wather4 = day4_listItem.getJSONArray("weather");
                    JSONObject weatherItem4 = wather4.getJSONObject(0);
                    String main4 = weatherItem4.getString("main");
                    String ico4 = weatherItem4.getString("icon");
                    String date4 = day4_listItem.getString("dt_txt"); // date for the fourth day
                    String day4 = getDayOfWeek(date4);
                    String fullIcon4 = "weather" + ico4;
                    int climateIcon4 = getResources().getIdentifier(fullIcon4, "drawable", getPackageName());

                    JSONObject mainObject4 = day4_listItem.getJSONObject("main");
                    Double temp4 = mainObject4.getDouble("temp");
                    Double feels4 = mainObject4.getDouble("feels_like");
                    int humidity4 = mainObject4.getInt("humidity");

                    String temp_4 = String.valueOf(temp4)+" °C";
                    String feels_4 = String.valueOf(feels4)+" °C";
                    String humidity_4 = String.valueOf(humidity4)+" %";


                    //Day 05
                    JSONArray wather5 = day5_listItem.getJSONArray("weather");
                    JSONObject weatherItem5 = wather5.getJSONObject(0);
                    String main5 = weatherItem5.getString("main");
                    String ico5 = weatherItem5.getString("icon");
                    String date5 = day5_listItem.getString("dt_txt"); // date for the fifth day
                    String day5 = getDayOfWeek(date5);
                    String fullIcon5 = "weather" + ico5;
                    int climateIcon5 = getResources().getIdentifier(fullIcon5, "drawable", getPackageName());

                    JSONObject mainObject5 = day5_listItem.getJSONObject("main");
                    Double temp5 = mainObject5.getDouble("temp");
                    Double feels5 = mainObject5.getDouble("feels_like");
                    int humidity5 = mainObject5.getInt("humidity");


                    String temp_5 = String.valueOf(temp5)+" °C";
                    String feels_5 = String.valueOf(feels5)+" °C";
                    String humidity_5 = String.valueOf(humidity5)+" %";


                    days[0] = day2;
                    days[1] = day3;
                    days[2] = day4;
                    days[3] = day5;


                    temperatures[0] = temp_2;
                    temperatures[1] = temp_3;
                    temperatures[2] = temp_4;
                    temperatures[3] = temp_5;


                    climateIcons[0] = climateIcon2;
                    climateIcons[1] = climateIcon3;
                    climateIcons[2] = climateIcon4;
                    climateIcons[3] = climateIcon5;


                    feelsLikeData[0] = feels_2;
                    feelsLikeData[1] = feels_3;
                    feelsLikeData[2] = feels_4;
                    feelsLikeData[3] = feels_5;


                    humidity[0] = humidity_2;
                    humidity[1] = humidity_3;
                    humidity[2] = humidity_4;
                    humidity[3] = humidity_5;

                    description[0] = main2;
                    description[1] = main3;
                    description[2] = main4;
                    description[3] = main5;

                    adapter = new WeatherAdapter(Home.this, days, temperatures, climateIcons);
                    ListView listView = (ListView) findViewById(R.id.listView);
                    listView.setAdapter(adapter);


                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent detailActivity = new Intent(Home.this, DetailsDisplay.class);
                            detailActivity.putExtra("day", days[i]);
                            detailActivity.putExtra("climate", description[i]);
                            detailActivity.putExtra("temperature", temperatures[i]);
                            detailActivity.putExtra("feelsLike", feelsLikeData[i]);
                            detailActivity.putExtra("humidity", humidity[i]);
                            detailActivity.putExtra("icon", climateIcons[i]);
                            startActivity(detailActivity);
                        }
                    });




                } catch (Exception e) {
                    Log.e("Home", "Error", e);
                }
            }
        }

        private String getDayOfWeek(String dateStr) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = format.parse(dateStr);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                return new DateFormatSymbols().getWeekdays()[dayOfWeek];
            } catch (ParseException e) {
                Log.e("Home", "Error parsing date", e);
                return "";
            }
        }
    }
}


