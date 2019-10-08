/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehiculos;

import catalogos.Marca;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import utilerias.Procedimiento;

/**
 *
 *
 */
public class VehiculoDAO extends conexion.ConexionOracle implements java.io.Serializable {

    public Procedimiento operacionesVehiculo(Vehiculo ve,int operacion) throws SQLException {
        System.out.println("Entro a operacionesVehiculo "+operacion);
        Procedimiento pro=new Procedimiento();
        CallableStatement cs = null;
        Integer error=null;
        String mensaje="";
        Integer noVehiculo=null;
        try {
            super.abrirConexion();
            cs = super.getConn().prepareCall("{call pdbVehiculo(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        cs.registerOutParameter( 1, Types.INTEGER ); // parametro de salida error
        cs.registerOutParameter( 2, Types.VARCHAR ); // parametro de salida mensaje
        cs.setInt(3, operacion); //operacion
        cs.registerOutParameter( 4, Types.INTEGER ); // parametro de salida noVehiculo
        cs.setInt(4, ve.getNoVehiculo()); //tabla
        cs.setString(5, ve.getSerie()); //tabla
        cs.setString(6, ve.getPlacas()); //tabla
        cs.setInt(7, ve.getModelo()); //tabla
        cs.setString(8, ve.getTipo()); //tabla
        cs.setInt(9, ve.getNoUr()); //tabla
        cs.setInt(10, ve.getNoUsuarioOperacion()); //tabla
        cs.setString(11, ve.getColor()); //tabla
        cs.setString(12, ve.getIme()); //tabla
        cs.setInt(13, ve.getNoMarca()); //tabla
        cs.setString(14, ve.getResguardante()); //tabla
        cs.setString(15, ve.getPuesto()); //tabla
        cs.execute();
        noVehiculo = cs.getInt(4);
        error = cs.getInt(1);
        mensaje = cs.getString(2);
        pro.setError(error);
        pro.setMensaje(mensaje);
        } catch (Exception e) {
            //System.out.println("Error en insertaTipoEvento() " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                
            } catch (Exception e) {
                //System.out.println("Error en insertaTipoEvento() " + e.getMessage());
                e.printStackTrace();
            }
            super.cerrarConexion();
        }
        return pro;
    }
    
    public Procedimiento obtenerKM(int ve,Connection conn) throws SQLException {
        Procedimiento pro=new Procedimiento();
        CallableStatement cs = null;
        Integer error=null;
        String mensaje="";
        Integer km=null;
        String fechaEvento="";
        try {
            //super.abrirConexion();
            cs = conn.prepareCall("{call pdbConsultaKM(?, ?, ?, ?, ?)}");
        cs.registerOutParameter( 1, Types.INTEGER ); // parametro de salida error
        cs.registerOutParameter( 2, Types.VARCHAR ); // parametro de salida mensaje
        cs.registerOutParameter( 3, Types.INTEGER ); // parametro de salida KM
        cs.registerOutParameter( 4, Types.VARCHAR ); // parametro de salida fechaEvento
        cs.setInt(5, ve); //tabla
        cs.executeUpdate();
        km = cs.getInt(3);
        error = cs.getInt(1);
        mensaje = cs.getString(2);
        fechaEvento=cs.getString(4);
        pro.setError(error);
        pro.setMensaje(mensaje);
        pro.setKm(km);
        pro.setFechaEvento(fechaEvento);
        } catch (Exception e) {
            //System.out.println("Error en obtenerKM() " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
               //cs.close(); 
            } catch (Exception e) {
                //System.out.println("Error en obtenerKM() " + e.getMessage());
                e.printStackTrace();
            }
            //super.cerrarConexion();
            
        }
        return pro;
    }
    
