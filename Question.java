package com.quiz;

public class Question {
    private int id;
    private String question;
    private String[] options;
    private int correctOption;

    public Question(int id, String question, String[] options, int correctOption) {
        this.id = id;
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }

    // Getters
    public int getId() { return id; }
    public String getQuestion() { return question; }
    public String[] getOptions() { return options; }
    public int getCorrectOption() { return correctOption; }
}

