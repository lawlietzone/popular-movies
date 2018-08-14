package r.com.popular_movie;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ReviewList extends RecyclerView.Adapter<ReviewList.NumberViewHolder> {
    private static final String TAG = ReviewList.class.getSimpleName();
    private List<ReviewDetailModel> reviews;
    private Context context;

    public ReviewList(List<ReviewDetailModel> reviews, Context context) {
        this.reviews = reviews;
        this.context = context;
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.custom_review_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return reviews.size();
    }


    class NumberViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView review;

        public NumberViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.author_tv);
            review=(TextView)itemView.findViewById(R.id.review_tv);
        }

        void bind(int listIndex) {
            name.setText(reviews.get(listIndex).getAuthor());
            review.setText(reviews.get(listIndex).getContent());
        }
    }
}