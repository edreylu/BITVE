/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parametros;


import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.PrimeFaces;
import utilerias.Mensaje;
@ManagedBean (name = "parametrosControl")
@SessionScoped
public class ParametrosControl implements java.io.Serializable {

   private final ParametrosDAO pardao;
   private Parametros par;
   private List<Parametros> parametros;
   private Mensaje msg= new Mensaje();
   public ParametrosControl() {
      par = new Parametros();
      pardao=new ParametrosDAO();
   }
   
   public void init() {
        par = pardao.parametros();
        //parametros=pardao.traeRegistrosParametros();
   }

    public Parametros getPar() {
        return par;
    }

    public void setPar(Parametros par) {
        this.par = par;
    }

    public Mensaje getMsg() {
        return msg;
    }

    public void setMsg(Mensaje msg) {
        this.msg = msg;
    }

    public List<Parametros> getParametros() {
        return parametros;
    }

    public void setParametros(List<Parametros> parametros) {
        this.parametros = parametros;
    }

    

   

   
   
  public void insertar(){
     
           int valor=pardao.insertaParametros(par);
           if(valor==1){
            msg.info("Procesado..", "Registro guardado");
           }else{
            msg.warn("Error..", "No se guardo la informacion");
           }
           PrimeFaces.current().executeScript("PF('dlginsertar').hide();");
        }
  
  public void modificar(){
           int valor=0;
            System.out.println("valor de par: "+par.getTiempoMaximoModif());
           
             valor=pardao.actualizaParametros(par);
             if(valor==1){
            msg.info("Procesado..", "Registro actualizado");
             }else{
             msg.warn("Error", "No se actualizo la informacion");
                
             }
          PrimeFaces.current().executeScript("PF('dlgmodificar').hide();");
        }
  
      public void eliminar(){
        
      
           /*boolean existe=urdao.existeUrUsuario(ur.getNoUr());
           if(existe){
           msg.info("No se puede eliminar..", "existe un usuario usando esta UR");
           }else{*/
           int valor=pardao.eliminaParametros(par);
           if(valor==1){
            msg.info("Procesado..", "Registro eliminado");
           }else{
            msg.warn("Error..", "No se elimino la informacion");
           }
           
            PrimeFaces.current().executeScript("PF('dlgeliminar').hide();");
        }
      /*
      public void subirImagen(FileUploadEvent event) throws IOException {
        par = new Parametros();
        par.setImagenByte(IOUtils.toByteArray(event.getFile().getInputstream()));
        int valor=pardao.insertaImagen(par);
        if(valor==1){
               msg.info("Procesado..", "Imagen subida");
               
           }else{
               msg.warn("Error..", "No se subio Imagen");
            
           }
        pardao.imagenReporte();
        PrimeFaces.current().executeScript("PF('dlgsubir').hide();");
    }
*/
        public void cancelarActualizar(){PrimeFaces.current().executeScript("PF('dlgmodificar').hide();");}
        public void cancelarEliminar(){PrimeFaces.current().executeScript("PF('dlgeliminar').hide();");}

        
    
   
   
  
}
