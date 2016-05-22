package br.com.sba.model;
import javax.persistence.*;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The persistent class for the usuario database table.
 * 
 */

@Entity
@Table(name="usuario")
@NamedQuery(name="Usuario.findByLogin", query="SELECT u FROM Usuario u WHERE u.login = :login ")
public class Usuario{
	public static final String FIND_BY_LOGIN = "Usuario.findByLogin";
	
	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty cep = new SimpleStringProperty();
	private StringProperty cpf = new SimpleStringProperty();
	private StringProperty email = new SimpleStringProperty();
	private StringProperty endereco = new SimpleStringProperty();
	private StringProperty login = new SimpleStringProperty();
	private StringProperty nome = new SimpleStringProperty();
	private StringProperty senha = new SimpleStringProperty();
	private IntegerProperty tipo = new SimpleIntegerProperty();

	public Usuario() {
	}
		
	public Usuario(String login, String senha) {
		this.login = new SimpleStringProperty(login);
		this.senha = new SimpleStringProperty(senha);
	}

	public Usuario(int id,String nome,String cpf, 
				   String email,String login,String senha,
				   String endereco,String cep,int tipo) {
		this.id = new SimpleIntegerProperty(id);
		this.cep = new SimpleStringProperty(cep);
		this.cpf = new SimpleStringProperty(cpf);
		this.email = new SimpleStringProperty(email);
		this.endereco = new SimpleStringProperty(endereco);
		this.login = new SimpleStringProperty(login);
		this.nome = new SimpleStringProperty(nome);
		this.senha = new SimpleStringProperty(senha);
		this.tipo = new SimpleIntegerProperty(tipo);
	}


	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="idusuario")	
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
	

	public StringProperty cpfProperty() {
		return this.cpf;
	}
	

	public String getCpf() {
		return this.cpfProperty().get();
	}
	

	public void setCpf(final String cpf) {
		this.cpfProperty().set(cpf);
	}
	

	public StringProperty emailProperty() {
		return this.email;
	}
	

	public String getEmail() {
		return this.emailProperty().get();
	}
	

	public void setEmail(final String email) {
		this.emailProperty().set(email);
	}
	

	public StringProperty enderecoProperty() {
		return this.endereco;
	}
	

	public String getEndereco() {
		return this.enderecoProperty().get();
	}
	

	public void setEndereco(final String endereco) {
		this.enderecoProperty().set(endereco);
	}
	

	public StringProperty loginProperty() {
		return this.login;
	}
	

	public String getLogin() {
		return this.loginProperty().get();
	}
	

	public void setLogin(String login) {
		this.loginProperty().set(login);
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
	

	public StringProperty senhaProperty() {
		return this.senha;
	}
	

	public String getSenha() {
		return this.senhaProperty().get();
	}
	

	public void setSenha(final String senha) {
		this.senhaProperty().set(senha);
	}
	
	public IntegerProperty tipoProperty() {
		return this.tipo;
	}
	

	public int getTipo() {
		return this.tipoProperty().get();
	}
	

	public void setTipo(final int tipo) {
		this.tipoProperty().set(tipo);
	}


	

		






	




	




	
	
	
}