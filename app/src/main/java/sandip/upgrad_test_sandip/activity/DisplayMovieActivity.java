package sandip.upgrad_test_sandip.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import sandip.upgrad_task.R;
import sandip.upgrad_test_sandip._interface.IError;
import sandip.upgrad_test_sandip.adapter.MovieListAdapter;
import sandip.upgrad_test_sandip.adapter.ShowImageAdapter;
import sandip.upgrad_test_sandip.loadingview.LoadingView;
import sandip.upgrad_test_sandip.obj.Movies;
import sandip.upgrad_test_sandip.utils.MyUtils;
import sandip.upgrad_test_sandip.webservices.CommonThread;

/**
 * Created by Sandip on 7/20/2016.
 */
public class DisplayMovieActivity extends BaseActivity {

    RecyclerView rv_movie;
    LoadingView lv_movie;
    IError iError;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    ArrayList<Movies> al_Movies=new ArrayList<>();
    MovieListAdapter movieListAdapter;

    private ViewPager mPager;
    private ShowImageAdapter mAdapter;
    private CirclePageIndicator mIndicator;

    String current="Popular";

    ArrayList<String> al_url=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_list);
        iError=(IError) this;
        init_controls();
        get_movies_data(getString(R.string.ws_popular_data));

    }


    public void init_controls(){
        lv_movie=(LoadingView) findViewById(R.id.lv);
        rv_movie=(RecyclerView) findViewById(R.id.rv_movie);
        mPager=(ViewPager) findViewById(R.id.pager);
        mIndicator=(CirclePageIndicator) findViewById(R.id.indicator);


        collapsingToolbarLayout  = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");

        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(current);
                    collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));
                    collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle("");

                    isShow = false;
                }
            }
        });

        rv_movie.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        // TODO Handle item click
                        Movies movies=al_Movies.get(position);

                        Intent single_intent=new Intent(DisplayMovieActivity.this,DisplaySingleMovieActivity.class);

                        single_intent.putExtra(MyUtils.MOVIE_DATA, movies);
                        startActivity(single_intent);

                    }
                })
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_popular:
                if(current.equalsIgnoreCase("Popular")){

                }
                else{
                    current="Popular";
                    get_movies_data(getString(R.string.ws_popular_data));
                }
                return true;
            case R.id.action_top:
                if(current.equalsIgnoreCase("Top Rated")){

                }
                else{
                    current="Top Rated";
                    get_movies_data(getString(R.string.ws_top_rated_data));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void get_movies_data(String url){

        al_Movies.clear();
        lv_movie.setLoading(true);
        CommonThread commonThread=new CommonThread(this, MyUtils.GET,url);

        commonThread.start();
    }

    @Override
    public void web_response(ArrayList<String> response, Context context) {
        super.web_response(response, context);
        lv_movie.setLoading(false);
        MyUtils.l("DisplayInfoActivity","response:"+response.get(2).toString());

        if (handle_response(response, iError,true)) {

            MyUtils.l("Data response", ":" + response.get(2));
            //parse the response from server
            parse_response(response.get(2));

            if(al_Movies.size()>0) {
                GridLayoutManager   layoutManager =new GridLayoutManager(DisplayMovieActivity.this, 2);
                rv_movie.setHasFixedSize(true);
                rv_movie.setLayoutManager(layoutManager);

                rv_movie.setLayoutManager(layoutManager);
                MovieListAdapter movieListAdapter=new MovieListAdapter(this,al_Movies);
                rv_movie.setAdapter(movieListAdapter);
                ShowImageAdapter     mAdapter = new ShowImageAdapter(getSupportFragmentManager(),al_url);
                mPager.setAdapter(mAdapter);
                mIndicator.setViewPager(mPager);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    public void parse_response(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.has("results")) {

                //if the data is JSON Array
                if (jsonObject.get("results") instanceof JSONArray) {
                    JSONArray data_jarray = jsonObject.getJSONArray("results");
                    MyUtils.l("Data ARRAY", ":" + data_jarray.length());
                    for (int i = 0; i < data_jarray.length(); i++) {
                        //parse data JSON Object
                        set_movies_data(data_jarray.getJSONObject(i));

                    }
                }
                //if the data is JSON Object
                // if only one data then data might be json (Assumption)
                if (jsonObject.get("results") instanceof JSONObject) {
                    JSONObject data_jobj = jsonObject.getJSONObject("results");
                    //parse data JSON Object
                    set_movies_data(data_jobj);
                }
            }
        }  catch(Exception e){

        }

    }



    public void set_movies_data(JSONObject jsonObject){
        Movies movies=new Movies();

        movies.setAdult(jsonObject.optString("adult",""));
        movies.setBackdrop_path(jsonObject.optString("backdrop_path",""));
        movies.setId(jsonObject.optString("id",""));
        movies.setOriginal_language(jsonObject.optString("original_language",""));
        movies.setOriginal_title(jsonObject.optString("original_title",""));
        movies.setOverview(jsonObject.optString("overview",""));
        movies.setPopularity(jsonObject.optString("popularity",""));
        movies.setPoster_path(jsonObject.optString("poster_path",""));
        movies.setRelease_date(jsonObject.optString("release_date",""));
        movies.setTitle(jsonObject.optString("title",""));
        movies.setVideo(jsonObject.optString("video",""));
        movies.setVote_average(jsonObject.optString("vote_average",""));
        movies.setVote_count(jsonObject.optString("vote_count",""));

        al_Movies.add(movies);

        if(al_url!=null&&al_url.size()<=3){
            al_url.add(getString(R.string.poster_path)+movies.getBackdrop_path());
        }

    }


    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
