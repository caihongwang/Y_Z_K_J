package com.oilStationMap.service.impl;

import com.oilStationMap.MySuperTest;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.utils.MapUtil;
import com.oilStationMap.utils.WX_PublicNumberUtil;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * caihongwang
 */
public class WX_CustomMenuServiceImplTest extends MySuperTest {

    private static final Logger logger = LoggerFactory.getLogger(WX_CommonServiceImpl.class);

    @Test
    public void TEST() throws Exception {

        //油价地图的菜单
        Map<String, Object> paramMap = Maps.newHashMap();
        this.createCustomMenu(paramMap);

//        getWxUserInfo(
//                "o8-g249hJL8mmxq6MGsxIAAz4ZaM",
//                "wx07cf52be1444e4b7",
//                "d6de12032cfe660253b96d5f2868a06c");
    }

    /**
     * 创建自定义菜单
     * @param paramMap
     * @return
     */
    public ResultMapDTO createCustomMenu(Map<String, Object> paramMap) {
        logger.info("在service中创建公众号自定义菜单-createCustomMenu,请求-paramMap:" + paramMap);
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        String menuStr = paramMap.get("menuStr")!=null?paramMap.get("menuStr").toString():"";
        if("".equals(menuStr)){
            menuStr = "{\n" +
                    "    \"button\":[\n" +

//                    "        {\n" +
//                    "            \"name\":\"油站运营\",\n" +
//                    "            \"sub_button\":[\n" +
//                    "                {\n" +
//                    "                    \"type\":\"view\",\n" +
//                    "                    \"name\":\"装逼神器\",\n" +
//                    "                    \"url\":\"http://www.91caihongwang.com:90/zhuangbi\"\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                    \"type\":\"view\",\n" +
//                    "                    \"name\":\"免费WIFI\",\n" +
//                    "                    \"url\":\"http://wifi.weixin.qq.com/mbl/connect.xhtml?type=1\"\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                    \"type\":\"view\",\n" +
//                    "                    \"name\":\"油站神器\",\n" +
//                    "                    \"url\":\"https://mp.weixin.qq.com/s/w2X4xUE0XGrC2lu4Oe475Q\"\n" +
//                    "                }\n" +
//                    "            ]\n" +
//                    "        },\n" +

                    "        {\n" +
                    "            \"type\":\"miniprogram\",\n" +
                    "            \"name\":\"油价资讯\",\n" +
                    "            \"url\":\"http://mp.weixin.qq.com\",\n" +
                    "            \"appid\":\"wx701956c2c1a14a26\",\n" +
                    "            \"pagepath\":\"pages/other/information/wxPublicNumberInformation/index\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"type\":\"miniprogram\",\n" +
                    "            \"name\":\"加油红包\",\n" +
                    "            \"url\":\"http://mp.weixin.qq.com\",\n" +
                    "            \"appid\":\"wx5d3ead40a644f052\",\n" +
                    "            \"pagepath\":\"pages/other/activity/redActivity/index\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"type\":\"miniprogram\",\n" +
                    "            \"name\":\"油价地图\",\n" +
                    "            \"url\":\"http://mp.weixin.qq.com\",\n" +
                    "            \"appid\":\"wxaca42346c1a46d5d\",\n" +
                    "            \"pagepath\":\"pages/tabBar/todayOilPrice/todayOilPrice\"\n" +
                    "        }\n" +
//                    "        {\n" +
//                    "            \"name\":\"油站神器\",\n" +
//                    "            \"type\":\"view\",\n" +
//                    "            \"url\":\"https://mp.weixin.qq.com/s/w2X4xUE0XGrC2lu4Oe475Q\"\n" +
//                    "        }\n" +
//                    "        {\n" +
//                    "            \"name\":\"福利中心\",\n" +
//                    "            \"type\":\"view\",\n" +
//                    "            \"url\":\"https://engine.seefarger.com/index/activity?appKey=2mdmXx9VAtdWaZbHc9SeiD1DJaT6&adslotId=290262\"\n" +
//                    "        }\n" +

//                    "        {\n" +
//                    "            \"name\":\"关于我们\",\n" +
//                    "            \"sub_button\":[\n" +
//                    "                {\n" +
//                    "                    \"type\":\"view\",\n" +
//                    "                    \"name\":\"我在这里\",\n" +
//                    "                    \"url\":\"https://mp.weixin.qq.com/s/ifBVk8VBUci1yPCVUug4hg\"\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                    \"type\":\"view\",\n" +
//                    "                    \"name\":\"营业执照\",\n" +
//                    "                    \"url\":\"https://mp.weixin.qq.com/s/9OZP7KFScJkXBa9JslGirg\"\n" +
//                    "                }\n" +
//                    "            ]\n" +
//                    "        }\n" +
                    "    ]\n" +
                    "}\n" +
                    "\n";
        }
        resultMap = WX_PublicNumberUtil.createCustomMenu(menuStr);
        if (resultMap != null && resultMap.size() > 0) {
            resultMapDTO.setResultMap(MapUtil.getStringMap(resultMap));
            resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
            resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        } else {
            resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        logger.info("在service中创建公众号自定义菜单-createCustomMenu,响应-response:" + resultMapDTO);
        return resultMapDTO;
    }



    public Map<String, Object> getWxUserInfo(String openId, String appId, String secret){
        Map<String, Object> userInfoMap = Maps.newHashMap();
        //获取accessToken
        Map<String, Object> accessTokenMap = WX_PublicNumberUtil.getAccessToken(appId, secret);
        if (accessTokenMap != null && accessTokenMap.size() > 0) {
            String accessToken = accessTokenMap.get("access_token") != null ? accessTokenMap.get("access_token").toString() : "";
            //拉取用户信息(需scope为 snsapi_userinfo)
            Map<String, Object> snsApiUserInfoMap = WX_PublicNumberUtil.getCgiBinUserInfo(openId, appId, secret);
            logger.info("snsApiUserInfoMap: " + snsApiUserInfoMap);
            Integer errCode = snsApiUserInfoMap.get("errcode") != null ? Integer.parseInt(snsApiUserInfoMap.get("errcode").toString()) : null;
            if ((errCode == null || errCode.intValue() == 0)
                    && snsApiUserInfoMap.size() > 0) {
                //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
                String country = snsApiUserInfoMap.get("country") != null ? snsApiUserInfoMap.get("country").toString() : "";//国家
                String province = snsApiUserInfoMap.get("province") != null ? snsApiUserInfoMap.get("province").toString() : "";//省份
                String city = snsApiUserInfoMap.get("city") != null ? snsApiUserInfoMap.get("city").toString() : "";//城市
                String wxpOpenId = snsApiUserInfoMap.get("openid") != null ? snsApiUserInfoMap.get("openid").toString() : "";//openid
                String gender = snsApiUserInfoMap.get("sex") != null ? snsApiUserInfoMap.get("sex").toString() : "";  //性别
                String nickName = snsApiUserInfoMap.get("nickname") != null ? snsApiUserInfoMap.get("nickname").toString() : "";  //昵称
                String avatarUrl = snsApiUserInfoMap.get("headimgurl") != null ? snsApiUserInfoMap.get("headimgurl").toString() : "";//头像
                String language = snsApiUserInfoMap.get("language") != null ? snsApiUserInfoMap.get("language").toString() : "";//语言

                //保存用户信息
                userInfoMap.put("country", country);
                userInfoMap.put("province", province);
                userInfoMap.put("city", city);
                userInfoMap.put("openId", openId);
                userInfoMap.put("gender", gender);
                userInfoMap.put("nickName", nickName);
                userInfoMap.put("avatarUrl", avatarUrl);
                userInfoMap.put("language", language);
                userInfoMap.put("avatarUrl", avatarUrl);
            } else {
                //获取用户信息失败
                logger.info("在 获取微信用户信息 时 失败，可以无视.");
            }
        } else {
            //获取access_token失败
            logger.info("在 获取access_token失败 时 失败，可以无视.");
        }
        return userInfoMap;
    }

}
