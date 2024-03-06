import 'package:flutter_test/flutter_test.dart';
import 'package:sd_camera_plugin/sd_camera_plugin.dart';
import 'package:sd_camera_plugin/sd_camera_plugin_platform_interface.dart';
import 'package:sd_camera_plugin/sd_camera_plugin_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockSdCameraPluginPlatform
    with MockPlatformInterfaceMixin
    implements SdCameraPluginPlatform {

  @override
  Future<String?> getCameraPlugin() => Future.value('42');

  @override
  Future addNewCameraThroughWifi() {
    // TODO: implement addNewCameraThroughWifi
    throw UnimplementedError();
  }
}

void main() {
  final SdCameraPluginPlatform initialPlatform = SdCameraPluginPlatform.instance;

  test('$MethodChannelSdCameraPlugin is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelSdCameraPlugin>());
  });

  test('getPlatformVersion', () async {
    SdCameraPlugin sdCameraPlugin = SdCameraPlugin();
    MockSdCameraPluginPlatform fakePlatform = MockSdCameraPluginPlatform();
    SdCameraPluginPlatform.instance = fakePlatform;

    expect(await sdCameraPlugin.getCameraPlugin(), '42');
  });
}
