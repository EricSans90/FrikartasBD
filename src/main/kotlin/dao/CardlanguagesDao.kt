package dao

import classes.Cardlanguages
import org.hibernate.Session
import util.HibernateUtil

class CardlanguagesDao {
    fun createCardLanguage(cardLanguage: Cardlanguages, session: Session) {
        session.save(cardLanguage)
    }

    fun getCardLanguagesByCardId(cardId: Int, session: Session): List<Cardlanguages> {
        val query = session.createQuery("FROM Cardlanguages WHERE cardId = :cardId", Cardlanguages::class.java)
        query.setParameter("cardId", cardId)
        return query.list()
    }

    fun updateCardLanguage(cardLanguage: Cardlanguages, session: Session) {
        session.update(cardLanguage)
    }

    fun deleteLanguagesByCardId(cardId: Int, session: Session) {
        val languages = session.createQuery("FROM Cardlanguages WHERE cardId = :cardId", Cardlanguages::class.java)
            .setParameter("cardId", cardId)
            .list()
        for (language in languages) {
            session.delete(language)
        }
    }
}