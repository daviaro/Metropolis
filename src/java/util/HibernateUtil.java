/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author andre
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static final Logger logger = LogManager.getLogger(HibernateUtil.class);
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            logger.info("Cargando configuracion de hibernate");
            sessionFactory = new AnnotationConfiguration().configure("/util/hibernate.cfg.xml").buildSessionFactory();
            
            
        } catch (Throwable ex) {
            // Log the exception. 
            logger.error("Initial SessionFactory creation failed [{}] " , ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
