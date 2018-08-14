package r.com.popular_movie;

import java.util.List;

public class VideoDataResponse {
    int id;
    List<MovieTrailerModel>results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MovieTrailerModel> getResults() {
        return results;
    }

    public void setMovieTrailerModels(List<MovieTrailerModel> results) {
        this.results = results;
    }
}
