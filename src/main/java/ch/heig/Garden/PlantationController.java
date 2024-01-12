package ch.heig.Garden;

import io.javalin.Javalin;

public class PlantationController {
    public static final int PORT = 8080;

    public static void growPlant(Customer customer, PlantType type) {
        // TODO WSI : Passer par des const pour éviter d'instancier la plante dès le début
        Plant plant = new Plant(type);

        if (customer.getWallet() < plant.getPurchasePrice()) {
            // Lâche ça chef, t'es pas prêt pour cette fumée
            return;
        }
        customer.addToWallet(-plant.getPurchasePrice());
        customer.addPlantToGarden(plant);

        plant.grow();
    }

    public static void harvestPlant(Customer customer, int id) {
        Plant plant = customer.getPlantById(id);
        if (plant.canHarvest()) {
            // Logique de récolte ici
            double profit = plant.getHarvest() * plant.getSellingPricePrice();
            customer.addToWallet(profit);
            customer.removePlantFromGarden(plant);

            System.out.println("Récolte effectuée! Argent gagné: " + profit + ", Nouveau solde du portefeuille: " + customer.getWallet());
        }
    }


    public static void main(String[] args) {
        Javalin app = Javalin.create().start(PORT);
        Customer customer = new Customer(5.0);

        app.get("/grow/{plantType}",ctx -> {
            PlantType type = PlantType.valueOf(ctx.pathParam("plantType").toUpperCase());
            PlantationController.growPlant(customer, type);
            ctx.result("La plante " + type + " est en train de pousser!");
        });

        app.get("/garden", ctx -> ctx.json(customer.getPlants()));

        app.get("/harvest/{id}",ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            PlantationController.harvestPlant(customer, id);
            ctx.result("Récolte effectuée!");
        });
    }
}