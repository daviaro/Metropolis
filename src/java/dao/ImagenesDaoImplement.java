/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import model.Imagenes;
import model.Oferta;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author andre
 */
public class ImagenesDaoImplement implements ImagenesDao{

    @Override
    public boolean insertarImagen(Imagenes imagen) {
        boolean flag;
        Transaction trans = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            session.save(imagen);
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
    
    public List<Imagenes> getImagenes(Oferta oferta) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Imagenes> lista = new ArrayList<Imagenes>();
        String sql = "FROM Imagenes im LEFT JOIN FETCH im.portafolio as porta LEFT JOIN FETCH porta.oferta as ofer WHERE ofer.idOferta = "+oferta.getIdOferta();
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
    
}
