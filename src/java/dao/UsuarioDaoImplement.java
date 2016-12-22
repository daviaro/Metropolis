/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.List;
import model.Usuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author anfevari
 */
public class UsuarioDaoImplement implements UsuarioDao,Serializable {

    private Logger logger = LogManager.getLogger(UsuarioDaoImplement.class);
    List<Usuario> usuarios;
    Integer usuariosCount;

    @Override
    public List<Usuario> mostrarUsuarios() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Usuario> lista = null;
        String sql = "FROM Usuario u left join fetch u.ubicacion order by u.nombres asc";
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
    public boolean insertarUsuario(Usuario usuario) {
        boolean flag;
        Transaction trans = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trans = session.beginTransaction();
            session.save(usuario);
            trans.commit();
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
    public boolean modificarUsuario(Usuario usuario) {
        boolean flag = false;
        Transaction trans = null;
        Session session = null;

        logger.info("Se ingresa al metodo modificarUsuario");
        try {
            logger.info("Se obtiene la session");
            session = HibernateUtil.getSessionFactory().openSession();
            trans = session.beginTransaction();
            logger.debug("se va actualizar usuario [{}]", usuario.getIdUsuario());
            session.update(usuario);

            trans.commit();
            logger.debug("Usuario actualizado");
            flag = true;
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }

            logger.error("Se encuentra un error modificando el usuario [{}]. Error [{}] ", usuario.getIdUsuario(), e.getMessage());

        } finally {
            if (session != null) {

                logger.debug("Cerrando session");
                session.flush();
                session.close();
            }

        }
        return flag;
    }

