import classes.*
import dao.*
import org.hibernate.Hibernate
import util.HibernateUtil
import java.util.logging.Level
import java.util.logging.LogManager


fun main(args: Array<String>) {
    LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE)
    val collectionsDao = CollectionsDao()
    val cardsDao = CardsDao()

    while (true) {
        println("Elige una opción:")
        println("1. Consultar una colección")
        println("2. Consultar una carta")
        println("3. Crear una colección")
        println("4. Crear una carta")
        println("5. Crear tags/languages/urlImages de una carta")
        println("6. Borrar una colección")
        println("7. Borrar una carta")
        println("8. Salir")

        when (readLine()) {
            "1" -> {
                ConsultateCollection(collectionsDao)
                println()
            }
            "2" -> {
                ConsultateCard(cardsDao)
                println()
            }
            "3" -> {
                CreateCollection(collectionsDao)
                println()
            }
            "4" -> {
                CreateCard(collectionsDao, cardsDao)
                println()
            }
            "5" -> {
                CreateTagsLanguagesImages(cardsDao)
                println()
            }
            "6" -> {
                println("Introduce el ID de la colección a borrar:")
                DeleteCollection(collectionsDao)
                println()
            }
            "7" -> {
                println("Introduce el ID de la carta a borrar:")
                DeleteCard(cardsDao)
                println()
            }
            "8" -> break
            else -> println("Opción no reconocida. Intente de nuevo.")
        }
    }

}

private fun ConsultateCard(cardsDao: CardsDao) {
    println("Introduce el ID de la carta que quieres consultar:")
    val cardId = readLine()?.toIntOrNull()
    if (cardId != null) {
        val card = cardsDao.getCardById(cardId)
        val cardtagsDao = CardtagsDao()
        val cardimagesDao = CardimagesDao()
        val cardlanguagesDao = CardlanguagesDao()
        if (card != null) {
            println("Información de la carta:")
            println("ID: ${card.cardId}")
            println("Nombre: ${card.name}")
            // Consultar y mostrar tags
            val tags = cardtagsDao.getCardTagsByCardId(card.cardId)
            println("Tags:")
            tags.forEach { tag -> println("- ${tag.tag}") }
            println("Rareza: ${card.rarity}")
            println("Stock: ${card.stock}")
            println("Precio: ${card.price}")
            println("Descuento: ${card.discount}")
            println("SKU: ${card.sku}")
            // Consultar y mostrar urlImages
            val images = cardimagesDao.getCardImagesByCardId(card.cardId)
            println("Images:")
            images.forEach { image -> println("- ${image.urlImage}") }
            println("Descripción: ${card.description}")
            // Consultar y mostrar languages
            val languages = cardlanguagesDao.getCardLanguagesByCardId(card.cardId)
            println("Languages:")
            languages.forEach { language -> println("- ${language.language}") }
            println("Tamaño: ${card.size}")
            println("Colección ID: ${card.collectionsByCollectionId?.collectionId}")
        } else {
            println("No se encontró una carta con ID $cardId.")
        }
    } else {
        println("ID inválido.")
    }
}

private fun ConsultateCollection(collectionsDao: CollectionsDao) {
    println("Introduce el ID de la colección que quieres consultar:")
    val collectionId = readLine()?.toIntOrNull()
    if (collectionId != null) {
        val session = HibernateUtil.getSession().openSession()
        try {
            val collection = collectionsDao.getCollectionById(collectionId, session)
            if (collection != null) {
                Hibernate.initialize(collection.cardsByCollectionId)
                println("Información de la colección:")
                println("ID: ${collection.collectionId}")
                println("Nombre: ${collection.name}")
                println("Tipo: ${collection.collectType}")
                println("Año de publicación: ${collection.publicationYear}")
                println("Cantidad de cartas: ${collection.cardsByCollectionId.size}")
                collection.cardsByCollectionId.forEach { card ->
                    println("Carta ID: ${card.cardId}, Nombre: ${card.name}, ...otros detalles...")
                }
            } else {
                println("No se encontró una colección con ID $collectionId.")
            }
        } finally {
            session.close()
        }
    } else {
        println("ID inválido.")
    }
}

private fun DeleteCollection(collectionsDao: CollectionsDao) {
    println("Introduce el ID de la colección a borrar:")
    val collectionId = readLine()?.toIntOrNull()
    if (collectionId != null) {
        val session = HibernateUtil.getSession().openSession()
        try {
            val collection = collectionsDao.getCollectionById(collectionId, session)
            if (collection != null) {
                collectionsDao.deleteCollection(collectionId, session)
                println("Colección con ID $collectionId borrada con éxito.")
            } else {
                println("No se encontró una colección con ID $collectionId.")
            }
        } finally {
            session.close()
        }
    } else {
        println("ID inválido.")
    }
}

