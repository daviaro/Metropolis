/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Etiqueta;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author andre
 */
public class EtiquetaDaoImplement implements EtiquetaDao {

    @Override
    public List<Etiqueta> findAll() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Etiqueta> lista = null;
        String sql = "SELECT e FROM Etiqueta e INNER JOIN FETCH e.categoria";
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
    public boolean modificarEtiqueta(Etiqueta selectedEtiqueta) {
        boolean flag;
        Transaction trans = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            session.update(selectedEtiqueta);
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
    public boolean insertarEtiqueta(Etiqueta createEtiqueta) {
        boolean flag;
        Transaction trans = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            session.save(createEtiqueta);
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

}
