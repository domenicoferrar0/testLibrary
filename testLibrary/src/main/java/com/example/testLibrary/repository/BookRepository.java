package com.example.testLibrary.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.testLibrary.dto.BookDTOList;
import com.example.testLibrary.dto.BookDTOView;
import com.example.testLibrary.enums.Genre;
import com.example.testLibrary.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	//LA MAGGIOR PARTE DEI DTO VENGONO ESTRATTI CREANDOLI DIRETTAMENTE A PARTIRE DAL REPOSITORY
	//I CAST SERVONO PER LEGGERE GLI ENUM COME STRING E USARLI COME METODO DI RICERCA
	
	@Query("SELECT new com.example.testLibrary.dto.BookDTOList(b.id, b.title, a.firstName, a.lastName, b.publicationDate, b.genre, b.ISBN, b.format, b.pageNumber)"
			+ " FROM Book b JOIN b.author a WHERE b.title LIKE %?1% OR b.ISBN LIKE %?1% OR a.firstName "
			+ "LIKE %?1% OR a.lastName LIKE %?1% OR CAST(b.format as string) LIKE %?1% OR CAST(b.genre as string) LIKE %?1% ")
	Page<BookDTOList> findByContaining(String text, Pageable pageable);
		
	
	@Query("SELECT new com.example.testLibrary.dto.BookDTOList(b.id, b.title, a.firstName, a.lastName, b.publicationDate, b.genre, b.ISBN, b.format, b.pageNumber) FROM Book b JOIN b.author a")	
	Page<BookDTOList>findForList(Pageable pageable);
	
	
	@Query("SELECT new com.example.testLibrary.dto.BookDTOView(b.id, b.title, c) FROM Book b LEFT JOIN b.bookCover c WHERE b.genre = ?1")
	Page<BookDTOView>findForView(Genre genre, Pageable pageable);
	
	
	List<Book> findByAuthor_AuthorId(Long authorId);
	
	Optional<Book> findById(Long id);
	
	Optional<Book> findByISBN(String ISBN);	
	
	@Query("select MIN(b.id) FROM Book b WHERE b.id > ?1")
	Long findNextById(Long id);
	
	@Query("SELECT MAX(b.id) FROM Book b WHERE b.id < ?1")
	Long findPrevById(Long id);
	
	@Query("SELECT MAX(b.id) FROM Book b")
	Long findLastId();

	@Query("SELECT MIN(b.id) FROM Book b")
	Long findFirstId();
}
