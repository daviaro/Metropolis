/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.TipoPago;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author jotamarios
 */
public class TipoPagoDaoImplement implements TipoPagoDao {

    @Override
    public TipoPago buscarTipoPago(String nombre) {
        Session session = null;
        TipoPago tp = null;
        String sql = "FROM TipoPago WHERE nombre ='" + nombre + "'";
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            tp = (TipoPago) session.createQuery(sql).uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.beginTransaction().rollback();
                session.close();
            }
        }
        return tp;
    }
}
