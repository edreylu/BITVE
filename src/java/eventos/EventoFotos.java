/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventos;

/**
 *
 * @author usuario
 */
     

import java.sql.Blob;
import org.primefaces.model.DefaultStreamedContent;

public class EventoFotos implements java.io.Serializable{
private int noEvento;
     private int noImagen;
     private Blob imagen;
     private byte[] imagenByte;
     private DefaultStreamedContent img;
    public int getNoEvento() {
        return noEvento;
    }

    public void setNoEvento(int noEvento) {
        this.noEvento = noEvento;
    }

    public int getNoImagen() {
        return noImagen;
    }

    public void setNoImagen(int noImagen) {
        this.noImagen = noImagen;
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

    public DefaultStreamedContent getImg() {
        return img;
    }

    public void setImg(DefaultStreamedContent img) {
        this.img = img;
    }

     
}
