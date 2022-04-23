package com.example.hangman;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class GameStateFragment extends Fragment {
    public GameStateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_state, container, false);
    }

    public void onStart() {

        super.onStart();
        View fragmentView = getView();
        TextView gameStateTV = (TextView) fragmentView.findViewById(R.id.state_of_game);
        MainActivity fragmentActivity = (MainActivity) getActivity();
        gameStateTV.setText(fragmentActivity.getGame().currentIncompleteWord());
        EditText answerET = (EditText) fragmentView.findViewById(R.id.letter);
        OnEditorHandler editorHandler = new OnEditorHandler();
        answerET.setOnEditorActionListener(editorHandler);
    }


    private class OnEditorHandler implements TextView.OnEditorActionListener {
        public boolean onEditorAction(TextView v, int keyCode, KeyEvent event) {
            // hide the keyboard
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
// play
            MainActivity fragmentActivity = (MainActivity) getActivity();
            fragmentActivity.play();

            return true;
        }


    }
}

