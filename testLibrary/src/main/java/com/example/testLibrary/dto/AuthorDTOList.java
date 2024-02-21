package com.example.testLibrary.dto;

import java.sql.Date;

import com.example.testLibrary.enums.Country;
import com.example.testLibrary.enums.Gender;

public class AuthorDTOList {

	private Long authorId;

	private String firstName;

	private String lastName;

	private Date birthDate;

	private Gender gender;

	private String cf;

	private Country country;

	
	public AuthorDTOList(Long authorId, String firstName, String lastName, Date birthDate, Gender gender, String cf,
			Country country) {
		
		this.authorId = authorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gender = gender;
		this.cf = cf;
		this.country = country;
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

	public Date getBirthDate() {
		return birthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public String getCf() {
		return cf;
	}

	public Country getCountry() {
		return country;
	}
	
	

}
