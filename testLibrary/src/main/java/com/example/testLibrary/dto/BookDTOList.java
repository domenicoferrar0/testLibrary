package com.example.testLibrary.dto;

import java.sql.Date;

import com.example.testLibrary.enums.Format;
import com.example.testLibrary.enums.Genre;

public class BookDTOList {

	private Long bookId;

	private String title;

	private String authorFirstName;

	private String authorLastName;

	private Date publicationDate;

	private Genre genre;

	private String ISBN;

	private Format format;

	private Integer pageNumber;

	public String getAuthorName() {
		return authorFirstName + " " + authorLastName;
	}

	public BookDTOList(Long bookId, String title, String authorFirstName, String authorLastName, Date publicationDate,
			Genre genre, String ISBN, Format format, Integer pageNumber) {

		this.bookId = bookId;
		this.title = title;
		this.authorFirstName = authorFirstName;
		this.authorLastName = authorLastName;
		this.publicationDate = publicationDate;
		this.genre = genre;
		this.ISBN = ISBN;
		this.format = format;
		this.pageNumber = pageNumber;
	}

	public Long getBookId() {
		return bookId;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthorFirstName() {
		return authorFirstName;
	}

	public String getAuthorLastName() {
		return authorLastName;
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

	public Integer getPageNumber() {
		return pageNumber;
	}

}
