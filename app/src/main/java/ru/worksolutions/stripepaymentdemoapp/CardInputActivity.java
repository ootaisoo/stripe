package ru.worksolutions.stripepaymentdemoapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.Stripe;
import com.stripe.android.model.Token;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CardInputActivity extends AppCompatActivity {

    private static final String LOG_TAG = CardInputActivity.class.getSimpleName();

    Button paymentButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_input_activity);

        final CardInputWidget cardInputWidget = findViewById(R.id.card_input_widget);
        paymentButton = findViewById(R.id.payment_button);

        final TokenUpLoader tokenUpLoader = new TokenUpLoader();

        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card cardToSave = cardInputWidget.getCard();
                if (cardToSave == null) {
                    Log.e(LOG_TAG, "Invalid Card Data");
                } else {
                    Log.e(LOG_TAG, "Valid Card Data");
                }

                Stripe stripe = new Stripe(CardInputActivity.this, "pk_test_WSS5YrKdL2LNMAM4ehujXKBN");
                stripe.createToken(cardToSave, new TokenCallback() {
                            public void onSuccess(Token token) {
                                Log.e(LOG_TAG, "onSuccess() ");
                                tokenUpLoader.completeCharge("100", "usd", ".", token.getId());
                            }
                            public void onError(Exception error) {
                                Log.e(LOG_TAG, "onError() " + error.getMessage());
                                Toast.makeText(CardInputActivity.this, "Cannot obtain token", Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }
        });
    }
}
