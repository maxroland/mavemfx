package br.com.sba.model;

import javax.persistence.*;


/**
 * The persistent class for the biblioteca database table.
 * 
 */
@Entity
@NamedQuery(name="Biblioteca.findAll", query="SELECT b FROM Biblioteca b")
public class Biblioteca  {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idbiblioteca;

	private String cep;

	private String cnpj;

	private String estado;

	private String nome;

	private String razaoSocial;

	private String rua;

	public Biblioteca() {
	}

	public int getIdbiblioteca() {
		return this.idbiblioteca;
	}

	public void setIdbiblioteca(int idbiblioteca) {
		this.idbiblioteca = idbiblioteca;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRazaoSocial() {
		return this.razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getRua() {
		return this.rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

}