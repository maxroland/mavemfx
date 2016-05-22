package br.com.sba.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * The persistent class for the editora database table.
 * 
 */
@Entity
@Access(AccessType.FIELD) //mixed mode property
@NamedQuery(name="Editora.findByName", query="SELECT e FROM Editora e WHERE e.nome = :nome ")
public class Editora  {

	public static final String FIND_BY_NAME = "Editora.findByName";
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="ideditora")	
	private int id;
	@Transient
	private StringProperty nome = new SimpleStringProperty();

	public Editora() {
	}

	public Editora(int id, String nome) {
		this.id = id;
		this.nome = new SimpleStringProperty(nome);
	}


	public int getId() {
		return id;
	}
	

	public void setId(int id) {
		this.id=id;
	}
	

	public StringProperty nomeProperty() {
		return this.nome;
	}
	
	@Access(AccessType.PROPERTY)
	public String getNome() {
		return this.nomeProperty().get();
	}
	

	public void setNome(final String nome) {
		this.nomeProperty().set(nome);
	}

	@Override
	public String toString() {
		return nome.get();
	}
		
	

}