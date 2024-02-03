package classes;

public class Cardlanguages {
    private int cardLanguageId;
    private int cardId;
    private String language;

    public Cardlanguages(int cardId, String language) {
        this.cardId = cardId;
        this.language = language;
    }

    public Cardlanguages() {

    }

    public int getCardLanguageId() {
        return cardLanguageId;
    }

    public void setCardLanguageId(int cardLanguageId) {
        this.cardLanguageId = cardLanguageId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cardlanguages that = (Cardlanguages) o;

        if (cardLanguageId != that.cardLanguageId) return false;
        if (cardId != that.cardId) return false;
        if (language != null ? !language.equals(that.language) : that.language != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cardLanguageId;
        result = 31 * result + cardId;
        result = 31 * result + (language != null ? language.hashCode() : 0);
        return result;
    }
}
