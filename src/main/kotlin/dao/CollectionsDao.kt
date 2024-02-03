package dao

import classes.Collections
import org.hibernate.Session
import util.HibernateUtil

class CollectionsDao {
    fun createCollection(collection: Collections, session: Session) {
        session.save(collection)
    }

    fun updateCollection(collection: Collections, session: Session) {
        session.update(collection)
    }

    fun getCollectionById(collectionId: Int, session: Session): Collections? {
        val collection: Collections? = session.get(Collections::class.java, collectionId)
        return collection
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