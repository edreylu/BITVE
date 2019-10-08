/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventos;


import acceso.AccesoControl;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
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
import org.primefaces.context.RequestContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import tiposevento.KTipoEvento;
import tiposevento.KTipoEventoDAO;
import ur.Ur;
import ur.UrDAO;
import utilerias.Mensaje;
import utilerias.Procedimiento;
import vehiculos.Vehiculo;
import vehiculos.VehiculoDAO;
@ManagedBean (name = "eventoControl")
@SessionScoped
public class EventoControl implements java.io.Serializable {
   @ManagedProperty(value = "#{accesoC}")
   private AccesoControl ac;
   private final EventoDAO evdao;
   private final KTipoEventoDAO tedao;
   private final VehiculoDAO vedao;
   private final UrDAO urdao;
   private List<Evento> eventos;
   private Evento evento;
   private List<Evento> filteredEvento;
   private List<Vehiculo> filteredVehiculo;
   private List<KTipoEvento> tiposevento;
   private List<Vehiculo> vehiculos;
   private EventoFotos imagen;
   private List<EventoFotos> imagenes;
   private List<Ur> urs;
   private Mensaje msg= new Mensaje();
   private Procedimiento pro= new Procedimiento();
   private int ur;
   private String nomUr;
   private String nomUrReporte;
   private int noVehiculoP;
   private String fechaP1,fechaP2;
   private StreamedContent pdf;
   private String mostrar;
   public EventoControl() {
      evdao = new EventoDAO();
      tedao = new KTipoEventoDAO();
      vedao = new VehiculoDAO();
      evento = new Evento();
      imagen = new EventoFotos();
      urdao = new UrDAO();
      
   }
   
   public void init(int noUr) {
        //eventos = evdao.traeRegistros(noUsuario);
        fechaP1="";
        fechaP2="";
        mostrar="";
        tiposevento=tedao.traeRegistros();
        urs=urdao.traeRegistros(ac.getNoUsuario());
        if(noUr==0){if(urs.isEmpty()){noUr=0;nomUr="";}else{noUr=urs.get(0).getNoUr();
        if(urs.size()>1){nomUr=null;}else{nomUr=urs.get(0).getUr();}
        nomUrReporte=urs.get(0).getUr();
        }}
        vehiculos=vedao.traeRegistrosActivosElegir(noUr);
        ur=noUr;
   }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public List<Evento> getFilteredEvento() {
        return filteredEvento;
    }

    public void setFilteredEvento(List<Evento> filteredEvento) {
        this.filteredEvento = filteredEvento;
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
    

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public List<KTipoEvento> getTiposevento() {
        return tiposevento;
    }

    public void setTiposevento(List<KTipoEvento> tiposevento) {
        this.tiposevento = tiposevento;
    }

    public List<EventoFotos> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<EventoFotos> imagenes) {
        this.imagenes = imagenes;
    }
    public List<Ur> getUrs() {
        return urs;
    }

    public void setUrs(List<Ur> urs) {
        this.urs = urs;
    }

    public int getUr() {
        return ur;
    }

    public void setUr(int ur) {
        this.ur = ur;
    }

    public String getNomUr() {
        return nomUr;
    }

    public void setNomUr(String nomUr) {
        this.nomUr = nomUr;
    }

    public EventoFotos getImagen() {
        return imagen;
    }

    public void setImagen(EventoFotos imagen) {
        this.imagen = imagen;
    }

    public int getNoVehiculoP() {
        return noVehiculoP;
    }

    public void setNoVehiculoP(int noVehiculoP) {
        this.noVehiculoP = noVehiculoP;
    }

    public String getFechaP1() {
        return fechaP1;
    }

    public void setFechaP1(String fechaP1) {
        this.fechaP1 = fechaP1;
    }

    public String getFechaP2() {
        return fechaP2;
    }

    public void setFechaP2(String fechaP2) {
        this.fechaP2 = fechaP2;
    }

    public String getNomUrReporte() {
        return nomUrReporte;
    }

    public void setNomUrReporte(String nomUrReporte) {
        this.nomUrReporte = nomUrReporte;
    }

    public AccesoControl getAc() {
        return ac;
    }

    public void setAc(AccesoControl ac) {
        this.ac = ac;
    }
    
