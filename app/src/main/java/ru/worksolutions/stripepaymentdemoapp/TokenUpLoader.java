package ru.worksolutions.stripepaymentdemoapp;

import android.util.Log;

import com.stripe.android.model.Token;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class TokenUpLoader {

    public static final String LOG_TAG = TokenUpLoader.class.getName();

    private TokenUploadeingService tokenUploadeingService;

    public TokenUpLoader() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:4567/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tokenUploadeingService = retrofit.create(TokenUploadeingService.class);
    }

    public interface TokenUploadeingService {
        @POST("/charge")
        Call<ResponseBody> completeCharge(@Query("amount") String amount,
                                          @Query("currency") String currency,
                                          @Query("description") String description,
                                          @Query("token") String token);
    }

    void completeCharge(String amount, String currency, String description, String token){
        Call<ResponseBody> call = tokenUploadeingService.completeCharge(amount, currency, description, token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null){
                    Log.e(LOG_TAG, response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(LOG_TAG, "Throwable Message " + t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
