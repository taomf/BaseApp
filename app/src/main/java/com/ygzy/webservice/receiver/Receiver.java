package com.ygzy.webservice.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * @author :
 * Date    : 2023/11/9/15:17
 * Desc    :
 */
public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(intent.getAction(), Intent.ACTION_BOOT_COMPLETED)) {
            Intent intentMainActivity = new Intent(Intent.ACTION_MAIN);
            intentMainActivity.setClassName(context.getPackageName(), "io.dcloud.PandoraEntry");
            intentMainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentMainActivity);
        }
    }
}
