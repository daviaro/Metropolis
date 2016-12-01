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
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();;
        TipoPago tp = null;
        String sql = "FROM TipoPago WHERE nombre ='" + nombre + "'";
        try {
            session.beginTransaction();
            tp = (TipoPago) session.createQuery(sql).uniqueResult();
            session.beginTransaction().commit();
        } catch (Exception e) {
            session.beginTransaction().rollback();
        }
        return tp;
    }
}
