package com.example.imdbmovie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imdbmovie.pojo.MovieSearch;
import com.example.imdbmovie.pojo.Result;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    List<Result> resultItems = new ArrayList<>();


    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_search,parent,false);

        SearchViewHolder searchViewHolder=new SearchViewHolder(view);
        return searchViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {


        holder.txtMovieTitle.setText(resultItems.get(position).getTitle());
        holder.txtMovieRate.setText(String.valueOf(resultItems.get(position).getVoteAverage()));
        holder.txtMovieDesc.setText(resultItems.get(position).getOverview());



    }


    public void setResultItems(List<Result> resultItems) {
        this.resultItems = resultItems;

    }

    @Override
    public int getItemCount() {
        return resultItems.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView txtMovieTitle,txtMovieRate,txtMovieDesc;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMovieTitle=itemView.findViewById(R.id.txtMovieTitle);
            txtMovieRate=itemView.findViewById(R.id.txtMovieRate);
            txtMovieDesc=itemView.findViewById(R.id.txtMovieDesc);

        }
    }

}
