package com.oilStationMap.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
            String line = null;
            while ((line = br.readLine()) != null) {
                result += line + "\n";
            }
            result = command + "\n" + result; //加上命令本身，打印出来
        } finally {
            if (process != null) {
                process.destroy();
            }
            System.out.println(result);
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
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
            String line = null;
            while ((line = br.readLine()) != null) {
                result += line + "\n";
            }
            result = command + "\n" + result; //加上命令本身，打印出来
        } finally {
            if (input != null) {
                input.close();
            }
            if (process != null) {
                process.destroy();
            }
            System.out.println(result);
        }
        return result;
    }


    public static void main(String[] args) throws Exception{
//        String commandStr = "ping blog.yoodb.com";
        String commandStr = "/opt/android_sdk/platform-tools/adb devices";
//        CommandUtil.exeCmd(commandStr);
        CommandUtil.run(commandStr);
        CommandUtil.run(new String[]{"/bin/sh", "-c", commandStr});
    }


}