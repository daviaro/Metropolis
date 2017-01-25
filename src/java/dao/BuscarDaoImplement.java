/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Date;
import model.Busqueda;
import model.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author Jotamarios
 */
public class BuscarDaoImplement implements BuscarDao{

    @Override
    public void almacenarBusqueda(String texto, Usuario usuario) {
        Date fecha = new Date();
        
        Busqueda busqueda = new Busqueda(usuario, texto, fecha);
        Transaction trans = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            session.save(busqueda);
            session.getTransaction().commit();
            
        } catch (RuntimeException e) {
            if (trans != null) {
                trans.rollback();

            }
            e.printStackTrace();
            
        } finally {
            session.flush();
            session.close();
        }
        
    }
    
}
