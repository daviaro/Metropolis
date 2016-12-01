/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Iterator;
import java.util.List;
import model.Ubicacion;
import model.Usuario;
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
public class UbicacionDaoImplement implements UbicacionDao {

    
     private Logger logger = LogManager.getLogger(UbicacionDaoImplement.class);
    
    @Override
    public List<Ubicacion> mostrarUbicaciones() {
        logger.info("Ingresando a la operacione mostrarUbicaciones");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Ubicacion> lista = null;
        String sql = "FROM Ubicacion";
        logger.info("Script de consulta HQL: ["+sql+"]");
        try {
            session.beginTransaction();
            lista = session.createQuery(sql).list();
            logger.info("Total registros: [{}]",lista.size());
            
            for(Ubicacion ubc:lista){
                logger.debug("Objeto {} - {} ",ubc.getIdUbicacion().intValue(),ubc.getBarrio());
            }
            
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            logger.error("Error. mostrarUbicaciones:[{}]",e.getMessage());
        }
        return lista;
    }

    @Override
    public Ubicacion buscarUbicacion(Ubicacion ubicacion) {
        Ubicacion model = null;
        Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM Ubicacion WHERE nombre ='" + ubicacion.getBarrio() + "'";
        try {
            sesion.beginTransaction();
            model = (Ubicacion) sesion.createQuery(sql).uniqueResult();
            sesion.beginTransaction().commit();
        } catch (Exception e) {
            sesion.beginTransaction().rollback();
        }
        return model;
    }

    @Override
    public boolean insertarUbicacion(Ubicacion ubicacion) {
        boolean flag;
        Transaction trans = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            session.save(ubicacion);
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
    public boolean modificarUbicacion(Ubicacion ubicacion) {
        boolean flag;
        Transaction trans = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            session.update(ubicacion);
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
    public boolean eliminarUbicacion(Integer idubicacion) {
        boolean flag;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            Ubicacion ubicacion = (Ubicacion) session.load(Ubicacion.class, idubicacion);
            session.delete(ubicacion);
            session.getTransaction().commit();
            flag = true;
        } catch (HibernateException e) {
            flag = false;
            session.getTransaction().rollback();
        }
        return flag;
    }

    @Override
    public Ubicacion buscarUbicacionById(Integer idUbicacion) {
        Ubicacion ubicacion = null;
        Session session = null;
        //Se crea un try catch para hacer la consulta
        String sql = "SELECT DISTINCT u from Ubicacion u  LEFT JOIN FETCH u.usuarios where idUbicacion = '" + idUbicacion + "'";
        try {
            //Se recupera la session actual
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicializo transaccion
            session.beginTransaction();
            Query query = session.createQuery(sql);
            query.setMaxResults(1);
            //uniqueResult() para que sea solo un solo resultado
            ubicacion = (Ubicacion) query.uniqueResult();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            session.getTransaction().rollback();
        }
        return ubicacion;
    }
    
}
