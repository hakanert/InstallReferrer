package com.hakanert.installapi;


import android.content.Context;
import android.os.RemoteException;

import androidx.annotation.NonNull;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class InstallApi {
    public static String BASE_URL ="https://domain.com/api/"; // todo edit this line
    String token;

    public InstallApi(String token) {
        this.token = token;
    }

    public void callApi(Context context){
        InstallReferrerClient referrerClient;
        referrerClient = InstallReferrerClient.newBuilder(context).build();
        referrerClient.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:
                        try {
                            ReferrerDetails response = referrerClient.getInstallReferrer();
                            String refrer = response.getInstallReferrer();
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

                            Call<CALL> call = retrofitApi.api(refrer,  token);
                            call.enqueue(new Callback<CALL>() {
                                @Override
                                public void onResponse(@NonNull Call<CALL> call, @NonNull Response<CALL> response) {

                                }

                                @Override
                                public void onFailure(@NonNull Call<CALL> call, @NonNull Throwable t) {
                                }
                            });
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                }
            }

            @Override
            public void onInstallReferrerServiceDisconnected() {

                //Toast.makeText(Login.this, "Service disconnected..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static Retrofit retrofit;
    public static Retrofit getClientApi(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static RetrofitApi create(){
        return getClientApi().create(RetrofitApi.class);
    }
    public interface RetrofitApi {
        @POST("api.php") // todo edit this line
        Call<CALL> api(@Query("ref") String ref,
                       @Query("token") String token);
    }

    public static class CALL {
        String id;
    }
}
