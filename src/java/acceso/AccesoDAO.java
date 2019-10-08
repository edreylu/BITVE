/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acceso;

import java.sql.ResultSet;
import java.sql.SQLException;


public class AccesoDAO extends conexion.ConexionOracle implements java.io.Serializable {

    public Rolbotones getPermisos(long r) {
        Rolbotones rolbotones = null;
        ResultSet rs = null;
        final String query = "SELECT  RO.NO_ROL, RO.DESCRIPCION, RO.INSERTAR, RO.ACTUALIZAR, RO.ELIMINAR, RO.CONSULTAR, RO.DESCARGAR \n" +
"                FROM ROLES RO,\n" +
"                    ROLES_USUARIOS RU\n" +
"                    WHERE RU.NO_USUARIO = ? \n" +
"                    AND RU.NO_ROL = RO.NO_ROL\n" +
"                    AND RU.ESTATUS='A'\n" +
"                    and RO.ESTATUS='A' ";
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

}
