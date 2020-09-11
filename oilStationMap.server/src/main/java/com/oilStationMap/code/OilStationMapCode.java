package com.oilStationMap.code;


/**
 * 给前端的状态码
 *
 * @author caihongwang
 */
public class OilStationMapCode {

    private int no;
    private String message;

    public OilStationMapCode() {

    }

    public OilStationMapCode(int no, String message) {
        this.no = no;
        this.message = message;
    }

    public int getNo() {
        return no;
    }

    public String getMessage() {
        return message;
    }

    //system
    public static OilStationMapCode SUCCESS = new OilStationMapCode(0, "成功");
    public static OilStationMapCode SERVER_INNER_ERROR = new OilStationMapCode(10001, "服务异常,请稍后重试.");
    public static OilStationMapCode IS_USER_SESSION_OVERDUE = new OilStationMapCode(10002, "网络开小差了，请重新操作！");
    public static OilStationMapCode SESSION_KEY_IS_NOT_NULL = new OilStationMapCode(10003, "网络开小差了，请重新操作！");
    public static OilStationMapCode WX_SERVER_INNER_ERROR = new OilStationMapCode(10004, "微信服务异常，无法获取openid和session_key.");
    public static OilStationMapCode WX_SERVER_INNER_ERROR_FOR_ACCESS_TOKEN = new OilStationMapCode(10004, "微信服务异常，无法获取access_token和expires_in.");
    public static OilStationMapCode WX_PARAM_IS_NOT_NULL = new OilStationMapCode(10005, "向微信服务器访问的必要参数不允许为空.");
    public static OilStationMapCode NO_DATA_CHANGE = new OilStationMapCode(10009, "没有数据发生更改.");

    //字典
    public static OilStationMapCode DIC_EXIST = new OilStationMapCode(30001, "字典已经存在，请修改。");
    public static OilStationMapCode DIC_TYPE_OR_CODE_OR_NAME_IS_NOT_NULL = new OilStationMapCode(30002, "字典的类型或者编码或者名称不能为空");
    public static OilStationMapCode DIC_ID_OR_CODE_IS_NOT_NULL = new OilStationMapCode(30003, "字典的ID或者编码不能为空");
    public static OilStationMapCode DIC_LIST_IS_NULL = new OilStationMapCode(0, "当前字典没有数据.");

    //常量值
    public static final int MSG_EXPIRED_TIME = 60 * 2;    //短信失效时间：2分钟
    public static final String REDIS_PREFIX = "sp:";
    public static final String REDIS_MSG_PREFIX = REDIS_PREFIX + "getVerificationCode:";//财富名片夹的短信在redis中的前缀
    public static final int USER_SESSION_EXPIRED_TIME = 86400;    //session失效时间：1天
    public static final String USER_SESSION_PREFIX = "user:session:";//财富名片夹的短信在redis中的前缀
    public static final String CURRENT_LON_UID = "current:lon:uid:";  //用户当前的坐标
    public static final String CURRENT_LAT_UID = "current:lat:uid:";  //用户当前的坐标

    //申请财富名片夹--短信验证码
    public static OilStationMapCode USER_EXIST = new OilStationMapCode(0, "用户已经存在，请直接使用。");
    public static OilStationMapCode CARD_ERROR_PHONE_CAPTCHA = new OilStationMapCode(40001, "手机号或者验证码错误，请重新输入");
    public static OilStationMapCode PHONE_OR_CAPTCHA_IS_NOT_NULL = new OilStationMapCode(40002, "手机号或者验证码不能为空");
    public static OilStationMapCode CODE_IS_NOT_NULL = new OilStationMapCode(40004, "code不能为空");
    public static OilStationMapCode UID_COMMENT_IS_NOT_NULL = new OilStationMapCode(40005, "uid或者评论不能为空");
    public static OilStationMapCode PHONE_IS_NOT_NULL = new OilStationMapCode(40006, "手机号不能为空");

    //意见
    public static OilStationMapCode COMMENTS_LIST_IS_NULL = new OilStationMapCode(0, "当前用户没有反馈意见.");
    public static OilStationMapCode COMMENTS_NOT_MORE_200 = new OilStationMapCode(80001, "反馈意见不能超过200个字.");

    //公共模板
    public static OilStationMapCode PARAM_IS_NULL = new OilStationMapCode(60001, "必填参数不允许为空.");
    public static OilStationMapCode DECRYPT_IS_ERROR = new OilStationMapCode(60002, "解密手机号失败.");

