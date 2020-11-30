package advisor;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpotifyGetMethods extends HttpMethods {


    public SpotifyGetMethods(Authorisation authorisation) {
        super(authorisation);
    }

    public String[] getCategory() throws IOException, InterruptedException {
        Optional<String> response = super.GET("/v1/browse/categories");
        if (response.isPresent()) {
            JsonObject jsonObject = JsonParser.parseString(response.get()).getAsJsonObject();

            List<String> output = new ArrayList<>();
            for (JsonElement category : jsonObject.get("categories").getAsJsonObject().get("items").getAsJsonArray()) {
                JsonObject jo = category.getAsJsonObject();
                output.add(jo.get("name").getAsString());
            }
            return output.toArray(new String[0]);
        } else {
            return new String[0];
        }
    }


    public String[] getPlaylist(String name) throws IOException, InterruptedException {
        Optional<String> response = super.GET("/v1/browse/categories");
        if (response.isPresent()) {
            JsonObject jsonObject = JsonParser.parseString(response.get()).getAsJsonObject();
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
                Optional<String> responseTwo = super.GET("/v1/browse/categories/" + id + "/playlists");
                if (responseTwo.isPresent()) {
                    JsonObject jsonObjectTwo = JsonParser.parseString(responseTwo.get()).getAsJsonObject();

                    if (responseTwo.get().contains("error")) {
                        System.out.println(jsonObjectTwo.get("error").getAsJsonObject().get("message").getAsString());

                    } else {
                        for (JsonElement playlist : jsonObjectTwo.get("playlists").getAsJsonObject()
                                .get("items").getAsJsonArray()) {

                            JsonObject jo = playlist.getAsJsonObject();

                            output.add(jo.get("name").getAsString() + "\n" + jo.get("external_urls").getAsJsonObject()
                                    .get("spotify").getAsString());

                        }
                    }
                } else {
                    return new String[0];
                }
            }
            return output.toArray(new String[0]);
        } else {
            return new String[0];
        }

    }


    public String[] getNew() throws IOException, InterruptedException {
        Optional<String> response = super.GET("/v1/browse/new-releases");

        if(response.isPresent()) {
            JsonObject jsonObject = JsonParser.parseString(response.get()).getAsJsonObject();
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
                output.add(jo.get("name")
                        .getAsString()
                        + "\n[" + artists + "]\n" +
                        jo.get("external_urls")
                                .getAsJsonObject()
                                .get("spotify")
                                .getAsString());
                artists.setLength(0);
            }

            return output.toArray(new String[0]);
        } else {
            return new String[0];
        }
    }



    public String[] getFeatured() throws IOException, InterruptedException {
        Optional<String> response = super.GET("/v1/browse/featured-playlists");
        if (response.isPresent()) {
            JsonObject jsonObject = JsonParser.parseString(response.get()).getAsJsonObject();


            List<String> output = new ArrayList<>();
            for (JsonElement album : jsonObject.get("playlists").getAsJsonObject().get("items").getAsJsonArray()) {
                JsonObject jo = album.getAsJsonObject();
                output.add(jo.get("name").getAsString()
                        + "\n" +
                        jo.get("external_urls").getAsJsonObject().get("spotify").getAsString());

            }
            return output.toArray(new String[0]);
        } else {
            return new String[0];
        }
    }
}
