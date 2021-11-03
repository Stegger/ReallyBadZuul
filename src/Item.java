public class Item {

    private String itemDescription;
    private double itemWeight;

    public Item(String itemDescription, double itemWeight) {
        this.itemDescription = itemDescription;
        this.itemWeight = itemWeight;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setItemWeight(double itemWeight) {
        this.itemWeight = itemWeight;
    }

    public String toString()
    {
        return "Item: " + itemDescription + ". Weight: " + String.valueOf(itemWeight);
    }
}
