package com.example.caffeapplicationjava;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MakeOrderActivity extends AppCompatActivity {
    private static final String EXTRA_USER_NAME = "userName";

    TextView greeting_user;
    TextView additives_textView;

    RadioGroup radio_group_drinks;
    RadioButton radio_button_tea;
    RadioButton radio_button_coffee;

    CheckBox check_box_sugar;
    CheckBox check_box_milk;
    CheckBox check_box_lemon;

    Spinner spinner_tea;
    Spinner spinner_coffee;

    Button make_order_button;

    private String userName;
    private String drink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);

        initViews(); // переопределяем все классы в MakeOrderActivity.

        setUpUserName(); // вписываем имя пользователя при регистрации в greeting_user

        radio_group_drinks.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int id) {
                // если пользователь в radio group выбрал чай или кофе, то мы выполняем метод onUserChoseTea() или onUserChoseCoffee()
                int getTeaId = radio_button_tea.getId();
                int getCoffeeId = radio_button_coffee.getId();

                if (id == getTeaId) {
                    onUserChoseTea();
                } else if (id == getCoffeeId) {
                    onUserChoseCoffee();
                }
            }
        });
        radio_button_tea.setChecked(true); //при переходе на эту Activity у нас по умолчанию в radio group выбран - чай.

        make_order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserMadeOrder(); // при нажатии на кнопку, мы переносим пользователя на другую Activity (OrderDetailActivity).
            }
        });
    }

    public void initViews() {//переопределённые макеты с activity_make_order.xml
        greeting_user = findViewById(R.id.greeting_user);
        radio_group_drinks = findViewById(R.id.radio_group_drinks);
        additives_textView = findViewById(R.id.additives_textView);
        radio_button_tea = findViewById(R.id.radio_button_tea);
        radio_button_coffee = findViewById(R.id.radio_button_coffee);

        check_box_sugar = findViewById(R.id.check_box_sugar);
        check_box_milk = findViewById(R.id.check_box_milk);
        check_box_lemon = findViewById(R.id.check_box_lemon);

        spinner_tea = findViewById(R.id.spinner_tea);
        spinner_coffee = findViewById(R.id.spinner_coffee);

        make_order_button = findViewById(R.id.make_order_button);
    }

    private void setUpUserName() { //метод, который принимает имя пользователя из usernameLayout (RegistrationActivity) и показывает его в greeting_user TextView (MakeOrderActivity).
        userName = getIntent().getStringExtra(EXTRA_USER_NAME);
        String greetings = getString(R.string.greeting_user_make_order_activity, userName);
        greeting_user.setText(greetings);

    }

    private void onUserChoseTea() {//метод, который выполняет код (меняет в additives_textView текст, а также убриает spinner с кофе), если пользователь выбрал из напитков - чай.
        drink = getString(R.string.radio_button_text_tea);
        String additivesLabel = getString(R.string.additives, drink);
        additives_textView.setText(additivesLabel);
        check_box_lemon.setVisibility(View.VISIBLE);

        spinner_tea.setVisibility(View.VISIBLE);
        spinner_coffee.setVisibility(View.INVISIBLE);
    }

    private void onUserChoseCoffee() {//метод, который выполняет код (меняет в additives_textView текст, а также убриает spinner с чаем), если пользователь выбрал из напитков - кофе.
        drink = getString(R.string.radio_button_text_coffee);
        String additivesLabel = getString(R.string.additives, drink);
        additives_textView.setText(additivesLabel);
        check_box_lemon.setVisibility(View.INVISIBLE);

        spinner_coffee.setVisibility(View.VISIBLE);
        spinner_tea.setVisibility(View.INVISIBLE);
    }

    private void onUserMadeOrder() { //метод, который выполняет код, когда пользователь сделал свой заказ, а потом нажал на кнопку, а также передаёт данные из этого файла (MakeOrderActivity) в файл (OrderDetailActivity).
        boolean sugarIsChecked = check_box_sugar.isChecked();
        boolean milkIsChecked = check_box_milk.isChecked();
        boolean lemonIsChecked = check_box_lemon.isChecked();
        boolean radioButtonTeaChecked = radio_button_tea.isChecked();
        boolean radioButtonCoffeeChecked = radio_button_coffee.isChecked();

        String sugarGetText = check_box_sugar.getText().toString();
        String milkGetText = check_box_milk.getText().toString();
        String lemonGetText = check_box_lemon.getText().toString();


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
            drinkType = spinner_tea.getSelectedItem().toString();
        } else if (radioButtonCoffeeChecked) {
            drinkType = spinner_coffee.getSelectedItem().toString();
        }

        Intent intent = OrderDetailActivity.newIntent(this,
                userName,
                drink,
                drinkType,
                additives.toString()
        );
        startActivity(intent);
    }

    public static Intent newIntent(Context context, String userName) { //intent method, который переносит пользователя после регестрации на эту activity из (RegistrationActivity) в (MakeOrderActivity).
        Intent intent = new Intent(context, MakeOrderActivity.class);
        intent.putExtra(EXTRA_USER_NAME, userName);
        return intent;
    }


}