import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:sd_camera_plugin/sd_camera_plugin_method_channel.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  MethodChannelSdCameraPlugin platform = MethodChannelSdCameraPlugin();
  const MethodChannel channel = MethodChannel('sd_camera_plugin');

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(
      channel,
      (MethodCall methodCall) async {
        return '42';
      },
    );
  });

  tearDown(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(channel, null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getCameraPlugin(), '42');
  });
}
