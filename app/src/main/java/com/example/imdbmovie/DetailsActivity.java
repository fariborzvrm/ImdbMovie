package com.example.imdbmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.imdbmovie.db.SqliteDbHelper;
import com.example.imdbmovie.pojo.MovieSearch;
import com.example.imdbmovie.pojo.Result;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DetailsActivity extends AppCompatActivity {

    Intent intent;
    ImageView imageMoviePoster, imgDownloadDetails;
    TextView txtMovieTitle, txtMovieBudget, txtMovieGenres, txtMovieRate, txtMovieDesc;
    SqliteDbHelper dbHelper;
    Result result;
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        intent = getIntent();
        initViews();
        if (intent.getIntExtra(Statics.TAG_ITEM_ID, 0) != 0) {
            ID = intent.getIntExtra(Statics.TAG_ITEM_ID, 0);
            if (dbHelper.getMovieDetail(ID))
                imgDownloadDetails.setImageResource(R.drawable.ic_ok);
            connectToInternet(ID);
        }
        click();

    }


    private void connectToInternet(int ID) {
        AsyncHttpClient client = new AsyncHttpClient();


        String address = "https://api.themoviedb.org/3/movie/" + ID
                + "?api_key=" + Statics.API_KEY + "&language=en-US";


        client.get(address, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("Response", response.toString());
                Gson gson = new Gson();
                result = gson.fromJson(response.toString(), Result.class);
                setViewValue(result);
                imgDownloadDetails.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

        });
    }




    private void initViews() {
        imageMoviePoster = findViewById(R.id.imageMoviePoster);
        txtMovieTitle = findViewById(R.id.txtMovieTitle);
        txtMovieBudget = findViewById(R.id.txtMovieBudget);
        txtMovieGenres = findViewById(R.id.txtMovieGenres);
        txtMovieRate = findViewById(R.id.txtMovieRate);
        txtMovieDesc = findViewById(R.id.txtMovieDesc);
        imgDownloadDetails = findViewById(R.id.imgDownloadDetails);
        dbHelper = new SqliteDbHelper(this, "Movie", null, 1);
    }


    private void setViewValue(Result result) {
        Glide.with(this).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2"
                + result.getPosterPath())
                .into(imageMoviePoster).clearOnDetach();
        txtMovieTitle.setText(result.getTitle());
        txtMovieBudget.setText(String.valueOf(result.getReleaseDate()));
        txtMovieRate.setText(String.valueOf(result.getVoteAverage()));
        txtMovieDesc.setText(result.getOverview());
    }



    public void click() {
        imgDownloadDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (result != null) {
                    if (!dbHelper.getMovieDetail(result.getId())) {
                        dbHelper.inserIntoTable(result.getId(), result.getTitle());
                        imgDownloadDetails.setImageResource(R.drawable.ic_ok);
                    } else {
                        dbHelper.deleteFromTable(result.getId());
                        imgDownloadDetails.setImageResource(R.drawable.ic_download_button);
                    }
                }
            }
        });


    }

}