/*
    public int getNoVehiculoSiguiente() throws SQLException {
        Integer noRol = null;
        String error = "";
        String mensaje = "";
        ResultSet rs = null;
        CallableStatement cs = null;
        Connection conn = null;
        
        try {
            super.abrirConexion();
            conn = super.getConn();
            cs = conn.prepareCall("{call PDBOBTIENEFOLIO(?, ?, ?, ?)}");
        cs.registerOutParameter( 1, Types.INTEGER ); // parametro de salida error
        cs.registerOutParameter( 2, Types.VARCHAR ); // parametro de salida mensaje
        cs.registerOutParameter( 3, Types.VARCHAR ); // parametro de salida folio
        cs.setString(4, "VEHICULO"); //tabla
        cs.execute();
        noRol = cs.getInt(3);
        error = cs.getString(1);
        mensaje = cs.getString(2);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en getNoVehiculoSiguiente() error: "+error+" mensaje: "+mensaje);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn.close();
            super.cerrarConexion();
        }
        return noRol;
    }
*/
    public List<Vehiculo> traeRegistrosInactivos() {
        List<Vehiculo> listaVehiculo;
        System.out.println("Entro a cargar la lista de registros traeRegistrosInactivos");
        Vehiculo ve = null;
        Procedimiento pro = null;
        ResultSet rs = null;
        String query = null;
        query = "select VE.NOVEHICULO,\n" +
"     VE.SERIE,\n" +
"     VE.PLACAS,\n" +
"     VE.NOMARCA,\n" +
"     VE.MODELO,\n" +
"     VE.TIPO,\n" +
"     VE.FECHAALTA,\n" +
"     VE.NOUSUARIOALTA,\n" +
"     VE.FECHAMODIF,\n" +
"     VE.NOUSUARIOMODIF,\n" +
"     VE.FECHABAJA,\n" +
"     VE.NOUSUARIOBAJA, \n" +
"     VE.NOUR, \n" +
"     UR.UR, \n" +
"     VE.ESTATUS,VE.COLOR, EV.RESGUARDANTE, EV.PUESTO, UR.CLAVEUR, "+
"           (select MARCA from kmarca where nomarca = VE.NOMARCA and estatus ='A') MARCA, VE.IME \n" +
"               from VEHICULO VE,\n" +
"                    EVENTO EV,\n" +
"                      UR UR\n" +
"              where VE.NOUR = UR.NOUR\n" +
"              AND ve.novehiculo = ev.novehiculo\n" +
"              AND ev.notipoevento=9\n" +
"              AND ev.estatus ='A'\n" +
"               order by 1";
System.out.println(query);
        try {
            listaVehiculo = new java.util.ArrayList<>();
            super.abrirConexion();
            rs = super.getPreparedStatement(query).executeQuery();
            while (rs.next()) {
                ve = new Vehiculo();
                //pro = new Procedimiento();
                ve.setNoVehiculo(rs.getInt(1));
                ve.setSerie(rs.getString(2));
                ve.setPlacas(rs.getString(3));
                ve.setNoMarca(rs.getInt(4));
                ve.setModelo(rs.getInt(5));
                ve.setTipo(rs.getString(6));
                ve.setFechaAlta(rs.getString(7));
                ve.setNoUsuarioAlta(rs.getInt(8));
                ve.setFechaModif(rs.getString(9));
                ve.setNoUsuarioModif(rs.getInt(10));
                ve.setFechaBaja(rs.getString(11));
                ve.setNoUsuarioBaja(rs.getInt(12));
                ve.setNoUr(rs.getInt(13));
                ve.setUr(rs.getString(14));
                ve.setEstatus(rs.getString(15));
                pro=obtenerKM(ve.getNoVehiculo(),super.getConn());
                ve.setKm(pro.getKm());
                ve.setFechaEvento(pro.getFechaEvento());
                ve.setColor(rs.getString(16));
                ve.setResguardante(rs.getString(17));
                ve.setPuesto(rs.getString(18));
                ve.setClaveUr(rs.getString(19));
                ve.setMarca(rs.getString(20));
                ve.setIme(rs.getString(21));
                listaVehiculo.add(ve);
                pro=null;

            }
            System.out.println("listaVehiculo.size() = " + listaVehiculo.size());
        } catch (SQLException e) {
            System.out.println("Error en listaVehiculo() " + e.getMessage());
            listaVehiculo = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en listaVehiculo() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return listaVehiculo;
    }
    public List<Vehiculo> traeRegistrosInactivosUsuario(int noUsuario) {
        List<Vehiculo> listaVehiculo;
        System.out.println("Entro a cargar la lista de registros traeRegistrosInactivosUsuario");
        Vehiculo ve = null;
        ResultSet rs = null;
        String query = null;
        query = "SELECT VE.NOVEHICULO,VE.SERIE,VE.PLACAS,VE.NOMARCA,VE.MODELO,VE.TIPO,VE.FECHAALTA,VE.NOUSUARIOALTA,VE.FECHAMODIF,VE.NOUSUARIOMODIF,VE.FECHABAJA,VE.NOUSUARIOBAJA, VE.NOUR,VE.ESTATUS,"
                + " (select MARCA from kmarca where nomarca = VE.NOMARCA and estatus ='A') MARCA, VE.IME \n" +
"               FROM VEHICULO VE,\n" +
"                    URUSUARIO UU\n" +
"               WHERE  VE.NOUR = UU.NOUR\n" +
"                and   UU.NOUSUARIO = ? order by 1";

        try {
            listaVehiculo = new java.util.ArrayList<>();
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{noUsuario}).executeQuery();

            while (rs.next()) {
                ve = new Vehiculo();
                ve.setNoVehiculo(rs.getInt(1));
                ve.setSerie(rs.getString(2));
                ve.setPlacas(rs.getString(3));
                ve.setNoMarca(rs.getInt(4));
                ve.setModelo(rs.getInt(5));
                ve.setTipo(rs.getString(6));
                ve.setFechaAlta(rs.getString(7));
                ve.setNoUsuarioAlta(rs.getInt(8));
                ve.setFechaModif(rs.getString(9));
                ve.setNoUsuarioModif(rs.getInt(10));
                ve.setFechaBaja(rs.getString(11));
                ve.setNoUsuarioBaja(rs.getInt(12));
                ve.setNoUr(rs.getInt(13));
                ve.setEstatus(rs.getString(14));
                ve.setMarca(rs.getString(15));
                ve.setIme(rs.getString(16));
                Procedimiento pro = new Procedimiento();
                pro=obtenerKM(rs.getInt(1),super.getConn());
                ve.setKm(pro.getKm());
                ve.setFechaEvento(pro.getFechaEvento());
                listaVehiculo.add(ve);

            }
            System.out.println("listaVehiculo.size() = " + listaVehiculo.size());
        } catch (SQLException e) {
            System.out.println("Error en listaVehiculo() " + e.getMessage());
            listaVehiculo = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en listaVehiculo() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return listaVehiculo;
    }
    
    public List<Vehiculo> traeRegistrosActivosElegir(int noUr) {
        List<Vehiculo> listaVehiculo;
        System.out.println("Entro a cargar la lista de registros traeRegistrosActivosElegir");
        Vehiculo ve = null;
        ResultSet rs = null;
        String query = null;
        query = "SELECT VE.NOVEHICULO,VE.SERIE,VE.PLACAS,"
                + " VE.NOMARCA,VE.MODELO,VE.TIPO,VE.FECHAALTA,VE.NOUSUARIOALTA,VE.FECHAMODIF,VE.NOUSUARIOMODIF,VE.FECHABAJA,VE.NOUSUARIOBAJA, VE.NOUR," +
                " (SELECT UR.UR FROM \n" +
"      UR UR\n" +
"      WHERE VE.NOUR = UR.NOUR\n" +
"      AND UR.ESTATUS='A'\n" +
"      ) URDESC,\n" +
                "VE.ESTATUS, (select MARCA from kmarca where nomarca = VE.NOMARCA and estatus ='A') MARCA, VE.IME \n" +
"               FROM VEHICULO VE\n" +
"               WHERE  VE.NOUR = ? order by 1";
        System.out.println(query);
        try {
            listaVehiculo = new java.util.ArrayList<>();
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{noUr}).executeQuery();

            while (rs.next()) {
                ve = new Vehiculo();
                ve.setNoVehiculo(rs.getInt(1));
                ve.setSerie(rs.getString(2));
                ve.setPlacas(rs.getString(3));
                ve.setNoMarca(rs.getInt(4));
                ve.setModelo(rs.getInt(5));
                ve.setTipo(rs.getString(6));
                ve.setFechaAlta(rs.getString(7));
                ve.setNoUsuarioAlta(rs.getInt(8));
                ve.setFechaModif(rs.getString(9));
                ve.setNoUsuarioModif(rs.getInt(10));
                ve.setFechaBaja(rs.getString(11));
                ve.setNoUsuarioBaja(rs.getInt(12));
                ve.setNoUr(rs.getInt(13));
                ve.setUr(rs.getString(14));
                ve.setEstatus(rs.getString(15));
                ve.setMarca(rs.getString(16));
                ve.setIme(rs.getString(17));
                listaVehiculo.add(ve);

            }
            System.out.println("traeRegistrosActivosElegir.size() = " + listaVehiculo.size());
        } catch (SQLException e) {
            System.out.println("Error en traeRegistrosActivosElegir() " + e.getMessage());
            listaVehiculo = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en traeRegistrosActivosElegir() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return listaVehiculo;
    }

public void imagenReporte() throws IOException {
      
        ResultSet rs = null;
        FileOutputStream fos = null;
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        String path = context.getRealPath("/");
        String query = "select imagen from PARAMETROS ";
        
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query).executeQuery();
            
            while (rs.next()) {
               String imagePath = path+"//resources//img//SEP.png";
               String pathname=imagePath;
                File file = new File(pathname);
                fos = new FileOutputStream(file);                    
                Blob bin = rs.getBlob("imagen");
                InputStream inStream = bin.getBinaryStream();
                int size = (int)bin.length();
                byte[] buffer = new byte[size];
                int length = -1;
                while ((length = inStream.read(buffer)) != -1)
                {
                  fos.write(buffer, 0, length);                
                }
                
            }
        } 
        catch (SQLException e) {
            System.out.println("Error en getFoto() " + e.getMessage());
            
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (fos != null){
                fos.close();
                }
            } 
            catch (SQLException e) {
                System.out.println("Error en getFoto() " + e.getMessage());
            }
            super.cerrarConexion();
        }
   }

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

public List<Marca> traeRegistrosMarca() {
        List<Marca> listaMarca;
        System.out.println("Entro a cargar la lista de registros listaUrC");
        Marca marca = null;
        ResultSet rs = null;
        String query = null;
        query = "select nomarca, marca, observaciones from kmarca order by 1";

        try {
            listaMarca = new java.util.ArrayList<>();
            super.abrirConexion();
            rs = super.getPreparedStatement(query).executeQuery();

            while (rs.next()) {
                marca = new Marca();
                marca.setNoMarca(rs.getInt(1));
                marca.setMarca(rs.getString(2));
                marca.setObservaciones(rs.getString(3));
                listaMarca.add(marca);

            }
            System.out.println("listaMarca.size() = " + listaMarca.size());
        } catch (SQLException e) {
            System.out.println("Error en listaMarca() " + e.getMessage());
            listaMarca = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en listaMarca() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return listaMarca;
    }
}
