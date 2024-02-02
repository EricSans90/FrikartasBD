package classes;

import java.util.Collection;

public class Collections {
    private int collectionId;
    private String name;
    private String collectType;
    private Integer publicationYear;
    private Collection<Cards> cardsByCollectionId;

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollectType() {
        return collectType;
    }

    public void setCollectType(String collectType) {
        this.collectType = collectType;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Collections that = (Collections) o;

        if (collectionId != that.collectionId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (collectType != null ? !collectType.equals(that.collectType) : that.collectType != null) return false;
        if (publicationYear != null ? !publicationYear.equals(that.publicationYear) : that.publicationYear != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = collectionId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (collectType != null ? collectType.hashCode() : 0);
        result = 31 * result + (publicationYear != null ? publicationYear.hashCode() : 0);
        return result;
    }

    public Collection<Cards> getCardsByCollectionId() {
        return cardsByCollectionId;
    }

    public void setCardsByCollectionId(Collection<Cards> cardsByCollectionId) {
        this.cardsByCollectionId = cardsByCollectionId;
    }
}