    @Override
    public boolean eliminarUsuario(Integer idUsuario) {
        boolean flag;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            Usuario usuario = (Usuario) session.load(Usuario.class, idUsuario);
            session.delete(usuario);
            session.getTransaction().commit();
            flag = true;
        } catch (HibernateException e) {
            flag = false;
            session.getTransaction().rollback();
        }
        return flag;
    }

    //Busqueda por Email
    @Override
    public Usuario buscarUsuario(Usuario usuario) {
        Usuario user = null;
        Session session = null;
        Transaction trans = null;
        //Se crea un try catch para hacer la consulta
        String sql = "From Usuario u LEFT JOIN FETCH u.ubicacion as ubi LEFT JOIN FETCH u.rols as r  LEFT JOIN FETCH u.ofertas as o WHERE email = '" + usuario.getEmail() + "'";

        try {

            logger.info("Se ingresa a buscarUsuario");
            //Se recupera la session actual
            logger.debug("Se obtiene session");
            session = HibernateUtil.getSessionFactory().openSession();

            //inicializo transaccion
            trans = session.beginTransaction();

            logger.debug("Se inicia ejecucion del query [{}]", sql);
            Query query = session.createQuery(sql);
            logger.debug("Query ejecutado", sql);
            List results = query.list();

            logger.debug("Cantidad de resultados [{}]", results.size());
            if (!results.isEmpty()) {
                
                user = (Usuario) query.list().get(0);
                logger.debug("se encontro un resultado [{}] ",user.toString());
                
            } else {
                user = usuario;
                logger.debug("se encontro un resultado [{}] ",user.getNombres());
            }

            trans.commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            if (trans != null) {
                trans.rollback();
            }
            logger.error("Error buscarUsuario [{}] ", e.getMessage());

        } catch (Exception e) {

            logger.error("Error buscarUsuario [{}] ", e.getMessage());
        } finally {
            if (session != null) {
                session.flush();
                session.close();
                logger.debug("Se cierra la session");
            }
        }
        return user;
    }

    @Override
    public Integer countAll() {

        Session session = null;
        usuarios = null;
        usuariosCount = 0;
        //Se crea un try catch para hacer la consulta
        String sql = "FROM Usuario";
        try {
            //Se recupera la session actual
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicializo transaccion
            session.beginTransaction();
            Query query = session.createQuery(sql);
            usuarios = query.list();
            usuariosCount = usuarios.size();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            session.getTransaction().rollback();
        }
        return usuariosCount;
    }

    @Override
    public Usuario login(Usuario usuario) {
        Usuario user = this.buscarUsuario(usuario);
        if (user != null) {
            //Si no coinciden las claves
            if (!usuario.getContrasena().equals(user.getContrasena())) {
                user = null;
            }
        }
        return user;
    }

    @Override
    public Usuario buscarUsuariobyEmail(String email) {
        Usuario usuario = null;
        Session session = null;
        //Se crea un try catch para hacer la consulta
        String sql = "from Usuario where email = '" + email + "'";
        try {
            //Se recupera la session actual
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicializo transaccion
            session.beginTransaction();
            Query query = session.createQuery(sql);
            query.setMaxResults(1);
            //uniqueResult() para que sea solo un solo resultado
            usuario = (Usuario) query.uniqueResult();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            session.getTransaction().rollback();
        }
        return usuario;
    }

    //Busqueda por Email
    @Override
    public List<Usuario> buscarUsuariobyNombre(String texto) {
        List<Usuario> lista = null;
        Session session = null;
        //Se crea un try catch para hacer la consulta
        String sql = "From Usuario u LEFT JOIN FETCH u.rols as r  LEFT JOIN FETCH u.ofertas as o "
                + "WHERE r.nombre = 'Empleado' AND (apellidos LIKE '%" + texto + "%' OR nombres  "
                + "LIKE '%" + texto + "%' OR documento_identidad  LIKE '%" + texto + "%')";

        try {
            //Se recupera la session actual
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicializo transaccion
            session.beginTransaction();
            lista = session.createQuery(sql).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            session.getTransaction().rollback();
        }
        return lista;
    }

    @Override
    public Usuario buscarUsuariobyCedula(String cedula) {
        Usuario usuario = null;
        Session session = null;
        //Se crea un try catch para hacer la consulta
        String sql = "from Usuario where documentoIdentidad = '" + cedula + "'";
        try {
            //Se recupera la session actual
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicializo transaccion
            session.beginTransaction();
            Query query = session.createQuery(sql);
            query.setMaxResults(1);
            //uniqueResult() para que sea solo un solo resultado
            usuario = (Usuario) query.uniqueResult();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            session.getTransaction().rollback();
        }
        return usuario;
    }

    @Override
    public Usuario buscarUsuariobyEmailnCedula(Usuario usuarioRecuperar) {
        Usuario user = null;
        Session session = null;
        //Se crea un try catch para hacer la consulta
        String sql = "From Usuario u WHERE email = '" + usuarioRecuperar.getEmail() + "' AND documentoIdentidad = '" + usuarioRecuperar.getDocumentoIdentidad() + "'";

        try {
            //Se recupera la session actual
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            //inicializo transaccion
            session.beginTransaction();
            Query query = session.createQuery(sql);
            query.setMaxResults(1);
            //uniqueResult() para que sea solo un solo resultado
            user = (Usuario) query.uniqueResult();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            //si no se cumple se hace un rollback
            session.getTransaction().rollback();
        }
        return user;
    }

    @Override
    public Usuario buscarUsuariobyID(String ID) {
        Usuario usuario = null;
        Session session = null;
        //Se crea un try catch para hacer la consulta
        String sql = "from Usuario where idUsuario = '" + ID + "'";
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(sql);
            query.setMaxResults(1);
            usuario = (Usuario) query.uniqueResult();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        }
        return usuario;
    }

    @Override
    public int insertarUsuario2(Usuario usuario) {
        int usuarioID = -1;
        Transaction trans = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            trans = session.beginTransaction();
            session.save(usuario);
            usuarioID = usuario.getIdUsuario();
            trans.commit();
        } catch (RuntimeException e) {
            if (trans != null) {
                trans.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        return usuarioID;
    }

}
