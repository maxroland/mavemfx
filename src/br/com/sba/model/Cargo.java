package br.com.sba.model;

import javax.persistence.*;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * The persistent class for the cargo database table.
 * 
 */
@Entity
@Table(name="cargo")
@NamedQuery(name="Cargo.findAll", query="SELECT c FROM Cargo c")
public class Cargo  {


	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty nome = new SimpleStringProperty();

	public Cargo() {
	}

	public Cargo(int id, String nome) {
		this.id = new SimpleIntegerProperty(id);
		this.nome = new SimpleStringProperty(nome);
	}

	public IntegerProperty idProperty() {
		return this.id;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="idcargo")
	public int getId() {
		return this.idProperty().get();
	}
	

	public void setId(final int id) {
		this.idProperty().set(id);
	}
	

	public StringProperty nomeProperty() {
		return this.nome;
	}
	

	public String getNome() {
		return this.nomeProperty().get();
	}
	

	public void setNome(final String nome) {
		this.nomeProperty().set(nome);
	}

}