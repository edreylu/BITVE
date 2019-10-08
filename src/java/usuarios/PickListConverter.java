/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuarios;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;
import ur.Ur;

/**
 *
 * @author usuario
 */
@FacesConverter(value = "usuConverter")
public class PickListConverter implements Converter {
  @Override
  public Object getAsObject(FacesContext fc, UIComponent comp, String value) {
      DualListModel<Ur> model = (DualListModel<Ur>) ((PickList) comp).getValue();
      for (Ur ur : model.getSource()) {
          if (ur.getUr().equals(value)) {
              return ur;
          }
      }
      for (Ur ur : model.getTarget()) {
          if (ur.getUr().equals(value)) {
              return ur;
          }
      }
      return null;
  }

  @Override
  public String getAsString(FacesContext fc, UIComponent comp, Object value) {
      return ((Ur) value).getUr();
  }

    
}
