package sandip.upgrad_test_sandip.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.florent37.picassopalette.PicassoPalette;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sandip.upgrad_task.R;
import sandip.upgrad_test_sandip.obj.Movies;
import sandip.upgrad_test_sandip.widgets.MyTextView;

/**
 * Created by Sumit on 26/06/2016.
 */
public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Movies> al_Movies;
    Context context;

    public MovieListAdapter(Context context, ArrayList<Movies>al_Movies){
        this.al_Movies=al_Movies;
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ItemViewHolder item_holder = (ItemViewHolder)holder;

        item_holder.tv_movie_name.setText(al_Movies.get(position).getTitle());



        Picasso.with(context).load(context.getString(R.string.poster_path)+al_Movies.get(position).getPoster_path()).into(item_holder.iv_movie_image,
                PicassoPalette.with(context.getString(R.string.poster_path)+al_Movies.get(position).getPoster_path(), item_holder.iv_movie_image)
                        .use(PicassoPalette.Profile.MUTED_DARK)
                        .intoBackground(item_holder.ll_title_bg)
                        //.intoTextColor(ll_image_bg)

                        .use(PicassoPalette.Profile.VIBRANT)
                        .intoBackground(item_holder.ll_title_bg, PicassoPalette.Swatch.RGB)
                        .intoTextColor(item_holder.tv_movie_name, PicassoPalette.Swatch.BODY_TEXT_COLOR));


    }

    @Override
    public int getItemCount() {
        return al_Movies.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {



        MyTextView tv_movie_name;
        ImageView iv_movie_image;
        LinearLayout ll_title_bg;

        public ItemViewHolder(View itemView) {
            super(itemView);


            tv_movie_name= (MyTextView)itemView.findViewById(R.id.tv_movie_name);
            iv_movie_image= (ImageView) itemView.findViewById(R.id.iv_movie);
            ll_title_bg= (LinearLayout) itemView.findViewById(R.id.ll_title);

        }
    }
}
