import classes.*
import dao.*
import org.hibernate.Hibernate
import org.hibernate.Session
import util.HibernateUtil
import java.util.logging.Level
import java.util.logging.LogManager


fun main(args: Array<String>) {
    LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE)
    val collectionsDao = CollectionsDao()
    val cardsDao = CardsDao()
    val cardtagsDao = CardtagsDao()
    val cardlanguagesDao = CardlanguagesDao()
    val cardimagesDao = CardimagesDao()

    while (true) {
        println("Elige una opción:")
        println("1. Consultar una colección")
        println("2. Consultar una carta")
        println("3. Crear una colección")
        println("4. Crear una carta")
        println("5. Crear tags/languages/urlImages de una carta")
        println("6. Modificar una colección")
        println("7. Modificar una carta")
        println("8. Borrar una colección")
        println("9. Borrar una carta")
        println("10. Salir")

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
                ModificateCollection(collectionsDao)
                println()
            }
            "7" -> {
                ModificateCard(cardsDao, collectionsDao, cardtagsDao, cardlanguagesDao, cardimagesDao)
                println()
            }
            "8" -> {
                DeleteCollection(collectionsDao, cardsDao, cardtagsDao, cardlanguagesDao, cardimagesDao)
                println()
            }
            "9" -> {
                println("Introduce el ID de la carta a borrar:")
                val cardId = readLine()?.toIntOrNull()
                if (cardId != null) {
                    DeleteCard(cardsDao, cardtagsDao, cardlanguagesDao, cardimagesDao, cardId)
                } else {
                    println("ID inválido.")
                }
            }
            "10" -> break
            else -> println("Opción no reconocida. Intente de nuevo.")
        }
    }

}

