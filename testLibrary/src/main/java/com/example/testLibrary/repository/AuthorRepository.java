package com.example.testLibrary.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import com.example.testLibrary.dto.AuthorDTOList;
import com.example.testLibrary.dto.AuthorDTONames;
import com.example.testLibrary.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>{
	
	@Query("SELECT new com.example.testLibrary.dto.AuthorDTOList(a.authorId, a.firstName, a.lastName, a.birthDate, a.gender, a.cf, a.country) "
			+ "from Author a WHERE a.firstName LIKE %?1% OR a.lastName LIKE %?1% "
			+ "OR a.cf LIKE %?1% OR CAST(a.country as string) LIKE %?1% OR CAST(a.gender as string) LIKE %?1%")
	public Page<AuthorDTOList> findByContaining(String query, Pageable pageable); 
	
	@Query("SELECT new com.example.testLibrary.dto.AuthorDTOList(a.authorId, a.firstName, a.lastName, a.birthDate, a.gender, a.cf, a.country) "
			+ "from Author a")
	public Page<AuthorDTOList> findForList(Pageable pageable);
	
	Optional<Author> findByCf(String cf);
	
	@Query("SELECT new com.example.testLibrary.dto.AuthorDTONames(a.authorId, a.firstName, a.lastName) FROM Author a")
	List<AuthorDTONames> findAuthorNames();
	
	@Query("SELECT a from Author a WHERE a.firstName LIKE %?1%")
	Optional<Author> findAuthorByName(String name);
	
	/* @Query("SELECT a from Author a WHERE a.name LIKE %?1%")
	Optional<Author> findAuthorByName(String name);

Una cosa del genere nel repository?*/
}
