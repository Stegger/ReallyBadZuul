public class Item {

    private String name;
    private String itemDescription;
    private boolean edible;
    private double itemWeight;

    public Item(String name, String itemDescription, double itemWeight) {
        this.name = name;
        this.itemDescription = itemDescription;
        this.itemWeight = itemWeight;
    }

    public String getName() {
        return name;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public double getItemWeight() {
        return itemWeight;
    }

    public boolean isEdible() {
        return edible;
    }

    public String toString()
    {
        return "Item: " + getName() + ". Description: " + getItemDescription() + ". Weight: " + String.valueOf(getItemWeight());
    }
}
