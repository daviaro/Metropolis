/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Categoria;

/**
 *
 * @author chris
 */
public interface CategoriaDao {
    public List<Categoria> mostrarCategorias();
    public List<Categoria> mostrarCategoriasPrincipales();
    public List<Categoria> mostrarSubCategorias();
    public Categoria buscarCategoria(Categoria categoria);
    public boolean insertarCategoria(Categoria categoria);
    public boolean modificarCategoria(Categoria categoria);
    public boolean eliminarCategoria(Integer idcategoria);    
    public List<Categoria> buscarSubCategoriasByCategoria(Integer idCategoria);
    public Categoria buscarbyId(int idCat);
}
