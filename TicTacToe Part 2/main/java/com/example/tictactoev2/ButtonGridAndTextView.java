package com.example.tictactoev2;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class ButtonGridAndTextView extends GridLayout {
    private int side;
    private Button[][] buttons;
    private TextView status;
    public ButtonGridAndTextView(Context context, int width, int newSide, OnClickListener listener ) {
        super(context);
        side = newSide;
        // Set # of rows and columns of this GridLayout
        setColumnCount(side);
        setRowCount(side + 1);
        // Create the buttons and add them to this GridLayout
        buttons = new Button[side][side];
        for( int row = 0; row < side; row++ ) {
            for( int col = 0; col < side; col++ ) {
                buttons[row][col] = new Button(context);
                //Set the textsize for each button to w * 0.2
                buttons[row][col].setTextSize((float) (width*0.2));
                // Register the event for each button
                buttons[row][col].setOnClickListener(listener);
                addView( buttons[row][col], width, width );
            }
        }
        // set up layout parameters of 4th row of gridLayout
        //YOUR CODE
        status = new TextView( context );
        Spec rowSpec = GridLayout.spec(side, 1);
        Spec columnSpec = GridLayout.spec(0, side);
        LayoutParams lp = new LayoutParams( rowSpec, columnSpec );
        status.setLayoutParams( lp);
        status.setWidth( side * width );
        status.setHeight( width );
        status.setGravity( Gravity.CENTER );
        status.setBackgroundColor( Color.GREEN );
        status.setTextSize( ( int ) ( width * .15 ) );

        addView(status);
    }

    public void setStatusText( String text ) { status.setText( text );
    }
    public void setStatusBackgroundColor( int color ) { status.setBackgroundColor( color );
    }
    public void setButtonText( int row, int column, String text ) { buttons[row][column].setText( text );
    }
    public boolean isButton( Button b, int row, int column ) { return ( b == buttons[row][column] );
    }
    public void resetButtons( ) {
        for( int row = 0; row < side; row++ )
            for( int col = 0; col < side; col++ )
                buttons[row][col].setText( "" );
    }
    public void enableButtons( boolean enabled ) {
        for( int row = 0; row < side; row++ )
            for( int col = 0; col < side; col++ )
                buttons[row][col].setEnabled( enabled );
    }

}