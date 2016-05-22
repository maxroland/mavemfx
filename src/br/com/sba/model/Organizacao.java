package br.com.sba.model;


import javax.persistence.*;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The persistent class for the biblioteca database table.
 * 
 */
@Entity
@Table(name = "biblioteca")
//Query Eclipselink é construída com base na classe Organização
@NamedQuery(name = "Organizacao.findByCnpj", query = "SELECT b FROM Organizacao b WHERE b.cnpj = :cnpj ")
public class Organizacao {
	public static final String FIND_BY_CNPJ = "Organizacao.findByCnpj";

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty nome = new SimpleStringProperty();
	private StringProperty razaoSocial = new SimpleStringProperty();
	private StringProperty cnpj = new SimpleStringProperty();
	private StringProperty endereco = new SimpleStringProperty();
	private StringProperty cep = new SimpleStringProperty();

	public Organizacao() {}

	public Organizacao(int id, String nome, String razaoSocial, String cnpj, String endereco, String cep) {
		this.id = new SimpleIntegerProperty(id);
		this.nome = new SimpleStringProperty(nome);
		this.razaoSocial = new SimpleStringProperty(razaoSocial);
		this.cnpj = new SimpleStringProperty(cnpj);
		this.endereco = new SimpleStringProperty(endereco);
		this.cep = new SimpleStringProperty(cep);

	}


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "idbiblioteca")
	public int getId() {
		return this.idProperty().get();
	}

	public void setId(final int id) {
		this.idProperty().set(id);
	}

	public IntegerProperty idProperty() {
		return this.id;
	}
	
	public StringProperty cepProperty() {
		return this.cep;
	}

	public String getCep() {
		return this.cepProperty().get();
	}

	public void setCep(final String cep) {
		this.cepProperty().set(cep);
	}

	public StringProperty cnpjProperty() {
		return this.cnpj;
	}

	public String getCnpj() {
		return this.cnpjProperty().get();
	}

	public void setCnpj(final String cnpj) {
		this.cnpjProperty().set(cnpj);
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

	public StringProperty razaoSocialProperty() {
		return this.razaoSocial;
	}

	public String getRazaoSocial() {
		return this.razaoSocialProperty().get();
	}

	public void setRazaoSocial(final String razaoSocial) {
		this.razaoSocialProperty().set(razaoSocial);
	}

	public StringProperty enderecoProperty() {
		return this.endereco;
	}

	public String getEndereco() {
		return this.enderecoProperty().get();
	}

	public void setEndereco(final String rua) {
		this.enderecoProperty().set(rua);
	}

}