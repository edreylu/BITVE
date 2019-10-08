/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roles;

import acceso.Rolbotones;
import formas.FormasMenu;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;


public class RolesDAO extends conexion.ConexionOracle implements java.io.Serializable {

  
    public Rolbotones getPermisos(int r) {
        Rolbotones rolbotones = null;
        ResultSet rs = null;
        final String query = "SELECT  NO_ROL, DESCRIPCION, INSERTAR, ACTUALIZAR, ELIMINAR, CONSULTAR, DESCARGAR \n" +
                "      FROM ROLES where NO_ROL = ? AND estatus='A' order by 1 ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{r}).executeQuery();
            while (rs.next()) {
                rolbotones = new Rolbotones();
                rolbotones.setIdRol(rs.getLong("NO_ROL"));
                rolbotones.setNombre(rs.getString("DESCRIPCION"));
                rolbotones.setInsertar(rs.getString("INSERTAR"));
                rolbotones.setActualizar(rs.getString("ACTUALIZAR"));
                rolbotones.setEliminar(rs.getString("ELIMINAR"));
                rolbotones.setConsultar(rs.getString("CONSULTAR"));
            }
        } catch (SQLException e) {
            System.out.println("Error en getPermisos() " + e.getMessage());

            rolbotones = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en getPermisos() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return rolbotones;
    }

    public boolean existeRolFormas(int clave) {
        boolean existe = false;
        ResultSet rs = null;
        final String query = "  SELECT COUNT(1) "
                + "  FROM ROLES_FORMAS_MENU "
                + "  WHERE NO_ROL =  ?   ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{clave}).executeQuery();
            if (rs.next()) {
                existe = (rs.getInt(1) >= 1);
            }
            System.err.println("valor de la consulta: " + existe);
        } catch (SQLException e) {
            System.out.println("Error en existeRol() " + e.getMessage());
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
        return existe;
    }
    public boolean existeRolUsuario(int clave) {
        boolean existe = false;
        ResultSet rs = null;
        final String query = "  SELECT COUNT(1) "
                + "  FROM ROLES_USUARIOS "
                + "  WHERE NO_ROL =  ?   ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{clave}).executeQuery();
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
                System.out.println("Error en existePersona() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return existe;
    }
    
    public int insertaRol(Roles rol) {
        System.out.println("Entro a insertar ");
        System.out.println("no_rol: " + rol.getNoRol());
        int valor=0;
        ResultSet rs = null;
        final String query = "INSERT INTO ROLES (no_rol, descripcion, consultar, insertar, actualizar, eliminar, descargar, estatus ) "
                + "VALUES (?,?,?,?,?,?,?,'A')";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{
                rol.getNoRol(),
                rol.getDescripcion(),
                rol.getConsultar(),
                rol.getInsertar(),
                rol.getActualizar(),
                rol.getEliminar(),
                rol.getDescargar()}
            ).executeQuery();
        } catch (SQLException e) {
            System.out.println("Error en insertaRol() " + e.getMessage());
        } finally {
            try {
                if (null != rs) {
                    valor=1;
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en insertaRol() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return valor;
    }
    
    public int actualizaRol(Roles rol) {
        int valor=0;
        ResultSet rs = null;
        //final String query = "";
        final String query = "UPDATE ROLES set descripcion = ? , insertar =  ? , actualizar = ? , eliminar = ? , consultar = ?, descargar = ?  "
                + " where no_rol = ? ";

        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{rol.getDescripcion(),rol.getInsertar(), rol.getActualizar(), rol.getEliminar(), rol.getConsultar(),rol.getDescargar(), rol.getNoRol()}).executeQuery();
            
        } catch (SQLException e) {
            System.out.println("Error en actualiza() " + e.getMessage());
        } finally {
            try {
                if (null != rs) {
                    valor=1;
                    rs.close();
                }
                super.cerrarConexion();
            } catch (SQLException e) {
                System.out.println("Error en actualiza() " + e.getMessage());
            }
        }
        return valor;
    }

