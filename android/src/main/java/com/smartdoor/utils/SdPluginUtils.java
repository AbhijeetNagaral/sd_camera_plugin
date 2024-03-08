package com.smartdoor.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SdPluginUtils {
    public static String formatIpAddress(int ip) {
        byte[] ipAddress = new byte[4];
        InetAddress myaddr;
        try {
            ipAddress[3] = (byte) ((ip >> 24) & 0xff);
            ipAddress[2] = (byte) ((ip >> 16) & 0xff);
            ipAddress[1] = (byte) ((ip >> 8) & 0xff);
            ipAddress[0] = (byte) (ip & 0xff);
            myaddr = InetAddress.getByAddress(ipAddress);
            String hostaddr = myaddr.getHostAddress();
            return hostaddr;
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public static int getEncrypPasswordType(String capabilities) {

        if (capabilities.contains("WPA2") && capabilities.contains("CCMP")) {
            // sEncrypType = "AES";
            // sAuth = "WPA2";
            return 1;
        } else if (capabilities.contains("WPA2")
                && capabilities.contains("TKIP")) {
            // sEncrypType = "TKIP";
            // sAuth = "WPA2";
            return 2;
        } else if (capabilities.contains("WPA")
                && capabilities.contains("TKIP")) {
            // EncrypType = "TKIP";
            // sAuth = "WPA";
            return 2;
        } else if (capabilities.contains("WPA")
                && capabilities.contains("CCMP")) {
            // sEncrypType = "AES";
            // sAuth = "WPA";
            return 1;
        } else if (capabilities.contains("WEP")) {
            return 3;
        } else {
            // sEncrypType = "NONE";
            // sAuth = "OPEN";
            return 0;
        }
    }

}
