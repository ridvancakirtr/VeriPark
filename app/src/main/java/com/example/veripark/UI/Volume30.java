package com.example.veripark.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veripark.CryptoHandler.CryptoHandler;
import com.example.veripark.API.ApiClient;
import com.example.veripark.API.HolderAPI;
import com.example.veripark.Adaptor.StockIndicesAdaptor;
import com.example.veripark.MainActivity;
import com.example.veripark.Model.List.ListPost;
import com.example.veripark.Model.List.ListResponse;
import com.example.veripark.Model.List.Stocks;
import com.example.veripark.R;
import com.example.veripark.Require.Const;
import com.example.veripark.Require.Method;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Volume30 extends Fragment {
    StockIndicesAdaptor stockIndicesAdaptor;
    RecyclerView recyclerView;
    String TAG = MainActivity.class.getName();
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.period, container, false);
        recyclerView= view.findViewById(R.id.recyclerView);
        progressBar=view.findViewById(R.id.progressBarStockIndices);
        setHasOptionsMenu(true);
        try {
            getListData();
        } catch (NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return view;
    }

    public void getListData() throws NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        progressBar.setVisibility(View.VISIBLE);
        List<Stocks> stocksList=new ArrayList<Stocks>();
        HolderAPI holderAPI = ApiClient.getClient().create(HolderAPI.class);
        Call<ListResponse> call = holderAPI.stocksList(Const.getAuthorization(),new ListPost(CryptoHandler.getInstance().encrypt(Method.getVOLUME30(),Const.getAesKey(),Const.getAesIV())));
        call.enqueue(new Callback<ListResponse>() {
            @Override
            public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                Log.d("RESULT",""+response.body());
                if (!response.isSuccessful()) {
                    Log.d(TAG, "" + response.code());
                } else {
                    if (response.body() != null) {
                        Log.d(TAG, "" + response.code());
                        ListResponse listResponse = response.body();
                        if (listResponse.status.isSuccess) {
                            for (Stocks stocks : listResponse.stocks) {
                                try {
                                    stocksList.add(new Stocks(
                                            CryptoHandler.getInstance().decrypt(stocks.getSymbol(), Const.getAesKey(), Const.getAesIV()),
                                            stocks.getVolume(),
                                            stocks.getPrice(),
                                            stocks.getOffer(),
                                            stocks.getOffer(),
                                            stocks.getBid(),
                                            stocks.isUp(),
                                            stocks.isDown(),
                                            stocks.getId()
                                    ));
                                } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e) {
                                    e.printStackTrace();
                                }
                            }
                            setRecyclerandArrayList(stocksList);
                            progressBar.setVisibility(View.INVISIBLE);
                        }else{
                            Log.d(TAG, "HATA");
                            Toast.makeText(getContext(), "Sorun Oluştu", Toast.LENGTH_SHORT).show();
                            new Intent(getContext(),MainActivity.class);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ListResponse> call, Throwable t) {
                Log.d(TAG,call.request() +" ----- "+t.getMessage()+"---"+t.getCause());
                Toast.makeText(getContext(), "Sorun Oluştu", Toast.LENGTH_SHORT).show();
                new Intent(getContext(),MainActivity.class);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setRecyclerandArrayList(List<Stocks> stocksList) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        stockIndicesAdaptor = new StockIndicesAdaptor(getContext(),stocksList);
        recyclerView.setAdapter(stockIndicesAdaptor);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                stockIndicesAdaptor.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

}
