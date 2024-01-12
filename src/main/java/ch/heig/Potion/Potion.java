package ch.heig.Potion;

public class Potion { // TODO WSI : Maybe make record (we will see in future)
    // region Private Parameters
    private final String name;
    private final double price;
    // endregion

    // region ctor
    public Potion(String name, double price) {
        this.name = name;
        this.price = price;
    }
    // endregion

    // region Public Methods
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    // endregion
}
