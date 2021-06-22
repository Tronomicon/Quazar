package com.example.QuazarApp;

public class ChoiceData {

    private final String choiceText;
    private final boolean correct;

    public ChoiceData(String choiceText, boolean correct) {

        this.choiceText = choiceText;
        this.correct = correct;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public boolean isCorrect() {
        return correct;
    }
}
