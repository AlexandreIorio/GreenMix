package ch.heig.Garden;

import ch.heig.Potion.Potion;

public class Customer {
    public static final int MAX_PLANTS = 6;
    public static final int NB_POTIONS = 2;

    // region Private Parameters
    private double wallet;
    private final Plant[] plants;
    private final Potion[] potions;
    // endregion

    // region Ctor
    public Customer(double wallet) {
        this.wallet = wallet;
        this.plants = new Plant[MAX_PLANTS];

        this.potions = new Potion[NB_POTIONS];
        this.potions[0] = new Potion("Réducto", 10.0); // Potion de réduction du temps de pousse
        this.potions[1] = new Potion("Multiplicare", 15.0); // potion de multiplication de la récolte
    }
    // endregion

    // region Public Methods
    public void useTimeReductionPotion(Plant plant) { // TODO WSI : ENUM POTION ?
        double price = potions[0].getPrice();
        if (wallet >= price) {
            wallet -= price;
            plant.reduceGrowTime(); // Méthode à ajouter dans la classe Plant
            System.out.println("Potion utilisée : " + potions[0].getName());
        } else {
            System.out.println("Pas assez d'argent pour acheter la potion.");
        }
    }

    public void useHarvestMultiplierPotion(Plant plant) {
        double price = potions[1].getPrice();
        if (wallet >= price) {
            wallet -= price;
            plant.multiplyHarvest(); // Méthode à ajouter dans la classe Plant
            System.out.println("Potion utilisée : " + potions[1].getName());
        } else {
            System.out.println("Pas assez d'argent pour acheter la potion.");
        }
    }

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

    public Potion[] getPotions() {
        return potions;
    }
    // endregion
}
