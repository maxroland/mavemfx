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
@NamedQuery(name="Cargo.findAll", query="SELECT c FROM Cargo c")
public class Cargo  {


	private IntegerProperty idcargo= new SimpleIntegerProperty();

	private StringProperty nomecargo = new SimpleStringProperty();

	public Cargo() {
	}

	public Cargo(int idcargo, String nomecargo) {
		this.idcargo = new SimpleIntegerProperty(idcargo);
		this.nomecargo = new SimpleStringProperty(nomecargo);
	}

	public IntegerProperty idcargoProperty() {
		return this.idcargo;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getIdcargo() {
		return this.idcargoProperty().get();
	}
	

	public void setIdcargo(final int idcargo) {
		this.idcargoProperty().set(idcargo);
	}
	

	public StringProperty nomecargoProperty() {
		return this.nomecargo;
	}
	

	public String getNomecargo() {
		return this.nomecargoProperty().get();
	}
	

	public void setNomecargo(final String nomecargo) {
		this.nomecargoProperty().set(nomecargo);
	}

}