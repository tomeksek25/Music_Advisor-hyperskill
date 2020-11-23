package advisor.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class HttpMethods extends Service{

    public Optional<String> GET(String path) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + Authorisation.getAccessToken())
                .uri(URI.create(getApiServerPath() + path))
                .GET()
                .build();
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assert response != null;
            return Optional.of(response.body());

        } catch (InterruptedException | IOException e) {
            System.out.println("Error response");
            return Optional.empty();
        }



    }


}
