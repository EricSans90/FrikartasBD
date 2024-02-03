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

    fun deleteCardLanguage(cardLanguageId: Int) {
        val session: Session = HibernateUtil.getSession().openSession()
        session.beginTransaction()

        val cardLanguage: Cardlanguages? = session.get(Cardlanguages::class.java, cardLanguageId)
        if (cardLanguage != null) {
            session.delete(cardLanguage)
        }

        session.transaction.commit()
        session.close()
    }
}