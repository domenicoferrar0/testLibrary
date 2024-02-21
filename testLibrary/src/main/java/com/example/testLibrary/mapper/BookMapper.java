package com.example.testLibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import com.example.testLibrary.dto.BookDTODetails;
import com.example.testLibrary.model.Author;
import com.example.testLibrary.model.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {
 //MANTENGO IL MAPPER ANCHE SE NON MI SERVE COME REFERENCE IN FUTURO, E' L'UNICO DTO CHE HO MANTENUTO COL MAPPER
	BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
	
	@Mapping(source = "bookId", target = "bookId")
	@Mapping(source = "title", target = "title")
	@Mapping(source = "author", target = "authorName", qualifiedByName = "authorFullName")
	@Mapping(source = "publicationDate", target = "publicationDate")
	@Mapping(source = "genre", target = "genre")
	@Mapping(source = "ISBN", target = "ISBN")
	@Mapping(source = "format", target = "format")
	@Mapping(source = "description", target = "description")
	@Mapping(source = "pageNumber", target = "pageNumber")
	@Mapping(source = "bookCover", target = "bookCover")
	BookDTODetails bookToBookDetails(Book book);
	
	
				
	
	@Named("authorFullName")
    default String map(Author value) {
        return authorFullName(value);
    }
	
	 default String authorFullName(Author author) {
	        return author.getFullName();
	    }
}
