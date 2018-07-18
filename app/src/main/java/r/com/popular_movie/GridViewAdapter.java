package r.com.popular_movie;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static r.com.popular_movie.MainActivity.IMAGE_URL_BASE_PATH;

/**
 * Created by chongfei on 2017/8/4.
 */

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<MovieDataModel> values;
    View view;


    public GridViewAdapter(Context context, List<MovieDataModel> values) {
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null){
            view=new View(context);
            view=inflater.inflate(R.layout.movies_item,null);
        } else {
            view=(View)convertView;
        }
        ImageView moviePoster = (ImageView) view.findViewById(R.id.movies_iv);
        setMovieImage(position,moviePoster);
        return view;
    }

    public void setMovieImage(int position,ImageView moviePoster){
        String image_url = IMAGE_URL_BASE_PATH + values.get(position).getBackdropPath();
        Picasso.with(context)
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(moviePoster);
    }
}
