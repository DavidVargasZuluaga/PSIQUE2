/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.entidad;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DavidBrootal
 */
@Entity
@Table(name = "familiar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Familiar.findAll", query = "SELECT f FROM Familiar f"),
    @NamedQuery(name = "Familiar.findByIdFamiliar", query = "SELECT f FROM Familiar f WHERE f.idFamiliar = :idFamiliar"),
    @NamedQuery(name = "Familiar.findByParentezco", query = "SELECT f FROM Familiar f WHERE f.parentezco = :parentezco"),
    @NamedQuery(name = "Familiar.findByNombres", query = "SELECT f FROM Familiar f WHERE f.nombres = :nombres"),
    @NamedQuery(name = "Familiar.findByPrimeroApellido", query = "SELECT f FROM Familiar f WHERE f.primeroApellido = :primeroApellido"),
    @NamedQuery(name = "Familiar.findBySeguntoApellido", query = "SELECT f FROM Familiar f WHERE f.seguntoApellido = :seguntoApellido"),
    @NamedQuery(name = "Familiar.findByFechaNacimiento", query = "SELECT f FROM Familiar f WHERE f.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Familiar.findByFechaDdefuncion", query = "SELECT f FROM Familiar f WHERE f.fechaDdefuncion = :fechaDdefuncion"),
    @NamedQuery(name = "Familiar.findByUbicacion", query = "SELECT f FROM Familiar f WHERE f.ubicacion = :ubicacion"),
    @NamedQuery(name = "Familiar.findBySexo", query = "SELECT f FROM Familiar f WHERE f.sexo = :sexo"),
    @NamedQuery(name = "Familiar.findByEstadoCivil", query = "SELECT f FROM Familiar f WHERE f.estadoCivil = :estadoCivil"),
    @NamedQuery(name = "Familiar.findByRaza", query = "SELECT f FROM Familiar f WHERE f.raza = :raza"),
    @NamedQuery(name = "Familiar.findByReligion", query = "SELECT f FROM Familiar f WHERE f.religion = :religion"),
    @NamedQuery(name = "Familiar.findByTendenciaPolitica", query = "SELECT f FROM Familiar f WHERE f.tendenciaPolitica = :tendenciaPolitica"),
    @NamedQuery(name = "Familiar.findByOrientacionSexual", query = "SELECT f FROM Familiar f WHERE f.orientacionSexual = :orientacionSexual"),
    @NamedQuery(name = "Familiar.findByOcupacion", query = "SELECT f FROM Familiar f WHERE f.ocupacion = :ocupacion"),
    @NamedQuery(name = "Familiar.findByDiscapacidad", query = "SELECT f FROM Familiar f WHERE f.discapacidad = :discapacidad")})
public class Familiar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFamiliar")
    private Long idFamiliar;
    @Size(max = 20)
    @Column(name = "parentezco")
    private String parentezco;
    @Size(max = 25)
    @Column(name = "nombres")
    private String nombres;
    @Size(max = 20)
    @Column(name = "primeroApellido")
    private String primeroApellido;
    @Size(max = 20)
    @Column(name = "seguntoApellido")
    private String seguntoApellido;
    @Column(name = "fechaNacimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;
    @Column(name = "fechaDdefuncion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDdefuncion;
    @Size(max = 20)
    @Column(name = "ubicacion")
    private String ubicacion;
    @Size(max = 15)
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
    @Size(max = 30)
    @Column(name = "ocupacion")
    private String ocupacion;
    @Size(max = 40)
    @Column(name = "discapacidad")
    private String discapacidad;
    @Lob
    @Size(max = 65535)
    @Column(name = "comentario")
    private String comentario;
    @JoinColumn(name = "idAprendiz", referencedColumnName = "idAprendiz")
    @ManyToOne(optional = false)
    private Aprendiz idAprendiz;

    public Familiar() {
    }

    public Familiar(Long idFamiliar) {
        this.idFamiliar = idFamiliar;
    }

    public Long getIdFamiliar() {
        return idFamiliar;
    }

    public void setIdFamiliar(Long idFamiliar) {
        this.idFamiliar = idFamiliar;
    }

    public String getParentezco() {
        return parentezco;
    }

    public void setParentezco(String parentezco) {
        this.parentezco = parentezco;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPrimeroApellido() {
        return primeroApellido;
    }

    public void setPrimeroApellido(String primeroApellido) {
        this.primeroApellido = primeroApellido;
    }

    public String getSeguntoApellido() {
        return seguntoApellido;
    }

    public void setSeguntoApellido(String seguntoApellido) {
        this.seguntoApellido = seguntoApellido;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaDdefuncion() {
        return fechaDdefuncion;
    }

    public void setFechaDdefuncion(Date fechaDdefuncion) {
        this.fechaDdefuncion = fechaDdefuncion;
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

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(String discapacidad) {
        this.discapacidad = discapacidad;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Aprendiz getIdAprendiz() {
        return idAprendiz;
    }

    public void setIdAprendiz(Aprendiz idAprendiz) {
        this.idAprendiz = idAprendiz;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFamiliar != null ? idFamiliar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Familiar)) {
            return false;
        }
        Familiar other = (Familiar) object;
        if ((this.idFamiliar == null && other.idFamiliar != null) || (this.idFamiliar != null && !this.idFamiliar.equals(other.idFamiliar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.entidad.Familiar[ idFamiliar=" + idFamiliar + " ]";
    }
    
}
