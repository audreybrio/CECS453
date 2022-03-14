package com.example.candystore;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.candystore.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private DatabaseManager dbManager;
    private double total;
    private ScrollView scrollView;
    private int buttonWidth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbManager = new DatabaseManager(this);
        total = 0.0;
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        buttonWidth = size.x / 2;
        updateView();


    }

    protected void onResume( ) {
        super.onResume( );
        updateView();
    }

    public void updateView( ) {
        ArrayList<Candy> candies = dbManager.selectAll();
        if( candies.size( ) > 0 ) {
            // remove subviews inside scrollView if necessary
            scrollView.removeAllViewsInLayout( );
            // set up the grid layout
            GridLayout grid = new GridLayout( this );
            grid.setRowCount( ( candies.size( ) + 1 ) / 2 );
            grid.setColumnCount( 2 );
            // create array of buttons, 2 per row
            CandyButton [] buttons = new CandyButton[candies.size()];
            ButtonHandler bh = new ButtonHandler();
            // fill the grid
            int i=0;
            for ( Candy candy : candies ) {
                // create the button
                buttons[i] = new CandyButton( this, candy );
                buttons[i].setText( candy.getName() + "\n" + candy.getPrice() ); // set up event handling
                buttons[i].setOnClickListener(bh);
                // add the button to grid
                grid.addView( buttons[i], buttonWidth, GridLayout.LayoutParams.WRAP_CONTENT );
                i++;
            }
            scrollView.addView( grid );
            //setContentView(scrollView);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_add:
                Log.w("MainActivity", "Add selected");
                Intent insertIntent = new Intent(this, InsertActivity.class );
                this.startActivity( insertIntent );
                return true;
            case R.id.action_delete:
                Log.w("MainActivity", "Delete selected");
                Intent deleteIntent = new Intent(this, DeleteActivity.class );
                this.startActivity( deleteIntent );
                return true;
            case R.id.action_update:
                Log.w("MainActivity", "Update selected");
                Intent updateIntent = new Intent(this, UpdateActivity.class );
                this.startActivity( updateIntent );
                return true;
            case R.id.action_reset:
                Log.w("MainActivity", "Reset selected");
                total = 0.0;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class ButtonHandler implements View.OnClickListener {
        public void onClick( View v ) {
// retrieve price of the candy and add it to total
            total += ((CandyButton) v).getPrice();
            String pay = NumberFormat.getCurrencyInstance( ).format( total );
            Toast.makeText( MainActivity.this, pay, Toast.LENGTH_LONG ).show( );
        }
    }
}




