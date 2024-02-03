package dao

import classes.Cardtags
import org.hibernate.Session
import util.HibernateUtil

class CardtagsDao {
    fun createCardTag(cardTag: Cardtags) {
        val session: Session = HibernateUtil.getSession().openSession()
        session.beginTransaction()

        session.save(cardTag)

        session.transaction.commit()
        session.close()
    }

    fun getCardTagById(cardTagId: Int): Cardtags? {
        val session: Session = HibernateUtil.getSession().openSession()
        val cardTag: Cardtags? = session.get(Cardtags::class.java, cardTagId)
        session.close()
        return cardTag
    }

    fun updateCardTag(cardTag: Cardtags) {
        val session: Session = HibernateUtil.getSession().openSession()
        session.beginTransaction()

        session.update(cardTag)

        session.transaction.commit()
        session.close()
    }

    fun deleteCardTag(cardTagId: Int) {
        val session: Session = HibernateUtil.getSession().openSession()
        session.beginTransaction()

        val cardTag: Cardtags? = session.get(Cardtags::class.java, cardTagId)
        if (cardTag != null) {
            session.delete(cardTag)
        }

        session.transaction.commit()
        session.close()
    }
}