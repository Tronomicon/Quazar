package com.example.QuazarApp;

import java.util.ArrayList;
import java.util.List;

public class QuestionData {

    private final String question;
    private final List<ChoiceData> choices;
    private final int image;

    public QuestionData(String question, String[] choices, int image) {
        this.question = question;
        this.choices = new ArrayList<>();
        for(int i=0; i<choices.length; i++){
            this.choices.add(new ChoiceData(choices[i], i==0));
        }
        this.image = image;
    }

    public String getQuestion() {
        return question;
    }

    public List<ChoiceData> getChoices() {
        return choices;
    }

    public int getImage() {
        return image;
    }


}
