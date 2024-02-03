package ch.heig.Garden;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum PlantType {

    TURBOFEUILLASSE(1, 2, 1.0, 1),
    BITBUD(5, 5, 5.0, 2.0),
    DEBUGDREAM(15, 1, 13.0, 30.0),
    HASHHACK(10, 3, 10.0, 7.0),
    BYTEBLOOM(20, 1, 30.0, 35.0),
    KERNELHAZE(30, 1, 50.0, 50.0),
    PIXELPOT(40, 8, 20.0, 8.0),
    JAVAJOINT(50, 5, 25.0, 20.0),
    CYBERCUSH(60, 3, 30.0, 42.0),
    DATAWEED(70, 5, 35.0, 32.0),
    CLOUDKUSH(400, 8, 180.0, 30.0),
    QUANTUMSKUNK(3600, 10, 800.0, 150.0);
    final int duration;
    final int harvest;
    final double purchasePrice;
    final double sellingPrice;

    static List<PlantData> plantDatas = Arrays.stream(PlantType.values())
            .map(plantType -> new PlantData(plantType.name(), plantType.duration, plantType.harvest, plantType.purchasePrice, plantType.sellingPrice))
            .collect(Collectors.toList());

    PlantType(int duration, int harvest, double purchasePrice, double sellingPrice) {
        this.duration = duration;
        this.harvest = harvest;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
    }

    public int getDuration() {
        return duration;
    }

    public int getHarvest() {
        return harvest;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }


}