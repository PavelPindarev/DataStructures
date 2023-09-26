package model;

public class Invoice extends BaseEntity {
    private int priceCents;
    private String description;

    public Invoice(int id, int parentId) {
        super(id, parentId);
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int getId() {
        return super.getId();
    }

    public int getPriceCents() {
        return priceCents;
    }

    public void setPriceCents(int priceCents) {
        this.priceCents = priceCents;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
