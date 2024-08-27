package com.example.caffeapplicationjava.mainAppFiles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caffeapplicationjava.R;
import com.example.caffeapplicationjava.databinding.ActivityMakeOrderBinding;
import com.example.caffeapplicationjava.utility.IntentUtility;

import java.util.ArrayList;

public class MakeOrderActivity extends AppCompatActivity {
    public static final String EXTRA_USER_NAME = "userName";
    private ActivityMakeOrderBinding binding;

    private String userName;
    private String drink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMakeOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpUserName(); // вписываем имя пользователя при регистрации в greeting_user
        binding.radioGroupDrinks.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int id) {
                // если пользователь в radio group выбрал чай или кофе, то мы выполняем метод onUserChoseTea() или onUserChoseCoffee()
                int getTeaId = binding.radioButtonTea.getId();
                int getCoffeeId = binding.radioButtonCoffee.getId();

                if (id == getTeaId) {
                    onUserChoseTea();
                } else if (id == getCoffeeId) {
                    onUserChoseCoffee();
                }
            }
        });
        binding.radioButtonTea.setChecked(true); //при переходе на эту Activity у нас по умолчанию в radio group выбран - чай.

        binding.makeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserMadeOrder(); // при нажатии на кнопку, мы переносим пользователя на другую Activity (OrderDetailActivity).
            }
        });
    }


    private void setUpUserName() { //метод, который принимает имя пользователя из usernameLayout (RegistrationActivity) и показывает его в greeting_user TextView (MakeOrderActivity).
        userName = getIntent().getStringExtra(EXTRA_USER_NAME);
        String greetings = getString(R.string.greeting_user_make_order_activity, userName);
        binding.greetingUser.setText(greetings);
    }

    private void onUserChoseTea() {//метод, который выполняет код (меняет в additives_textView текст, а также убриает spinner с кофе), если пользователь выбрал из напитков - чай.
        drink = getString(R.string.radio_button_text_tea);
        String additivesLabel = getString(R.string.additives, drink);
        binding.additivesTextView.setText(additivesLabel);
        binding.checkBoxLemon.setVisibility(View.VISIBLE);

        binding.spinnerTea.setVisibility(View.VISIBLE);
        binding.spinnerCoffee.setVisibility(View.INVISIBLE);
    }

    private void onUserChoseCoffee() {//метод, который выполняет код (меняет в additives_textView текст, а также убриает spinner с чаем), если пользователь выбрал из напитков - кофе.
        drink = getString(R.string.radio_button_text_coffee);
        String additivesLabel = getString(R.string.additives, drink);
        binding.additivesTextView.setText(additivesLabel);
        binding.checkBoxLemon.setVisibility(View.INVISIBLE);

        binding.spinnerCoffee.setVisibility(View.VISIBLE);
        binding.spinnerTea.setVisibility(View.INVISIBLE);
    }

    private void onUserMadeOrder() { //метод, который выполняет код, когда пользователь сделал свой заказ, а потом нажал на кнопку, а также передаёт данные из этого файла (MakeOrderActivity) в файл (OrderDetailActivity).
        boolean sugarIsChecked = binding.checkBoxSugar.isChecked();
        boolean milkIsChecked = binding.checkBoxMilk.isChecked();
        boolean lemonIsChecked = binding.checkBoxLemon.isChecked();
        boolean radioButtonTeaChecked = binding.radioButtonTea.isChecked();
        boolean radioButtonCoffeeChecked = binding.radioButtonCoffee.isChecked();

        String sugarGetText = binding.checkBoxSugar.getText().toString();
        String milkGetText = binding.checkBoxMilk.getText().toString();
        String lemonGetText = binding.checkBoxLemon.getText().toString();


        ArrayList<String> additives = new ArrayList<>();
        if (sugarIsChecked) {
            additives.add(sugarGetText);
        }
        if (milkIsChecked) {
            additives.add(milkGetText);
        }
        if (radioButtonTeaChecked && lemonIsChecked) {
            additives.add(lemonGetText);
        }

        String drinkType = "";
        if (radioButtonTeaChecked) {
            drinkType = binding.spinnerTea.getSelectedItem().toString();
        } else if (radioButtonCoffeeChecked) {
            drinkType = binding.spinnerCoffee.getSelectedItem().toString();
        }

        IntentUtility.navigateToOrderDetailActivity(this,
                userName,
                drink,
                drinkType,
                additives.toString()
        );

    }

    public static Intent newIntent(Context context, String userName) { //intent method, который переносит пользователя после регестрации на эту activity из (RegistrationActivity) в (MakeOrderActivity).
        Intent intent = new Intent(context, MakeOrderActivity.class);
        intent.putExtra(EXTRA_USER_NAME, userName);
        return intent;
    }


}