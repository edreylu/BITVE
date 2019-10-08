/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formas;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean (name = "botones")
@SessionScoped
public class Botones {
   private String Guardar;
   private String Cancelar;
   private String Insertar;
   private String Actualizar;
   private String Eliminar; 
    
   private String GuardarModal;
   private String CancelarModal;
   private String InsertarModal;
   private String ActualizarModal;
   private String EliminarModal;

   public void botones(int i){
       
      switch(i){
          
          case 1://si contiene al menos un registro
            Insertar="S";
            Actualizar="S";
            Guardar="N";
            Cancelar="N";
              break;
                
              case 2://al pulsar insertar se activa guardar y cancelar
            Guardar="S";
            Cancelar="S";
            Insertar="N";
            Actualizar="N";
            break;
               
          case 3:// al pulsar cancelar se desactivan guardar y cancelar 
              Guardar="N";
              Cancelar="N";
              break;
              
          case 4: 
              Insertar="N";
            Actualizar="S";
            Guardar="N";
            Cancelar="N";
              break;    
               }    
   }
   
   
   public void botonesModal(int i){
       
      switch(i){
          case 1: //si esta vacia la lista
            InsertarModal="S";
            GuardarModal="N";
            CancelarModal="N";
            ActualizarModal="N";
            EliminarModal ="N";
            break;
               
          case 2://si contiene al menos un registro
            InsertarModal="S";
            ActualizarModal="S";
            EliminarModal ="S";
            GuardarModal="N";
            CancelarModal="N";
              break;
                
              case 3://al pulsar insertar se activa guardar y cancelar
            GuardarModal="S";
            CancelarModal="S";
            InsertarModal="N";
            ActualizarModal="N";
            EliminarModal ="N";
            break;
               
          case 4:// al pulsar cancelar se desactivan guardar y cancelar 
              GuardarModal="N";
              CancelarModal="N";
              break;
               }    
      
      
}

    public String getGuardar() {
        return Guardar;
    }

    public void setGuardar(String Guardar) {
        this.Guardar = Guardar;
    }

    public String getCancelar() {
        return Cancelar;
    }

    public void setCancelar(String Cancelar) {
        this.Cancelar = Cancelar;
    }

    public String getInsertar() {
        return Insertar;
    }

    public void setInsertar(String Insertar) {
        this.Insertar = Insertar;
    }

    public String getActualizar() {
        return Actualizar;
    }

    public void setActualizar(String Actualizar) {
        this.Actualizar = Actualizar;
    }

    public String getEliminar() {
        return Eliminar;
    }

    public void setEliminar(String Eliminar) {
        this.Eliminar = Eliminar;
    }
   
   
    public String getGuardarModal() {
        return GuardarModal;
    }

    public void setGuardarModal(String GuardarModal) {
        this.GuardarModal = GuardarModal;
    }

    public String getCancelarModal() {
        return CancelarModal;
    }

    public void setCancelarModal(String CancelarModal) {
        this.CancelarModal = CancelarModal;
    }

    public String getInsertarModal() {
        return InsertarModal;
    }

    public void setInsertarModal(String InsertarModal) {
        this.InsertarModal = InsertarModal;
    }

    public String getActualizarModal() {
        return ActualizarModal;
    }

    public void setActualizarModal(String ActualizarModal) {
        this.ActualizarModal = ActualizarModal;
    }

    public String getEliminarModal() {
        return EliminarModal;
    }

    public void setEliminarModal(String EliminarModal) {
        this.EliminarModal = EliminarModal;
    }
   
   
   
   
   
   
   
   
}
