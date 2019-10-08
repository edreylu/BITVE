/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import utilerias.Mensaje;

/**
 *
 * @author usuario
 */
@ManagedBean (name = "reportesControl")
@SessionScoped
public class ReportesControl {
   private final ReportesDAO rdao;
   private final Mensaje msg= new Mensaje();
   public ReportesControl() {
      rdao = new ReportesDAO();
   }
   
   public void init() {
       
   }
    
    public void generaExcel(String consulta,String tituloReporte,String nombreReporte)
  {
    ResultSet CursorConsulta;
    int columna = 0;
    int fila = 0;
    String Plantilla = "";
    String TituloEncabezado = "S";
    String TituloColumna = "S";
    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        String path = context.getRealPath("/");
        String realPath = path+"//resources//reportes//REPORTE_BITVE.xls";
        Plantilla = realPath;
     String ruta = path+"//resources//reportes//reportesModulo//"+nombreReporte+".xls";
     String imagePath = path+"//resources//img//SEP.png";
     System.out.println("Ruta: "+ruta);
    try
    {
      POIFSFileSystem loFS = new POIFSFileSystem(new FileInputStream(Plantilla));
      HSSFWorkbook loWB = new HSSFWorkbook(loFS);
      HSSFCellStyle style = loWB.createCellStyle();
      HSSFCellStyle style2 = loWB.createCellStyle();
      HSSFSheet loSheet = loWB.getSheet("Hoja1");
      fila = 5;
      //vedao.imagenReporte();
      final FileInputStream stream =new FileInputStream(imagePath);
    final CreationHelper helper = loWB.getCreationHelper();
    final Drawing drawing = loSheet.createDrawingPatriarch();
    final ClientAnchor anchor = helper.createClientAnchor();
    anchor.setAnchorType( ClientAnchor.MOVE_AND_RESIZE );
    final int pictureIndex =loWB.addPicture(IOUtils.toByteArray(stream), loWB.PICTURE_TYPE_PNG);
    anchor.setCol1(0);
    anchor.setRow1(0); 
    anchor.setRow2(0);
    anchor.setCol2(0);
    final Picture pict = drawing.createPicture( anchor, pictureIndex );
    double pors = 1.5;
    pict.resize(pors, 3);
    //pict.resize();
        if (TituloEncabezado.equals("S"))
      {
        HSSFRow loRow = loSheet.getRow(fila - 2);
        if (loRow == null) loRow = loSheet.createRow(fila - 2);
        HSSFCell loCell = loRow.getCell((short)0);
        if (loCell == null) loCell = loRow.createCell((short)0);
        style2.setAlignment(style2.ALIGN_CENTER);
        loCell.setCellStyle(style2);
        loCell.setCellValue(tituloReporte);
      }

      CursorConsulta = rdao.getConsulta(consulta);
      ResultSetMetaData MetaData = CursorConsulta.getMetaData();
        if (TituloColumna.equals("S"))
      {
        HSSFRow loFila = loSheet.getRow(fila);
        if (loFila == null) loFila = loSheet.createRow(fila);
        for (columna = 0; columna < MetaData.getColumnCount(); columna++)
        {
          HSSFCell celda = loFila.getCell((short)columna);
          if (celda == null) celda = loFila.createCell((short)columna);
          style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
          style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
          celda.setCellValue(MetaData.getColumnName(columna + 1));
          celda.setCellStyle(style);
        }
      }

      fila++;

      while (CursorConsulta.next())
      {
        HSSFRow loFila = loSheet.getRow(fila);
        if (loFila == null) loFila = loSheet.createRow(fila);

        for (columna = 0; columna < MetaData.getColumnCount(); columna++)
        {
          HSSFCell celda = loFila.getCell((short)columna);
          if (celda == null) celda = loFila.createCell((short)columna);
          if (MetaData.getColumnTypeName(columna + 1).toUpperCase().equals("DATE"))
          {
            if (CursorConsulta.getDate(columna + 1) != null)
              celda.setCellValue(CursorConsulta.getDate(columna + 1));
            
            else
              celda.setCellValue("");
          }
          else if ((MetaData.getColumnTypeName(columna + 1).toUpperCase().equals("INTEGER")) || 
                    (MetaData.getColumnTypeName(columna + 1).toUpperCase().equals("NUMBER")) || 
                    (MetaData.getColumnTypeName(columna + 1).toUpperCase().equals("DECIMAL")))
          {
            if (CursorConsulta.getString(columna + 1) != null)
              celda.setCellValue(CursorConsulta.getDouble(columna + 1));
          }
          else
            celda.setCellValue(CursorConsulta.getString(columna + 1));
        }
        fila++;
      }
      CursorConsulta.close();
      FileOutputStream fos = null;
int leido = 0;
String rutaArchivoNuevo = ruta;
File archivoNuevo = new File(rutaArchivoNuevo);
if(archivoNuevo.exists()){ archivoNuevo.delete();}
 else{ archivoNuevo.createNewFile();}
fos = new FileOutputStream(archivoNuevo);
BufferedOutputStream bos = new BufferedOutputStream(fos);       
      loWB.write(bos);
      bos.flush();
      bos.close();
      
FacesContext fc = FacesContext.getCurrentInstance();
FileInputStream inputS = new FileInputStream(archivoNuevo);
byte[] datos = new byte[1000];

if (!fc.getResponseComplete()) {
   String nombreArchivo = archivoNuevo.getName();
   String tipoContenido = "application/vnd.ms-excel";
   HttpServletResponse servletRespuesta =(HttpServletResponse) fc.getExternalContext().getResponse();

   servletRespuesta.setContentType(tipoContenido);
   servletRespuesta.setHeader("Content-Disposition",
                      "attachment;filename=\"" + nombreArchivo + "\"");
   ServletOutputStream salida = servletRespuesta.getOutputStream();

   while ((leido = inputS.read(datos)) != -1) {
        salida.write(datos, 0, leido);
   }

   salida.flush();
   salida.close();
   System.out.println("se descargo correctamente");
   fc.responseComplete();
}
    }
    catch (Exception ex)
    {
      System.out.println("\nse produjo error\n"+ex.getMessage());
    }
    rdao.cerrarConexion();
  }
}