    public StreamedContent getPdf(){
        
        return pdf;
    }

    public void setPdf(StreamedContent pdf) {
        this.pdf = pdf;
    }

    public String getMostrar() {
        return mostrar;
    }

    public void setMostrar(String mostrar) {
        this.mostrar = mostrar;
    }

    
    
    public void valueChange(ValueChangeEvent event)
			throws AbortProcessingException {
        filteredVehiculo=null;
	int noUr=(int) event.getNewValue();
        System.out.println("valueChange : "+noUr);
	vehiculos=vedao.traeRegistrosActivosElegir(noUr);
        ur=noUr;
        nomUrReporte=vehiculos.get(0).getUr();
        PrimeFaces.current().ajax().update(":formMostrar");
	}
    
    
    public void valueChangeTipoEvento(ValueChangeEvent event)
			throws AbortProcessingException {
        
	int noTipoEvento=(int) event.getNewValue();
        System.out.println("valueChange : "+noTipoEvento);
       switch (noTipoEvento) {
           case 9:
               mostrar="RV";
               break;
           case 5:
               mostrar="VV";
               break;
           default:
               mostrar="";
               break;
       }
	}
 
   public void CargaVentanaInserta(int ob){
    
        RequestContext context = RequestContext.getCurrentInstance();
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            filteredEvento=null;
            evento = new Evento();
            evento.setFecha(dateFormat.format(date));
            evento.setNoUr(ob);
            PrimeFaces.current().ajax().update(":formInsertar");
            PrimeFaces.current().executeScript("PF('dlginsertar').show();");
    }
   
   public void CargaVentanaEvento(int ob){
    
        RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("CargaVentanaEvento : "+ob);
            eventos = evdao.traeRegistrosEvento(ob);
            noVehiculoP=ob;
            PrimeFaces.current().ajax().update(":formEvento");
            PrimeFaces.current().executeScript("PF('dlgevento').show();");
    }
   
   public void CargaVentanaImagenes(int ob) throws IOException{
       
        pdf = evdao.getPDF(ob);
        PrimeFaces.current().executeScript("PF('dlgsubir').show();");
    }
   
   public void CargaVentanaParametros(int ob){
    
        RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("noVehiculoP : "+ob);
            noVehiculoP=ob;
            fechaP1="";
            fechaP2="";
            PrimeFaces.current().ajax().update(":formParametros");
            PrimeFaces.current().executeScript("PF('dlgparametros').show();");
    }
   public void cargaImagenes(int ob){
   imagenes = evdao.traeRegistrosImagenes(ob);
   }
   
   public void cargaEventos(int ob){
    
        RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("cargaEventos : "+ob);
            eventos = evdao.traeRegistrosEvento(ob);
            PrimeFaces.current().ajax().update(":formEvento");
            PrimeFaces.current().executeScript("PF('dlgevento').show();");
    }
  public void insertar() throws SQLException, ParseException{
           evento.setNoUsuarioOperacion(ac.getNoUsuario());
           evento.setFecha(formatoFecha(evento.getFecha()));
           evento.setFechaVencimiento(formatoFecha(evento.getFechaVencimiento()));
           evento.setNoVehiculo(noVehiculoP);
           System.out.println("usuario : "+ac.getNoUsuario());
           System.out.println("fecha : "+evento.getFecha());
           System.out.println("vehiculo : "+evento.getNoVehiculo());
           System.out.println("UR : "+evento.getNoUr());
           pro=evdao.operacionesEvento(evento,1);
           if(pro.getError()!=-1){
            msg.info("Procesado..", "Registro guardado");
               cargaEventos(noVehiculoP);
           }else{
            msg.warn("Error..", pro.getMensaje());
           }
           eventos = evdao.traeRegistrosEvento(evento.getNoVehiculo());
           PrimeFaces.current().executeScript("PF('dlginsertar').hide();");
        }
  
