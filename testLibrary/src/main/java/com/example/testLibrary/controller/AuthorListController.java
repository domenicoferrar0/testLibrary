package com.example.testLibrary.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.testLibrary.dto.AuthorDTOList;
import com.example.testLibrary.model.Book;
import com.example.testLibrary.service.AuthorService;
import com.example.testLibrary.service.BookService;
import com.example.testLibrary.service.ImageService;

@Controller
public class AuthorListController {

	@Autowired
	AuthorService authorServ;

	@Autowired
	BookService bookServ;

	@Autowired
	ImageService imageServ;

	@GetMapping("/authorspage/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo", required = false) int pageNo,
			@RequestParam(value = "query", required = false) String query, Model model) {
		int pageSize = 10;
		Page<AuthorDTOList> page;
		if (query == null || query.equals("")) {
			query = "";
			page = authorServ.findPaginated(pageNo, pageSize);

		} else {
			page = authorServ.findPaginated(query, pageNo, pageSize);
		}
		List<AuthorDTOList> authorList = page.getContent();

		model.addAttribute("query", query);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("authorList", authorList);
		return "authorList";
	}

	@GetMapping("/deleteAuthorId/{id}/{pageNo}")
	public String deleteAuthor(@PathVariable(value = "id") Long id, @PathVariable(value = "pageNo") int pageNo,
			@RequestParam(value = "query", required = false) String query, RedirectAttributes redirectAtt) {

		if (query == null || query.equals("")) {
			query = "";
		}

		List<Book> libri = bookServ.findByAuthor_AuthorId(id); // PRENDO LA LISTA DEI LIBRI A CUI RIMUOVERE L'IMMAGINE
		try {
			authorServ.deleteAuthorById(id); // ELIMINO AUTORE
		} catch (IllegalArgumentException e) {
			redirectAtt.addFlashAttribute("showErrorNotification", true);
			return "redirect:/authorspage/" + pageNo + "?query=" + query;
		}

		for (Book b : libri) {

			try {
				if (b.getBookCover() != null) {
					imageServ.removeImage(b.getBookCover()); // PULISCO LE IMMAGINI NEL SISTEMA ORFANE
				}
			} catch (SecurityException | IllegalArgumentException e) {
				e.printStackTrace();
				redirectAtt.addFlashAttribute("showErrorNotification", true);
				return "redirect:/authorspage/" + pageNo + "?query=" + query;
			}
		}

		return "redirect:/authorspage/" + pageNo + "?query=" + query;
	}

}
