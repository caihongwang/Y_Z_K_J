package com.automation.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CommandUtil {

    /**
     * 执行shell文件
     * @param command
     * @return
     * @throws IOException
     */
    public static String run(String command) throws IOException {
        String result = "";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            try {
                //等待命令执行完成
                process.waitFor(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("UTF-8")));
            String line = null;
            while ((line = br.readLine()) != null) {
                result += line + "\n";
            }
        } finally {
            if (process != null) {
                process.destroy();
            }
            //输出命令及结果
            System.out.println();
            System.out.println(command);
            System.out.println(result);
            System.out.println();
        }
        return result;
    }

    /**
     * 执行单条命令
     * @param command
     * @return
     * @throws IOException
     */
    public static String run(String[] command) throws IOException {
        Scanner input = null;
        String result = "";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            try {
                //等待命令执行完成
                process.waitFor(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("UTF-8")));
            String line = null;
            while ((line = br.readLine()) != null) {
                result += line + "\n";
            }
        } finally {
            if (input != null) {
                input.close();
            }
            if (process != null) {
                process.destroy();
            }
            //输出命令及结果
            System.out.println();
            for (String str : command) {
                System.out.print(str + "\t");
            }
            System.out.println();
            System.out.println(result);
            System.out.println();
        }
        return result;
    }


    /**
     * 通过adb devices命令检测android设备是否在线
     * @param deviceName
     * @return
     * @throws IOException
     */
    public static boolean isOnline4AndroidDevice(String deviceName) throws IOException {
        boolean isOnlineFlag = false;
        String commandStr = "/opt/android_sdk/platform-tools/adb devices";
        String commandRes = CommandUtil.run(commandStr);
        if(commandRes.contains(deviceName)){
            isOnlineFlag = true;
        } else {
            isOnlineFlag = false;
        }
        return isOnlineFlag;
    }



    public static void main(String[] args) throws Exception{
//        String commandStr = "ping blog.yoodb.com";
        String commandStr = "/opt/android_sdk/platform-tools/adb devices";
//        String commandStr = " ps -ef|grep appium | awk '{print $2}'";
        CommandUtil.run(commandStr);
        CommandUtil.run(new String[]{"/bin/sh", "-c", commandStr});
//        System.out.println("isOnline4AndroidDevice = " + isOnline4AndroidDevice("候车室"));
    }


}