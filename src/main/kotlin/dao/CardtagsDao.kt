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

    fun getCardTagsByCardId(cardId: Int): List<Cardtags> {
        val session: Session = HibernateUtil.getSession().openSession()
        val query = session.createQuery("FROM Cardtags WHERE cardId = :cardId", Cardtags::class.java)
        query.setParameter("cardId", cardId)
        val cardTags: List<Cardtags> = query.resultList
        session.close()
        return cardTags
    }
    fun updateCardTag(cardTag: Cardtags) {
        val session: Session = HibernateUtil.getSession().openSession()
        session.beginTransaction()

        session.update(cardTag)

        session.transaction.commit()
        session.close()
    }

    fun deleteTagsByCardId(cardId: Int, session: Session) {
        val tags = session.createQuery("FROM Cardtags WHERE cardId = :cardId", Cardtags::class.java)
            .setParameter("cardId", cardId)
            .list()
        for (tag in tags) {
            session.delete(tag)
        }
    }
}