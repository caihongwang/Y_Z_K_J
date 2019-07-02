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
        //油价地图
//        paramMap.put("appId", "wxf768b49ad0a4630c");
//        paramMap.put("secret", "a481dd6bc40c9eec3e57293222e8246f");
//        //智恵油站
        paramMap.put("appId", "wx469a910e7e87b9d4");
        paramMap.put("secret", "327047cc315d0c5ab24e62d2efd958c0");
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
        String appId = paramMap.get("appId")!=null?paramMap.get("appId").toString():"wxf768b49ad0a4630c";
        String secret = paramMap.get("secret")!=null?paramMap.get("secret").toString():"a481dd6bc40c9eec3e57293222e8246f";
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
                    "            \"name\":\"油价地图\",\n" +
                    "            \"url\":\"http://mp.weixin.qq.com\",\n" +
                    "            \"appid\":\"wx07cf52be1444e4b7\",\n" +
                    "            \"pagepath\":\"pages/tabBar/todayOilPrice/todayOilPrice\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"type\":\"miniprogram\",\n" +
                    "            \"name\":\"加油红包\",\n" +
                    "            \"url\":\"http://mp.weixin.qq.com\",\n" +
                    "            \"appid\":\"wx06ef82e30bbff9ea\",\n" +
                    "            \"pagepath\":\"pages/other/activity/redActivity/index\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"type\":\"miniprogram\",\n" +
                    "            \"name\":\"油站神器\",\n" +
                    "            \"url\":\"http://mp.weixin.qq.com\",\n" +
                    "            \"appid\":\"wx57a88640fe6eb319\",\n" +
                    "            \"pagepath\":\"pages/other/information/wxPublicNumberInformation/informationDetails/index?scene=materialDetailJson%3D%257B%2522materialDetailUrl%2522%253A%2522http%253A//mp.weixin.qq.com/s%253F__biz%253DMzI1ODMwMzAxMw%253D%253D%2526mid%253D100000204%2526idx%253D1%2526sn%253Dfc727f95af80936dac7db021aadfb014%2526chksm%253D6a0b74865d7cfd90b7e42b556ba4bc1205abee3314cfda91071cb5db4089f072d8f969ee7d95%2523rd%2522%252C%2522materialDetailTitle%2522%253A%2522%25u52A0%25u6CB9%25u7AD9%25u795E%25u5668-%25u81EA%25u52A8%25u552E%25u8D27%25u673A%2522%257D\"\n" +
                    "        }\n" +
//                    "        {\n" +
//                    "            \"name\":\"油站神器\",\n" +
//                    "            \"type\":\"view\",\n" +
//                    "            \"url\":\"https://mp.weixin.qq.com/s/w2X4xUE0XGrC2lu4Oe475Q\"\n" +
//                    "        }\n" +
//                    "        {\n" +
//                    "            \"name\":\"福利中心\",\n" +
//                    "            \"type\":\"view\",\n" +
//                    "            \"url\":\"https://engine.seefarger.com/index/activity?appKey=4Djteae9wZ9noyKnzd1VEeQD4Tiw&adslotId=294762\"\n" +
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
        resultMap = WX_PublicNumberUtil.createCustomMenu(menuStr, appId, secret);
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
