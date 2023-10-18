package com.ygzy.lib_base.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.ygzy.lib_base.base.BaseApp;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 说明：手机信息相关工具类
 * @author taomf
 */

public final class AndroidInfoUtils {

    private AndroidInfoUtils() {}

    /**
     * 说明：获取手机IMEI号码
     *
     * @return 返回手机IMEI号码
     */
    @SuppressLint("MissingPermission")
    public static String getImeiCode() {
        String result = "";
        try {
            final TelephonyManager tm = (TelephonyManager) BaseApp.Companion.getAppContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);
            result = tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 说明：获取手机IMSI号码
     *
     * @return 返回手机IMEI号码
     */
    @SuppressLint("MissingPermission")
    public static String getImsiCode() {
        String result = "";
        try {
            final TelephonyManager tm = (TelephonyManager) BaseApp.Companion.getAppContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);
            result = tm.getSubscriberId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
   /**
    * date   : 2021/4/21 15:37
    * @author : taomf
    * Desc   : 获取设备序列号
    */

    @SuppressLint("MissingPermission")
    public static String getSimSerialNumber() {
        String result = "";
        try {
            final TelephonyManager tm = (TelephonyManager) BaseApp.Companion.getAppContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);
            result = tm.getSimSerialNumber();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取手机Android_ID
     *
     * @return MacAddress String
     */
    public static String getAndroidId() {
        String androidId = Secure.getString(BaseApp.Companion.getAppContext().getContentResolver(),
                Secure.ANDROID_ID);

        return "".equals(androidId) ? getAppUUid() : androidId ;
    }

    /**
     * 说明：获取本机手机号码
     *
     * @return 返回本机手机号码
     */
    @SuppressLint("MissingPermission")
    public static String getMobilNumber() {
        String result = "";
        try {
            final TelephonyManager tm = (TelephonyManager) BaseApp.Companion.getAppContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);
            result = tm.getLine1Number();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 说明：myPid
     * @return
     */
    public static int myPid(){
        return android.os.Process.myPid();
    }

    /**
     * 说明：获取系统信息
     *
     * @return
     */
    public static String getOs() {
        return Build.VERSION.RELEASE;
    }


    /**
     * 获取mac地址（适配所有Android版本）
     * @return
     */
    public static String getMac(){
        String mac= "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mac = getMacAddress();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mac = getMacAddress6();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mac = getMacFromHardware();
        }
        return mac.replace("-",":");
    }

    /**
     * Android 6.0-Android 7.0 获取mac地址
     */
    public static String getMacAddress6() {
        String macSerial = null;
        String str = "";

        try {
            Process pp = Runtime.getRuntime().exec("cat/sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            while (null != str) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();//去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }

        if (macSerial == null || TextUtils.isEmpty(macSerial)) {
            try {
                return loadFileAsString("/sys/class/net/eth0/address").toUpperCase().substring(0, 17);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("----->" + "NetInfoManager","getMacAddress:" + e.toString());
            }
        }

        return macSerial;
    }
    private static String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }

    private static String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }

    /**
     * Android 7.0之后获取Mac地址
     * 遍历循环所有的网络接口，找到接口是 wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET"></uses-permission>
     * @return
     */
    public static String getMacFromHardware() {
        try {
            List<NetworkInterface>  all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!"wlan0".equalsIgnoreCase(nif.getName())) {
                    continue;
                }
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }
                StringBuilder res1 = new StringBuilder();
                for (Byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }
                if (!TextUtils.isEmpty(res1)) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 说明：获取手机MAC地址
     *
     * @return 返回手机MAC地址
     */
    @SuppressLint({"WifiManagerLeak", "MissingPermission", "HardwareIds"})
    public static String getMacAddress() {
        String res = "";
        try {

            final WifiManager wifiManager = (WifiManager) BaseApp.Companion.getAppContext()
                    .getSystemService(Context.WIFI_SERVICE);
            final WifiInfo info = wifiManager.getConnectionInfo();
            if (null != info) {
                res = info.getMacAddress();
            }
            if (TextUtils.isEmpty(res)) {
                res = "00:00:00:00:00:00";
            }
        } catch (Exception e) {
            res = "";
        }
        return res;
    }

    /**
     * 说明：获取当前应用程序的VersionName
     *
     *            当前上下文环境
     * @return 返回当前应用的版本号
     */
    public static String versionName() {
        try {
            PackageInfo info = BaseApp.Companion.getAppContext().getPackageManager().getPackageInfo(
                    BaseApp.Companion.getAppContext().getPackageName(), 0);
            return info.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 说明：获取当前应用程序的VersionCode
     *
     * @return 返回当前应用的版本号
     */
    public static int versionCode() {
        try {
            PackageInfo info = BaseApp.Companion.getAppContext().getPackageManager().getPackageInfo(
                    BaseApp.Companion.getAppContext().getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 说明：检测手机空间可用大小 get the space is left over on phone self
     */
    public static long getRealSizeOnPhone() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        @SuppressWarnings("deprecation")
        long blockSize = stat.getBlockSize();
        @SuppressWarnings("deprecation")
        long availableBlocks = stat.getAvailableBlocks();
        long realSize = blockSize * availableBlocks;
        return realSize;
    }

    /**
     * 说明：获取设备终端码
     *
     * @return
     */
    public static String getTerminalCode() {
        try {
            return  MD5.getMD5(getImeiCode() + getImsiCode() + getAndroidId());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取手机系统SDK版本
     *
     * @return 如API 17 则返回 17
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取设备的可用内存大小
     *
     * @return 当前内存大小
     */
    public static int getDeviceUsableMemory() {
        ActivityManager am = (ActivityManager) BaseApp.Companion.getAppContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        // 返回当前系统的可用内存
        return (int) (mi.availMem / (1024 * 1024));
    }

    /**
     * 说明：获取当前线程名称
     * @return
     */
    public static String getCurProcessName(){
        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) BaseApp.Companion.getAppContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    public static String getVersionString(){
        return Build.VERSION.RELEASE;
    }

    private static String getAppUUid() {
        String uuid = SPUtils.getInstance("device_info").readString("deviceId");
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            SPUtils.getInstance("device_info").write("deviceId",uuid);
        }
        return uuid;
    }

    /**
     * 判断网络是否连接
     */
    public static boolean isConnectIsNomarl() {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseApp.Companion.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String name = info.getTypeName();
            MyLogUtils.INSTANCE.i(MyLogUtils.NET_TAG, "当前网络名称：" + name);
            return true;
        } else {
            MyLogUtils.INSTANCE.i(MyLogUtils.NET_TAG, "没有可用网络");
            return false;
        }
    }

}
