import classes.*
import dao.*


fun main(args: Array<String>) {
    // Crear una nueva colección
    val newCollection = Collections()
    newCollection.name = "Colección de prueba"
    newCollection.collectType = "ONEPIECE"
    newCollection.publicationYear = 2024

    // Guarda la colección en la base de datos
    val collectionsDao = CollectionsDao()
    collectionsDao.createCollection(newCollection)

    // Crear una nueva carta
    val newCard = Cards()
    newCard.name = "Nueva Carta"
    newCard.rarity = "SR"
    newCard.stock = 10
    newCard.price = 1.99
    newCard.discount = false
    newCard.sku = "SKU1234"
    newCard.description = "Descripción de la nueva carta."
    newCard.size = "6.3x8.8 cm"
    newCard.collectionsByCollectionId = newCollection

    val cardsDao = CardsDao()
    cardsDao.createCard(newCard)

    // Crear una nueva imagen de carta
    val newCardImage = Cardimages()
    newCardImage.urlImage = "https://drive.google.com/file/d/1JdCDqwZNbpx_FB19ZuiJpsg3sy0wvv1N/view?usp=drive_link"
    newCardImage.cardId = newCard.cardId // El ID de la carta que acabo de crear

    val cardimagesDao = CardimagesDao()
    cardimagesDao.createCardImage(newCardImage)

    // Crear un nuevo idioma para la carta
    val newCardLanguage = Cardlanguages()
    newCardLanguage.language = "CHINESE"
    newCardLanguage.cardId = newCard.cardId // El ID de la carta que acabo de crear

    val cardlanguagesDao = CardlanguagesDao()
    cardlanguagesDao.createCardLanguage(newCardLanguage)

    // Crear un nuevo tag para la carta
    val newCardTag = Cardtags()
    newCardTag.tag = "Tag de prueba"
    newCardTag.cardId = newCard.cardId // El ID de la carta que acabo de crear

    val cardtagsDao = CardtagsDao()
    cardtagsDao.createCardTag(newCardTag)

    // Obtener la carta por ID
    val card = cardsDao.getCardById(newCard.cardId)
    if (card != null) {
        println("Nombre de la carta: ${card.name}")
        println("Rareza: ${card.rarity}")
    } else {
        println("La carta no fue encontrada.")
    }
}