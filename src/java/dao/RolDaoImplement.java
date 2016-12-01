/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Rol;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author andre
 */
public class RolDaoImplement  implements RolDao{

    @Override
    public Rol buscarRolByName(String nombre) {
        Rol rol = null;
        Session session = null;
        //Se crea un try catch para hacer la consulta
        String sql = "from Rol where nombre = '" + nombre + "'";
        try {
            //Se recupera la session actual
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicializo transaccion
            session.beginTransaction();
            Query query = session.createQuery(sql);
            query.setMaxResults(1);
            //uniqueResult() para que sea solo un solo resultado
            rol = (Rol) query.uniqueResult();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            session.getTransaction().rollback();
        }
        return rol;
    }
    
}
