package advisor;

import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Authorisation {


    private String serverPath = "https://accounts.spotify.com";
    private final String redirectUri = "http://localhost:8080";
    private final String clientId = "03e5aae2c546474897e68d8e63e4406d";
    private final String clientSecret = "12d8c12d22744da188db3e8bcae7c1d8";
    private String accessToken = "";
    private String accessCode = "";


    public void setAccessCode() {

        String uri = serverPath + "/authorize"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=code";

        System.out.println("use this link to request the access code: ");
        System.out.println(uri);

        try {
            HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            server.start();
            server.createContext("/",
                    new HttpHandler() {

                        @Override
                        public void handle(HttpExchange exchange) throws IOException{
                            String query = exchange.getRequestURI().getQuery();
                            String msg;

                            if (query != null && query.contains("code")) {
                                accessCode = query.substring(query.indexOf('=') + 1);
                                System.out.println("code received");
                                System.out.println(accessCode);
                                msg = "Got the code. Return back to your program.";

                            } else {
                                msg = "Not found authorization code. Try again.";
                            }
                            exchange.sendResponseHeaders(200, msg.length());
                            exchange.getResponseBody().write(msg.getBytes());
                            exchange.getResponseBody().close();
                        }
                    });

            System.out.println("waiting for code...");
            while (accessCode.length() == 0) {
                Thread.sleep(5);
            }
            server.stop(10);


        } catch (IOException  | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setAccessToken() {

        System.out.println("making http request for access_token...");
        System.out.println("response:");

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(serverPath + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=authorization_code"
                                + "&code=" + accessCode
                                + "&client_id=" + clientId
                                + "&client_secret=" + clientSecret
                                + "&redirect_uri=" + redirectUri))
                .build();

        try {

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assert response != null;
            System.out.println(response.body());
            accessToken = JsonParser.parseString(response.body()).getAsJsonObject().get("access_token").getAsString();
            System.out.println("---SUCCESS---");

        } catch (InterruptedException | IOException e) {
            System.out.println("Error response");
        }
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }
}
