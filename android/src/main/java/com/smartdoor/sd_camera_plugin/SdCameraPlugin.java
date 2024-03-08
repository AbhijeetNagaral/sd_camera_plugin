package com.smartdoor.sd_camera_plugin;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** SdCameraPlugin */
public class SdCameraPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private Context applicationContext;


  SDCameraImplementation sdCameraImplementation = new SDCameraImplementation();

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    this.applicationContext = flutterPluginBinding.getApplicationContext();

    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "sd_camera_plugin");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
//    MethodName methodName = MethodName.valueOf(call.method.toUpperCase());

    switch (call.method){
      case "CAMERA_LOGIN":
        String mWifiSsid = call.argument("wifiSsid");
        String mPassword = call.argument("password");
        Log.d("myTag", "Camera login invoked" + mWifiSsid + mPassword);
        sdCameraImplementation.addCameraThroughWifi(this.applicationContext, mWifiSsid, mPassword);
        break;

    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
