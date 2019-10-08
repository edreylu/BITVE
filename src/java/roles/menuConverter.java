/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roles;

import formas.FormasMenu;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

/**
 *
 * @author usuario
 */


@FacesConverter(value = "menuConverter")
public class menuConverter implements Converter {
  @Override
 public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        PickList  p = (PickList) component;
        DualListModel dl = (DualListModel) p.getValue();
        return dl.getSource().get(Integer.valueOf(submittedValue));
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        PickList  p = (PickList) component;
        DualListModel dl = (DualListModel) p.getValue();
        return  String.valueOf(dl.getSource().indexOf(value));
    }

}