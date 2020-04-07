package com.example.hangman;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.solver.Cache;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected String mystery_word;
    protected String guess_word;
    int gallows_state;
    String[] words;
    TextView txtWord;
    EditText edtLetter;
    String guessText;
    String guess_char;
    char xxx;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private static final String TAG = "MainActivity";



    int ids[] = {
            R.drawable.hangman_gray,
            R.drawable.hangman0,
            R.drawable.hangman1,
            R.drawable.hangman2,
            R.drawable.hangman3,
            R.drawable.hangman4,
            R.drawable.hangman5,
            R.drawable.hangman6,
            R.drawable.hangman7,
            R.drawable.hangman8,
            R.drawable.hangman9,
            R.drawable.hangman10

    };

    int Counter = 0;
    int Length;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtLetter = findViewById(R.id.edtLetter);
        txtWord = findViewById(R.id.txtWord);

        // read the string-array into words array
        words = getResources().getStringArray(R.array.words);



        Length = ids.length;


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar make = Snackbar.make(view, "Start Game?", Snackbar.LENGTH_LONG);
                make.setAction("YES", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        init();



                        Counter =0;
                        imgView();
                        edtLetter.setText("");



                    }
                });
                make.show();
            }
        });


    }

    private void nightSet() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
        recreate();
    }

    private void init() {
        int number = (int) (words.length * Math.random());
        mystery_word = words[number];

        int tul = mystery_word.length();
        switch (tul) {
            case 2:
                txtWord.setText("**");
                break;
            case 3:
                txtWord.setText("***");
                break;
            case 4:
                txtWord.setText("****");
                break;
            case 5:
                txtWord.setText("*****");
                break;
            case 6:
                txtWord.setText("******");
                break;
            case 7:
                txtWord.setText("*******");
                break;
            case 8:
                txtWord.setText("********");
                break;
            case 9:
                txtWord.setText("*********");
                break;
            case 10:
                txtWord.setText("**********");
                break;
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
        if (id == R.id.action_settings) {

            addFragment();
            //nightSet();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void checkWord(View view) {
        int id = view.getId();
        if (id == R.id.btnWord) {

            imgView();


            guessText = edtLetter.getText().toString();

            try {


                if (guessText.isEmpty()) {
                    //Toast.makeText(this, "write", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(view, "Guess something and write it", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    //snackbar.setAction()


                } else if (mystery_word.equals(guessText)) {
                    txtWord.setText(mystery_word);
                    Snackbar s = Snackbar.make(view, "You win. Do you want a new game?", Snackbar.LENGTH_LONG);
                    s.setAction("YES", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            init();
                            edtLetter.setText("");

                        }
                    });
                    s.show();
                    Counter = 0;
                    imgView();


                } else {
                    Counter++;
                    imgView();
                    Snackbar snak = Snackbar.make(view, "Take care your answer was wrong!!!!!", Snackbar.LENGTH_SHORT);
                    snak.show();

                }
            } finally {
                if (Counter == 11) {
                    txtWord.setText(mystery_word);
                    Snackbar snk = Snackbar.make(view, "Sorry you lost the game. do you want a new game?", Snackbar.LENGTH_INDEFINITE);
                    snk.setAction("YES", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            init();
                            edtLetter.setText("");
                            Counter = 0;
                            imgView();
                        }
                    });
                    snk.show();


                }
            }


            // int index = mystery_word.indexOf(guess_char, 0);// search from index 0)
            //   StringBuilder tmp = new StringBuilder(guess_word);
            //   tmp.setCharAt(index, guess_char);
            //     Toast.makeText(this, guess_char, Toast.LENGTH_SHORT).show();
            //   Snackbar.make(view,index,Snackbar.LENGTH_SHORT);
            // txtWord.setText(guess_char);
        }

        if (id == R.id.btnLetter) {
            guess_char = edtLetter.getText().toString();
            guessText = txtWord.getText().toString();

            try {

            if (guess_char.isEmpty()) {
                //Toast.makeText(this, "write", Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar.make(view, "Guess something and write it", Snackbar.LENGTH_SHORT);
                snackbar.show();
                //snackbar.setAction()


            }else if (mystery_word.contains(guess_char)) {
                    // int indx;

                    int indx = mystery_word.indexOf(guess_char);
                    xxx = guess_char.charAt(0);
                    StringBuilder tmp = new StringBuilder(guessText);

                    tmp.setCharAt(indx, xxx);
                    txtWord.setText(tmp);
                    edtLetter.setText("");
                    Snackbar snak = Snackbar.make(view, "Great guess. Go ahead!!!!!", Snackbar.LENGTH_SHORT);
                    snak.show();


                    // Toast.makeText(this, tmp, Toast.LENGTH_SHORT).show();
                } else {
                    Snackbar snak = Snackbar.make(view, "Take care your answer was wrong!!!!!", Snackbar.LENGTH_SHORT);
                    snak.show();
                    //Toast.makeText(this, "nokey", Toast.LENGTH_SHORT).show();
                    edtLetter.setText("");
                    Counter++;
                    imgView();
                }
            } finally {
                if (Counter == 11) {
                    txtWord.setText(mystery_word);
                    Snackbar snk = Snackbar.make(view, "Sorry you lost the game. do you want a new game?", Snackbar.LENGTH_INDEFINITE);
                    snk.setAction("YES", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            init();
                            edtLetter.setText("");
                            Counter = 0;
                            imgView();
                        }
                    });
                    snk.show();
                }
            }
        }
    }

    private void imgView() {

        ImageView gallows = findViewById(R.id.imageView);
        if(Counter >= Length )
            Counter=0;

        gallows.setImageResource(ids[Counter]);
    }

    private void addFragment() {
        Log.i(TAG, "mohsen");

        HelpFragment fragment = new HelpFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.container,fragment).addToBackStack(null).commit();





    }

    //.addToBackStack(null)

    // char[] xxx = mystery_word.toCharArray();
    //for (int i = 1; i < xxx.length - 1; i++) {
    //  xxx[i] = '_';

}
