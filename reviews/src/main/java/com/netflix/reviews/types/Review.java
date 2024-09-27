package com.netflix.reviews.types;

public record Review(Integer score, String text, Show show) {
    public Review(Integer score, String text) {
        this(score, text, null);
    }
}

