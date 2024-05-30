package com.example.polechudes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private GameManager gameManager;
    private Player player;
    private TextView descriptionView;
    private TextView guessedWordView;
    private Button adminButton;
    private Word currentWord;

    private EditText letterEditText;
    private EditText wordEditText;
    private Button guessLetterButton;
    private Button guessWordButton;

    private static final String FILE_NAME = "words.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameManager = GameManager.getInstance();
        player = new Player();

        descriptionView = findViewById(R.id.descriptionView);
        guessedWordView = findViewById(R.id.guessedWordView);
        adminButton = findViewById(R.id.adminButton);

        loadWordsFromFile();

        startGame();

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdminPage();
            }
        });

        letterEditText = findViewById(R.id.letterEditText);
        wordEditText = findViewById(R.id.wordEditText);
        guessLetterButton = findViewById(R.id.guessLetterButton);
        guessWordButton = findViewById(R.id.guessWordButton); // Correct reference

        guessLetterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String letter = letterEditText.getText().toString().trim();
                if (!letter.isEmpty() && letter.length() == 1) {
                    player.guessLetter(currentWord.getWord(), letter.charAt(0));
                    checkGuessLetter();
                } else {
                    Toast.makeText(MainActivity.this, "Введите одну букву", Toast.LENGTH_SHORT).show();
                }
                letterEditText.setText("");
            }
        });

        guessWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guessedWord = wordEditText.getText().toString().trim();
                if (!guessedWord.isEmpty() ) {
                    player.guessWord(guessedWord);
                    checkGuessWord();
                } else {
                    Toast.makeText(MainActivity.this, "Введите слово", Toast.LENGTH_SHORT).show();
                }
                wordEditText.setText("");
            }
        });
    }

    private void checkGuessWord() {
        String currentWordStr = currentWord.getWord();
        Log.d("LETTER_TAG", currentWordStr);

        if (currentWordStr != null) {
            String guessedWord = player.getGuessedWord().trim();
            Log.d("LETTER_TAG", guessedWord);

            // Отладочный вывод
            Log.d("CHECK_GUESS", "Guessed word: " + guessedWord);

            if (guessedWord.equalsIgnoreCase(currentWordStr)) {
                Toast.makeText(this, "Слово " + currentWordStr + " угадано!", Toast.LENGTH_SHORT).show();
                startGame(); // Start a new game when the word is guessed
            } else {
                Toast.makeText(this, "Слово не угадано!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkGuessLetter() {
        String letter = letterEditText.getText().toString();
        Log.d("LETTER_TAG", letter);
        String currentWordStr = currentWord.getWord();
        Log.d("LETTER_TAG", currentWordStr);

        if (currentWordStr != null) {
            String guessedWord = player.getGuessedWord();
            Log.d("LETTER_TAG", guessedWord);

            // Отладочный вывод
            Log.d("CHECK_GUESS", "Guessed word: " + guessedWord);

            if (currentWordStr.toLowerCase().contains(letter)) {
                guessedWordView.setText(guessedWord); // Corrected
                if (guessedWord.equalsIgnoreCase(currentWordStr)) {
                    Toast.makeText(this, "Слово " + currentWordStr + " угадано!", Toast.LENGTH_SHORT).show();
                    startGame(); // Start a new game when the word is guessed
                } else {
                    Toast.makeText(this, "Буква " + letter + " угадана!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Буква не угадана!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startGame() {
        Word word = gameManager.getRandomWord();
        if (word != null) {
            currentWord = word;
            descriptionView.setText(word.getDescription());
            player.clearGuessedLetters(); // Clear guessed letters when starting a new game
            player.guessWord(new String(new char[word.getWord().length()]).replace("\0", " _ ")); // Initialize with underscores
            guessedWordView.setText(player.getGuessedWord());
        } else {
            Toast.makeText(this, "Слова закончились", Toast.LENGTH_SHORT).show();
        }
    }

    private void openAdminPage() {
        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
        startActivity(intent);
    }

    private void loadWordsFromFile() {
        try (FileInputStream fis = openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    gameManager.addWord(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
