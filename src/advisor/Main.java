package advisor;


public class Main {
    public static void main(String[] args) {
        MusicAdvisorController musicAdvisorController = new MusicAdvisorController();
        musicAdvisorController.setServiceArguments(args);
        musicAdvisorController.run();

    }
}
