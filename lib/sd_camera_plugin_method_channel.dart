import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'sd_camera_plugin_platform_interface.dart';

/// An implementation of [SdCameraPluginPlatform] that uses method channels.
class MethodChannelSdCameraPlugin extends SdCameraPluginPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('sd_camera_plugin');

  @override
  Future<String?> getCameraPlugin() async {
    final version = await methodChannel.invokeMethod<String>('CAMERA_LOGIN');
    return version;
  }

  @override
  Future addNewCameraThroughWifi(String pWifiSsid, String pPassword) async{
    await methodChannel.invokeMethod<String>('CAMERA_LOGIN', {
      "wifiSsid": pWifiSsid,
      "password": pPassword
    });
  }
}
