/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.*;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import utilerias.Procedimiento;

/**
 *
 *
 */
public class EventoDAO extends conexion.ConexionOracle implements java.io.Serializable {

    public Procedimiento operacionesEvento(Evento ev,int operacion) throws SQLException {
        System.out.println("Entro a operacionesEvento "+operacion);
        Procedimiento pro=new Procedimiento();
        CallableStatement cs = null;
        Integer error=null;
        String mensaje="";
        Integer noEvento=null;
        try {
            super.abrirConexion();
            cs = super.getConn().prepareCall("{call pdbEvento(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        cs.registerOutParameter( 1, Types.INTEGER ); // parametro de salida error
        cs.registerOutParameter( 2, Types.VARCHAR ); // parametro de salida mensaje
        cs.setInt(3, operacion); //operacion
        cs.registerOutParameter( 4, Types.INTEGER ); // parametro de salida noEvento
        cs.setInt(4, ev.getNoEvento()); //tabla
        cs.setInt(5, ev.getNoVehiculo()); //tabla
        cs.setString(6, ev.getDescripcion()); //tabla
        cs.setString(7, ev.getFecha()); //tabla
        cs.setString(8, ev.getProveedor()); //tabla
        cs.setDouble(9, ev.getImporte()); //tabla
        cs.setString(10, ev.getNumFactura()); //tabla
        cs.setInt(11, ev.getNoTipoEvento()); //tabla
        cs.setInt(12, ev.getNoUsuarioOperacion()); //tabla
        cs.setInt(13, ev.getKm()); //tabla
        cs.setInt(14, ev.getNoUr()); //tabla
        cs.setString(15, ev.getResguardante()); //tabla
        cs.setString(16, ev.getPuesto()); //tabla
        cs.setString(17, ev.getHolograma()); //tabla
        cs.setString(18, ev.getFechaVencimiento()); //tabla
        cs.setInt(19, ev.getNoOrigen()); //tabla
        cs.setInt(20, ev.getNoFormaAdquisicion()); //tabla
        cs.execute();
        noEvento = cs.getInt(4);
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
        System.out.println("mensaje:("+mensaje+")");
        return pro;
    }
    
    
     public void getFotoStream(HttpServletRequest request,HttpServletResponse response,String clave) throws IOException {
      
        ResultSet rs = null;
        InputStream sImage = null;
        byte[] bytearray = new byte[1048576];
        int size = 0;
        String query = "SELECT IMAGEN from IMAGENEVENTO where NOEVENTO = ? ";
        
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{clave}).executeQuery();
            
            while (rs.next()) {
                //System.out.println("Extrayendo foto");
                sImage = rs.getBinaryStream(1);
                response.reset();
                response.setContentType("image/jpeg");
                
                while ((size = sImage.read(bytearray)) != -1) {
                    response.getOutputStream().write(bytearray, 0, size);
                    
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
            } 
            catch (SQLException e) {
                System.out.println("Error en getFoto() " + e.getMessage());
            }
            super.cerrarConexion();
        }
   }
    
     
     public void getPDFStream(HttpServletRequest request,HttpServletResponse response,String clave) throws IOException {
      
        ResultSet rs = null;
        InputStream sImage = null;
        byte[] bytearray = new byte[1048576];
        int size = 0;
        String query = "SELECT IMAGEN from IMAGENEVENTO where NOEVENTO = ? ";
        
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{clave}).executeQuery();
            if(rs.next()){
                rs = super.getPreparedStatement(query, new Object[]{clave}).executeQuery();
            while (rs.next()) {
                //System.out.println("Extrayendo foto");
                sImage = rs.getBinaryStream(1);
                response.reset();
                response.setContentType("application/pdf");
                
                while ((size = sImage.read(bytearray)) != -1) {
                    response.getOutputStream().write(bytearray, 0, size);
                    
                }
            }
            }else{
ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
     String path = context.getRealPath("/");
     String ruta = path+"//resources//reportes//blanco.pdf";           
 String rutaArchivoNuevo = ruta;
InputStream inputStream = new FileInputStream(rutaArchivoNuevo);
response.reset();
response.setContentType("application/pdf");
  while ((size = inputStream.read(bytearray)) != -1) {
                    response.getOutputStream().write(bytearray, 0, size);
                    
                }
            }
           
        }
        catch (SQLException e) {
            System.out.println("Error en getPDF() " + e.getMessage());
            
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } 
            catch (SQLException e) {
                System.out.println("Error en getPDF() " + e.getMessage());
            }
            super.cerrarConexion();
        }
   }
    public StreamedContent getPDF(int noEvento) throws FileNotFoundException, IOException {
      
        ResultSet rs = null;
        byte[] bytearray = new byte[1048576];
        StreamedContent sc=null;
        ByteArrayInputStream bais=null;
        String query = "SELECT IMAGEN from IMAGENEVENTO where NOEVENTO = ? ";
        
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{noEvento}).executeQuery();
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            String path = context.getRealPath("/");
            if(rs.next()){
                bytearray=rs.getBytes(1);
                System.out.println("Leyendo archivo "+noEvento+" desde la base de datos...");
                bais= new ByteArrayInputStream(bytearray);
                sc = new DefaultStreamedContent(bais, "application/pdf","Evento.pdf");
            }else{
                String ruta = path+"\\resources\\reportes\\blanco.pdf";
                InputStream inputStream = new FileInputStream(ruta);
                sc = new DefaultStreamedContent(inputStream,"application/pdf","SinPDF.pdf");
            }
        }
        catch (SQLException e) {
            System.out.println("Error en getPDF() " + e.getMessage());
            
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } 
            catch (SQLException e) {
                System.out.println("Error en getPDF() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return sc;
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
      
      
      public boolean existePdfEvento(int clave) {
        boolean existe = false;
        ResultSet rs = null;
        final String query = "  SELECT COUNT(1) "
                + "  FROM IMAGENEVENTO "
                + "  WHERE NOEVENTO =  ?   ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{clave}).executeQuery();
            if (rs.next()) {
                existe = (rs.getInt(1) >= 1);
            }
            System.err.println("valor de la consulta: " + existe);
        } catch (SQLException e) {
            System.out.println("Error en existePdfEvento() " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en existePdfEvento() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return existe;
    }
      
     public int insertaImagen(EventoFotos ef,boolean existe) throws IOException {
      
        ResultSet rs = null;
        String query="";
        int valor=0;
        if(existe){
        query = "UPDATE IMAGENEVENTO "
                + " SET IMAGEN = ? "
                + "  WHERE NOEVENTO = ? "; }
        else{
         query = "INSERT INTO IMAGENEVENTO "
                + " (IMAGEN,NOIMAGEN,NOEVENTO) "
                + "  VALUES (?,(select nvl(max(NOIMAGEN),0)+ 1 from IMAGENEVENTO),?) ";
        }
        System.out.println(query);
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{ef.getImagenByte(),
                                                                ef.getNoEvento()
                                                                }).executeQuery();
            
        }
        catch (SQLException e) {
            System.out.println("Error en insertaImagen() " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    valor=1;
                }
            } 
            catch (SQLException e) {
                System.out.println("Error en insertaImagen() " + e.getMessage());
            }
            super.cerrarConexion();
        }
   return valor;
     }
     
     public int eliminaImagen(int noEvento) throws IOException {
      
        ResultSet rs = null;
        String query="";
        int valor=0;
         query = "DELETE FROM IMAGENEVENTO "
                + "  where NOEVENTO = ? ";
        
        System.out.println(query);
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{noEvento}).executeQuery();
            
        } 
        catch (SQLException e) {
            System.out.println("Error en eliminaImagen() " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    valor=1;
                }
            } 
            catch (SQLException e) {
                System.out.println("Error en eliminaImagen() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return valor;
   }
     
     public List<EventoFotos> traeRegistrosImagenes(int noEvento) {
        List<EventoFotos> listaEventoImagenes;
        System.out.println("Entro a cargar la lista de registros listaEventoImagenes");
        EventoFotos ev = null;
        ResultSet rs = null;
        String query = null;
        query = "SELECT NOIMAGEN, NOEVENTO FROM IMAGENEVENTO where NOEVENTO = ? ";
        try {
            listaEventoImagenes = new java.util.ArrayList<>();
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{noEvento}).executeQuery();

            while (rs.next()) {
                ev = new EventoFotos();
                ev.setNoImagen(rs.getInt(1));
                ev.setNoEvento(rs.getInt(2));
                listaEventoImagenes.add(ev);

            }
            System.out.println("listaEventoImagenes.size() = " + listaEventoImagenes.size());
        } catch (SQLException e) {
            System.out.println("Error en listaEventoImagenes() " + e.getMessage());
            listaEventoImagenes = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en listaEventoImagenes() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return listaEventoImagenes;
    }
/*
    public int getNoEventoSiguiente() throws SQLException {
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
        cs.setString(4, "EVENTO"); //tabla
        cs.execute();
        noRol = cs.getInt(3);
        error = cs.getString(1);
        mensaje = cs.getString(2);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en getNoEventoSiguiente() error: "+error+" mensaje: "+mensaje);
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
    public List<Evento> traeRegistros(int noUsuario) {
        List<Evento> listaEvento;
        System.out.println("Entro a cargar la lista de registros listaEvento");
        Evento ev = null;
        ResultSet rs = null;
        String query = null;
        query = "SELECT EV.NOEVENTO,\n" +
"       EV.NOVEHICULO,\n" +
"       EV.DESCRIPCION,\n" +
"       TO_CHAR(EV.FECHA, 'DD/MM/YYYY'),\n" +
"       EV.PROVEEDOR,\n" +
"       EV.IMPORTE,\n" +
"       EV.NUMFACTURA,\n" +
"       EV.NOTIPOEVENTO,\n" +
"       (SELECT TE.TIPOEVENTO FROM KTIPOEVENTO TE WHERE TE.NOTIPOEVENTO = EV.NOTIPOEVENTO) TIPOEVENTO,\n" +
"       EV.FECHAALTA,\n" +
"       EV.NOUSUARIOALTA,\n" +
"       EV.FECHAMODIF,\n" +
"       EV.NOUSUARIOMODIF,\n" +
"       EV.FECHABAJA,\n" +
"       EV.NOUSUARIOBAJA,\n" +
"       (SELECT UR.NOUR FROM \n" +
"      VEHICULO VE,\n" +
"      UR UR\n" +
"      WHERE VE.NOUR = UR.NOUR\n" +
"      AND UR.ESTATUS='A'\n" +
"      AND VE.ESTATUS='A'\n" +
"      AND VE.NOVEHICULO = EV.NOVEHICULO) UR,\n" +
"      EV.ESTATUS, EV.KM \n" +
"FROM EVENTO EV,"+ 
"     VEHICULO VE,\n" +
"     URUSUARIO UU\n" +
"WHERE EV.NOVEHICULO = VE.NOVEHICULO\n" +
"AND   VE.NOUR = UU.NOUR\n" +
"and   UU.NOUSUARIO = ? "+ 
" order by EV.FECHA DESC";
       
        try {
            listaEvento = new java.util.ArrayList<>();
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{noUsuario}).executeQuery();

            while (rs.next()) {
                ev = new Evento();
                ev.setNoEvento(rs.getInt(1));
                ev.setNoVehiculo(rs.getInt(2));
                ev.setDescripcion(rs.getString(3));
                ev.setFecha(rs.getString(4));
                ev.setProveedor(rs.getString(5));
                ev.setImporte(rs.getDouble(6));
                ev.setNumFactura(rs.getString(7));
                ev.setNoTipoEvento(rs.getInt(8));
                ev.setTipoEvento(rs.getString(9));
                ev.setFechaAlta(rs.getString(10));
                ev.setNoUsuarioAlta(rs.getInt(11));
                ev.setFechaModif(rs.getString(12));
                ev.setNoUsuarioModif(rs.getInt(13));
                ev.setFechaBaja(rs.getString(14));
                ev.setNoUsuarioBaja(rs.getInt(15));
                ev.setNoUr(rs.getInt(16));
                ev.setEstatus(rs.getString(17));
                ev.setKm(rs.getInt(18));
                listaEvento.add(ev);

            }
            System.out.println("listaEvento.size() = " + listaEvento.size());
        } catch (SQLException e) {
            System.out.println("Error en listaEvento() " + e.getMessage());
            listaEvento = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en listaEvento() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return listaEvento;
    }
    
    public List<Evento> traeRegistrosEvento(int noVehiculo) {
        List<Evento> listaEvento;
        System.out.println("Entro a cargar la lista de registros traeRegistrosEvento");
        Evento ev = null;
        ResultSet rs = null;
        String query = null;
        query = "SELECT EV.NOEVENTO,\n" +
"       EV.NOVEHICULO,\n" +
"       EV.DESCRIPCION,\n" +
"       TO_CHAR(EV.FECHA, 'DD/MM/YYYY'),\n" +
"       EV.PROVEEDOR,\n" +
"       EV.IMPORTE,\n" +
"       EV.NUMFACTURA,\n" +
"       EV.NOTIPOEVENTO,\n" +
"       (SELECT TE.TIPOEVENTO FROM KTIPOEVENTO TE WHERE TE.NOTIPOEVENTO = EV.NOTIPOEVENTO) TIPOEVENTO,\n" +
"       EV.FECHAALTA,\n" +
"       EV.NOUSUARIOALTA,\n" +
"       EV.FECHAMODIF,\n" +
"       EV.NOUSUARIOMODIF,\n" +
"       EV.FECHABAJA,\n" +
"       EV.NOUSUARIOBAJA,\n" +
"       (SELECT UR.NOUR FROM \n" +
"      VEHICULO VE,\n" +
"      UR UR\n" +
"      WHERE VE.NOUR = UR.NOUR\n" +
"      AND UR.ESTATUS='A'\n" +
"      AND VE.ESTATUS='A'\n" +
"      AND VE.NOVEHICULO = EV.NOVEHICULO) UR,\n" +
"      EV.ESTATUS, EV.KM \n" +
"FROM EVENTO EV,"+ 
"     VEHICULO VE\n" +
"WHERE EV.NOVEHICULO = VE.NOVEHICULO\n" +
"AND   VE.NOVEHICULO = ? \n" +
" order by EV.FECHA DESC";
        //System.out.println(query);
        try {
            listaEvento = new java.util.ArrayList<>();
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{noVehiculo}).executeQuery();

            while (rs.next()) {
                ev = new Evento();
                ev.setNoEvento(rs.getInt(1));
                ev.setNoVehiculo(rs.getInt(2));
                ev.setDescripcion(rs.getString(3));
                ev.setFecha(rs.getString(4));
                ev.setProveedor(rs.getString(5));
                ev.setImporte(rs.getDouble(6));
                ev.setNumFactura(rs.getString(7));
                ev.setNoTipoEvento(rs.getInt(8));
                ev.setTipoEvento(rs.getString(9));
                ev.setFechaAlta(rs.getString(10));
                ev.setNoUsuarioAlta(rs.getInt(11));
                ev.setFechaModif(rs.getString(12));
                ev.setNoUsuarioModif(rs.getInt(13));
                ev.setFechaBaja(rs.getString(14));
                ev.setNoUsuarioBaja(rs.getInt(15));
                ev.setNoUr(rs.getInt(16));
                ev.setEstatus(rs.getString(17));
                ev.setKm(rs.getInt(18));
                listaEvento.add(ev);

            }
            System.out.println("traeRegistrosEvento.size() = " + listaEvento.size());
        } catch (SQLException e) {
            System.out.println("Error en traeRegistrosEvento() " + e.getMessage());
            listaEvento = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en traeRegistrosEvento() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return listaEvento;
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
}
