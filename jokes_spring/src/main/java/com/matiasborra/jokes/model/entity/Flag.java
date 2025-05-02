package com.matiasborra.jokes.model.entity;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;

@Entity
@Table(name = "flags", schema = "public")
public class Flag implements java.io.Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "flag", nullable = false)
	private String flag;

	@Column(name = "name")
	private String name;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "jokes_flags", schema = "public", joinColumns = {
			@JoinColumn(name = "flag_id", nullable = false, updatable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "joke_id", nullable = false, updatable = false) })
	private Set<Joke> jokeses = new HashSet<Joke>(0);

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "joke_flag", schema = "public", joinColumns = {
			@JoinColumn(name = "flag_id", nullable = false, updatable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "joke_id", nullable = false, updatable = false) })
	private Set<Joke> jokeses_1 = new HashSet<Joke>(0);

	public Flag() {
	}

	public Flag(long id, String flag) {
		this.id = id;
		this.flag = flag;
	}

	public Flag(long id, String flag, String name, Set<Joke> jokeses, Set<Joke> jokeses_1) {
		this.id = id;
		this.flag = flag;
		this.name = name;
		this.jokeses = jokeses;
		this.jokeses_1 = jokeses_1;
	}

	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getFlag() {
		return this.flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Set<Joke> getJokeses() {
		return this.jokeses;
	}
	public void setJokeses(Set<Joke> jokeses) {
		this.jokeses = jokeses;
	}

	public Set<Joke> getJokeses_1() {
		return this.jokeses_1;
	}
	public void setJokeses_1(Set<Joke> jokeses_1) {
		this.jokeses_1 = jokeses_1;
	}

}
