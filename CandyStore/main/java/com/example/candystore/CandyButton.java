package com.example.candystore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("AppCompatCustomView")
public class CandyButton extends Button {

    private Candy candy;

    public CandyButton(Context context, Candy newCandy) {
        super(context);
        candy = newCandy;
    }

    public double getPrice() {
        return candy.getPrice();
    }
}
