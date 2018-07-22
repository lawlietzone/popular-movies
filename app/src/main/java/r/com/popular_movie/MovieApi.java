package r.com.popular_movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("movie/top_rated")
    Call<DataResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<DataResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<DataResponse> getMovieVideo(@Path("id") int id,@Query("api_key") String apiKey);

    @GET("movie/id/reviews")
    Call<DataResponse> getMovieReview(@Query("api_key") String apiKey,@Path("id") int groupId);

}
