package com.example.testLibrary.enums;

public enum Gender {
	MALE("Male"),
    FEMALE("Female"),
    OTHER("Other"),
    PREFER_NOT_TO_SAY("Prefer not to say");

    private final String nome;

    Gender(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
