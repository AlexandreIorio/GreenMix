package ch.heig.Potion;

import ch.heig.Garden.Plant;

public class MultiplicarePotion extends Potion {
    // region Private Const
    private static final int HARVEST_MULTIPLICATION_FACTOR = 2;
    // endregion

    // region ctor
    public MultiplicarePotion() {
        super(15.0);
    }
    // endregion

    // region Overrided Method
    @Override
    public void usePotion(Plant plant) {
        plant.multiplyHarvest(this);
    }
    // endregion

    // region Public Method
    public int getHarvestMultiplicationFactor() {
        return HARVEST_MULTIPLICATION_FACTOR;
    }
    // endregion
}
