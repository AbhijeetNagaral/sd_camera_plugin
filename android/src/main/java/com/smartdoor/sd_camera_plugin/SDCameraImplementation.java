package com.smartdoor.sd_camera_plugin;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Message;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.lib.FunSDK;
import com.lib.MsgContent;
import com.lib.sdk.bean.StringUtils;
import com.manager.account.BaseAccountManager;
import com.manager.account.XMAccountManager;
import com.manager.device.DeviceManager;
import com.smartdoor.utils.DeviceWifiManager;
import com.smartdoor.utils.SdPluginUtils;

import java.util.ArrayList;
import java.util.Collections;

public class SDCameraImplementation {

    static DeviceManager deviceManager = DeviceManager.getInstance();
    static XMAccountManager manager = XMAccountManager.getInstance();


    public boolean startWiFiQuickConfig(String ssid,
                                        String data, String info, String gw_ipaddr, int type, int isbroad, String mac, int nTimeout) {
        int nGetType = 2; // 2nd generation configuration
        int result = FunSDK.DevStartAPConfig(-1,
                nGetType,
                ssid, data, info, gw_ipaddr, type, isbroad, mac, nTimeout);
        return (result == 0);
    }

    public void addCameraThroughWifi(
            Context pContext,
            String pWifiSsid,
            String pPassword
    ){
        try{
            WifiManager wifiManager = (WifiManager)pContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            System.out.println("Initialized Wifi manager");
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            System.out.println("Got Wifi connection info");
            DhcpInfo wifiDhcp = wifiManager.getDhcpInfo();
            System.out.println("Got Wifi DHCP info");

            if ( null == wifiInfo ) {
                System.out.println("wifi info is null");
                Toast.makeText(pContext.getApplicationContext(), "Current Wifi Error", Toast.LENGTH_SHORT).show();
                return;
            }

            String ssid = wifiInfo.getSSID().replace("\"", "");
            if ( StringUtils.isStringNULL(ssid) ) {
                System.out.println("Ssid is null");
                Toast.makeText(pContext.getApplicationContext(), "Current Wifi Error", Toast.LENGTH_SHORT).show();
                return;
            }

            ScanResult scanResult = null;
//            for (ScanResult result : wifiManager.getScanResults()) {
//                if (result.SSID.equals(ssid)) {
//                    scanResult = result;
//                    break;
//                }
//            }

            if (
                    ActivityCompat.checkSelfPermission(
                            pContext,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    ) !=
                            PackageManager.PERMISSION_GRANTED
            ) {
                System.out.println("Fine location permission not granted");
                return;
            }

            scanResult = DeviceWifiManager.getInstance(pContext.getApplicationContext()).getCurScanResult(ssid);
            if ( null == scanResult ) {
                System.out.println("Scan results obtained null");
                Toast.makeText(pContext.getApplicationContext(), "Current Wifi Error", Toast.LENGTH_SHORT).show();
                return;
            }
            System.out.println("Got Scan results and capabilities: " + scanResult.capabilities);

            int pwdType = SdPluginUtils.getEncrypPasswordType(scanResult.capabilities);
            String wifiPwd = pPassword.trim();

            if ( pwdType != 0 && StringUtils.isStringNULL(wifiPwd) ) {
                // Password required
                System.out.println("wifPwd is null");
                Toast.makeText(pContext.getApplicationContext(), "Current Wifi Error", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuffer data = new StringBuffer();
            data.append("S:").append(ssid).append("P:").append(wifiPwd).append("T:").append(pwdType);

            String submask;
            if (wifiDhcp.netmask == 0) {
                System.out.println("Submask255");
                submask = "255.255.255.0";
            } else {
                System.out.println("Submask IP");
                submask = SdPluginUtils.formatIpAddress(wifiDhcp.netmask);
            }

            String mac = wifiInfo.getMacAddress();
            System.out.println("Got mac info");
            StringBuffer info = new StringBuffer();
            info.append("gateway:").append(SdPluginUtils.formatIpAddress(wifiDhcp.gateway)).append(" ip:")
                    .append(SdPluginUtils.formatIpAddress(wifiDhcp.ipAddress)).append(" submask:").append(submask)
                    .append(" dns1:").append(SdPluginUtils.formatIpAddress(wifiDhcp.dns1)).append(" dns2:")
                    .append(SdPluginUtils.formatIpAddress(wifiDhcp.dns2)).append(" mac:").append(mac)
                    .append(" ");
            System.out.println("Got info: " + info);

            System.out.println("Starting Wifi config.");
            startWiFiQuickConfig(ssid,
                    data.toString(), info.toString(),
                    SdPluginUtils.formatIpAddress(wifiDhcp.gateway),
                    pwdType, 0, mac, -1);



//            deviceManager.startQuickSetWiFi(
//                    wifiInfo.getSSID(),
//                    wifiPwd,
//                    scanResult.capabilities,
//                    wifiDhcp,
//                    1000,
//                    (xmDevInfo, errorId) -> {
//                        manager.addDev(
//                                xmDevInfo,
//                                true,
//                                new BaseAccountManager.OnAccountManagerListener() {
//                                    public void onSuccess(int msgId) {}
//
//                                    public void onFailed(int msgId, int errorId) {}
//
//                                    @Override
//                                    public void onFunSDKResult(Message msg, MsgContent ex) {
//                                        if (errorId == 0) {
//                                            ArrayList list = new ArrayList();
//                                            list.add(Collections.singleton(xmDevInfo.getDevId()));
//                                            System.out.println("Result List " +list.get(0));
//
//
//                                        } else {
//                                            System.out.println("" + errorId + "Failed to connect to Device");
//                                        }
//                                    }
//                                }
//                        );
//                    }
//            );
//            FunWifiPassword.getInstance().saveWifiPassword(ssid, wifiPwd);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
