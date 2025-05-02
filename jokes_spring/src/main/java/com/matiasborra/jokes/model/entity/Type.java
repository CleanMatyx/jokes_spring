package com.matiasborra.jokes.model.entity;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "types", schema = "public")
public class Type implements java.io.Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "type", nullable = false)
	private String type;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "types")
	private Set<Joke> jokeses = new HashSet<Joke>(0);

	public Type() {
	}

	public Type(long id, String type) {
		this.id = id;
		this.type = type;
	}

	public Type(long id, String type, Set<Joke> jokeses) {
		this.id = id;
		this.type = type;
		this.jokeses = jokeses;
	}

	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Set<Joke> getJokeses() {
		return this.jokeses;
	}
	public void setJokeses(Set<Joke> jokeses) {
		this.jokeses = jokeses;
	}
}
