package br.com.sba.model;

import javax.persistence.*;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * The persistent class for the funcionario database table.
 * 
 */
@Entity
@Table(name="funcionario")
//@NamedQuery(name="Funcionario.findAll", query="SELECT f FROM Funcionario f")
public class Funcionario {

	private IntegerProperty idfuncionario = new SimpleIntegerProperty();

	private StringProperty ctps = new SimpleStringProperty();

	private IntegerProperty idcargo = new SimpleIntegerProperty();

	private IntegerProperty iddepartamento = new SimpleIntegerProperty();
	
	private StringProperty numConselho = new SimpleStringProperty();

	private ObjectProperty<Usuario> usuario = new SimpleObjectProperty<>();

	public Funcionario() {
	}

	public IntegerProperty idfuncionarioProperty() {
		return this.idfuncionario;
	}
	
	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	public int getIdfuncionario() {
		return this.idfuncionarioProperty().get();
	}
	

	public void setIdfuncionario(final int idfuncionario) {
		this.idfuncionarioProperty().set(idfuncionario);
	}
	
	public StringProperty ctpsProperty() {
		return this.ctps;
	}

	public String getCtps() {
		return this.ctpsProperty().get();
	}

	public void setCtps(final String ctps) {
		this.ctpsProperty().set(ctps);
	}

	public IntegerProperty idcargoProperty() {
		return this.idcargo;
	}
	
	public int getIdcargo() {
		return this.idcargoProperty().get();
	}

	public void setIdcargo(final int idcargo) {
		this.idcargoProperty().set(idcargo);
	}

	public IntegerProperty iddepartamentoProperty() {
		return this.iddepartamento;
	}

	public int getIddepartamento() {
		return this.iddepartamentoProperty().get();
	}

	public void setIddepartamento(final int iddepartamento) {
		this.iddepartamentoProperty().set(iddepartamento);
	}

	public StringProperty numConselhoProperty() {
		return this.numConselho;
	}
	
	@Column(name="num_conselho")
	public String getNumConselho() {
		return this.numConselhoProperty().get();
	}
	

	public void setNumConselho(final String numConselho) {
		this.numConselhoProperty().set(numConselho);
	}

	public ObjectProperty<Usuario> usuarioProperty() {
		return this.usuario;
	}
	
	//uni-directional one-to-one association to Usuario
	@OneToOne
	@JoinColumn(name="idusuario")
	public Usuario getUsuario() {
		return this.usuarioProperty().get();
	}
	
	public void setUsuario(final Usuario usuario) {
		this.usuarioProperty().set(usuario);
	}



}