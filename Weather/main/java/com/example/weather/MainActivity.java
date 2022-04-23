package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public static final char DEGREE = '\u00B0';
    public static final String STARTING_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    public static final String KEY_NAME = "&appid=";
    // private String city = "New York, NY";
    private String city = "London,UK";
    private String mykey = "8da4a7612a4952eaaba49f4d4315e1e2";
    String json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText location = (EditText) findViewById(R.id.location_id);
        TextChangeHandler tch = new TextChangeHandler();
        location.addTextChangedListener(tch);
    }
    private class TextChangeHandler implements TextWatcher {
        public void afterTextChanged (Editable e) {
            autosearch();
        }
        public void beforeTextChanged( CharSequence s, int start, int count, int after ) {
        }
        public void onTextChanged( CharSequence s, int start, int before, int after ) {
        }
    }
    public void autosearch() {
        EditText location = (EditText) findViewById(R.id.location_id);
        city = location.getText().toString().replaceAll("\\s", "%20");;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Do background here
                String baseUrl = STARTING_URL, cityString = city, keyName = KEY_NAME, key = mykey;
                //Create an object RemoteDataReader
                RemoteDataReader rdr = new RemoteDataReader(baseUrl, cityString, keyName, key);
                //Get the JSON string
                json = rdr.getData();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TemperatureParser parser = new TemperatureParser(json);
                        Log.w("MainActivity", String.valueOf(parser.getTemperatureK()) + DEGREE + "K");
                        Log.w("MainActivity", String.valueOf(parser.getTemperatureC()) + DEGREE + "C");
                        Log.w("MainActivity", String.valueOf(parser.getTemperatureF()) + DEGREE + "F");
                        TextView result = (TextView) findViewById(R.id.result_id);
                        result.setText(String.valueOf(parser.getTemperatureF()) + DEGREE + "F");
                    }
                });
            }
        });
    }
    public void search(View v) {
        EditText location = (EditText) findViewById(R.id.location_id);
        city = location.getText().toString().replaceAll("\\s", "%20");;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Do background here
                String baseUrl = STARTING_URL, cityString = city, keyName = KEY_NAME, key = mykey;
                //Create an object RemoteDataReader
                RemoteDataReader rdr = new RemoteDataReader(baseUrl, cityString, keyName, key);
                //Get the JSON string
                json = rdr.getData();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TemperatureParser parser = new TemperatureParser(json);
                        Log.w("MainActivity", String.valueOf(parser.getTemperatureK()) + DEGREE + "K");
                        Log.w("MainActivity", String.valueOf(parser.getTemperatureC()) + DEGREE + "C");
                        Log.w("MainActivity", String.valueOf(parser.getTemperatureF()) + DEGREE + "F");
                        TextView result = (TextView) findViewById(R.id.result_id);
                        result.setText(String.valueOf(parser.getTemperatureF()) + DEGREE + "F");
                    }
                });
            }
        });
    }
}
