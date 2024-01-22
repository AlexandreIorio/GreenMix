package ch.heig.Garden;

import ch.heig.Customer.Customer;
import ch.heig.Potion.Potion;
import ch.heig.Potion.PotionType;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import io.javalin.http.staticfiles.Location;

public class PlantationController {
    // region public Const
    public static final int PORT = 80;
    // endregion

    // region Private Methods
    private static boolean growPlant(Customer customer, PlantType type) {
        Plant plant = new Plant(type);
        return customer.plantPlant(plant);
    }

    private static boolean harvestPlant(Customer customer, int plantId) {
        Plant plant = customer.getPlantById(plantId);
        if (plant == null) {
            throw new NullPointerException();
        }
        return customer.harvestPlant(plant);
    }

    public static boolean usePotion(Customer customer, PotionType type, int plantId) {
        Plant plant = customer.getPlantById(plantId);
        if (plant == null) {
            throw new NullPointerException();
        }
        Potion potion = Potion.PotionFactory.createPotion(type);
        return customer.usePotion(potion, plant);
    }
    // endregion

    // region static methods
    public static String getMenuHtml(Customer customer){
        StringBuilder sb = new StringBuilder();
        sb.append(getHeaderHtml() +
                "<h1>Mon wallet</h1>\n");
        sb.append("<p>Solde du compte: ").append(customer.getWallet()).append(" $</p>\n");
        sb.append("<a href=\"/mygarden\">Mon jardin</a>\n");
        sb.append("<h1>Liste des plantes</h1>\n");

        for (PlantType p : PlantType.values()) {
            sb.append("<li><a href=\"/growview/").append(p).append("\">")
                    .append(p).append("&nbsp;").append("cost: ").append(p.getPurchasePrice()).append(" $")
                    .append(" &nbsp; ").append("duration: ").append(p.getDuration()).append(" s")
                    .append(" &nbsp; ").append("harvest: ").append(p.getHavrest()).append(" buds")
                    .append(" &nbsp; ").append("Profit: ").append(p.getSellingPrice() * p.getHavrest()).append(" $")
                    .append("</a></li>\n");

        }
        return sb.toString();
    }
    public static String getGardenHtml(Customer customer) {
        StringBuilder sb = new StringBuilder();
        sb.append(getHeaderHtml() +
                "<h1>Mon jardin</h1>" +
                "<table>");
        int i = 0;
        for (Plant p : customer.getPlants()) {
            if (i == 0) {
                sb.append("<tr>");
            }
            sb.append("<td>");

            if (p != null) {
                String cssClass = p.getHasGrown() ? "plant-growed" : "plant";

                if (p.getHasGrown()) {
                    sb.append("<a href=\"/harvestview/").append(p.getId()).append("\">");
                }else {
                    sb.append("<a>");
                }

                sb.append("<div class=\""+cssClass+"\">");
                sb.append("<h2>").append(p.getType()).append("</h2>");
                String harvest = p.getHasGrown() ? "Oui" : "Non";
                sb.append("<p>").append("Recoltable: ").append(harvest).append("</p>");
                String image = p.getPlantType().getImageFileName();
                sb.append("<img src=\"").append(image).append("\"/><br>");
                sb.append("</div>");
                sb.append("</a>");
            }
            sb.append("</td>");
            if (i == 2) {
                sb.append("</tr>");
                i = 0;
            } else {
                ++i;
            }

        }
        sb.append("</table>");

        sb.append("<a href=\"/menu\">Menu</a>\n");
        return sb.toString();
    }
    public static String getIsGrowingHtml(PlantType type) {
        StringBuilder sb = new StringBuilder();
        sb.append(getHeaderHtml() +
                "<h1>Youpiiii</h1>\n");
        sb.append("<p>"+type+" est en train de pousser !"+"</p>\n");
        sb.append("<p>tu pourras la recolter dans "+type.getDuration()+" secondes"+"</p>\n");
        sb.append("<a href=\"/menu\">retourner au menu</a><br>");
        sb.append("<a href=\"/mygarden\">voir mon jardin</a>\n");
        return sb.toString();
    }
    public static String getHarvestedHtml(double profit) {
        StringBuilder sb = new StringBuilder();
        sb.append(getHeaderHtml() +
                "<h1>Bravo</h1>\n");
        sb.append("<p>Belle recolte !"+"</p>\n");
        sb.append("<p>tu as gagne "+profit+" $</p>\n");
        sb.append("<a href=\"/menu\">retourner au menu</a><br>\n");
        sb.append("<a href=\"/mygarden\">voir mon jardin</a>\n");
        return sb.toString();
    }
    public static String getHeaderHtml() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<link rel=\"stylesheet\" href=\"style.css\">\n" +
                "</head>";
    }

    // endregion

    // region Main with HTTP Request
    public static void main(String[] args) {
        int port = PORT;

        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }

        Javalin app = Javalin.create().start(port);

        Customer customer = new Customer("Jerry", 5.0);

        // Menu
        app.get("/menu", ctx -> {
            ctx.status(HttpStatus.OK);
            ctx.html(getMenuHtml(customer));
        });
        // Customer
        app.get("/profile", ctx ->  {
            ctx.status(HttpStatus.OK);
            ctx.json(customer);
        });

        app.get("/mygarden", ctx -> {
            ctx.status(HttpStatus.OK);
            ctx.html(getGardenHtml(customer));
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

        // Plantation
        app.get("/grow/{plantType}", ctx -> {
            String plantType = "";
            try {
                plantType = ctx.pathParam("plantType").toUpperCase();
                PlantType type = PlantType.valueOf(plantType);

                if (growPlant(customer, type)) {
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

        app.get("/growview/{plantType}", ctx -> {
            String plantType = "";
            try {
                plantType = ctx.pathParam("plantType").toUpperCase();
                PlantType type = PlantType.valueOf(plantType);

                if (growPlant(customer, type)) {
                    ctx.status(HttpStatus.OK);
                    ctx.html(getIsGrowingHtml(type));
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

                if (harvestPlant(customer, id)) {
                    double newWalletValue = customer.getWallet();
                    double profit = newWalletValue - oldWalletValue;
                    ctx.status(HttpStatus.OK);
                    ctx.result("Récolte effectuée! Argent gagné: " + profit + ", Nouveau solde du portefeuille: " + customer.getWallet());
                } else {
                    ctx.status(HttpStatus.UNPROCESSABLE_CONTENT);
                    ctx.result("La plante n'a pas terminé de pousser!");
                }
            } catch (NumberFormatException numberFormatException) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.result("Le format du paramètre plantId n'est pas valable !");
            } catch (NullPointerException nullPointerException) {
                ctx.status(HttpStatus.NOT_ACCEPTABLE);
                ctx.result("La plante recherchée n'existe pas !");
            }
        });

        app.get("/harvestview/{plantId}", ctx -> {
            try {
                double oldWalletValue = customer.getWallet();
                int id = Integer.parseInt(ctx.pathParam("plantId"));

                if (harvestPlant(customer, id)) {
                    double newWalletValue = customer.getWallet();
                    double profit = newWalletValue - oldWalletValue;
                    ctx.status(HttpStatus.OK);
                    ctx.html(getHarvestedHtml(profit));
                } else {
                    ctx.status(HttpStatus.UNPROCESSABLE_CONTENT);
                    ctx.result("La plante n'a pas terminé de pousser!");
                }
            } catch (NumberFormatException numberFormatException) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.result("Le format du paramètre plantId n'est pas valable !");
            } catch (NullPointerException nullPointerException) {
                ctx.status(HttpStatus.NOT_ACCEPTABLE);
                ctx.result("La plante recherchée n'existe pas !");
            }
        });

        app.get("/potion/{potionType}/{plantId}", ctx -> {
            String type = "";
            try {
                type = ctx.pathParam("potionType").toUpperCase();
                PotionType potionType = PotionType.valueOf(type);
                int id = Integer.parseInt(ctx.pathParam("plantId"));

                if (usePotion(customer, potionType, id)) {
                    ctx.status(HttpStatus.OK);
                    ctx.result("La potion " + potionType + " a été utilisée avec succès!");
                } else {
                    ctx.status(HttpStatus.PAYMENT_REQUIRED);
                    ctx.result("Pas assez d'argent pour acheter la potion " + potionType);
                }
            }
            catch (NumberFormatException numberFormatException) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.result("Le format du paramètre plantId n'est pas valable !");
            }
            catch (IllegalArgumentException illegalArgumentException) {
                ctx.status(HttpStatus.BAD_REQUEST);
                ctx.result("La potion " + type.toUpperCase() + " n'existe pas !");
            }
            catch (NullPointerException nullPointerException) {
                ctx.status(HttpStatus.NOT_ACCEPTABLE);
                ctx.result("La plante recherchée n'existe pas !");
            }
        });

        app.get("/garden", ctx ->  {
            ctx.status(HttpStatus.OK);
            ctx.json(customer.getPlants());
        });
    }
    // endregion
}