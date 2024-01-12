package ch.heig.Garden;

public class Customer {
    public static final int MAX_PLANTS = 6;

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

    // region Public Method
    public double getWallet() {
        return wallet;
    }

    public void addToWallet(double money) {
        this.wallet += money;
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
