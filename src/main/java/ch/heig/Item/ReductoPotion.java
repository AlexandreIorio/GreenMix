package ch.heig.Item;

import ch.heig.Garden.Plant;

public class ReductoPotion extends Potion {
    // region Private Const
    private static final double GROWTH_REDUCTION_FACTOR = 0.5;
    // endregion

    // region ctor
    public ReductoPotion() {
        //super(10.0);
    }
    // endregion

    // region Public Overrided Method
    @Override
    public void usePotion(Plant plant) {
        plant.reduceGrowTime(this);
    }
    // endregion

    // region Public Method
    public double getGrowthReductionFactor() {
        return GROWTH_REDUCTION_FACTOR;
    }
    // endregion
}
