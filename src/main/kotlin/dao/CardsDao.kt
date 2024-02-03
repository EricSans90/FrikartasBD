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

    fun deleteCard(cardId: Int, cardtagsDao: CardtagsDao, cardlanguagesDao: CardlanguagesDao, cardimagesDao: CardimagesDao, session: Session) {
        try {
            // Primero, eliminar todas las referencias de la carta
            cardtagsDao.deleteTagsByCardId(cardId, session)
            cardlanguagesDao.deleteLanguagesByCardId(cardId, session)
            cardimagesDao.deleteImagesByCardId(cardId, session)

            // Luego, eliminar la carta
            val card: Cards? = session.get(Cards::class.java, cardId)
            if (card != null) {
                session.delete(card)
            }

            println("La carta con ID $cardId ha sido eliminada con éxito.")
        } catch (e: Exception) {
            println("Error al eliminar la carta: ${e.message}")
            throw e
        }
    }

}