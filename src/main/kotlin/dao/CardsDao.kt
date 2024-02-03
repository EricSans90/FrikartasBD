package dao

import classes.Cards
import org.hibernate.Session
import util.HibernateUtil

class CardsDao {
    fun createCard(card: Cards, session: Session) {
        session.save(card)
    }

    fun getCardById(cardId: Int, session: Session): Cards? {
        return session.get(Cards::class.java, cardId)
    }

    fun updateCard(card: Cards, session: Session) {
        session.update(card)
    }

    fun getCardsByCollectionId(collectionId: Int, session: Session): List<Cards> {
        val query = session.createQuery("FROM Cards WHERE collectionsByCollectionId.collectionId = :collectionId", Cards::class.java)
        query.setParameter("collectionId", collectionId)
        return query.resultList
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