package dao

import classes.Cardlanguages
import org.hibernate.Session
import util.HibernateUtil

class CardlanguagesDao {
    fun createCardLanguage(cardLanguage: Cardlanguages) {
        val session: Session = HibernateUtil.getSession().openSession()
        session.beginTransaction()

        session.save(cardLanguage)

        session.transaction.commit()
        session.close()
    }

    fun getCardLanguagesByCardId(cardId: Int): List<Cardlanguages> {
        val session: Session = HibernateUtil.getSession().openSession()
        val query = session.createQuery("FROM Cardlanguages WHERE cardId = :cardId", Cardlanguages::class.java)
        query.setParameter("cardId", cardId)
        val cardLanguages = query.list()
        session.close()
        return cardLanguages
    }

    fun updateCardLanguage(cardLanguage: Cardlanguages) {
        val session: Session = HibernateUtil.getSession().openSession()
        session.beginTransaction()

        session.update(cardLanguage)

        session.transaction.commit()
        session.close()
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