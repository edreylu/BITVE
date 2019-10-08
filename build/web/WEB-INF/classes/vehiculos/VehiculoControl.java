/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehiculos;


import acceso.AccesoControl;
import catalogos.Marca;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.primefaces.context.RequestContext;
import org.primefaces.PrimeFaces;
import ur.Ur;
import ur.UrDAO;
import utilerias.Mensaje;
import utilerias.Procedimiento;
@ManagedBean (name = "vehiculoControl")
@SessionScoped
public class VehiculoControl implements java.io.Serializable {
   @ManagedProperty(value = "#{accesoC}")
   private AccesoControl ac;
   private final VehiculoDAO vedao;
   private final UrDAO urdao;
   private List<Vehiculo> vehiculos;
   private Vehiculo vehiculo;
   private List<Vehiculo> filteredVehiculo;
   private List<Ur> urs;
   private List<Marca> marcas;
   private Mensaje msg= new Mensaje();
   private Procedimiento pro= new Procedimiento();
   private boolean veh;
   public VehiculoControl() {
      vedao = new VehiculoDAO();
      urdao = new UrDAO();
      vehiculo = new Vehiculo();
   }
   
   public void init() {
        vehiculos = vedao.traeRegistrosInactivos();
        urs=urdao.traeRegistrosC();
        marcas=vedao.traeRegistrosMarca();
        veh=false;
   }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public List<Vehiculo> getFilteredVehiculo() {
        return filteredVehiculo;
    }

    public void setFilteredVehiculo(List<Vehiculo> filteredVehiculo) {
        this.filteredVehiculo = filteredVehiculo;
    }

    public Mensaje getMsg() {
        return msg;
    }

    public void setMsg(Mensaje msg) {
        this.msg = msg;
    }

    public Procedimiento getPro() {
        return pro;
    }

    public void setPro(Procedimiento pro) {
        this.pro = pro;
    }

    public List<Ur> getUrs() {
        return urs;
    }

    public void setUrs(List<Ur> urs) {
        this.urs = urs;
    }

    public boolean isVeh() {
        return veh;
    }

    public void setVeh(boolean veh) {
        this.veh = veh;
    }

    public AccesoControl getAc() {
        return ac;
    }

    public void setAc(AccesoControl ac) {
        this.ac = ac;
    }

