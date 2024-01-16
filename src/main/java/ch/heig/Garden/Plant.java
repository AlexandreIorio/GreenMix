package ch.heig.Garden;

import ch.heig.Potion.MultiplicarePotion;
import ch.heig.Potion.ReductoPotion;

import java.util.Timer;
import java.util.TimerTask;

public class Plant {
    // region Private Parameters
    private int harvest;   // nombre de fruits récupéré à la récolte
    private double duration; // temps pour que la plante pousse [s]

    private double purchasePrice; // prix d'achat de la graine de la plante
    private double sellingPrice;  // prix de vente d'une buds

    private boolean hasGrown; // indique si la plante a finit de pousser

    private static int nb;
    private final int id = nb++;

    private Timer timer;
    // endregion

    // region Ctor
    public Plant(PlantType type) {
        CreatePlant(type);
    }
    // endregion

    // region Public Methods
    public void grow() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Logique de croissance ici
                hasGrown = true;
            }
        }, (long) (duration * 1000));
    }

    public boolean canHarvest() {
        if (hasGrown) {
            hasGrown = false;
            return true;
        }
        else {
            return false;
        }
    }

    public void reduceGrowTime(ReductoPotion reductoPotion) {
        this.duration *= reductoPotion.getGrowthReductionFactor();

        if (!hasGrown) {
            resetTimer();
        }
    }

    private void resetTimer() {
        // Réinitialiser le minuteur avec la nouvelle durée restante
        timer.cancel();;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Logique de croissance ici
                hasGrown = true;
            }
        }, (long) (duration * 1000));
    }

    public void multiplyHarvest(MultiplicarePotion multiplicarePotion) {
        this.harvest *= multiplicarePotion.getHarvestMultiplicationFactor();
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
                initializePlant(3, 3, 5, 5.);
                break;

            case B:
                initializePlant(1, 8, 11, 15.);
                break;

            case C:
                initializePlant(2, 5, 9, 10.);
                break;

            case D:
                initializePlant(1, 10, 14, 20.);
                break;

            case E:
                initializePlant(1, 15, 21, 30.);
                break;
        }
    }

    private void initializePlant(int harvest, double purchasePrice, double sellingPrice, double duration) {
        this.harvest = harvest;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this. duration = duration;
        this.hasGrown = false;
    }
    // endregion
}
