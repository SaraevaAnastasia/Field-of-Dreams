package com.example.polechudes;

import android.util.Log;

import java.util.HashSet;
import java.util.Set;

public class Player {
    private String guessedWord;
    private Set<Character> guessedLetters = new HashSet<>();

    public void guessLetter(String word, char letter) {
        guessedLetters.add(Character.toLowerCase(letter));
        updateGuessedWord(word);
    }

    public void guessWord(String word) {
        guessedWord = word;
    }

    public boolean isWordGuessed(String actualWord) {
        return guessedWord.equalsIgnoreCase(actualWord);
    }

    public String getGuessedWord() {
        return guessedWord;
    }

    public void clearGuessedLetters() {
        guessedLetters.clear();
    }

    private void updateGuessedWord(String word) {
        StringBuilder guessedWordBuilder = new StringBuilder();
        for (char c : word.toCharArray()) {
            if (guessedLetters.contains(Character.toLowerCase(c))) {
                guessedWordBuilder.append(c);
            } else {
                guessedWordBuilder.append(" _ ");
            }
        }
        guessedWord = guessedWordBuilder.toString();
    }
}
