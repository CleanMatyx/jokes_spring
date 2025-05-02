package com.matiasborra.jokes.model.entity;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "categories", schema = "public")
public class Category implements java.io.Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "category", nullable = false)
	private String category;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "categories")
	private Set<Joke> jokeses = new HashSet<Joke>(0);

	public Category() {
	}

	public Category(long id, String category) {
		this.id = id;
		this.category = category;
	}

	public Category(long id, String category, Set<Joke> jokeses) {
		this.id = id;
		this.category = category;
		this.jokeses = jokeses;
	}

	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getCategory() {
		return this.category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	public Set<Joke> getJokeses() {
		return this.jokeses;
	}
	public void setJokeses(Set<Joke> jokeses) {
		this.jokeses = jokeses;
	}

}
