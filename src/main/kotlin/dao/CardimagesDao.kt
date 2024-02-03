package dao

import classes.Cardimages
import org.hibernate.Session
import util.HibernateUtil

class CardimagesDao {
    fun createCardImage(cardImage: Cardimages) {
        val session: Session = HibernateUtil.getSession().openSession()
        session.beginTransaction()

        session.save(cardImage)

        session.transaction.commit()
        session.close()
    }

    fun getCardImagesByCardId(cardId: Int): List<Cardimages> {
        val session: Session = HibernateUtil.getSession().openSession()
        val query = session.createQuery("FROM Cardimages WHERE cardId = :cardId", Cardimages::class.java)
        query.setParameter("cardId", cardId)
        val cardImages = query.list()
        session.close()
        return cardImages
    }

    fun updateCardImage(cardImage: Cardimages) {
        val session: Session = HibernateUtil.getSession().openSession()
        session.beginTransaction()

        session.update(cardImage)

        session.transaction.commit()
        session.close()
    }

    fun deleteCardImage(cardImageId: Int) {
        val session: Session = HibernateUtil.getSession().openSession()
        session.beginTransaction()

        val cardImage: Cardimages? = session.get(Cardimages::class.java, cardImageId)
        if (cardImage != null) {
            session.delete(cardImage)
        }

        session.transaction.commit()
        session.close()
    }
}