/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import model.Comision;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author jotamarios
 */
public class ComisionDaoImplement implements ComisionDao{
    
    @Override
    public Comision getComisionAldia(){
        Comision comision = null;
        
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        String sql = "FROM Comision c  where  fechaCreacion = (Select MAX(fechaCreacion) FROM Comision co)";
        try {
            session.beginTransaction();
            comision = (Comision)session.createQuery(sql).uniqueResult();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
        }        
        
        return comision;
        
    }
    
}
