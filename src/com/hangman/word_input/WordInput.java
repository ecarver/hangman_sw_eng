package com.hangman.word_input;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class WordInput extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    // Called when the OK button is clicked
    // Checks to make sure the input is only alphabetic characters
    public void click_ok(View view) {
        EditText input = (EditText)findViewById(R.id.input);
        String temp_word = input.getText().toString().toLowerCase();
        if ( !temp_word.matches("^[a-z]+$") ) {
            Toast.makeText(getApplicationContext(), "Please enter a valid word", Toast.LENGTH_SHORT).show();
        }
        // TODO: Enforce other constraints on the input (length?)
        else {
            word = temp_word;
            Toast.makeText(getApplicationContext(), (CharSequence)word, Toast.LENGTH_SHORT).show();
            // TODO: show the guessing activity
        }
    }

    private String word;
}
