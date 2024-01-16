package ch.heig.Potion;

import ch.heig.Garden.Plant;

public abstract class Potion {
    // region Private Parameters
    private final double price;
    // endregion

    // region ctor
    protected Potion(double price) {
        this.price = price;
    }
    // endregion

    // region Public Methods
    public double getPrice() {
        return price;
    }
    // endregion

    // region Public abstract Method
    public abstract void usePotion(Plant plant);
    // endregion

    // region Static Potion Factory
    public static class PotionFactory {
        public static Potion createPotion(PotionType type) {
            return switch (type) {
                case REDUCTO -> new ReductoPotion();
                case MULTIPLICARE -> new MultiplicarePotion();
            };
        }
    }
    // endregion
}
