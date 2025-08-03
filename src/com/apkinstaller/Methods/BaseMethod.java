package com.apkinstaller.Methods;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import open.cn.awg.pro.R;

public class BaseMethod {
  
  public static boolean checkHasADB() {
    try {
      return !CommandMethod.runCommand(new String[]{"adb"}).contains("adb: not found");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
  
  public static boolean checkRun(Context context) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return sharedPreferences.getBoolean("RUN_ONCE", false);
  }
  
  public static void setRunOnce(Context context) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    sharedPreferences.edit().putBoolean("RUN_ONCE", true).apply();
  }
  
  public static boolean hasADB(Context context) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return sharedPreferences.getBoolean("HAS_ADB", false);
  }
  
  public static void setHasADB(Context context) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    sharedPreferences.edit().putBoolean("HAS_ADB", true).apply();
  }
  
  static void showStatusDialog(final Activity activity, String text, String detail, String apkName, Drawable apkIcon, final String pkgName) {
    LayoutInflater layoutInflater = activity.getLayoutInflater();
    View view = layoutInflater.inflate(R.layout.dialog_content_install_status, (ViewGroup) activity.findViewById(R.id.dialog_layout_install_status));
    TextView textView_status = view.findViewById(R.id.textView_install_status);
    textView_status.setText(text);
    if (detail != null) {
      TextView textView_detail = view.findViewById(R.id.textView_install_detail);
      textView_detail.setText(detail);
    }
    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
    builder.setTitle(apkName);
    builder.setIcon(apkIcon);
    if (pkgName != null) {
      PackageManager packageManager = activity.getPackageManager();
      final Intent intent = packageManager.getLaunchIntentForPackage(pkgName);
      if (intent != null) {
        builder.setPositiveButton(R.string.open, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            activity.startActivity(intent);
            activity.finish();
          }
        });
      }
    }
    builder.setNegativeButton(R.string.complete, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        activity.finish();
      }
    });
    /*builder.setNeutralButton(R.string.donate, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        Uri uri = Uri.parse("https://QR.ALIPAY.COM/FKX04268ELODAA2RIXEW54");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(intent);
        activity.finish();
      }
    });*/
    builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
      @Override
      public void onCancel(DialogInterface dialog) {
        activity.finish();
      }
    });
    builder.setView(view);
    builder.show();
  }
  
}