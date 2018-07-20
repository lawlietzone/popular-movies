package r.com.popular_movie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

import static r.com.popular_movie.MainActivity.IMAGE_URL_BASE_PATH;
import static r.com.popular_movie.MainActivity.moviesDatas;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    ImageView backdrop;
    ImageView moviePoster;
    @BindViews({R.id.title_tv,R.id.original_title_tv,R.id.rate_population_tv,R.id.rated_tv,R.id.popularity_tv,R.id.original_language_TV,R.id.release_date_tv,R.id.overview_TV,R.id.adult_tv})
    List<TextView> textViewList;
    VideoView videoView;
    MediaController mediaController;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        button=(Button)findViewById(R.id.button);

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

    public void  videoPlay(View view){
        videoView =(VideoView)findViewById(R.id.video_view);
        String video_url="https://www.youtube.com/watch?v=c25GKl5VNeY";
        Uri uri=Uri.parse(video_url);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
    }
}
