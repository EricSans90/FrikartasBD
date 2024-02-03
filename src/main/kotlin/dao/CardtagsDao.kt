package dao

import classes.Cardtags
import org.hibernate.Session
import util.HibernateUtil

class CardtagsDao {
    fun createCardTag(cardTag: Cardtags, session: Session) {
        session.save(cardTag)
    }

    fun getCardTagsByCardId(cardId: Int, session: Session): List<Cardtags> {
        val query = session.createQuery("FROM Cardtags WHERE cardId = :cardId", Cardtags::class.java)
        query.setParameter("cardId", cardId)
        return query.list()
    }

    fun updateCardTag(cardTag: Cardtags, session: Session) {
        session.update(cardTag)
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