package com.oilStationMap.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.MessageDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.dto.ResultMapDTO;
import com.oilStationMap.service.*;
import com.oilStationMap.utils.HttpsUtil;
import com.oilStationMap.utils.MapUtil;
import com.oilStationMap.utils.WX_PublicNumberUtil;
import com.oilStationMap.dao.WX_UserDao;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.util.*;

/**
 * 用户service
 */
@Service
public class WX_UserServiceImpl implements WX_UserService {

    private static final Logger logger = LoggerFactory.getLogger(WX_UserServiceImpl.class);

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private HttpsUtil httpsUtil;

    @Autowired
    private WX_UserDao wxUserDao;

    @Autowired
    private WX_DicService wxDicService;

    @Autowired
    private WX_CommonService wxCommonService;

    @Autowired
    private WX_MessageService wxMessageService;

    @Autowired
    private WX_AccountService wxAccountService;

    @Value("${oilStationMap.user.miniProgramCode}")
    private String userMiniProgramCodePath;

    @Autowired
    private WX_OilStationOperatorService wxOilStationOperatorService;

    /**
     * 添加用户,根据openId进行注册用户
     * seesion尚未完成
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResultMapDTO login(Map<String, Object> paramMap) {
        Integer addNum = 0;
        Map<String, String> resultMap = Maps.newHashMap();
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        //对微信的用户信息进行解析
        String userInfoStr = paramMap.get("userInfo") != null ? paramMap.get("userInfo").toString() : "";
        if (!"".equals(userInfoStr)) {
            Map<String, Object> userInfoMap = JSONObject.parseObject(userInfoStr, Map.class);
            paramMap.putAll(userInfoMap);
        }
        paramMap.put("grayStatus", "0");
        String code = paramMap.get("code") != null ? paramMap.get("code").toString() : "";
        if (!"".equals(code)) {
            Map<String, String> wx_resultMap =
                    wxCommonService.getOpenIdAndSessionKeyForWX(paramMap).getResultMap();
            if (wx_resultMap != null && wx_resultMap.size() > 0) {
                if (wx_resultMap.get("openid") != null && !"".equals(wx_resultMap.get("openid").toString())) {
                    //获取session,如果没有则创一个有效的session
                    String session_key = wx_resultMap.get("session_key").toString();
                    String openid = wx_resultMap.get("openid").toString();
                    //1.判断用户这个用户是否已经存在,但是也要判断这个用户是否还在会话范围之内
                    Map<String, Object> paramMap_temp = Maps.newHashMap();
                    paramMap_temp.put("openId", openid);
                    List<Map<String, Object>> userList = wxUserDao.getSimpleUserByCondition(paramMap_temp);
                    if (userList != null && userList.size() > 0) {        //存在，则更新
                        String uid = userList.get(0).get("id").toString();
                        String key = paramMap.get("sessionKey") != null ? paramMap.get("sessionKey").toString() : "";
                        if (!"".equals(key)) {                //再次登录存在seesionKey
                            Map<String, Object> tempMap = Maps.newHashMap();
                            tempMap.put("sessionKey", key);
                            BoolDTO boolDTO = this.checkSession(tempMap);           //检测用户的是否还在会话范围之内
                            if (boolDTO.getCode() != 0) {         //如果不在会话范围之内，则将他
                                //获取session,如果没有则创一个有效的session
                                try (Jedis jedis = jedisPool.getResource()) {
                                    jedis.set(key, session_key);        //将session_key作为redius中的value
                                    jedis.expire(key, OilStationMapCode.USER_SESSION_EXPIRED_TIME);
                                }
                                resultMap.put("sessionKey", key);
                                resultMap.put("uid", uid);
                                resultMapDTO.setResultMap(resultMap);
                                resultMapDTO.setCode(OilStationMapCode.USER_EXIST.getNo());
                                resultMapDTO.setMessage(OilStationMapCode.USER_EXIST.getMessage());
                            } else {
                                resultMap.put("sessionKey", key);
                                resultMap.put("uid", uid);
                                resultMapDTO.setResultMap(resultMap);
                                resultMapDTO.setCode(OilStationMapCode.USER_EXIST.getNo());
                                resultMapDTO.setMessage(OilStationMapCode.USER_EXIST.getMessage());
                            }
                        } else {                        //在次登录没有sessionKey则创建sessionKey并保存redius
                            key = UUID.randomUUID().toString();          //将key转化成uuid
                            //设置session
                            Map<String, Object> sessionMap = Maps.newHashMap();
                            sessionMap.put("key", key);
                            sessionMap.put("session_key", session_key);
                            this.setSession(sessionMap);
                            //获取session,如果没有则创一个有效的session
                            try (Jedis jedis = jedisPool.getResource()) {
                                jedis.set(key, session_key);        //将session_key作为redius中的value
                                jedis.expire(key, OilStationMapCode.USER_SESSION_EXPIRED_TIME);
                            }
                            //设置回传参数
                            resultMap.put("sessionKey", key);
                            resultMap.put("uid", uid);
                            resultMapDTO.setResultMap(resultMap);
                            resultMapDTO.setCode(OilStationMapCode.USER_EXIST.getNo());
                            resultMapDTO.setMessage(OilStationMapCode.USER_EXIST.getMessage());
                        }
//                        if ("1762".equals(uid)){
//                            paramMap.clear();
//                            paramMap.put("admin_openId", "oFX2m5C8fpa4o7sHwMFxuAG9zgC8");
//                            paramMap.put("new_openId", openid);
//                            new Thread(){
//                                public void run(){
//                                    try{
//                                        wxMessageService.dailyIllegalUpdateUserInfoMessageSend(paramMap);
//                                    } catch (Exception e1){
//                                        logger.info("服务器异常，发送消息通知管理员异常，e : ", e1);
//                                    }
//                                }
//                            }.start();
//                        }
                    } else {                                            //不存在，则添加
                        //3.添加用户
                        paramMap.put("openId", openid);
                        paramMap.put("source", paramMap.get("accountId"));
                        addNum = wxUserDao.addUser(paramMap);
                        //创建session
                        if (addNum != null && addNum > 0) {
                            //获取session,如果没有则创一个有效的session
                            List<Map<String, Object>> userList_temp = wxUserDao.getSimpleUserByCondition(paramMap);
                            if (userList_temp != null && userList_temp.size() > 0) {
                                //设置session参数
                                String uid = userList_temp.get(0).get("id").toString();
                                String key = UUID.randomUUID().toString();          //将key转化成uuid
                                Map<String, Object> sessionMap = Maps.newHashMap();
                                sessionMap.put("key", key);
                                sessionMap.put("session_key", session_key);
                                this.setSession(sessionMap);
                                //设置回传参数
                                resultMap.put("sessionKey", key);
                                resultMap.put("uid", uid);
                                resultMapDTO.setResultMap(resultMap);
                                resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                                resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
//                                if ("1762".equals(uid)){
//                                    paramMap.clear();
//                                    paramMap.put("admin_openId", "oFX2m5C8fpa4o7sHwMFxuAG9zgC8");
//                                    paramMap.put("new_openId", openid);
//                                    new Thread(){
//                                        public void run(){
//                                            try{
//                                                wxMessageService.dailyIllegalUpdateUserInfoMessageSend(paramMap);
//                                            } catch (Exception e1){
//                                                logger.info("服务器异常，发送消息通知管理员异常，e : ", e1);
//                                            }
//                                        }
//                                    }.start();
//                                }
                            } else {
                                resultMapDTO.setResultMap(resultMap);
                                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                            }
                        } else {
                            resultMapDTO.setResultMap(resultMap);
                            resultMapDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                            resultMapDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
                        }
                    }
                } else {
                    resultMapDTO.setResultMap(resultMap);
                    resultMapDTO.setCode(OilStationMapCode.WX_SERVER_INNER_ERROR.getNo());
                    resultMapDTO.setMessage(OilStationMapCode.WX_SERVER_INNER_ERROR.getMessage());
                }
            } else {
                resultMapDTO.setResultMap(resultMap);
                resultMapDTO.setCode(OilStationMapCode.WX_PARAM_IS_NOT_NULL.getNo());
                resultMapDTO.setMessage(OilStationMapCode.WX_PARAM_IS_NOT_NULL.getMessage());
            }
        } else {

            resultMapDTO.setCode(OilStationMapCode.CODE_IS_NOT_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.CODE_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中添加用户-addUser,结果-result:" + resultMapDTO);
        return resultMapDTO;
    }

    /**
     * 设置用户的session
     * userSessionKey还存在对应的value,则说明用户的会话时间还是有效的
     * 如果失效了，对应的value则会被删除
     *
     * @return
     */
    @Override
    public BoolDTO setSession(Map<String, Object> paramMap) {
        BoolDTO boolDTO = new BoolDTO();
        String key = paramMap.get("key") != null ? paramMap.get("key").toString() : "";
        String session_key = paramMap.get("session_key") != null ? paramMap.get("session_key").toString() : "";
        if (!"".equals(session_key)) {
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.set(key, session_key);        //将session_key作为redius中的value
                jedis.expire(key, OilStationMapCode.USER_SESSION_EXPIRED_TIME);

                boolDTO.setValue(true);
                boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } catch (Exception e) {

                boolDTO.setValue(false);
                boolDTO.setCode(OilStationMapCode.IS_USER_SESSION_OVERDUE.getNo());
                boolDTO.setMessage(OilStationMapCode.IS_USER_SESSION_OVERDUE.getMessage());
//                // TODO: 2018/5/5
//                //临时修改，默认都有效，待建立redis数据库之后，就更改回来
//
//                boolDTO.setValue(true);
//                boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
//                boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
//                logger.error("在service中设置用户会话是否过期-checkSession is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {

            boolDTO.setValue(false);
            boolDTO.setCode(OilStationMapCode.SESSION_KEY_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.SESSION_KEY_IS_NOT_NULL.getMessage());
        }
        return boolDTO;
    }

