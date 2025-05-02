package com.matiasborra.jokes.model.entity;

import jakarta.persistence.*;

import java.io.Serial;

@Entity
@Table(name = "languages", schema = "public")
public class Languages implements java.io.Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "language", nullable = false)
	private String language;

	public Languages() {
	}

	public Languages(int id, String language) {
		this.id = id;
		this.language = language;
	}

	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getLanguage() {
		return this.language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
}
