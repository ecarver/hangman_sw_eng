package com.hangman.word_input;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class WordGuess extends Activity {

    String word;
    int count = 0;
    String hangman = "HANGMAN";
    TextView[] displayWordArray;
    boolean[] word_status;
    String guessed = "";
    int num_guesses = 0;
    static final int allowed_guesses = 7;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_guess);

        Intent word_receive = getIntent();
        word = word_receive.getStringExtra("secret_word");

        int textViewCount = word.length();

        final LinearLayout lm = (LinearLayout) findViewById(R.id.main_layout);

        TextView hangman_view = new TextView(this);
        hangman_view.setText("");

        LayoutParams lpView = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        LinearLayout word_layout = new LinearLayout(this);
        word_layout.setOrientation(LinearLayout.HORIZONTAL);
        word_layout.setGravity(Gravity.CENTER);

        /* The for loop below generates the TextViews for each letter. If the word has 6 letters,
         * it will make 6 TextViews with an underscore in each.
         * 
         * TODO: Put it into a separate function.
         */
        displayWordArray = new TextView[textViewCount];
        word_status = new boolean[textViewCount];
        for (int i = 0; i < textViewCount; i++) {
        	word_status[i] = false;
            displayWordArray[i] = new TextView(this);
            displayWordArray[i].setText("_");

            displayWordArray[i].setLayoutParams(lpView);
            displayWordArray[i].setPadding(0, 0, 10, 0);       // Spaces the letters out
            word_layout.addView(displayWordArray[i], lpView);  // Adds the TextView to the Linear Layout 
        }

        lm.addView(word_layout); // Adds word_layout to the Linear Layout with id main_layout
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.word_guess, menu);
        return true;
    }

    public void clickGuess(View view)
    {
        EditText input = (EditText)findViewById(R.id.input_guess);
        String guess = input.getText().toString().toLowerCase();
        if ( !guess.matches("^[a-z]$") ) {
            Toast.makeText(getApplicationContext(), "Please enter a letter only", Toast.LENGTH_SHORT).show();
        }
        else if ( guessed.contains(guess) ) {
            //The user already guessed this letter. Don't penalize him
            Toast.makeText(getApplicationContext(), "You already guessed \" " + guess + "\"", Toast.LENGTH_SHORT).show();
        }
        else if ( word.contains(guess) ) {
            // The user guessed correctly
            testGuess(view, guess);
            if ( test_true(word_status) ) {
                // User has won. Notify him.
                input.setEnabled(false);
                Toast.makeText(getApplicationContext(), "You win!", Toast.LENGTH_LONG).show();
            }
        }
        else {
            // The user guessed incorrectly
            Toast.makeText(getApplicationContext(), "Incorrect guess", Toast.LENGTH_SHORT).show();
            TextView hangman_display = (TextView)findViewById(R.id.hangman_display);
            hangman_display.setText((String)hangman_display.getText() + hangman.charAt(num_guesses));
            if ( ++num_guesses >= allowed_guesses ) {
                Toast.makeText(getApplicationContext(), "You lose. The word was \"" + word + "\".", Toast.LENGTH_LONG).show();
                input.setEnabled(false);
            }
        }
        guessed += guess;
        input.setText("");
    }

    private void testGuess(View v, String guess)
    {
        for (int i = 0; i < word.length(); i++) {
            if ( word.charAt(i) == guess.charAt(0) ) {
                displayWordArray[i].setText(guess);
                word_status[i] = true;
            }
        }
    }

    private boolean test_true(boolean[] arr) {
        for (boolean test: arr)
            if ( !test ) return false;
        return true;
    }
}
