package ch.heig.Customer;

import ch.heig.Database.CustomerDAO;
import ch.heig.Database.PlantDAO;
import ch.heig.Garden.Garden;
import ch.heig.Garden.Plant;
import ch.heig.Garden.PlantType;
import ch.heig.Item.Potion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {

    public static final double LEVEL_FACTOR = 2;
    public static final double PROGRESS_FACTOR = 3;
    public static final double SPACE_FACTOR = 0.1;

    // endregion

    // region Private Parameters
    private String username;
    private String email;
    private String hashcode;
    private double wallet;
    private final ArrayList<Plant> plants = new ArrayList<>();
    private final ArrayList<Plant> havrestedPlants = new ArrayList<>();
    private final ArrayList<Tool> tools = new ArrayList<>();
    private int plantSpace = 0;
    private int id;
    // endregion


    // region Ctor

    public Customer(int id, String username, String hash, double wallet, int plantSpace, String email) {
        this(id, username, hash, wallet);
        this.plantSpace = plantSpace;
        this.email = email;
    }
    public Customer(int id, String username, String hash, double wallet) {
        this(username,hash, wallet);
        this.id = id;
    }
    public Customer(String username, String hash, double wallet) {
        this(username, wallet);
        this.hashcode = hash;
    }
    public Customer(String username, double wallet) {
        this.username = username;
        this.wallet = wallet;
        this.tools.add(new Tool("FOURCHE"));
        this.tools.add(new Tool("CISAILLE"));
    }
    // endregion

    // region Public Methods

    // Getter method

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getHashcode() {
        return hashcode;
    }

    public double getWallet() {
        return wallet;
    }

    @JsonProperty("xp")
    public long getXp() {
        long xp = 0;
        for (Plant plant : havrestedPlants) {
            xp += (int)plant.getPurchasePrice();
        }
        return xp;
    }

    @JsonProperty("gardenSize")
    public int getGardenSize() {
        return Garden.defineNbPlant(this);
    }

    public int getPlantSpace() { return plantSpace; }
    @JsonProperty("spacePrice")
    public double getPlantSpacePrice() {
        return ((plantSpace + 1)/SPACE_FACTOR)*((plantSpace)/SPACE_FACTOR);
    }

    @JsonProperty("level")
    public int getLevel() {
        return (int) Math.floor(Math.pow(getXp() / LEVEL_FACTOR, 1 / PROGRESS_FACTOR));
    }

    @JsonProperty("currentLevelXp")
    public long getXpCurrentLevel() {
        //xp = (unit ** progress_factore) * xpConstante
        return (long)(Math.pow(getLevel(), PROGRESS_FACTOR) * LEVEL_FACTOR);
    }
    @JsonProperty("nextLevelXp")
    public long getXpToNextLevel() {
        return (long)(Math.pow(getLevel() + 1, PROGRESS_FACTOR) * LEVEL_FACTOR);
    }

    public long getXpToLevel(int level) {
        return (int)Math.pow((level / LEVEL_FACTOR), 2);
    }

    public ArrayList<Plant> getPlants() {
        return plants;
    }

    public List<Tool> getTools() {
        return tools;
    }

    // setter method
    public void setId(int id) {
        this.id = id;
    }

    // API method
    public void addMoney(double money) {
        wallet += money;
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public boolean deleteTool(String toolName) {
        return tools.removeIf(tool -> tool.getName().equals(toolName.toUpperCase()));
    }

    // Plantation method
    public boolean plantPlant(PlantType type) {

        if (wallet < type.getPurchasePrice()) {
            return false;
        } else {
            Plant plant = new Plant(type, this.id);
            wallet -= plant.getPurchasePrice();
            addPlantToGarden(plant);
            return true;
        }
    }

    public boolean addPlantSpace(){
        double price = getPlantSpacePrice();
        if (wallet < price) {
            return false;
        } else {
            wallet -= price;
            plantSpace += 1;
            CustomerDAO.updateCustomer(this);
            return true;
        }
    }

    public boolean harvestPlant(Plant plant) {
        if (plant.hasGrown()) {
            double profit = plant.getHarvest() * plant.getSellingPricePrice();
            wallet += profit;
            removePlantFromGarden(plant);
            addHavrestedPlant(plant);
            PlantDAO.havrestPlant(this, plant);
            return true;
        } else {
            return false;
        }
    }

    public boolean harvestPlant(int targetId) {
        Plant plant = getPlantById(targetId);

        if (plant == null)
            throw new NullPointerException("Plant not found");
        else
            return harvestPlant(plant);

    }

    public boolean usePotion(Potion potion, Plant plant) {
        if (wallet < potion.getPrice()) {
            return false;
        } else {
            wallet -= potion.getPrice();
            //potion.usePotion(plant);
            return true;
        }
    }

    public Plant getPlantById(int targetId) {
        for (Plant plant : plants) {
            if (plant != null && plant.getId() == targetId) {
                return plant;
            }
        }
        return null;
    }

    public void addPlantToGarden(Plant plant) {
        if (plant == null) return;
        plants.add(plant);
    }

    public void removePlantFromGarden(Plant plant) {
        plants.remove(plant);
    }

    public boolean connect(String hashcode) {
        return this.hashcode.equals(hashcode);
    }

    public void setGarden(Map<Integer, Plant> plants) {
        if (plants == null)
            return;
        for (Plant plant : plants.values()) {
            addPlantToGarden(plant);
        }
    }

    public ArrayList<Plant> getHarvestedPlants() {
       return havrestedPlants;
    }
    public void setHarvestedPlants(Map<Integer, Plant> harvestedPlants) {
        if (harvestedPlants == null)
            return;
        for (Plant plant : harvestedPlants.values()) {
            addHavrestedPlant(plant);
        }
    }

    public String getEmail() {
        return email;
    }

    private void addHavrestedPlant(Plant plant) {
        if (plant == null) return;
        havrestedPlants.add(plant);
    }


    // endregion
}
