package com.example.testLibrary.enums;

public enum Format {

	HARDCOVER("Hardcover"),
    PAPERBACK("Paperback"),
    EBOOK("E-book"),
    AUDIO_BOOK("Audio Book");

    private final String nomeS;

    Format(String nomeS) {
        this.nomeS = nomeS;
        
    }

    public String getName() {
        return nomeS;
    }
	
	

}
