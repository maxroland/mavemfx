/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxroland.mavenfx.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Rolando
 */
@Entity
@Table(name = "persona")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p")
    , @NamedQuery(name = "Persona.findById", query = "SELECT p FROM Persona p WHERE p.id = :id")
    , @NamedQuery(name = "Persona.findByApellido", query = "SELECT p FROM Persona p WHERE p.apellido = :apellido")
    , @NamedQuery(name = "Persona.findByNombre", query = "SELECT p FROM Persona p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Persona.findByEdad", query = "SELECT p FROM Persona p WHERE p.edad = :edad")
    , @NamedQuery(name = "Persona.findByIdioma", query = "SELECT p FROM Persona p WHERE p.idioma = :idioma")
    , @NamedQuery(name = "Persona.findByFePresent", query = "SELECT p FROM Persona p WHERE p.fePresent = :fePresent")
    , @NamedQuery(name = "Persona.findBySueldoPret", query = "SELECT p FROM Persona p WHERE p.sueldoPret = :sueldoPret")})
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "edad")
    private Integer edad;
    @Column(name = "idioma")
    private String idioma;
    @Column(name = "fePresent")
    @Temporal(TemporalType.DATE)
    private Date fePresent;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "sueldoPret")
    private Double sueldoPret;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personaId")
    private List<TelPerso> telPersoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personaId")
    private List<PersoUpdate> persoUpdateList;

    public Persona() {
    }

    public Persona(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Date getFePresent() {
        return fePresent;
    }

    public void setFePresent(Date fePresent) {
        this.fePresent = fePresent;
    }

    public Double getSueldoPret() {
        return sueldoPret;
    }

    public void setSueldoPret(Double sueldoPret) {
        this.sueldoPret = sueldoPret;
    }

    @XmlTransient
    public List<TelPerso> getTelPersoList() {
        return telPersoList;
    }

    public void setTelPersoList(List<TelPerso> telPersoList) {
        this.telPersoList = telPersoList;
    }

    @XmlTransient
    public List<PersoUpdate> getPersoUpdateList() {
        return persoUpdateList;
    }

    public void setPersoUpdateList(List<PersoUpdate> persoUpdateList) {
        this.persoUpdateList = persoUpdateList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.maxroland.mavenfx.modelo.Persona[ id=" + id + " ]";
    }
    
}
