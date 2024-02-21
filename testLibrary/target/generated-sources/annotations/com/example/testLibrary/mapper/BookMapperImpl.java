package com.example.testLibrary.mapper;

import com.example.testLibrary.dto.BookDTODetails;
import com.example.testLibrary.model.Book;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-19T09:29:13+0100",
    comments = "version: 1.6.0.Beta1, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDTODetails bookToBookDetails(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDTODetails bookDTODetails = new BookDTODetails();

        bookDTODetails.setBookId( book.getBookId() );
        bookDTODetails.setTitle( book.getTitle() );
        bookDTODetails.setAuthorName( map( book.getAuthor() ) );
        bookDTODetails.setPublicationDate( book.getPublicationDate() );
        bookDTODetails.setGenre( book.getGenre() );
        bookDTODetails.setISBN( book.getISBN() );
        bookDTODetails.setFormat( book.getFormat() );
        bookDTODetails.setDescription( book.getDescription() );
        bookDTODetails.setPageNumber( book.getPageNumber() );
        bookDTODetails.setBookCover( book.getBookCover() );

        return bookDTODetails;
    }
}
