package com.newMall.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oilStationMap.utils.FileUtil;
import com.oilStationMap.utils.NumberUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 粉象生活 抓取存在优惠券的淘口令
 */
public class SpriderForFenXiangShengHuoUtil {

    public static final Logger logger = LoggerFactory.getLogger(SpriderForFenXiangShengHuoUtil.class);

    /**
     * 获取所有粉象生活的商品
     * @param paramMap
     */
    public static void getFenXiangShengHuoProduct(Map<String, Object> paramMap) {
        List<String> productSqlList = Lists.newArrayList();
        String[] excelHeader = {"产品图片", "产品名称", "产品价格", "优惠券金额","券后价格", "专属领券链接/淘口令", "备注"};
        int[] excelHeaderWidth = {300, 400, 125, 125, 125, 270, 250};
        String fenXiangProductPath = "/opt/resourceOfOilStationMap/webapp/fen_xiang_sheng_huo/json/";
        //获取当前文件夹下的所有文件
        File fenXiangProductPathDir = new File(fenXiangProductPath);
        if (fenXiangProductPathDir.isDirectory()) {
            String[] productCategrylist = fenXiangProductPathDir.list();
            for (int i = 0; i < productCategrylist.length; i++) {
                String productCategryPath = fenXiangProductPath + productCategrylist[i];
                File productCategryFile = new File(productCategryPath);
                if (productCategrylist[i].startsWith(".")){     //mac系统的隐藏文件
                    continue;
                }
                if (productCategryFile.isDirectory()) {
                    String productCatoryName = productCategryFile.getName();
                    //删除已有的商品类目,如果存在则删除
//                    String currentPath = "/opt/resourceOfOilStationMap/webapp/fen_xiang_sheng_huo/excel/";              //服务器地址
                    String currentPath = "/Users/caihongwang/ownCloud/铜仁市碧江区智惠加油站科技服务工作室/粉象生活/excel/";        //ownCloud同步地址
                    File productCatoryFile = new File(currentPath + productCatoryName);
//                    if(productCatoryFile.exists()){
//                        boolean deleteFlag = FileUtil.deleteDir(productCatoryFile);
//                    }
                    String[] productlist = productCategryFile.list();
                    if(productlist == null || productlist.length <= 0){
                        continue;
                    }
                    logger.info("createXLSX begin...");
                    try {
                        Date currentDate = new Date();          //定时任务在每天下午17点整开始生成报表
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(currentDate);
                        calendar.add(Calendar.DAY_OF_MONTH, -1);
                        HSSFWorkbook wb = new HSSFWorkbook();
                        HSSFSheet sheet = wb.createSheet(productCatoryName + "_隐藏优惠券");
                        // 设置标题头居中样式
                        HSSFCellStyle headStyle = wb.createCellStyle();
                        headStyle.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE); //下边框
//                        headStyle.setFillForegroundColor(IndexedColors.GREEN.index);// 设置背景色
//                        headStyle.setFillBackgroundColor(IndexedColors.GREEN.index);// 设置背景色
                        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
                        headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
                        HSSFFont font = wb.createFont();
                        font.setColor(HSSFColor.GREEN.index);
                        font.setFontHeightInPoints((short) 16);//设置字体大小
                        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
                        headStyle.setFont(font);

                        HSSFRow row = sheet.createRow((int) 0);
                        row.setHeightInPoints((short)30);
                        // 设置列宽度（像素）
                        for (int j = 0; j < excelHeaderWidth.length; j++) {
                            sheet.setColumnWidth(j, excelHeaderWidth[j] * 32);
                        }
                        // 添加表格头
                        for (int j = 0; j < excelHeader.length; j++) {
                            HSSFCell cell = row.createCell(j);
                            cell.setCellValue(excelHeader[j]);
                            cell.setCellStyle(headStyle);
                        }
                        for(int j = 0; j < productlist.length; j++){
                            String product = productlist[j];
                            if (product.startsWith(".")){       //mac系统的隐藏文件
                                continue;
                            }
                            String productPath = productCategryFile.getPath() + "/" + product;
                            File productFile = new File(productPath);
                            StringBuilder productJson = new StringBuilder();
                            try{
                                BufferedReader br = new BufferedReader(new FileReader(productFile));//构造一个BufferedReader类来读取文件
                                String s = null;
                                while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                                    productJson.append(System.lineSeparator()+s);
                                }
                                br.close();
                                JSONObject productJsonObject = JSONObject.parseObject(productJson.toString());
                                JSONObject productData = productJsonObject.getJSONObject("data");
                                //产品ID
                                String productId = productData.getString("itemIdStr");
                                //产品名称
                                String productName = productData.getString("itemTitle");
                                //产品图片
                                String productHeadImage = productData.getString("itemPicUrl");
                                //产品原价
                                Integer productPrice = productData.getInteger("itemPrice");
                                //券后价格
                                Integer productDiscountPrice = productData.getInteger("itemDiscountPrice");
                                //淘口令
                                String taoBaoToken = productData.getString("shareUrl");

                                // 设置 单元格 居中样式
                                HSSFCellStyle cellStyle = wb.createCellStyle();
                                cellStyle.setBorderBottom(HSSFCellStyle.BORDER_DASH_DOT); //下边框
                                cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
                                cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
                                HSSFFont cellFont = wb.createFont();
                                cellFont.setColor(HSSFColor.BLACK.index);
                                cellFont.setFontHeightInPoints((short) 14);//设置字体大小
                                cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
                                cellStyle.setFont(cellFont);

                                row = sheet.createRow(j + 1);
                                row.setHeightInPoints((short)150);


                                try{
                                    ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();               //图片
                                    URL productHeadImgUrl = new URL(productHeadImage);
                                    BufferedImage bufferImg = ImageIO.read(productHeadImgUrl);
                                    ImageIO.write(bufferImg, "jpg", byteArrayOut);
                                    HSSFPatriarch patri_item = sheet.createDrawingPatriarch();
                                    short startRowIndex = (short) (1 + j);
                                    short endRowIndex = (short) (2 + j);
                                    HSSFClientAnchor anchor_item = new HSSFClientAnchor(0, 0, 0, 0, (short)0, startRowIndex, (short)1, endRowIndex);
                                    patri_item.createPicture(anchor_item , wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
                                } catch (Exception e) {
                                    logger.info("产品图片 异常， productHeadImgUrl = " + productHeadImage);
                                }


                                HSSFCell cell_1 = row.createCell(1);
                                cell_1.setCellStyle(cellStyle);
                                cell_1.setCellValue(productName != null ? productName : "");                    //产品名称


                                HSSFCell cell_2 = row.createCell(2);
                                cell_2.setCellStyle(cellStyle);
                                Double productPrice_double = 0D;
                                try{
                                    if(productPrice != null){
                                        productPrice_double = NumberUtil.getPointTowNumber(((double)productPrice / 100));
                                    }
                                } catch (Exception e) {
                                    logger.info("产品原价 异常， productPrice = " + productPrice);
                                }
                                cell_2.setCellValue((productPrice_double != null ? productPrice_double.toString() : "") + " 元");       //产品原价


                                HSSFCell cell_4 = row.createCell(4);
                                cell_4.setCellStyle(cellStyle);
                                Double productDiscountPrice_double = 0D;
                                try{
                                    if(productPrice != null){
                                        productDiscountPrice_double = NumberUtil.getPointTowNumber(((double)productDiscountPrice / 100));
                                    }
                                } catch (Exception e) {
                                    logger.info("券后价格 异常， productDiscountPrice = " + productDiscountPrice);
                                }
                                cell_4.setCellValue((productDiscountPrice_double != null  ? productDiscountPrice_double.toString() : "") + " 元");  //券后价格


                                HSSFCellStyle tempCellStyle = wb.createCellStyle();
                                tempCellStyle.setBorderBottom(HSSFCellStyle.BORDER_DASH_DOT); //下边框
                                tempCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
                                tempCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
                                HSSFFont tempCellFont = wb.createFont();
                                tempCellFont.setColor(HSSFColor.RED.index);
                                tempCellFont.setFontHeightInPoints((short) 14);//设置字体大小
                                tempCellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
                                tempCellStyle.setFont(tempCellFont);
                                HSSFCell cell_3 = row.createCell(3);
                                cell_3.setCellStyle(tempCellStyle);
                                Double discountPrice = productPrice_double-productDiscountPrice_double;
                                cell_3.setCellValue(discountPrice.toString() + " 元");          //优惠券金额


                                HSSFCell cell_5 = row.createCell(5);
                                cell_5.setCellStyle(cellStyle);
                                cell_5.setCellValue(taoBaoToken != null ? taoBaoToken : "");                    //淘口令


                                HSSFCell cell_6 = row.createCell(6);
                                cell_6.setCellStyle(cellStyle);
                                cell_6.setCellValue("抓紧了，优惠券秒慢无!!!");                                    //备注

                                logger.info("类目【"+productCatoryName+"】 ， 商品【"+productName+"】");

                            } catch (Exception e) {
                                System.out.println("路径："+productPath+"的数据有问题.");
                                e.printStackTrace();
                                continue;
                            }
                        }

                        String exlsFilePath = FileUtil.createFile(
                                wb,
                                productCatoryFile.getPath()+"/",
                                productCatoryName+"_隐藏优惠券",
                                "xls"
                        );
                        logger.info("exlsFilePath = " + exlsFilePath);
                        logger.info("exlsFilePath = " + exlsFilePath);
                        logger.info("exlsFileUrl = " + "https://www.91caihongwang.com/" + exlsFilePath.substring(5, exlsFilePath.length()));
                        logger.info("exlsFileUrl = " + "https://www.91caihongwang.com/" + exlsFilePath.substring(5, exlsFilePath.length()));

                    } catch (Exception e) {
                        logger.error("exportProductCatoryExcel is failed。", e);
                    }
                } else {
                    logger.error(productCategryPath + " , 当前路径不是文件夹.");
                }
            }
        } else {
            logger.error(fenXiangProductPath + " , 当前路径不是文件夹.");
        }
    }
}
