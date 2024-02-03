package util

import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

object HibernateUtil {
    private val sessionFactory: SessionFactory

    init {
        try {
            // Creo la SessionFactory desde hibernate.cfg.xml
            sessionFactory = Configuration().configure().buildSessionFactory()
        } catch (ex: Throwable) {
            println("La creación inicial de la SessionFactory falló. $ex")
            throw ExceptionInInitializerError(ex)
        }
    }

    fun getSession(): SessionFactory {
        return sessionFactory
    }
}