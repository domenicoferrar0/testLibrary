package com.example.testLibrary.dto;

public class AuthorDTONames {
//POSSO TOGLIERE I SETTER I GUESS
	private Long authorId;

	private String firstName;

	private String lastName;

	public String getFullName() {
		return firstName+" "+lastName;
	}
	
	public Long getAuthorId() {
		return authorId;
	}

	

	public String getFirstName() {
		return firstName;
	}

	

	public String getLastName() {
		return lastName;
	}

	

	public AuthorDTONames(Long authorId, String firstName, String lastName) {
		
		this.authorId = authorId;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	

}
