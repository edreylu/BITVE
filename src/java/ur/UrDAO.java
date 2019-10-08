/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ur;

import catalogos.Marca;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 *
 */
public class UrDAO extends conexion.ConexionOracle implements java.io.Serializable {

    public int insertaUr(Ur ur) {
        System.out.println("Entro a insertar ");
        int valor=0;
        ResultSet rs = null;
        final String query = "INSERT INTO UR (NOUR,CLAVEUR,UR,AP1,AP2,NOMBRE,ESTATUS,NOUR_GRUPO) "
                + "VALUES (?,?,?,?,?,?,'A',?)";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{
                ur.getNoUr(),
                ur.getClaveUr(),
                ur.getUr(),
                ur.getAp1(),
                ur.getAp2(),
                ur.getNombre()
                }
            ).executeQuery();
            
            
        } catch (SQLException e) {
            System.out.println("Error en insertaTipoEvento() " + e.getMessage());
            
        } finally {
            try {
                if (null != rs) {
                    valor=1;
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en insertaTipoEvento() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        System.out.println("valor: " + valor);
        return valor;
    }
    
    public int actualizaUr(Ur ur) {
        int valor=0;
        ResultSet rs = null;
        
        final String query = "UPDATE UR set UR =? ,CLAVEUR =? ,AP1 = ? ,AP2 = ? ,NOMBRE = ? , NOUR_GRUPO = ? "
                + " where NOUR = ? ";

        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{ur.getUr(),ur.getClaveUr(),
                ur.getAp1(),ur.getAp2(),ur.getNombre(),ur.getNoUrGrupo(),ur.getNoUr(),}).executeQuery();
            
            
        } catch (SQLException e) {
            System.out.println("Error en actualizaUr() " + e.getMessage());
        } finally {
            try {
                if (null != rs) {
                    valor=1;
                    rs.close();
                }
                super.cerrarConexion();
            } catch (SQLException e) {
                System.out.println("Error en actualizaUr() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return valor;
    }

    public int eliminaUr(Ur ur) {
        int valor=0;
        ResultSet rs = null;
        final String query = "DELETE FROM UR WHERE NOUR = ? ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{ur.getNoUr()}).executeQuery();
            

        } catch (SQLException e) {
            System.out.println("Error en eliminaUr() " + e.getMessage());
        } finally {
            try {
                if (null != rs) {
                    valor=1;
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en eliminaUr() " + e.getMessage());
                
            }
            super.cerrarConexion();
        }
        return valor;
    }

    public boolean existeUrUsuario(int ur) {
        boolean existe = false;
        ResultSet rs = null;
        final String query = " select count(1) from URUSUARIO WHERE NOUR= ? ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{ur}).executeQuery();
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
    
    public boolean existeUrVehiculo(int ur) {
        boolean existe = false;
        ResultSet rs = null;
        final String query = " select count(1) from VEHICULO WHERE NOUR= ? ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{ur}).executeQuery();
            if (rs.next()) {
                existe = (rs.getInt(1) >= 1);
            }
            System.err.println("valor de la consulta: " + existe);
        } catch (SQLException e) {
            System.out.println("Error en existeUrVehiculo() " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en existeUrVehiculo() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return existe;
    }
    
    public int getNoUrSiguiente() {
        int noSiguiente = 0;
        ResultSet rs = null;
        final String query = "select nvl(max(NOUR),0)+ 1 from UR ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query).executeQuery();
            if (rs.next()) {
                noSiguiente = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error en getNoUrSiguiente() " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en getNoUrSiguiente() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return noSiguiente;
    }

    public List<Ur> traeRegistros(int noUsuario) {
        List<Ur> listaUr;
        System.out.println("Entro a cargar la lista de registros listaUr");
        Ur ur = null;
        ResultSet rs = null;
        String query = null;
        query = "SELECT UR.NOUR,UR.CLAVEUR,UR.UR,UR.AP1,UR.AP2,UR.NOMBRE,UR.ESTATUS "+
                 " FROM UR UR,"+
                 "      URUSUARIO UU"+
                 " WHERE   UR.NOUR = UU.NOUR\n" +
                 "   and   UU.NOUSUARIO = ? AND UR.ESTATUS='A' "+
                 " order by 1";

        try {
            listaUr = new java.util.ArrayList<>();
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{noUsuario}).executeQuery();

            while (rs.next()) {
                ur = new Ur();
                ur.setNoUr(rs.getInt(1));
                ur.setClaveUr(rs.getString(2));
                ur.setUr(rs.getString(3));
                ur.setAp1(rs.getString(4));
                ur.setAp2(rs.getString(5));
                ur.setNombre(rs.getString(6));
                listaUr.add(ur);

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
    
    public List<Ur> traeRegistrosC() {
        List<Ur> listaUr;
        System.out.println("Entro a cargar la lista de registros listaUrC");
        Ur ur = null;
        ResultSet rs = null;
        String query = null;
        query = "SELECT UR.NOUR,UR.CLAVEUR,UR.UR,UR.AP1,UR.AP2,UR.NOMBRE,UR.ESTATUS,UR.NOUR_GRUPO,"
                + "(SELECT UR2.CLAVEUR FROM UR UR2 WHERE UR2.NOUR=UR.NOUR_GRUPO) NOUR_GRUPO_DESC FROM UR UR order by 1";

        try {
            listaUr = new java.util.ArrayList<>();
            super.abrirConexion();
            rs = super.getPreparedStatement(query).executeQuery();

            while (rs.next()) {
                ur = new Ur();
                ur.setNoUr(rs.getInt(1));
                ur.setClaveUr(rs.getString(2));
                ur.setUr(rs.getString(3));
                ur.setAp1(rs.getString(4));
                ur.setAp2(rs.getString(5));
                ur.setNombre(rs.getString(6));
                ur.setEstatus(rs.getString(7));
                ur.setNoUrGrupo(rs.getInt(8));
                ur.setClaveUrGrupo(rs.getString(9));
                listaUr.add(ur);

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
    
    public List<Ur> traeRegistrosUrGrupo(int noUr) {
        List<Ur> listaUr;
        System.out.println("Entro a cargar la lista de registros listaUrGrupo");
        Ur ur = null;
        ResultSet rs = null;
        String query = null;
        query = "SELECT NOUR,CLAVEUR,UR,AP1,AP2,NOMBRE,ESTATUS FROM UR UR where UR.NOUR != ? order by 1";

        try {
            listaUr = new java.util.ArrayList<>();
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{noUr}).executeQuery();

            while (rs.next()) {
                ur = new Ur();
                ur.setNoUr(rs.getInt(1));
                ur.setClaveUr(rs.getString(2));
                ur.setUr(rs.getString(3));
                ur.setAp1(rs.getString(4));
                ur.setAp2(rs.getString(5));
                ur.setNombre(rs.getString(6));
                listaUr.add(ur);

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
    
    
    
    
}
