/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuarios;


import formas.FormasMenuControl;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;
import roles.Roles;
import roles.RolesDAO;
import ur.Ur;
import ur.UrDAO;
import utilerias.Mensaje;


@ManagedBean (name = "usuariosControl")
@SessionScoped
public class UsuariosControl implements java.io.Serializable {
   private final UsuariosDAO pdao;
   private final RolesDAO rdao;
   private final UrDAO urdao;
   private Usuarios usuario;
   private List<Usuarios> usuarios;
   private List<Usuarios> filteredUsuarios;
   private List<Roles> roles;
   private List<Ur> urs;
   private List<Ur> filteredUrs;
   private Mensaje msg= new Mensaje();
   private boolean isSelectedAll;
   public UsuariosControl() {
      pdao = new UsuariosDAO();
      rdao = new RolesDAO();
      urdao = new UrDAO();
      usuario = new Usuarios();
   }
      @PostConstruct
   public void init() {
        usuarios = pdao.traeRegistros();
        roles=rdao.traeRegistros();
        //urs=null;
   }
     public List<Usuarios> getUsuarios() { return usuarios; }
   public void setUsuarios(List<Usuarios> usuarios) { 
       this.usuarios = usuarios; 
   
   }

    public List<Usuarios> getFilteredUsuarios() {
        //usuarios = pdao.traeRegistros();
        return filteredUsuarios;
    }

