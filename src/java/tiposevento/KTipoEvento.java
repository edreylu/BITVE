/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiposevento;

import formas.*;
import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author usuario
 */
public class KTipoEvento implements Serializable {
    private int noTipoEvento;
    private String tipoEvento;
    private String observaciones;
    private int estatus;

    public KTipoEvento() {
    }

    public int getNoTipoEvento() {
        return noTipoEvento;
    }

    public void setNoTipoEvento(int noTipoEvento) {
        this.noTipoEvento = noTipoEvento;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento.toUpperCase();
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones.toUpperCase();
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }


}
