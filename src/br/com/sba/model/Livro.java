package br.com.sba.model;


import java.time.LocalDate;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The persistent class for the livro database table.
 * 
 */
@Entity
@Table(name="Livro")
@Access(AccessType.PROPERTY) //mixed mode property
@NamedQuery(name = "Livro.findByIsbn", query = "SELECT l FROM Livro l WHERE l.isbn = :isbn")
public class Livro {
	public static final String FIND_BY_ISBN = "Livro.findByIsbn";
	
	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty autor = new SimpleStringProperty();
	private LocalDate dataPublicacao;
	private StringProperty isbn = new SimpleStringProperty();
	private StringProperty nome = new SimpleStringProperty();
	private ObjectProperty<Editora> editora =  new SimpleObjectProperty<>();

	public Livro() {
	}
	

	public Livro(int id,String nome, String autor, String isbn,
				 LocalDate dataPublicacao, Editora editora) {
		this.id = new SimpleIntegerProperty(id);
		this.nome = new SimpleStringProperty(nome);		
		this.autor = new SimpleStringProperty(autor);
		this.dataPublicacao =  dataPublicacao;
		this.isbn = new SimpleStringProperty(isbn);
		this.editora = new SimpleObjectProperty<>(editora);
	}


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "idlivro")
	public int getId() {
		return this.idProperty().get();
	}

	public void setId(final int id) {
		this.idProperty().set(id);
	}

	public IntegerProperty idProperty() {
		return this.id;
	}
	
	// bi-directional many-to-one association to Editora
	@ManyToOne
	@JoinColumn(name = "ideditora")	
	public Editora getEditora() {
		return this.editoraProperty().get();
	}
	
	public void setEditora(final Editora editora) {
		this.editoraProperty().set(editora);
	}
	
	public ObjectProperty<Editora> editoraProperty() {
		return this.editora;
	}
	
	public StringProperty autorProperty() {
		return this.autor;
	}

	public String getAutor() {
		return this.autorProperty().get();
	}

	public void setAutor(final String autor) {
		this.autorProperty().set(autor);
	}

	public StringProperty isbnProperty() {
		return this.isbn;
	}

	public String getIsbn() {
		return this.isbnProperty().get();
	}

	public void setIsbn(final String isbn) {
		this.isbnProperty().set(isbn);
	}

	public StringProperty nomeProperty() {
		return this.nome;
	}
	@Column(name = "titulo")
	public String getNome() {
		return this.nomeProperty().get();
	}

	public void setNome(final String nome) {
		this.nomeProperty().set(nome);
	}


	public LocalDate getDataPublicacao() {
		return dataPublicacao;
	}


	public void setDataPublicacao(LocalDate dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
	
	
	
}