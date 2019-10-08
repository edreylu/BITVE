/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author
 */
public class Mensaje implements Serializable {

    public Mensaje() {
    }
    
    public void info(String titulo, String cuerpo) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, titulo,cuerpo));
    }
     
    public void warn(String titulo, String cuerpo) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, titulo,cuerpo));
    }
     
    public void error(String titulo, String cuerpo) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo,cuerpo));
    }
     
    public void fatal(String titulo, String cuerpo) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, titulo,cuerpo));
    }  
    
}
