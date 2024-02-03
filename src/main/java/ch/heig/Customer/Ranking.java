package ch.heig.Customer;

import ch.heig.Database.CustomerDAO;

import java.util.*;

public class Ranking {
    public static ArrayList<Ranking> ranking = new ArrayList<Ranking>();
    public String name;
    public long points;
    public int harvested;
    public double wallet;

    private static void computeRanking(){
        Map<String, Customer> customers = CustomerDAO.getCustomers();
        ranking.clear();
        for(Map.Entry<String, Customer> entry : customers.entrySet()){
            Customer customer = entry.getValue();
            Ranking ranking = new Ranking();
            ranking.name = customer.getUsername();
            ranking.points = customer.getXp();
            ranking.harvested = customer.getHarvestedPlants().size();
            ranking.wallet = customer.getWallet();
            Ranking.ranking.add(ranking);
        }// Triez la liste ranking par points
        Collections.sort(ranking, new Comparator<Ranking>() {
            @Override
            public int compare(Ranking r1, Ranking r2) {
                return Long.compare(r2.points, r1.points);            }
        });



    }




    public static Object getRank() {
        computeRanking();
        return ranking;
    }
}
