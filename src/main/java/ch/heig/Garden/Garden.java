package ch.heig.Garden;

import ch.heig.Customer.Customer;

public class Garden {

    private final static int LEVEL_STEP = 5;
    private final static int NB_PLANT_STEP = 1;
    public static int defineNbPlant(Customer customer) {
        customer.getLevel();
        int nbPlants = 1;
        nbPlants += ((int)customer.getLevel() / LEVEL_STEP) * NB_PLANT_STEP;
        nbPlants += customer.getPlantSpace();
        return nbPlants;
    }
}
