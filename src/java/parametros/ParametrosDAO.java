/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parametros;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import ur.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 *
 */
public class ParametrosDAO extends conexion.ConexionOracle implements java.io.Serializable {

    public int insertaParametros(Parametros par) {
        System.out.println("Entro a insertar ");
        int valor=0;
        ResultSet rs = null;
        final String query = "INSERT INTO PARAMETROS (TIEMPOMAXIMOMODIF,MOSTRARINACTIVOS) "
                + "VALUES (?,?)";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{
                par.getTiempoMaximoModif(),
                par.getMostrarInactivos()
                }
            ).executeQuery();
            
            
        } catch (SQLException e) {
            System.out.println("Error en insertaParametros() " + e.getMessage());
            
        } finally {
            try {
                if (null != rs) {
                    valor=1;
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en insertaParametros() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        System.out.println("valor: " + valor);
        return valor;
    }
    
    public int actualizaParametros(Parametros par) {
        int valor=0;
        ResultSet rs = null;
        
        final String query = "UPDATE PARAMETROS set TIEMPOMAXIMOMODIF =? ,MOSTRARINACTIVOS = ?  ";

        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{par.getTiempoMaximoModif(),
                par.getMostrarInactivos()}).executeQuery();
            
            
        } catch (SQLException e) {
            System.out.println("Error en actualizaParametros() " + e.getMessage());
        } finally {
            try {
                if (null != rs) {
                    valor=1;
                    rs.close();
                }
                super.cerrarConexion();
            } catch (SQLException e) {
                System.out.println("Error en actualizaParametros() " + e.getMessage());
            }
        }
        return valor;
    }

    public int eliminaParametros(Parametros ur) {
        int valor=0;
        ResultSet rs = null;
        final String query = "DELETE FROM PARAMETROS";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query).executeQuery();
            

        } catch (SQLException e) {
            System.out.println("Error en eliminaParametros() " + e.getMessage());
        } finally {
            try {
                if (null != rs) {
                    valor=1;
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en eliminaParametros() " + e.getMessage());
                
            }
            super.cerrarConexion();
        }
        return valor;
    }



    public Parametros parametros() {
        Parametros parametros= null;
        ResultSet rs = null;
        final String query = "select TIEMPOMAXIMOMODIF,MOSTRARINACTIVOS,IMAGEN from PARAMETROS ";
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query).executeQuery();
            if (rs.next()) {
                parametros = new Parametros();
                parametros.setTiempoMaximoModif(rs.getInt(1));
                parametros.setMostrarInactivos(rs.getString(2));
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
        return parametros;
    }

    public List<Parametros> traeRegistrosParametros() {
        List<Parametros> listaParametros;
        System.out.println("Entro a cargar la lista de registros listaParametros");
        Parametros par = null;
        ResultSet rs = null;
        String query = null;
        query = "select TIEMPOMAXIMOMODIF,MOSTRARINACTIVOS,IMAGEN from PARAMETROS ";
        try {
            listaParametros = new java.util.ArrayList<>();
            super.abrirConexion();
            rs = super.getPreparedStatement(query).executeQuery();
            StreamedContent myImage;
            while (rs.next()) {
                par = new Parametros();
                par.setTiempoMaximoModif(rs.getInt(1));
                par.setMostrarInactivos(rs.getString(2));
                InputStream inputStream = rs.getBlob(3).getBinaryStream();
                myImage = new DefaultStreamedContent(inputStream, "image/png");
                par.setSc(myImage);
                listaParametros.add(par);

            }
            System.out.println("listaParametros.size() = " + listaParametros.size());
        } catch (SQLException e) {
            System.out.println("Error en listaParametros() " + e.getMessage());
            listaParametros = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en listaParametros() " + e.getMessage());
            }
            super.cerrarConexion();
        }
        return listaParametros;
    }
    public void getFotoStream(HttpServletRequest request,HttpServletResponse response) throws IOException {
      
        ResultSet rs = null;
        InputStream sImage = null;
        byte[] bytearray = new byte[1048576];
        int size = 0;
        String query = "SELECT IMAGEN from PARAMETROS";
        
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query).executeQuery();
            
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
    
    public int insertaImagen(Parametros par) throws IOException {
      
        ResultSet rs = null;
        String query="";
        int valor=0;
         query = "UPDATE PARAMETROS set IMAGEN =? ";
        
        System.out.println(query);
        try {
            super.abrirConexion();
            rs = super.getPreparedStatement(query, new Object[]{par.getImagenByte()}).executeQuery();
            
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
               System.out.println("Descargando foto: " + imagePath);
               String pathname=imagePath;
                File file = new File(pathname);
                fos = new FileOutputStream(file);                    
                Blob bin = rs.getBlob("imagen");
                InputStream inStream = bin.getBinaryStream();
                //int size = (int)bin.length();
                byte[] buffer = new byte[1048576];
                int length = 0;
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
    
}
