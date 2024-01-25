package ch.heig.Customer;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;

public class Customers {

    // Map <username, Customer>
    private static final Map<String, Customer> customers = new HashMap<>();

    // Map <cookie, username>
    private static final Map<String, String> loggedUsers = new HashMap<>();

    public static Customer resolveUser(String username, String password) {
        Customer c = customers.get(username);
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

    public static void createUser(String username, String hash) {
        if (customers.containsKey(username)) {
            throw new RuntimeException("User already exists");
        } else {
            customers.put(username, new Customer(username, hash, 0));
        }
    }
}
