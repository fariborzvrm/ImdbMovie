package com.example.imdbmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.imdbmovie.pojo.MovieSearch;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    RecyclerView recyclerView;
    EditText edtSearch=findViewById(R.id.edtSearch);
    Button btnSearch=findViewById(R.id.btnSearch);
    SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.search);
        adapter= new SearchAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false));




    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnSearch:

                AsyncHttpClient asyncHttpClient= new AsyncHttpClient();
                String address="https://api.themoviedb.org/3/search/movie?api_key=544724643e5ac4e6d434f20524e10921&language=en-US&query="+edtSearch.getText().toString()+"&page=1&include_adult=false";

                asyncHttpClient.get(address, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.i("Response", response.toString());
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

    }
}
