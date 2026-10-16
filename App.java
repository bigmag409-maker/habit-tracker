import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class App {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/health", ex -> send(ex, "{\"status\":\"ok\"}"));

        server.createContext("/habits", ex -> send(ex,
            "[{\"id\":1,\"name\":\"Пить воду\",\"streak\":3},"
          + "{\"id\":2,\"name\":\"Зарядка\",\"streak\":5},"
          + "{\"id\":3,\"name\":\"Чтение 20 минут\",\"streak\":2}]"));

        server.start();
        System.out.println("Habit tracker API started on port " + port);
    }

    private static void send(HttpExchange ex, String body) throws IOException {
        System.out.println("Request: " + ex.getRequestMethod() + " " + ex.getRequestURI());
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        ex.sendResponseHeaders(200, bytes.length);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(bytes);
        }
    }
}