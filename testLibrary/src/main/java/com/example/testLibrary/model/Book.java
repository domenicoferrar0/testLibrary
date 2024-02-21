package com.example.testLibrary.model;

import java.sql.Date;

import com.example.testLibrary.enums.Format;
import com.example.testLibrary.enums.Genre;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private Long bookId;

	@Column(nullable = false)
	@Size(min = 1, max = 50)
	@Pattern(regexp = "^\\S(.*\\S)?$", message = "Formato titolo non valido, rimuovi spazi superflui")
	private String title;

	@ManyToOne
	@JoinColumn(name = "author_id", nullable = false)
	private Author author;

	@Column(nullable = false)
	private Date publicationDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Genre genre;

	@Column(nullable = false, unique = true)
	@Pattern(regexp = "^(?:\\d{9}[\\dXx])$", message = "Formato ISBN non valido")
	private String ISBN;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Format format;

	@Column(columnDefinition = "TEXT", nullable = true)
	@Size(max = 250, message = "La descrizione non può superare i 250 caratteri")
	private String description;

	@Column(nullable = true)
	@Positive(message = "Il numero delle pagine non può essere negativo")
	private Integer pageNumber;
	
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id", unique = false, nullable = true)
	private Image bookCover;
	
	public Image getBookCover() {
		return bookCover;
	}

	public void setBookCover(Image bookCover) {
		this.bookCover = bookCover;
	}

	public String getCoverName() {
		return getBookCover().getImageName();
	}

	

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getAuthorName() {
		return getAuthor().getFullName();
	}

}
