package com.automation.utils.wei_xin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 微信自动化操作 工具
 */
public class WeiXinUtil {

    public static final Logger logger = LoggerFactory.getLogger(WeiXinUtil.class);

    /**
     * 判断 nickNameStr 是否为群名或者好友昵称
     * @param deviceNameDesc
     * @param deviceName
     * @param nickNameStr
     * @return
     */
    public static boolean isChatGroupOrChatNick(String deviceNameDesc, String deviceName, String nickNameStr) {
        boolean isChatGroupOrChatNickFlag = true;

        String regex = ".*\\[[0-9]*条\\].*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(nickNameStr);
        if (m.matches()) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【[122条]】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.contains("油站科技")) {      //确保坐标：微信( 存在
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【油站科技】对应的是【自己人】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
//        if (nickNameStr.endsWith("群")) {
//            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】末尾包含【群】对应的是【微信群昵称】,继续下一个昵称....");
//            isChatGroupOrChatNickFlag = false;
//        }
        if (nickNameStr.contains("[店员消息]")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【[店员消息]】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.contains("[链接]")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【[链接]】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.contains("[图片]")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【[图片]】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.contains("[文件]")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【[文件]】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.contains("[视频]")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【[视频]】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.contains("[小程序]")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【[小程序]】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.contains("[群待办]")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【[群待办]】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.contains("[聊天记录]")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【[聊天记录]】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.contains("[语音]")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【[语音]】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.contains("[语音通话]")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【[语音通话]】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.contains("[视频通话]")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【[视频通话]】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.contains("[草稿]")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【[草稿]】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.contains("[动画表情]")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【[动画表情]】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.contains("[有人@我]")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【[有人@我]】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.contains("[应用消息]")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【[应用消息]】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.contains("[微信红包]")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【[微信红包]】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.contains("我通过了你的朋友验证请求")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【我通过了你的朋友验证请求】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.contains("与群里其他人都不是")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【与群里其他人都不是微信朋友关系】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.contains("对方为企业微信用户")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【对方为企业微信用户】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.startsWith("你已添加了")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【你已添加了*】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.startsWith("移除群里")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【*移除群里】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.startsWith("邀请确认")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【邀请确认】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.startsWith("撤回")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【撤回】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.startsWith("微信运动")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【微信运动】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        if (nickNameStr.startsWith("订阅号消息")) {
            logger.info("【将群保存到通讯录】设备描述【" + deviceNameDesc + "】设备编码【" + deviceName + "】当前昵称【" + nickNameStr + "】包含【订阅号消息】对应的是【微信群的聊天记录】,继续下一个昵称....");
            isChatGroupOrChatNickFlag = false;
        }
        return isChatGroupOrChatNickFlag;
    }
}
