/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roles;


import acceso.AccesoControl;
import acceso.Rolbotones;
import usuarios.UsuariosControl;
import formas.FormasMenu;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import org.primefaces.model.menu.MenuModel;
import utilerias.Mensaje;


@ManagedBean (name = "rolesControl")
@SessionScoped
public class RolesControl implements java.io.Serializable {
   @ManagedProperty(value = "#{accesoC}")
   private AccesoControl accesoC;
   private final RolesDAO roldao;
   private List<FormasMenu> menus;
   private DualListModel<FormasMenu> formasmenu;
   private DualListModel<String> formasmenus;
   private Roles rol;
   private Rolbotones rolbotones;
   private FormasMenu menu;
   private List<Roles> roles;
   private MenuModel model;
   private List<Roles> rolesseleccionados;
   private UsuariosControl jusuariosC;
   private String[] permisosseleccionados;   
   private List<String> permisos;
   private List<Roles> filteredRoles;
   private Mensaje msg= new Mensaje();
   public RolesControl() {
      roldao = new RolesDAO();
      rol = new Roles();
      jusuariosC = new UsuariosControl();
      menu = new FormasMenu();
      rolbotones = new Rolbotones();
      
      
   }
      @PostConstruct
   public void init() {
        roles = roldao.traeRegistros();
        
      permisos = new ArrayList<>();
      if(permisos.isEmpty()){
        permisos.add("INSERTAR");
        permisos.add("ACTUALIZAR");
        permisos.add("ELIMINAR");
        permisos.add("CONSULTAR");
        }
   }
     public List<Roles> getRoles() { return roles; }
   public void setRoles(List<Roles> roles) {
       roles = roldao.traeRegistros();
       this.roles = roles; 
   
   }

