
package roles;

import java.io.Serializable;

/**
 *
 * @author usuario
 */
public class Roles implements Serializable {
    private String insertar;
    private String actualizar;
    private String eliminar;
    private String consultar;
    private String descargar;
    private Boolean insertarSel;
    private Boolean actualizarSel;
    private Boolean eliminarSel;
    private Boolean consultarSel;
    private Boolean insertarSelI;
    private Boolean actualizarSelI;
    private Boolean eliminarSelI;
    private Boolean consultarSelI;
    private String clavebuscada;
   
    private int noRol;
    private String descripcion;
    private String nombreinsertar;

    public Roles() {
    }

    public int getNoRol() {
        return noRol;
    }

    public void setNoRol(int noRol) {
        this.noRol = noRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.toUpperCase();
    }

    

    public String getInsertar() {
        return insertar;
    }

    public void setInsertar(String insertar) {
        this.insertar = insertar;
    }

    public String getActualizar() {
        return actualizar;
    }

    public void setActualizar(String actualizar) {
        this.actualizar = actualizar;
    }

    public String getEliminar() {
        return eliminar;
    }

    public void setEliminar(String eliminar) {
        this.eliminar = eliminar;
    }

    public String getConsultar() {
        return consultar;
    }

    public void setConsultar(String consultar) {
        this.consultar = consultar;
    }

    public String getDescargar() {
        return descargar;
    }

    public void setDescargar(String descargar) {
        this.descargar = descargar;
    }
   
    
    public Boolean getInsertarSel() {
        return insertarSel;
    }

    public void setInsertarSel(Boolean insertarSel) {
        this.insertarSel = insertarSel;
    }

    public Boolean getActualizarSel() {
        return actualizarSel;
    }

    public void setActualizarSel(Boolean actualizarSel) {
        this.actualizarSel = actualizarSel;
    }

    public Boolean getEliminarSel() {
        return eliminarSel;
    }

    public void setEliminarSel(Boolean eliminarSel) {
        this.eliminarSel = eliminarSel;
    }

    public Boolean getConsultarSel() {
        return consultarSel;
    }

    public void setConsultarSel(Boolean consultarSel) {
        this.consultarSel = consultarSel;
    }

    public String getNombreinsertar() {
        return nombreinsertar;
    }

    public void setNombreinsertar(String nombreinsertar) {
        this.nombreinsertar = null != nombreinsertar ? nombreinsertar.toUpperCase() : null;
    }

    public String getClavebuscada() {
        return clavebuscada;
    }

    public void setClavebuscada(String clavebuscada) {
        this.clavebuscada = null != clavebuscada ? clavebuscada.toUpperCase() : null;
    }

    public Boolean getInsertarSelI() {
        return insertarSelI;
    }

    public void setInsertarSelI(Boolean insertarSelI) {
        this.insertarSelI = insertarSelI;
    }

    public Boolean getActualizarSelI() {
        return actualizarSelI;
    }

    public void setActualizarSelI(Boolean actualizarSelI) {
        this.actualizarSelI = actualizarSelI;
    }

    public Boolean getEliminarSelI() {
        return eliminarSelI;
    }

    public void setEliminarSelI(Boolean eliminarSelI) {
        this.eliminarSelI = eliminarSelI;
    }

    public Boolean getConsultarSelI() {
        return consultarSelI;
    }

    public void setConsultarSelI(Boolean consultarSelI) {
        this.consultarSelI = consultarSelI;
    }
    
    
    
}
