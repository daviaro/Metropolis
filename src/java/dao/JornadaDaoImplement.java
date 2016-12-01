/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Jornada;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author chris
 */
public class JornadaDaoImplement implements JornadaDao {

    @Override
    public List<Jornada> mostrarJornadas() {
        Session session= HibernateUtil.getSessionFactory().getCurrentSession();
        List<Jornada> lista=null;
        String sql = "FROM Jornada";
        try{
            session.beginTransaction();
            lista = session.createQuery(sql).list();
            session.getTransaction().commit();
        }catch(HibernateException e){
            session.getTransaction().rollback();            
            System.out.println(e.getMessage());
        }
        return lista;
    }

    @Override
    public Jornada buscarJornada(Jornada jornada) {
        Jornada model=null;
        Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
        String sql = "FROM Jornada WHERE nombre ='"+jornada.getNombre()+"'";
        try{
            sesion.beginTransaction();
            model = (Jornada) sesion.createQuery(sql).uniqueResult();
            sesion.beginTransaction().commit();
        }catch(Exception e){
            sesion.beginTransaction().rollback();
        }
        return model;
    }

    @Override
    public boolean insertarJornada(Jornada jornada) {
       boolean flag;
        Transaction trans = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            trans = session.beginTransaction();
            session.save(jornada);
            session.getTransaction().commit();
            flag=true;
        }catch(RuntimeException e){
            if(trans!=null){
                trans.rollback();
        
            }
            
            flag=false;
        }finally{
            session.flush();
            session.close();
        }
        
        return flag;
    }

    @Override
    public boolean modificarJornada(Jornada jornada) {
        boolean flag;
        Transaction trans = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            session.update(jornada);
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
    public boolean eliminarJornada(Integer idjornada) {
        boolean flag;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            session.beginTransaction();
            Jornada jornada = (Jornada) session.load(Jornada.class, idjornada);
            session.delete(jornada);
            session.getTransaction().commit();
            flag=true;
        }catch(HibernateException e){
            flag=false;
            session.getTransaction().rollback();
        }
        return flag;
    }
    
    @Override
    public Jornada buscarJornadabyId(Integer idJornada) {
        Jornada jornada = null;
        Session session = null;
        //Se crea un try catch para hacer la consulta
        String sql = "from Jornada where idJornada = '" + idJornada+ "'";
        try {
            //Se recupera la session actual
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicializo transaccion
            session.beginTransaction();
            Query query = session.createQuery(sql);
            query.setMaxResults(1);
            //uniqueResult() para que sea solo un solo resultado
            jornada = (Jornada) query.uniqueResult();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            session.getTransaction().rollback();
        }
        return jornada;
    
    }
    
}
