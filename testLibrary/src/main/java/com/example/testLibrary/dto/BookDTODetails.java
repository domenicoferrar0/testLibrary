package com.example.testLibrary.dto;

import java.sql.Date;

import com.example.testLibrary.enums.Format;
import com.example.testLibrary.enums.Genre;
import com.example.testLibrary.model.Image;

public class BookDTODetails {

	private Long bookId;

	private String title;

	private String authorName;

	private Date publicationDate;

	private Genre genre;

	private String ISBN;

	private Format format;

	private String description;

	private Integer pageNumber;

	private Image bookCover;
	
	public String getCoverName() {
		return bookCover.getImageName();
	}
	
	public Image getImage() {
		return bookCover;
	}
	
	public Long getBookId() {
		return bookId;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthorName() {
		return authorName;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public Genre getGenre() {
		return genre;
	}

	public String getISBN() {
		return ISBN;
	}

	public Format getFormat() {
		return format;
	}

	public String getDescription() {
		return description;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public Image getBookCover() {
		return bookCover;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setBookCover(Image bookCover) {
		this.bookCover = bookCover;
	}
	
	
	
}
