/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import dao.CotizacionDao;
import dao.CotizacionDaoImplement;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import model.Cotizacion;
@FacesConverter("cotizacionConverter")
public class CotizacionConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                CotizacionDao cotizacionlink = new CotizacionDaoImplement();
                int idCoti = Integer.valueOf(value);
                Cotizacion c = cotizacionlink.buscarCotizacionById(idCoti);
                
                return c;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return null;
        } else {
            return String.valueOf(((Cotizacion) value).getIdCotizacion());
        }
    }

}