public fun ModificateCollection(collectionsDao: CollectionsDao) {
    println("Introduce el ID de la colección que quieres modificar:")
    val collectionId = readLine()?.toIntOrNull()
    if (collectionId != null) {
        val session = HibernateUtil.getSession().openSession()
        try {
            val collection = collectionsDao.getCollectionById(collectionId, session)
            if (collection != null) {
                println("Información actual de la colección:")
                println("Nombre: ${collection.name}")
                println("Tipo: ${collection.collectType}")
                println("Año de publicación: ${collection.publicationYear}")

                println("Introduce el nuevo nombre de la colección (o presiona ENTER para mantener el actual):")
                val newName = readLine()
                if (!newName.isNullOrBlank()) {
                    collection.name = newName
                }

                println("Introduce el nuevo tipo de la colección (o presiona ENTER para mantener el actual):")
                val newType = readLine()
                if (!newType.isNullOrBlank()) {
                    collection.collectType = newType
                }

                println("Introduce el nuevo año de publicación de la colección (o presiona ENTER para mantener el actual):")
                val newYear = readLine()?.toIntOrNull()
                if (newYear != null) {
                    collection.publicationYear = newYear
                }

                collectionsDao.updateCollection(collection,session)
                println("Colección actualizada con éxito.")

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

public fun ModificateCard(cardsDao: CardsDao, collectionsDao: CollectionsDao, cardtagsDao: CardtagsDao, cardlanguagesDao: CardlanguagesDao, cardimagesDao: CardimagesDao) {
    println("Introduce el ID de la carta que quieres modificar:")
    val cardId = readLine()?.toIntOrNull()
    if (cardId != null) {
        val session = HibernateUtil.getSession().openSession()
        val transaction = session.beginTransaction()
    try{
        val card = cardsDao.getCardById(cardId,session)
        if (card != null) {
            println("Información actual de la carta:")
            println("Nombre: ${card.name}")
            println("Rareza: ${card.rarity}")
            println("Stock: ${card.stock}")
            println("Precio: ${card.price}")
            println("Descuento: ${card.discount}")
            println("SKU: ${card.sku}")
            println("Descripción: ${card.description}")
            println("Tamaño: ${card.size}")
            println("ID de Colección: ${card.collectionId}")

            // Actualización de Nombre
            println("Introduce el nuevo nombre de la carta (o presiona ENTER para mantener el actual):")
            val newName = readLine()
            if (!newName.isNullOrBlank()) {
                card.name = newName
            }

            // Actualización de Tags
            println("¿Quieres modificar los tags de la carta? (si/no):")
            if (readLine().equals("si", ignoreCase = true)) {
                cardtagsDao.deleteTagsByCardId(card.cardId, session)  // Eliminar todos los tags existentes
                var continuar = true
                while (continuar) {
                    println("Introduce un tag para la carta (o presiona ENTER para finalizar):")
                    val tagText = readLine()
                    if (!tagText.isNullOrBlank()) {
                        val tag = Cardtags(card.cardId, tagText)
                        cardtagsDao.createCardTag(tag,session)
                    } else {
                        continuar = false
                    }
                }
            }

            // Actualización de rareza
            println("Introduce la nueva rareza de la carta (o presiona ENTER para mantener el actual):")
            val newRarity = readLine()
            if (!newRarity.isNullOrBlank()) {
                card.rarity = newRarity
            }

            // Actualización de stock
            println("Introduce el nuevo stock de la carta (o presiona ENTER para mantener el actual):")
            val newStock = readLine()?.toIntOrNull()
            if (newStock != null) {
                card.stock = newStock
            }

            // Actualización de price
            println("Introduce el nuevo precio de la carta (o presiona ENTER para mantener el actual):")
            val newPrice = readLine()?.toDoubleOrNull()
            if (newPrice != null) {
                card.price = newPrice
            }

            // Actualización de discount
            println("¿Tiene descuento la carta? (true/false, o presiona ENTER para mantener el actual):")
            val newDiscount = readLine()?.toBoolean()
            if (newDiscount != null) {
                card.discount = newDiscount
            }

            // Actualización de SKU
            println("Introduce el nuevo SKU de la carta (o presiona ENTER para mantener el actual):")
            val newSKU = readLine()
            if (!newSKU.isNullOrBlank()) {
                card.sku = newSKU
            }

            // Actualización de URL de Imágenes
            println("¿Quieres modificar las URL de las imágenes de la carta? (si/no):")
            if (readLine().equals("si", ignoreCase = true)) {
                cardimagesDao.deleteImagesByCardId(card.cardId, session)  // Eliminar todas las imágenes existentes
                var continuar = true
                while (continuar) {
                    println("Introduce una URL de imagen para la carta (o presiona ENTER para finalizar):")
                    val imageUrl = readLine()
                    if (!imageUrl.isNullOrBlank()) {
                        val image = Cardimages(card.cardId, imageUrl)
                        cardimagesDao.createCardImage(image,session)
                    } else {
                        continuar = false
                    }
                }
            }

            // Actualización de description
            println("Introduce la nueva descripción de la carta (o presiona ENTER para mantener el actual):")
            val newDescription = readLine()
            if (!newDescription.isNullOrBlank()) {
                card.description = newDescription
            }

            // Actualización de Languages
            println("¿Quieres modificar los idiomas de la carta? (si/no):")
            if (readLine().equals("si", ignoreCase = true)) {
                cardlanguagesDao.deleteLanguagesByCardId(card.cardId, session)  // Eliminar todos los idiomas existentes
                var continuar = true
                while (continuar) {
                    println("Introduce un idioma para la carta (o presiona ENTER para finalizar):")
                    val languageText = readLine()
                    if (!languageText.isNullOrBlank()) {
                        val language = Cardlanguages(card.cardId, languageText)
                        cardlanguagesDao.createCardLanguage(language,session)
                    } else {
                        continuar = false
                    }
                }
            }

            // Actualización de size
            println("Introduce el nuevo tamaño de la carta (o presiona ENTER para mantener el actual):")
            val newSize = readLine()
            if (!newSize.isNullOrBlank()) {
                card.size = newSize
            }

            // Actualización de collection ID
            println("Introduce el nuevo ID de la colección para esta carta (o presiona ENTER para mantener el actual):")
            val newCollectionId = readLine()?.toIntOrNull()
            if (newCollectionId != null) {
                val newCollection = collectionsDao.getCollectionById(newCollectionId,session)
                if (newCollection != null) {
                    card.collectionId = newCollectionId
                } else {
                    println("No se encontró una colección con ID $newCollectionId.")
                }
            }

            cardsDao.updateCard(card, session)
            transaction.commit()
            println("Carta actualizada con éxito.")

        } else {
            println("No se encontró una carta con ID $cardId.")
        }
    } catch (e: Exception) {
            transaction.rollback()
            println("Transacción revertida debido a un error. ${e.message}")
        } finally {
            session.close()
        }
    } else {
        println("ID inválido.")
    }
}
public fun ConsultateCard(cardsDao: CardsDao) {
    println("Introduce el ID de la carta que quieres consultar:")
    val cardId = readLine()?.toIntOrNull()
    if (cardId != null) {
        val session = HibernateUtil.getSession().openSession()
        try{
            val card = cardsDao.getCardById(cardId, session)
            val cardtagsDao = CardtagsDao()
            val cardimagesDao = CardimagesDao()
            val cardlanguagesDao = CardlanguagesDao()
            if (card != null) {
                println("Información de la carta:")
                println("ID: ${card.cardId}")
                println("Nombre: ${card.name}")
                // Consultar y mostrar tags
                val tags = cardtagsDao.getCardTagsByCardId(card.cardId,session)
                println("Tags:")
                tags.forEach { tag -> println("- ${tag.tag}") }
                println("Rareza: ${card.rarity}")
                println("Stock: ${card.stock}")
                println("Precio: ${card.price}")
                println("Descuento: ${card.discount}")
                println("SKU: ${card.sku}")
                // Consultar y mostrar urlImages
                val images = cardimagesDao.getCardImagesByCardId(card.cardId,session)
                println("Images:")
                images.forEach { image -> println("- ${image.urlImage}") }
                println("Descripción: ${card.description}")
                // Consultar y mostrar languages
                val languages = cardlanguagesDao.getCardLanguagesByCardId(card.cardId,session)
                println("Languages:")
                languages.forEach { language -> println("- ${language.language}") }
                println("Tamaño: ${card.size}")
                println("Colección ID: ${card.collectionsByCollectionId?.collectionId}")
            } else {
                println("No se encontró una carta con ID $cardId.")
            }
        } finally {
            session.close()
        }
    } else {
        println("ID inválido.")
    }
}

public fun ConsultateCollection(collectionsDao: CollectionsDao) {
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

public fun DeleteCollection(collectionsDao: CollectionsDao, cardsDao: CardsDao, cardtagsDao: CardtagsDao, cardlanguagesDao: CardlanguagesDao, cardimagesDao: CardimagesDao) {
    println("Introduce el ID de la colección a borrar:")
    val collectionId = readLine()?.toIntOrNull()
    if (collectionId != null) {
        val session = HibernateUtil.getSession().openSession()
        val transaction = session.beginTransaction()
        try {
            val collection = collectionsDao.getCollectionById(collectionId, session)
            if (collection != null) {
                // Primero, eliminar todas las cartas de la colección
                val cards = cardsDao.getCardsByCollectionId(collectionId, session)
                for (card in cards) {
                    // Eliminar todas las referencias de la carta
                    cardtagsDao.deleteTagsByCardId(card.cardId, session)
                    cardlanguagesDao.deleteLanguagesByCardId(card.cardId, session)
                    cardimagesDao.deleteImagesByCardId(card.cardId, session)
                    // Luego, eliminar la carta
                    session.delete(card)
                }

                // Luego, eliminar la colección
                session.delete(collection)

                transaction.commit()
                println("Colección con ID $collectionId y todas sus cartas asociadas borradas con éxito.")
            } else {
                println("No se encontró una colección con ID $collectionId.")
                transaction.rollback()
            }
        } catch (e: Exception) {
            transaction.rollback()
            println("Transacción revertida debido a un error. ${e.message}")
            e.printStackTrace()
        } finally {
            session.close()
        }
    } else {
        println("ID inválido.")
    }
}

public fun DeleteCard(cardsDao: CardsDao, cardtagsDao: CardtagsDao, cardlanguagesDao: CardlanguagesDao, cardimagesDao: CardimagesDao, cardId: Int) {
    val session = HibernateUtil.getSession().openSession()
    val transaction = session.beginTransaction()

    try {
        cardsDao.deleteCard(cardId, cardtagsDao, cardlanguagesDao, cardimagesDao, session)

        transaction.commit()
    } catch (e: Exception) {
        transaction.rollback()
        println("Transacción revertida debido a un error.")
    } finally {
        session.close()
    }
}

public fun CreateTagsLanguagesImages(cardsDao: CardsDao) {
    println("Introduce el ID de la carta a la que quieres añadir tags/languages/urlImages:")
    val cardId = readLine()?.toIntOrNull()
    if (cardId != null) {
        val session = HibernateUtil.getSession().openSession()
        val transaction = session.beginTransaction()
        try {
            val card = cardsDao.getCardById(cardId, session)
            if (card != null) {
                addExtraToCard(card, session)
                transaction.commit()  // Hacer commit de la transacción
                println("Tags/languages/urlImages añadidos con éxito.")
            } else {
                println("No se encontró una carta con ID $cardId.")
            }
        } catch (e: Exception) {
            transaction.rollback()  // Hacer rollback en caso de error
            println("Transacción revertida debido a un error.")
        } finally {
            session.close()
        }
    } else {
        println("ID inválido.")
    }
}

public fun CreateCard(collectionsDao: CollectionsDao, cardsDao: CardsDao) {
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
        val transaction = session.beginTransaction()
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

                cardsDao.createCard(card, session)

                // Opción para añadir tags/languages/urlImages
                println("¿Quieres añadir tags/languages/urlImages a la carta? (si/no):")
                val addExtra = readLine()
                if (addExtra.equals("si", ignoreCase = true)) {
                    addExtraToCard(card, session)  // Asegúrate de que esta función usa la sesión que se pasa
                }

                transaction.commit()
                println("Carta creada con éxito.")
            } else {
                println("La colección con ID $collectionId no existe.")
                transaction.rollback()
            }
        } catch (e: Exception) {
            transaction.rollback()
            println("Error al crear la carta: ${e.message}")
        } finally {
            session.close()
        }
    } else {
        println("Datos inválidos.")
    }
}

fun addExtraToCard(card: Cards, session: Session) {
    val cardtagsDao = CardtagsDao()
    var continuar = true
    while (continuar) {
        println("¿Quieres añadir un tag a la carta? (si/no):")
        val addTag = readLine()
        if (addTag.equals("si", ignoreCase = true)) {
            println("Introduce el tag para la carta:")
            val tagText = readLine()
            if (!tagText.isNullOrBlank()) {
                val tag = Cardtags(card.cardId, tagText)
                cardtagsDao.createCardTag(tag,session)
            }
        } else {
            continuar = false
        }
    }

    // Añadir languages
    val cardlanguagesDao = CardlanguagesDao()
    continuar = true
    while (continuar) {
        println("¿Quieres añadir un language a la carta? (si/no):")
        val addLanguage = readLine()
        if (addLanguage.equals("si", ignoreCase = true)) {
            println("Introduce el idioma para la carta:")
            val languageText = readLine()
            if (!languageText.isNullOrBlank()) {
                val language = Cardlanguages(card.cardId, languageText)
                cardlanguagesDao.createCardLanguage(language,session)
            }
        } else {
            continuar = false
        }
    }

    // Añadir urlImages
    val cardimagesDao = CardimagesDao()
    continuar = true
    while (continuar) {
        println("¿Quieres añadir una imagen a la carta? (si/no):")
        val addImage = readLine()
        if (addImage.equals("si", ignoreCase = true)) {
            println("Introduce la URL de la imagen para la carta:")
            val imageUrl = readLine()
            if (!imageUrl.isNullOrBlank()) {
                val image = Cardimages(card.cardId, imageUrl)
                cardimagesDao.createCardImage(image,session)
            }
        } else {
            continuar = false
        }
    }
}


public fun CreateCollection(collectionsDao: CollectionsDao) {
    println("Introduce el nombre de la colección:")
    val name = readLine()
    println("Introduce el tipo de la colección:")
    val type = readLine()
    println("Introduce el año de publicación de la colección:")
    val year = readLine()?.toIntOrNull()
    if (name != null && type != null && year != null) {
        val session = HibernateUtil.getSession().openSession()
        val transaction = session.beginTransaction()
        try {
            collectionsDao.createCollection(Collections(name, type, year), session)
            transaction.commit()
            println("Colección creada con éxito.")
        } catch (e: Exception) {
            transaction.rollback()
            println("Error al crear la colección: ${e.message}")
        } finally {
            session.close()
        }
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