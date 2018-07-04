package r.com.popular_movie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
   public static List<MovieDetailData>moviesDatas=new ArrayList<>();
    CustomMovieArrayAdapter customMovieArrayAdapter;
    ListView listView;
    private static Retrofit retrofit = null;
    public static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w342//";
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private final static String API_KEY = "6f0ebed546cd90804f9b8bbd4ac47673";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectAndGetApiData();
        customMovieArrayAdapter = new CustomMovieArrayAdapter(this, moviesDatas);
        // Simplification: Using a ListView instead of a RecyclerView
        ListView listView =(ListView) findViewById(R.id.movie_listV);
        listView.setAdapter(customMovieArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                launchDetailActivity(position);
            }
        });
    }

   private void launchDetailActivity(int position) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_POSITION, position);
        startActivity(intent);
    }

    public void connectAndGetApiData(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        MovieApi movieApiService = retrofit.create(MovieApi.class);

        Call<DataResponse> call = movieApiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                List<MovieDetailData> movies = response.body().getResults();
                Log.d(TAG, "Number of movies received: " + movies.size());

            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }
}
