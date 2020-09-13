package com.example.veripark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.veripark.CryptoHandler.CryptoHandler;
import com.example.veripark.API.ApiClient;
import com.example.veripark.API.HolderAPI;
import com.example.veripark.Require.Const;
import com.example.veripark.Require.Device;
import com.example.veripark.Model.Handshake.HandshakeStartPost;
import com.example.veripark.Model.Handshake.HandshakeStartResponse;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    String TAG = MainActivity.class.getName();
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=findViewById(R.id.progressBar);

    }

    public void getLoginData(View view) {
        progressBar.setVisibility(View.VISIBLE);
        HolderAPI holderAPI = ApiClient.getClient().create(HolderAPI.class);
        Call<HandshakeStartResponse> call = holderAPI.handshakeStart(new HandshakeStartPost(Device.getDeviceManifacturer(),Device.getDeviceModel(),Device.getPlatformName(),Device.getSystemVersion(),Device.getDeviceID()));
        call.enqueue(new Callback<HandshakeStartResponse>() {
            @Override
            public void onResponse(Call<HandshakeStartResponse> call, Response<HandshakeStartResponse> response) {

                if (!response.isSuccessful()) {
                    Log.d(TAG, "" + response.code());
                } else {
                    if (response.body() != null) {
                        Log.d(TAG, "" + response.code());
                        HandshakeStartResponse handshakeStartResponse = response.body();
                        if(handshakeStartResponse.status.isSuccess){
                            progressBar.setVisibility(View.INVISIBLE);

                            Const.setAesIV(handshakeStartResponse.aesIV);
                            Const.setAesKey(handshakeStartResponse.aesKey);
                            Const.setAuthorization(handshakeStartResponse.authorization);

                            startActivity(new Intent(MainActivity.this, ListActivity.class));
                        }else{
                            Log.d(TAG, "HATA");
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this, "Sorun Oluştu", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<HandshakeStartResponse> call, Throwable t) {
                Log.d(TAG,call.request() +" ----- "+t.getMessage());
                Toast.makeText(MainActivity.this, "Sorun Oluştu", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }


}