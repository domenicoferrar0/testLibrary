package com.example.testLibrary.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.testLibrary.model.Author;
import com.example.testLibrary.model.Book;
import com.example.testLibrary.model.Image;
import com.example.testLibrary.service.AuthorService;
import com.example.testLibrary.service.BookService;
import com.example.testLibrary.service.ImageService;

import jakarta.validation.Valid;

@Controller
public class FormController {

	@Autowired
	AuthorService authorServ;

	@Autowired
	BookService bookServ;

	@Autowired
	ImageService imageServ;

	@GetMapping("/authorForm")
	public String authorAdd(Model model) {
		model.addAttribute("authorForm", new Author());
		model.addAttribute("addingSuccess", false);
		return "addAuthor";
	}

	@PostMapping("/addAuthor")
	public String postAuthor(@ModelAttribute("authorForm") @Valid Author author, BindingResult bindingRes,
			Model model) {
		if (bindingRes.hasErrors()) {	//VALIDA TUTTI I CAMPI IN BASE ALLE ANNOTAZIONI		
			return "addAuthor";
		}

		try {
			authorServ.findByCf(author.getCf()); //CONTROLLO CF UNIQUE
		} catch (IllegalArgumentException e) {
			model.addAttribute("duplicateError", "Codice Fiscale duplicato, inserirne uno valido");
			return "addAuthor";
		}

		try {
			authorServ.save(author);			

		} catch (Exception e) {
			model.addAttribute("authorFormError", true); //TRIGGERA MESSAGGIO DI ERRORE IN CASO DI ERRORE			
			return "addAuthor";

		}
		model.addAttribute("showSuccessNotification", true); //CONFERMA
		return "addAuthor";
	}

	@GetMapping("/bookForm")
	public String bookAdd(Model model) {
		model.addAttribute("bookForm", new Book());
		model.addAttribute("authors", authorServ.getAuthorNames()); //PASSO SOLO IL DTO CON ID E NOMI
		return "addBook";
	}

	@PostMapping("/addBook")
	public String postBook(@ModelAttribute("bookForm") @Valid Book book, BindingResult bindingRes, Model model,
			@RequestParam(value = "cazzo", required = false) MultipartFile cazzo, RedirectAttributes redirectAtt) {
		Image image = null; //IMMAGINE NULL DI APPOGGIO
		model.addAttribute("authors", authorServ.getAuthorNames()); 
		if (bindingRes.hasErrors()) {//VALIDA I CAMPI 
			return "addBook";
		}
		try {
			bookServ.findByISBN(book.getISBN()); //CHECK ISBN DUPLICATO
		} catch (IllegalArgumentException e) {
			model.addAttribute("duplicateError", "ISBN duplicato, inserirne uno valido");

			return "addBook";
		}
		if (!cazzo.isEmpty()) { //CONTROLLA IL FILE SOLO SE PRESENTE SENNO' LO LASCIA NULL
			if (!imageServ.imageCheck(cazzo)) {
				

				redirectAtt.addFlashAttribute("imageUploadError", "Il tuo file non è consentito");
				return "redirect:/bookForm"; //REDIRECT CON MESSAGGIO FLASH PER EVITARE INSERT ACCIDENTALI
			}
			try {
				image = imageServ.saveImage(cazzo);
			} catch (IllegalStateException | IOException e) {

				e.printStackTrace();
				redirectAtt.addFlashAttribute("imageUploadError", "C'è stato un problema con il tuo file");
				return "redirect:/bookForm";
			}
		}
		try {
			book.setBookCover(image); //SETTA IMMAGINE IN ENTRAMBI I CASI, ANCHE SE NULL
			bookServ.save(book);

		} catch (Exception e) {
			model.addAttribute("bookFormError", "C'è stato un problema con l'inserimento del libro");
			imageServ.removeImage(image); //SE NON RIESCE A SALVARE RIMUOVO L'IPOTETICO FILE
			//IL CONTROLLO LO FA NEL METODO SOLO SE NULL
			return "addBook";
		}
		model.addAttribute("showSuccessNotification", true);
		return "addBook";
	}

}
