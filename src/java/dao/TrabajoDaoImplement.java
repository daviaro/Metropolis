/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Trabajo;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author andre
 */
public class TrabajoDaoImplement implements TrabajoDao {

    @Override
    public List<Trabajo> mostrarTrabajos() {
        Session session = null;
        List<Trabajo> lista = null;
        //Se crea un try catch para hacer la consulta
        String sql = "from Trabajo";
        try {
            //Se recupera la session actual
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicializo transaccion
            session.beginTransaction();
            Query query = session.createQuery(sql);
            lista = (List<Trabajo>) query.list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            session.getTransaction().rollback();
        }
        return lista;
    }

    @Override
    public List<Trabajo> buscarTrabajosByCategoriaId(Integer idCategoria) {
        List<Trabajo> trabajos = null;
        Session session = null;
        //Se crea un try catch para hacer la consulta
        String sql = "from Trabajo where categoria='" + idCategoria+ "'";
        try {
            //Se recupera la session actual
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicializo transaccion
            session.beginTransaction();
            Query query = session.createQuery(sql);
            trabajos = (List<Trabajo>) query.list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            session.getTransaction().rollback();
        }
        return trabajos;
    }

    @Override
    public Trabajo buscarTrabajoById(Integer idTrabajo) {
        Trabajo trabajo = null;
        Session session = null;
        //Se crea un try catch para hacer la consulta
        String sql = "from Trabajo where idTrabajo = '" + idTrabajo+ "'";
        try {
            //Se recupera la session actual
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicializo transaccion
            session.beginTransaction();
            Query query = session.createQuery(sql);
            query.setMaxResults(1);
            //uniqueResult() para que sea solo un solo resultado
            trabajo = (Trabajo) query.uniqueResult();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            session.getTransaction().rollback();
        }
        return trabajo;
    }

}
