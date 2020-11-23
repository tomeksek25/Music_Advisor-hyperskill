package advisor.view;

import advisor.service.Service;

public class MusicAdvisorView {

    final String[] output;
    final int numberPerPage;
    final int numberOfPages;
    int page;

    public MusicAdvisorView(String[] output) {
        this.output = output;
        this.numberPerPage = Service.getPAGINATION();
        this.numberOfPages = output.length % numberPerPage == 0
                ? output.length / numberPerPage : output.length / numberPerPage + 1;
        page = 1;
    }

    public void printView() {
        for (int i = (page - 1) * numberPerPage; i < page * numberPerPage && i < output.length; i++) {
            System.out.println(output[i] + "\n");
        }
        System.out.println("---PAGE " + page + " OF " + numberOfPages + "---");
    }

    public void nextPage() {
        if (page == numberOfPages) {
            System.out.println("No more pages.");
        } else {
            page++;
        }
    }

    public void prevPage() {
        if (page == 1) {
            System.out.println("No more pages.");
        } else {
            page--;
        }
    }
}
