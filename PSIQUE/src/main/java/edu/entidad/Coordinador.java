/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DavidBrootal
 */
@Entity
@Table(name = "coordinador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Coordinador.findAll", query = "SELECT c FROM Coordinador c"),
    @NamedQuery(name = "Coordinador.findByIdCoordinador", query = "SELECT c FROM Coordinador c WHERE c.idCoordinador = :idCoordinador")})
public class Coordinador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idCoordinador")
    private Long idCoordinador;
    @JoinColumn(name = "idPrograma", referencedColumnName = "idPrograma")
    @ManyToOne
    private Programaformacion idPrograma;
    @JoinColumn(name = "idCoordinador", referencedColumnName = "idUsuario", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario;

    public Coordinador() {
    }

    public Coordinador(Long idCoordinador) {
        this.idCoordinador = idCoordinador;
    }

    public Long getIdCoordinador() {
        return idCoordinador;
    }

    public void setIdCoordinador(Long idCoordinador) {
        this.idCoordinador = idCoordinador;
    }

    public Programaformacion getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Programaformacion idPrograma) {
        this.idPrograma = idPrograma;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCoordinador != null ? idCoordinador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Coordinador)) {
            return false;
        }
        Coordinador other = (Coordinador) object;
        if ((this.idCoordinador == null && other.idCoordinador != null) || (this.idCoordinador != null && !this.idCoordinador.equals(other.idCoordinador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.entidad.Coordinador[ idCoordinador=" + idCoordinador + " ]";
    }
    
}
