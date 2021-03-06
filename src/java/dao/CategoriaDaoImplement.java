/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Categoria;
import model.Trabajo;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author chris
 */
public class CategoriaDaoImplement implements CategoriaDao {

    @Override
    public List<Categoria> mostrarCategorias() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Categoria> lista = null;
        String sql = "FROM Categoria";
        try {
            session.beginTransaction();
            lista = session.createQuery(sql).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean insertarCategoria(Categoria categoria) {
        boolean flag;
        Transaction trans = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            session.save(categoria);
            session.getTransaction().commit();
            flag = true;
        } catch (RuntimeException e) {
            if (trans != null) {
                trans.rollback();

            }
            e.printStackTrace();
            flag = false;
        } finally {
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
        try {
            trans = session.beginTransaction();
            session.update(categoria);
            session.getTransaction().commit();
            flag = true;
        } catch (RuntimeException e) {
            if (trans != null) {
                trans.rollback();
            }
            e.printStackTrace();
            flag = false;
        } finally {
            session.flush();
            session.close();
        }
        return flag;
    }

    @Override
    public boolean eliminarCategoria(Integer idcategoria) {
        boolean flag;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            Categoria categoria = (Categoria) session.load(Categoria.class, idcategoria);
            session.delete(categoria);
            session.getTransaction().commit();
            flag = true;
        } catch (HibernateException e) {
            flag = false;
            if (session != null) {
                session.getTransaction().rollback();
            }
        }
        return flag;
    }

    @Override
    public Categoria buscarCategoria(Categoria categoria) {
        Categoria model = null;
        Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM Categoria WHERE nombre ='" + categoria.getNombre() + "'";
        try {
            sesion.beginTransaction();
            model = (Categoria) sesion.createQuery(sql).uniqueResult();
            sesion.beginTransaction().commit();
        } catch (Exception e) {
            if (sesion != null) {
                sesion.beginTransaction().rollback();
            }
        }
        return model;
    }
    
    //@Override
    public Categoria buscarCategoria(Trabajo tr) {
        Categoria categoria = null;
        Trabajo trabajo = null;
        Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM Trabajo t WHERE idTrabajo ='" + tr.getIdTrabajo() + "'";
        try {
            sesion.beginTransaction();
            trabajo = (Trabajo) sesion.createQuery(sql).uniqueResult();
            sesion.beginTransaction().commit();
            int idTrabajo =trabajo.getCategoria().getIdCategoria();
            sesion.close();
            categoria = this.buscarbyId(idTrabajo);
            
        } catch (Exception e) {
            if (sesion != null) {
                sesion.beginTransaction().rollback();
            }
        }
        return categoria;
    }

    @Override
    public List<Categoria> mostrarCategoriasPrincipales() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Categoria> lista = null;
        String sql = "FROM Categoria c WHERE categoria = null";
        try {
            session.beginTransaction();
            lista = session.createQuery(sql).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
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
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        }
        return lista;
    }

    @Override
    public List<Categoria> buscarSubCategoriasByCategoria(Integer idCategoria) {
        List<Categoria> categorias = null;
        Session session = null;
        //Se crea un try catch para hacer la consulta
        String sql = "from Categoria where categoria='" + idCategoria + "'";
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
            if (session != null) {
                session.getTransaction().rollback();
            }
        }
        return categorias;
    }

    @Override
    public Categoria buscarbyId(int idCat) {
        Categoria model = null;
        Session session = null;
        String sql = "FROM Categoria WHERE idCategoria ='" + idCat + "'";
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
            if (session != null) {
                session.getTransaction().rollback();
            }
        }
        return model;
    }
}
