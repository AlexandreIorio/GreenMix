package ch.heig;

import io.javalin.Javalin;

public class BootStrapper {
    public static final int PORT = 8080;

    public static void main(String[] args) {
        Javalin app = Javalin.create();
        app.get("/", ctx -> ctx.result("Hello, world!"));
        app.start(PORT);
    }
}