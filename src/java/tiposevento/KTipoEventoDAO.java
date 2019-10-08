/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiposevento;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 *
 */
public class KTipoEventoDAO extends conexion.ConexionOracle implements java.io.Serializable {

    public int insertaTipoEvento(KTipoEvento te) {
        System.out.println("Entro a insertar ");
        int valor=0;
        ResultSet rs = null;
        final String query = "INSERT INTO KTIPOEVENTO (NOTIPOEVENTO, TIPOEVENTO,OBSERVACIONES, ESTATUS ) "
                + "VALUES (?,?,?,'A')";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{
                te.getNoTipoEvento(),
                te.getTipoEvento(),
                te.getObservaciones()
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
    
    public int actualizaTipoEvento(KTipoEvento te) {
        int valor=0;
        ResultSet rs = null;
        
        final String query = "UPDATE KTIPOEVENTO set TIPOEVENTO = ?, OBSERVACIONES = ? "
                + " where NOTIPOEVENTO = ? ";

        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{te.getTipoEvento(),te.getObservaciones(),
                te.getNoTipoEvento(),}).executeQuery();
            
            
        } catch (SQLException e) {
            System.out.println("Error en actualizaTipoEvento() " + e.getMessage());
        } finally {
            try {
                if (null != rs) {
                    valor=1;
                    rs.close();
                }
                super.cerrarConexion();
            } catch (SQLException e) {
                System.out.println("Error en actualizaTipoEvento() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return valor;
    }

    public int eliminaTipoEvento(KTipoEvento te) {
        int valor=0;
        ResultSet rs = null;
        final String query = "UPDATE KTIPOEVENTO SET estatus='I' WHERE NOTIPOEVENTO = ? ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{te.getNoTipoEvento()}).executeQuery();
            

        } catch (SQLException e) {
            System.out.println("Error en eliminaTipoEvento() " + e.getMessage());
        } finally {
            try {
                if (null != rs) {
                    valor=1;
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en eliminaTipoEvento() " + e.getMessage());
                
            }
            super.cerrarConexion();
        }
        return valor;
    }

    public boolean existeTipoEventoEvento(int noTipoEvento) {
        boolean existe = false;
        ResultSet rs = null;
        final String query = " select count(1) from EVENTO WHERE NOTIPOEVENTO= ? ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{noTipoEvento}).executeQuery();
            if (rs.next()) {
                existe = (rs.getInt(1) >= 1);
            }
            System.err.println("valor de la consulta: " + existe);
        } catch (SQLException e) {
            System.out.println("Error en existeTipoEventoEvento() " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en existeTipoEventoEvento() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return existe;
    }
    

    public Short getNoTipoEventoSiguiente() {
        Short noRol = null;
        ResultSet rs = null;
        final String query = "select nvl(max(NOTIPOEVENTO),0)+ 1 from KTIPOEVENTO ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query).executeQuery();
            if (rs.next()) {
                noRol = rs.getShort(1);
            }
        } catch (SQLException e) {
            System.out.println("Error en getNoTipoEventoSiguiente() " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en getNoTipoEventoSiguiente() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return noRol;
    }

    public List<KTipoEvento> traeRegistros() {
        List<KTipoEvento> listaTipoEvento;
        System.out.println("Entro a cargar la lista de registros ktipoEvento");
        KTipoEvento te = null;
        ResultSet rs = null;
        String query = null;
        query = "SELECT NOTIPOEVENTO, TIPOEVENTO, OBSERVACIONES FROM KTIPOEVENTO order by 1";

        try {
            listaTipoEvento = new java.util.ArrayList<>();
            super.abrirConexion();
            rs = super.getPreparedStatement(query).executeQuery();

            while (rs.next()) {
                te = new KTipoEvento();
                te.setNoTipoEvento(rs.getInt(1));
                te.setTipoEvento(rs.getString(2));
                te.setObservaciones(rs.getString(3));
                listaTipoEvento.add(te);

            }
            System.out.println("listaTipoEvento.size() = " + listaTipoEvento.size());
        } catch (SQLException e) {
            System.out.println("Error en listaTipoEvento() " + e.getMessage());
            listaTipoEvento = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en listaTipoEvento() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return listaTipoEvento;
    }
    
}