  public void modificar() throws SQLException, ParseException{
           System.out.println("valor a modificar: "+evento.getNoEvento());
           evento.setNoUsuarioOperacion(ac.getNoUsuario());
           evento.setFecha(formatoFecha(evento.getFecha()));
           evento.setFechaVencimiento(formatoFecha(evento.getFechaVencimiento()));
           evento.setNoVehiculo(noVehiculoP);
            System.out.println("usuario: "+ac.getNoUsuario());
            System.out.println("vehiculo: "+evento.getNoVehiculo());
             pro=evdao.operacionesEvento(evento,2);
             if(pro.getError()!=-1){
            msg.info("Procesado..", "Registro actualizado");
            cargaEventos(noVehiculoP);
             }else{
             msg.warn("Error", pro.getMensaje());
                
             }
            eventos = evdao.traeRegistrosEvento(evento.getNoVehiculo());
          PrimeFaces.current().executeScript("PF('dlgmodificar').hide();");
        }
  
      
       public void activardesactivar(int op) throws SQLException{
       System.out.println("valor: "+evento.getNoEvento());
       evento.setNoUsuarioOperacion(ac.getNoUsuario());
           pro=evdao.operacionesEvento(evento,op);
           if(pro.getError()!=-1){
               if(op==3){msg.info("Procesado..", "Registro inactivado");}
               else{msg.info("Procesado..", "Registro activado");}
           }else{
               if(op==3){msg.warn("Error..", pro.getMensaje());}
               else{msg.warn("Error..", pro.getMensaje());}
            
           }
            
            PrimeFaces.current().executeScript("PF('dlgactivardesactivar').hide();");
        }
       
       public void subirImagen(FileUploadEvent event) throws IOException {
        imagen = new EventoFotos();
        imagen.setNoEvento(evento.getNoEvento());
        imagen.setImagenByte(IOUtils.toByteArray(event.getFile().getInputstream()));
        System.out.println("valor noEvento: "+imagen.getNoEvento());
        boolean existe=evdao.existePdfEvento(imagen.getNoEvento());
        int valor=evdao.insertaImagen(imagen,existe);
        if(valor==1){
               msg.info("Procesado..", "PDF subido");
               //cargaImagenes(imagen.getNoEvento());
           }else{
               msg.warn("Error..", "No se subio PDF");
            
           }                      
    }
       
    public void eliminarImagen() throws SQLException, IOException{
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
       String evento = params.get("noEvento");
       int noEvento = Integer.parseInt(evento);
       System.out.println("valor noEvento: "+noEvento);
          int valor= evdao.eliminaImagen(noEvento);
           if(valor==1){
               msg.info("Procesado..", "PDF Eliminado");
               //cargaImagenes(noEvento);
           }else{
               msg.warn("Error..", "No se elimino PDF");
            
           }
        }
    
    public void cargaReporte() throws ParseException{
        System.out.println("eventos.EventoControl.cargaReporte()"+fechaP1);
        if(fechaP1.isEmpty() || fechaP2.isEmpty()){
        generaExcel(2);
        }else{
          fechaP1=formatoFecha(fechaP1);
          fechaP2=formatoFecha(fechaP2);
          System.out.println("eventos.EventoControl.cargaReporte()2"+fechaP1);
          generaExcel(3);
        }
        }
       