    /**
     * 获取用户的session
     * userSessionKey还存在对应的value,则说明用户的会话时间还是有效的
     * 如果失效了，对应的value则会被删除
     *
     * @return
     */
    @Override
    public BoolDTO checkSession(Map<String, Object> paramMap) {
        BoolDTO boolDTO = new BoolDTO();
        String userSessionKey = paramMap.get("sessionKey") != null ? paramMap.get("sessionKey").toString() : "";
        if (!"".equals(userSessionKey)) {
            try (Jedis jedis = jedisPool.getResource()) {
                String value = jedis.get(userSessionKey);
                if (value != null && !"".equals(value)) {
                    boolDTO.setValue(true);
                    boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                    boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
                } else {
                    boolDTO.setValue(false);
                    boolDTO.setCode(OilStationMapCode.IS_USER_SESSION_OVERDUE.getNo());
                    boolDTO.setMessage(OilStationMapCode.IS_USER_SESSION_OVERDUE.getMessage());
                }
            } catch (Exception e) {
                boolDTO.setValue(false);
                boolDTO.setCode(OilStationMapCode.IS_USER_SESSION_OVERDUE.getNo());
                boolDTO.setMessage(OilStationMapCode.IS_USER_SESSION_OVERDUE.getMessage());
            }
        } else {

            boolDTO.setValue(false);
            boolDTO.setCode(OilStationMapCode.SESSION_KEY_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.SESSION_KEY_IS_NOT_NULL.getMessage());
        }
        return boolDTO;
    }

