package com.example.imdbmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.imdbmovie.Adapter.SearchAdapter;
import com.example.imdbmovie.pojo.MovieSearch;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements SearchAdapter.AdapterItemClicked  {


    RecyclerView recyclerView;
    EditText edtSearch;
    Button btnSearch;
    SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        adapter= new SearchAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false));


click();


    }


    public void bindViews(){

        recyclerView=findViewById(R.id.search);
        edtSearch=findViewById(R.id.edtSearch);
        btnSearch=findViewById(R.id.btnSearch);
        adapter.setAdapterItemClicked(this);

    }



    public void click() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                String address = "https://api.themoviedb.org/3/search/movie?api_key=6f35b1bfbbde79225a8a057b3e5818b9&language=en-US&query=" + edtSearch.getText().toString() + "&page=1&include_adult=false";

                asyncHttpClient.get(address, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Gson gson = new Gson();
                        MovieSearch result = gson.fromJson(response.toString(), MovieSearch.class);
                        adapter.setResultItems(result.getResults());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }

                });

            }
        });

    }


    @Override
    public void itemClicked(int Id) {
        Intent intent =new Intent(MainActivity.this,DetailsActivity.class);
        intent.putExtra(Statics.TAG_ITEM_ID,Id);
        startActivity(intent);

    }
}

