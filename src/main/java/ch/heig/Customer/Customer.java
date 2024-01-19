package ch.heig.Customer;

import ch.heig.Garden.Plant;
import ch.heig.Potion.Potion;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    // region Public const
    public static final int MAX_PLANTS = 6;
    // endregion

    // region Private Parameters
    private static int nb;
    private final int id = nb++;
    private String username;
    private double wallet;
    private final Plant[] plants;
    private final List<Tool> tools = new ArrayList<>();
    // endregion

    // region Ctor
    public Customer(String username, double wallet) {
        this.username = username + id;
        this.wallet = wallet;
        this.plants = new Plant[MAX_PLANTS];
        this.tools.add(new Tool("Fourche"));
        this.tools.add(new Tool("Cisaille"));
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
            if (plants[i] == null) {
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

    public void addMoney(double money){
        wallet += money;
    }

    public void updateUsername(String username){
        username = username + id;
    }

    public boolean deleteTool(String toolName) {
        return tools.removeIf(tool -> tool.getName().equals(toolName));
    }
    // endregion
}
