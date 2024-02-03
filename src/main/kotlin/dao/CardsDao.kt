package dao

import classes.Cards
import org.hibernate.Session
import util.HibernateUtil

class CardsDao {
    fun createCard(card: Cards) {
        val session: Session = HibernateUtil.getSession().openSession()
        session.beginTransaction()

        session.save(card)

        session.transaction.commit()
        session.close()
    }

    fun getCardById(cardId: Int): Cards? {
        val session: Session = HibernateUtil.getSession().openSession()
        val card: Cards? = session.get(Cards::class.java, cardId)
        session.close()
        return card
    }

    fun updateCard(card: Cards) {
        val session: Session = HibernateUtil.getSession().openSession()
        session.beginTransaction()

        session.update(card)

        session.transaction.commit()
        session.close()
    }

    fun deleteCard(cardId: Int) {
        val session: Session = HibernateUtil.getSession().openSession()
        session.beginTransaction()

        val card: Cards? = session.get(Cards::class.java, cardId)
        if (card != null) {
            session.delete(card)
        }

        session.transaction.commit()
        session.close()
    }
}