package r.com.popular_movie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import java.net.IDN;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static r.com.popular_movie.MainActivity.API_KEY;
import static r.com.popular_movie.MainActivity.BASE_URL;
import static r.com.popular_movie.MainActivity.IMAGE_URL_BASE_PATH;
import static r.com.popular_movie.MainActivity.moviesDatas;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final  String BASE_YOUTUBE_URL="https://www.youtube.com/watch?v=";
    private static final  String TAG="ADAM";
    ImageView backdrop;
    ImageView moviePoster;
    @BindViews({R.id.title_tv,R.id.original_title_tv,R.id.rate_population_tv,R.id.rated_tv,R.id.popularity_tv,R.id.original_language_TV,R.id.release_date_tv,R.id.overview_TV,R.id.adult_tv})
    List<TextView> textViewList;
    Button button;
    private static Retrofit retrofit = null;
    List<MovieTrailerModel>trailerModels;
    List<String> movieKey=new ArrayList<>();
    List<ReviewDetailModel>reviewDetailModels;
    private RecyclerView mNumbersList;
    private ReviewList reviewList;
    int sha=0;
    int movieCategorize;
    Boolean favor;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        MovieApi movieApiService = retrofit.create(MovieApi.class);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        final int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }
        String image_url = IMAGE_URL_BASE_PATH + moviesDatas.get(position).getPosterPath();
        movieUI(image_url,position);
         int id=moviesDatas.get(position).getId();
        Call<VideoDataResponse> call = movieApiService.getMovieVideo(id,API_KEY);
        connectAndGetApiData(call);
        Call<ReviewDataReponse>reviewCall=movieApiService.getMovieReviews(id,API_KEY);
        reviewCall(reviewCall);
        MaterialFavoriteButton materialFavoriteButtonNice = (MaterialFavoriteButton) findViewById(R.id.favorite_button);
        getData(moviesDatas.get(position).getTitle());
        if(favor==true){
            materialFavoriteButtonNice.setFavorite(true);
        }

        db=new DatabaseHelper(this);

        materialFavoriteButtonNice.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                if(favorite){
                    saveData(favorite,moviesDatas.get(position).getTitle());
                    db.insertData(moviesDatas.get(position));

                }else {
                    saveData(false,moviesDatas.get(position).getTitle());

                }
            }
        });
        textViewList.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchDetailActivity();
            }
        });
    }

    public void saveData(boolean data,String key){
        SharedPreferences prefs = getSharedPreferences(
                "adam", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key,data);
        editor.commit();
    }

    public void getData(String key){
        SharedPreferences prefs=getSharedPreferences("adam", Context.MODE_PRIVATE);
        favor=prefs.getBoolean(key,false);
    }

    public void reviewCall(Call<ReviewDataReponse> call) {
        call.enqueue(new Callback<ReviewDataReponse>() {
            @Override
            public void onResponse(Call<ReviewDataReponse> call, Response<ReviewDataReponse> response) {
                reviewDetailModels = response.body().getResults();
                Log.d("sha",String.valueOf(reviewDetailModels.size()));
                if(reviewDetailModels.size()!=0) {
                    mNumbersList = (RecyclerView) findViewById(R.id.rv_numbers);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    mNumbersList.setLayoutManager(layoutManager);

                    mNumbersList.setHasFixedSize(true);

                    reviewList = new ReviewList(reviewDetailModels,getApplicationContext());

                    mNumbersList.setAdapter(reviewList);
                }

            }

            @Override
            public void onFailure(Call<ReviewDataReponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }

    private void movieUI(String image_url,int position){
        moviePoster=(ImageView)findViewById(R.id.movie_IV);
        Picasso.with(this)
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(moviePoster);
        textViewList.get(0).setText(moviesDatas.get(position).getTitle());
        textViewList.get(1).setText(moviesDatas.get(position).getOriginalTitle());
        textViewList.get(2).setText(String.valueOf( moviesDatas.get(position).getVote_count()));
        textViewList.get(3).setText(String.valueOf( moviesDatas.get(position).getVote_average()));
        textViewList.get(4).setText(String.valueOf( moviesDatas.get(position).getPopularity()));
        textViewList.get(5).setText(moviesDatas.get(position).getOriginalLanguage());
        textViewList.get(6).setText(moviesDatas.get(position).getReleaseDate());
        textViewList.get(7).setText(moviesDatas.get(position).getOverview());
        textViewList.get(8).setText(String.valueOf(moviesDatas.get(position).isAdult()));
    }

    public void  videoPlay(String video_url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(video_url));
        startActivity(i);
    }


    public void connectAndGetApiData(Call<VideoDataResponse>call){
        call.enqueue(new Callback<VideoDataResponse>() {
            @Override
            public void onResponse(Call<VideoDataResponse> call, Response<VideoDataResponse> response) {
                trailerModels = response.body().getResults();
                for(int c=0;c<trailerModels.size();c++){
                    movieKey.add( "Trailer"+String.valueOf(c+1)+"  play");
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,movieKey );
                ListView listView=findViewById(R.id.trailer_lv);
                listView.setAdapter(adapter);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) listView.getLayoutParams();
                layoutParams.height = movieKey.size()*170;
                listView.setLayoutParams(layoutParams);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        videoPlay(BASE_YOUTUBE_URL+trailerModels.get(position).getKey());
                    }
                });

            }

            @Override
            public void onFailure(Call<VideoDataResponse> call, Throwable throwable) {
                Log.e("dee", throwable.toString());
            }
        });
    }

    private void launchDetailActivity() {
        Intent intent = new Intent(this, favorite.class);
        startActivity(intent);
    }


}
