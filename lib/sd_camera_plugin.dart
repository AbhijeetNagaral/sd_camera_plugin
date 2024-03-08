
import 'sd_camera_plugin_platform_interface.dart';

class SdCameraPlugin {
  Future<String?> getCameraPlugin() {
    return SdCameraPluginPlatform.instance.getCameraPlugin();
  }

  Future addNewCameraThroughWifi(String pWifiSsid, String pPassword)async{
    await SdCameraPluginPlatform.instance.addNewCameraThroughWifi( pWifiSsid, pPassword);
  }

}
