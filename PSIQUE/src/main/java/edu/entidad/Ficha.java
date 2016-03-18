/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.entidad;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DavidBrootal
 */
@Entity
@Table(name = "ficha")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ficha.findAll", query = "SELECT f FROM Ficha f"),
    @NamedQuery(name = "Ficha.findByNoFicha", query = "SELECT f FROM Ficha f WHERE f.noFicha = :noFicha"),
    @NamedQuery(name = "Ficha.findByEstado", query = "SELECT f FROM Ficha f WHERE f.estado = :estado")})
public class Ficha implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "noFicha")
    private String noFicha;
    @Size(max = 15)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "idPrograma", referencedColumnName = "idPrograma")
    @ManyToOne
    private Programaformacion idPrograma;
    @OneToMany(mappedBy = "ficha")
    private Collection<Aprendiz> aprendizCollection;

    public Ficha() {
    }

    public Ficha(String noFicha) {
        this.noFicha = noFicha;
    }

    public String getNoFicha() {
        return noFicha;
    }

    public void setNoFicha(String noFicha) {
        this.noFicha = noFicha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Programaformacion getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Programaformacion idPrograma) {
        this.idPrograma = idPrograma;
    }

    @XmlTransient
    public Collection<Aprendiz> getAprendizCollection() {
        return aprendizCollection;
    }

    public void setAprendizCollection(Collection<Aprendiz> aprendizCollection) {
        this.aprendizCollection = aprendizCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noFicha != null ? noFicha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ficha)) {
            return false;
        }
        Ficha other = (Ficha) object;
        if ((this.noFicha == null && other.noFicha != null) || (this.noFicha != null && !this.noFicha.equals(other.noFicha))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.entidad.Ficha[ noFicha=" + noFicha + " ]";
    }
    
}
