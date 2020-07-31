package advisor;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class ServerApi {
    private String code = "";
    private final String fullAccessLink;
    private final String accessLink;
    public String accessToken;
    HttpClient client;
    HttpServer server;



    ServerApi(String fullAccessLink, String accessLink, int port) throws IOException {


        this.fullAccessLink = fullAccessLink;
        this.accessLink = accessLink;
        client = HttpClient.newBuilder().build();
        server = HttpServer.create();
        server.bind(new InetSocketAddress(port), 0);

        server.createContext("/",
                new HttpHandler() {
                    public void handle(HttpExchange exchange) throws IOException {

                        String query = exchange.getRequestURI().getQuery();

                        String msg;

                        if (query == null) {
                            query = "";
                        }
                        if (query.contains("code=")) {
                            msg = "Got the code. Return back to your program.";
                            code = query.substring(query.indexOf('=') + 1);

                        } else {
                            msg = "Not found authorization code. Try again.";
                        }

                        exchange.sendResponseHeaders(200, msg.length());
                        exchange.getResponseBody().write(msg.getBytes());
                        exchange.getResponseBody().close();

                    }
                }
        );
    }

    public void server() throws InterruptedException {
        server.start();

        System.out.println("use this link to request the access code:");
        System.out.println(fullAccessLink);
        System.out.println("waiting for code...");

        while (code.equals("")) {
            Thread.sleep(5);

        }
        server.stop(10);


    }

    public String GET(String fullPath) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(fullPath))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();


    }





    public void POST() throws IOException, InterruptedException {
        String req = "grant_type=code&authorization_code=" + this.code + "&redirect_uri=" + "http://localhost:8080";
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(accessLink + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(req))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonElement jsonElement = new JsonParser().parse(response.body());
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        accessToken = jsonObject.get("access_token").getAsString(); //parseToken

    }

}