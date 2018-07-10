package r.com.popular_movie;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static r.com.popular_movie.MainActivity.IMAGE_URL_BASE_PATH;

/**
 * Created by chongfei on 2017/8/4.
 */

public class CustomMovieArrayAdapter extends ArrayAdapter<MovieDataModel> {
    private Context context;
    private List<MovieDataModel> values;


    public CustomMovieArrayAdapter(Context context, List<MovieDataModel> values) {
        super(context, R.layout.movies_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.movies_item, parent, false);
        ImageView moviePoster = (ImageView) rowView.findViewById(R.id.movies_iv);
        Log.d("adam",String.valueOf(values.get(position).getOriginalTitle()));
        setMovieImage(position,moviePoster);
        return rowView;
    }

    public void setMovieImage(int position,ImageView moviePoster){
        String image_url = IMAGE_URL_BASE_PATH + values.get(position).getPosterPath();
        Log.d("dex",image_url);
        Picasso.with(context)
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(moviePoster);
    }
}
