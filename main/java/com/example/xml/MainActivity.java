package com.example.xml;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class MainActivity extends AppCompatActivity {

    private final String URL = "https://www.nasa.gov/rss/dyn/educationnews.rss";
    private ArrayList<Item> items;
    private ListView listView;
    private ArrayList<Item> listItems;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser saxParser = factory.newSAXParser();
                    SAXHandler handler = new SAXHandler();
                    // InputStream is = getResources().openRawResource(R.raw.test);
                    saxParser.parse(URL, handler);
                    items = handler.getItems();
                } catch (Exception e) {
                    Log.w("MainActivity", e.toString());
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        displayList(items);
                    }
                });
            }
        });
    }

    public void displayList(ArrayList<Item> items) {
        listItems = items;
        if (items != null) {
            ArrayList<String> titles = new ArrayList<String>( );
            for (Item item : items)
                titles.add( item.getTitle() );
            ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, titles );
            listView.setAdapter( adapter );
            ListItemHandler lih = new ListItemHandler( );
            listView.setOnItemClickListener(lih);
        }
        else
            Toast.makeText( this, "Sorry - No data found", Toast.LENGTH_LONG ).show( );

    }

    private class ListItemHandler implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id ) {
            Item selectedItem = listItems.get(position);
            Uri uri = Uri.parse( selectedItem.getLink());
            Intent browserIntent = new Intent( Intent.ACTION_VIEW, uri);
            startActivity(browserIntent);
        }
    }
}