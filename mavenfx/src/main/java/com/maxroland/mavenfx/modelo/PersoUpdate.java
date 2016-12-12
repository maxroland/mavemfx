/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxroland.mavenfx.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rolando
 */
@Entity
@Table(name = "perso_update")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersoUpdate.findAll", query = "SELECT p FROM PersoUpdate p")
    , @NamedQuery(name = "PersoUpdate.findById", query = "SELECT p FROM PersoUpdate p WHERE p.id = :id")
    , @NamedQuery(name = "PersoUpdate.findByApeNomOld", query = "SELECT p FROM PersoUpdate p WHERE p.apeNomOld = :apeNomOld")
    , @NamedQuery(name = "PersoUpdate.findByEdadOld", query = "SELECT p FROM PersoUpdate p WHERE p.edadOld = :edadOld")
    , @NamedQuery(name = "PersoUpdate.findByIdiomaOld", query = "SELECT p FROM PersoUpdate p WHERE p.idiomaOld = :idiomaOld")
    , @NamedQuery(name = "PersoUpdate.findByFePresentOld", query = "SELECT p FROM PersoUpdate p WHERE p.fePresentOld = :fePresentOld")
    , @NamedQuery(name = "PersoUpdate.findBySueldoOldl", query = "SELECT p FROM PersoUpdate p WHERE p.sueldoOldl = :sueldoOldl")
    , @NamedQuery(name = "PersoUpdate.findByApeNomNew", query = "SELECT p FROM PersoUpdate p WHERE p.apeNomNew = :apeNomNew")
    , @NamedQuery(name = "PersoUpdate.findByEdadNew", query = "SELECT p FROM PersoUpdate p WHERE p.edadNew = :edadNew")
    , @NamedQuery(name = "PersoUpdate.findByIdiomaNew", query = "SELECT p FROM PersoUpdate p WHERE p.idiomaNew = :idiomaNew")
    , @NamedQuery(name = "PersoUpdate.findByFePresentNew", query = "SELECT p FROM PersoUpdate p WHERE p.fePresentNew = :fePresentNew")
    , @NamedQuery(name = "PersoUpdate.findBySueldoPretNew", query = "SELECT p FROM PersoUpdate p WHERE p.sueldoPretNew = :sueldoPretNew")
    , @NamedQuery(name = "PersoUpdate.findByObserv", query = "SELECT p FROM PersoUpdate p WHERE p.observ = :observ")})
public class PersoUpdate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "apeNomOld")
    private String apeNomOld;
    @Column(name = "edadOld")
    private Integer edadOld;
    @Column(name = "idiomaOld")
    private String idiomaOld;
    @Column(name = "fePresentOld")
    @Temporal(TemporalType.DATE)
    private Date fePresentOld;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "sueldoOldl")
    private Double sueldoOldl;
    @Column(name = "apeNomNew")
    private String apeNomNew;
    @Column(name = "edadNew")
    private Integer edadNew;
    @Column(name = "idiomaNew")
    private String idiomaNew;
    @Column(name = "fePresentNew")
    @Temporal(TemporalType.DATE)
    private Date fePresentNew;
    @Column(name = "sueldoPretNew")
    private Double sueldoPretNew;
    @Column(name = "observ")
    private String observ;
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Persona personaId;
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

    public PersoUpdate() {
    }

    public PersoUpdate(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApeNomOld() {
        return apeNomOld;
    }

    public void setApeNomOld(String apeNomOld) {
        this.apeNomOld = apeNomOld;
    }

    public Integer getEdadOld() {
        return edadOld;
    }

    public void setEdadOld(Integer edadOld) {
        this.edadOld = edadOld;
    }

    public String getIdiomaOld() {
        return idiomaOld;
    }

    public void setIdiomaOld(String idiomaOld) {
        this.idiomaOld = idiomaOld;
    }

    public Date getFePresentOld() {
        return fePresentOld;
    }

    public void setFePresentOld(Date fePresentOld) {
        this.fePresentOld = fePresentOld;
    }

    public Double getSueldoOldl() {
        return sueldoOldl;
    }

    public void setSueldoOldl(Double sueldoOldl) {
        this.sueldoOldl = sueldoOldl;
    }

    public String getApeNomNew() {
        return apeNomNew;
    }

    public void setApeNomNew(String apeNomNew) {
        this.apeNomNew = apeNomNew;
    }

    public Integer getEdadNew() {
        return edadNew;
    }

    public void setEdadNew(Integer edadNew) {
        this.edadNew = edadNew;
    }

    public String getIdiomaNew() {
        return idiomaNew;
    }

    public void setIdiomaNew(String idiomaNew) {
        this.idiomaNew = idiomaNew;
    }

    public Date getFePresentNew() {
        return fePresentNew;
    }

    public void setFePresentNew(Date fePresentNew) {
        this.fePresentNew = fePresentNew;
    }

    public Double getSueldoPretNew() {
        return sueldoPretNew;
    }

    public void setSueldoPretNew(Double sueldoPretNew) {
        this.sueldoPretNew = sueldoPretNew;
    }

    public String getObserv() {
        return observ;
    }

    public void setObserv(String observ) {
        this.observ = observ;
    }

    public Persona getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Persona personaId) {
        this.personaId = personaId;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
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
        if (!(object instanceof PersoUpdate)) {
            return false;
        }
        PersoUpdate other = (PersoUpdate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.maxroland.mavenfx.modelo.PersoUpdate[ id=" + id + " ]";
    }
    
}
