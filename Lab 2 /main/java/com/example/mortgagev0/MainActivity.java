package com.example.mortgagev0;


import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mortgagev0.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static Mortgage mortgage;
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        mortgage = new Mortgage( );
        setContentView( R.layout.activity_main );
    }
    public void onStart( )
    {
        super.onStart( );
        updateView( );
    }

    public void updateView( ) {
        TextView amountTV = (TextView) findViewById(R.id.amount);
        amountTV.setText(mortgage.getFormattedAmount());
        TextView yearsTV = (TextView) findViewById(R.id.years);
        yearsTV.setText("" + mortgage.getYears());
        TextView rateTV = (TextView) findViewById(R.id.rate);
        rateTV.setText(mortgage.getRate() * 100 + "%");
        TextView monthlyTV = (TextView) findViewById(R.id.monthly);
        monthlyTV.setText(mortgage.formattedMonthlyPayment());
        TextView totalTV = (TextView) findViewById(R.id.total);
        totalTV.setText(mortgage.formattedTotalPayment());
 }

   public void modifyData( View v ) {
        Intent myIntent = new Intent(this, DataActivity.class);
        this.startActivity(myIntent);
    }

}