    //用户
    public static OilStationMapCode USER_IS_NULL = new OilStationMapCode(70001, "用户不存在.");
    public static OilStationMapCode USER_PHONE_IS_ERROR = new OilStationMapCode(70004, "用户用户手机号错误，请重新输入.");
    public static OilStationMapCode USER_CODE_IS_NOT_NULL = new OilStationMapCode(70006, "用户微信访问的code参数不能为空.");

    //订单
    public static OilStationMapCode ORDER_RESPONSE_UNIFIEDORDER_IS_ERROR = new OilStationMapCode(120002, "微信的统一订单方式支付请求失败。");
    public static OilStationMapCode ORDER_PAY_MONEY_IS_NOT_NULL = new OilStationMapCode(120003, "支付金额不允许为空，请输入您的金额。");

    //加油站
    public static OilStationMapCode OIL_QUERY_IS_NULL = new OilStationMapCode(130002, "获取加油站信息为空。");
    public static OilStationMapCode OIL_ADDRESS_QUERY_IS_NULL = new OilStationMapCode(130005, "您所处的位置不是加油站，请打赏一点开发小哥哥吧。");
    public static OilStationMapCode OIL_STATION_EXIST_AND_UPDATE = new OilStationMapCode(130003, "加油站已存在，并更新。");
    public static OilStationMapCode OIL_STATION_PARAM_IS_NOT_NULL = new OilStationMapCode(130004, "加油站必填参数不允许为空,相关参数请查看文档。");
    public static OilStationMapCode SHOP_UID_PAGE_SCENE_FILEPATH_IS_NOT_NULL = new OilStationMapCode(210005, "用户小程序码的用户uid或者小程序页面或者小程序码存放路径不能为空.");
    public static OilStationMapCode IS_NOT_OIL_STATION_OWNER_UID = new OilStationMapCode(210006, "AAA已被认领，您的油价修改无效，请点击【我的】【在线客服】进行沟通确认.");


    //微信的form_id
    public static OilStationMapCode USER_FORM_UID_OR_FORMID_IS_NOT_NULL = new OilStationMapCode(80001, "用户id或者微信的formId不能为空");
    public static OilStationMapCode USER_FORM_ID_OR_UID_IS_NOT_NULL = new OilStationMapCode(80002, "用户id或者uid不能为空");
    public static OilStationMapCode USER_FORM_LIST_IS_NULL = new OilStationMapCode(80003, "当前用户对应的formId没有数据.");

    //红包领取
    public static OilStationMapCode WX_RED_PACKET__ACTIVITY_IS_NOT_EXIST = new OilStationMapCode(150001, "红包活动不存在");
    public static OilStationMapCode WX_RED_PACKET__SEND_SUCCESS = new OilStationMapCode(150002, "红包发送成功");
    public static OilStationMapCode WX_RED_PACKET__SEND_FAILTURE = new OilStationMapCode(150003, "红包发送失败");
    public static OilStationMapCode WX_RED_PACKET__UID_OR_MONEY_IS_NOT_NULL = new OilStationMapCode(150004, "用户UID或者红包金额不允许为空");
    public static OilStationMapCode WX_RED_PACKET__HISTORY_IS_NULL = new OilStationMapCode(150005, "当前用户没有红包领取记录");
    public static OilStationMapCode RED_PACKET_SEND_IS_ERROR = new OilStationMapCode(50020, "红包状态发送异常，请联系管理员检查微信公众号相关配置.");
    public static OilStationMapCode RED_PACKET_SEND_IS_ERROR_BUT_RESPOSE_SUCCESS = new OilStationMapCode(50020, "发送红包响应正常，但是发送失败.");