    public List<FormasMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<FormasMenu> menus) {
        this.menus = menus;
    }
 
   
   
          public void llenarpermisos(long usuario){
              int us=(int) usuario;
              rolbotones = roldao.getPermisos(us);
            }

    public List<Roles> getFilteredRoles() {
        return filteredRoles;
    }

    public void setFilteredRoles(List<Roles> filteredRoles) {
        this.filteredRoles = filteredRoles;
    }

    public void CargaVentanaInserta(String ob){
    
        RequestContext context = RequestContext.getCurrentInstance();
            filteredRoles=null;
            rol = new Roles();
            PrimeFaces.current().ajax().update(":formInsertar");
            PrimeFaces.current().executeScript("PF('dlginsertar').show();");
    }
          
  public void insertar(){
     
          rol.setNoRol(roldao.getNoRolSiguiente());
          rol.setInsertar("N"); 
          rol.setActualizar("N"); 
          rol.setEliminar("N"); 
          rol.setConsultar("N"); 
      
      if (rol.getInsertarSelI().equals(true)){ rol.setInsertar("S");}
      if (rol.getActualizarSelI().equals(true)){rol.setActualizar("S");}
      if (rol.getEliminarSelI().equals(true)){rol.setEliminar("S");}
      if (rol.getConsultarSelI().equals(true)){rol.setConsultar("S");}
      rol.setDescargar("S");
      
       System.out.println("valor de rol insertar: "+rol.getNoRol());
       System.out.println("valor de permisos para insertar:"+rol.getInsertar());
       System.out.println("valor de permisos para actualizar:"+rol.getActualizar());
       System.out.println("valor de permisos para eliminar:"+rol.getEliminar());
       System.out.println("valor de permisos para consultar:"+rol.getConsultar());
       
           int valor=roldao.insertaRol(rol);
           if(valor==1){
            msg.info("Procesado..", "Registro guardado");
            
           }else{
            msg.warn("Error..", "No se guardo la informacion");
           }
           PrimeFaces.current().executeScript("PF('dlginsertar').hide();");
        }
  
  public void modificar(){
           rol.setInsertar("N"); 
          rol.setActualizar("N"); 
          rol.setEliminar("N"); 
          rol.setConsultar("N");
          rol.setDescargar("S"); 
      if (rol.getInsertarSel().equals(true)){ rol.setInsertar("S");}
      if (rol.getActualizarSel().equals(true)){rol.setActualizar("S");}
      if (rol.getEliminarSel().equals(true)){rol.setEliminar("S");}
      if (rol.getConsultarSel().equals(true)){rol.setConsultar("S");}
         
           
       System.out.println("valor de usuario actualizar: "+rol.getInsertar()+"-"+rol.getActualizar()+"-"+rol.getEliminar()+"-"+rol.getConsultar()+" con clave: "+rol.getNoRol());
           int valor=roldao.actualizaRol(rol);
           if(valor==1){
            msg.info("Procesado..", "Registro actualizado");
             }else{
               msg.warn("Procesado..", "Registro actualizado");
             }
           PrimeFaces.current().executeScript("PF('dlgmodificar').hide();");
        }
      public void eliminar(){
           System.out.println("valor de rol a eliminar: "+rol.getNoRol());
           boolean existe = roldao.existeRolFormas(rol.getNoRol());
           boolean existe2 = roldao.existeRolUsuario(rol.getNoRol());
            if(existe){
                msg.warn("Error..", "No se puede eliminar Rol pues tiene pantallas asociadas");
            }else if(existe2){
                msg.warn("Error..", "No se puede eliminar Rol pues tiene usuarios que lo utilizan");
            }
            else{
            int valor=roldao.eliminaRol(rol);
            if(valor==1){
            msg.warn("Procesado..", "Registro eliminado");
           }else{
            msg.warn("Error..", "No se elimino la informacion");
            
           }
            }
            PrimeFaces.current().executeScript("PF('dlgeliminar').hide();");
            }
      
           public void asignar(){
           System.out.println("valor de rol a asignar: "+rol.getNoRol());
           boolean existe = roldao.existeRolFormas(rol.getNoRol());
            if(existe){
            roldao.eliminaFormaMenu(rol);
            }
           int valor=roldao.asignaFormaMenu(rol.getNoRol(),menus);
           if(valor==-1){
           msg.error("Error", "No se pudo asignar las Pantallas");
        }else{
           msg.info("Procesado..", valor+" Pantallas Asignadas");
           }
        PrimeFaces.current().executeScript("PF('dlgasignar').hide();");   
        }

         public void asignapantallas(Roles ro){
         RequestContext context = RequestContext.getCurrentInstance();    
         menus= roldao.traeRegistrosM(ro);
        
         PrimeFaces.current().ajax().update(":formAsignar");
         PrimeFaces.current().executeScript("PF('dlgasignar').show();");
        }
            
        public void cancelarActualizar(){PrimeFaces.current().executeScript("PF('dlgmodificar').hide();");}
        public void cancelarAsignar(){PrimeFaces.current().executeScript("PF('dlgasignar').hide();");}
        public void cancelarEliminar(){PrimeFaces.current().executeScript("PF('dlgeliminar').hide();");}
   
 public Roles getRol() {


return rol;


}
    public void setRol(Roles rol) {


this.rol = rol;


}

    public Rolbotones getRolbotones() {
        return rolbotones;
    }

    public void setRolbotones(Rolbotones rolbotones) {
        this.rolbotones = rolbotones;
    }
    
    

    public FormasMenu getMenu() {
        return menu;
    }

    public void setMenu(FormasMenu menu) {
        this.menu = menu;
    }

    public DualListModel<FormasMenu> getFormasmenu() {
        return formasmenu;
    }

    public void setFormasmenu(DualListModel<FormasMenu> formasmenu) {
        this.formasmenu = formasmenu;
    }

    public DualListModel<String> getFormasmenus() {
        return formasmenus;
    }

    public void setFormasmenus(DualListModel<String> formasmenus) {
        this.formasmenus = formasmenus;
    }
    
    
    
    
     public List<Roles> getRolesseleccionados() {
        return rolesseleccionados;
    }

    public void setRolesseleccionados(List<Roles> rolesseleccionados) {
        this.rolesseleccionados = rolesseleccionados;
    }
    
     public String[] getPermisosseleccionados() {
        return permisosseleccionados;
    }
 
    public void setPermisosseleccionados(String[] permisosseleccionados) {
        this.permisosseleccionados = permisosseleccionados;
    }
 
    public List<String> getPermisos() {
        return permisos;
    }

    public UsuariosControl getJusuariosC() {
        return jusuariosC;
    }

    public void setJusuariosC(UsuariosControl jusuariosC) {
        this.jusuariosC = jusuariosC;
    }

   
  public MenuModel getModel() {
         
        return model;
 } 
  public AccesoControl getAccesoC() { return accesoC; }
   public void setAccesoC(AccesoControl accesoMB) { this.accesoC = accesoMB; }
   @Override
   protected void finalize() throws Throwable {
      super.finalize();
   }
}
