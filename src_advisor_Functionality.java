package advisor;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Functionality {
    JsonParser parser = new JsonParser();
    private final String apiPath;
    ServerApi sa;

    public Functionality(ServerApi sa, String apiPath) {
        this.sa = sa;
        System.out.println(sa.accessToken);
        this.apiPath = apiPath;
    }



    public String[] printCategory() throws IOException, InterruptedException {
        String json = sa.GET(apiPath + "/v1/browse/categories");

        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        List<String> output = new ArrayList<>();
        for (JsonElement category : jsonObject.get("categories").getAsJsonObject().get("items").getAsJsonArray()) {
            JsonObject jo = category.getAsJsonObject();
            output.add(jo.get("name").getAsString());
        }
        return output.toArray(new String[0]);
    }


    public String[] printPlaylist(String name) throws IOException, InterruptedException {
        String json = sa.GET(apiPath + "/v1/browse/categories");
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        String id = "Unknown category name.";

        for (JsonElement category : jsonObject.get("categories").getAsJsonObject().get("items").getAsJsonArray()) {
            JsonObject jo = category.getAsJsonObject();

            if (jo.get("name").getAsString().equals(name)) {
                id = jo.get("id").getAsString();
            }
        }
        List<String> output = new ArrayList<>();
        if (id.equals("Unknown category name.")) {
            System.out.println(id);
        } else {
            String jsonTwo = sa.GET(apiPath + "/v1/browse/categories/" + id + "/playlists");
            JsonObject jsonObjectTwo = JsonParser.parseString(jsonTwo).getAsJsonObject();

            if (jsonTwo.contains("error")) {
                System.out.println(jsonObjectTwo.get("error").getAsJsonObject().get("message").getAsString());

            } else {
                for (JsonElement playlist : jsonObjectTwo.get("playlists").getAsJsonObject().get("items").getAsJsonArray()) {
                    JsonObject jo = playlist.getAsJsonObject();
                    output.add(jo.get("name").getAsString() + "\n" + jo.get("external_urls").getAsJsonObject().get("spotify").getAsString());

                }
            }
        }
        return output.toArray(new String[0]);

    }


    public String[] printNew() throws IOException, InterruptedException {
        String json = sa.GET(apiPath + "/v1/browse/new-releases");

        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        StringBuilder artists = new StringBuilder();

        List<String> output = new ArrayList<>();
        for (JsonElement album : jsonObject.get("albums").getAsJsonObject().get("items").getAsJsonArray()) {
            JsonObject jo = album.getAsJsonObject();
            int i = 0;


            for (JsonElement alb : jo.get("artists").getAsJsonArray()) {

                JsonObject joOne = alb.getAsJsonObject();
                if (i > 0) {
                    artists.append(", ");
                }
                artists.append(joOne.get("name").getAsString());
                i++;
            }
            output.add(jo.get("name").getAsString() + "\n[" + artists + "]\n" + jo.get("external_urls").getAsJsonObject().get("spotify").getAsString());
            artists.setLength(0);
        }

        return output.toArray(new String[0]);
    }



    public String[] printFeatured() throws IOException, InterruptedException {
        String json = sa.GET(apiPath + "/v1/browse/featured-playlists");
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();


        List<String> output = new ArrayList<>();
        for (JsonElement album : jsonObject.get("playlists").getAsJsonObject().get("items").getAsJsonArray()) {
            JsonObject jo = album.getAsJsonObject();
            output.add(jo.get("name").getAsString() + "\n" + jo.get("external_urls").getAsJsonObject().get("spotify").getAsString());

        }
        return output.toArray(new String[0]);
    }
}
