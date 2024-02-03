package ch.heig.Customer;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.crypto.Data;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;
import java.util.logging.Logger;

import ch.heig.Database.CustomerDAO;
import ch.heig.Database.Database;

public class Customers {
    static {

        customers = CustomerDAO.getCustomers();
        tryCustomer();
    }

    private static final double INITIAL_AMOUNT = 5.0;
    public static void tryCustomer() {
        for (Map.Entry<String, Customer> c : customers.entrySet()) {
            System.out.println("Customer: " + c.getValue().getUsername());
        }
    }
    // Map <username, Customer>
    private static Map<String, Customer> customers;

    // Map <cookie, username>
    private static final Map<String, String> loggedUsers = new HashMap<>();

    public static Customer resolveUser(String username, String password) {

        Customer c = customers.get(username);
        if (c == null) {
            return null;
        }
        if (c.connect(password)) {
            return c;
        } else {
            return null;
        }
    }

    public static Customer resolveCookie(String cookie) {
        return customers.get(loggedUsers.get(cookie));
    }

    public static String logUser(String username) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        SecretKeyFactory factory;

        KeySpec spec = new PBEKeySpec(username.toCharArray(), salt, 65536, 128);
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            loggedUsers.put(hash.toString(), username);
            return hash.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateLoggedUser(String cookie, String username) {
        loggedUsers.put(cookie, username);
    }

    public static void createUser(String username, String passwordHash) {
        if (customers.containsKey(username)) {
            throw new RuntimeException("User already exists");
        } else {
            Customer customer = new Customer(username, passwordHash, INITIAL_AMOUNT);
            //create and get inserted customer with id
            customer = CustomerDAO.createCustomer(customer);
            customers.put(username, customer);
        }
    }

    public static void updateCustomer(Customer customer) {
        customers = CustomerDAO.getCustomers();
    }
}
