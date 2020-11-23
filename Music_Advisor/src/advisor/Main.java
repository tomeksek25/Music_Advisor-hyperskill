package advisor;
import advisor.controller.MusicAdvisorController;
import advisor.service.Authorisation;
import advisor.service.Service;


public class Main {
    public static void main(String[] args) {

        for (int i = 0; i < args.length - 1; i++) {
            if ("-access".equals(args[i])) {
                Authorisation.setServerPath(args[i + 1]);
            } else if ("-resource".equals(args[i])) {
                Service.setApiServerPath(args[i + 1]);
            } else if ("-page".equals(args[i])) {
                Service.setPAGINATION(Integer.parseInt(args[i + 1]));
            }
        }

        MusicAdvisorController musicAdvisorController = new MusicAdvisorController();
        musicAdvisorController.run();
    }
}
