package r.com.popular_movie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

    }
}
