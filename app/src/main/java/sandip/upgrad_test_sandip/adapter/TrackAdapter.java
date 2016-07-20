package sandip.upgrad_test_sandip.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sandip.upgrad_task.R;
import sandip.upgrad_test_sandip.obj.Track;
import sandip.upgrad_test_sandip.widgets.MyTextView;

/**
 * Created by Sumit on 26/06/2016.
 */
public class TrackAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Track> al_Tracks;
    Context context;

    public TrackAdapter(Context context,ArrayList<Track>al_Tracks){
        this.al_Tracks=al_Tracks;
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ItemViewHolder item_holder = (ItemViewHolder)holder;

        item_holder.tv_track.setText(al_Tracks.get(position).getTrackName());
        item_holder.tv_collection_name.setText(al_Tracks.get(position).getCollectionName());
        item_holder.tv_track_time.setText(al_Tracks.get(position).getTrackTimeMillis());

        Picasso.with(context)
                .load(al_Tracks.get(position).getArtworkUrl60())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(item_holder.iv_track_image);
    }

    @Override
    public int getItemCount() {
        return al_Tracks.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {



        MyTextView tv_track,tv_collection_name,tv_track_time;
        ImageView iv_track_image;

        public ItemViewHolder(View itemView) {
            super(itemView);


            tv_track= (MyTextView)itemView.findViewById(R.id.tv_track_name);
            tv_collection_name= (MyTextView)itemView.findViewById(R.id.tv_collection_name);
            tv_track_time= (MyTextView)itemView.findViewById(R.id.tv_track_time);
            iv_track_image= (ImageView) itemView.findViewById(R.id.iv_track_image);

        }
    }
}