    public void setFilteredUsuarios(List<Usuarios> filteredUsuarios) {
        this.filteredUsuarios = filteredUsuarios;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public List<Ur> getUrs() {
        return urs;
    }

    public void setUrs(List<Ur> urs) {
        this.urs = urs;
    }

    public List<Ur> getFilteredUrs() {
        return filteredUrs;
    }

    public void setFilteredUrs(List<Ur> filteredUrs) {
        this.filteredUrs = filteredUrs;
    }
   
    
   public Usuarios existePersona(String clave, String pas) {
    if(!clave.equals("") && !pas.equals("")){
        usuario=pdao.existePersona(clave,pas);
     }
    return usuario;
   }

   
   public List<String> existePermisoPantalla(int noPersonal) {
    
    List<String> pantallas=pdao.getPermisoPantalla(noPersonal);
    return pantallas;
   }
   
   public Long existePersonaSP(String clave) {
       long valor = 0;
    if(!clave.equals("")){
        valor=pdao.existePersonaSP(clave);
     }
    System.out.println("clave: "+clave+" valor: "+valor);
    return valor;
   }


   public void cargarQuery(String query) {
    
      pdao.ejecutarQuery(query);
   }
   
       public void CargaVentanaInserta(String ob){
    
        RequestContext context = RequestContext.getCurrentInstance();
            filteredUsuarios=null;
            usuario= new Usuarios();
            PrimeFaces.current().ajax().update(":formInsertar");
            PrimeFaces.current().executeScript("PF('dlginsertar').show();");
    }
       
       public void insertar(){
          
      usuario.setNoUsuario(pdao.getNoUsuarioSiguiente());
      System.out.println("valor de numero de usuario a insertar: "+usuario.getNoUsuario());
           
           int valor=pdao.insertaUsuario(usuario);
           if(valor==1){
               System.out.println("valor de rol a asignar: "+usuario.getNoRol());
            pdao.eliminaRolUsuario(usuario.getNoUsuario());
            pdao.asignaRolUsuario(usuario.getNoUsuario(),usuario.getNoRol());
            msg.info("Procesado..", "Registro guardado");
            
           }else{
            msg.warn("Error..", "No se guardo la informacion");
           }
           PrimeFaces.current().executeScript("PF('dlginsertar').hide();");
        }
       
       public void modificar(){
       System.out.println("valor de usuario actualizar: "+usuario.getClave()+ " con numero de usuario: "+usuario.getNoUsuario());
           
           int valor=pdao.actualizaUsuario(usuario);
           if(valor==1){
               System.out.println("valor de rol a asignar: "+usuario.getNoRol());
             pdao.eliminaRolUsuario(usuario.getNoUsuario());
             pdao.asignaRolUsuario(usuario.getNoUsuario(),usuario.getNoRol());
            
            msg.info("Procesado..", "Registro actualizado");
             }else{
               msg.warn("Error..", "Registro no actualizado");
             }
           PrimeFaces.current().executeScript("PF('dlgmodificar').hide();");
        }
       
       public void modificarPasaporte(){
           
           int valor=pdao.actualizaPasaporte(usuario.getNoUsuario(),usuario.getPasaporte());
           if(valor==1){
            msg.info("Procesado..", "Password actualizado");
             }else{
               msg.warn("Error..", "Password no actualizado");
             }
           PrimeFaces.current().executeScript("PF('dlgpasaporte').hide();");
        }
       
       public void modificarPasaporteSP(int us,String pas){
           
           int valor=pdao.actualizaPasaporte(us,pas);
           if(valor==1){
            msg.info("Procesado..", "Password actualizado");
             }else{
               msg.warn("Error..", "Password no actualizado");
             }
           PrimeFaces.current().executeScript("PF('dlgpasaporte').hide();");
        }
    
    public void eliminar(){
            
       System.out.println("valor de usuario eliminar: "+usuario.getNoUsuario());
       
           int noUsuario=usuario.getNoUsuario();
           int valor=pdao.eliminaUsuario(usuario);
            if(valor==1){
            boolean existe = pdao.existeRolUsuario(noUsuario);
            boolean existe2 = pdao.existeUrUsuario(noUsuario);
            if(existe){
            pdao.eliminaRolUsuario(usuario.getNoUsuario());
            }
            if(existe2){
            pdao.eliminaUrUsuario(usuario);
            }
            
            msg.info("Procesado..", "Registro inactivado");
           }else{
            msg.warn("Error..", "No se inactivo la informacion");
           }
            PrimeFaces.current().executeScript("PF('dlgeliminar').hide();");
          
        }
   
    public void asignar(){
           System.out.println("valor de usuario a asignar  ur: "+usuario.getNoUsuario());
            boolean existe = pdao.existeUrUsuario(usuario.getNoUsuario());
            if(existe){
            pdao.eliminaUrUsuario(usuario);
            }
        int valor=pdao.asignaUrUsuario(usuario.getNoUsuario(),urs);
        if(valor==-1){
        msg.error("Error", "No se pudo asignar las URS");
        }else{
        msg.info("Procesado..", valor+" Urs Asignadas");
        }
        PrimeFaces.current().executeScript("PF('dlgasignar').hide();"); 
        }
       
    public void asignaur(Usuarios us){
         filteredUrs=null;
         urs= pdao.traeRegistrosur(us);
         PrimeFaces.current().executeScript("PF('dlgasignar').show();");
        }
      
     public void valueChange(ValueChangeEvent event)
			throws AbortProcessingException {
	boolean select=(boolean) event.getNewValue();
        System.out.println("valueChange : "+select);
        
	for(int i=0;i<urs.size();i++) {
             Ur ur=urs.get(i);
             ur.setUrSeleccionada(select);
	    urs.set(i, ur);
	}
         
         PrimeFaces.current().ajax().update(":formAsignar");
	}
     
     public void selectodo() {
        boolean val = isSelectedAll;
        for(int i=0;i<urs.size();i++) {
             Ur ur=urs.get(i);
             ur.setUrSeleccionada(val);
	    urs.set(i, ur);
	}
         
         PrimeFaces.current().ajax().update(":formAsignar");
    }
     public void selecUno(Ur ur) {
        for(int i=0;i<urs.size();i++) {
            if(urs.get(i).getNoUr()==ur.getNoUr()){
                if(ur.isUrSeleccionada()){
                ur.setUrSeleccionada(true);
	        urs.set(i, ur);
                }else{
                ur.setUrSeleccionada(false);
	        urs.set(i, ur);
            }
            }
	}
         
         PrimeFaces.current().ajax().update(":formAsignar");
    }
     
        public void cancelarActualizar(){PrimeFaces.current().executeScript("PF('dlgmodificar').hide();");}
        public void cancelarAsignar(){PrimeFaces.current().executeScript("PF('dlgasignar').hide();");}
        public void cancelarEliminar(){PrimeFaces.current().executeScript("PF('dlgeliminar').hide();");}

    public boolean isIsSelectedAll() {
        return isSelectedAll;
    }

    public void setIsSelectedAll(boolean isSelectedAll) {
        this.isSelectedAll = isSelectedAll;
    }
      
   
 public Usuarios getUsuario() {


return usuario;


}
    public void setUsuario(Usuarios usuario) {


this.usuario = usuario;


}
    

  
}
