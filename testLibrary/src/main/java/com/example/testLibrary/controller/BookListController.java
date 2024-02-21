package com.example.testLibrary.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.testLibrary.dto.BookDTOList;
import com.example.testLibrary.model.Book;
import com.example.testLibrary.model.Image;
import com.example.testLibrary.service.AuthorService;
import com.example.testLibrary.service.BookService;
import com.example.testLibrary.service.ImageService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BookListController {

	@Autowired
	AuthorService authorServ;

	@Autowired
	BookService bookServ;

	@Autowired
	ImageService imageServ;

	@GetMapping("/")
	public String root(HttpSession session) {
		return "redirect:/bookspage/1";
	}

	@GetMapping("/bookspage/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo", required = false) int pageNo,
			@RequestParam(value = "query", required = false) String query, Model model) {
		int pageSize = 10;
		Page<BookDTOList> page;
		if (query == null || query.equals("")) {//IN MODO DA NON AVERE MAI QUERY NULL, NEL CASO SIA VUOTA ME LI RESTITUISCE TUTTI
			query = "";

			page = bookServ.findPaginated(pageNo, pageSize); //FULL RISULTATI
		} else {

			page = bookServ.findPaginated(query, pageNo, pageSize); //RICERCA CON QUERY
		}

		List<BookDTOList> bookList = page.getContent();
		
		model.addAttribute("query", query); //LO PASSO COME PARAMETRO PERCHE' LO PASSO AL BOTTONE DELETE
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("bookList", bookList);
		return "bookList";
	}

	@GetMapping("/deleteBookId/{id}/{pageNo}")
	public String deleteBook(@PathVariable(value = "id") Long id, @PathVariable(value = "pageNo") int pageNo,
			RedirectAttributes redirectAtt, @RequestParam(value = "query", required = false) String query) {
		
		if (query == null) {
			query = "";
		}
		
		
		Optional<Book> book = bookServ.findById(id);
		if (!book.isPresent()) {
			redirectAtt.addFlashAttribute("showErrorNotification", true); //NOTIFICA DI ERRORE SE NON TROVA IL LIBRO
			return "redirect:/bookspage/" + pageNo + "?query=" + query;
		}
		Book foundBook = book.get();
		Image image = foundBook.getBookCover(); //ESTRAGGO L'IMMAGINE PER POTER CANCELLARE IL FILE USANDO IL NOME DEL FILE
		try {
			bookServ.delete(foundBook);

		} catch (IllegalArgumentException e) {
			redirectAtt.addFlashAttribute("showErrorNotification", true);
			return "redirect:/bookspage/" + pageNo + "?query=" + query;
		}
		if (image != null) {
			try {

				imageServ.removeImage(image); //RIMUOVE L'IMMAGINE A PARTIRE DALL'ENTITY
			} catch (SecurityException | IllegalArgumentException e) {
				redirectAtt.addFlashAttribute("showErrorNotification", true);
				return "redirect:/bookspage/" + pageNo + "?query=" + query;
			}
		}

		return "redirect:/bookspage/" + pageNo + "?query=" + query;
	}
}
