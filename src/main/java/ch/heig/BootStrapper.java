package ch.heig;

import io.javalin.Javalin;

public class BootStrapper {
    public static final int PORT = 8080;
    public static final double FACTOR = 1.0;    // Allows to multiply the time of plants growing

    public static void main(String[] args) {
        Javalin app = Javalin.create();
        app.get("/", ctx -> ctx.result("Hello, world!"));
        app.start(PORT);
    }
}