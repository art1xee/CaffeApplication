package com.example.caffeapplicationjava;

import android.content.Context;
import android.content.res.ColorStateList;

import com.google.android.material.textfield.TextInputLayout;

public abstract class TextInputLayoutUtils {


    public static void updateHelperTextColor(TextInputLayout inputLayout, Context context, int colorResId) {
        // Получаем ColorStateList из ресурса цвета
        ColorStateList colorStateList = ColorStateList.valueOf(context.getResources().getColor(colorResId));
        // Устанавливаем цвет вспомогательного текста
        inputLayout.setHelperTextColor(colorStateList);
    }

    public static void setErrorText(TextInputLayout inputLayout, String errorText) {
        // Устанавливаем error text в input Layout
        inputLayout.setError(errorText);
    }

    public static void setHelperText(TextInputLayout inputLayout, String helperText) {
        // Устанавливаем helper text в input Layout
        inputLayout.setHelperText(helperText);
    }
}
