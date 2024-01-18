package ch.heig.Customer;

import ch.heig.Garden.Plant;
import ch.heig.Potion.Potion;

public class Customer {
    // region Public const
    public static final int MAX_PLANTS = 6;
    // endregion

    // region Private Parameters
    private double wallet;
    private final Plant[] plants;
    // endregion

    // region Ctor
    public Customer(double wallet) {
        this.wallet = wallet;
        this.plants = new Plant[MAX_PLANTS];
    }
    // endregion

    // region Public Methods
    public boolean plantPlant(Plant plant) {
        if (wallet < plant.getPurchasePrice()) {
            return false;
        }
        else {
            wallet -= plant.getPurchasePrice();
            addPlantToGarden(plant);
            plant.grow();
            return true;
        }
    }

    public boolean harvestPlant(Plant plant) {
        if (plant.canHarvest()) {
            double profit = plant.getHarvest() * plant.getSellingPricePrice();
            wallet += profit;
            removePlantFromGarden(plant);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean usePotion(Potion potion, Plant plant) {
        if (wallet < potion.getPrice()) {
            return false;
        }
        else {
            wallet -= potion.getPrice();
            potion.usePotion(plant);
            return true;
        }
    }

    public double getWallet() {
        return wallet;
    }

    public Plant[] getPlants() {
        return plants;
    }

    public Plant getPlantById(int targetId) {
        for (Plant plant : plants) {
            if (plant.getId() == targetId) {
                return plant;
            }
        }
        throw new ArrayStoreException("Pas de plante avec l'id demandé");
    }

    public void addPlantToGarden(Plant plant) {
        for (int i = 0; i < plants.length; ++ i) {
            if (plants[i] != null) {
                plants[i] = plant;
                break;
            }
        }
    }

    public void removePlantFromGarden(Plant plant) {
        for (int i = 0; i < plants.length; i++) {
            if (plants[i] == plant) {
                plants[i] = null;
                break;
            }
        }
    }
    // endregion
}