      public void generaExcel(int opcion)
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
     String ruta = path+"//resources//reportes//reportesModulo//REPORTE_BITVE.xls";
     String imagePath = path+"//resources//img//SEP.png";
     System.out.println("Ruta: "+ruta);
     if(opcion==3){
     consulta="select EV.NOEVENTO, \n" +
"       EV.NOVEHICULO, \n" +
"       VE.SERIE, \n" +
"       VE.PLACAS,\n" +
"       (select MARCA from kmarca where nomarca = VE.NOMARCA and estatus ='A') MARCA,\n" +
"       VE.TIPO,\n" +
"       EV.DESCRIPCION DESCRIPCION_EVENTO, \n" +
"       TO_CHAR(EV.FECHA,'DD/MM/YYYY') FECHA,EV.PROVEEDOR,\n" +
"       EV.IMPORTE,\n" +
"       EV.NUMFACTURA,\n" +
"       EV.NOTIPOEVENTO,\n" +
"(select KTE.TIPOEVENTO from KTIPOEVENTO kte where KTE.NOTIPOEVENTO = EV.NOTIPOEVENTO) TIPOEVENTO\n" +
"from EVENTO EV,\n" +
"     VEHICULO VE\n" +
"     where EV.NOVEHICULO = VE.NOVEHICULO\n" +
"     AND VE.NOVEHICULO = "+noVehiculoP+" AND EV.FECHA BETWEEN '"+fechaP1+"' AND '"+fechaP2+"'";
     } else if(opcion==2){
     consulta="select EV.NOEVENTO, \n" +
"       EV.NOVEHICULO, \n" +
"       VE.SERIE, \n" +
"       VE.PLACAS,\n" +
"       (select MARCA from kmarca where nomarca = VE.NOMARCA and estatus ='A') MARCA,\n" +
"       VE.TIPO,\n" +
"       EV.DESCRIPCION DESCRIPCION_EVENTO, \n" +
"       TO_CHAR(EV.FECHA,'DD/MM/YYYY') FECHA,EV.PROVEEDOR,\n" +
"       EV.IMPORTE,\n" +
"       EV.NUMFACTURA,\n" +
"       EV.NOTIPOEVENTO,\n" +
"(select KTE.TIPOEVENTO from KTIPOEVENTO kte where KTE.NOTIPOEVENTO = EV.NOTIPOEVENTO) TIPOEVENTO\n" +
"from EVENTO EV,\n" +
"     VEHICULO VE\n" +
"     where EV.NOVEHICULO = VE.NOVEHICULO\n" +
"     AND VE.NOVEHICULO = "+noVehiculoP+" ";
     }
     else{
     consulta="select EV.NOEVENTO, \n" +
"       EV.NOVEHICULO, \n" +
"       VE.SERIE, \n" +
"       VE.PLACAS,\n" +
"       (select MARCA from kmarca where nomarca = VE.NOMARCA and estatus ='A') MARCA,\n" +
"       VE.TIPO,\n" +
"       EV.DESCRIPCION DESCRIPCION_EVENTO, \n" +
"       TO_CHAR(EV.FECHA,'DD/MM/YYYY') FECHA,EV.PROVEEDOR,\n" +
"       EV.IMPORTE,\n" +
"       EV.NUMFACTURA,\n" +
"       EV.NOTIPOEVENTO,\n" +
"(select KTE.TIPOEVENTO from KTIPOEVENTO kte where KTE.NOTIPOEVENTO = EV.NOTIPOEVENTO) TIPOEVENTO\n" +
"from EVENTO EV,\n" +
"     VEHICULO VE\n" +
"     where EV.NOVEHICULO = VE.NOVEHICULO\n" +
"     AND VE.NOUR ="+ur;
     }
    try
    {
      POIFSFileSystem loFS = new POIFSFileSystem(new FileInputStream(Plantilla));
      HSSFWorkbook loWB = new HSSFWorkbook(loFS);
      HSSFCellStyle style = loWB.createCellStyle();
      HSSFCellStyle style2 = loWB.createCellStyle();
      HSSFSheet loSheet = loWB.getSheet("Hoja1");
      fila = 5;
      //evdao.imagenReporte();
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
        loCell.setCellValue("REPORTE DE EVENTOS DE UR: "+nomUrReporte);
        
        HSSFRow loFila2 = loSheet.getRow(fila - 1);
        if (loFila2 == null) loFila2 = loSheet.createRow(fila - 1);
        HSSFCell loCell2 = loFila2.getCell((short)0);
        if (loCell2 == null) loFila.createCell((short)0);
        loCell2.setCellStyle(style2);
        loCell2.setCellValue("USUARIO: "+ac.getClave());
      }
      
        
      CursorConsulta = evdao.getConsulta(consulta);
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
    fechaP1="";
    fechaP2="";
    evdao.cerrarConexion();
  }
   public String formatoFecha(String fecha) throws ParseException{
           DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy",Locale.ENGLISH);
           Date date = formatter.parse(fecha);
           Calendar cal = Calendar.getInstance();
           cal.setTime(date);
           String resultado = cal.get(Calendar.DATE)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR);
   return resultado;
   }
        public void cancelarActualizar(){PrimeFaces.current().executeScript("PF('dlgmodificar').hide();");}
        public void cancelarEliminar(){PrimeFaces.current().executeScript("PF('dlgeliminar').hide();");}
        public void cancelarActivarDesactivar(){PrimeFaces.current().executeScript("PF('dlgactivardesactivar').hide();");}
        
}
