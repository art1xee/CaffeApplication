package com.example.caffeapplicationjava.mainAppFiles;


import android.os.Bundle;
import android.text.Editable;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caffeapplicationjava.databinding.ActivityMainBinding;
import com.example.caffeapplicationjava.utility.DefaultListener;
import com.example.caffeapplicationjava.utility.IntentUtility;
import com.example.caffeapplicationjava.utility.TextInputLayoutUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //метод которая вызывает ошибку, если в usernameLayout символов будет больше чем 20
        binding.usernameEditText.addTextChangedListener(new DefaultListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String user = s.toString();

                if (user.length() >= 20) {
                    TextInputLayoutUtils.setErrorText(binding.usernameInputLayout, "No more!");
                } else {
                    TextInputLayoutUtils.setErrorText(binding.usernameInputLayout, null);
                }
            }
        });


        // метод которая вызывает ошибку, если в passwordLayout будет слишком слабый пароль (который меньше чем 8 символов)
        binding.passwordEditText.addTextChangedListener(new DefaultListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = s.toString();

                if (password.length() >= 8) {
                    Pattern pattern = Pattern.compile("[a-zA-Z0-9]");
                    Matcher matcher = pattern.matcher(password);

                    boolean isPwdContainsSpeChar = matcher.find();

                    if (isPwdContainsSpeChar) {
                        TextInputLayoutUtils.updateHelperTextColor(binding.passwordInputLayout, RegistrationActivity.this, android.R.color.holo_green_dark);// меняю цвет setHelperText
                        TextInputLayoutUtils.setHelperText(binding.passwordInputLayout, "Perfect password!");
                        TextInputLayoutUtils.setErrorText(binding.passwordInputLayout, "");
                    } else {
                        TextInputLayoutUtils.setHelperText(binding.passwordInputLayout, "");
                    }
                } else {
                    TextInputLayoutUtils.updateHelperTextColor(binding.passwordInputLayout, RegistrationActivity.this, android.R.color.holo_red_dark);// меняю цвет setHelperText
                    TextInputLayoutUtils.setHelperText(binding.passwordInputLayout, "Create a more stronger password.");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = s.toString();
                if (password.length() == 0) {
                    TextInputLayoutUtils.updateHelperTextColor(binding.passwordInputLayout, RegistrationActivity.this, android.R.color.holo_green_dark);// меняю цвет setHelperText
                    TextInputLayoutUtils.setHelperText(binding.passwordInputLayout, "Please enter a password.");
                }
            }
        });


        // Проверка того, заполнены ли все поля для переключения на следующий скрин, если - да, то выполняем строку else, а если - нет, то выполняем строку if
        binding.logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllFieldsFill();
            }
        });
    }

    private void isAllFieldsFill() {//метод который проверят все ли поля заполнены, если - да, то выполняется else, а если - нет, то мы просим пользователя заполнить все незаполненные поля.
        String userName = binding.usernameEditText.getEditableText().toString().trim();
        String userPassword = binding.passwordEditText.getEditableText().toString().trim();

        boolean isUserNameEmpty = userName.isEmpty();
        boolean isUserPasswordEmpty = userPassword.isEmpty();

        if (isUserNameEmpty || isUserPasswordEmpty) {
            if (isUserNameEmpty) {
                TextInputLayoutUtils.setErrorText(binding.usernameInputLayout, "Enter this field.");
            }
            if (isUserPasswordEmpty) {
                TextInputLayoutUtils.setErrorText(binding.passwordInputLayout, "Enter this field.");
            }
        } else {
            IntentUtility.navigateToMakeOrderActivity(this, userName);
        }
    }

}