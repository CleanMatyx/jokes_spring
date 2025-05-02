package com.matiasborra.jokes.model.entity;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;

@Entity
@Table(name = "jokes", schema = "public")
public class Joke implements java.io.Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category categories;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "language_id")
	private Language language;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	private Type types;

	@Column(name = "text1")
	private String text1;

	@Column(name = "text2")
	private String text2;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "joke_flag", schema = "public", joinColumns = {
			@JoinColumn(name = "joke_id", nullable = false, updatable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "flag_id", nullable = false, updatable = false) })
	private Set<Flag> flagses = new HashSet<Flag>(0);

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "jokes_flags", schema = "public", joinColumns = {
			@JoinColumn(name = "joke_id", nullable = false, updatable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "flag_id", nullable = false, updatable = false) })
	private Set<Flag> flagses_1 = new HashSet<Flag>(0);

	public Joke() {
	}

	public Joke(long id) {
		this.id = id;
	}

	public Joke(long id, Category categories, Language language, Type types, String text1, String text2,
				Set<Flag> flagses, Set<Flag> flagses_1) {
		this.id = id;
		this.categories = categories;
		this.language = language;
		this.types = types;
		this.text1 = text1;
		this.text2 = text2;
		this.flagses = flagses;
		this.flagses_1 = flagses_1;
	}

	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public Category getCategory() {
		return this.categories;
	}
	public void setCategory(Category categories) {
		this.categories = categories;
	}

	public Language getLanguage() {
		return this.language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}

	public Type getType() {
		return this.types;
	}
	public void setType(Type types) {
		this.types = types;
	}

	public String getText1() {
		return this.text1;
	}
	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return this.text2;
	}
	public void setText2(String text2) {
		this.text2 = text2;
	}

	public Set<Flag> getFlags() {
		return this.flagses;
	}
	public void setFlags(Set<Flag> flagses) {
		this.flagses = flagses;
	}

	public Set<Flag> getFlags_1() {
		return this.flagses_1;
	}
	public void setFlags_1(Set<Flag> flagses_1) {
		this.flagses_1 = flagses_1;
	}
}
