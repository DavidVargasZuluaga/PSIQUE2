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
import javax.persistence.ManyToOne;
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
@Table(name = "aprendiz")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aprendiz.findAll", query = "SELECT a FROM Aprendiz a"),
    @NamedQuery(name = "Aprendiz.findByIdAprendiz", query = "SELECT a FROM Aprendiz a WHERE a.idAprendiz = :idAprendiz"),
    @NamedQuery(name = "Aprendiz.findByUbicacion", query = "SELECT a FROM Aprendiz a WHERE a.ubicacion = :ubicacion"),
    @NamedQuery(name = "Aprendiz.findBySexo", query = "SELECT a FROM Aprendiz a WHERE a.sexo = :sexo"),
    @NamedQuery(name = "Aprendiz.findByEstadoCivil", query = "SELECT a FROM Aprendiz a WHERE a.estadoCivil = :estadoCivil"),
    @NamedQuery(name = "Aprendiz.findByRaza", query = "SELECT a FROM Aprendiz a WHERE a.raza = :raza"),
    @NamedQuery(name = "Aprendiz.findByReligion", query = "SELECT a FROM Aprendiz a WHERE a.religion = :religion"),
    @NamedQuery(name = "Aprendiz.findByTendenciaPolitica", query = "SELECT a FROM Aprendiz a WHERE a.tendenciaPolitica = :tendenciaPolitica"),
    @NamedQuery(name = "Aprendiz.findByOrientacionSexual", query = "SELECT a FROM Aprendiz a WHERE a.orientacionSexual = :orientacionSexual"),
    @NamedQuery(name = "Aprendiz.findByDiscapacidad", query = "SELECT a FROM Aprendiz a WHERE a.discapacidad = :discapacidad"),
    @NamedQuery(name = "Aprendiz.findByPasaTiempo", query = "SELECT a FROM Aprendiz a WHERE a.pasaTiempo = :pasaTiempo")})
public class Aprendiz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idAprendiz")
    private Long idAprendiz;
    @Size(max = 20)
    @Column(name = "ubicacion")
    private String ubicacion;
    @Size(max = 10)
    @Column(name = "sexo")
    private String sexo;
    @Size(max = 15)
    @Column(name = "estadoCivil")
    private String estadoCivil;
    @Size(max = 20)
    @Column(name = "raza")
    private String raza;
    @Size(max = 20)
    @Column(name = "religion")
    private String religion;
    @Size(max = 25)
    @Column(name = "tendenciaPolitica")
    private String tendenciaPolitica;
    @Size(max = 25)
    @Column(name = "orientacionSexual")
    private String orientacionSexual;
    @Size(max = 40)
    @Column(name = "discapacidad")
    private String discapacidad;
    @Size(max = 40)
    @Column(name = "pasaTiempo")
    private String pasaTiempo;
    @OneToMany(mappedBy = "idAprendiz")
    private Collection<Test> testCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAprendiz")
    private Collection<Familiar> familiarCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAprendiz")
    private Collection<Cita> citaCollection;
    @JoinColumn(name = "ficha", referencedColumnName = "noFicha")
    @ManyToOne
    private Ficha ficha;
    @JoinColumn(name = "idAprendiz", referencedColumnName = "idUsuario", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAprendiz")
    private Collection<Observacion> observacionCollection;

    public Aprendiz() {
    }

    public Aprendiz(Long idAprendiz) {
        this.idAprendiz = idAprendiz;
    }

    public Long getIdAprendiz() {
        return idAprendiz;
    }

    public void setIdAprendiz(Long idAprendiz) {
        this.idAprendiz = idAprendiz;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getTendenciaPolitica() {
        return tendenciaPolitica;
    }

    public void setTendenciaPolitica(String tendenciaPolitica) {
        this.tendenciaPolitica = tendenciaPolitica;
    }

    public String getOrientacionSexual() {
        return orientacionSexual;
    }

    public void setOrientacionSexual(String orientacionSexual) {
        this.orientacionSexual = orientacionSexual;
    }

    public String getDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(String discapacidad) {
        this.discapacidad = discapacidad;
    }

    public String getPasaTiempo() {
        return pasaTiempo;
    }

    public void setPasaTiempo(String pasaTiempo) {
        this.pasaTiempo = pasaTiempo;
    }

    @XmlTransient
    public Collection<Test> getTestCollection() {
        return testCollection;
    }

    public void setTestCollection(Collection<Test> testCollection) {
        this.testCollection = testCollection;
    }

    @XmlTransient
    public Collection<Familiar> getFamiliarCollection() {
        return familiarCollection;
    }

    public void setFamiliarCollection(Collection<Familiar> familiarCollection) {
        this.familiarCollection = familiarCollection;
    }

    @XmlTransient
    public Collection<Cita> getCitaCollection() {
        return citaCollection;
    }

    public void setCitaCollection(Collection<Cita> citaCollection) {
        this.citaCollection = citaCollection;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public Collection<Observacion> getObservacionCollection() {
        return observacionCollection;
    }

    public void setObservacionCollection(Collection<Observacion> observacionCollection) {
        this.observacionCollection = observacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAprendiz != null ? idAprendiz.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aprendiz)) {
            return false;
        }
        Aprendiz other = (Aprendiz) object;
        if ((this.idAprendiz == null && other.idAprendiz != null) || (this.idAprendiz != null && !this.idAprendiz.equals(other.idAprendiz))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.entidad.Aprendiz[ idAprendiz=" + idAprendiz + " ]";
    }
    
}
