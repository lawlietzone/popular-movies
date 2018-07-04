package r.com.popular_movie;

import java.util.List;

public class DataResponse {
    int page;
    int total_results;
    int total_pages;
    List<MovieDetailData>results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MovieDetailData> getResults() {
        return results;
    }

    public void setResults(List<MovieDetailData> results) {
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
