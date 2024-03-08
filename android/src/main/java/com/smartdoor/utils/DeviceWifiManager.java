package com.smartdoor.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.lib.sdk.bean.StringUtils;

import java.util.List;

public class DeviceWifiManager {
    private WifiManager mWifiManager;
    private ConnectivityManager connManager;
    private static DeviceWifiManager mInstance;
    private WifiInfo mWifiInfo;
    private int wifiNumber = 0;



    private DeviceWifiManager(Context context) {
        if (null != context) {
            mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            mWifiInfo = mWifiManager.getConnectionInfo();
        } else {
            mInstance = null;
        }
    }

    public static synchronized DeviceWifiManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DeviceWifiManager(context);
        }
        return mInstance;
    }

    public ScanResult getCurScanResult(String ssid) {
        System.out.println("Scanning wifi results " + ssid);
        wifiNumber = 0;
        mWifiManager.startScan();
        List<ScanResult> wifiList = mWifiManager.getScanResults();
        System.out.println("Scanning results list " + wifiList);
        if (wifiList != null) {
            wifiNumber = wifiList.size();
        }
        ScanResult scanResult = null;
        if (wifiList != null && ssid != null) {
            for (ScanResult result : wifiList) {
                String tmpSSID = result.SSID.trim();
                // If there are quotes before and after, remove the quotes first
                if (tmpSSID.startsWith("\"") && tmpSSID.endsWith("\"")) {
                    tmpSSID = tmpSSID.substring(1, tmpSSID.length() - 1);
                }
                if (StringUtils.contrast(tmpSSID,ssid)) {
                    if (result.frequency > 4900 && result.frequency < 5900) {
                        //In the case of dual-band integration, the WiFi with the same name may be 5GHZ. Record and traverse the entire list. If there is no 2.4GHZ, return to 5G for prompts.                        scanResult = result;
                        continue;
                    } else {
                        return result;
                    }
                }
            }
        }
        return scanResult;
    }

}
