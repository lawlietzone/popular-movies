package r.com.popular_movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static r.com.popular_movie.MainActivity.IMAGE_URL_BASE_PATH;
import static r.com.popular_movie.MainActivity.moviesDatas;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    ImageView moviePoster;
    TextView title;
    TextView originalTitle;
    TextView totalRated;
    TextView rated;
    TextView popularity;
    TextView originalLanguage;
    TextView releaseDate;
    TextView overView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String image_url = IMAGE_URL_BASE_PATH + moviesDatas.get(position).getPosterPath();
        movieUI(image_url,position);
    }


    private void closeOnError() {
        finish();
        Toast.makeText(this, "wrong data", Toast.LENGTH_SHORT).show();
    }

    private void movieUI(String image_url,int position){
        moviePoster=(ImageView)findViewById(R.id.movie_IV);
        Picasso.with(this)
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(moviePoster);
        title=(TextView)findViewById(R.id.title_tv);
        title.setText(moviesDatas.get(position).getTitle());
        originalTitle=(TextView)findViewById(R.id.original_title_tv);
        originalTitle.setText(moviesDatas.get(position).getOriginalTitle());
        totalRated=(TextView)findViewById(R.id.rate_population_tv);
        totalRated.setText(String.valueOf( moviesDatas.get(position).getVote_count()));
        rated=(TextView)findViewById(R.id.rated_tv);
        rated.setText(String.valueOf( moviesDatas.get(position).getVote_average()));
        popularity=(TextView)findViewById(R.id.popularity_tv);
        popularity.setText(String.valueOf( moviesDatas.get(position).getPopularity()));
        originalLanguage=(TextView)findViewById(R.id.original_language_TV);
        originalLanguage.setText(moviesDatas.get(position).getOriginalLanguage());
        releaseDate=(TextView)findViewById(R.id.release_date_tv);
        releaseDate.setText(moviesDatas.get(position).getReleaseDate());
        overView=(TextView)findViewById(R.id.overview_TV);
        overView.setText(moviesDatas.get(position).getOverview());


    }
}
