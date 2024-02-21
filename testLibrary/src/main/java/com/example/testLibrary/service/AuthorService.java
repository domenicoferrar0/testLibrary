package com.example.testLibrary.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.example.testLibrary.dto.AuthorDTOList;
import com.example.testLibrary.dto.AuthorDTONames;
import com.example.testLibrary.model.Author;
import com.example.testLibrary.repository.AuthorRepository;

@Service
public class AuthorService {

	@Autowired
	AuthorRepository authorRep;
	
	@Transactional
	public void save(Author author) {
		authorRep.save(author);
	}
	
	//CERCA DUPLICATI
	public void findByCf(String cf) {
		if(authorRep.findByCf(cf).isPresent()) {
			throw new IllegalArgumentException("Codice fiscale duplicato inserirne uno valido");
		}
	}
	
	@Transactional
	public void delete(Author author) {
		authorRep.delete(author);
	}
	
	public Optional<Author> findById(Long id) {
		return authorRep.findById(id);
	}
	
	public List<AuthorDTONames> getAuthorNames(){
		return authorRep.findAuthorNames();
	}
	
	public List<Author> getAuthorList() {
		return authorRep.findAll();
	}
	
	@Transactional
	public void deleteAuthorById(Long id) {
		this.authorRep.deleteById(id);
	}
	
	//QUESTI DUE SONO PER LA LISTA CON E SENZA SEARCHTERM
	public Page<AuthorDTOList> findPaginated(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return this.authorRep.findForList(pageable);
	}

	public Page<AuthorDTOList> findPaginated(String query, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return this.authorRep.findByContaining(query, pageable);
	}

}
