package ch.heig.Garden;

import ch.heig.Customer.Customer;
import ch.heig.Potion.Potion;
import ch.heig.Potion.PotionType;
import io.javalin.Javalin;

public class PlantationController {
    // region public Const
    public static final int PORT = 8080;
    // endregion

    // region Private Methods
    private static boolean growPlant(Customer customer, PlantType type) {
        Plant plant = new Plant(type);
        return customer.plantPlant(plant);
    }

    private static boolean harvestPlant(Customer customer, int plantId) {
        Plant plant = customer.getPlantById(plantId);
        return customer.harvestPlant(plant);
    }

    public static boolean usePotion (Customer customer, PotionType type, int plantId) {
        Plant plant = customer.getPlantById(plantId);
        Potion potion = Potion.PotionFactory.createPotion(type);
        return customer.usePotion(potion, plant);
    }
    // endregion

    // region Main with HTTP Request
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(PORT);
        Customer customer = new Customer("Walid",5.0);

        // Customer
        app.get("/profile", ctx -> {
            ctx.json(customer);
        });

        app.post("/profile/wallet/{money}", ctx -> {
            customer.addMoney(Double.parseDouble(ctx.pathParam("money")));
        });

        app.put("/profile/update/{username}", ctx -> {
            customer.updateUsername(ctx.pathParam("username"));
        });

        app.delete("/profile/tools/{tool}", ctx -> {
            customer.deleteTool(ctx.pathParam("tool"));
        });

        // Plantation
        app.get("/grow/{plantType}",ctx -> {
            PlantType type = PlantType.valueOf(ctx.pathParam("plantType").toUpperCase());
            if (growPlant(customer, type)) {
                ctx.result("La plante " + type + " est en train de pousser!");
            }
            else {
                ctx.result("Pas assez d'argent pour acheter une graine de plante " + type);
            }
        });

        app.get("/harvest/{plantId}",ctx -> {
            double oldWalletValue = customer.getWallet();
            int id = Integer.parseInt(ctx.pathParam("plantId"));
            if (harvestPlant(customer, id)) {
                double newWalletValue = customer.getWallet();
                double profit = newWalletValue - oldWalletValue;
                ctx.result("Récolte effectuée! Argent gagné: " + profit + ", Nouveau solde du portefeuille: " + customer.getWallet());
            }
            else {
                ctx.result("La plante n'a pas terminé de pousser!");
            }
        });

        app.get("/potion/{potionType}/{plantId}", ctx -> {
            PotionType type = PotionType.valueOf(ctx.pathParam("potionType").toUpperCase());
            int id = Integer.parseInt(ctx.pathParam("plantId"));
            if (usePotion(customer, type, id)) {
                ctx.result("La potion " + type + " a été utilisée avec succès!");
            }
            else {
                ctx.result("Pas assez d'argent pour acheter la potion " + type);
            }
        });

        app.get("/garden", ctx -> ctx.json(customer.getPlants()));

    }
    // endregion
}