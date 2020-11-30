package advisor;
import java.util.Scanner;

public class MusicAdvisorController {

    private final Scanner scanner;
    private final SpotifyGetMethods spotifyGetMethods;
    private final Authorisation authorisation;
    private int pages = 5;


    public MusicAdvisorController() {
        this.authorisation = new Authorisation();
        this.scanner = new Scanner(System.in);
        this.spotifyGetMethods = new SpotifyGetMethods(authorisation);

    }

    public void setServiceArguments(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            if ("-access".equals(args[i])) {
                authorisation.setServerPath(args[i + 1]);
            } else if ("-resource".equals(args[i])) {
                spotifyGetMethods.setApiServerPath(args[i + 1]);
            } else if ("-page".equals(args[i])) {
                this.pages = Integer.parseInt(args[i + 1]);
            }
        }
    }

    public void run() {

        String command;

        while (true) {
            command = scanner.next();
            if ("exit".equals(command)) {
                System.out.println("---GOODBYE!---");
                break;
            } else if (spotifyGetMethods.isAuthorised()) {
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

                    MusicAdvisorView view = new MusicAdvisorView(output, pages);
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
                spotifyGetMethods.setAuthorization();
                System.out.println("---SUCCESS---");
            } else {
                System.out.println("Please, provide access for application.");

            }
        }
    }

}
