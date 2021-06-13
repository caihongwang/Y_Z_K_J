$(function() {
    //点击 For 默认：阅读微信文章并点击广告 或者 同意好友请求 或者 将群保存到通讯录 或者 同意进群 或者 转发微信消息
    $("#clickArticleAd").on('click', clickFunc_For_default);
    $("#agreeToFriendRequest").on('click', clickFunc_For_default);
    $("#saveToAddressBook").on('click', clickFunc_For_default);
    $("#agreeToJoinTheGroup").on('click', clickFunc_For_default);
    $("#relayTheWxMessage").on('click', clickFunc_For_default);
    //表单提交 For 默认：阅读微信文章并点击广告 或者 同意好友请求 或者 将群保存到通讯录 或者 同意进群 或者 转发微信消息
    var automationOperationModalValidate = $("#automationOperationModal .form").validate({
        errorElement : 'span',
        errorClass : 'help-block',
        focusInvalid : true,
        highlight : function(element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        success : function(label) {
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        },
        errorPlacement : function(error, element) {
            element.parent('div').append(error);
        },
        submitHandler : function(form) {
            var automationOperation_url = $("#automationOperationModal .form input[name='automationOperation_url']").val();
            console.log("automationOperation_url = " + automationOperation_url);
            console.log($("#automationOperationModal .form").serializeArray());
            $.post(
                automationOperation_url,
                $("#automationOperationModal .form").serialize(),
                function(data, status) {
                    if (data.code == "0") {
                        $('#automationOperationModal').modal('hide');
                        layer.open({
                            title: I18n.system_tips ,
                            btn: [ I18n.system_ok ],
                            content: (data.message || I18n.system_opt_suc ),
                            icon: '1'
                        });
                    } else {
                        layer.open({
                            title: I18n.system_tips ,
                            btn: [ I18n.system_ok ],
                            content: (data.message || I18n.system_opt_fail ),
                            icon: '2'
                        });
                    }
                }
            );
        }
    });


    //点击 For 发送朋友圈 或者 根据微信昵称进行聊天 或者 分享微信文章到微信朋友圈 或者 添加群成员为好友的V群
    $("#chatByNickName").on('click', clickFunc_For_sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends);
    $("#sendFriendCircle").on('click', clickFunc_For_sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends);
    $("#addGroupMembersAsFriends").on('click', clickFunc_For_sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends);
    $("#shareArticleToFriendCircle").on('click', clickFunc_For_sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends);
    //表单提交 For 发送朋友圈 或者 根据微信昵称进行聊天 或者 分享微信文章到微信朋友圈 或者 添加群成员为好友的V群
    var sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_ModalValidate = $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form").validate({
        errorElement : 'span',
        errorClass : 'help-block',
        focusInvalid : true,
        highlight : function(element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        success : function(label) {
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        },
        errorPlacement : function(error, element) {
            element.parent('div').append(error);
        },
        submitHandler : function(form) {
            var automationOperation_url = $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form input[name='automationOperation_url']").val();
            console.log("automationOperation_url = " + automationOperation_url);
            console.log($("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form").serializeArray());
            $.post(
                automationOperation_url,
                $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form").serialize(),
                function(data, status) {
                    if (data.code == "0") {
                        $('#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal').modal('hide');
                        layer.open({
                            title: I18n.system_tips ,
                            btn: [ I18n.system_ok ],
                            content: (data.message || I18n.system_opt_suc ),
                            icon: '1'
                        });
                    } else {
                        layer.open({
                            title: I18n.system_tips ,
                            btn: [ I18n.system_ok ],
                            content: (data.message || I18n.system_opt_fail ),
                            icon: '2'
                        });
                    }
                }
            );
        }
    });


    //点击 For 点赞和评论朋友圈
    $("#praiseAndCommentFriendsCircle").on('click', clickFunc_For_praiseAndCommentFriendsCircle);
    //表单提交 For 点赞和评论朋友圈
    var praiseAndCommentFriendsCircleModalValidate = $("#praiseAndCommentFriendsCircleModal .form").validate({
        errorElement : 'span',
        errorClass : 'help-block',
        focusInvalid : true,
        highlight : function(element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        success : function(label) {
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        },
        errorPlacement : function(error, element) {
            element.parent('div').append(error);
        },
        submitHandler : function(form) {
            var automationOperation_url = $("#praiseAndCommentFriendsCircleModal .form input[name='automationOperation_url']").val();
            console.log("automationOperation_url = " + automationOperation_url);
            console.log($("#praiseAndCommentFriendsCircleModal .form").serializeArray());
            $.post(
                automationOperation_url,
                $("#praiseAndCommentFriendsCircleModal .form").serialize(),
                function(data, status) {
                    if (data.code == "0") {
                        $('#praiseAndCommentFriendsCircleModal').modal('hide');
                        layer.open({
                            title: I18n.system_tips ,
                            btn: [ I18n.system_ok ],
                            content: (data.message || I18n.system_opt_suc ),
                            icon: '1'
                        });
                    } else {
                        layer.open({
                            title: I18n.system_tips ,
                            btn: [ I18n.system_ok ],
                            content: (data.message || I18n.system_opt_fail ),
                            icon: '2'
                        });
                    }
                }
            );
        }
    });
});

/**
 * 点击 For 默认：阅读微信文章并点击广告 或者 同意好友请求 或者 将群保存到通讯录 或者 同意进群 或者 转发微信消息
 */
function clickFunc_For_default(){
    console.log(this);
    console.log($(this).find('span.info-box-number').html() + "\t" + $(this).attr('id'));
    //变更 modal-title
    var operationId = $(this).attr('id');
    var operationName = $(this).find('span.info-box-number').html();
    var operationTitleArr = $("#automationOperationModal h4[class='modal-title']").html().split("-");
    var operationTitle = operationName + ' - ' + operationTitleArr[1];
    $("#automationOperationModal h4[class='modal-title']").html(operationTitle);
    //设置默认参数
    var automationOperation_name = "转发微信消息";
    var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/relayTheWxMessage";     //转发微信消息

    if(operationId == "relayTheWxMessage"){
        var automationOperation_name = "转发微信消息";
        var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/relayTheWxMessage";     //转发微信消息
    } else if(operationId == "agreeToJoinTheGroup"){
        var automationOperation_name = "同意进群";
        var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/agreeToJoinTheGroup";   //同意进群
    } else if(operationId == "saveToAddressBook"){
        var automationOperation_name = "将群保存到通讯录";
        var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/saveToAddressBook";     //将群保存到通讯录
    } else if(operationId == "agreeToFriendRequest"){
        var automationOperation_name = "同意好友请求";
        var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/agreeToFriendRequest";  //同意好友请求
    } else if(operationId == "clickArticleAd"){
        var automationOperation_name = "阅读微信文章并点击广告";
        var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/clickArticleAd";        //阅读微信文章并点击广告
    }
    var currentDeviceListStr = "[\n" +
        "    \"小米Max3_10\",\n" +
        "    \"华为Mate8_11\",\n" +
        "    \"华为Mate8_12\",\n" +
        "    \"华为Mate8_13\",\n" +
        "    \"华为Mate8_14\",\n" +
        "    \"华为Mate8_15\",\n" +
        "    \"华为Mate8_16\",\n" +
        "    \"华为Mate8_17\",\n" +
        "    \"华为Mate8海外版_18\",\n" +
        "    \"华为Mate8_19\",\n" +
        "    \"华为Mate8_20\",\n" +
        "    \"华为Mate8_21\"\n" +
        "]";
    $("#automationOperationModal .form input[name='automationOperation_name']").val( automationOperation_name );  //操作名称
    $("#automationOperationModal .form input[name='automationOperation_url']").val( automationOperation_url );    //操作地址
    $("#automationOperationModal .form textarea[name='currentDeviceListStr']").val( JSON.stringify(JSON.parse(currentDeviceListStr),null,2) );//操作时间列表
    $('#automationOperationModal').modal({backdrop: false, keyboard: false}).modal('show');
}

/**
 * 点击 For 点赞和评论朋友圈
 */
function clickFunc_For_praiseAndCommentFriendsCircle(){
    console.log(this);
    console.log($(this).find('span.info-box-number').html() + "\t" + $(this).attr('id'));
    //变更 modal-title
    var operationId = $(this).attr('id');
    var operationName = $(this).find('span.info-box-number').html();
    var operationTitleArr = $("#praiseAndCommentFriendsCircleModal h4[class='modal-title']").html().split("-");
    var operationTitle = operationName + ' - ' + operationTitleArr[1];
    $("#praiseAndCommentFriendsCircleModal h4[class='modal-title']").html(operationTitle);
    //设置默认参数
    var automationOperation_name = "点赞和评论朋友圈";
    var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/praiseAndCommentFriendsCircle";
    var nickName = "所有";
    var allSwipeNum = "11";
    var commentContent = "看着好高级啊，真棒...";
    var currentDeviceListStr = "[\n" +
        "    \"小米Max3_10\",\n" +
        "    \"华为Mate8_11\",\n" +
        "    \"华为Mate8_12\",\n" +
        "    \"华为Mate8_13\",\n" +
        "    \"华为Mate8_14\",\n" +
        "    \"华为Mate8_15\",\n" +
        "    \"华为Mate8_16\",\n" +
        "    \"华为Mate8_17\",\n" +
        "    \"华为Mate8海外版_18\",\n" +
        "    \"华为Mate8_19\",\n" +
        "    \"华为Mate8_20\",\n" +
        "    \"华为Mate8_21\"\n" +
        "]";
    $("#praiseAndCommentFriendsCircleModal .form input[name='automationOperation_name']").val( automationOperation_name );  //操作名称
    $("#praiseAndCommentFriendsCircleModal .form input[name='automationOperation_url']").val( automationOperation_url );    //操作地址
    $("#praiseAndCommentFriendsCircleModal .form input[name='nickName']").val( nickName );                                  //操作微信号
    $("#praiseAndCommentFriendsCircleModal .form input[name='allSwipeNum']").val( allSwipeNum );                            //滑动朋友圈次数
    $("#praiseAndCommentFriendsCircleModal .form input[name='commentContent']").val( commentContent );                      //评论内容
    $("#praiseAndCommentFriendsCircleModal .form textarea[name='currentDeviceListStr']").val( JSON.stringify(JSON.parse(currentDeviceListStr),null,4) );//操作时间列表
    $('#praiseAndCommentFriendsCircleModal').modal({backdrop: false, keyboard: false}).modal('show');
}

/**
 * 点击 For 发送朋友圈 或者 根据微信昵称进行聊天 或者 分享微信文章到微信朋友圈 或者 添加群成员为好友的V群
 */
function clickFunc_For_sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends(){
    console.log(this);
    console.log($(this).find('span.info-box-number').html() + "\t" + $(this).attr('id'));
    //变更 modal-title
    var operationId = $(this).attr('id');
    var operationName = $(this).find('span.info-box-number').html();
    var operationTitleArr = $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal h4[class='modal-title']").html().split("-");
    console.log(operationTitleArr);
    var operationTitle = operationName + ' - ' + operationTitleArr[1];
    $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal h4[class='modal-title']").html(operationTitle);
    //设置默认参数
    var automationOperation_name = "发送朋友圈";
    var nickNameListStr = "[\n" + "    \"cai_hong_wang\"\n" + "]";
    var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/sendFriendCircle";
    var currentDeviceListStr = "[\n" +
        "    \"小米Max3_10\",\n" +
        "    \"华为Mate8_11\",\n" +
        "    \"华为Mate8_12\",\n" +
        "    \"华为Mate8_13\",\n" +
        "    \"华为Mate8_14\",\n" +
        "    \"华为Mate8_15\",\n" +
        "    \"华为Mate8_16\",\n" +
        "    \"华为Mate8_17\",\n" +
        "    \"华为Mate8海外版_18\",\n" +
        "    \"华为Mate8_19\",\n" +
        "    \"华为Mate8_20\",\n" +
        "    \"华为Mate8_21\"\n" +
        "]";
    if(operationId == "sendFriendCircle"){
        var automationOperation_name = "发送朋友圈";
        var nickNameListStr = "[\n" + "    \"cai_hong_wang\"\n" + "]";
        var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/sendFriendCircle";              //发送朋友圈
        var currentDeviceListStr = "[\n" +
        "    \"小米Max3_08\",\n" +
        "    \"华为Mate8_09\",\n" +
        "    \"华为Mate8_10\",\n" +
        "    \"华为Mate8_11\",\n" +
        "    \"华为Mate8_12\",\n" +
        "    \"华为Mate8_13\",\n" +
        "    \"华为Mate8_14\",\n" +
        "    \"华为Mate8_15\",\n" +
        "    \"华为Mate8海外版_16\",\n" +
        "    \"华为Mate8_17\",\n" +
        "    \"华为Mate8_18\",\n" +
        "    \"华为Mate8_19\"\n" +
        "]";
    } else if(operationId == "shareArticleToFriendCircle"){
        var automationOperation_name = "分享微信文章到微信朋友圈";
        var nickNameListStr = "[\n" + "    \"油站科技\"\n" + "]";
        var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/shareArticleToFriendCircle";    //分享微信文章到微信朋友圈
        var currentDeviceListStr = "[\n" +
            "    \"小米Max3_06\",\n" +
            "    \"华为Mate8_07\",\n" +
            "    \"华为Mate8_08\",\n" +
            "    \"华为Mate8_09\",\n" +
            "    \"华为Mate8_10\",\n" +
            "    \"华为Mate8_11\",\n" +
            "    \"华为Mate8_12\",\n" +
            "    \"华为Mate8_13\",\n" +
            "    \"华为Mate8海外版_14\",\n" +
            "    \"华为Mate8_15\",\n" +
            "    \"华为Mate8_16\",\n" +
            "    \"华为Mate8_17\"\n" +
            "]";
    } else if(operationId == "chatByNickName"){
        var automationOperation_name = "根据微信昵称进行聊天";
        var nickNameListStr = "[\n" + "    \"cai_hong_wang\"\n" + "]";
        var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/chatByNickName";                //根据微信昵称进行聊天
    } else if(operationId == "addGroupMembersAsFriends"){
        var automationOperation_name = "添加群成员为好友的V群";
        var nickNameListStr = "[\n" + "    \"印江跑跑群\"\n" + "]";
        var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/addGroupMembersAsFriends";      //添加群成员为好友的V群
    }
    $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form input[name='automationOperation_name']").val( automationOperation_name );  //操作名称
    $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form input[name='automationOperation_url']").val( automationOperation_url );    //操作地址
    $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form textarea[name='nickNameListStr']").val( JSON.stringify(JSON.parse(nickNameListStr),null,4) );           //微信号列表
    $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form textarea[name='currentDeviceListStr']").val( JSON.stringify(JSON.parse(currentDeviceListStr),null,4) );  //操作时间列表
    $('#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal').modal({backdrop: false, keyboard: false}).modal('show');
}
