/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import dao.MedicionTrabajoDaoImplement;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import model.MedicionTrabajo;

/**
 *
 * @author Jotamarios
 */
@FacesConverter("medicionTrabajoConverter")
public class MedicionTrabajoConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null; 
        }
        MedicionTrabajoDaoImplement mtdi = new MedicionTrabajoDaoImplement();

        Integer id = Integer.valueOf(value);
        MedicionTrabajo u = mtdi.buscarbyId(id);
        return u;

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == "") {
            return "";
        }

        if (!(value instanceof MedicionTrabajo)) {
            throw new ConverterException("Value is not a valid instance of MedicionTrabajo.");
        }
        Integer id = ((MedicionTrabajo) value).getIdMedicionTrabajo();
        return (id != null) ? id.toString() : "";
    }
}
