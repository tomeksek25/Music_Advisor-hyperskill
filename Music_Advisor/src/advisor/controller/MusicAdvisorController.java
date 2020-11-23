package advisor.controller;
import advisor.service.Service;
import advisor.service.SpotifyGetMethods;
import advisor.view.MusicAdvisorView;
import java.util.Scanner;

public class MusicAdvisorController {


    public void run() {

        Service service = new Service();
        Scanner scanner = new Scanner(System.in);
        SpotifyGetMethods spotifyGetMethods = new SpotifyGetMethods();
        MusicAdvisorView view;
        String command = "";
        boolean flag = false;

        while (true) {
            command = scanner.next();
            if ("exit".equals(command)) {
                System.out.println("---GOODBYE!---");
                break;
            } else if (service.isAuthorised()) {
                try {
                    String[] output;
                    switch (command) {
                        case "featured":
                            output = spotifyGetMethods.getFeatured();
                            break;
                        case "new":
                            output = spotifyGetMethods.getNew();
                            break;
                        case "categories":
                            output = spotifyGetMethods.getCategory();
                            break;
                        case "playlists":
                            command = scanner.nextLine().substring(1);
                            output = spotifyGetMethods.getPlaylist(command);
                            break;
                        default:
                            output = new String[0];
                            break;
                    }

                    view = new MusicAdvisorView(output);
                    while(!command.equals("exit")) {
                        view.printView();
                        command = scanner.next();
                        if (command.equals("next")) {
                            view.nextPage();
                        } else if (command.equals("prev")) {
                            view.prevPage();
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else if ("auth".equals(command)) {

                service.setAuthorization();
                System.out.println("---SUCCESS---");
            } else {
                System.out.println("Please, provide access for application.");

            }
        }
    }

}
