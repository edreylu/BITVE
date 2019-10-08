/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globales;


import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@ManagedBean (name = "direcciona")
@SessionScoped
public class Direcciona implements java.io.Serializable{

  
   public Direcciona() {
    
     
   }

   @PostConstruct
   public void init() {
    
   }
   
    public void invalidaSesion() throws IOException{
      FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
      
   }
   
   public void index() throws IOException{
      invalidaSesion();
      Dir.redireccionar("/index.xhtml");
   }
   public void menu() throws IOException{
      invalidaSesion();
      Dir.redireccionar("/menu.xhtml");
   }
   
    
    
   }
