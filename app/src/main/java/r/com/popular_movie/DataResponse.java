package r.com.popular_movie;

import java.util.List;

public class DataResponse {
    int page;
    int total_results;
    int total_pages;
    List<MovieDataModel>results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MovieDataModel> getResults() {
        return results;
    }

    public void setResults(List<MovieDataModel> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return total_results;
    }

    public void setTotalResults(int totalResults) {
        this.total_results = totalResults;
    }

    public int getTotalPages() {
        return total_pages;
    }

    public void setTotalPages(int totalPages) {
        this.total_pages = totalPages;
    }

}
