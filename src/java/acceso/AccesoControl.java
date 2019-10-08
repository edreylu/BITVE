/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acceso;

import usuarios.UsuariosControl;
import globales.Dir;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;
import usuarios.Usuarios;
import utilerias.Mensaje;

/**
 *
 * 
 */
@ManagedBean (name = "accesoC")
@SessionScoped
public class AccesoControl implements java.io.Serializable {
   
   private int noUsuario;
   private String clave;
   private String pasaporte;
   private String clavec;
   private String pasaportec;
   private String pasaportec2;
   private Rolbotones rolbotones;
   private final AccesoDAO accdao;
   private String nombreCompleto;
   private Mensaje msg = new Mensaje();
   private Usuarios usuario;
   private List<String> pantallas;
   public AccesoControl() {
       accdao= new AccesoDAO();
   }

   @PostConstruct
   public void init() {
      clave = "";
      pasaporte = "";
      nombreCompleto="";
      usuario=null;
   }

    public int getNoUsuario() {
        return noUsuario;
    }

    public void setNoUsuario(int noUsuario) {
        this.noUsuario = noUsuario;
    }

   

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = null != clave ? clave.toUpperCase() : null;
    }

    public String getPasaporte() {
        return pasaporte;
    }

    public void setPasaporte(String pasaporte) {
        this.pasaporte = null != pasaporte ? pasaporte.toUpperCase() : null;
    }

    public String getClavec() {
        return clavec;
    }

    public void setClavec(String clavec) {
        this.clavec =  this.clavec = null != clavec ? clavec.toUpperCase() : null;
    }

    public String getPasaportec() {
        return pasaportec;
    }

    public void setPasaportec(String pasaportec) {
         this.pasaportec = null != pasaportec ? pasaportec.toUpperCase() : null;
    }

    public String getPasaportec2() {
        return pasaportec2;
    }

    public void setPasaportec2(String pasaportec2) {
         this.pasaportec2 = null != pasaportec2 ? pasaportec2.toUpperCase() : null;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public List<String> getPantallas() {
        return pantallas;
    }

    public void setPantallas(List<String> pantallas) {
        this.pantallas = pantallas;
    }

   public void validarInformacion() {
      UsuariosControl pc = new UsuariosControl();
       if(clave.equals(pasaporte)){
       msg.warn("Error al ingresar", "Usuario y Password iguales, favor de cambiarlo");
       }
       else{
       usuario=pc.existePersona(clave,pasaporte);
       System.out.println("USUARIO OBTENIDO DEL LOGIN:  "+usuario.getNoUsuario());
      if (clave != null &&pasaporte !=null&& usuario.getNoUsuario()>0) {
          setNoUsuario(usuario.getNoUsuario());
          pantallas= pc.existePermisoPantalla(usuario.getNoUsuario());
          setNombreCompleto(usuario.getNombre()+" "+usuario.getApellidoPaterno()+" "+usuario.getApellidoMaterno());
          rolbotones = accdao.getPermisos(usuario.getNoUsuario());
         msg.info("Ingreso satisfactorio", "BIENVENIDO: "+usuario.getNombre());
         Dir.redireccionar("/menu.xhtml");
      } else {
         msg.warn("Error al ingresar", "Usuario o Contraseña Invalidas");
      }
       }
   }
   
   public void CargaVentanaInserta(String ob){
    
        RequestContext context = RequestContext.getCurrentInstance();
        
           clavec="";
           pasaportec="";
           pasaportec2="";
            PrimeFaces.current().ajax().update(":formPasaporte");
            PrimeFaces.current().executeScript("PF('dlgpasaporte').show();");
    }
public void guardarCambio() {
  UsuariosControl pc = new UsuariosControl();
       System.out.println("valor de clave: "+clavec);
       System.out.println("valor de pasaporte: "+pasaportec);
       System.out.println("valor de pasaporte: "+pasaportec2);
       usuario=pc.existePersona(clavec,pasaportec);
       if(usuario.getNoUsuario()>0){
       if(pasaportec.equals(pasaportec2)){
       msg.warn("Error...", "Los password son iguales, favor de cambiarlos");
       }else{
       pc.modificarPasaporteSP(usuario.getNoUsuario(), pasaportec2);
       PrimeFaces.current().ajax().update(":formPasaporte");
       PrimeFaces.current().executeScript("PF('dlgpasaporte').hide();");
       }
       }else{
       msg.warn("Lo sentimos...", "El usuario y password no coinciden");
       }
       
}

    public Rolbotones getRolbotones() {
        return rolbotones;
    }

    public void setRolbotones(Rolbotones rolbotones) {
        this.rolbotones = rolbotones;
    }
   
   
   public void CerrarSesion() throws IOException{
       setNoUsuario(0);
       setNombreCompleto("");
       rolbotones = new Rolbotones();
       FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
       pantallas=null;
       Dir.redireccionar("/index.xhtml");
    }
 public void CerrarSesionListener(){ 
       setNoUsuario(0);
       setNombreCompleto("");
       rolbotones = new Rolbotones();
       FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
       pantallas=null;
       Dir.redireccionar("/index.xhtml");
	     }
 
 public void ActualizarPag(){ 
       Dir.redireccionar("/index.xhtml");
	     }
 
 public void validaSesion(String obj){
    
       RequestContext context = RequestContext.getCurrentInstance();
        if(noUsuario ==0){
       setNoUsuario(0);
       setNombreCompleto("");
       rolbotones = new Rolbotones();
       FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
       pantallas=null;
       Dir.redireccionar("/index.xhtml");
        }
        else if(noUsuario!=0){
            if(!obj.equals(" ")){
            UsuariosControl pc = new UsuariosControl();
            boolean existe = false;
                int sim=0;
                if(!pantallas.isEmpty()){
                for(int i=0; i < pantallas.size() ; i++){
                  if(pantallas.get(i).equals(obj)){
                  sim=sim+1;
                  }
                }
                }
            existe = sim > 0;
            System.out.println("acceso.AccesoControl.validaSesion()"+obj+":"+existe);
            if(!existe){
            Dir.redireccionar("/menu.xhtml");
            }
            }
        }
    }
}
