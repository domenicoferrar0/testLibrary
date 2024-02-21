package com.example.testLibrary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.testLibrary.dto.BookDTODetails;
import com.example.testLibrary.dto.BookDTOView;
import com.example.testLibrary.service.BookService;
import com.example.testLibrary.service.ImageService;

@Controller
public class DetailsController {

	@Autowired
	BookService bookServ;

	@Autowired
	ImageService imageServ;

	@GetMapping("/bookDetails/{id}/{pageNo}")
	public String showBookDetails(@PathVariable(value = "id") Long id, @PathVariable(value = "pageNo") Integer pageNo,
			Model model) {
		BookDTODetails book = bookServ.findDetailsById(id); //TROVA IL LIBRO A PARTIRE DALL'ID

		if (book == null) {
			return "error";
		}
		/*
		 * Image cover = book.getBookCover(); if (cover != null) { model.addAttribute("cover",
		 * cover.getImageName()); }
		 */
		model.addAttribute("book", book);
		model.addAttribute("lastBook", bookServ.findLastId()); //HO BISOGNO DI PRIMO E ULTIMO ID PER NASCONDERE I TASTI NAVIGAZIONE
		model.addAttribute("firstBook", bookServ.findFirstId());

		if (pageNo == null) {
			pageNo = 1;
		}

		int pageSize = 10;
		Page<BookDTOView> page = bookServ.findForView(book.getGenre(),pageNo, pageSize); //PER VIEW INTENDO INFORMAZIONI VISIVE, OVVERO SOLO TITOLO E IMMAGINE
		List<BookDTOView> bookList = page.getContent();
		model.addAttribute("bookList", bookList);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("id", id); //PASSO L'ID CORRENTE DEL PATH PER MANTENERE COERENZA NELLA NAVIGAZIONE PAGINE
		return "bookDetails";
	}

	@GetMapping("/image/{imageName}") //th:src="@{'/image/' + ${book.getCoverName()}}
	public ResponseEntity<Resource> showImage(@PathVariable(value = "imageName", required = false) String imageName) {
		
		byte[] imageData = imageServ.getImage(imageName);
		if (imageData == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(new ByteArrayResource(imageData));
	}

	@GetMapping("/nextBook/{id}/{pageNo}")//SELECT MIN id WHERE id > currentID
	public String showNextBook(@PathVariable(value = "id") Long id, @PathVariable(value = "pageNo") Integer pageNo) {// POSSO TOGLIERE MODEL?
		Long nextId = bookServ.findNextById(id);
		return "redirect:/bookDetails/" + nextId + "/" + pageNo;
	}

	@GetMapping("/prevBook/{id}/{pageNo}") //SELECT MAX id WHERE id < currentID
	public String showPrevBook(@PathVariable(value = "id") Long id, @PathVariable(value = "pageNo") Integer pageNo) { // POSSO TOGLIERE MODEL?
		Long prevId = bookServ.findPrevById(id);
		return "redirect:/bookDetails/" + prevId + "/" + pageNo;
	}
}
