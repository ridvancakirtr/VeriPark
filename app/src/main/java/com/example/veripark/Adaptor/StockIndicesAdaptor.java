package com.example.veripark.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veripark.DetailActivity;
import com.example.veripark.Model.List.Stocks;
import com.example.veripark.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StockIndicesAdaptor extends RecyclerView.Adapter<StockIndicesAdaptor.MyViewHolder> implements Filterable {
    Context context;
    List<Stocks> stocks;
    List<Stocks> stocksAll = new ArrayList<>();
    public StockIndicesAdaptor(Context context, List<Stocks> stocks) {
        this.context = context;
        this.stocks = stocks;
        for (Stocks s : stocks) {
            stocksAll.add(new Stocks(s.getSymbol(), s.getVolume(), s.getPrice(), s.getOffer(), s.getDifference(), s.getBid(), s.isUp(), s.isDown(),s.getId()));
        }
    }

    public StockIndicesAdaptor() {}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(context).inflate(R.layout.oneline, parent, false);
        return new MyViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.id.setText(stocks.get(position).getId());
        if (position % 2 == 1 && position!=0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#EAECEE"));
        }

        if (position==0){
            holder.itemView.findViewById(R.id.header).setVisibility(View.VISIBLE);
        }else{
            holder.itemView.findViewById(R.id.header).setVisibility(View.GONE);
        }

        if (Boolean.parseBoolean(stocks.get(position).isDown())){
            holder.isDownisUp.setImageResource(R.drawable.ic_down);
        }

        if (Boolean.parseBoolean(stocks.get(position).isUp())){
            holder.isDownisUp.setImageResource(R.drawable.ic_up);
        }

        holder.bid.setText(stocks.get(position).getBid());
        holder.difference.setText(stocks.get(position).getDifference());
        holder.offer.setText(stocks.get(position).getOffer());
        holder.price.setText(stocks.get(position).getPrice());
        holder.volume.setText(stocks.get(position).getVolume());
        holder.symbol.setText(stocks.get(position).getSymbol());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("ID",stocks.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Stocks> stocksList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                stocksList.addAll(stocksAll);
            }else{
                for(Stocks stocks_one : stocksAll){
                    if(stocks_one.getSymbol().toLowerCase().contains(constraint.toString().toLowerCase())){
                        stocksList.add(stocks_one);
                        Log.d("DENEME",stocks_one.getPrice());
                    }
                }

            }

            FilterResults filterResults = new FilterResults();
            filterResults.values= stocksList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            stocks.clear();
            stocks.addAll((List<Stocks>) results.values);
            notifyDataSetChanged();
        }
    };

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView isDownisUp;
        TextView bid, difference, offer, price, volume, symbol;
        TableRow header;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            header=itemView.findViewById(R.id.header);
            isDownisUp = itemView.findViewById(R.id.isDownIsUp);
            bid = itemView.findViewById(R.id.bid);
            difference = itemView.findViewById(R.id.difference);
            offer = itemView.findViewById(R.id.offer);
            price = itemView.findViewById(R.id.price);
            volume = itemView.findViewById(R.id.volume);
            symbol = itemView.findViewById(R.id.symbol);
        }
    }
}
