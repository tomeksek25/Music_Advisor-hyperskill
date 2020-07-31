package advisor;
import java.io.IOException;
import java.util.Scanner;

public class Controller {

    ServerApi ap;
    Functionality p;
    Scanner scanner;
    String command;
    boolean flag;
    int numberPerPage;

    public Controller (ServerApi ap, Functionality p, int n) throws IOException, InterruptedException {
        this.ap = ap;
        this.p = p;
        scanner = new Scanner(System.in);
        this.flag = false;
        this.numberPerPage = n;
    }

    public void runController() throws IOException, InterruptedException {
        while (true) {
            command = scanner.next();
            if ("exit".equals(command)) {
                System.out.println("---GOODBYE!---");
                break;
            }
            if (flag) {
                String[] output;
                switch (command) {
                    case "featured":
                        output = p.printFeatured();
                        break;
                    case "new":
                        output = p.printNew();
                        break;
                    case "categories":
                        output = p.printCategory();
                        break;
                    case "playlists":
                        command = scanner.nextLine().substring(1);
                        output = p.printPlaylist(command);
                        break;
                    default:
                        output = new String[0];
                        break;
                }
                View view = new View(output, numberPerPage);
                while(!command.equals("exit")) {
                    view.printView();
                    command = scanner.next();
                    if (command.equals("next")) {
                        view.nextPage();
                    } else if (command.equals("prev")) {
                        view.prevPage();
                    }
                }
            } else if ("auth".equals(command)) {

                ap.server();
                ap.POST();
                System.out.println("---SUCCESS---");
                flag = true;

            } else {
                System.out.println("Please, provide access for application.");

            }
        }
    }
}
