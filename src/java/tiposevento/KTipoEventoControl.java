/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiposevento;


import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.PrimeFaces;
import utilerias.Mensaje;
@ManagedBean (name = "kTIpoEventoControl")
@SessionScoped
public class KTipoEventoControl implements java.io.Serializable {

   private final KTipoEventoDAO ktedao;
   private List<KTipoEvento> ktiposevento;
   private KTipoEvento ktipoevento;
   private List<KTipoEvento> filteredTiposEvento;
   private Mensaje msg= new Mensaje();
   public KTipoEventoControl() {
      ktedao = new KTipoEventoDAO();
      ktipoevento = new KTipoEvento();
   }
   
   public void init() {
        ktiposevento = ktedao.traeRegistros();
   }

    public List<KTipoEvento> getKtiposevento() {
        return ktiposevento;
    }

    public void setKtiposevento(List<KTipoEvento> ktiposevento) {
        ktiposevento = ktedao.traeRegistros();
        this.ktiposevento = ktiposevento;
    }

    public List<KTipoEvento> getFilteredTiposEvento() {
        return filteredTiposEvento;
    }

    public void setFilteredTiposEvento(List<KTipoEvento> filteredTiposEvento) {
        this.filteredTiposEvento = filteredTiposEvento;
    }

    public KTipoEvento getKtipoevento() {
        return ktipoevento;
    }

    public void setKtipoevento(KTipoEvento ktipoevento) {
        this.ktipoevento = ktipoevento;
    }

   
   public void CargaVentanaInserta(String ob){
    
        RequestContext context = RequestContext.getCurrentInstance();
            filteredTiposEvento=null;
            ktipoevento = new KTipoEvento();
            PrimeFaces.current().ajax().update(":formInsertar");
            PrimeFaces.current().executeScript("PF('dlginsertar').show();");
    }
   
  public void insertar(){
     
           ktipoevento.setNoTipoEvento(ktedao.getNoTipoEventoSiguiente());
           int valor=ktedao.insertaTipoEvento(ktipoevento);
           if(valor==1){
            msg.info("Procesado..", "Registro guardado");
           }else{
            msg.warn("Error..", "No se guardo la informacion");
           }
           PrimeFaces.current().executeScript("PF('dlginsertar').hide();");
        }
  
  public void modificar(){
           int valor=0;
            System.out.println("valor de pantalla3: "+ktipoevento.getTipoEvento());
           
             valor=ktedao.actualizaTipoEvento(ktipoevento);
             if(valor==1){
            msg.info("Procesado..", "Registro actualizado");
             }else{
             msg.warn("Error", "No se actualizo la informacion");
                
             }
            
          PrimeFaces.current().executeScript("PF('dlgmodificar').hide();");
        }
  
      public void eliminar(){
        
       System.out.println("valor a eliminar: "+ktipoevento.getNoTipoEvento());
       boolean existe=ktedao.existeTipoEventoEvento(ktipoevento.getNoTipoEvento());
       if(existe){
       msg.info("No se puede eliminar..", "Existe un evento con este tipo de evento");
       }
       else{
           int valor=ktedao.eliminaTipoEvento(ktipoevento);
           if(valor==1){
            msg.info("Procesado..", "Registro eliminado");
           }else{
            msg.warn("Error..", "No se elimino la informacion");
            
           }
       }
            PrimeFaces.current().executeScript("PF('dlgeliminar').hide();");
        }
      

        public void cancelarActualizar(){PrimeFaces.current().executeScript("PF('dlgmodificar').hide();");}
        public void cancelarEliminar(){PrimeFaces.current().executeScript("PF('dlgeliminar').hide();");}

        
    
   
   
  
}
