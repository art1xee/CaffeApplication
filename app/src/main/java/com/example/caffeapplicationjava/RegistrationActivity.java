package com.example.caffeapplicationjava;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.caffeapplicationjava.databinding.ActivityMainBinding;
import com.example.caffeapplicationjava.util.edittext.watcher.DefaultTextWatcher;
import com.example.caffeapplicationjava.util.edittext.watcher.username.UsernameChangeListener;
import com.example.caffeapplicationjava.util.edittext.watcher.username.UsernameTextWatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity implements UsernameChangeListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // метод которая вызывает ошибку, если в usernameLayout символов будет больше чем 20
        binding.usernameEditText.addTextChangedListener(new UsernameTextWatcher(this));

        // метод которая вызывает ошибку, если в passwordLayout будет слишком слабый пароль (который меньше чем 8 символов)
        binding.passwordEditText.addTextChangedListener(new DefaultTextWatcher() {
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
                if (password.isEmpty()) {
                    updatePasswordHelperTextColor(android.R.color.holo_green_dark);// меняю цвет setHelperText
                    setHelperTextPasswordLayout("Please enter a password.");
                }
            }
        });

        // Проверка того, заполнены ли все поля для переключения на следующий скрин, если - да, то выполняем строку else, а если - нет, то выполняем строку if
        binding.logInButton.setOnClickListener(v -> isAllFieldsFill());
    }

    @Override
    public void onNameChanged(String name) {
        if (name.length() >= 20) {
            setErrorTextUserLayout("No more!");
        } else {
            setErrorTextUserLayout(null);
        }
    }

    private void setErrorTextUserLayout(String text) { //метод, который позволяет написать любой текст в ошибку (работает только в usernameLayout)
        binding.usernameInputLayout.setError(text);
    }

    private void setErrorTextPasswordLayout(String text) {//метод, который позволяет написать любой текст в ошибку (работает только в passwordLayout)
        binding.passwordInputLayout.setError(text);
    }

    private void updateUsernameHelperTextColor(int color) {  //метод который меняет цвет helperText в username layout
        binding.usernameInputLayout.setHelperTextColor(ColorStateList.valueOf(ContextCompat.getColor(RegistrationActivity.this, color)));
    }

    private void updatePasswordHelperTextColor(int color) {  //метод который меняет цвет helperText в password layout
        binding.passwordInputLayout.setHelperTextColor(ColorStateList.valueOf(ContextCompat.getColor(RegistrationActivity.this, color)));
    }

    private void setHelperTextPasswordLayout(String text) { //метод, который позволяет написать любой текст в хелпер метод (работает только в passwordLayout)
        binding.passwordInputLayout.setHelperText(text);
    }

    private void setHelperTextUserNameLayout(String text) { //метод, который позволяет написать любой текст в хелпер метод (работает только в usernameLayout)
        binding.usernameInputLayout.setHelperText(text);
    }

    private void isAllFieldsFill() {//метод который проверят все ли поля заполнены, если - да, то выполняется else, а если - нет, то мы просим пользователя заполнить все незаполненные поля.
        String userName = binding.usernameEditText.getEditableText().toString().trim();
        String userPassword = binding.passwordEditText.getEditableText().toString().trim();

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
}