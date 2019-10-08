/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogos;

import java.io.Serializable;

/**
 *
 * @author usuario
 */
public class Marca implements Serializable{
    
    private int noMarca;
    private String marca;
    private String estatus;
    private String observaciones;
    private String fechaRegistro;

    public Marca(int noMarca, String marca, String estatus, String observaciones, String fechaRegistro) {
        this.noMarca = noMarca;
        this.marca = marca;
        this.estatus = estatus;
        this.observaciones = observaciones;
        this.fechaRegistro = fechaRegistro;
    }
    
    public Marca(){}

    public int getNoMarca() {
        return noMarca;
    }

    public void setNoMarca(int noMarca) {
        this.noMarca = noMarca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    
    
}
