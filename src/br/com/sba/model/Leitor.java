package br.com.sba.model;

import java.time.LocalDate;

import javax.persistence.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.sba.util.LocalDateAdapter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;




/**
 * The persistent class for the leitor database table.
 * 
 */
@Entity
//@NamedQuery(name="Leitor.findAll", query="SELECT l FROM Leitor l")
public class Leitor{

	private IntegerProperty idleitor = new SimpleIntegerProperty();
	private ObjectProperty<LocalDate> dataAssociacao = new SimpleObjectProperty<>();
	private StringProperty status = new SimpleStringProperty();
	private ObjectProperty<Usuario> usuario = new SimpleObjectProperty<>();

	public Leitor() {
	}

	
	public IntegerProperty idleitorProperty() {
		return this.idleitor;
	}
	
	@Id
	@GeneratedValue//(strategy=GenerationType.AUTO)
	public int getIdleitor() {
		return this.idleitorProperty().get();
	}	

	public void setIdleitor(final int idleitor) {
		this.idleitorProperty().set(idleitor);
	}

	public StringProperty statusProperty() {
		return this.status;
	}

	public String getStatus() {
		return this.statusProperty().get();
	}

	public void setStatus(final String status) {
		this.statusProperty().set(status);
	}

	public ObjectProperty<LocalDate> dataAssociacaoProperty() {
		return this.dataAssociacao;
	}
	
//	public LocalDate getDataAssociacao() {
//		return this.dataAssociacaoProperty().get();
//	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	@Column(name="data_associacao")
	public LocalDate getDataAssociacao() {
	    return this.dataAssociacaoProperty().get();
	}
	
	public void setDataAssociacao(final LocalDate dataAssociacao) {
		this.dataAssociacaoProperty().set(dataAssociacao);
	}
	
	public ObjectProperty<Usuario> usuarioProperty() {
		return this.usuario;
	}
	
	//bi-directional many-to-one association to Usuario
	@OneToOne
	@JoinColumn(name="idusuario")
	public Usuario getUsuario() {
		return this.usuarioProperty().get();
	}

	public void setUsuario(final Usuario usuario) {
		this.usuarioProperty().set(usuario);
	}

}