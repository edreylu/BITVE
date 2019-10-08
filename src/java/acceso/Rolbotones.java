/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acceso;

import formas.FormasMenu;
import usuarios.Usuarios;
import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author usuario
 */

public class Rolbotones implements Serializable {
    private String insertar;
    private String actualizar;
    private String eliminar;
    private String consultar;
    private String descargar;
    private Boolean insertarSel;
    private Boolean actualizarSel;
    private Boolean eliminarSel;
    private Boolean consultarSel;
    
    private static final long serialVersionUID = 1L;
    private Long idRol;
    private String nombre;
    private String nombreinsertar;
    private Collection<FormasMenu> menuCollection;
    private Collection<Usuarios> usuariosCollection;

    public Rolbotones() {
    }

    public Rolbotones(Long idRol) {
        this.idRol = idRol;
    }

    public Rolbotones(Long idRol, String nombre) {
        this.idRol = idRol;
        this.nombre = nombre;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
        
    }

    public void setNombre(String nombre) {
        this.nombre = null != nombre ? nombre.toUpperCase() : null;
    }

    @XmlTransient
    public Collection<FormasMenu> getMenuCollection() {
        return menuCollection;
    }

    public void setMenuCollection(Collection<FormasMenu> menuCollection) {
        this.menuCollection = menuCollection;
    }

    @XmlTransient
    public Collection<Usuarios> getUsuariosCollection() {
        return usuariosCollection;
    }

    public void setUsuariosCollection(Collection<Usuarios> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRol != null ? idRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rolbotones)) {
            return false;
        }
        Rolbotones other = (Rolbotones) object;
        if ((this.idRol == null && other.idRol != null) || (this.idRol != null && !this.idRol.equals(other.idRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "acceso.Rol[ idRol=" + idRol + " ]";
    }

    public String getInsertar() {
        return insertar;
    }

    public void setInsertar(String insertar) {
        this.insertar = insertar;
    }

    public String getActualizar() {
        return actualizar;
    }

    public void setActualizar(String actualizar) {
        this.actualizar = actualizar;
    }

    public String getEliminar() {
        return eliminar;
    }

    public void setEliminar(String eliminar) {
        this.eliminar = eliminar;
    }

    public String getConsultar() {
        return consultar;
    }

    public void setConsultar(String consultar) {
        this.consultar = consultar;
    }

    public String getDescargar() {
        return descargar;
    }

    public void setDescargar(String descargar) {
        this.descargar = descargar;
    }
    
    
    public Boolean getInsertarSel() {
        return insertarSel;
    }

    public void setInsertarSel(Boolean insertarSel) {
        this.insertarSel = insertarSel;
    }

    public Boolean getActualizarSel() {
        return actualizarSel;
    }

    public void setActualizarSel(Boolean actualizarSel) {
        this.actualizarSel = actualizarSel;
    }

    public Boolean getEliminarSel() {
        return eliminarSel;
    }

    public void setEliminarSel(Boolean eliminarSel) {
        this.eliminarSel = eliminarSel;
    }

    public Boolean getConsultarSel() {
        return consultarSel;
    }

    public void setConsultarSel(Boolean consultarSel) {
        this.consultarSel = consultarSel;
    }

    public String getNombreinsertar() {
        return nombreinsertar;
    }

    public void setNombreinsertar(String nombreinsertar) {
        this.nombreinsertar = null != nombreinsertar ? nombreinsertar.toUpperCase() : null;
    }
    
    
    
}
