package br.com.sba.model;

//import java.io.Serializable;
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

	private IntegerProperty idusuario =  new SimpleIntegerProperty();

	private StringProperty cep = new SimpleStringProperty();

	private StringProperty cpf = new SimpleStringProperty();

	private StringProperty email = new SimpleStringProperty();

	private StringProperty endereco = new SimpleStringProperty();

	private StringProperty estado = new SimpleStringProperty();

	private StringProperty login = new SimpleStringProperty();

	private StringProperty nome = new SimpleStringProperty();

	private StringProperty senha = new SimpleStringProperty();

	private IntegerProperty tipousuario = new SimpleIntegerProperty();

	public Usuario() {
	}

		
	public Usuario(int idusuario) {
		this.idusuario = new SimpleIntegerProperty(idusuario);
	}

	public Usuario(String login, String senha) {
		this.login = new SimpleStringProperty(login);
		this.senha = new SimpleStringProperty(senha);
	}


	public Usuario(int idusuario, String cep, String cpf, String email, String endereco, 
			String estado, String login, String nome, String senha, int tipousuario) {
		this.idusuario = new SimpleIntegerProperty(idusuario);
		this.cep = new SimpleStringProperty(cep);
		this.cpf = new SimpleStringProperty(cpf);
		this.email = new SimpleStringProperty(email);
		this.endereco = new SimpleStringProperty(endereco);
		this.estado = new SimpleStringProperty(estado);
		this.login = new SimpleStringProperty(login);
		this.nome = new SimpleStringProperty(nome);
		this.senha = new SimpleStringProperty(senha);
		this.tipousuario = new SimpleIntegerProperty(tipousuario);
	}


	public IntegerProperty idusuarioProperty() {
		return this.idusuario;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getIdusuario() {
		return this.idusuarioProperty().get();
	}
	

	public void setIdusuario(final int idusuario) {
		this.idusuarioProperty().set(idusuario);
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
	

	public StringProperty estadoProperty() {
		return this.estado;
	}
	

	public String getEstado() {
		return this.estadoProperty().get();
	}
	

	public void setEstado(final String estado) {
		this.estadoProperty().set(estado);
	}
	

	public StringProperty loginProperty() {
		return this.login;
	}
	

	public String getLogin() {
		return this.loginProperty().get();
	}
	

	public void setLogin(final String login) {
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
	

	public IntegerProperty tipousuarioProperty() {
		return this.tipousuario;
	}
	
	public int getTipousuario() {
		return this.tipousuarioProperty().get();
	}
	

	public void setTipousuario(final int tipousuario) {
		this.tipousuarioProperty().set(tipousuario);
	}	

	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idusuario == null) ? 0 : idusuario.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (idusuario == null) {
			if (other.idusuario != null)
				return false;
		} else if (!idusuario.equals(other.idusuario))
			return false;
		return true;
	}		
	
}