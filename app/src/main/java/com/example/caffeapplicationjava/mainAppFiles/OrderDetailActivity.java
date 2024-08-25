package com.example.caffeapplicationjava.mainAppFiles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caffeapplicationjava.databinding.ActivityOrderDetailBinding;


public class OrderDetailActivity extends AppCompatActivity {
    private ActivityOrderDetailBinding binding;

    protected static final String EXTRA_USER_NAME = "userName";
    protected static final String EXTRA_DRINK = "drink";
    protected static final String EXTRA_DRINK_TYPE = "drinkType";
    protected static final String EXTRA_ADDITIVES = "additives";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        binding.textViewName.setText(intent.getStringExtra(EXTRA_USER_NAME));
        binding.textViewDrink.setText(intent.getStringExtra(EXTRA_DRINK));
        binding.textViewDrinkType.setText(intent.getStringExtra(EXTRA_DRINK_TYPE));
        binding.textViewAdditives.setText(intent.getStringExtra(EXTRA_ADDITIVES));

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