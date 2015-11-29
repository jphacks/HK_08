package com.example.sony.smarteyeglass.extension.helloworld;

/**
 * Created by b1013043 on 15/11/29.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Created by b1013043 on 15/11/29.
 */
public final class VRExtensionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.d(Constants.LOG_TAG, "onReceive: " + intent.getAction());
        intent.setClass(context, VRExtensionService.class);
        context.startService(intent);
    }
}
