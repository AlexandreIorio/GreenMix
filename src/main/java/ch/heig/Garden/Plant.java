package ch.heig.Garden;

import ch.heig.Database.PlantDAO;
import ch.heig.Item.MultiplicarePotion;
import ch.heig.Item.ReductoPotion;

import java.util.Objects;

public class Plant {
    // region Private Parameters
    private double durationFactor = 1; // facteur de réduction/augmentation de temps de pousse
    private double quantityFactor = 1; // facteur de réduction/augmentation de quantité de buds
    private long plantingTime; // heure de plantation
    private int id;


    private PlantData plantType;
    // endregion

    // region Ctor

    public Plant(PlantType type, int ownerId) {
        this.plantType = new PlantData(type);
        plantingTime = System.currentTimeMillis()/1000;
        this.id = Objects.requireNonNull(PlantDAO.createPlant(this, ownerId)).id;
    }

    public Plant(int id, String type, double duration_factor, double quantity_factor, long plantingTime) {
        this.plantType = new PlantData(PlantType.valueOf(type));
        this.id = id;
        this.durationFactor = duration_factor;
        this.quantityFactor = quantity_factor;
        this.plantingTime = plantingTime;
    }
    // endregion

    public boolean hasGrown() {
        return plantingTime + (long) (plantType.getDuration() * durationFactor) < System.currentTimeMillis()/1000;
    }

    public void reduceGrowTime(ReductoPotion reductoPotion) {
        this.durationFactor *= reductoPotion.getGrowthReductionFactor();
    }

    public double getDurationFactor(){
        return durationFactor;
    }

    public double getQuantityFactor(){
        return quantityFactor;
    }

    public long getPlantingTime() {
        return plantingTime;
    }

    public void multiplyHarvest(MultiplicarePotion multiplicarePotion) {
        //this.quantityFactor *= multiplicarePotion.getHarvestMultiplicationFactor();
    }

    public int getHarvest() {
        return plantType.getHarvest() ;
    }

    public double getPurchasePrice() {
        return plantType.getPurchasePrice();
    }

    public double getSellingPricePrice() {
        return plantType.getSellingPrice();
    }

    public PlantData getPlantType() {
        return plantType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    // endregion


}