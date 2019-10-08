/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class FormasMenuDAO extends conexion.ConexionOracle implements java.io.Serializable {

    
    public boolean existeHijos(int clave) {
        boolean existe = false;
        ResultSet rs = null;
        final String query = "  SELECT COUNT(1) "
                + "  FROM FORMAS_MENU "
                + "  WHERE NO_FORMA_PADRE =  ?   ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{clave}).executeQuery();
            if (rs.next()) {
                existe = (rs.getInt(1) >= 1);
            }
            System.err.println("valor de existeHijos: " + existe);
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
        return existe;
    }
    
    public boolean existeFormaRol(int clave) {
        boolean existe = false;
        ResultSet rs = null;
        final String query = "  SELECT COUNT(1) "
                + "  FROM ROLES_FORMAS_MENU "
                + "  WHERE NO_FORMA =  ?   ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{clave}).executeQuery();
            if (rs.next()) {
                existe = (rs.getInt(1) >= 1);
            }
            System.err.println("valor de la consulta: " + existe);
        } catch (SQLException e) {
            System.out.println("Error en existeFormaRol() " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en existeFormaRol() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return existe;
    }
    
    public int insertaForma(FormasMenu menu) {
        System.out.println("Entro a insertar ");
        int valor=0;
        ResultSet rs = null;
        int tipoForma=menu.getNoFormaPadre()>0?3:1;
        System.out.println("insertaForma " + tipoForma);
        final String query = "INSERT INTO FORMAS_MENU (NO_FORMA, TITULO ,URL ,ICONO ,ESTATUS,NO_TIPO_FORMA,NO_FORMA_PADRE ) "
                + "VALUES (?,?,?,?,'A',?,?)";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{
                menu.getNoFormaMenu(),
                menu.getTitulo(),
                menu.getUrl(),
                menu.getIcono(),
                tipoForma,
                menu.getNoFormaPadre()
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
                System.out.println("Error en insertapantalla() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        System.out.println("valor: " + valor);
        return valor;
    }
    
    public int actualizaForma(FormasMenu menu) {
        int valor=0;
        ResultSet rs = null;
        int tipoForma=menu.getNoFormaPadre()>0?3:1;
        final String query = "UPDATE FORMAS_MENU set TITULO = ? ,URL = ? ,ICONO = ? ,NO_TIPO_FORMA = ? , NO_FORMA_PADRE = ? "
                + " where NO_FORMA = ? ";

        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{menu.getTitulo(),
                menu.getUrl(),
                menu.getIcono(),
                tipoForma,
                menu.getNoFormaPadre(),
                menu.getNoFormaMenu()}).executeQuery();
            
            
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

    public int eliminaForma(FormasMenu menu) {
        int valor=0;
        ResultSet rs = null;
        final String query = "DELETE FROM FORMAS_MENU WHERE NO_FORMA = ? ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{menu.getNoFormaMenu()}).executeQuery();
            

        } catch (SQLException e) {
            System.out.println("Error en eliminapantalla() " + e.getMessage());
        } finally {
            try {
                if (null != rs) {
                    valor=1;
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en eliminapantalla() " + e.getMessage());
                
            }
            super.cerrarConexion();
        }
        return valor;
    }

    
    

    public Short getNoMenuSiguiente() {
        Short noRol = null;
        ResultSet rs = null;
        final String query = "select nvl(max(no_forma),0)+ 1 from formas_menu ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query).executeQuery();
            if (rs.next()) {
                noRol = rs.getShort(1);
            }
        } catch (SQLException e) {
            System.out.println("Error en getNomenuSiguiente() " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en getNomenuSiguiente() " + e.getMessage());
            }
        super.cerrarConexion();
        }
        return noRol;
    }

    public List<FormasMenu> traeRegistros() {
        List<FormasMenu> listaMenus;
        System.out.println("Entro a cargar la lista de registros pantallas");
        FormasMenu menu = null;
        ResultSet rs = null;
        String query = null;
        query = "SELECT ME.NO_FORMA as ID_MENU,ME.TITULO as DESCRIPCION,ME.URL as ENLACE,ME.ICONO as ICONO,\n" +
"                        DECODE((SELECT ME1.NO_FORMA_PADRE  \n" +
"                                    FROM FORMAS_MENU ME1 \n" +
"                                WHERE ME1.NO_FORMA = ME.NO_FORMA_PADRE),null,'PADRE',\n" +
"                        (SELECT ME1.TITULO \n" +
"                                    FROM FORMAS_MENU ME1 \n" +
"                                    WHERE ME1.NO_FORMA = ME.NO_FORMA_PADRE)) as PADRE,\n" +
"                                    ME.NO_FORMA_PADRE AS ID_MENU_PADRE \n" +
"                             FROM FORMAS_MENU me WHERE ME.NO_FORMA<>0 order by 1";

        try {
            listaMenus = new java.util.ArrayList<>();
            super.abrirConexion();
            rs = super.getPreparedStatement(query).executeQuery();

            while (rs.next()) {
                menu = new FormasMenu();
                menu.setNoFormaMenu(rs.getInt("ID_MENU"));
                menu.setTitulo(rs.getString("DESCRIPCION"));
                menu.setUrl(rs.getString("ENLACE"));
                menu.setIcono(rs.getString("ICONO"));
                menu.setNombrePapa(rs.getString("PADRE"));
                menu.setNoFormaPadre(rs.getShort("ID_MENU_PADRE"));
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
    
    public List<FormasMenu> traeRegistrosp() {
        List<FormasMenu> listaMenus;
        System.out.println("Entro a cargar la lista de registros pantallas padre");
        FormasMenu menu = null;
        ResultSet rs = null;
        String query = null;
        query = "SELECT NO_FORMA, TITULO\n" +
"              FROM FORMAS_MENU            \n" +
"              WHERE (NO_FORMA_PADRE IS NULL OR NO_FORMA_PADRE =0)\n" +
"              AND NO_TIPO_FORMA=1 AND NO_FORMA<>0 order by 1";

        try {
            listaMenus = new java.util.ArrayList<>();
            super.abrirConexion();
            rs = super.getPreparedStatement(query).executeQuery();

            while (rs.next()) {
                menu = new FormasMenu();
                menu.setNoFormaMenu(rs.getShort("NO_FORMA"));
                menu.setTitulo(rs.getString("TITULO"));
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
   
    public List<FormasMenu> traeRegistrospCOM(int noForma) {
        List<FormasMenu> listaMenus;
        System.out.println("Entro a cargar la lista de registros pantallas padre 2");
        FormasMenu menu = null;
        ResultSet rs = null;
        String query = null;
        query = "SELECT NO_FORMA, TITULO\n" +
"              FROM FORMAS_MENU            \n" +
"              WHERE (NO_FORMA_PADRE IS NULL OR NO_FORMA_PADRE =0)\n" +
"              AND NO_TIPO_FORMA=1 AND NO_FORMA <> "+noForma+" AND NO_FORMA <>0 order by 1";
System.out.println(query);
        try {
            listaMenus = new java.util.ArrayList<>();
            super.abrirConexion();
            rs = super.getPreparedStatement(query).executeQuery();

            while (rs.next()) {
                menu = new FormasMenu();
                menu.setNoFormaMenu(rs.getShort("NO_FORMA"));
                menu.setTitulo(rs.getString("TITULO"));
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
public List<FormasMenu> obtenerMenu(long noUsuario) {
        System.out.println("Entro a cargar el menu");
        ResultSet rSet = null;
        FormasMenu menu = null;
        List<FormasMenu> listaMenu = new ArrayList<FormasMenu>();
        try {
            String query = "SELECT ME.TITULO, \n" +
"                           ME.ICONO, \n" +
"                           ME.URL, \n" +
"                           (SELECT me2.TITULO \n" +
"                              FROM FORMAS_MENU ME2 \n" +
"                             WHERE ME.NO_FORMA_PADRE = ME2.NO_FORMA) nombre_papa\n" +
"                     from USUARIOS ua, \n" +
"                          ROLES ro, \n" +
"                          FORMAS_MENU ME,\n" +
"                          ROLES_USUARIOS RU,\n" +
"                          ROLES_FORMAS_MENU rfm\n" +
"                      WHERE UA.NO_USUARIO =  RU.NO_USUARIO\n" +
"                      AND   RU.NO_ROL = RO.NO_ROL\n" +
"                      AND   ME.NO_FORMA = RFM.NO_FORMA\n" +
"                      AND   RO.NO_ROL   = RFM.NO_ROL\n" +
"                      AND   RU.ESTATUS   = 'A'\n" +
"                      AND   UA.ESTATUS   = 'A'\n" +                    
"                      and   RU.NO_USUARIO =" +noUsuario +
"                      AND   NVL(ME.NO_FORMA_PADRE,0) > 0 \n" +
"                      order by 4, 1";

            System.out.println(query);
            
           super.abrirConexion();
           rSet = super.getPreparedStatement(query).executeQuery();
         

            while (rSet.next()) {
                menu = new FormasMenu();
                menu.setTitulo(rSet.getString(1));
                menu.setIcono(rSet.getString(2));
                menu.setUrl(rSet.getString(3));
                menu.setNombrePapa(rSet.getString(4));
                listaMenu.add(menu);
            }
            System.out.println("listaMenu.size() = " + listaMenu.size());
        } catch (SQLException e) {
            System.out.println("Error al cargar el menu " + e.getMessage());
        }finally{
           super.cerrarConexion();
        }

        return listaMenu;

    }
}
