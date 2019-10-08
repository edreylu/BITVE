/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ur;

import java.io.Serializable;

/**
 *
 * @author usuario
 */
public class Ur implements Serializable {
    private int noUr;
    private String claveUr;
    private String ur;
    private String ap1;
    private String ap2;
    private String nombre;
    private String estatus;
    private boolean urSeleccionada;
    private int noUrGrupo;
    private String claveUrGrupo;
    public Ur() {
    }

    public int getNoUr() {
        return noUr;
    }

    public void setNoUr(int noUr) {
        this.noUr = noUr;
    }

    public String getClaveUr() {
        return claveUr;
    }

    public void setClaveUr(String claveUr) {
        this.claveUr = claveUr;
    }
    
    public String getUr() {
        return ur;
    }

    public void setUr(String ur) {
        this.ur = ur==null?ur:ur.toUpperCase();
    }

    public String getAp1() {
        return ap1;
    }

    public void setAp1(String ap1) {
        this.ap1 = ap1==null?ap1:ap1.toUpperCase();
    }

    public String getAp2() {
        return ap2;
    }

    public void setAp2(String ap2) {
        this.ap2 = ap2==null?ap2:ap2.toUpperCase();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre==null?nombre:nombre.toUpperCase();
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public boolean isUrSeleccionada() {
        return urSeleccionada;
    }

    public void setUrSeleccionada(boolean urSeleccionada) {
        this.urSeleccionada = urSeleccionada;
    }

    public int getNoUrGrupo() {
        return noUrGrupo;
    }

    public void setNoUrGrupo(int noUrGrupo) {
        this.noUrGrupo = noUrGrupo;
    }

    public String getClaveUrGrupo() {
        return claveUrGrupo;
    }

    public void setClaveUrGrupo(String claveUrGrupo) {
        this.claveUrGrupo = claveUrGrupo;
    }

    
}