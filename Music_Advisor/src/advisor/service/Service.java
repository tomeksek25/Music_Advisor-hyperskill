package advisor.service;

public class Service {

    private static String API_SERVER_PATH = "https://api.spotify.com";
    private static int  PAGINATION = 5;
    private boolean isAuthorised = false;

    public boolean isAuthorised() {
        return isAuthorised;
    }


    public static int getPAGINATION() {
        return PAGINATION;
    }

    public static String getApiServerPath() {
        return API_SERVER_PATH;
    }

    public static void setApiServerPath(String apiServerPath) {
        API_SERVER_PATH = apiServerPath;
    }

    public static void setPAGINATION(int PAGINATION) {
        Service.PAGINATION = PAGINATION;
    }

    public void setAuthorization() {
        Authorisation authorisation = new Authorisation();
        authorisation.setAccessCode();
        authorisation.setAccessToken();
        this.isAuthorised = true;
    }
}
