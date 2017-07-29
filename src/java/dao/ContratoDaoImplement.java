/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Contrato;
import model.Usuario;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import Pagos.EstructuraVentas;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author andre
 */
public class ContratoDaoImplement implements ContratoDao {

    @Override
    public Boolean crearContrato(Contrato contrato) {
        boolean flag;
        Transaction trans = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            session.save(contrato);
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
    public List<Contrato> findAllContratadoByUsuario(Usuario usuarioRegistrado) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Contrato> lista = null;
        String sql = "From Contrato contr LEFT JOIN FETCH contr.cotizacion cotiz LEFT JOIN FETCH cotiz.oferta ofer LEFT JOIN FETCH ofer.usuario u WHERE cotiz.usuario = '" + usuarioRegistrado.getIdUsuario() + "'";
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
    public List<Contrato> findAllContratadoFromUsuario(Usuario usuarioRegistrado) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Contrato> lista = null;
        String sql = "From Contrato contr LEFT JOIN FETCH contr.cotizacion cotiz LEFT JOIN FETCH cotiz.oferta ofer LEFT JOIN FETCH ofer.usuario u WHERE ofer.usuario = '" + usuarioRegistrado.getIdUsuario() + "'";
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
    public List<Contrato> findAllContratos(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Contrato> lista = null;
        String sql = "From Contrato contr LEFT JOIN FETCH contr.cotizacion cotiz LEFT JOIN FETCH cotiz.oferta ofer LEFT JOIN FETCH ofer.usuario u WHERE ofer.usuario = '" + usuario.getIdUsuario() + "'";
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
    public Contrato findContratadoById(String idContrato) {
        Contrato model = null;
        Session session = null;
        String sql = "FROM Contrato WHERE idContrato ='" + idContrato + "'";
        try {
            //Se recupera la session actual
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicializo transaccion
            session.beginTransaction();
            Query query = session.createQuery(sql);
            query.setMaxResults(1);
            //uniqueResult() para que sea solo un solo resultado
            model = (Contrato) query.uniqueResult();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            session.getTransaction().rollback();
        }
        return model;
    }

    @Override
    public List<Contrato> findVigentes() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Contrato> lista = null;
        String sql = "From Contrato contr where contr.liquidacion is null";
        try {
            session.beginTransaction();
            lista = session.createQuery(sql).list();
            //Lazy
            for (Contrato c : lista) {
                c.getPagos().size();
                c.getCotizacion().getDescripcion();
                c.getCotizacion().getUsuario().getNombres();
                c.getCotizacion().getOferta().getUsuario().getNombres();
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public List<EstructuraVentas> getContratosXmes() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Object> lista = null;
        List<EstructuraVentas> listaes = new ArrayList<>();
        String sql = "select CONCAT(YEAR(fecha), MONTH (fecha)), count(*) From Contrato GROUP BY CONCAT(YEAR(fecha), MONTH (fecha)) ORDER BY 1";
        try {
            session.beginTransaction();
            lista = session.createQuery(sql).list();

            Iterator itr = lista.iterator();
            while (itr.hasNext()) {
                Object[] obj = (Object[]) itr.next();
                String mes = String.valueOf(obj[0]);
                int cantidad = Integer.parseInt(String.valueOf(obj[1]));
                listaes.add(new EstructuraVentas(mes, cantidad));
            }

            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        return listaes;
    }

    @Override
    public boolean modificarContrato(Contrato contrato) {
        boolean flag;
        Transaction trans = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            session.update(contrato);
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
