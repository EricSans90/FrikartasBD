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

    fun getCardLanguageById(cardLanguageId: Int): Cardlanguages? {
        val session: Session = HibernateUtil.getSession().openSession()
        val cardLanguage: Cardlanguages? = session.get(Cardlanguages::class.java, cardLanguageId)
        session.close()
        return cardLanguage
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