package classes;

public class Cardtags {
    private int cardTagId;
    private int cardId;
    private String tag;

    public int getCardTagId() {
        return cardTagId;
    }

    public void setCardTagId(int cardTagId) {
        this.cardTagId = cardTagId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cardtags cardtags = (Cardtags) o;

        if (cardTagId != cardtags.cardTagId) return false;
        if (cardId != cardtags.cardId) return false;
        if (tag != null ? !tag.equals(cardtags.tag) : cardtags.tag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cardTagId;
        result = 31 * result + cardId;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }
}
