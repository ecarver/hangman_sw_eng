package com.hangman.word_input;

import android.app.Activity;
import android.content.Intent;
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
    public void clickButton(View view) {
        EditText input = (EditText)findViewById(R.id.input);
        String temp_word = input.getText().toString().toLowerCase();
        if ( !temp_word.matches("^[a-z]+$") ) {
            Toast.makeText(getApplicationContext(), "Please enter a valid word", Toast.LENGTH_SHORT).show();
        }
        // Length constraint has been enforced in the XML file.
        else {
            word = temp_word;
            Intent word_guess = new Intent(getApplicationContext(), com.hangman.word_input.WordGuess.class);
            word_guess.putExtra("secret_word", word);
            startActivity(word_guess);
        }
    }

    private String word;
}
