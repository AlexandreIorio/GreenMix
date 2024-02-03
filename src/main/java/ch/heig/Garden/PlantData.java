package ch.heig.Garden;

public class PlantData {
    private final String name;
    private final int duration;
    private final int harvest;
    private final double purchasePrice;
    private final double sellingPrice;

    public PlantData(String name, int duration, int harvest, double purchasePrice, double sellingPrice) {
        this.name = name;
        this.duration = duration;
        this.harvest = harvest;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
    }

    public PlantData(PlantType plantType) {
        this.name = plantType.name();
        this.duration = plantType.duration;
        this.harvest = plantType.harvest;
        this.purchasePrice = plantType.purchasePrice;
        this.sellingPrice = plantType.sellingPrice;
    }

    // Getters
    public String getName() { return name; }
    public int getDuration() { return duration; }
    public int getHarvest() { return harvest; }
    public double getPurchasePrice() { return purchasePrice; }
    public double getSellingPrice() { return sellingPrice; }
}
