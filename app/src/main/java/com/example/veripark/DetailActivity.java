package com.example.veripark;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.veripark.CryptoHandler.CryptoHandler;
import com.example.veripark.API.ApiClient;
import com.example.veripark.API.HolderAPI;
import com.example.veripark.Model.Detail.DetailPost;
import com.example.veripark.Model.Detail.DetailResponse;
import com.example.veripark.Require.Const;

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

public class DetailActivity extends AppCompatActivity {
    String ID;
    String TAG = DetailActivity.class.getName();
    TextView detail_sembol, detail_fiyat, detail_gunluk_yuksek, detail_gunluk_dusuk, detail_fark, detail_adet, detail_tavan, detail_hacim, detail_taban, detail_alis, detail_satis;
    ImageView degisim;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ID = getIntent().getStringExtra("ID");

        this.detail_sembol = findViewById(R.id.detail_sembol);
        this.detail_fiyat = findViewById(R.id.detail_fiyat);
        this.detail_gunluk_yuksek = findViewById(R.id.detail_gunluk_yuksek);
        this.detail_gunluk_dusuk = findViewById(R.id.detail_gunluk_dusuk);
        this.detail_fark = findViewById(R.id.detail_fark);
        this.detail_adet = findViewById(R.id.detail_adet);
        this.detail_tavan = findViewById(R.id.detail_tavan);
        this.detail_hacim = findViewById(R.id.detail_hacim);
        this.detail_taban = findViewById(R.id.detail_taban);
        this.detail_alis = findViewById(R.id.detail_alis);
        this.detail_satis = findViewById(R.id.detail_satis);
        this.degisim = findViewById(R.id.degisim);
        progressBar=findViewById(R.id.progressBarDetail);

        try {
            getListData();
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException | UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void getListData() throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeyException {
        HolderAPI holderAPI = ApiClient.getClient().create(HolderAPI.class);
        Call<DetailResponse> call = holderAPI.stocksDetail(Const.getAuthorization(), new DetailPost(CryptoHandler.getInstance().encrypt(ID,Const.getAesKey(),Const.getAesIV())));
        Log.d(TAG, "getListData : " + call.isExecuted());
        call.enqueue(new Callback<DetailResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                Log.d(TAG, "" + response.body());
                if (!response.isSuccessful()) {
                    Log.d(TAG, "" + response.code());
                } else {
                    if (response.body() != null) {
                        Log.d(TAG, "" + response.code());
                        DetailResponse detailResponse = response.body();
                        if (detailResponse.status.isSuccess) {
                            drawChart(detailResponse.graphicData);
                            try {
                                detail_sembol.setText("Sembol : " + CryptoHandler.getInstance().decrypt(detailResponse.symbol.toString(),Const.getAesKey(),Const.getAesIV()));
                            } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e) {
                                e.printStackTrace();
                            }
                            detail_fiyat.setText("Fiyat : " + detailResponse.price);
                            detail_gunluk_yuksek.setText("Günlük Yüksek : " + detailResponse.highest);
                            detail_gunluk_dusuk.setText("Günlük Düşük : " + detailResponse.lowest);
                            detail_fark.setText("% Fark : " + detailResponse.difference);
                            detail_adet.setText("Adet : " + detailResponse.count);
                            detail_tavan.setText("Tavan : " + detailResponse.maximum);
                            detail_hacim.setText("Hacim : " + detailResponse.volume);
                            detail_taban.setText("Taban : " + detailResponse.minimum);
                            detail_alis.setText("Alış : " + detailResponse.bid);

                            if (Boolean.parseBoolean(detailResponse.isDown)) {
                                degisim.setImageResource(R.drawable.ic_down);
                            }

                            if (Boolean.parseBoolean(detailResponse.isUp)) {
                                degisim.setImageResource(R.drawable.ic_up);
                            }


                            detail_satis.setText("Satış : " + detailResponse.offer);


                            Log.d(TAG, "BAŞARILI");
                        } else {
                            Log.d(TAG, "HATA");
                            Toast.makeText(DetailActivity.this, "Sorun Oluştu", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                Log.d(TAG, call.request() + " ----- " + t.getMessage() + "---" + t.getCause());
            }
        });
    }


    public void drawChart(List<DetailResponse.GraphicData> graphicData) {

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progressBarDetail));

        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Günlük Detaylar");

        //cartesian.yAxis(0).title("Veri Oranları");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);


        List<DataEntry> seriesData = new ArrayList<>();

        for (DetailResponse.GraphicData graphicData1 : graphicData){
            seriesData.add(new CustomDataEntry(graphicData1.day, graphicData1.value));
        }

        //seriesData.add(new CustomDataEntry("1988", 8.5));


        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Gün Verileri");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);
    }
    static class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value) {
            super(x, value);
        }

    }
}

