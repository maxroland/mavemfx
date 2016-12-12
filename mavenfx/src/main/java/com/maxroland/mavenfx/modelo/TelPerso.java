/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxroland.mavenfx.modelo;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rolando
 */
@Entity
@Table(name = "tel_perso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TelPerso.findAll", query = "SELECT t FROM TelPerso t")
    , @NamedQuery(name = "TelPerso.findById", query = "SELECT t FROM TelPerso t WHERE t.id = :id")
    , @NamedQuery(name = "TelPerso.findByNumTel", query = "SELECT t FROM TelPerso t WHERE t.numTel = :numTel")
    , @NamedQuery(name = "TelPerso.findByEmpTel", query = "SELECT t FROM TelPerso t WHERE t.empTel = :empTel")})
public class TelPerso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "numTel")
    private String numTel;
    @Column(name = "empTel")
    private String empTel;
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Persona personaId;

    public TelPerso() {
    }

    public TelPerso(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getEmpTel() {
        return empTel;
    }

    public void setEmpTel(String empTel) {
        this.empTel = empTel;
    }

    public Persona getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Persona personaId) {
        this.personaId = personaId;
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
        if (!(object instanceof TelPerso)) {
            return false;
        }
        TelPerso other = (TelPerso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.maxroland.mavenfx.modelo.TelPerso[ id=" + id + " ]";
    }
    
}
