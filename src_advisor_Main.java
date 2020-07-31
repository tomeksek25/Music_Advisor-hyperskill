package advisor;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException{
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        int port = rand.nextInt(8000) + 5000;
        String command = "";
        boolean auth = false;
        String accessLink = "https://accounts.spotify.com";
        String accessApi = "https://api.spotify.com";
        int page = 5;
        for (int i = 0; i < args.length - 1; i++) {

            if ("-access".equals(args[i])) {
                accessLink = args[i + 1];
            } else if ("-resource".equals(args[i])) {
                accessApi = args[i + 1];
            } else if ("-page".equals(args[i])) {
                page = Integer.parseInt(args[i + 1]);
            }
        }

        String fullAccessLink = accessLink + "/authorize?client_id=03e5aae2c546474897e68d8e63e4406d&redirect_uri=http://localhost:" + port + "&response_type=code";
        ServerApi ap = new ServerApi(fullAccessLink, accessLink, port);
        Functionality p = new Functionality(ap, accessApi);
        Controller control = new Controller(ap, p, page);
        control.runController();

    }
}

