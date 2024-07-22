package com.example.weather24;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Signup extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signupBtn), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button loginButton = findViewById(R.id.login_menu_signup_btn);
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        });

        EditText username = findViewById(R.id.Username);
        EditText email = findViewById(R.id.Email);
        EditText password = findViewById(R.id.Password);
        EditText confirmPassword = findViewById(R.id.Confirm_Password);
        EditText city = findViewById(R.id.City);

        Button SignUpBtn = findViewById(R.id.signupBtn);

        SignUpBtn.setOnClickListener(v -> {
            String user_name = username.getText().toString();
            String email_id = email.getText().toString();
            String pass = password.getText().toString();
            String confirm_pass = confirmPassword.getText().toString();
            String city_name = city.getText().toString();

            if (user_name.isEmpty() || email_id.isEmpty() || pass.isEmpty() || confirm_pass.isEmpty() || city_name.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else if (!pass.equals(confirm_pass)) {
                Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT).show();
            } else {
                new SignupTask().execute(user_name, email_id, pass, city_name);
            }
        });
    }

    public class SignupTask extends AsyncTask<String, Void, String> {

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
                final String BASE_URL = "https://22sea024.000webhostapp.com/api2/index.php?api_key=123456&action=signup&username=" + strings[0] + "&email=" + strings[1] + "&password=" + strings[2] + "&city=" + strings[3];
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonResponse = new JSONObject(s);
                String status = jsonResponse.getString("status");
                String message = jsonResponse.getString("message");

                if (status.equals("success")) {
                    Toast.makeText(Signup.this, "User created successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Signup.this, Login.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Signup.this, message, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Log.e("JSONParse", "Error parsing JSON response", e);
                Toast.makeText(Signup.this, "Error processing response", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
