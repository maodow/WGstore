package com.louisk.wgstore.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetUtil {

  /**
   * 判断网络连接是否打开,包括移动数据连接
   */
  public static boolean isNetworkAvailable(Context context) {
    if (context != null) {
      ConnectivityManager connectivityManager =
          (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      TelephonyManager telephonyManager =
          (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
      return ((connectivityManager.getActiveNetworkInfo() != null
          && connectivityManager.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED)
          || telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }
    return true;
  }

  /**
   * 检测网络类型是否WIFI
   */
  public static boolean isWifi(Context context) {
    ConnectivityManager connectivityManager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
    if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
      return true;
    }
    return false;
  }

  /**
   * 检测网络类型是否3G
   */
  public static boolean is3G(Context context) {
    ConnectivityManager connectivityManager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
    if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
      return true;
    }
    return false;
  }

  /**
   * 检测网络类型是否4G
   */
  public static boolean is4G(Context context) {
    ConnectivityManager connectivityManager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
    if (activeNetInfo != null && activeNetInfo.isConnectedOrConnecting()&&activeNetInfo.getType() == TelephonyManager.NETWORK_TYPE_LTE) {
        return true;
    }
    return false;
  }

  /**
   * IP地址校验
   */
  public static boolean isIP(String ip) {
    Pattern pattern =
        Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)" +
            "\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)" +
            "\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)" +
            "\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
    Matcher matcher = pattern.matcher(ip);
    return matcher.matches();
  }

}
