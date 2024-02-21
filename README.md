This is my very first attempt on doing a functional web app.
My Objective was to make a Thymeleaf/Spring Data JPA based web app.
It has been primarly done for achieving some experience and understanding how could JPA and Thymeleaf work together.
The main goal of this application was to:

- Inserting Authors entities inside the db.
- Inserting and associate their books in a ManyToOne relationship
- Associate a corresponding Image entity to the Book
- Saving the image entity inside the database and the actual jpg inside a system directory.
- Showing the content using Thymeleaf

Each one of the two main entities, Book and Author have 2 dedicated templates:

- addBook/Author is essentially a form for creating a new entity, including field validations and the option to upload a cover for the book.
- bookList/authorList for showing the paginated content, including a functional search button and a delete button.
- Book have and additional template where you can see every information (image included), a list of books of the same genre and navigate.

