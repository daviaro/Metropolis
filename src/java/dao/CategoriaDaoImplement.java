/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Categoria;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author chris
 */
public class CategoriaDaoImplement implements CategoriaDao{
    
    private Logger logger = LogManager.getLogger(CategoriaDaoImplement.class);

    @Override
    public List<Categoria> mostrarCategorias() {
        
        logger.info("Se incia  ejecucion del metodo mostrarCategorias");
        Session session= HibernateUtil.getSessionFactory().getCurrentSession();
        List<Categoria> lista=null;
        String sql = "FROM Categoria";
        logger.debug("Consulta a categoria "+sql);
        try{
            logger.info("Se incia transaccion ");
            session.beginTransaction();
            lista = session.createQuery(sql).list();
            for(Categoria categoria:lista){
                logger.debug("Listado de Catergoria {} ", categoria.getNombre());
            }
            logger.info("Se obtiene lista de categorias. Tamaño {} ",lista.size());
            session.getTransaction().commit();
        }catch(HibernateException e){
            session.getTransaction().rollback();    
            logger.error("Error mostrarCaterogiras [{}] ",e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean insertarCategoria(Categoria categoria) {
        boolean flag;
        Transaction trans=null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            trans = session.beginTransaction();
            session.save(categoria);
            session.getTransaction().commit();
            flag=true;
        }catch(RuntimeException e){
            if(trans!=null){
                trans.rollback();
        
            }
            e.printStackTrace();
            flag=false;
        }finally{
            session.flush();
            session.close();
        }
        return flag;
    }

    @Override
    public boolean modificarCategoria(Categoria categoria) {
        boolean flag;
        Transaction trans = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            trans = session.beginTransaction();
            session.update(categoria);
            session.getTransaction().commit();
            flag = true;
        }catch(RuntimeException e){
            if (trans != null) {
                trans.rollback();
            }
            e.printStackTrace();
            flag = false;
        }finally {
            session.flush();
            session.close();
        }
        return flag;
    }

    @Override
    public boolean eliminarCategoria(Integer idcategoria) {
       boolean flag;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            session.beginTransaction();
            Categoria categoria = (Categoria) session.load(Categoria.class, idcategoria);
            session.delete(categoria);
            session.getTransaction().commit();
            flag=true;
        }catch(HibernateException e){
            flag=false;
            session.getTransaction().rollback();
        }
        return flag;
    }

    @Override
    public Categoria buscarCategoria(Categoria categoria) {
        Categoria model=null;
        Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM Categoria WHERE nombre ='"+categoria.getNombre()+"'";
        try{
            sesion.beginTransaction();
            model = (Categoria) sesion.createQuery(sql).uniqueResult();
            sesion.beginTransaction().commit();
        }catch(Exception e){
            sesion.beginTransaction().rollback();
        }
        return model;
    }
    
    @Override
    public List<Categoria> mostrarCategoriasPrincipales() {
        
        logger.info("Se incia  ejecucion del metodo mostrarCategoriasPrincipales");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Categoria> lista = null;
        String sql = "FROM Categoria c WHERE categoria = null ";
        logger.debug("Consulta a categoria "+sql);
               // + "WHERE categoria = null";
        try {
            logger.info("Se incia transaccion ");
            session.beginTransaction();
            lista = session.createQuery(sql).list();
            for(Categoria categoria:lista){
                logger.debug("Listado de Catergoria {} ", categoria.getNombre());
            }
            logger.info("Se obtiene lista de categorias. Tamaño {} ",lista.size());
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            logger.error("Error mostrarCaterogirasPrincipales [{}] ",e.getMessage());
        }
        return lista;
    }
    
    @Override
    public List<Categoria> mostrarSubCategorias() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Categoria> lista = null;
        String sql = "FROM Categoria c WHERE c.categoria is not null";
        try {
            session.beginTransaction();
            lista = session.createQuery(sql).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        return lista;
    }

    @Override
    public List<Categoria> buscarSubCategoriasByCategoria(Integer idCategoria) {
        List<Categoria> categorias = null;
        Session session = null;
        //Se crea un try catch para hacer la consulta
        String sql = "from Categoria where categoria='" + idCategoria+ "'";
        try {
            //Se recupera la session actual
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicializo transaccion
            session.beginTransaction();
            Query query = session.createQuery(sql);
            categorias = (List<Categoria>) query.list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            session.getTransaction().rollback();
        }
        return categorias;
    }

    @Override
    public Categoria buscarbyId(int idCat) {
        Categoria model=null;
        Session session = null;
        String sql = "FROM Categoria WHERE idCategoria ='"+idCat+"'";
        try {
            //Se recupera la session actual
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicializo transaccion
            session.beginTransaction();
            Query query = session.createQuery(sql);
            query.setMaxResults(1);
            //uniqueResult() para que sea solo un solo resultado
            model = (Categoria) query.uniqueResult();
            model.getTrabajos().size();
            model.getEtiquetas().size();
            model.getCategorias().size();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            session.getTransaction().rollback();
        }
        return model;
    }
}
