package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import android.util.Log;

import java.io.*;
import java.net.*;

public class WolModule extends ReactContextBaseJavaModule {

    public static final int PORT = 9;    

    private final ReactApplicationContext reactContext;
    private static final String TAG = WolModule.class.getSimpleName();

    public WolModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @ReactMethod
    public static void send(String ipStr, String macStr, Callback callback) {        
        try {
            byte[] macBytes = getMacBytes(macStr);
            byte[] bytes = new byte[6 + 16 * macBytes.length];
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) 0xff;
            }
            for (int i = 6; i < bytes.length; i += macBytes.length) {
                System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
            }
            
            InetAddress address = InetAddress.getByName(ipStr);
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, PORT);
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            socket.close();
            
            Log.v(TAG, "Wake-on-LAN packet sent.");
            callback.invoke(true, "Wake-on-LAN packet has been sent");
        }
        catch (Exception e) {
            Log.v(TAG, "Failed to send Wake-on-LAN packet: + e");
            callback.invoke(false, "Wake-on-LAN packet has not been sent");
        }
        
    }
    
    private static byte[] getMacBytes(String macStr) throws IllegalArgumentException {
        byte[] bytes = new byte[6];
        String[] hex = macStr.split("(\\:|\\-)");
        if (hex.length != 6) {
            throw new IllegalArgumentException("Invalid MAC address.");
        }
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hex digit in MAC address.");
        }
        return bytes;
    }

    @Override
    public String getName() {
        return "Wol";
    }

    // @ReactMethod
    // public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
    //     // TODO: Implement some actually useful functionality
    //     callback.invoke("Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
    // }
}
