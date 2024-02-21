package com.example.testLibrary.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.example.testLibrary.dto.BookDTODetails;
import com.example.testLibrary.dto.BookDTOList;
import com.example.testLibrary.dto.BookDTOView;
import com.example.testLibrary.enums.Genre;
import com.example.testLibrary.mapper.BookMapper;
import com.example.testLibrary.model.Book;
import com.example.testLibrary.repository.BookRepository;

@Service
public class BookService {
//DA FARE PRIVATE LE INJECTION?
	@Autowired
	BookRepository bookRep;

	@Autowired
	BookMapper bookMapper;

	public List<Book> findByAuthor_AuthorId(Long authorId) {
		return bookRep.findByAuthor_AuthorId(authorId);
	}
	
	
	//Li mappa direttamente, il check null lo fa il controller
	public BookDTODetails findDetailsById(Long id) {
		Optional<Book> book = findById(id);
		if (book.isPresent()) {
			return bookMapper.bookToBookDetails(book.get());
		}
		return null;
	}

	@Transactional
	public void save(Book book) {
		bookRep.save(book);
	}
	
	//PER DUPLICATI
	public void findByISBN(String ISBN) {
		if (bookRep.findByISBN(ISBN).isPresent()) {
			throw new IllegalArgumentException("Impossibile inserire duplicati, ISBN già esistente");
		}
	}

	@Transactional
	public void delete(Book book) {

		bookRep.delete(book);
	}

	public List<Book> getBookList() {
		return bookRep.findAll();
	}

	@Transactional
	public void deleteBookById(Long id) {
		this.bookRep.deleteById(id);
	}
	
	public Page<BookDTOView> findForView(Genre genre, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return this.bookRep.findForView(genre, pageable);
	}
	
	public Page<BookDTOList> findPaginated(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return this.bookRep.findForList(pageable);
	}

	public Page<BookDTOList> findPaginated(String query, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return this.bookRep.findByContaining(query, pageable);
	}

	public Optional<Book> findById(Long id) {
		return this.bookRep.findById(id);
	}

	public Long findNextById(Long id) {
		return bookRep.findNextById(id);
	}

	public Long findPrevById(Long id) {
		return bookRep.findPrevById(id);
	}

	public Long findLastId() {
		return bookRep.findLastId();
	}

	public Long findFirstId() {
		return bookRep.findFirstId();
	}
}
