package dao

import classes.Cardimages
import org.hibernate.Session
import util.HibernateUtil

class CardimagesDao {
    fun createCardImage(cardImage: Cardimages, session: Session) {
        session.save(cardImage)
    }

    fun getCardImagesByCardId(cardId: Int, session: Session): List<Cardimages> {
        val query = session.createQuery("FROM Cardimages WHERE cardId = :cardId", Cardimages::class.java)
        query.setParameter("cardId", cardId)
        return query.list()
    }

    fun updateCardImage(cardImage: Cardimages, session: Session) {
        session.update(cardImage)
    }


    fun deleteImagesByCardId(cardId: Int, session: Session) {
        val images = session.createQuery("FROM Cardimages WHERE cardId = :cardId", Cardimages::class.java)
            .setParameter("cardId", cardId)
            .list()
        for (image in images) {
            session.delete(image)
        }
    }
}