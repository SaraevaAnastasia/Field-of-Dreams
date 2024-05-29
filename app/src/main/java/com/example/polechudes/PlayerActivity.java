package com.example.polechudes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;


public class PlayerActivity extends AppCompatActivity {
    private EditText letterEditText;
    private EditText wordEditText;
    private Button guessLetterButton;
    private Button guessWordButton;
    private TextView guessedWordView;  // Добавленный TextView
    private GameManager gameManager;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        gameManager = GameManager.getInstance();
        player = new Player();

        letterEditText = findViewById(R.id.letterEditText);
        wordEditText = findViewById(R.id.wordEditText);
        guessLetterButton = findViewById(R.id.guessLetterButton);
        guessWordButton = findViewById(R.id.guessWordButton);
        guessedWordView = findViewById(R.id.guessedWordView);  // Инициализация TextView

        guessLetterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String letter = letterEditText.getText().toString().trim();
                String wordToGuess = wordEditText.getText().toString().trim();
                if (!letter.isEmpty() && letter.length() == 1 && !wordToGuess.isEmpty()) {
                    player.guessLetter(wordToGuess, letter.charAt(0));
                    updateGuessedWord();  // Обновление отображения угаданных букв
                } else {
                    Toast.makeText(PlayerActivity.this, "Введите слово и одну букву", Toast.LENGTH_SHORT).show();
                }
                letterEditText.setText("");
            }
        });
    }

    private void updateGuessedWord() {
        guessedWordView.setText(player.getGuessedWord());  // Устанавливаем текст в TextView
    }
}
