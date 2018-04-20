package com.jaja.songwritermobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {

    private static ArrayList<Chord> c = new ArrayList<>();
    private static ArrayList<Key> keys = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File file = new File("CMajorTest.csv");

        try {
            Scanner fileReader = new Scanner(file);
            int count = 0;
            while(fileReader.hasNextLine()) {
                String [] temp = fileReader.nextLine().split(",");
                if (temp[0].equalsIgnoreCase("Key")) continue;

                if ((count-1)%7==0 || count == 0) {
                    Key x = new Key(temp[0]);
                    keys.add(x);
                }

                Chord x = new Chord(temp);
                Objects.requireNonNull(keyFinder(temp[0])).chords.add(x);
                c.add(x);

                count++;
            }
            Scanner a = new Scanner(System.in);
            while(true) {
                /*System.out.println("press 0 to exit, 1 to search by chord, and 2 to search by chord and key (WIP).");
                String nextOpp = a.next();
                if (nextOpp.equals("0")) break;
                else if (nextOpp.equalsIgnoreCase("1")) {
                    System.out.println("What chord would you like to search for?");
                    String nextChord = a.next();
                    Objects.requireNonNull(chordFinder(nextChord)).suggestChords();
                }
                else if (nextOpp.equalsIgnoreCase("2")) {
                    System.out.println("What Key would you like to search for?");
                    String nextKey = a.next();
                    System.out.println("What chord would you like to search for?");
                    String nextChord = a.next();
                    Objects.requireNonNull(chordFinder(nextChord, nextKey)).suggestChords();
                }*/
                TextView chordReader = (TextView) findViewById(R.id.chordReader);

                EditText chordPrompt = (EditText) findViewById(R.id.ChordPrompt);
                String nextChord = chordPrompt.getText().toString();
                chordReader.setText(Objects.requireNonNull(chordFinder(nextChord)).suggestChords());


            }
        } catch (FileNotFoundException e) {
            System.out.println("oops");
            e.printStackTrace();
        }
    }
    private static Key keyFinder(String keyName) {
        for (Key temp: keys) {
            if (temp.name.equalsIgnoreCase(keyName)) {
                return temp;
            }
        }
        return null;
    }
    private static Chord chordFinder(String chordName, String keyName) {
        try {
            for (Chord temp: Objects.requireNonNull(keyFinder(keyName)).chords) {
                if (temp.name.equalsIgnoreCase(chordName)) {
                    return temp;
                }
            }
        } catch (NullPointerException e) {
            System.out.println("invalid key name");
        }
        return null;
    }
    private static Chord chordFinder(String chordName) {
        for (Chord temp: c) {
            if (temp.name.equalsIgnoreCase(chordName)) {
                return temp;
            }
        }
        return null;
    }
}