    /**
     * 删除用户
     *
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO deleteUser(Map<String, Object> paramMap) {
        Integer deleteNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        deleteNum = wxUserDao.deleteUser(paramMap);
        if (deleteNum != null && deleteNum > 0) {

            boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
            boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        } else {

            boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
            boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
        }
        logger.info("在service中删除用户-deleteUser,结果-result:" + boolDTO);
        return boolDTO;
    }

    /**
     * 更新用户信息
     *
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO updateUser(Map<String, Object> paramMap) {
        Integer updateNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        //对微信的用户信息(包括推荐过来的用户信息)进行解析
        String userInfoStr = paramMap.get("userInfo") != null ? paramMap.get("userInfo").toString() : "";
        if (!"".equals(userInfoStr)) {
            Map<String, Object> userInfoMap = JSONObject.parseObject(userInfoStr, Map.class);
            paramMap.putAll(userInfoMap);
        }
        paramMap.put("id", paramMap.get("uid"));

        // 更新用户的推荐人用户uid，
        // 推荐的人：没有被推荐过的用户才可以领取【推荐用户】
        // 被推荐的人：没有被推荐过的用户才可以领取【加油红包】
        updateNum = wxUserDao.updateUserOfRecommendUid(paramMap);
        String oilStationCode = paramMap.get("oilStationCode") != null ? paramMap.get("oilStationCode").toString() : "";
        String recommendUid = paramMap.get("recommendUid") != null ? paramMap.get("recommendUid").toString() : "";
        if (updateNum != null && updateNum > 0
                && !"".equals(oilStationCode) && !"".equals(recommendUid)) {
            //新增【推荐用户】的操作，便于后期发送红包的参考
            Map<String, Object> oilStationOperator_paramMap = Maps.newHashMap();
            oilStationOperator_paramMap.put("status", "0");
            oilStationOperator_paramMap.put("uid", recommendUid);
            oilStationOperator_paramMap.put("operator", "recommendUser");
            oilStationOperator_paramMap.put("oilStationCode", oilStationCode);
            wxOilStationOperatorService.addOilStationOperator(oilStationOperator_paramMap);
            //判断当前加油站是否是【田坝加油站】，如果是则赠送【加油红包】
            if ("56454".equals(oilStationCode)) {
                oilStationOperator_paramMap.clear();
                oilStationOperator_paramMap.put("status", "0");
                oilStationOperator_paramMap.put("uid", paramMap.get("uid"));
                oilStationOperator_paramMap.put("operator", "fuelCharging");
                oilStationOperator_paramMap.put("oilStationCode", oilStationCode);
                wxOilStationOperatorService.addOilStationOperator(oilStationOperator_paramMap);
            }
        }
        //更新用户信息
        updateNum = wxUserDao.updateUser(paramMap);
        if (updateNum != null && updateNum > 0) {
            boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
            boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        } else {
            boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
            boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
        }
        logger.info("在service中更新用户-updateUser,结果-result:" + boolDTO);
        return boolDTO;
    }

    /**
     * 获取单一的用户信息
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResultDTO getSimpleUserByCondition(Map<String, Object> paramMap) {
        ResultDTO resultDTO = new ResultDTO();
        String uid = paramMap.get("uid") != null ? paramMap.get("uid").toString() : "";
        List<Map<String, Object>> userList = wxUserDao.getSimpleUserByCondition(paramMap);
        if (!"".equals(uid)) {
            if (userList != null && userList.size() > 0) {
                List<Map<String, String>> userStrList = MapUtil.getStringMapList(userList);
                Integer total = wxUserDao.getSimpleUserTotalByCondition(paramMap);
                resultDTO.setResultListTotal(total);
                resultDTO.setResultList(userStrList);

                resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                List<Map<String, String>> resultList = Lists.newArrayList();
                resultDTO.setResultListTotal(0);
                resultDTO.setResultList(resultList);

                resultDTO.setCode(OilStationMapCode.USER_IS_NULL.getNo());
                resultDTO.setMessage(OilStationMapCode.USER_IS_NULL.getMessage());
            }
        } else {

            resultDTO.setCode(OilStationMapCode.PARAM_IS_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.PARAM_IS_NULL.getMessage());
        }
        logger.info("在service中获取单一的用户信息-getSimpleUserByCondition,结果-result:" + resultDTO);
        return resultDTO;
    }

    /**
     * 获取所有单一的用户信息
     * @param paramMap
     * @return
     */
    @Override
    public ResultDTO getAllSimpleUserByCondition(Map<String, Object> paramMap) {
        ResultDTO resultDTO = new ResultDTO();
        List<Map<String, Object>> userList = wxUserDao.getSimpleUserByCondition(paramMap);
        if (userList != null && userList.size() > 0) {
            List<Map<String, String>> userStrList = MapUtil.getStringMapList(userList);
            Integer total = wxUserDao.getSimpleUserTotalByCondition(paramMap);
            resultDTO.setResultListTotal(total);
            resultDTO.setResultList(userStrList);

            resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
            resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        } else {
            List<Map<String, String>> resultList = Lists.newArrayList();
            resultDTO.setResultListTotal(0);
            resultDTO.setResultList(resultList);

            resultDTO.setCode(OilStationMapCode.USER_IS_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.USER_IS_NULL.getMessage());
        }
        logger.info("在service中获取所有单一的用户信息-getAllSimpleUserByCondition,结果-result:" + resultDTO);
        return resultDTO;
    }

