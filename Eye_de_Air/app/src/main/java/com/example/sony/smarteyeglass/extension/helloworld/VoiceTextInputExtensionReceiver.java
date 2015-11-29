package com.example.sony.smarteyeglass.extension.helloworld;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by b1013043 on 15/11/29.
 */

public final class VoiceTextInputExtensionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        //  Log.d(Constants.LOG_TAG, "onReceive: " + intent.getAction());
        intent.setClass(context, VoiceTextInputExtensionService.class);
        context.startService(intent);
    }
}