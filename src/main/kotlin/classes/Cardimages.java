package classes;

public class Cardimages {
    private int cardImageId;
    private int cardId;
    private String urlImage;

    public int getCardImageId() {
        return cardImageId;
    }

    public void setCardImageId(int cardImageId) {
        this.cardImageId = cardImageId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cardimages that = (Cardimages) o;

        if (cardImageId != that.cardImageId) return false;
        if (cardId != that.cardId) return false;
        if (urlImage != null ? !urlImage.equals(that.urlImage) : that.urlImage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cardImageId;
        result = 31 * result + cardId;
        result = 31 * result + (urlImage != null ? urlImage.hashCode() : 0);
        return result;
    }
}
