
package usuarios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ur.Ur;


public class UsuariosDAO extends conexion.ConexionOracle implements java.io.Serializable {

 
   public Usuarios existePersona(String clave, String pas) {
       Usuarios usuario=null;
      ResultSet rs = null;
      final String query = "  SELECT NO_USUARIO, NOMBRE,APELLIDO_PATERNO,APELLIDO_MATERNO "
              + "  FROM USUARIOS "
              + " WHERE CLAVE = ? "
              + "  AND  ENC.DESENCRIP(PASAPORTE) =  ?  AND ESTATUS = 'A' ";
      try {
          //System.out.println(query);
         super.abrirConexion();
         rs = super.getPreparedStatement(query, new Object[]{clave,pas}).executeQuery();
         usuario=new Usuarios();
         if (rs.next()) {
            usuario.setNoUsuario(rs.getInt(1));
            usuario.setNombre(rs.getString(2));
            usuario.setApellidoPaterno(rs.getString(3));
            usuario.setApellidoMaterno(rs.getString(4));
         }
          System.err.println("valor de la consulta: "+usuario.getNoUsuario());
      } catch (SQLException e) {
         System.out.println("Error en existePersona() " + e.getMessage());
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException e) {
            System.out.println("Error en existePersona() " + e.getMessage());
         }
         super.cerrarConexion();
      }
      return usuario;
   }
   
   public ArrayList<String> getPermisoPantalla(int noPersonal) {
      ArrayList<String> pantalla=new ArrayList<>();
      ResultSet rs = null;
      final String query = "  select fm.url \n" +
            "from formas_menu fm,\n" +
            "     roles_formas_menu rfm,\n" +
            "     roles_usuarios ru\n" +
            "     where rfm.no_forma = fm.no_forma\n" +
            "     and ru.no_rol = rfm.no_rol\n" +
            "     and ru.no_usuario= ? and ru.estatus='A' ";
      try {
         super.abrirConexion();
         rs = super.getPreparedStatement(query, new Object[]{noPersonal}).executeQuery();
         while(rs.next()) {
            pantalla.add(rs.getString(1));
         }
      } catch (SQLException e) {
         System.out.println("Error en getPermisoPantalla() " + e.getMessage());
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException e) {
            System.out.println("Error en getPermisoPantalla() " + e.getMessage());
         }
         super.cerrarConexion();
      }
      return pantalla;
   }
   
   public Long existePersonaSP(String clave) {
       long existe=0;
      ResultSet rs = null;
      final String query = "  SELECT NO_USUARIO "
              + "  FROM USUARIOS "
              + " WHERE CLAVE = ? ";
      try {
         super.abrirConexion();
         rs = super.getPreparedStatement(query, new Object[]{clave}).executeQuery();
         if (rs.next()) {
            existe = (rs.getLong(1));
         }
          System.err.println("valor de la consulta: "+existe);
      } catch (SQLException e) {
         System.out.println("Error en existePersonaSP() " + e.getMessage());
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException e) {
            System.out.println("Error en existePersonaSP() " + e.getMessage());
         }
         super.cerrarConexion();
      }
      return existe;
   }
   public boolean existeRolUsuario(int noUsuario) {
        boolean existe = false;
        ResultSet rs = null;
        final String query = "  SELECT COUNT(1) "
                + "  FROM ROLES_USUARIOS "
                + "  WHERE NO_USUARIO =  ? AND ESTATUS = 'A'  ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{noUsuario}).executeQuery();
            if (rs.next()) {
                existe = (rs.getInt(1) >= 1);
            }
            System.err.println("valor de la consulta: " + existe);
        } catch (SQLException e) {
            System.out.println("Error en existeRolUsuario() " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en existeRolUsuario() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return existe;
    }
   
   public boolean existeUrUsuario(int clave) {
        boolean existe = false;
        ResultSet rs = null;
        final String query = "  SELECT COUNT(1) "
                + "  FROM URUSUARIO "
                + "  WHERE NOUSUARIO =  ?   ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{clave}).executeQuery();
            if (rs.next()) {
                existe = (rs.getInt(1) >= 1);
            }
            System.err.println("valor de la consulta: " + existe);
        } catch (SQLException e) {
            System.out.println("Error en existeUrUsuario() " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en existeUrUsuario() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return existe;
    }
   
   public boolean existeRolUsuarioAsignar(int noUsuario, int noRol) {
        boolean existe = false;
        ResultSet rs = null;
        final String query = "  SELECT COUNT(1) "
                + "  FROM ROLES_USUARIOS "
                + "  WHERE NO_USUARIO =  ?  AND NO_ROL = ? AND ESTATUS = 'I'";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{noUsuario,noRol}).executeQuery();
            if (rs.next()) {
                existe = (rs.getInt(1) >= 1);
            }
            System.err.println("valor de la consulta: " + existe);
        } catch (SQLException e) {
            System.out.println("Error en existeRolUsuarioAsignar() " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en existeRolUsuarioAsignar() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return existe;
    }
   
   public int insertaUsuario(Usuarios us) {
        System.out.println("Entro a insertar ");
        System.out.println("no_usuario: " +us.getNoUsuario());
      ResultSet rs = null;
      int valor=0;
      final String query = "INSERT INTO USUARIOS (NO_USUARIO, CLAVE, PASAPORTE, NOMBRE,APELLIDO_PATERNO,APELLIDO_MATERNO, "
              + "   CORREO, TELEFONO, ESTATUS, FECHA_AUDITORIA) "
                             + "VALUES (?,?,ENC.ENCRIP(?),?,?,?,?,?,'A',SYSDATE)";
      try {
          super.abrirConexion();
          rs = super.getPreparedStatement
        (query, new Object[]{
            us.getNoUsuario(),
            us.getClave(),
            us.getPasaporte(),
            us.getNombre(),
            us.getApellidoPaterno(),
            us.getApellidoMaterno(),
            us.getCorreo(),
            us.getTelefono()
            }
        ).executeQuery();
      } catch (SQLException e) {
         System.out.println("Error en insertaPersona() " + e.getMessage());
      } finally {
         try {
            if (null != rs) {
                valor=1;
               rs.close();
            }
         } catch (SQLException e) {
            System.out.println("Error en insertaPersona() " + e.getMessage());
         }
         super.cerrarConexion();
      }
      return valor;
   }

   public int actualizaUsuario(Usuarios us) {
       System.out.println("user: " +us.getNoUsuario());
       System.out.println("pasaporte: " +us.getPasaporte());
       System.out.println("no_usuario: " +us.getNoUsuario());
       
       
      ResultSet rs = null;
      int valor=0;
      final String query = "UPDATE USUARIOS set CLAVE = ?, NOMBRE = ? ,"
              + " APELLIDO_PATERNO = ? ,APELLIDO_MATERNO = ? ,CORREO = ? , TELEFONO = ? "
             + " where NO_USUARIO = ? ";
       
      try {
         super.abrirConexion();
         rs = super.getPreparedStatement(query, new Object[]{us.getClave(),
                                                             us.getNombre(),
                                                             us.getApellidoPaterno(),
                                                             us.getApellidoMaterno(),
                                                             us.getCorreo(),
                                                             us.getTelefono(),
                                                             us.getNoUsuario()}).executeQuery();
         
         
      } catch (SQLException e) {
         System.out.println("Error en actualiza() " + e.getMessage());
      } finally {
         try {
            if (null != rs) {
                valor=1;
               rs.close();
            }
            
         } catch (SQLException e) {
            System.out.println("Error en actualiza() " + e.getMessage());
         }
         super.cerrarConexion();
      }
      return valor;
   }
   
    public int actualizaPasaporte(int us,String pas) {
       System.out.println("user: " +us);
       System.out.println("pasaporte: " +pas);
       
       
      ResultSet rs = null;
      int valor=0;
      final String query = "UPDATE USUARIOS set PASAPORTE =  ENC.ENCRIP(?) "
             + " where NO_USUARIO = ? ";
      
      try {
         super.abrirConexion();
         rs = super.getPreparedStatement(query, new Object[]{pas,us}).executeQuery();
         
         
      } catch (SQLException e) {
         System.out.println("Error en actualiza() " + e.getMessage());
      } finally {
         try {
            if (null != rs) {
                valor=1;
               rs.close();
            }
         } catch (SQLException e) {
            System.out.println("Error en actualiza() " + e.getMessage());
         }
         super.cerrarConexion();
      }
      return valor;
   }
   
    public int eliminaUsuario(Usuarios us) {
       System.out.println("clave: " +us.getClave());
       System.out.println("no_usuario: " +us.getNoUsuario());
       System.out.println("Entro a eliminar valor: "+us.getNoUsuario());
      ResultSet rs = null;
      int valor=0;
      final String query = "UPDATE USUARIOS set ESTATUS = 'I' where no_usuario = ? ";
      try {
          super.abrirConexion();
          rs = super.getPreparedStatement(query, new Object[]{us.getNoUsuario()}).executeQuery();
         
         
      } catch (SQLException e) {
         System.out.println("Error en eliminaPersona() " + e.getMessage());
      } finally {
         try {
            if (null != rs) {
                valor=1;
               rs.close();
            }
         } catch (SQLException e) {
            System.out.println("Error en eliminaPersona() " + e.getMessage());
           
         }
         super.cerrarConexion();
      }
      return valor;
      }
    
    public void eliminaRolUsuario(int noUsuario) {

        System.out.println("Entro a eliminar valor: " + noUsuario);
        ResultSet rs = null;
        final String query = "UPDATE ROLES_USUARIOS set ESTATUS = 'I' WHERE no_usuario = ? ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{noUsuario}).executeQuery();
            

        } catch (SQLException e) {
            System.out.println("Error en eliminaRolUsuario() " + e.getMessage());
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en eliminaRolUsuario() " + e.getMessage());
                
            }
            super.cerrarConexion();
        }
    }
    
    public void eliminaUrUsuario(Usuarios us) {

        System.out.println("Entro a eliminar valor: " + us.getNoUsuario());
        ResultSet rs = null;
        final String query = "DELETE FROM URUSUARIO WHERE NOUSUARIO = ? ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{us.getNoUsuario()}).executeQuery();
            

        } catch (SQLException e) {
            System.out.println("Error en eliminaUrUsuario() " + e.getMessage());
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en eliminaUrUsuario() " + e.getMessage());
                
            }
        super.cerrarConexion();
        }
    }
    
   public int asignaRolUsuario(int usuario,int rol) {
        System.out.println("Entro a Asignar ");
        System.out.println("no_rol: " + rol + "usuario: "+usuario);
        ResultSet rs = null;
        int valor=0;
        String query =null;
        Boolean existe=existeRolUsuarioAsignar(usuario, rol);
        if(existe){
                query = "UPDATE ROLES_USUARIOS SET ESTATUS = 'A' WHERE no_usuario = ? AND no_rol = ?";
            }
            else{
            query = "INSERT INTO ROLES_USUARIOS (no_usuario, no_rol, estatus) "
                + "VALUES (?,?,'A')";
            }
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{usuario,rol}).executeQuery();
        }catch (SQLException e) {
            System.out.println("Error en asignaUsuarioRol() " + e.getMessage());
            
        } finally {
            try {
                if (null != rs) {
                    valor=1;
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en asignaUsuarioRol() " + e.getMessage());
                
            }
            super.cerrarConexion();
        }
        return valor;
    }
   
   public int asignaUrUsuario(int usuario, List<Ur> urs) {
        System.out.println("Entro a Asignar ur a usuario "+usuario);
        ResultSet rs = null;
        int valor=0;
        String query="";
        try {
            super.abrirConexion();
            for( Iterator it = urs.iterator(); it.hasNext(); ) {
	    Ur x = (Ur)it.next();
            
            if(x.isUrSeleccionada()){
            query="INSERT INTO URUSUARIO (NOUR, NOUSUARIO) VALUES ("+x.getNoUr()+","+usuario+") ";
            super.getPreparedStatement(query).executeQuery();
            query="";
            valor=valor+1;
            }
	}
            
            
        } catch (SQLException e) {
            System.out.println("Error en asignaUrUsuario() " + e.getMessage());
            valor=-1;
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en asignaUrUsuario() " + e.getMessage());
                valor=-1;
            }
            super.cerrarConexion();
        }
        return valor;
    }
   
     public int getNoUsuarioSiguiente() {
      int noUsuario = 0;
      ResultSet rs = null;
      final String query = "select nvl(max(no_usuario),0)+ 1 from USUARIOS";
      try {
          super.abrirConexion();
         rs = super.getPreparedStatement(query).executeQuery();
         if (rs.next()) {
            noUsuario = rs.getInt(1);
         }
      } catch (SQLException e) {
         System.out.println("Error en getNoUsuarioSiguiente() " + e.getMessage());
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException e) {
            System.out.println("Error en getNoUsuarioSiguiente() " + e.getMessage());
         }
         super.cerrarConexion();
      }
      return noUsuario;
   }
     
     
   
   public void ejecutarQuery(String query) {
       
      System.out.println("Entro a ejecutar query: " +query);
      
      ResultSet rs = null;
      try {
         super.abrirConexion();
         rs = super.getPreparedStatement(query).executeQuery();
      } catch (SQLException e) {
         System.out.println("Error en ejecutarquery() " + e.getMessage());
      } finally {
         try {
            if (null != rs) {
               rs.close();
            }
            super.cerrarConexion();
         } catch (SQLException e) {
            System.out.println("Error en ejecutarquery() " + e.getMessage());
         }
         super.cerrarConexion();
      }
   }

   
   public List<Usuarios> traeRegistros() {
    List<Usuarios> listaPersonas;
     System.out.println("Entro a cargar la lista de registros");
    Usuarios persona = null;
      ResultSet rs = null;
      String query =null;
      query = "select ua.no_usuario as no_usuario, ua.CLAVE as usuario , ua.correo as correo, ua.nombre as nombre, \n" +
"                  UA.APELLIDO_PATERNO AS APELLIDO_PATERNO, UA.APELLIDO_MATERNO AS APELLIDO_MATERNO, UA.TELEFONO AS TELEFONO, \n" +
"               (SELECT RU.NO_ROL FROM ROLES_USUARIOS RU WHERE RU.NO_USUARIO=UA.NO_USUARIO AND RU.ESTATUS='A') AS rol,\n" +
"               (SELECT RO.DESCRIPCION FROM ROLES_USUARIOS RU, ROLES RO\n" +
"               WHERE RU.NO_USUARIO=UA.NO_USUARIO\n" +
"               AND RO.NO_ROL=RU.NO_ROL\n" +
"               \n" +
"               AND RO.ESTATUS ='A' AND RU.ESTATUS='A') AS nombreRol, UA.ESTATUS \n" +
"               FROM USUARIOS UA\n" +
"                \n" +
"               order by 1";
      
System.out.println(query);
      try {
          listaPersonas = new java.util.ArrayList<>();
           super.abrirConexion();
         rs = super.getPreparedStatement(query).executeQuery();
         
         while (rs.next()) {
             
            persona = new Usuarios();
persona.setNoUsuario(rs.getInt("no_usuario"));
persona.setClave(rs.getString("usuario"));
persona.setNombre(rs.getString("nombre"));
persona.setApellidoPaterno(rs.getString("apellido_paterno"));
persona.setApellidoMaterno(rs.getString("apellido_materno"));
persona.setCorreo(rs.getString("correo"));
persona.setTelefono(rs.getString("telefono"));
persona.setNoRol(rs.getInt("rol"));
String desc="";
if(rs.getString("nombreRol")!=(null)){
desc=rs.getString("nombreRol");
}else{desc="SIN ROL";}
persona.setDescripcionRol(desc);
persona.setEstatus(rs.getString("ESTATUS"));
listaPersonas.add(persona);
             
      
         
         }
         System.out.println("listaPersona.size() = " + listaPersonas.size());
      } catch (SQLException e) {
         System.out.println("Error en listaPersona1() " + e.getMessage());
         listaPersonas = null;
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException e) {
            System.out.println("Error en listaPersona2() " + e.getMessage());
         }
         super.cerrarConexion();
      }
      return listaPersonas;
   }
   public List<Ur> traeRegistrosur(Usuarios us) {
        List<Ur> listaUr;
        System.out.println("Entro a cargar la lista de registros listaUr , el usuario es:"+us.getNoUsuario());
        Ur u = null;
        ResultSet rs = null;
        String query = null;
        query= "SELECT UR.NOUR,UR.CLAVEUR,UR.UR,(SELECT UU.NOUSUARIO FROM URUSUARIO UU WHERE UU.NOUR = UR.NOUR AND UU.NOUSUARIO= ? ) URUSUARIO \n" +
"                FROM UR UR  \n" +
"               WHERE UR.ESTATUS= 'A' \n" +
"               ORDER BY 1";
System.out.println(query);
        try {
            listaUr = new java.util.ArrayList<>();
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{
                us.getNoUsuario()}).executeQuery();

            while (rs.next()) {
                u = new Ur();
                u.setNoUr(rs.getInt("NOUR"));
                u.setClaveUr(rs.getString("CLAVEUR"));
                u.setUr(rs.getString("UR"));
                u.setUrSeleccionada(rs.getBoolean("URUSUARIO"));
                listaUr.add(u);
                
                
            }
            System.out.println("listaUr.size() = " + listaUr.size());
        } catch (SQLException e) {
            System.out.println("Error en listaUr() " + e.getMessage());
            listaUr = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en listaUr() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return listaUr;
    }
         
      public Usuarios getUsuario(String clave) {
      Usuarios usuario = null;
      ResultSet rs = null;
      
      final String query = "SELECT NO_USUARIO "
             
              + " FROM USUARIOS where CLAVE = ? ";
      try {
         super.abrirConexion();
         rs = super.getPreparedStatement(query, new Object[]{clave}).executeQuery();
         while (rs.next()) {
            usuario = new Usuarios();
            usuario.setNoUsuario(rs.getInt("NO_USUARIO"));
            
         }
      } catch (SQLException e) {
         System.out.println("Error en usuario() " + e.getMessage());
         
         usuario = null;
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }
         } catch (SQLException e) {
            System.out.println("Error en usuario() " + e.getMessage());
         }
         super.cerrarConexion();
      }
      return usuario;
   }
    
    

}
