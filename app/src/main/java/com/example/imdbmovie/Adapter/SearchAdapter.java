package com.example.imdbmovie.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imdbmovie.R;
import com.example.imdbmovie.pojo.Result;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    List<Result> resultItems = new ArrayList<>();
    AdapterItemClicked itemCLicked = null;


    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_search,parent,false);

        SearchViewHolder searchViewHolder=new SearchViewHolder(view);
        return searchViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, final int position) {


        holder.txtMovieTitle.setText(resultItems.get(position).getTitle());
        holder.txtMovieRate.setText(String.valueOf(resultItems.get(position).getVoteAverage()));
        holder.txtMovieDesc.setText(resultItems.get(position).getOverview());
        holder.linearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemCLicked.itemClicked(resultItems.get(position).getId());
            }
        });



    }

    public void setAdapterItemClicked(AdapterItemClicked itemClicked){
        this.itemCLicked =itemClicked;
    }


    public void setResultItems(List<Result> resultItems) {
        this.resultItems = resultItems;

    }

    @Override
    public int getItemCount() {
        return resultItems.size();
    }

    public  interface AdapterItemClicked{
        public void itemClicked(int Id);
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView txtMovieTitle,txtMovieRate,txtMovieDesc;
        LinearLayout linearSearch;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMovieTitle=itemView.findViewById(R.id.txtMovieTitle);
            txtMovieRate=itemView.findViewById(R.id.txtMovieRate);
            txtMovieDesc=itemView.findViewById(R.id.txtMovieDesc);
            linearSearch=itemView.findViewById(R.id.linearSearch);

        }
    }

}