    /**
     * 根据手机号校验验证码是否正确
     */
    @Override
    public MessageDTO getCheckVerificationCode(Map<String, Object> paramMap) {
        MessageDTO messageDTO = new MessageDTO();
        String userPhone = paramMap.get("userPhone") != null ? paramMap.get("userPhone").toString() : "";
        String captcha = paramMap.get("captcha") != null ? paramMap.get("captcha").toString() : "";
        //1.校验手机验证码是否正确
        if (!"".equals(userPhone) && !"".equals(captcha)) {
            try (Jedis jedis = jedisPool.getResource()) {
                String key = getKey(userPhone);
                String checkCaptcha = jedis.get(key);
                boolean result = checkCaptcha != null && captcha.trim().equals(checkCaptcha.trim());
                if (result) {
                    jedis.del(key);
                    messageDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                    messageDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
                } else {
                    messageDTO.setCode(OilStationMapCode.CARD_ERROR_PHONE_CAPTCHA.getNo());
                    messageDTO.setMessage(OilStationMapCode.CARD_ERROR_PHONE_CAPTCHA.getMessage());
                }
            } catch (Exception e) {

                messageDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                messageDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.error("在service中校验手机验证码-getCheckVerificationCode is error, paramMap : " + paramMap + ", e : " + e);
            }
        } else {

            messageDTO.setCode(OilStationMapCode.PHONE_OR_CAPTCHA_IS_NOT_NULL.getNo());
            messageDTO.setMessage(OilStationMapCode.PHONE_OR_CAPTCHA_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中校验手机验证码-getCheckVerificationCode,结果-result:" + messageDTO);
        return messageDTO;
    }

    /**
     * 根据用户uid创建其用户的小程序码
     * /opt/resourceOfOilStationMap/user/
     * @param paramMap
     * @return
     */
    @Override
    public ResultMapDTO getUserMiniProgramCode(Map<String, Object> paramMap) {
        logger.info("【service】根据用户uid创建其用户的小程序码-getUserMiniProgramCode,响应-paramMap = {}", JSONObject.toJSONString(paramMap));
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        String page = paramMap.get("page") != null ? paramMap.get("page").toString() : "";
        String recommendUid = paramMap.get("recommendUid") != null ? paramMap.get("recommendUid").toString() : "";
        if (!"".equals(recommendUid) && !"".equals(page) && !"".equals(userMiniProgramCodePath)) {
            String scene = "recommendUid=" + recommendUid;
            logger.info("整合之前 userMiniProgramCodePath = " + userMiniProgramCodePath);
            String filaPath = userMiniProgramCodePath + "uid_" + recommendUid + "_miniProgramCode.jpg";
            logger.info("整合之后 filaPath = {}" + filaPath);
            logger.info("用户uid = {}", recommendUid, " ,已创建 个人小程序码，路径 filaPath = {}", filaPath);
            //1.检测 需要创建的 个人小程序码 是否被创建过
            File userMiniProgramCodeFile = new File(filaPath);
            //2.创建 个人小程序吗
            logger.info("userMiniProgramCodeFile.exists() = " + userMiniProgramCodeFile.exists());
            logger.info("!userMiniProgramCodeFile.exists() = " + !userMiniProgramCodeFile.exists());
            if (!userMiniProgramCodeFile.exists()) {
                logger.info("个人小程序码不存在，所以我被执行了.");
                String accountId = paramMap.get("accountId")!=null?paramMap.get("accountId").toString():"";
                Map<String, Object> accountMap = wxAccountService.getWxAccount(accountId);
                String appid = accountMap.get("customMessageAccountAppId").toString();
                String secret = accountMap.get("customMessageAccountSecret").toString();
                resultMap = WX_PublicNumberUtil.getUserMiniProgramCode(
                        appid,
                        secret,
                        page,
                        scene,
                        filaPath);
            } else {
                filaPath = filaPath.substring(4);
                String miniProgramCodeUrl = OilStationMapCode.THE_DOMAIN + filaPath;
                resultMap.put("miniProgramCodeUrl", miniProgramCodeUrl);
            }
            if (resultMap != null && resultMap.size() > 0) {
                resultMapDTO.setResultMap(MapUtil.getStringMap(resultMap));
                resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            }
        } else {
            resultMapDTO.setCode(OilStationMapCode.SHOP_UID_PAGE_SCENE_FILEPATH_IS_NOT_NULL.getNo());
            resultMapDTO.setMessage(OilStationMapCode.SHOP_UID_PAGE_SCENE_FILEPATH_IS_NOT_NULL.getMessage());
        }
        logger.info("【service】根据用户uid创建其用户的小程序码-getUserMiniProgramCode,响应-resultMapDTO = {}", JSONObject.toJSONString(resultMapDTO));
        return resultMapDTO;
    }

    /**
     * 获取微信的AccessToken
     */
    @Override
    public ResultMapDTO getWxAccessToken(Map<String, Object> paramMap){
        String accountId = paramMap.get("accountId")!=null?paramMap.get("accountId").toString():"gh_417c90af3488";  //默认油价地图的 原始ID
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, Object> resultMap = Maps.newHashMap();
        Map<String, Object> customMessageAccountParamMap = Maps.newHashMap();
        customMessageAccountParamMap.put("dicType", "customMessageAccount");
        customMessageAccountParamMap.put("dicCode", accountId);
        ResultDTO customMessageAccountResultDTO = wxDicService.getSimpleDicByCondition(customMessageAccountParamMap);
        if(customMessageAccountResultDTO != null && customMessageAccountResultDTO.getResultList() != null
                && customMessageAccountResultDTO.getResultList().size() > 0){
            Map<String, String> customMessageAccountMap = customMessageAccountResultDTO.getResultList().get(0);
            String customMessageAccountName = customMessageAccountMap.get("customMessageAccountName") != null ? customMessageAccountMap.get("customMessageAccountName").toString() : "";
            String customMessageAccountAppId = customMessageAccountMap.get("customMessageAccountAppId") != null ? customMessageAccountMap.get("customMessageAccountAppId").toString() : "";
            String customMessageAccountSecret = customMessageAccountMap.get("customMessageAccountSecret") != null ? customMessageAccountMap.get("customMessageAccountSecret").toString() : "";
            resultMap = WX_PublicNumberUtil.getAccessToken(customMessageAccountAppId, customMessageAccountSecret);
            if (resultMap.get("access_token") != null &&
                    !"".equals(resultMap.get("access_token").toString())) {
                String accessToken = resultMap.get("access_token").toString();
                resultMap.put("accessToken", accessToken);
                resultMapDTO.setResultMap(MapUtil.getStringMap(resultMap));
                resultMapDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {
                resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
                logger.info("【service】获取微信的AccessToken-getWxAccessToken 获取微信access_token失败.");
            }
        }else{
            resultMapDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
            resultMapDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
        }
        return resultMapDTO;
    }

    /**
     * 整合在redis中查询的key值
     *
     * @param mobile
     * @return
     */
    private String getKey(String mobile) {
        return OilStationMapCode.REDIS_MSG_PREFIX + mobile;
    }

    /**
     * 整合在redis中查询的key值--针对用户的会话时间
     *
     * @param session
     * @return
     */
    private String getUserKey(String session) {
        return OilStationMapCode.USER_SESSION_PREFIX + session;
    }

}
