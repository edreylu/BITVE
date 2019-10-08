/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

import java.sql.ResultSet;

/**
 *
 * @author usuario
 */
public class ReportesDAO extends conexion.ConexionOracle implements java.io.Serializable{
    
    
    public ResultSet getConsulta(String psQuery)
	{
		ResultSet cursor=null;
                System.out.println(psQuery);
		try
		{
                    super.abrirConexion();
			
                        cursor = super.getPreparedStatement(psQuery).executeQuery();
		}
		catch(Exception e)
		{System.out.println("Error en getConsulta() " + e.getMessage());}
		return cursor;
	}
}
