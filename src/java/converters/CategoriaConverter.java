/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;


import dao.CategoriaDao;
import dao.CategoriaDaoImplement;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import model.Categoria;

/**
 *
 * @author andre
 */
@FacesConverter("categoriaConverter")
public class CategoriaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null; 
        }
        CategoriaDao catlink = new CategoriaDaoImplement();

        Integer id = Integer.valueOf(value);
        Categoria u = catlink.buscarbyId(id);
        return u;

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == "") {
            return "";
        }

        if (!(value instanceof Categoria)) {
            throw new ConverterException("Value is not a valid instance of Categoria.");
        }
        Integer id = ((Categoria) value).getIdCategoria();
        return (id != null) ? id.toString() : "";
    }

}
