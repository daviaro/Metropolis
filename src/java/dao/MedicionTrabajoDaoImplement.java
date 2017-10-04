/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.MedicionTrabajo;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author Jotamarios
 */
public class MedicionTrabajoDaoImplement {

    public MedicionTrabajoDaoImplement() {

    }

    public List<MedicionTrabajo> mostrarMedidionTrabjo() {
        Session session = null;
        List<MedicionTrabajo> lista = null;
        String sql = "from MedicionTrabajo";
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(sql);
            lista = (List<MedicionTrabajo>) query.list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        }
        return lista;
    }

    public MedicionTrabajo buscarbyId(int idMedTrabajo) {
        MedicionTrabajo model = null;
        Session session = null;
        String sql = "FROM MedicionTrabajo WHERE idMedicionTrabajo ='" + idMedTrabajo + "'";
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(sql);
            query.setMaxResults(1);
            model = (MedicionTrabajo) query.uniqueResult();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
        }
        return model;
    }
}
