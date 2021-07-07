package com.automation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.automation.service.Automation_DicService;
import com.automation.utils.EmojiUtil;
import com.automation.utils.TimestampUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.automation.code.Automation_Code;
import com.automation.dao.Automation_DicDao;
import com.automation.dto.BoolDTO;
import com.automation.dto.ResultDTO;
import com.automation.dto.ResultMapDTO;
import com.automation.utils.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 字典service
 */
@Service
public class Automation_DicServiceImpl implements Automation_DicService {

    private static final Logger logger = LoggerFactory.getLogger(Automation_DicServiceImpl.class);

    @Autowired
    private Automation_DicDao automation_DicDao;

    /**
     * 添加字典
     *
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO addDic(Map<String, Object> paramMap) {
        Integer addNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String dicType = paramMap.get("dicType") != null ? paramMap.get("dicType").toString() : "";
        String dicCode = paramMap.get("dicCode") != null ? paramMap.get("dicCode").toString() : "";
        String dicName = paramMap.get("dicName") != null ? paramMap.get("dicName").toString() : "";
        if (!"".equals(dicType) && !"".equals(dicCode) && !"".equals(dicName)) {
            Map<String, Object> paramMap_temp = Maps.newHashMap();
            paramMap_temp.put("dicType", dicType);
            paramMap_temp.put("dicCode", dicCode);
            paramMap_temp.put("dicStatus", "0");
            Integer total = automation_DicDao.getSimpleDicTotalByCondition(paramMap_temp);
            if (total != null && total <= 0) {
                paramMap.put("createTime", TimestampUtil.getTimestamp());
                paramMap.put("updateTime", TimestampUtil.getTimestamp());
                addNum = automation_DicDao.addDic(paramMap);
                if (addNum != null && addNum > 0) {
                    boolDTO.setCode(Automation_Code.SUCCESS.getNo());
                    boolDTO.setMessage(Automation_Code.SUCCESS.getMessage());
                } else {
                    boolDTO.setCode(Automation_Code.NO_DATA_CHANGE.getNo());
                    boolDTO.setMessage(Automation_Code.NO_DATA_CHANGE.getMessage());
                }
            } else {
                boolDTO.setCode(Automation_Code.DIC_EXIST.getNo());
                boolDTO.setMessage(Automation_Code.DIC_EXIST.getMessage());
            }
        } else {
            boolDTO.setCode(Automation_Code.DIC_TYPE_OR_CODE_OR_NAME_IS_NOT_NULL.getNo());
            boolDTO.setMessage(Automation_Code.DIC_TYPE_OR_CODE_OR_NAME_IS_NOT_NULL.getMessage());
        }
        logger.info("【service】【添加字典】，结果-result:" + boolDTO);
        return boolDTO;
    }

    /**
     * 删除字典
     *
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO deleteDic(Map<String, Object> paramMap) {
        Integer deleteNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String id = paramMap.get("id") != null ? paramMap.get("id").toString() : "";
        String dicCode = paramMap.get("dicCode") != null ? paramMap.get("dicCode").toString() : "";
        if (!"".equals(id) || !"".equals(dicCode)) {
            deleteNum = automation_DicDao.deleteDic(paramMap);
            if (deleteNum != null && deleteNum > 0) {

                boolDTO.setCode(Automation_Code.SUCCESS.getNo());
                boolDTO.setMessage(Automation_Code.SUCCESS.getMessage());
            } else {

                boolDTO.setCode(Automation_Code.NO_DATA_CHANGE.getNo());
                boolDTO.setMessage(Automation_Code.NO_DATA_CHANGE.getMessage());
            }
        } else {

            boolDTO.setCode(Automation_Code.DIC_ID_OR_CODE_IS_NOT_NULL.getNo());
            boolDTO.setMessage(Automation_Code.DIC_ID_OR_CODE_IS_NOT_NULL.getMessage());
        }
        logger.info("【service】【删除字典】，结果-result:" + boolDTO);
        return boolDTO;
    }

    /**
     * 更新字典
     *
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO updateDic(Map<String, Object> paramMap) {
        Integer updateNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String id = paramMap.get("id") != null ? paramMap.get("id").toString() : "";
        String dicCode = paramMap.get("dicCode") != null ? paramMap.get("dicCode").toString() : "";
        if (!"".equals(id) || !"".equals(dicCode)) {
            updateNum = automation_DicDao.updateDic(paramMap);
            if (updateNum != null && updateNum > 0) {

                boolDTO.setCode(Automation_Code.SUCCESS.getNo());
                boolDTO.setMessage(Automation_Code.SUCCESS.getMessage());
            } else {

                boolDTO.setCode(Automation_Code.NO_DATA_CHANGE.getNo());
                boolDTO.setMessage(Automation_Code.NO_DATA_CHANGE.getMessage());
            }
        } else {

            boolDTO.setCode(Automation_Code.DIC_ID_OR_CODE_IS_NOT_NULL.getNo());
            boolDTO.setMessage(Automation_Code.DIC_ID_OR_CODE_IS_NOT_NULL.getMessage());
        }
        logger.info("【service】【更新字典】，结果-result:" + boolDTO);
        return boolDTO;
    }

    /**
     * 获取单一的字典信息
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResultDTO getSimpleDicByCondition(Map<String, Object> paramMap) {
        ResultDTO resultDTO = new ResultDTO();
        List<Map<String, String>> dicStrList = Lists.newArrayList();
        String dicType = paramMap.get("dicType") != null ? paramMap.get("dicType").toString() : "";
        String dicCode = paramMap.get("dicCode") != null ? paramMap.get("dicCode").toString() : "";
        if (!"".equals(dicType) || !"".equals(dicCode)) {
            List<Map<String, Object>> dicList = automation_DicDao.getSimpleDicByCondition(paramMap);
            if (dicList != null && dicList.size() > 0) {
                for (Map<String, Object> dicMap : dicList) {
                    String dicRemark = dicMap.get("dicRemark") != null ? dicMap.get("dicRemark").toString() : "";
                    if (!"".equals(dicRemark)) {
                        Map<String, Object> dicRemarkMap = JSONObject.parseObject(dicRemark, Map.class);
                        dicMap.remove("dicRemark");
                        dicMap.putAll(dicRemarkMap);
                    }
                }
                dicStrList = MapUtil.getStringMapList(dicList);
                Integer total = automation_DicDao.getSimpleDicTotalByCondition(paramMap);
                resultDTO.setResultListTotal(total);
                resultDTO.setResultList(dicStrList);
                resultDTO.setCode(Automation_Code.SUCCESS.getNo());
                resultDTO.setMessage(Automation_Code.SUCCESS.getMessage());
            } else {
                List<Map<String, String>> resultList = Lists.newArrayList();
                resultDTO.setResultListTotal(0);
                resultDTO.setResultList(resultList);
                resultDTO.setCode(Automation_Code.DIC_LIST_IS_NULL.getNo());
                resultDTO.setMessage(Automation_Code.DIC_LIST_IS_NULL.getMessage());
            }
        } else {
            resultDTO.setCode(Automation_Code.PARAM_IS_NULL.getNo());
            resultDTO.setMessage(Automation_Code.PARAM_IS_NULL.getMessage());
        }
        logger.info("【service】【获取单一的字典信息】，结果-result:" + resultDTO);
        return resultDTO;
    }

    /**
     * 获取单一的字典信息(支持同时查询多个字典)
     * dicTypes可以传送多个参数，使用英文逗号(,)分隔
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResultMapDTO getMoreDicByCondition(Map<String, Object> paramMap) {
        Integer total = 0;
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        Map<String, String> resultMap = Maps.newHashMap();
        List<Map<String, String>> dicStrList = Lists.newArrayList();
        String dicTypeStr = paramMap.get("dicTypes") != null ? paramMap.get("dicTypes").toString() : "";
        if (!"".equals(dicTypeStr)) {
            if (dicTypeStr.contains(",")) {          //同事查询多个字典
                String[] dicTypeArr = dicTypeStr.split(",");
                for (String dicType : dicTypeArr) {
                    paramMap.put("dicType", dicType);
                    List<Map<String, Object>> dicList = automation_DicDao.getSimpleDicByCondition(paramMap);
                    if (dicList != null && dicList.size() > 0) {
                        for (Map<String, Object> dicMap : dicList) {
                            String dicRemark = dicMap.get("dicRemark") != null ? dicMap.get("dicRemark").toString() : "";
                            if (!"".equals(dicRemark)) {
                                Map<String, Object> dicRemarkMap = JSONObject.parseObject(dicRemark, Map.class);
                                dicMap.remove("dicRemark");
                                dicMap.putAll(dicRemarkMap);
                            }
                        }
                        resultMap.put(dicType, JSONObject.toJSONString(dicList));
                        total += automation_DicDao.getSimpleDicTotalByCondition(paramMap);
                    } else {
                        dicList = Lists.newArrayList();
                        resultMap.put("dicType", JSONObject.toJSONString(dicList));
                    }

                }
                resultMapDTO.setResultListTotal(total);
                resultMapDTO.setResultMap(resultMap);

                resultMapDTO.setCode(Automation_Code.SUCCESS.getNo());
                resultMapDTO.setMessage(Automation_Code.SUCCESS.getMessage());
            }
        } else {

            resultMapDTO.setCode(Automation_Code.PARAM_IS_NULL.getNo());
            resultMapDTO.setMessage(Automation_Code.PARAM_IS_NULL.getMessage());
        }
        logger.info("【service】【获取多个的字典信息】，结果-result:" + resultMapDTO);
        return resultMapDTO;
    }


    /**
     * 获取单一的字典列表For管理中心
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResultDTO getDicListByConditionForAdmin(Map<String, Object> paramMap) {
        ResultDTO resultDTO = new ResultDTO();
        List<Map<String, String>> dicStrList = Lists.newArrayList();
        List<Map<String, Object>> dicList = automation_DicDao.getDicListByConditionForAdmin(paramMap);
        if (dicList != null && dicList.size() > 0) {
//            for (Map<String, Object> dicMap : dicList) {
//                String dicRemark = dicMap.get("dicRemark") != null ? dicMap.get("dicRemark").toString() : "";
//                if (!"".equals(dicRemark)) {
//                    Map<String, Object> dicRemarkMap = JSONObject.parseObject(dicRemark, Map.class);
//                    dicMap.remove("dicRemark");
//                    dicMap.putAll(dicRemarkMap);
//                }
//            }
            dicStrList = MapUtil.getStringMapList(dicList);
            Integer total = automation_DicDao.getDicListTotalByConditionForAdmin(paramMap);
            resultDTO.setResultListTotal(total);
            resultDTO.setResultList(dicStrList);
            resultDTO.setCode(Automation_Code.SUCCESS.getNo());
            resultDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        } else {
            List<Map<String, String>> resultList = Lists.newArrayList();
            resultDTO.setResultListTotal(0);
            resultDTO.setResultList(resultList);
            resultDTO.setCode(Automation_Code.DIC_LIST_IS_NULL.getNo());
            resultDTO.setMessage(Automation_Code.DIC_LIST_IS_NULL.getMessage());
        }
        logger.info("【service】【获取单一的字典列表For管理中心】，结果-result:" + resultDTO);
        return resultDTO;
    }


    /**
     * 获取当前设备可以添加群成员为好友的群列表For管理中心
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResultMapDTO getGroupNickNameListByDeviceNameDescForAdmin(Map<String, Object> paramMap) {
        ResultMapDTO resultMapDTO = new ResultMapDTO();
        String dicCode = paramMap.get("deviceNameDesc") != null ? paramMap.get("deviceNameDesc").toString() : "";
        //1.获取当前设备可以添加群成员为好友的群列表
        paramMap.clear();
        paramMap.put("dicCode", dicCode);
        paramMap.put("dicType", "deviceNameDescToGroupNameMapStr");
        List<Map<String, Object>> dicList = automation_DicDao.getDicListByConditionForAdmin(paramMap);
        if (dicList != null && dicList.size() > 0) {
            for (Map<String, Object> dicMap : dicList) {
                String dicRemark = dicMap.get("dicRemark") != null ? dicMap.get("dicRemark").toString() : "";
                if (!"".equals(dicRemark)) {
                    Map<String, Object> dicRemarkMap = JSONObject.parseObject(dicRemark, Map.class);
                    dicMap.remove("dicRemark");
                    dicMap.putAll(dicRemarkMap);
                }
            }
            String groupNickNameMapStr = dicList.get(0).get("groupNickNameMapStr").toString();
            Map<String, String> groupNickNameMapOfDevice = JSON.parseObject(groupNickNameMapStr, Map.class);

            //2.获取所有的添加群成员为好友的群列表，并整理
            List<String> allUsedGroupNickNameList = automation_DicDao.getAllUsedGroupNickNameList(paramMap);

            //3.筛选出可以添加群成员为好友的群信息Map
            Map<String, String> groupNickNameMap = Maps.newHashMap();
            for (Map.Entry<String, String> entry : groupNickNameMapOfDevice.entrySet()) {
                try {
                    String groupNickName = EmojiUtil.emojiRecovery(entry.getKey());
                    String groupNum = entry.getValue();
                    //判断该群是否曾经被使用过
                    boolean isAddFlag = true;
                    for(String usedGroupNickName : allUsedGroupNickNameList){
                        try {
                            usedGroupNickName = EmojiUtil.emojiRecovery(usedGroupNickName);
                            if(usedGroupNickName.contains(groupNickName)){
                                isAddFlag = false;
                                break;
                            }
                        } catch (Exception e) {

                        }
                    }
                    if(isAddFlag){
                        groupNickNameMap.put(groupNickName, groupNum);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (Map.Entry<String, String> entry : groupNickNameMap.entrySet()) {
                System.out.println(entry.getKey() + "-------->>>>>" + entry.getValue());
            }
            resultMapDTO.setResultListTotal(groupNickNameMap.size());
            resultMapDTO.setResultMap(groupNickNameMap);
            resultMapDTO.setCode(Automation_Code.SUCCESS.getNo());
            resultMapDTO.setMessage(Automation_Code.SUCCESS.getMessage());
        } else {
            resultMapDTO.setCode(Automation_Code.DIC_LIST_IS_NULL.getNo());
            resultMapDTO.setMessage(Automation_Code.DIC_LIST_IS_NULL.getMessage());
        }
        logger.info("【service】【活当前设备可以添加群成员为好友的群列表For管理中心】，结果-result:" + resultMapDTO);
        return resultMapDTO;
    }

}
