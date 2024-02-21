package com.example.testLibrary.enums;

public enum Genre {
	FICTION("Narrativa", "Opere letterarie create dall'immaginazione"),
    MYSTERY("Mistero", "Storie incentrate sulla soluzione di un crimine o sulla scoperta di segreti"),
    SCIENCE_FICTION("Fantascienza", "Narrativa speculativa basata su concetti scientifici"),
    ROMANCE("Romanzo rosa", "Storie incentrate sulle relazioni romantiche"),
    FANTASY("Fantasy", "Mondi immaginari, magia e creature mitiche"),
    HORROR("Horror", "Intese a spaventare, scioccare o suscitare paura"),
    NON_FICTION("Saggistica", "Opere fattuali e informative");

    private final String nomeS;
    private final String descrizione;

    Genre(String nomeS, String descrizione) {
        this.nomeS = nomeS;
        this.descrizione = descrizione;
    }

    public String getName() {
        return nomeS;
    }

    public String getDescrizione() {
        return descrizione;
    }
}
