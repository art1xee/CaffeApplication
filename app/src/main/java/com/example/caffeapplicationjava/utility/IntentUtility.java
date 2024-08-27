package com.example.caffeapplicationjava.utility;

import android.content.Context;
import android.content.Intent;

import com.example.caffeapplicationjava.mainAppFiles.MakeOrderActivity;
import com.example.caffeapplicationjava.mainAppFiles.OrderDetailActivity;

public class IntentUtility {

    public static void navigateToMakeOrderActivity(Context context, String userName) {
        Intent intent = new Intent(context, MakeOrderActivity.class);
        intent.putExtra(MakeOrderActivity.EXTRA_USER_NAME, userName);
        context.startActivity(intent);
    }


    public static Intent newIntentForOrderDetail( // передаём данные с MakeOrderActivity в OrderDetailActivity
            Context context,
            String userName,
            String drink,
            String drinkType,
            String additives)
    {
        return OrderDetailActivity.newIntent(
                context,
                userName,
                drink,
                drinkType,
                additives
        );
    }

    // Метод для создания Intent для перехода к MakeOrderActivity
    public static Intent newIntentForMakeOrder(Context context, String userName) {
        Intent intent = new Intent(context, MakeOrderActivity.class);
        intent.putExtra(MakeOrderActivity.EXTRA_USER_NAME, userName);
        return intent;
    }

    // Метод для запуска OrderDetailActivity
    public static void navigateToOrderDetailActivity(Context context,
                                                     String userName,
                                                     String drink,
                                                     String drinkType,
                                                     String additives) {
        Intent intent = newIntentForOrderDetail(context,
                userName,
                drink,
                drinkType,
                additives);
        context.startActivity(intent);
    }

}
