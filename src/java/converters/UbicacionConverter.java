/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import dao.UbicacionDao;
import dao.UbicacionDaoImplement;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import model.Ubicacion;

/**
 *
 * @author andre
 */
@FacesConverter("ubicacionConverter")
public class UbicacionConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null; 
        }
        UbicacionDao ubicacionlink = new UbicacionDaoImplement();
        Integer id = Integer.valueOf(value);
        
        Ubicacion u = ubicacionlink.buscarUbicacionById(id);
        return u;      
       
    }

   @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == "") {
            return "";
        }
        if (!(value instanceof Ubicacion)) {
            throw new ConverterException("Value is not a valid instance of Categoria.");
        }
        Integer id = ((Ubicacion) value).getIdUbicacion();
        return (id != null) ? id.toString() : "";        
    } 
    
}
