import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'sd_camera_plugin_method_channel.dart';

abstract class SdCameraPluginPlatform extends PlatformInterface {
  /// Constructs a SdCameraPluginPlatform.
  SdCameraPluginPlatform() : super(token: _token);

  static final Object _token = Object();

  static SdCameraPluginPlatform _instance = MethodChannelSdCameraPlugin();

  /// The default instance of [SdCameraPluginPlatform] to use.
  ///
  /// Defaults to [MethodChannelSdCameraPlugin].
  static SdCameraPluginPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [SdCameraPluginPlatform] when
  /// they register themselves.
  static set instance(SdCameraPluginPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getCameraPlugin() {
    throw UnimplementedError('getCameraPlugin() has not been implemented.');
  }

  Future addNewCameraThroughWifi() {
    throw UnimplementedError('addNewCameraThroughWifi() has not been implemented.');
  }
}
