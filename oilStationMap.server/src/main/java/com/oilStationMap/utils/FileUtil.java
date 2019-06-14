package com.oilStationMap.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.UUID;

/**
 * 文件转化工具
 * Created by caihongwang on 2018/1/31.
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 替换文件中字符串
     * @param filePath
     */
    public static void replaceStrInFile(String filePath, String target, String newContent) {
        try {
            File file = new File(filePath);
            InputStream is = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is));

            String filename = file.getName();
            // tmpfile为缓存文件，代码运行完毕后此文件将重命名为源文件名字。
            File tmpfile = new File(file.getParentFile().getAbsolutePath()
                    + "\\" + filename + ".tmp");

            BufferedWriter writer = new BufferedWriter(new FileWriter(tmpfile));

            boolean flag = false;
            String str = null;
            while (true) {
                str = reader.readLine();

                if (str == null){
                    break;
                }

                if (str.contains(target)) {
                    str = str.replace(target, newContent);
                    writer.write(str + "\n");
                    flag = true;
                } else {
                    writer.write(str + "\n");
                }
            }

            is.close();

            writer.flush();
            writer.close();

            if (flag) {
                file.delete();
                tmpfile.renameTo(new File(file.getAbsolutePath()));
            } else {
                tmpfile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制文件夹及其文件内容
     * @param sourcePath
     * @param newPath
     * @throws IOException
     */
    public static void copyDirAndFile(String sourcePath, String newPath) throws IOException {
        File file = new File(sourcePath);
        String[] filePath = file.list();
        if (!(new File(newPath)).exists()) {
            (new File(newPath)).mkdir();
        } else {
            (new File(newPath)).delete();
            (new File(newPath)).mkdir();
        }
        for (int i = 0; i < filePath.length; i++) {
            if ((new File(sourcePath + File.separator + filePath[i])).isDirectory()) {
                copyDirAndFile(sourcePath  + File.separator  + filePath[i], newPath  + File.separator + filePath[i]);
            }
            if (new File(sourcePath  + File.separator + filePath[i]).isFile()) {
                copyFile(sourcePath + File.separator + filePath[i], newPath + File.separator + filePath[i]);
            }
        }
    }

    /**
     * 复制文件
     * @param oldPath
     * @param newPath
     * @throws IOException
     */
    public static void copyFile(String oldPath, String newPath) throws IOException {
        File oldFile = new File(oldPath);
        File file = new File(newPath);
        FileInputStream in = new FileInputStream(oldFile);
        FileOutputStream out = new FileOutputStream(file);;

        byte[] buffer=new byte[2097152];
        int readByte = 0;
        while((readByte = in.read(buffer)) != -1){
            out.write(buffer, 0, readByte);
        }
        in.close();
        out.close();
    }

    /**
     * 创建文件
     * @param imgBase64Data   文件的imgBase64Data
     * @param fileSuffix   文件后缀名，默认是jpg
     * @return
     */
    public static String createFile(String imgBase64Data, String currentFileUploadUrl, String fileSuffix) {
        String fileUploadUrl_temp = "";
        FileUtil fileUtil = new FileUtil();
        if(!"".equals(imgBase64Data)){
            if(fileSuffix == null || "".equals(fileSuffix)){
                fileSuffix = "jpg";
            }
            try{
                int size;
                byte[] buffer = new byte[1024 * 1000000];
                long startTime = System.currentTimeMillis();
                //判断 文件夹 是否存存在，如果不存在则创建
                File dirFile = new File(currentFileUploadUrl);
                if (!dirFile.getParentFile().getParentFile().exists()) {
                    dirFile.getParentFile().getParentFile().mkdirs();
                }
                if (!dirFile.getParentFile().exists()) {
                    dirFile.getParentFile().mkdirs();
                }
                if(!dirFile.exists()){
                    dirFile.mkdir();
                }
                fileUploadUrl_temp = currentFileUploadUrl + UUID.randomUUID() + "_" + startTime + "." + fileSuffix;
                buffer = new BASE64Decoder().decodeBuffer(imgBase64Data);
                FileOutputStream out = new FileOutputStream(fileUploadUrl_temp);
                out.write(buffer);
                out.close();
                logger.info("创建本地图片文件，在当前服务器中的本地地址 : " + fileUploadUrl_temp);
            } catch (Exception e) {
                logger.error("创建本地图片文件失败. e : " + e);
            }
        } else {
            logger.error("创建本地图片文件失败. imgBase64Data 不允许为空。");
        }
        return fileUploadUrl_temp;
    }
    /**
     * 创建文件
     * @param wb   文件的Object
     * @param fileSuffix   文件后缀名，默认是jpg
     * @return
     */
    public static String createFile(HSSFWorkbook wb, String currentFileUploadUrl, String fileSuffix) {
        String fileUploadUrl_temp = "";
        FileUtil fileUtil = new FileUtil();
        if(wb != null){
            if(fileSuffix == null || "".equals(fileSuffix)){
                fileSuffix = "xls";
            }
            try{
                int size;
                byte[] buffer = new byte[1024 * 1000000];
                long startTime = System.currentTimeMillis();
                //判断 文件夹 是否存存在，如果不存在则创建
                File dirFile = new File(currentFileUploadUrl);
                if (!dirFile.getParentFile().getParentFile().exists()) {
                    dirFile.getParentFile().getParentFile().mkdirs();
                }
                if (!dirFile.getParentFile().exists()) {
                    dirFile.getParentFile().mkdirs();
                }
                if(!dirFile.exists()){
                    dirFile.mkdir();
                }
                fileUploadUrl_temp = currentFileUploadUrl + UUID.randomUUID() + "_" + startTime + "." + fileSuffix;
                FileOutputStream out = new FileOutputStream(fileUploadUrl_temp);
                wb.write(out);
                out.close();
                logger.info("创建本地exls文件，在当前服务器中的本地地址 : " + fileUploadUrl_temp);
            } catch (Exception e) {
                logger.error("创建本地exls文件失败. e : " + e);
            }
        } else {
            logger.error("创建本地图片文件失败. imgBase64Data 不允许为空。");
        }
        return fileUploadUrl_temp;
    }
}
