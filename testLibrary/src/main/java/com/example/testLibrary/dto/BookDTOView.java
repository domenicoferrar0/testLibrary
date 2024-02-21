package com.example.testLibrary.dto;

import com.example.testLibrary.model.Image;

public class BookDTOView {

	private Long bookId;

	private String title;
	
	private Image bookCover;
	

	public Long getBookId() {
		return bookId;
	}

	public String getTitle() {
		return title;
	}

	public Image getImage() {
		return bookCover;
	}

	public BookDTOView(Long bookId, String title, Image bookCover) {
		
		this.bookId = bookId;
		this.title = title;
		this.bookCover = bookCover;
	}
	
	public String getCoverName() {
		return bookCover.getImageName();
	}
	
	
	
	
	
	
}
