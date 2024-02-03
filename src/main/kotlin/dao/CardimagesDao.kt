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

    fun getCardImageById(cardImageId: Int): Cardimages? {
        val session: Session = HibernateUtil.getSession().openSession()
        val cardImage: Cardimages? = session.get(Cardimages::class.java, cardImageId)
        session.close()
        return cardImage
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