    public List<Marca> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<Marca> marcas) {
        this.marcas = marcas;
    }
    
    
   
   public void CargaVentanaInserta(String ob){
    
        RequestContext context = RequestContext.getCurrentInstance();
            filteredVehiculo=null;
            vehiculo = new Vehiculo();
            PrimeFaces.current().ajax().update(":formInsertar");
            PrimeFaces.current().executeScript("PF('dlginsertar').show();");
    }
   
  public void insertar() throws SQLException, ParseException{
           int valor=0;
           vehiculo.setNoUsuarioOperacion(ac.getNoUsuario());
           vehiculo.setNoVehiculo(0);
           System.out.println("usuario : "+ac.getNoUsuario());
           pro=vedao.operacionesVehiculo(vehiculo,1);
           if(pro.getError()!=-1){
            msg.info("Procesado..", "Registro guardado");
           }else{
            msg.warn("Error..", pro.getMensaje());
           }
           init();
           PrimeFaces.current().executeScript("PF('dlginsertar').hide();");
        }
  
  public void modificar() throws SQLException{
           int valor;
           System.out.println("valor a modificar: "+vehiculo.getNoVehiculo());
           vehiculo.setNoUsuarioOperacion(ac.getNoUsuario());
            System.out.println("usuario: "+ac.getNoUsuario());
             pro=vedao.operacionesVehiculo(vehiculo,2);
             if(pro.getError()!=-1){
            msg.info("Procesado..", "Registro actualizado");
             }else{
             msg.warn("Error", pro.getMensaje());
                
             }
            init();
          PrimeFaces.current().executeScript("PF('dlgmodificar').hide();");
        }
  
      public void eliminar() throws SQLException{
       int valor;
       System.out.println("valor a eliminar: "+vehiculo.getNoVehiculo());
       vehiculo.setNoUsuarioOperacion(ac.getNoUsuario());
           pro=vedao.operacionesVehiculo(vehiculo,3);
           if(pro.getError()!=-1){
            msg.info("Procesado..", "Registro dado de Baja");
           }else{
            msg.warn("Error..", pro.getMensaje());
            
           }
            init();
            PrimeFaces.current().executeScript("PF('dlgeliminar').hide();");
        }
      
       public void activardesactivar(int op) throws SQLException{
       int valor;
       System.out.println("valor: "+vehiculo.getNoVehiculo());
       vehiculo.setNoUsuarioOperacion(ac.getNoUsuario());
           pro=vedao.operacionesVehiculo(vehiculo,op);
           if(pro.getError()!=-1){
               if(op==3){msg.info("Procesado..", "Registro inactivado");}
               else{msg.info("Procesado..", "Registro activado");}
           }else{
               if(op==3){msg.warn("Error..", pro.getMensaje());}
               else{msg.warn("Error..", pro.getMensaje());}
            
           }
            init();
            PrimeFaces.current().executeScript("PF('dlgactivardesactivar').hide();");
        }
      
       public void postProcessXLS(Object document) {

        String cabecera = "REPORTE VEHICULOS";
      
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        sheet.shiftRows(0, sheet.getLastRowNum(), 1);
        HSSFCell cell = sheet.createRow(0).createCell(0);
        cell.setCellValue(cabecera);

        HSSFFont font = wb.createFont();

        HSSFCellStyle style = wb.createCellStyle();
        style.setFont(font);

        cell.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, sheet.getRow(1).getLastCellNum() - 1));
        
        int i = 2;
        Row row = null;
            row = sheet.createRow(1);
            cell = (HSSFCell) row.createCell(0);
            cell.setCellValue("NO VEHICULO");
            cell = (HSSFCell) row.createCell(1);
            cell.setCellValue("SERIE");
            cell = (HSSFCell) row.createCell(2);
            cell.setCellValue("MARCA");
            cell = (HSSFCell) row.createCell(3);
            cell.setCellValue("PLACA");
            cell = (HSSFCell) row.createCell(4);
            cell.setCellValue("ESTATUS");
        for (Vehiculo asp : vehiculos) {
            row = sheet.createRow(i);
            cell = (HSSFCell) row.createCell(0);
            cell.setCellValue(asp.getNoVehiculo());
            cell = (HSSFCell) row.createCell(1); 
            cell.setCellValue(asp.getSerie());
            cell = (HSSFCell) row.createCell(2); 
            cell.setCellValue(asp.getNoMarca());
            cell = (HSSFCell) row.createCell(3); 
            cell.setCellValue(asp.getPlacas());
            cell = (HSSFCell) row.createCell(4); 
            cell.setCellValue(asp.getEstatus());
            
        i++;
        }
      
    }

        
      public void generaExcel()
  {
    ResultSet CursorConsulta;
    String consulta;
    int columna = 0;
    int fila = 0;
    String Plantilla = "";
    String TituloEncabezado = "S";
    String TituloColumna = "S";
    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        String path = context.getRealPath("/");
        String realPath = path+"//resources//reportes//REPORTE_BITVE.xls";
        Plantilla = realPath;
     String ruta = path+"//resources//reportes//reportesModulo//REPORTE_VEHICULOS.xls";
     String imagePath = path+"//resources//img//SEP.png";
     System.out.println("Ruta: "+ruta);
     consulta="select VE.NOVEHICULO,VE.SERIE,VE.PLACAS,(select MARCA from kmarca where nomarca = VE.NOMARCA and estatus ='A') MARCA,"
             + " VE.TIPO,VE.COLOR, "
             + "   (SELECT MAX(NVL(EV.KM,0))\n" +
"                              FROM   Evento EV\n" +
"                              WHERE  EV.NoVehiculo  = VE.NOVEHICULO \n" +
"                              AND    EV.Estatus    != 'I') KM," +
"  VE.NOUR,UR.UR,DECODE(VE.ESTATUS,'A','ACTIVO','INACTIVO') ESTATUS\n"+
             
"from VEHICULO VE,\n" +
"     UR UR\n" +
"where VE.NOUR = UR.NOUR";
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
        HSSFRow loFila = loSheet.getRow(fila - 2);
        if (loFila == null) loFila = loSheet.createRow(fila - 2);
        HSSFCell loCell = loFila.getCell((short)0);
        if (loCell == null) loCell = loFila.createCell((short)0);
        style2.setAlignment(style2.ALIGN_CENTER);
        loCell.setCellStyle(style2);
        loCell.setCellValue("REPORTE DE VEHICULOS");
        
        HSSFRow loFila2 = loSheet.getRow(fila - 1);
        if (loFila2 == null) loFila2 = loSheet.createRow(fila - 1);
        HSSFCell loCell2 = loFila2.getCell((short)0);
        if (loCell2 == null) loFila.createCell((short)0);
        loCell2.setCellStyle(style2);
        loCell2.setCellValue("USUARIO: "+ac.getClave());
      }

      CursorConsulta = vedao.getConsulta(consulta);
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
    vedao.cerrarConexion();
  }
        public void cancelarActualizar(){PrimeFaces.current().executeScript("PF('dlgmodificar').hide();");}
        public void cancelarEliminar(){PrimeFaces.current().executeScript("PF('dlgeliminar').hide();");}
        public void cancelarActivarDesactivar(){PrimeFaces.current().executeScript("PF('dlgactivardesactivar').hide();");}
        
    
   
   
  
}
