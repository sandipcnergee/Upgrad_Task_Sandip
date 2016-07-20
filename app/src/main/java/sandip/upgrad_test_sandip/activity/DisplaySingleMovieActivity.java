package sandip.upgrad_test_sandip.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.github.florent37.picassopalette.PicassoPalette;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import sandip.upgrad_task.R;
import sandip.upgrad_test_sandip.obj.Movies;
import sandip.upgrad_test_sandip.utils.MyUtils;
import sandip.upgrad_test_sandip.widgets.MyTextView;

/**
 * Created by Sumit on 26/06/2016.
 */
public class DisplaySingleMovieActivity extends BaseActivity {

    LinearLayout ll_image_bg;
    CircularImageView iv_artist_image;
    Toolbar toolbar;
    MyTextView tv_movie_name;
    MyTextView tv_rating,tv_release_date,tv_overview;
    int BG;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_single_movie);
        init_controls();




    }

    public void init_controls(){
        Intent i=getIntent();

        Movies movies=(Movies) i.getSerializableExtra(MyUtils.MOVIE_DATA);

        ll_image_bg=(LinearLayout) findViewById(R.id.ll_image_bg);
        iv_artist_image=(CircularImageView)findViewById(R.id.iv_track_artist);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        tv_movie_name=(MyTextView) findViewById(R.id.tv_movie_name);
        tv_rating=(MyTextView) findViewById(R.id.tv_rating_name);
        tv_release_date=(MyTextView) findViewById(R.id.tv_release_date);
        tv_overview=(MyTextView) findViewById(R.id.tv_overview_text);

        tv_movie_name.setText(movies.getTitle());
        tv_rating.setText(movies.getVote_average());
        tv_release_date.setText(movies.getRelease_date());
        tv_overview.setText(movies.getOverview());




        toolbar.setNavigationIcon(R.mipmap.back_icon);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplaySingleMovieActivity.this.finish();
            }
        });

        Picasso.with(this).load(getString(R.string.poster_path)+movies.getPoster_path()).into(iv_artist_image,

                PicassoPalette.with(getString(R.string.poster_path)+movies.getPoster_path(), iv_artist_image)

                        .use(PicassoPalette.Profile.MUTED_DARK)
                        .intoBackground(ll_image_bg)
                        //.intoTextColor(ll_image_bg)

                        .use(PicassoPalette.Profile.VIBRANT)
                        .intoBackground(ll_image_bg, PicassoPalette.Swatch.RGB)



                        //.intoTextColor(tv_movie_name, PicassoPalette.Swatch.BODY_TEXT_COLOR)


        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(BG);
        }


    }
}