    //红包提现
    public static OilStationMapCode WX_RED_PACKET_DRAW_CASH__ACTIVITY_IS_NOT_EXIST = new OilStationMapCode(160001, "红包活动不存在");
    public static OilStationMapCode WX_RED_PACKET_DRAW_CASH__SEND_SUCCESS = new OilStationMapCode(160002, "红包发送成功");
    public static OilStationMapCode WX_RED_PACKET_DRAW_CASH__SEND_FAILTURE = new OilStationMapCode(160003, "红包发送失败");
    public static OilStationMapCode WX_RED_PACKET_DRAW_CASH__UID_OR_PHONE_OR_MONEY_IS_NOT_NULL = new OilStationMapCode(160004, "用户uid或者用户phone或者红包金额不允许为空");
    public static OilStationMapCode WX_RED_PACKET_DRAW_CASH__HISTORY_IS_NULL = new OilStationMapCode(160005, "当前用户没有红包提现记录");
    public static OilStationMapCode WX_RED_PACKET_ACTIVITY_IS_NOT_START = new OilStationMapCode(160006, "红包活动尚未开始");
    public static OilStationMapCode WX_RED_PACKET_ACTIVITY_IS_END = new OilStationMapCode(160007, "红包活动已经结束");
    public static OilStationMapCode WX_RED_PACKET_ACTIVITY_INFO_ERROR = new OilStationMapCode(160008, "红包活动出现异常，请联系客服.");

    //加油站操作
    public static OilStationMapCode OIL_STATION_OPERATOR_ID_IS_NOT_NULL = new OilStationMapCode(170001, "加油站操作ID不允许为空.");
    public static OilStationMapCode OIL_STATION_OPERATOR_UID_OILSTATIONCODE_OPERATOR_IS_NOT_NULL = new OilStationMapCode(170002, "加油站操作UID,加油站编码，操作名称不允许为空.");
    public static OilStationMapCode OIL_STATION_OPERATOR_LIST_IS_NULL = new OilStationMapCode(170003, "当前用户没有加油站操作.");
    public static OilStationMapCode OIL_STATION_OPERATOR_IS_EXIST = new OilStationMapCode(170004, "当前用户的加油站操作已存在.");
    public static OilStationMapCode OIL_STATION_OPERATOR_ID_OR_UID_OR_OPERATOR_IS_NOT_NULL = new OilStationMapCode(170005, "当前操作的id或者uid不允许为空.");
    public static OilStationMapCode OIL_STATION_OPERATOR_RED_PACKET_IS_NOT_EXIST_OR_CASHED = new OilStationMapCode(170006, "当前红包已不存在，或许已被领取.");
    public static OilStationMapCode OIL_STATION_OPERATOR_ID_OR_UID_IS_NOT_NULL_AND_REDPACKETTOTAL_SHOULD_LARGER_0 = new OilStationMapCode(170007, "uid或者openId不允许为空，同事红包金额必须大于0.");
    public static OilStationMapCode CURRENT_PUBLIC_NUMBER_OPENID_IS_NOT_NULL = new OilStationMapCode(170008, "当前公众号没有粉丝，发送消息无效.");
    public static OilStationMapCode OIL_STATION_OPERATOR_RED_PACKET_IS_NOT_CASHED_OR_3 = new OilStationMapCode(170009, "推荐用户已被领取或者不满三人.");

    //加盟
    public static OilStationMapCode LEAGUE_LIST_IS_NULL = new OilStationMapCode(0, "当前加盟没有数据.");
    public static OilStationMapCode LEAGUE_UID_OR_PHONE_OR_NAME_OR_LEAGUETYPECODE_IS_NOT_NULL = new OilStationMapCode(220001, "加盟的uid或者手机号或者姓名或者加盟类型不能为空");
    public static OilStationMapCode LEAGUE_ID_IS_NOT_NULL = new OilStationMapCode(220002, "加盟的ID不能为空");
    public static OilStationMapCode LEAGUE_TYPE_IS_NULL = new OilStationMapCode(220003, "加盟类型不允许为空.");

    //广告信息
    public static OilStationMapCode AD_INFO_EXIST = new OilStationMapCode(230001, "广告信息已经存在，请修改。");
    public static OilStationMapCode AD_TITLE_OR_IMGURL_OR_CONTENT_IS_NOT_NULL = new OilStationMapCode(230002, "广告的标题或者图片或者内容那你过不能为空");
    public static OilStationMapCode AD_ID_IS_NOT_NULL = new OilStationMapCode(230003, "广告的ID不能为空");
    public static OilStationMapCode AD_LIST_IS_NULL = new OilStationMapCode(0, "当前广告没有数据.");


    //微信小程序
    public static final String WX_MINI_PROGRAM_NAME = "油价地图";     //小程序名称
    public static final String WX_MINI_PROGRAM_GH_ID = "gh_417c90af3488";     //小程序原始ID
    public static final String WX_MINI_PROGRAM_APPID = "wx07cf52be1444e4b7";     //appid
    public static final String WX_MINI_PROGRAM_SECRET = "d6de12032cfe660253b96d5f2868a06c";    //secret
    public static final String WX_MINI_PROGRAM_GRANT_TYPE_FOR_OPENID = "authorization_code";    //grant_type
    public static final String WX_MINI_PROGRAM_GRANT_TYPE_FOR_ACCESS_TOKEN = "client_credential";    //grant_type

