package r.com.popular_movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static r.com.popular_movie.MainActivity.API_KEY;
import static r.com.popular_movie.MainActivity.BASE_URL;
import static r.com.popular_movie.MainActivity.moviesDatas;

public class AudienceReview extends AppCompatActivity{
    List<ReviewDetailModel> reviewDetailModels;
    String TAG="ADAM";
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static Retrofit retrofit = null;
    private ReviewList reviewList;
    private RecyclerView mNumbersList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_list);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        MovieApi movieApiService = retrofit.create(MovieApi.class);
        Intent intent=getIntent();

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }
        Log.d("x",String.valueOf(position));
        int id=moviesDatas.get(position).getId();
        Call<ReviewDataReponse>reviewCall=movieApiService.getMovieReviews(id,API_KEY);
        reviewCall(reviewCall);
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
}
