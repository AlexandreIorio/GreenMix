package ch.heig.Garden;

import java.util.Timer;
import java.util.TimerTask;

public class Plant {
    // region Private Parameters
    private int harvest; // nombre de buds récupéré à la récolte
    private long duration; // temps pour que la plante pousse

    private double purchasePrice; // prix d'achat de la graine de la plante
    private double sellingPrice; // prix de vente d'une buds

    private boolean hasGrown; // indique si la plante a finit de pousser

    // TODO WSI : Les facteurs doivent être en double vrmt force au schedule (duration aussi)
    private int timeReductionFactor; // facteur de réduction du temps => potion Reducto
    private int harvestMultiplier; // facteur de multiplication des récoltes => potion Multiplicare

    private static int nb;
    private final int id = nb++;
    // endregion

    // region Ctor
    public Plant(PlantType type) {
        CreatePlant(type);
    }
    // endregion

    // region Public Methods
    public void grow() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() { // TODO WSI : Est peut être bloquant -> 1 thread par plante ?
                // Logique de croissance ici
                hasGrown = true;
                System.out.println("La plante a poussé!");
            }
        }, duration * 1000);
    }

    public boolean canHarvest() {
        if (hasGrown) {
            // Logique de récolte ici
            hasGrown = false; // Réinitialiser l'état de croissance
            System.out.println("La plante a été récoltée!");
            return true;
        } else {
            System.out.println("La plante n'a pas encore poussé.");
            return false;
        }
    }

    // TODO WSI : A tester
    public void reduceGrowTime() {
        this.duration /= timeReductionFactor;
        System.out.println("Temps de pousse réduit!");
    }

    public void multiplyHarvest() {
        this.harvest *= harvestMultiplier;
        System.out.println("Nombre de récolte multiplié!");
    }

    public int getHarvest() {
        return harvest;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public double getSellingPricePrice() {
        return sellingPrice;
    }

    public int getId() {
        return id;
    }
    // endregion

    // region Private Methods
    private void CreatePlant(PlantType type) {
        switch (type) {
            case A:
                initializePlant(3, 3, 5, 5);
                break;

            case B:
                initializePlant(1, 8, 11, 15);
                break;

            case C:
                initializePlant(2, 5, 9, 10);
                break;

            case D:
                initializePlant(1, 10, 14, 20);
                break;

            case E:
                initializePlant(1, 15, 21, 30);
                break;
        }
    }

    private void initializePlant(int harvest, double purchasePrice, double sellingPrice, long duration) {
        this.harvest = harvest;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this. duration = duration;

        this.hasGrown = false;
        this.harvestMultiplier = 2;
        this.timeReductionFactor = 2;
    }
    // endregion
}
