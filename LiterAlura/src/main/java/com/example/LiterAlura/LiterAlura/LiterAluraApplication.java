package com.example.literAlura;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import javax.persistence.*;
import java.util.List;

@SpringBootApplication
public class LiterAluraApplication {
	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	// Método para buscar livros por título
	public List<Book> searchBooksByTitle(ObjectMapper mapper, JsonNode rootNode) {
		return mapper.convertValue(
				rootNode.get("results"),
				new TypeReference<List<Book>>() {}
		);
	}
}

@Entity
class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String genre;

	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "author_id")
	private Author author;

	public Book() {}

	public Book(String title, String genre, Author author) {
		this.title = title;
		this.genre = genre;
		this.author = author;
	}

	// Getters e Setters
	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
}

@Entity
class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private Integer birthYear;

	public Author() {}

	public Author(String name, Integer birthYear) {
		this.name = name;
		this.birthYear = birthYear;
	}

	// Getters e Setters
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}
}

@Repository
interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findByTitleContaining(String title);
}

@Repository
interface AuthorRepository extends JpaRepository<Author, Long> {
	List<Author> findByBirthYearLessThanEqual(Integer year);
}