    //本服务器域名
    public static final String THE_DOMAIN = "https://www.91caihongwang.com/";        // 域名  线上

    //微信公众号
    public static final String WX_PUBLIC_NUMBER_APPID = "wxf768b49ad0a4630c";                   //appid
    public static final String WX_PUBLIC_NUMBER_SECRET = "a481dd6bc40c9eec3e57293222e8246f";    //secret

    //微信自定义信息发送，适用于小程序员和公众号
    public static final String WX_CUSTOM_MESSAGE_HOST = "https://api.weixin.qq.com";     //host
    public static final String WX_CUSTOM_MESSAGE_PATH = "/cgi-bin/message/custom/send?access_token=";     //path
    public static final String WX_CUSTOM_MESSAGE_METHOD = "POST";                        //method

    //微信支付
    public static final String WX_PAY_API_SECRET = "Caihongwang52013Caihongwang52014";     //微信支付 api secret
    public static final String WX_PAY_MCH_ID = "1345780701";     //微信支付 商家ID
    public static final String WX_PAY_CERT_PATH = "/Users/caihongwang/Desktop/cert/apiclient_cert.p12";     //微信支付 商家证书
//    public static final String WX_PAY_DOMAIN = "http://172.30.5.91:8080/oilStationMap";        //微信支付 域名  本机
    public static final String WX_PAY_DOMAIN = "https://www.91caihongwang.com/oilStationMap";        //微信支付 域名  线上
    public static final String WX_RED_PACK_NUMBER = "1";     //微信红包总数：1个
    public static final String WX_PAY_NOTIFY_URL = "/oilStationMap/wxOrder/wxNotify";  //支付成功后的服务器回调url
    public static final String WX_PAY_TRADE_TYPE = "JSAPI";  //交易类型


    //阿里常量值
    public static final String A_LI_YUN_HOST = "http://oil.market.alicloudapi.com";     //host
    public static final String A_LI_YUN_PATH = "/oil/local";                          //经纬度path
    public static final String A_LI_YUN_PATH_BY_LONLAT = "/oil/local";                          //经纬度path
    public static final String A_LI_YUN_PATH_BY_CITY = "/oil/region";                            //城市path
    public static final String A_LI_YUN_METHOD = "POST";                                //method
    public static final String A_LI_YUN_APP_CODE = "d725e59af6564dd89f0e6fede24c2427";     //app code
    public static final String A_LI_YUN_CONTENT_TYPE = "application/json; charset=UTF-8";     //Content-Type

    //腾讯常量值   绑定账号QQ:976499921,手机号：17701359899
    public static final String TENCENT_HOST = "https://apis.map.qq.com";     //host
    public static final String TENCENT_PATH_GET_ADDR = "/ws/geocoder/v1/";          //逆地址解析(坐标位置描述)
    public static final String TENCENT_PATH_GET_SEARCH = "/ws/place/v1/search";     //地点搜索
    public static final String TENCENT_METHOD = "GET";                                //method
    public static final String TENCENT_CONTENT_TYPE = "application/json; charset=UTF-8";     //Content-Type
    public static final String TENCENT_KEY = "Z73BZ-ISKC4-WMDU3-DO2UD-KCPIZ-JIFQI";     //key
    public static final String TENCENT_KEY_WORD_SEARCH = "http://apis.map.qq.com/ws/place/v1/suggestion/";     //关键词查找

    public static final String TENCENT_APP_ID = "11641";     //id
    public static final String TENCENT_SECRET_KEY = "g2RT4RVE3hy2YEYfWqYN3wWFys6";     //key
    public static final String TENCENT_URL = "https://openai.qq.com/api/json/ai/GetMultiAI";     //host
    public static final String TENCENT_CvRectDetect = "CvRectDetect";     //区域检测

    //百度常量值
    public static final String BAIDU_AK = "kuElvPX0dtH4CXPh2SZtXUxpnjWtuizH";
    public static final String BAIDU_HOST = "http://api.map.baidu.com";
    public static final String BAIDU_PATH_GET_SEARCH = "/place/v2/search";
    public static final String BAIDU_METHOD = "GET";                                //method


}