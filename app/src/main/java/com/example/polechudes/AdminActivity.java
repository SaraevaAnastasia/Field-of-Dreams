package com.example.polechudes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class AdminActivity extends AppCompatActivity {
    private EditText wordEditText;
    private EditText descriptionEditText;
    private Button addButton;
    private Button backButton;
    private GameManager gameManager;
    private static final String FILE_NAME = "words.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        gameManager = GameManager.getInstance();

        wordEditText = findViewById(R.id.wordEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        addButton = findViewById(R.id.addButton);
        backButton = findViewById(R.id.backButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = wordEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();

                if (!word.isEmpty() && !description.isEmpty()) {
                    gameManager.addWord(word, description);
                    saveWordToFile(word, description);
                    Toast.makeText(AdminActivity.this, "Слово добавлено", Toast.LENGTH_SHORT).show();
                    wordEditText.setText("");
                    descriptionEditText.setText("");
                } else {
                    Toast.makeText(AdminActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainActivity();
            }
        });
    }

    private void saveWordToFile(String word, String description) {
        try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_APPEND);
             OutputStreamWriter osw = new OutputStreamWriter(fos)) {
            osw.write(word + ";" + description + "\n");
            osw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
