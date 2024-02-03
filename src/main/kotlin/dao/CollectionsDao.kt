package dao

import classes.Collections
import org.hibernate.Session
import util.HibernateUtil

class CollectionsDao {
    fun createCollection(collection: Collections) {
        val session: Session = HibernateUtil.getSession().openSession()
        session.beginTransaction()

        session.save(collection)

        session.transaction.commit()
        session.close()
    }

    fun getCollectionById(collectionId: Int, session: Session): Collections? {
        val collection: Collections? = session.get(Collections::class.java, collectionId)
        return collection
    }

    fun updateCollection(collection: Collections) {
        val session: Session = HibernateUtil.getSession().openSession()
        session.beginTransaction()

        session.update(collection)

        session.transaction.commit()
        session.close()
    }

    fun deleteCollection(collectionId: Int, session: Session) {
        val transaction = session.beginTransaction()
        try {
            val collection: Collections? = session.get(Collections::class.java, collectionId)
            if (collection != null) {
                session.delete(collection)
            }
            transaction.commit()
        } catch (e: Exception) {
            transaction.rollback()
            throw e
        }
    }
}