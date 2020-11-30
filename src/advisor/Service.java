package advisor;

public class Service {

    Authorisation authorisation;
    private String apiServerPath = "https://api.spotify.com";
    private boolean isAuthorised = false;

    public Service(Authorisation authorisation) {
        this.authorisation = authorisation;
    }
    public boolean isAuthorised() {
        return isAuthorised;
    }


    public String getApiServerPath() {
        return apiServerPath;
    }



    public void setApiServerPath(String apiServerPath) {
        this.apiServerPath = apiServerPath;
    }


    public void setAuthorization() {
        authorisation.setAccessCode();
        authorisation.setAccessToken();
        this.isAuthorised = true;
    }


}