    public int eliminaRol(Roles rol) {
        int valor=0;
        System.out.println("Entro a eliminar valor: " + rol.getNoRol());
        ResultSet rs = null;
        final String query = "DELETE FROM ROLES WHERE no_rol = ? ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{rol.getNoRol()}).executeQuery();
            
        } catch (SQLException e) {
            System.out.println("Error en eliminarol() " + e.getMessage());
        } finally {
            try {
                if (null != rs) {
                    valor=1;
                    rs.close();
                }
            } catch (SQLException e) {
            }
            super.cerrarConexion();
        }
        return valor;
    }

    
    
    
     public void eliminaFormaMenu(Roles rol) {

        System.out.println("Entro a eliminar valor: " + rol.getNoRol());
        ResultSet rs = null;
        final String query = "DELETE FROM ROLES_FORMAS_MENU WHERE no_rol = ? ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{rol.getNoRol()}).executeQuery();
            

        } catch (SQLException e) {
            System.out.println("Error en eliminarol() " + e.getMessage());
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en eliminarol() " + e.getMessage());
                
            }
            super.cerrarConexion();
        }
    }
    
     public int asignaFormaMenu(int rol,List<FormasMenu> menus) {
        System.out.println("Entro a insertar ");
        System.out.println("no_rol: " + rol);
        ResultSet rs = null;
        int valor=0;
        String query = "";
        try {
            super.abrirConexion();
            for( Iterator it = menus.iterator(); it.hasNext(); ) {
	    FormasMenu x = (FormasMenu)it.next();
            
            if(x.isMenuSeleccionado()){
            query = "INSERT INTO ROLES_FORMAS_MENU (no_rol, no_forma, prioridad, estatus) "
                + "VALUES ("+rol+","+x.getNoFormaMenu()+",0,'A')";
            super.getPreparedStatement(query).executeQuery();
            query="";
            valor=valor+1;
            }
	}
            
            
        } catch (SQLException e) {
            System.out.println("Error en insertaPersona() " + e.getMessage());
            valor=-1;
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en insertaPersona() " + e.getMessage());
                valor=-1;
            }
            super.cerrarConexion();
        }
        return valor;
    }

 

    public int getNoRolSiguiente() {
        int noRol = 0;
        ResultSet rs = null;
        final String query = "select nvl(max(no_rol),0)+ 1 from ROLES ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query).executeQuery();
            if (rs.next()) {
                noRol = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error en getNoRolSiguiente() " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en getNoRolSiguiente() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return noRol;
    }


    public List<Roles> traeRegistros() {
        List<Roles> listaRoles;
        System.out.println("Entro a cargar la lista de registros roles");
        Roles rol = null;
        ResultSet rs = null;
        String query = null;
        query = "SELECT  NO_ROL, DESCRIPCION, INSERTAR, ACTUALIZAR, ELIMINAR, CONSULTAR, DESCARGAR \n" +
                "      FROM ROLES order by 1 ";

        try {
            listaRoles = new java.util.ArrayList<>();
            super.abrirConexion();
            rs = super.getPreparedStatement(query).executeQuery();

            while (rs.next()) {
                rol = new Roles();
                rol.setNoRol(rs.getInt("NO_ROL"));
                rol.setDescripcion(rs.getString("DESCRIPCION"));
                rol.setInsertar(rs.getString("INSERTAR"));
                rol.setActualizar(rs.getString("ACTUALIZAR"));
                rol.setEliminar(rs.getString("ELIMINAR"));
                rol.setConsultar(rs.getString("CONSULTAR"));
                rol.setInsertarSel(Boolean.FALSE);
                rol.setActualizarSel(Boolean.FALSE);
                rol.setEliminarSel(Boolean.FALSE);
                rol.setConsultarSel(Boolean.FALSE);
                if (rs.getString("INSERTAR").equals("S")) {
                    rol.setInsertarSel(Boolean.TRUE);
                }
                if (rs.getString("ACTUALIZAR").equals("S")) {
                    rol.setActualizarSel(Boolean.TRUE);
                }
                if (rs.getString("ELIMINAR").equals("S")) {
                    rol.setEliminarSel(Boolean.TRUE);
                }
                if (rs.getString("CONSULTAR").equals("S")) {
                    rol.setConsultarSel(Boolean.TRUE);
                }
                listaRoles.add(rol);

            }
            System.out.println("listaRoles.size() = " + listaRoles.size());
        } catch (SQLException e) {
            System.out.println("Error en listaRoles1() " + e.getMessage());
            listaRoles = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en listaRoles2() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return listaRoles;
    }
   

    public List<FormasMenu> traeRegistrosM(Roles rol) {
        List<FormasMenu> listaMenus;
        System.out.println("Entro a cargar la lista de registros permisos menu, el rol es:"+rol.getNoRol());
        FormasMenu menu = null;
        ResultSet rs = null;
        String query = null;
        query= "SELECT  ME.NO_FORMA AS ID_MENU , ME.TITULO AS DESCRIPCION, ME.URL AS ENLACE, ME.ICONO AS ICONO, ME.NO_FORMA_PADRE AS ID_MENU_PADRE,\n" +
"                        (SELECT PER.NO_FORMA FROM ROLES_FORMAS_MENU PER WHERE PER.NO_FORMA = ME.NO_FORMA AND PER.NO_ROL= ? ) ROL\n" +
"                FROM FORMAS_MENU ME  \n" +
"               WHERE (ME.NO_FORMA_PADRE IS NOT NULL and ME.NO_FORMA_PADRE <>0)\n" +
"               \n" +
"                ORDER BY 1";
System.out.println(query);
        try {
            listaMenus = new java.util.ArrayList<>();
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{
                rol.getNoRol()}).executeQuery();

            while (rs.next()) {
                menu = new FormasMenu();
                menu.setNoFormaMenu(rs.getShort("ID_MENU"));
                menu.setTitulo(rs.getString("DESCRIPCION"));
                menu.setUrl(rs.getString("ENLACE"));
                menu.setIcono(rs.getString("ICONO"));
                menu.setNoFormaPadre(rs.getShort("ID_MENU_PADRE"));
                menu.setMenuSeleccionado(rs.getBoolean("ROL"));
                listaMenus.add(menu);
                
                
            }
            System.out.println("listaRoles.size() = " + listaMenus.size());
        } catch (SQLException e) {
            System.out.println("Error en listaRoles1() " + e.getMessage());
            listaMenus = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en listaRoles2() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return listaMenus;
    }
    
  



}
