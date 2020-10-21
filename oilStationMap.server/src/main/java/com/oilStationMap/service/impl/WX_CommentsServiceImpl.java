package com.oilStationMap.service.impl;

import com.oilStationMap.code.OilStationMapCode;
import com.oilStationMap.dto.BoolDTO;
import com.oilStationMap.dto.ResultDTO;
import com.oilStationMap.service.WX_CommentsService;
import com.oilStationMap.utils.MapUtil;
import com.oilStationMap.dao.WX_CommentsDao;
import com.oilStationMap.dao.WX_UserDao;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 意见service
 */
@Service
public class WX_CommentsServiceImpl implements WX_CommentsService {

    private static final Logger logger = LoggerFactory.getLogger(WX_CommentsServiceImpl.class);

    @Autowired
    private WX_CommentsDao wxCommentsDao;

    @Autowired
    private WX_UserDao wxUserDao;

    /**
     * 添加意见
     *
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO addComments(Map<String, Object> paramMap) {
        Integer addNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String comments = paramMap.get("comments") != null ? paramMap.get("comments").toString() : "";
        String uid = paramMap.get("uid") != null ? paramMap.get("uid").toString() : "";
        if (!"".equals(comments) && !"".equals(uid)) {
            if (comments.length() <= 200) {
                Map<String, Object> paramMap_temp = Maps.newHashMap();
                paramMap_temp.put("id", uid);
                List<Map<String, Object>> userList = wxUserDao.getSimpleUserByCondition(paramMap_temp);
                if (userList != null && userList.size() > 0) {
                    paramMap.put("status", 0);
                    addNum = wxCommentsDao.addComments(paramMap);
                    if (addNum != null && addNum > 0) {

                        boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                        boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
                    } else {

                        boolDTO.setCode(OilStationMapCode.NO_DATA_CHANGE.getNo());
                        boolDTO.setMessage(OilStationMapCode.NO_DATA_CHANGE.getMessage());
                    }
                } else {

                    boolDTO.setCode(OilStationMapCode.USER_IS_NULL.getNo());
                    boolDTO.setMessage(OilStationMapCode.USER_IS_NULL.getMessage());
                }
            } else {

                boolDTO.setCode(OilStationMapCode.COMMENTS_NOT_MORE_200.getNo());
                boolDTO.setMessage(OilStationMapCode.COMMENTS_NOT_MORE_200.getMessage());
            }
        } else {

            boolDTO.setCode(OilStationMapCode.UID_COMMENT_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.UID_COMMENT_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中添加意见-addComments,结果-result:" + boolDTO);
        return boolDTO;
    }

    /**
     * 删除意见
     *
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO deleteComments(Map<String, Object> paramMap) {
        Integer deleteNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String id = paramMap.get("id") != null ? paramMap.get("id").toString() : "";
        if (!"".equals(id)) {
            deleteNum = wxCommentsDao.deleteComments(paramMap);
            if (deleteNum != null && deleteNum > 0) {

                boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {

                boolDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                boolDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            }
        } else {

            boolDTO.setCode(OilStationMapCode.PHONE_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.PHONE_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中删除意见-deleteComments,结果-result:" + boolDTO);
        return boolDTO;
    }

    /**
     * 更新意见
     *
     * @param paramMap
     * @return
     */
    @Override
    public BoolDTO updateComments(Map<String, Object> paramMap) {
        Integer updateNum = 0;
        BoolDTO boolDTO = new BoolDTO();
        String id = paramMap.get("id") != null ? paramMap.get("id").toString() : "";
        if (!"".equals(id)) {
            updateNum = wxCommentsDao.updateComments(paramMap);
            if (updateNum != null && updateNum > 0) {

                boolDTO.setCode(OilStationMapCode.SUCCESS.getNo());
                boolDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
            } else {

                boolDTO.setCode(OilStationMapCode.SERVER_INNER_ERROR.getNo());
                boolDTO.setMessage(OilStationMapCode.SERVER_INNER_ERROR.getMessage());
            }
        } else {

            boolDTO.setCode(OilStationMapCode.UID_COMMENT_IS_NOT_NULL.getNo());
            boolDTO.setMessage(OilStationMapCode.UID_COMMENT_IS_NOT_NULL.getMessage());
        }
        logger.info("在service中更新意见-updateComments,结果-result:" + boolDTO);
        return boolDTO;
    }


    /**
     * 获取单一的意见信息
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResultDTO getSimpleCommentsByCondition(Map<String, Object> paramMap) {
        ResultDTO resultDTO = new ResultDTO();
        List<Map<String, String>> commentsStrList = Lists.newArrayList();
        List<Map<String, Object>> commentsList = wxCommentsDao.getSimpleCommentsByCondition(paramMap);
        if (commentsList != null && commentsList.size() > 0) {
            commentsStrList = MapUtil.getStringMapList(commentsList);
            Integer total = wxCommentsDao.getSimpleCommentsTotalByCondition(paramMap);
            resultDTO.setResultListTotal(total);
            resultDTO.setResultList(commentsStrList);

            resultDTO.setCode(OilStationMapCode.SUCCESS.getNo());
            resultDTO.setMessage(OilStationMapCode.SUCCESS.getMessage());
        } else {
            List<Map<String, String>> resultList = Lists.newArrayList();
            resultDTO.setResultListTotal(0);
            resultDTO.setResultList(resultList);

            resultDTO.setCode(OilStationMapCode.COMMENTS_LIST_IS_NULL.getNo());
            resultDTO.setMessage(OilStationMapCode.COMMENTS_LIST_IS_NULL.getMessage());
        }
        logger.info("在service中获取单一的意见信息-getSimpleCommentsByCondition,结果-result:" + resultDTO);
        return resultDTO;
    }

}
