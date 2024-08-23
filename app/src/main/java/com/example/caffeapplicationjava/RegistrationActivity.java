package com.example.caffeapplicationjava;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private TextView greetTextView;
    private Button logInButton;
    private TextInputLayout usernameLayout;
    private TextInputLayout passwordLayout;
    private TextInputEditText usernameText;
    private TextInputEditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews(); // Инициализация виджетов после setContentView с помощью метода initViews()

        // метод которая вызывает ошибку, если в usernameLayout символов будет больше чем 20
        usernameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String user = s.toString();

                if (user.length() >= 20) {
                    setErrorTextUserLayout("No more!");
                } else {
                    setErrorTextUserLayout(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // метод которая вызывает ошибку, если в passwordLayout будет слишком слабый пароль (который меньше чем 8 символов)
        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = s.toString();

                if (password.length() >= 8) {
                    Pattern pattern = Pattern.compile("[a-zA-Z0-9]");
                    Matcher matcher = pattern.matcher(password);

                    boolean isPwdContainsSpeChar = matcher.find();

                    if (isPwdContainsSpeChar) {
                        updatePasswordHelperTextColor(android.R.color.holo_green_dark); // меняю цвет setHelperText
                        setHelperTextPasswordLayout("Perfect password!");
                        setErrorTextPasswordLayout("");
                    } else {
                        setHelperTextPasswordLayout("");
                    }
                } else {
                    updatePasswordHelperTextColor(android.R.color.holo_red_dark); // меняю цвет setHelperText
                    setHelperTextPasswordLayout("Create a more stronger password.");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = s.toString();
                if (password.length() == 0) {
                    updatePasswordHelperTextColor(android.R.color.holo_green_dark);// меняю цвет setHelperText
                    setHelperTextPasswordLayout("Please enter a password.");
                }
            }
        });

        // Проверка того, заполнены ли все поля для переключения на следующий скрин, если - да, то выполняем строку else, а если - нет, то выполняем строку if
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllFieldsFill();
            }
        });
    }

    private void setErrorTextUserLayout(String text) { //метод, который позволяет написать любой текст в ошибку (работает только в usernameLayout)
        usernameLayout.setError(text);
    }

    private void setErrorTextPasswordLayout(String text) {//метод, который позволяет написать любой текст в ошибку (работает только в passwordLayout)
        passwordLayout.setError(text);
    }

    private void updateUsernameHelperTextColor(int color) {  //метод который меняет цвет helperText в username layout
        usernameLayout.setHelperTextColor(ColorStateList.valueOf(ContextCompat.getColor(RegistrationActivity.this, color)));
    }

    private void updatePasswordHelperTextColor(int color) {  //метод который меняет цвет helperText в password layout
        passwordLayout.setHelperTextColor(ColorStateList.valueOf(ContextCompat.getColor(RegistrationActivity.this, color)));
    }

    private void setHelperTextPasswordLayout(String text) { //метод, который позволяет написать любой текст в хелпер метод (работает только в passwordLayout)
        passwordLayout.setHelperText(text);
    }

    private void setHelperTextUserNameLayout(String text) { //метод, который позволяет написать любой текст в хелпер метод (работает только в usernameLayout)
        usernameLayout.setHelperText(text);
    }

    private void isAllFieldsFill() {//метод который проверят все ли поля заполнены, если - да, то выполняется else, а если - нет, то мы просим пользователя заполнить все незаполненные поля.
        String userName = usernameText.getEditableText().toString().trim();
        String userPassword = passwordText.getEditableText().toString().trim();

        boolean isUserNameEmpty = userName.isEmpty();
        boolean isUserPasswordEmpty = userPassword.isEmpty();

        if (isUserNameEmpty || isUserPasswordEmpty) {
            if (isUserNameEmpty) {
                setErrorTextUserLayout("Enter this field.");
            }
            if (isUserPasswordEmpty) {
                setErrorTextPasswordLayout("Enter this field.");
            }
        } else {
            launchOrderDrinkScreen(userName);
        }
    }

    private void launchOrderDrinkScreen(String userName) { //метод для переключения на MakeOrderActivity screen, если условие в методе isAllFieldsFill равен true.
        Intent intent = new Intent(RegistrationActivity.this, MakeOrderActivity.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }

    private void initViews() { //переопределённые макеты с activity_main.xml
        greetTextView = findViewById(R.id.greet_text_view);
        logInButton = findViewById(R.id.log_in_button);
        usernameText = findViewById(R.id.username_edit_text);
        passwordText = findViewById(R.id.password_edit_text);
        usernameLayout = findViewById(R.id.username_input_layout);
        passwordLayout = findViewById(R.id.password_input_layout);
    }
}