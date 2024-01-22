package ch.heig.Garden;

import ch.heig.Potion.MultiplicarePotion;
import ch.heig.Potion.ReductoPotion;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Plant {
    // region Private Parameters
    private int harvest;   // nombre de fruits récupéré à la récolte
    private double duration; // temps pour que la plante pousse [s]

    private double purchasePrice; // prix d'achat de la graine de la plante
    private double sellingPrice;  // prix de vente d'une buds

    private boolean hasGrown; // indique si la plante a finit de pousser

    private static int nb;
    private final int id = nb++;

    private PlantType plantType;


    //private Timer timer;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> growFuture;

    private String type;
    // endregion

    // region Ctor
    public Plant(PlantType type) {
        plantType = type;
        CreatePlant(type);
    }
    // endregion

    // region Public Methods
    public void grow() {
        scheduler = Executors.newScheduledThreadPool(1);
        growFuture = scheduler.schedule(() -> {
            // Logique de croissance ici
            hasGrown = true;
        }, (long) (duration * 1000), TimeUnit.MILLISECONDS);
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
        if (!hasGrown && growFuture != null) {
            long timeRemaining = growFuture.getDelay(TimeUnit.SECONDS);
            growFuture.cancel(true);
            int newDuration = (int) (timeRemaining * reductoPotion.getGrowthReductionFactor());
            this.duration = newDuration;
            growFuture = scheduler.schedule(() -> {
                // Logique de croissance ici
                hasGrown = true;
            }, newDuration, TimeUnit.SECONDS);
        }
    }

    private void resetTimer() {
        if (growFuture != null) {
            growFuture.cancel(true);
        }
        grow();
    }

    public void multiplyHarvest(MultiplicarePotion multiplicarePotion) {
        this.harvest *= multiplicarePotion.getHarvestMultiplicationFactor();
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

    public boolean getHasGrown() {
        return hasGrown;
    }

    public double getDuration() {
        return duration;
    }

    public String getType() {
        return this.type;
    }

    public int getId() {
        return id;
    }
    public PlantType getPlantType() {
        return plantType;
    }

    // endregion

    // region Private Methods
    private void CreatePlant(PlantType type) {
        switch (type) {
            case BITBUD:
                initializePlant(type.getHavrest(), type.getPurchasePrice(), type.getSellingPrice(), type.getDuration(),"BITBUD");
                break;

            case DEBUGDREAM:
                initializePlant(type.getHavrest(), type.getPurchasePrice(), type.getSellingPrice(), type.getDuration(),"DEBUGDREAM");
                break;

            case HASHHACK:
                initializePlant(type.getHavrest(), type.getPurchasePrice(), type.getSellingPrice(), type.getDuration(),"HASHHACK");
                break;

            case BYTEBLOOM:
                initializePlant(type.getHavrest(), type.getPurchasePrice(), type.getSellingPrice(), type.getDuration(), "BYTEBLOOM");
                break;

            case KERNELHAZE:
                initializePlant(1, 15, 21, 30., "KERNELHAZE");
                break;
        }
    }

    private void initializePlant(int harvest, double purchasePrice, double sellingPrice, double duration, String type) {
        this.harvest = harvest;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this. duration = duration;
        this.hasGrown = false;
        this.type = type;
    }
    // endregion
}
