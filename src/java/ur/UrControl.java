/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ur;


import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.PrimeFaces;
import reportes.ReportesControl;
import utilerias.Mensaje;
@ManagedBean (name = "urControl")
@SessionScoped
public class UrControl implements java.io.Serializable {

   private final UrDAO urdao;
   private List<Ur> urs;
   private List<Ur> ursgrupo;
   private Ur ur;
   private List<Ur> filteredUr;
   private ReportesControl rcontrol;
   private final Mensaje msg= new Mensaje();
   public UrControl() {
      urdao = new UrDAO();
      ur = new Ur();
      rcontrol=new ReportesControl();
   }
   
   public void init() {
        urs = urdao.traeRegistrosC();
   }

    public List<Ur> getUrs() {
        return urs;
    }

    public void setUrs(List<Ur> urs) {
        this.urs = urs;
    }

    public Ur getUr() {
        return ur;
    }

    public void setUr(Ur ur) {
        this.ur = ur;
    }

    public List<Ur> getFilteredUr() {
        return filteredUr;
    }

    public void setFilteredUr(List<Ur> filteredUr) {
        this.filteredUr = filteredUr;
    }

    public List<Ur> getUrsgrupo() {
        return ursgrupo;
    }

    public void setUrsgrupo(List<Ur> ursgrupo) {
        this.ursgrupo = ursgrupo;
    }

   
   public void CargaVentanaInserta(String ob){
    
        RequestContext context = RequestContext.getCurrentInstance();
            filteredUr=null;
            ur = new Ur();
            ursgrupo=urdao.traeRegistrosUrGrupo(0);
            PrimeFaces.current().ajax().update(":formInsertar");
            PrimeFaces.current().executeScript("PF('dlginsertar').show();");
    }
   public void CargaVentanaActualiza(int ob){
    
        RequestContext context = RequestContext.getCurrentInstance();
            ursgrupo=urdao.traeRegistrosUrGrupo(ob);
            PrimeFaces.current().ajax().update(":formModificar");
            PrimeFaces.current().executeScript("PF('dlgmodificar').show();");
    }
   
  public void insertar(){
     
           ur.setNoUr(urdao.getNoUrSiguiente());
           int valor=urdao.insertaUr(ur);
           if(valor==1){
            msg.info("Procesado..", "Registro guardado");
           }else{
            msg.warn("Error..", "No se guardo la informacion");
           }
           PrimeFaces.current().executeScript("PF('dlginsertar').hide();");
        }
  
  public void modificar(){
           int valor=0;
            System.out.println("valor de ur: "+ur.getUr());
             valor=urdao.actualizaUr(ur);
             if(valor==1){
            msg.info("Procesado..", "Registro actualizado");
             }else{
             msg.warn("Error", "No se actualizo la informacion");
                
             }
            
          PrimeFaces.current().executeScript("PF('dlgmodificar').hide();");
        }
  
      public void eliminar(){
        
       System.out.println("valor de ur a eliminar: "+ur.getNoUr());
           boolean existe=urdao.existeUrUsuario(ur.getNoUr());
           if(existe){
           msg.info("No se puede eliminar..", "existe un usuario usando esta UR");
           }else{
               boolean existe2=urdao.existeUrVehiculo(ur.getNoUr());
               if(existe2){msg.info("No se puede eliminar..", "existe un vehiculo usando esta UR");}
               else{
           int valor=urdao.eliminaUr(ur);
           if(valor==1){
            msg.info("Procesado..", "Registro eliminado");
           }else{
            msg.warn("Error..", "No se elimino la informacion");
           }
               }
           }
            PrimeFaces.current().executeScript("PF('dlgeliminar').hide();");
        }
      
    public void reporte(){
        String consulta="SELECT UR.NOUR,UR.CLAVEUR,UR.UR,UR.NOMBRE,UR.AP1,UR.AP2,UR.NOUR_GRUPO,"
                + "(SELECT UR2.UR FROM UR UR2 WHERE UR2.NOUR=UR.NOUR_GRUPO) NOUR_GRUPO_DESC,UR.ESTATUS FROM UR UR",
               tituloReporte="REPORTE UNIDADES RESPONSABLES",
               nombreReporte="REPORTE_URS";
        
    rcontrol.generaExcel(consulta, tituloReporte, nombreReporte);
    }
        public void cancelarActualizar(){PrimeFaces.current().executeScript("PF('dlgmodificar').hide();");}
        public void cancelarEliminar(){PrimeFaces.current().executeScript("PF('dlgeliminar').hide();");}

        
    
   
   
  
}
