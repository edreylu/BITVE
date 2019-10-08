/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formas;


import usuarios.UsuariosControl;
import acceso.AccesoControl;
import globales.Dir;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.PrimeFaces;
import roles.Roles;
import utilerias.Mensaje;
@ManagedBean (name = "menuControl")
@SessionScoped
public class FormasMenuControl implements java.io.Serializable {
@ManagedProperty(value = "#{accesoC}")
   private AccesoControl accesoC;
   private final FormasMenuDAO menudao;
   private List<FormasMenu> menus;
   private List<FormasMenu> menusp;
   private FormasMenu menu;
   private Roles rol;
   private MenuModel model;
   private UsuariosControl usuariosC;
   private List<FormasMenu> filteredFormasMenu;
   private Mensaje msg= new Mensaje();
   public FormasMenuControl() {
      menudao = new FormasMenuDAO();
      menu = new FormasMenu();
      usuariosC = new UsuariosControl();
      rol = new Roles(); 
   }
   
   public void init() {
        menus = menudao.traeRegistros();
        menusp = menudao.traeRegistrosp();
   }

    public List<FormasMenu> getMenusp() {
        return menusp;
    }

    public void setMenusp(List<FormasMenu> menusp) {
        menusp= menudao.traeRegistrosp();
        this.menusp = menusp;
    }
      
   
   public List<FormasMenu> getMenus() { return menus; }
   public void setMenus(List<FormasMenu> menus) { 
   
   menus= menudao.traeRegistros();
   
   this.menus = menus; 
   }

    public List<FormasMenu> getFilteredFormasMenu() {
        return filteredFormasMenu;
    }

    public void setFilteredFormasMenu(List<FormasMenu> filteredFormasMenu) {
        this.filteredFormasMenu = filteredFormasMenu;
    }
   
   public void CargaVentanaInserta(String ob){
    
        RequestContext context = RequestContext.getCurrentInstance();
            filteredFormasMenu=null;
            menu = new FormasMenu();
            PrimeFaces.current().ajax().update(":formInsertar");
            PrimeFaces.current().executeScript("PF('dlginsertar').show();");
    }
    public void CargaVentanaModifica(FormasMenu ob){
    
        RequestContext context = RequestContext.getCurrentInstance();
        
             menusp= menudao.traeRegistrospCOM(ob.getNoFormaMenu());
            PrimeFaces.current().ajax().update(":formModificar");
            PrimeFaces.current().executeScript("PF('dlgmodificar').show();");
    }
  public void insertar(){
     
           menu.setNoFormaMenu(menudao.getNoMenuSiguiente());
           int valor=menudao.insertaForma(menu);
           if(valor==1){
            msg.info("Procesado..", "Registro guardado");
           }else{
            msg.warn("Error..", "No se guardo la informacion");
           }
           PrimeFaces.current().executeScript("PF('dlginsertar').hide();");
        }
  
  public void modificar(){
           int valor=0;
           boolean existe = false;
           existe = menudao.existeHijos(menu.getNoFormaMenu());
            System.out.println("valor de pantalla1: "+menu.getUrl());
            System.out.println("valor de pantalla2: "+menu.getIcono());
            System.out.println("valor de pantalla3: "+menu.getNoFormaPadre());
            if(existe){
            if(menu.getNoFormaPadre() != 0){
            msg.warn("Error", "No se puede actualizar Funcion pues tiene paginas asociadas");
            }else{
             valor=menudao.actualizaForma(menu);
             if(valor==1){
            msg.info("Procesado..", "Registro actualizado");
             }else{
                msg.warn("Error", "No se actualizo la informacion");
             }
            }
            }
            else{
             valor=menudao.actualizaForma(menu);
             if(valor==1){
            msg.info("Procesado..", "Registro actualizado");
             }else{
             msg.warn("Error", "No se actualizo la informacion");
                
             }
            
            }
          PrimeFaces.current().executeScript("PF('dlgmodificar').hide();");
        }
  
      public void eliminar(){
        
       System.out.println("valor de pantalla a eliminar: "+menu.getNoFormaMenu());
       boolean existe = false;
           existe = menudao.existeHijos(menu.getNoFormaMenu());
            if(existe){
            msg.warn("Error", "No se puede eliminar Funcion pues tiene pantallas asociadas");
            }
            else{
           boolean existe2 = menudao.existeFormaRol(menu.getNoFormaMenu());
           if(existe2){
           msg.info("No se puede eliminar..", "La funcion esta asignada aun rol, quitar del rol antes de eliminar");
           }
           else{
           int valor=menudao.eliminaForma(menu);
           if(valor==1){
            msg.info("Procesado..", "Registro eliminado");
           }else{
            msg.warn("Error..", "No se elimino la informacion");
           }
           }
            }
            PrimeFaces.current().executeScript("PF('dlgeliminar').hide();");
        }
      
       public void llenarlistas(){
       menus = menudao.traeRegistros();
        menusp = menudao.traeRegistrosp();
        }

        public void cancelarActualizar(){PrimeFaces.current().executeScript("PF('dlgmodificar').hide();");}
        public void cancelarEliminar(){PrimeFaces.current().executeScript("PF('dlgeliminar').hide();");}
 public FormasMenu getMenu() {


return menu;


}
    public void setMenu(FormasMenu menu) {


this.menu = menu;


}
    


   
  public MenuModel getModel() {
         
        return model;
 } 
  
   
   
   public void llenaMenu(){

        model = new DefaultMenuModel();
        int noUsuario=accesoC.getNoUsuario();
        if(noUsuario!=0){
        List<FormasMenu> listaM = menudao.obtenerMenu(noUsuario);
         
        String nombrePapa = "";
        DefaultSubMenu submenu = new DefaultSubMenu();
        DefaultMenuItem item;
        submenu = new DefaultSubMenu("INICIO");
        item = new DefaultMenuItem("INFORMACION");
        item.setIcon("fa fa-fw fa-home");
        item.setUrl("menu.xhtml");
        submenu.addElement(item);
        model.addElement(submenu);
        for (FormasMenu men : listaM) {
            if (!men.getNombrePapa().equals(nombrePapa)) {
                submenu = new DefaultSubMenu(men.getNombrePapa());
            } 
                
                item = new DefaultMenuItem(men.getTitulo());
                item.setIcon(men.getIcono());
                item.setUrl(men.getUrl());
                submenu.addElement(item);
            
            
            if (!men.getNombrePapa().equals(nombrePapa)) {
                nombrePapa = men.getNombrePapa();
                model.addElement(submenu);
            }
        }
        }else{
        Dir.redireccionar("/index.xhtml");
        }
}
   
    public AccesoControl getAccesoC() { return accesoC; }
   public void setAccesoC(AccesoControl accesoMB) { this.accesoC = accesoMB; }
   @Override
   protected void finalize() throws Throwable {
      super.finalize();
   }
}