private fun DeleteCard(cardsDao: CardsDao) {
    val cardId = readLine()?.toIntOrNull()
    if (cardId != null) {
        cardsDao.deleteCard(cardId)
    } else {
        println("ID inválido.")
    }
}

private fun CreateTagsLanguagesImages(cardsDao: CardsDao) {
    println("Introduce el ID de la carta a la que quieres añadir tags/languages/urlImages:")
    val cardId = readLine()?.toIntOrNull()
    if (cardId != null) {
        val card = cardsDao.getCardById(cardId)
        if (card != null) {
            addExtraToCard(card)
        } else {
            println("No se encontró una carta con ID $cardId.")
        }
    } else {
        println("ID inválido.")
    }
}

private fun CreateCard(collectionsDao: CollectionsDao, cardsDao: CardsDao) {
    println("Introduce el nombre de la carta:")
    val name = readLine()
    println("Introduce la rareza de la carta:")
    val rarity = readLine()
    println("Introduce el stock de la carta:")
    val stock = readLine()?.toIntOrNull()
    println("Introduce el precio de la carta:")
    val price = readLine()?.toDoubleOrNull()
    println("¿Tiene descuento la carta? (true/false):")
    val discount = readLine()?.toBoolean()
    println("Introduce el SKU de la carta:")
    val sku = readLine()
    println("Introduce la descripción de la carta:")
    val description = readLine()
    println("Introduce el tamaño de la carta:")
    val size = readLine()
    println("Introduce el ID de la colección a la que pertenece la carta:")

    val collectionId = readLine()?.toIntOrNull()
    if (name != null && rarity != null && stock != null && price != null && discount != null && sku != null && description != null && size != null && collectionId != null) {
        val session = HibernateUtil.getSession().openSession()
        try {
            val collection = collectionsDao.getCollectionById(collectionId, session)
            if (collection != null) {
                val card = Cards()
            card.setName(name)
            card.setRarity(rarity)
            card.setStock(stock)
            card.setPrice(price)
            card.setDiscount(discount)
            card.setSku(sku)
            card.setDescription(description)
            card.setSize(size)
            card.setCollectionsByCollectionId(collection)

            cardsDao.createCard(card)

            // Opción para añadir tags/languages/urlImages
            println("¿Quieres añadir tags/languages/urlImages a la carta? (si/no):")
            val addExtra = readLine()
            if (addExtra.equals("si", ignoreCase = true)) {
                // Llamar a la función para añadir tags/languages/urlImages
                addExtraToCard(card)
            }
            } else {
                println("La colección con ID $collectionId no existe.")
            }
        } finally {
            session.close()
        }
    } else {
        println("Datos inválidos.")
    }
}

fun addExtraToCard(card: Cards) {
    // Añadir tags
    val cardtagsDao = CardtagsDao()
    println("¿Quieres añadir un tag a la carta? (si/no):")
    val addTag = readLine()
    if (addTag.equals("si", ignoreCase = true)) {
        println("Introduce el tag para la carta:")
        val tagText = readLine()
        if (!tagText.isNullOrBlank()) {
            val tag = Cardtags(card.cardId,tagText)
            cardtagsDao.createCardTag(tag)
        }
    }

    // Añadir languages
    val cardlanguagesDao = CardlanguagesDao()
    println("¿Quieres añadir un language a la carta? (si/no):")
    val addLanguage = readLine()
    if (addLanguage.equals("si", ignoreCase = true)) {
        println("Introduce el idioma para la carta:")
        val languageText = readLine()
        if (!languageText.isNullOrBlank()) {
            val language = Cardlanguages(card.cardId,languageText)
            cardlanguagesDao.createCardLanguage(language)
        }
    }

    // Añadir urlImages
    val cardimagesDao = CardimagesDao()
    println("¿Quieres añadir una imagen a la carta? (si/no):")
    val addImage = readLine()
    if (addImage.equals("si", ignoreCase = true)) {
        println("Introduce la URL de la imagen para la carta:")
        val imageUrl = readLine()
        if (!imageUrl.isNullOrBlank()) {
            val image = Cardimages(card.cardId, imageUrl)
            cardimagesDao.createCardImage(image)
        }
    }
}


private fun CreateCollection(collectionsDao: CollectionsDao) {
    println("Introduce el nombre de la colección:")
    val name = readLine()
    println("Introduce el tipo de la colección:")
    val type = readLine()
    println("Introduce el año de publicación de la colección:")
    val year = readLine()?.toIntOrNull()
    if (name != null && type != null && year != null) {
        collectionsDao.createCollection(Collections(name, type, year))
    } else {
        println("Datos inválidos.")
    }
}


/* PRUEBAS A MANO

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

     */