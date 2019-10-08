/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuarios;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author usuario
 */
public class Usuarios implements Serializable {

    private int noUsuario;
    private String clave;
    private String pasaporte;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String telefono;
    private String estatus;
    private Date fechaAuditoria;
     private int noRol;
     private String descripcionRol;
     private int noUr;
    public Usuarios() {
    }

    public Usuarios(int noUsuario) {
        this.noUsuario = noUsuario;
    }

    public Usuarios(int noUsuario, String clave, String nombre, String apellidoPaterno, String correo, String telefono, String estatus, Date fechaAuditoria) {
        this.noUsuario = noUsuario;
        this.clave = clave.toUpperCase();
        this.nombre = nombre.toUpperCase();
        this.apellidoPaterno = apellidoPaterno.toUpperCase();
        this.correo = correo;
        this.telefono = telefono;
        this.estatus = estatus;
        this.fechaAuditoria = fechaAuditoria;
    }

    public int getNoUsuario() {
        return noUsuario;
    }

    public void setNoUsuario(int noUsuario) {
        this.noUsuario = noUsuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave.toUpperCase();
    }

    public String getPasaporte() {
        return pasaporte;
    }

    public void setPasaporte(String pasaporte) {
        this.pasaporte = pasaporte.toUpperCase();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno.toUpperCase();
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno.toUpperCase();
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Date getFechaAuditoria() {
        return fechaAuditoria;
    }

    public void setFechaAuditoria(Date fechaAuditoria) {
        this.fechaAuditoria = fechaAuditoria;
    }


    public int getNoRol() {
        return noRol;
    }

    public void setNoRol(int noRol) {
        this.noRol = noRol;
    }

    public String getDescripcionRol() {
        return descripcionRol;
    }

    public void setDescripcionRol(String descripcionRol) {
        this.descripcionRol = descripcionRol.toUpperCase();
    }

    public int getNoUr() {
        return noUr;
    }

    public void setNoUr(int noUr) {
        this.noUr = noUr;
    }

    
    
}
