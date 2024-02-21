package com.example.testLibrary.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.testLibrary.model.Image;
import com.example.testLibrary.repository.ImageRepository;

@Service
public class ImageService {
	//COSTANTE PATH DI DEFAULT
	private final static String UP_PATH = "C:\\Users\\Gennaro\\Desktop\\Cover";

	@Autowired
	ImageRepository imageRep;

	
	//PER RIMUOVERE IL FILE
	@Transactional
	public void removeImage(Image image) {
		if (image != null) { //GESTISCE I LIBRI SENZA IMMAGINI
			String fileName = image.getImageName(); //ESTRAE IL FILE A PARTIRE DALL'ENTITY
			File file = new File(UP_PATH, fileName);
			if (file.exists()) {
				try {
					if (!file.delete()) {
						throw new IllegalArgumentException("File wasn't deleted successfully");
					}

				} catch (SecurityException e) {

				}
			}
		}
	}
//0471958532
	@Transactional
	public Image saveImage(MultipartFile file) throws IllegalStateException, IOException {
		String fileName = UUID.randomUUID().toString()+".jpg";
		File filePath = new File(UP_PATH, fileName);
		file.transferTo(filePath);
		System.out.println("\n \n IMMAGINE SALVATA \n \n");
		Image image = new Image();
		image.setImageName(fileName);
		return imageRep.save(image);
	}
	//ESTRAE ARRAY DI byte A PARTIRE DAL FILE
	public byte[] getImage(String fileName) {

		Path imagePath = Paths.get(UP_PATH, fileName);
		try {
			return Files.readAllBytes(imagePath);
		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}

	}
			//CONTROLLO VALIDITA' IMMAGINE, CONTROLLA SIZE, LUNGHEZZA DEL NOME E FORMATO
	public boolean imageCheck(MultipartFile file) {
		if (file.getOriginalFilename().length() > 200) {
			return false;
		}
		MediaType mediaType = null;
		if (file == null || file.isEmpty() || file.getSize() > 204800) {
			return false;
		}
		try {
			TikaInputStream tika = TikaInputStream.get(file.getInputStream());
			Detector detector = new DefaultDetector();
			mediaType = detector.detect(tika, new Metadata());
			tika.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		if (mediaType == null
				|| (!mediaType.equals(MediaType.image("jpg")) && !mediaType.equals(MediaType.image("jpeg")))) {
			return false;
		}
		return true;
	}
}
