package com.oilStationMap.utils.PublishFriendCircleUtils;

import com.google.common.collect.Maps;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.service.WX_DicService;
import com.oilStationMap.service.impl.WX_DicServiceImpl;
import com.oilStationMap.utils.ApplicationContextUtils;
import com.oilStationMap.utils.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 发布朋友圈工具
 * appium -p 4723 -bp 4724 --session-override
 */
public class PublishFriendCircleUtils {

    public static final Logger logger = LoggerFactory.getLogger(PublishFriendCircleUtils.class);

    public static WX_DicService wxDicService = (WX_DicService) ApplicationContextUtils.getBeanByClass(WX_DicServiceImpl.class);

    /**
     * 发布朋友圈for所有设备
     * @param paramMap
     */
    public static void sendFriendCircle(Map<String, Object> paramMap){
        //此处可以从数据库获取类名,通过反射一次性执行,后期就只需要维护数据库脚本即可
//        try{
//            new HuaWeiP20Pro().sendFriendCircle(paramMap);
//            Thread.sleep(5000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try{
//            new HuaWeiMate7Index1().sendFriendCircle(paramMap);
//            Thread.sleep(5000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try{
//            new HuaWeiMate7Index2().sendFriendCircle(paramMap);
//            Thread.sleep(5000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try{
//            new HuaWeiMate7Index4().sendFriendCircle(paramMap);
//            Thread.sleep(5000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try{
//            new ZhongXing529Index5().sendFriendCircle(paramMap);            //未成功
//            Thread.sleep(5000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try{
            new HuaWeiMate8Index6().sendFriendCircle(paramMap);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try{
//            new HuaWeiMate8Index7().sendFriendCircle(paramMap);
//            Thread.sleep(5000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try{
//            new HuaWeiMate8Index8().sendFriendCircle(paramMap);
//            Thread.sleep(5000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try{
//            new HuaWeiMate8Index9().sendFriendCircle(paramMap);
//            Thread.sleep(5000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try{
//            new XiaoMiMax3().sendFriendCircle(paramMap);
//            Thread.sleep(5000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static void main(String[] args) {
        Map<String, Object> paramMap = Maps.newHashMap();

//        paramMap.put("action", "textMessageFriendCircle");
//        paramMap.put("content", "/玫瑰我们做的是广告，广告的目的是广而告之。 /微笑央视同样不保证效果，广告推广的意义就在于提高产品的知名度和覆盖面。/愉快推广面越广，覆盖人群越多，才越容易被接受。正规公司，全国统一价。 /勾引谈的是价值，不是价格。正品和高仿，您更愿意选择哪个？ /闪电不值得的花一分钱也是多， /闪电值得的一百万也值得。 /闪电 认准品牌， /闪电认准实力。/强 /强 /强 ");

//        paramMap.put("action", "imgMessageFriendCircle");
//        paramMap.put("photoNum", "1");
//        paramMap.put("content", "/玫瑰我们做的是广告，广告的目的是广而告之。 /微笑央视同样不保证效果，广告推广的意义就在于提高产品的知名度和覆盖面。/愉快推广面越广，覆盖人群越多，才越容易被接受。正规公司，全国统一价。 /勾引谈的是价值，不是价格。正品和高仿，您更愿意选择哪个？ /闪电不值得的花一分钱也是多， /闪电值得的一百万也值得。 /闪电 认准品牌， /闪电认准实力。/强 /强 /强 ");

        paramMap.put("dicType", "publishFriendCircle");
        ResultDTO resultDTO = wxDicService.getLatelyDicByCondition(paramMap);
        List<Map<String, String>> resultList = resultDTO.getResultList();
        if(resultList != null && resultList.size() > 0){
            Map<String, Object> sendFriendCircleParam = MapUtil.getObjectMap(resultList.get(0));
            new PublishFriendCircleUtils().sendFriendCircle(sendFriendCircleParam);
        }



    }
}
