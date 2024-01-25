package ch.heig.Garden;

import ch.heig.Customer.Customer;
import ch.heig.Customer.Customers;
import ch.heig.Potion.Potion;
import ch.heig.Potion.PotionType;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;

public class PlantationController {
    public static final int PORT = 80;

    public static void main(String[] args) {
        Customers.createUser("Jerry", "5f4dcc3b5aa765d61d8327deb882cf99");

        int port = PORT;




        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }


        Javalin app = Javalin.create().start(port);
        // Configuration des en-têtes CORS
        app.before(ctx -> {
            ctx.header("Access-Control-Allow-Origin", "null"); // Autoriser l'origine locale
            ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
            ctx.header("Access-Control-Allow-Credentials", "true"); // Autoriser les cookies
        });

        Customer customer = new Customer("Jerry", 5.0);

        // create connect request with username and password
        app.get("/connect/{username}/{password}", ctx -> {
            String username = ctx.pathParam("username");
            String password = ctx.pathParam("password");

            if (username.isBlank() || username.isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.result("Le format du paramètre {username} n'est pas valable !");
            } else if (password.isBlank() || password.isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.result("Le format du paramètre {password} n'est pas valable !");
            } else if (Customers.resolveUser(username, password) != null) {
                ctx.cookie(Customers.logUser(username));
                ctx.status(HttpStatus.OK);
                ctx.result("Connexion reussite !");
            } else {
                ctx.status(HttpStatus.UNAUTHORIZED);
                ctx.result("Connexion echouee !");
            }
        });

        app.get("/profile", ctx -> {
            ctx.status(HttpStatus.OK);
            ctx.json(customer);
        });

        app.post("/profile/wallet/{money}", ctx -> {
            try {
                customer.addMoney(Double.parseDouble(ctx.pathParam("money")));
                ctx.status(HttpStatus.OK);
            } catch (NumberFormatException numberFormatException) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.result("Le format du paramètre {money} n'est pas valable !");
            }
        });

        app.put("/profile/update/{username}", ctx -> {
            String username = ctx.pathParam("username");

            if (username.isBlank() || username.isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.result("Le format du paramètre {username} n'est pas valable !");
            } else {
                customer.updateUsername(username);
                ctx.status(HttpStatus.OK);
                ctx.result("Modification du username réussite !");
            }
        });

        app.delete("/profile/tool/{tool}", ctx -> {
            String toolToRemove = ctx.pathParam("tool");
            boolean result = customer.deleteTool(toolToRemove);

            if (result) {
                ctx.status(HttpStatus.OK);
                ctx.result("L'outil " + toolToRemove.toUpperCase() + " a été supprimé avec succès !");
            } else {
                ctx.status(HttpStatus.NOT_ACCEPTABLE);
                ctx.result("Impossible de supprimer l'outil " + toolToRemove.toUpperCase() + " car il n'existe pas !");
            }
        });

        app.get("/grow/{plantType}", ctx -> {
            String plantType = "";
            try {
                plantType = ctx.pathParam("plantType").toUpperCase();
                PlantType type = PlantType.valueOf(plantType);

                if (customer.plantPlant(type)) {
                    ctx.status(HttpStatus.OK);
                    ctx.result("La plante " + type + " est en train de pousser!");
                } else {
                    ctx.status(HttpStatus.PAYMENT_REQUIRED);
                    ctx.result("Pas assez d'argent pour acheter une graine de plante " + type);
                }
            } catch (IllegalArgumentException illegalArgumentException) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.result("La plante " + plantType.toUpperCase() + " n'existe pas !");
            }
        });

        app.get("/harvest/{plantId}", ctx -> {
            try {
                double oldWalletValue = customer.getWallet();
                int id = Integer.parseInt(ctx.pathParam("plantId"));

                if (customer.harvestPlant(id)) {
                    double newWalletValue = customer.getWallet();
                    double profit = newWalletValue - oldWalletValue;
                    ctx.status(HttpStatus.OK);
                    ctx.result("Récolte effectuée! Argent gagné: " + profit + ", Nouveau solde du portefeuille: " + customer.getWallet());
                } else {
                    ctx.status(HttpStatus.UNPROCESSABLE_CONTENT);
                    ctx.result("La plante n'a pas terminé de pousser!");
                }
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.result("Le format du paramètre plantId n'est pas valable !");
            } catch (NullPointerException e) {
                ctx.status(HttpStatus.NOT_ACCEPTABLE);
                ctx.result(e.getMessage());
            }
        });

        app.get("/potion/{potionType}/{plantId}", ctx -> {
            try {
                String type = ctx.pathParam("potionType").toUpperCase();
                PotionType potionType = PotionType.valueOf(type);
                int id = Integer.parseInt(ctx.pathParam("plantId"));

                if (usePotion(customer, potionType, id)) {
                    ctx.status(HttpStatus.OK);
                    ctx.result("La potion " + potionType + " a été utilisée avec succès!");
                } else {
                    ctx.status(HttpStatus.PAYMENT_REQUIRED);
                    ctx.result("Pas assez d'argent pour acheter la potion " + potionType);
                }
            } catch (NumberFormatException numberFormatException) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.result("Le format du paramètre plantId n'est pas valable !");
            } catch (IllegalArgumentException illegalArgumentException) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.result("La potion " + ctx.pathParam("potionType").toUpperCase() + " n'existe pas !");
            } catch (NullPointerException nullPointerException) {
                ctx.status(HttpStatus.NOT_ACCEPTABLE);
                ctx.result("La plante recherchée n'existe pas !");
            }
        });

        app.get("/garden", ctx -> {
            ctx.status(HttpStatus.OK);
            ctx.json(customer.getPlants());
        });
    }

    public static boolean usePotion(Customer customer, PotionType type, int plantId) {
        Plant plant = customer.getPlantById(plantId);
        if (plant == null) {
            throw new NullPointerException();
        }
        Potion potion = Potion.PotionFactory.createPotion(type);
        return customer.usePotion(potion, plant);
    }
}