/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parametros;

import java.io.Serializable;
import java.sql.Blob;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author usuario
 */
public class Parametros implements Serializable {
    private int tiempoMaximoModif;
    private String mostrarInactivos;
    private Blob imagen;
    private byte[] imagenByte;
    private StreamedContent sc;
    private int noTipoEvento;
    public Parametros() {
    }

    public int getTiempoMaximoModif() {
        return tiempoMaximoModif;
    }

    public void setTiempoMaximoModif(int tiempoMaximoModif) {
        this.tiempoMaximoModif = tiempoMaximoModif;
    }

    public String getMostrarInactivos() {
        return mostrarInactivos;
    }

    public void setMostrarInactivos(String mostrarInactivos) {
        this.mostrarInactivos = mostrarInactivos.toUpperCase();
    }

    public Blob getImagen() {
        return imagen;
    }

    public void setImagen(Blob imagen) {
        this.imagen = imagen;
    }

    public byte[] getImagenByte() {
        return imagenByte;
    }

    public void setImagenByte(byte[] imagenByte) {
        this.imagenByte = imagenByte;
    }

    public StreamedContent getSc() {
        return sc;
    }

    public void setSc(StreamedContent sc) {
        this.sc = sc;
    }

    public int getNoTipoEvento() {
        return noTipoEvento;
    }

    public void setNoTipoEvento(int noTipoEvento) {
        this.noTipoEvento = noTipoEvento;
    }
    
}