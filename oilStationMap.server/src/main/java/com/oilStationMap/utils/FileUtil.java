package com.oilStationMap.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件转化工具
 * Created by caihongwang on 2018/1/31.
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 替换文件中字符串
     *
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

                if (str == null) {
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
            reader.close();

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
     *
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
                copyDirAndFile(sourcePath + File.separator + filePath[i], newPath + File.separator + filePath[i]);
            }
            if (new File(sourcePath + File.separator + filePath[i]).isFile()) {
                copyFile(sourcePath + File.separator + filePath[i], newPath + File.separator + filePath[i]);
            }
        }
    }

    /**
     * 复制文件
     *
     * @param oldPath
     * @param newPath
     * @throws IOException
     */
    public static void copyFile(String oldPath, String newPath) {
        File oldFile = new File(oldPath);
        File file = new File(newPath);
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(oldFile);
            out = new FileOutputStream(file);
            byte[] buffer = new byte[2097152];
            int readByte = 0;
            while ((readByte = in.read(buffer)) != -1) {
                out.write(buffer, 0, readByte);
            }
        } catch (Exception e) {
            logger.info("复制文件夹的子文件内容失败...");
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    logger.info("关闭 in 流失败...");
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    logger.info("关闭 out 流失败...");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建文件
     *
     * @param imgBase64Data 文件的imgBase64Data
     * @param fileSuffix    文件后缀名，默认是jpg
     * @return
     */
    public static String createFile(String imgBase64Data, String currentFileUploadUrl, String fileSuffix) {
        String fileUploadUrl_temp = "";
        FileUtil fileUtil = new FileUtil();
        if (!"".equals(imgBase64Data)) {
            if (fileSuffix == null || "".equals(fileSuffix)) {
                fileSuffix = "jpg";
            }
            try {
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
                if (!dirFile.exists()) {
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
     *
     * @param wb         文件的Object
     * @param fileSuffix 文件后缀名，默认是jpg
     * @return
     */
    public static String createFile(HSSFWorkbook wb, String currentFileUploadUrl, String fileSuffix) {
        String fileUploadUrl_temp = "";
        FileUtil fileUtil = new FileUtil();
        if (wb != null) {
            if (fileSuffix == null || "".equals(fileSuffix)) {
                fileSuffix = "xls";
            }
            try {
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
                if (!dirFile.exists()) {
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

    /**
     * 创建文件
     *
     * @param wb         文件的Object
     * @param fileSuffix 文件后缀名，默认是jpg
     * @return
     */
    public static String createFile(HSSFWorkbook wb, String currentFileUploadUrl, String fileName, String fileSuffix) {
        String fileUploadUrl_temp = "";
        FileUtil fileUtil = new FileUtil();
        if (wb != null) {
            if (fileSuffix == null || "".equals(fileSuffix)) {
                fileSuffix = "xls";
            }
            try {
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
                if (!dirFile.exists()) {
                    dirFile.mkdir();
                }
                Date currentDate = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
                fileUploadUrl_temp = currentFileUploadUrl + fileName + "_" + formatter.format(currentDate) + "." + fileSuffix;
                File fileUploadUrl_tempFile = new File(fileUploadUrl_temp);
                if (fileUploadUrl_tempFile.exists()) {
                    boolean deleteFlag = FileUtil.deleteDir(fileUploadUrl_tempFile);
                }
                FileOutputStream out = new FileOutputStream(fileUploadUrl_tempFile);
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

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * @param filePath 文件将要保存的目录
     * @param method   请求方法，包括POST和GET
     * @param url      请求的路径
     * @return
     * @从制定URL下载文件并保存到指定目录
     */
    public static File saveUrlAs(String url, String filePath, String method) {
        //创建不同的文件夹目录
        File file = new File(filePath);
        //判断文件夹是否存在
        if (!file.exists()) {
            //如果文件夹不存在，则创建新的的文件夹
            file.mkdirs();
        }
        FileOutputStream fileOut = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try {
            // 建立链接
            URL httpUrl = new URL(url);
            conn = (HttpURLConnection) httpUrl.openConnection();
            //以Post方式提交表单，默认get方式
            conn.setRequestMethod(method);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // post方式不能使用缓存
            conn.setUseCaches(false);
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            inputStream = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            //判断文件的保存路径后面是否以/结尾
            if (!filePath.endsWith("/")) {

                filePath += "/";

            }
            //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
            fileOut = new FileOutputStream(filePath + "123.png");
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);

            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            //保存文件
            while (length != -1) {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("抛出异常！！");
        }
        return file;
    }
}
