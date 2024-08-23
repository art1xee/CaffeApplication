package com.example.caffeapplicationjava;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class OrderDetailActivity extends AppCompatActivity {
    private static final String EXTRA_USER_NAME = "userName;";
    private static final String EXTRA_DRINK = "drink;";
    private static final String EXTRA_DRINK_TYPE = "drinkType;";
    private static final String EXTRA_ADDITIVES = "additives;";

    private TextView text_view_name;
    private TextView text_view_drink;
    private TextView text_view_drink_type;
    private TextView text_view_additives;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        initViews();

        Intent intent = getIntent();
        text_view_name.setText(intent.getStringExtra(EXTRA_USER_NAME));
        text_view_drink.setText(intent.getStringExtra(EXTRA_DRINK));
        text_view_drink_type.setText(intent.getStringExtra(EXTRA_DRINK_TYPE));
        text_view_additives.setText(intent.getStringExtra(EXTRA_ADDITIVES));

    }

    private void initViews() {
        text_view_name = findViewById(R.id.text_view_name);
        text_view_drink = findViewById(R.id.text_view_drink);
        text_view_drink_type = findViewById(R.id.text_view_drink_type);
        text_view_additives = findViewById(R.id.text_view_additives);
    }


    public static Intent newIntent(
            Context context,
            String userName,
            String drink,
            String drinkType,
            String additives
    ) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra(EXTRA_USER_NAME, userName);
        intent.putExtra(EXTRA_DRINK, drink);
        intent.putExtra(EXTRA_DRINK_TYPE, drinkType);
        intent.putExtra(EXTRA_ADDITIVES, additives);
        return intent;
    }


}