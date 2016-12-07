/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Date;
import java.util.List;
import model.Cotizacion;
import model.Oferta;
import model.Usuario;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author andre
 */
public class CotizacionDaoImplement implements CotizacionDao {

    @Override
    public Boolean insertarCotizacion(Cotizacion cotizacion) {
        boolean flag = false;
        Transaction trans = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            session.save(cotizacion);
            session.getTransaction().commit();
            flag = true;
        } catch (RuntimeException e) {
            if (trans != null) {
                trans.rollback();
            }
            e.printStackTrace();
            flag = false;
        }
        //session.flush();
        session.close();

        return flag;

        //return flag;
    }

    @Override
    public List<Cotizacion> findAllbyOferta(Oferta oferta) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Cotizacion> lista = null;
        String sql = "FROM Cotizacion c INNER JOIN FETCH c.oferta as co INNER JOIN FETCH co.trabajo as  cot INNER JOIN FETCH cot.medicionTrabajo as  m where  co = '" + oferta.getIdOferta() + "'";
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
    public boolean actualizarCotizacion(Cotizacion cotizacion) {
        boolean flag;
        Transaction trans = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            session.update(cotizacion);
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
    public List<Cotizacion> findAllbyOfertaPendiente(Oferta oferta) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Cotizacion> lista = null;
        String sql = "FROM Cotizacion c LEFT JOIN FETCH c.contratos contra INNER JOIN FETCH c.oferta as co INNER JOIN FETCH co.trabajo as  cot INNER JOIN FETCH cot.medicionTrabajo as  m where (co.fechaLimite IS NULL OR  co.fechaLimite >= current_date) and co = '" + oferta.getIdOferta() + "' and c.respuesta = 0 and contra IS NULL and fechaRespuesta IS NULL";
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
    public List<Cotizacion> findAllbyOfertaContratada(Oferta oferta) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Cotizacion> lista = null;
        String sql = "FROM Cotizacion c LEFT JOIN FETCH c.contratos contra INNER JOIN FETCH c.oferta as co INNER JOIN FETCH co.trabajo as  cot INNER JOIN FETCH cot.medicionTrabajo as  m where  contra IS NOT NULL and co = '" + oferta.getIdOferta() + "'";

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
    public List<Cotizacion> findAllbyCotizacionesRealizadas(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Cotizacion> lista = null;
        String sql = "FROM Cotizacion c LEFT JOIN FETCH c.contratos contra INNER JOIN FETCH c.oferta as co INNER JOIN FETCH co.trabajo as  cot INNER JOIN FETCH cot.medicionTrabajo as  m WHERE (c.fechaRespuesta IS NULL) and contra IS NULL and c.usuario ='" + usuario.getIdUsuario() + "'";
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
    public List<Cotizacion> findAllbyCotizacionesAceptadas(Usuario usuarioRegistrado) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Cotizacion> lista = null;
        String sql = "FROM Cotizacion c LEFT JOIN FETCH c.contratos contra INNER JOIN FETCH c.oferta as co INNER JOIN FETCH co.trabajo as  cot INNER JOIN FETCH cot.medicionTrabajo as  m WHERE (c.fechaRespuesta IS NOT NULL and c.fechaContraPropuestaRespuesta IS NULL and  c.valorContrapropuesta IS NULL and c.fechaEstricta IS NULL) and contra IS NULL and c.usuario ='" + usuarioRegistrado.getIdUsuario() + "'";
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
    public List<Cotizacion> findAllbyCotizacionesContraofertadas(Usuario usuarioRegistrado) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Cotizacion> lista = null;
        String sql = "FROM Cotizacion c LEFT JOIN FETCH c.contratos contra INNER JOIN FETCH c.oferta as co INNER JOIN FETCH co.trabajo as  cot INNER JOIN FETCH cot.medicionTrabajo as  m WHERE (c.fechaRespuesta IS NOT NULL and c.fechaContraPropuestaRespuesta IS NOT NULL and  c.valorContrapropuesta IS NOT NULL) and contra IS NULL and c.usuario ='" + usuarioRegistrado.getIdUsuario() + "'";
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
    public List<Cotizacion> findAll() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Cotizacion> lista = null;
        String sql = "FROM Cotizacion c";
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
    public Cotizacion buscarCotizacionById(int idCoti) {
        Cotizacion cotizacion = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        //Se crea un try catch para hacer la consulta
        String sql = "from Cotizacion c WHERE idCotizacion = '" + idCoti + "'";
        try {
            //inicializo transaccion
            session.beginTransaction();
            cotizacion = (Cotizacion) session.createQuery(sql).uniqueResult();
            int size = cotizacion.getContratos().size();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            session.getTransaction().rollback();
        }
        return cotizacion;
    }

}
