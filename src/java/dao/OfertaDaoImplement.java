/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
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
public class OfertaDaoImplement implements OfertaDao {

    //int numeroOfertas();
    List<Oferta> ofertas;
    Usuario usuarioRegistrado;
    Integer ofertasCount;

    @Override
    public List<Oferta> findAllbyRecientes() {

        Session session = null;
        ofertas = null;
        String sql = "FROM Oferta o INNER JOIN FETCH o.jornada as j INNER JOIN FETCH o.trabajo as t INNER JOIN FETCH o.usuario as u INNER JOIN FETCH u.ubicacion as ub INNER JOIN FETCH o.trabajo.categoria as c INNER JOIN FETCH o.trabajo.medicionTrabajo as m where o.usuario = u.idUsuario  order by o.fechaCreacion asc";

        try {
            //Se recupera la session actual
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicializo transaccion
            session.beginTransaction();
            Query query = session.createQuery(sql);
            ofertas = query.list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            if (session != null && session.getTransaction() != null) {
                try{
                session.getTransaction().rollback();
                }
                catch(Exception ex){}
            }
        }
        return ofertas;
    }

    @Override
    public Integer countAll() {

        Session session = null;
        ofertas = null;
        ofertasCount = 0;
        //Se crea un try catch para hacer la consulta
        String sql = "FROM Oferta";
        try {
            //Se recupera la session actual
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicializo transaccion
            session.beginTransaction();
            Query query = session.createQuery(sql);
            ofertas = query.list();
            ofertasCount = ofertas.size();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            try{
            session.getTransaction().rollback();
            }
            catch(Exception ex){}
        }
        return ofertasCount;
    }

    @Override
    public List<Oferta> findAllbyCalificacion() {
        String sql = "SELECT o FROM Contrato c, Oferta o INNER JOIN FETCH o.usuario as u INNER JOIN FETCH u.ubicacion as ub INNER JOIN FETCH o.jornada as j INNER JOIN FETCH o.trabajo as t INNER JOIN FETCH t.categoria as cat INNER JOIN FETCH t.medicionTrabajo as m where c.cotizacion.oferta = o.idOferta and c.calificacion  is not null";
        Session session = null;
        ofertas = null;
        try {
            //Se recupera la session actual
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicializo transaccion
            session.beginTransaction();
            Query query = session.createQuery(sql);
            ofertas = query.list();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            try{
            session.getTransaction().rollback();
            }catch(Exception ex){}
        }
        return ofertas;
    }

    @Override
    public Boolean insertarOferta(Oferta oferta) {
        boolean flag;
        Transaction trans = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            session.save(oferta);
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
    public Boolean actualizarOferta(Oferta oferta){
        boolean flag=false;
        
        Transaction trans = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            session.update(oferta);
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
    public Oferta buscarOfertabyId(Integer id) {
        Oferta oferta = null;
        Session session = null;
        //Se crea un try catch para hacer la consulta
        String sql = "FROM Oferta as o INNER JOIN FETCH o.jornada as j INNER JOIN FETCH o.usuario as u INNER JOIN FETCH u.ubicacion as ub INNER JOIN FETCH o.trabajo as t INNER JOIN FETCH o.trabajo.categoria as c INNER JOIN FETCH o.trabajo.medicionTrabajo as m   where idOferta = '" + id + "'";
        try {
            //Se recupera la session actual
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicializo transaccion
            session.beginTransaction();
            Query query = session.createQuery(sql);
            query.setMaxResults(1);
            //uniqueResult() para que sea solo un solo resultado
            oferta = (Oferta) query.uniqueResult();
            oferta.getCotizacions().size();
            oferta.getPortafolios().size();
            oferta.getTrabajo().getTitulo();
            oferta.getUsuario().getUbicacion();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            try{
            session.getTransaction().rollback();
            }
            catch(Exception ex){}
        }
        return oferta;
    }

    @Override
    public List<Oferta> findAllbyIdUsuario(Integer idUsuario) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Oferta> lista = null;
        String sql = "FROM Oferta o INNER JOIN FETCH o.jornada as j INNER JOIN FETCH o.usuario as u INNER JOIN FETCH u.ubicacion as ub INNER JOIN FETCH o.trabajo as t INNER JOIN FETCH o.trabajo.categoria as c INNER JOIN FETCH o.trabajo.medicionTrabajo as m where u.idUsuario = '" + idUsuario + "'" + "order by o.fechaCreacion asc";
        try {
            if (session != null) {
                session.beginTransaction();
                lista = session.createQuery(sql).list();
                session.getTransaction().commit();
            }
        } catch (HibernateException e) {
            if (session != null) {
                try{
                session.getTransaction().rollback();
                } 
                catch(Exception ex){}
            }
            System.out.println(e.getMessage());
        }
        return lista;
    }

    @Override
    public List<Oferta> buscarOfertabyNombre(String query) {
        List<Oferta> lista = null;
        Session session = null;
        //Se crea un try catch para hacer la consulta
        String sql = "Select o From Oferta o LEFT JOIN FETCH o.trabajo as tra  LEFT JOIN FETCH tra.categoria as cat "
                + "LEFT JOIN cat.etiquetas as e "
                + "WHERE tra.titulo LIKE '%" + query + "%' OR e.nombre "
                + "LIKE '%" + query + "%' OR cat.nombre  "
                + "LIKE '%" + query + "%' OR tra.descripcion  LIKE '%" + query + "%'";

        try {
            //Se recupera la session actual
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicializo transaccion
            session.beginTransaction();
            lista = session.createQuery(sql).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            try{
            session.getTransaction().rollback();
            }
            catch(Exception ex){}
        }
        return lista;
    }

    @Override
    public List<Oferta> findAllbyRecientes(String filtroTitulo, String filtroUbicacion, String filtroJornada, String filtroValor) {
        Session session = null;
        ofertas = null;

        String whereClausula = " where o.usuario = u.idUsuario ";

        if (filtroTitulo != null && !filtroTitulo.equals("")) {
            whereClausula += " and t.titulo like '%" + filtroTitulo + "%'";
        }

        if (filtroUbicacion != null && !filtroUbicacion.equals("")) {
            whereClausula += " and o.usuario.ubicacion.barrio like '%" + filtroUbicacion + "%'";
        }

        if (filtroJornada != null && !filtroJornada.equals("")) {
            whereClausula += " and j.nombre like '%" + filtroJornada + "%'";
        }
        
        if (filtroValor != null && !filtroValor.equals("")) {
            whereClausula += " and o.costo <= " + filtroValor;
        }

        String sql = "FROM Oferta o INNER JOIN FETCH o.jornada as j INNER JOIN FETCH o.trabajo as t INNER JOIN FETCH o.usuario as u INNER JOIN FETCH u.ubicacion as ub INNER JOIN FETCH o.trabajo.categoria as c INNER JOIN FETCH o.trabajo.medicionTrabajo as m " + whereClausula + "  order by o.fechaCreacion asc";

        try {
            //Se recupera la session actual
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicializo transaccion
            session.beginTransaction();
            Query query = session.createQuery(sql);
            ofertas = query.list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            try{
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            }
            catch(Exception ex){
                
            }
        }
        return ofertas;
    }

}
