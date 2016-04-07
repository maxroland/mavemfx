package br.com.sba.model;

import javax.persistence.*;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * The persistent class for the editora database table.
 * 
 */
@Entity
@NamedQuery(name="Editora.findAll", query="SELECT e FROM Editora e")
public class Editora  {

	private IntegerProperty ideditora =  new SimpleIntegerProperty();

	private StringProperty nomeeditora = new SimpleStringProperty();

	public Editora() {
	}

	public Editora(int ideditora, String nomeeditora) {
		this.ideditora = new SimpleIntegerProperty(ideditora);
		this.nomeeditora = new SimpleStringProperty(nomeeditora);
	}

	public IntegerProperty ideditoraProperty() {
		return this.ideditora;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	public int getIdeditora() {
		return this.ideditoraProperty().get();
	}
	

	public void setIdeditora(final int ideditora) {
		this.ideditoraProperty().set(ideditora);
	}
	

	public StringProperty nomeeditoraProperty() {
		return this.nomeeditora;
	}
	

	public String getNomeeditora() {
		return this.nomeeditoraProperty().get();
	}
	

	public void setNomeeditora(final String nomeeditora) {
		this.nomeeditoraProperty().set(nomeeditora);
	}
	
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ideditora == null) ? 0 : ideditora.hashCode());
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
		Editora other = (Editora) obj;
		if (ideditora == null) {
			if (other.ideditora != null)
				return false;
		} else if (!ideditora.equals(other.ideditora))
			return false;
		return true;
	}		
	

}