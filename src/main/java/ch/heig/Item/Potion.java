package ch.heig.Item;

import ch.heig.Garden.Plant;

public abstract class Potion extends Item {

    PotionType potionType;

    abstract public void usePotion(Plant plant);

}
