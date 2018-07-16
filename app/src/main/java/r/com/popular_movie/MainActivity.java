package r.com.popular_movie;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "adam";
    public static List<MovieDataModel>moviesDatas=new ArrayList<>();
    public static List<MovieDataModel>movieDataModelList=new ArrayList<>();
    CustomMovieArrayAdapter customMovieArrayAdapter;
    ListView listView;
    private static Retrofit retrofit = null;
    public static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w342//";
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private final static String API_KEY = "your key";
    private static final String STATE_MOVIE = "STATE_MOVIE";
    private static final String sharedPreferences="pref";
    private static final String KEY="KEY";
    private static final int DEFAULT_VALUE=-1;
    int movieCategorize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        MovieApi movieApiService = retrofit.create(MovieApi.class);
        getData();
        if (movieCategorize == 1) {
            getSupportActionBar().setTitle(R.string.popular_movie_label);
            Call<DataResponse> call = movieApiService.getPopularMovies(API_KEY);
            connectAndGetApiData(call);
        } else {
            getSupportActionBar().setTitle(R.string.top_rate_movie_label);
            Call<DataResponse> call = movieApiService.getTopRatedMovies(API_KEY);
            connectAndGetApiData(call);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        MovieApi movieApiService = retrofit.create(MovieApi.class);
        if(id == R.id.top_rated){
            Call<DataResponse> call = movieApiService.getTopRatedMovies(API_KEY);
            connectAndGetApiData(call);
            getSupportActionBar().setTitle(R.string.top_rate_movie_label);
            saveData(0);
        }
        if(id==R.id.popular){
            Call<DataResponse> call = movieApiService.getPopularMovies(API_KEY);
            connectAndGetApiData(call);
            getSupportActionBar().setTitle(R.string.popular_movie_label);
            saveData(1);
        }

        return super.onOptionsItemSelected(item);
    }



   private void launchDetailActivity(int position) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_POSITION, position);
        startActivity(intent);

    }


    public void connectAndGetApiData(Call<DataResponse>call){

        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
               moviesDatas = response.body().getResults();
                customMovieArrayAdapter = new CustomMovieArrayAdapter(getApplicationContext(), moviesDatas);
                listView =(ListView) findViewById(R.id.movie_listV);
                listView.setAdapter(customMovieArrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        launchDetailActivity(position);
                    }
                });
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }

    public void saveData(int data){
        SharedPreferences prefs = getSharedPreferences(
                sharedPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY,data);
        editor.apply();
    }

    public void getData(){
        SharedPreferences prefs=getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE);
        movieCategorize=prefs.getInt(KEY,DEFAULT_VALUE);
    }
}
