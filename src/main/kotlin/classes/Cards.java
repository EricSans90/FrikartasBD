package classes;

public class Cards {
    private int cardId;
    private String name;
    private String rarity;
    private Integer stock;
    private Double price;
    private Boolean discount;
    private String sku;
    private String description;
    private String size;
    private Integer collectionId;
    private Collections collectionsByCollectionId;


    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getDiscount() {
        return discount;
    }

    public void setDiscount(Boolean discount) {
        this.discount = discount;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cards cards = (Cards) o;

        if (cardId != cards.cardId) return false;
        if (name != null ? !name.equals(cards.name) : cards.name != null) return false;
        if (rarity != null ? !rarity.equals(cards.rarity) : cards.rarity != null) return false;
        if (stock != null ? !stock.equals(cards.stock) : cards.stock != null) return false;
        if (price != null ? !price.equals(cards.price) : cards.price != null) return false;
        if (discount != null ? !discount.equals(cards.discount) : cards.discount != null) return false;
        if (sku != null ? !sku.equals(cards.sku) : cards.sku != null) return false;
        if (description != null ? !description.equals(cards.description) : cards.description != null) return false;
        if (size != null ? !size.equals(cards.size) : cards.size != null) return false;
        if (collectionId != null ? !collectionId.equals(cards.collectionId) : cards.collectionId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cardId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (rarity != null ? rarity.hashCode() : 0);
        result = 31 * result + (stock != null ? stock.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + (sku != null ? sku.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (collectionId != null ? collectionId.hashCode() : 0);
        return result;
    }

    public Collections getCollectionsByCollectionId() {
        return collectionsByCollectionId;
    }

    public void setCollectionsByCollectionId(Collections collectionsByCollectionId) {
        this.collectionsByCollectionId = collectionsByCollectionId;
    }
}
