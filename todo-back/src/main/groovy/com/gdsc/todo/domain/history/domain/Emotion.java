package com.gdsc.todo.domain.history.domain;

public enum Emotion {
    HAPPY("Happy"),
    UNHAPPY("Unhappy️");

    private final String emoji;

    Emotion(String emoji) {
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }
}
