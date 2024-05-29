package com.example.polechudes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class GameManager {
    private static GameManager instance;
    private Queue<Word> wordQueue = new LinkedList<>();
    private List<Word> words = new ArrayList<>();
    private Random random = new Random();

    private GameManager() {}

    private Word currentWord;

    public Word getCurrentWord() {
        return currentWord;
    }

    public void setCurrentWord(Word word) {
        this.currentWord = word;
    }
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void addWord(String word, String description) {
        Word newWord = new Word(word, description);
        words.add(newWord);
        wordQueue.offer(newWord);
    }

    public Word getRandomWord() {
        if (wordQueue.isEmpty()) {
            wordQueue.addAll(words);
        }

        Word selectedWord = wordQueue.poll();
        return selectedWord;
    }

    public boolean checkWord(String guessedWord, String actualWord) {
        return guessedWord.equalsIgnoreCase(actualWord);
    }
}
