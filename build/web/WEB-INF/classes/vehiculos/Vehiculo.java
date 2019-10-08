/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehiculos;

import java.io.Serializable;

/**
 *
 * @author usuario
 */
public class Vehiculo implements Serializable {
    private int noVehiculo;
    private String serie;
    private String placas;
    private String marca;
    private int modelo;
    private String tipo;
    private String fechaAlta;
    private int noUsuarioAlta;
    private String fechaModif;
    private int noUsuarioModif;
    private String fechaBaja;
    private int noUsuarioBaja;
    private int noUsuarioOperacion;
    private int noUr;
    private String ur;
    private String estatus;
    private int km;
    private String fechaEvento;
    private String color;
    private String resguardante;
    private String puesto;
    private String claveUr;
    private String ime;
    private int noMarca;
    public Vehiculo() {
    }

    public int getNoVehiculo() {
        return noVehiculo;
    }

    public void setNoVehiculo(int noVehiculo) {
        this.noVehiculo = noVehiculo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = (serie==null)?serie:serie.toUpperCase();
    }

    public String getPlacas() {
        return placas;
    }

    public void setPlacas(String placas) {
        this.placas = (placas==null)?placas:placas.toUpperCase();
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = (marca==null)?marca:marca.toUpperCase();
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        
        this.modelo = modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = (tipo==null)?tipo:tipo.toUpperCase();
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

    public int getNoUr() {
        return noUr;
    }

    public void setNoUr(int noUr) {
        this.noUr = noUr;
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

    public String getUr() {
        return ur;
    }

    public void setUr(String ur) {
        this.ur = ur.toUpperCase();
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(String fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = (color==null)?color:color.toUpperCase();
    }

    public String getResguardante() {
        return resguardante;
    }

    public void setResguardante(String resguardante) {
        this.resguardante = (resguardante==null)?resguardante:resguardante.toUpperCase();
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = (puesto == null) ? puesto : puesto.toUpperCase();
    }

    public String getClaveUr() {
        return claveUr;
    }

    public void setClaveUr(String claveUr) {
        this.claveUr = claveUr;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public int getNoMarca() {
        return noMarca;
    }

    public void setNoMarca(int noMarca) {
        this.noMarca = noMarca;
    }

    

}