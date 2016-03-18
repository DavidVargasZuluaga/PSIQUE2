/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.entidad;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "psicologo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Psicologo.findAll", query = "SELECT p FROM Psicologo p"),
    @NamedQuery(name = "Psicologo.findByIdPsicologo", query = "SELECT p FROM Psicologo p WHERE p.idPsicologo = :idPsicologo"),
    @NamedQuery(name = "Psicologo.findByJornada", query = "SELECT p FROM Psicologo p WHERE p.jornada = :jornada")})
public class Psicologo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idPsicologo")
    private Long idPsicologo;
    @Size(max = 15)
    @Column(name = "jornada")
    private String jornada;
    @JoinColumn(name = "idPsicologo", referencedColumnName = "idUsuario", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPsicologo")
    private Collection<Cita> citaCollection;

    public Psicologo() {
    }

    public Psicologo(Long idPsicologo) {
        this.idPsicologo = idPsicologo;
    }

    public Long getIdPsicologo() {
        return idPsicologo;
    }

    public void setIdPsicologo(Long idPsicologo) {
        this.idPsicologo = idPsicologo;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public Collection<Cita> getCitaCollection() {
        return citaCollection;
    }

    public void setCitaCollection(Collection<Cita> citaCollection) {
        this.citaCollection = citaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPsicologo != null ? idPsicologo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Psicologo)) {
            return false;
        }
        Psicologo other = (Psicologo) object;
        if ((this.idPsicologo == null && other.idPsicologo != null) || (this.idPsicologo != null && !this.idPsicologo.equals(other.idPsicologo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.entidad.Psicologo[ idPsicologo=" + idPsicologo + " ]";
    }
    
}
