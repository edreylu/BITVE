/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventos;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author usuario
 */
public class Evento implements Serializable {
    private int noEvento;
    private int noVehiculo;
    private String descripcion;
    private String fecha;
    private Date fechaD;
    private String proveedor;
    private double importe;
    private String numFactura;
    private int noTipoEvento;
    private String fechaAlta;
    private int noUsuarioAlta;
    private String fechaModif;
    private int noUsuarioModif;
    private String fechaBaja;
    private int noUsuarioBaja;
    private int noUsuarioOperacion;
    private int noUr;
    private String estatus;
    private String tipoEvento;
    private int km;
    private String resguardante;
    private String puesto;
    private String holograma;
    private String fechaVencimiento;
    private int noOrigen;
    private int noFormaAdquisicion;
    public Evento() {
    }

    public int getNoEvento() {
        return noEvento;
    }

    public void setNoEvento(int noEvento) {
        this.noEvento = noEvento;
    }

    public int getNoVehiculo() {
        return noVehiculo;
    }

    public void setNoVehiculo(int noVehiculo) {
        this.noVehiculo = noVehiculo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = (descripcion==null)?descripcion:descripcion.toUpperCase();
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = (proveedor==null)?proveedor:proveedor.toUpperCase();
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = (numFactura==null) ? numFactura:numFactura.toUpperCase();
    }

    public int getNoTipoEvento() {
        return noTipoEvento;
    }

    public void setNoTipoEvento(int noTipoEvento) {
        this.noTipoEvento = noTipoEvento;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public int getNoUsuarioAlta() {
        return noUsuarioAlta;
    }

    public void setNoUsuarioAlta(int noUsuarioAlta) {
        this.noUsuarioAlta = noUsuarioAlta;
    }

    public String getFechaModif() {
        return fechaModif;
    }

    public void setFechaModif(String fechaModif) {
        this.fechaModif = fechaModif;
    }

    public int getNoUsuarioModif() {
        return noUsuarioModif;
    }

    public void setNoUsuarioModif(int noUsuarioModif) {
        this.noUsuarioModif = noUsuarioModif;
    }

    public String getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(String fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public int getNoUsuarioBaja() {
        return noUsuarioBaja;
    }

    public void setNoUsuarioBaja(int noUsuarioBaja) {
        this.noUsuarioBaja = noUsuarioBaja;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus.toUpperCase();
    }

    public int getNoUsuarioOperacion() {
        return noUsuarioOperacion;
    }

    public void setNoUsuarioOperacion(int noUsuarioOperacion) {
        this.noUsuarioOperacion = noUsuarioOperacion;
    }

    public int getNoUr() {
        return noUr;
    }

    public void setNoUr(int noUr) {
        this.noUr = noUr;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento.toUpperCase();
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public Date getFechaD() {
        return fechaD;
    }

    public void setFechaD(Date fechaD) {
        this.fechaD = fechaD;
    }

    public String getResguardante() {
        return resguardante;
    }

    public void setResguardante(String resguardante) {
        this.resguardante = resguardante;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getHolograma() {
        return holograma;
    }

    public void setHolograma(String holograma) {
        this.holograma = holograma;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getNoOrigen() {
        return noOrigen;
    }

    public void setNoOrigen(int noOrigen) {
        this.noOrigen = noOrigen;
    }

    public int getNoFormaAdquisicion() {
        return noFormaAdquisicion;
    }

    public void setNoFormaAdquisicion(int noFormaAdquisicion) {
        this.noFormaAdquisicion = noFormaAdquisicion;
    }
    
    
}