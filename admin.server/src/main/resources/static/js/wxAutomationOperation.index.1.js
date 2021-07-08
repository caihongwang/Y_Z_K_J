$(function() {
    //点击 For 默认：阅读微信文章并点击广告 或者 同意好友请求 或者 将群保存到通讯录 或者 同意进群
    $("#clickArticleAd").on('click', clickFunc_For_default);
    $("#agreeToFriendRequest").on('click', clickFunc_For_default);
    $("#saveToAddressBook").on('click', clickFunc_For_default);
    $("#agreeToJoinTheGroup").on('click', clickFunc_For_default);
    //表单提交 For 默认：阅读微信文章并点击广告 或者 同意好友请求 或者 将群保存到通讯录 或者 同意进群
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
            if(!validateJsonInput()){       //对当前的Model弹窗中Json输入框进行校验
                layer.open({
                    title: I18n.system_tips,
                    btn: [I18n.system_ok],
                    content: "Json数据格式异常，请查看后再操作",
                    icon: '2'
                });
                return;
            }

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
            if(!validateJsonInput()){       //对当前的Model弹窗中Json输入框进行校验
                layer.open({
                    title: I18n.system_tips,
                    btn: [I18n.system_ok],
                    content: "Json数据格式异常，请查看后再操作",
                    icon: '2'
                });
                return;
            }

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
            if(!validateJsonInput()){       //对当前的Model弹窗中Json输入框进行校验
                layer.open({
                    title: I18n.system_tips,
                    btn: [I18n.system_ok],
                    content: "Json数据格式异常，请查看后再操作",
                    icon: '2'
                });
                return;
            }

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


    //点击 For 分享视频号到朋友圈
    $("#shareVideoNumToFriendCircle").on('click', clickFunc_For_shareVideoNumToFriendCircle);
    //表单提交 For 分享视频号到朋友圈
    var shareVideoNumToFriendCircleModalValidate = $("#shareVideoNumToFriendCircleModal .form").validate({
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
            if(!validateJsonInput()){       //对当前的Model弹窗中Json输入框进行校验
                layer.open({
                    title: I18n.system_tips,
                    btn: [I18n.system_ok],
                    content: "Json数据格式异常，请查看后再操作",
                    icon: '2'
                });
                return;
            }

            var automationOperation_url = $("#shareVideoNumToFriendCircleModal .form input[name='automationOperation_url']").val();
            console.log("automationOperation_url = " + automationOperation_url);
            console.log($("#shareVideoNumToFriendCircleModal .form").serializeArray());
            $.post(
                automationOperation_url,
                $("#shareVideoNumToFriendCircleModal .form").serialize(),
                function(data, status) {
                    if (data.code == "0") {
                        $('#shareVideoNumToFriendCircleModal').modal('hide');
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


    //点击 For 邀请进群
    $("#inviteToJoinTheGroup").on('click', clickFunc_For_inviteToJoinTheGroup);
    //表单提交 For 邀请进群
    var inviteToJoinTheGroupModalModalValidate = $("#inviteToJoinTheGroupModal .form").validate({
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
            if(!validateJsonInput()){       //对当前的Model弹窗中Json输入框进行校验
                layer.open({
                    title: I18n.system_tips,
                    btn: [I18n.system_ok],
                    content: "Json数据格式异常，请查看后再操作",
                    icon: '2'
                });
                return;
            }

            var automationOperation_url = $("#inviteToJoinTheGroupModal .form input[name='automationOperation_url']").val();
            console.log("automationOperation_url = " + automationOperation_url);
            console.log($("#inviteToJoinTheGroupModal .form").serializeArray());
            $.post(
                automationOperation_url,
                $("#inviteToJoinTheGroupModal .form").serialize(),
                function(data, status) {
                    if (data.code == "0") {
                        $('#inviteToJoinTheGroupModal').modal('hide');
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


    //点击 For 转发微信消息
    $("#relayTheWxMessage").on('click', clickFunc_For_relayTheWxMessage);
    //表单提交 For 转发微信消息
    var relayTheWxMessageModalValidate = $("#relayTheWxMessageModal .form").validate({
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
            if(!validateJsonInput()){       //对当前的Model弹窗中Json输入框进行校验
                layer.open({
                    title: I18n.system_tips,
                    btn: [I18n.system_ok],
                    content: "Json数据格式异常，请查看后再操作",
                    icon: '2'
                });
                return;
            }

            var automationOperation_url = $("#relayTheWxMessageModal .form input[name='automationOperation_url']").val();
            console.log("automationOperation_url = " + automationOperation_url);
            console.log($("#relayTheWxMessageModal .form").serializeArray());
            $.post(
                automationOperation_url,
                $("#relayTheWxMessageModal .form").serialize(),
                function(data, status) {
                    if (data.code == "0") {
                        $('#relayTheWxMessageModal').modal('hide');
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

function validateJsonInput() {
    var flag = true;
    //阅读微信文章并点击广告 或者 同意好友请求 或者 将群保存到通讯录 或者 同意进群 --->>> 操作设备列表
    var currentDeviceListStr = $("#automationOperationModal .form textarea[name='currentDeviceListStr']").val();
    if(!isNull(currentDeviceListStr)){
        try {
            $("#automationOperationModal .form textarea[name='currentDeviceListStr']").val(JSON.stringify(JSON.parse(currentDeviceListStr), null, 4));
        } catch (e) {
            $("#automationOperationModal .form textarea[name='currentDeviceListStr']").val(currentDeviceListStr);
            flag = false;
        }
    }
    //点赞和评论朋友圈 --->>> 操作设备列表
    var currentDeviceListStr = $("#praiseAndCommentFriendsCircleModal .form textarea[name='currentDeviceListStr']").val();
    if(!isNull(currentDeviceListStr)){
        console.log("aaaaaaaaa");
        try {
            $("#praiseAndCommentFriendsCircleModal .form textarea[name='currentDeviceListStr']").val(JSON.stringify(JSON.parse(currentDeviceListStr), null, 4));
        } catch (e) {
            $("#praiseAndCommentFriendsCircleModal .form textarea[name='currentDeviceListStr']").val(currentDeviceListStr);
            flag = false;
        }
    }
    //发送朋友圈 或者 根据微信昵称进行聊天 或者 分享微信文章到微信朋友圈 或者 添加群成员为好友的V群 --->>> 微信号列表
    var nickNameListStr = $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form textarea[name='nickNameListStr']").val();
    if(!isNull(nickNameListStr)){
        try {
            $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form textarea[name='nickNameListStr']").val(JSON.stringify(JSON.parse(nickNameListStr), null, 4));
        } catch (e) {
            $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form textarea[name='nickNameListStr']").val(nickNameListStr);
            flag = false;
        }
    }
    //发送朋友圈 或者 根据微信昵称进行聊天 或者 分享微信文章到微信朋友圈 或者 添加群成员为好友的V群 --->>> 操作设备列表
    var currentDeviceListStr = $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form textarea[name='currentDeviceListStr']").val();
    if(!isNull(currentDeviceListStr)){
        try {
            $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form textarea[name='currentDeviceListStr']").val(JSON.stringify(JSON.parse(currentDeviceListStr), null, 4));
        } catch (e) {
            $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form textarea[name='currentDeviceListStr']").val(currentDeviceListStr);
            flag = false;
        }
    }
    //分享视频号到朋友圈 --->>> 操作设备列表
    var currentDeviceListStr = $("#shareVideoNumToFriendCircleModal .form textarea[name='currentDeviceListStr']").val();
    if(!isNull(currentDeviceListStr)){
        try {
            $("#shareVideoNumToFriendCircleModal .form textarea[name='currentDeviceListStr']").val(JSON.stringify(JSON.parse(currentDeviceListStr), null, 4));
        } catch (e) {
            $("#shareVideoNumToFriendCircleModal .form textarea[name='currentDeviceListStr']").val(currentDeviceListStr);
            flag = false;
        }
    }
    //转发微信消息 --->>> 操作设备列表
    var currentDeviceListStr = $("#relayTheWxMessageModal .form textarea[name='currentDeviceListStr']").val();
    if(!isNull(currentDeviceListStr)){
        try {
            $("#relayTheWxMessageModal .form textarea[name='currentDeviceListStr']").val(JSON.stringify(JSON.parse(currentDeviceListStr), null, 4));
        } catch (e) {
            $("#relayTheWxMessageModal .form textarea[name='currentDeviceListStr']").val(currentDeviceListStr);
            flag = false;
        }
    }
    //转发微信消息 --->>> 操作设备列表
    var groupNickNameMapStr = $("#relayTheWxMessageModal .form textarea[name='groupNickNameMapStr']").val();
    if(!isNull(groupNickNameMapStr)){
        try {
            $("#relayTheWxMessageModal .form textarea[name='groupNickNameMapStr']").val(JSON.stringify(JSON.parse(groupNickNameMapStr), null, 4));
        } catch (e) {
            $("#relayTheWxMessageModal .form textarea[name='groupNickNameMapStr']").val(groupNickNameMapStr);
            flag = false;
        }
    }
    //邀请进群 --->>> 被邀请的昵称列表
    var nickNameListStr = $("#inviteToJoinTheGroupModal .form textarea[name='nickNameListStr']").val();
    if(!isNull(nickNameListStr)){
        try {
            $("#inviteToJoinTheGroupModal .form textarea[name='nickNameListStr']").val(JSON.stringify(JSON.parse(nickNameListStr), null, 4));
        } catch (e) {
            $("#inviteToJoinTheGroupModal .form textarea[name='nickNameListStr']").val(nickNameListStr);
            flag = false;
        }
    }
    //邀请进群 --->>> 操作设备列表
    var groupNickNameMapStr = $("#inviteToJoinTheGroupModal .form textarea[name='groupNickNameMapStr']").val();
    if(!isNull(groupNickNameMapStr)){
        try {
            $("#inviteToJoinTheGroupModal .form textarea[name='groupNickNameMapStr']").val(JSON.stringify(JSON.parse(groupNickNameMapStr), null, 4));
        } catch (e) {
            $("#inviteToJoinTheGroupModal .form textarea[name='groupNickNameMapStr']").val(groupNickNameMapStr);
            flag = false;
        }
    }
    return flag;
}


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
    $("#automationOperationModal .form textarea[name='currentDeviceListStr']").val( JSON.stringify(JSON.parse(currentDeviceListStr),null,2) );//操作设备列表
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
    $("#praiseAndCommentFriendsCircleModal .form textarea[name='currentDeviceListStr']").val( JSON.stringify(JSON.parse(currentDeviceListStr),null,4) );//操作设备列表
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
    } else if(operationId == "shareArticleToFriendCircle"){
        var automationOperation_name = "分享微信文章到微信朋友圈";
        var nickNameListStr = "[\n" + "    \"cai_hong_wang\"\n" + "]";
        var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/shareArticleToFriendCircle";    //分享微信文章到微信朋友圈
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
    $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form textarea[name='currentDeviceListStr']").val( JSON.stringify(JSON.parse(currentDeviceListStr),null,4) );  //操作设备列表
    $('#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal').modal({backdrop: false, keyboard: false}).modal('show');
}

/**
 * 点击 For 分享视频号到朋友圈
 */
function clickFunc_For_shareVideoNumToFriendCircle(){
    console.log(this);
    console.log($(this).find('span.info-box-number').html() + "\t" + $(this).attr('id'));
    //变更 modal-title
    var operationId = $(this).attr('id');
    var operationName = $(this).find('span.info-box-number').html();
    var operationTitleArr = $("#shareVideoNumToFriendCircleModal h4[class='modal-title']").html().split("-");
    var operationTitle = operationName + ' - ' + operationTitleArr[1];
    $("#shareVideoNumToFriendCircleModal h4[class='modal-title']").html(operationTitle);
    //设置默认参数
    var automationOperation_name = "分享视频号到朋友圈";
    var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/shareVideoNumToFriendCircle";     //分享视频号到朋友圈
    var automationOperation_shareFendCircleCentent = "恩，怎么说呢，你还是自己看吧，文字描述已无力...";
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
    $("#shareVideoNumToFriendCircleModal .form input[name='automationOperation_name']").val( automationOperation_name );  //操作名称
    $("#shareVideoNumToFriendCircleModal .form input[name='automationOperation_url']").val( automationOperation_url );    //操作地址
    $("#shareVideoNumToFriendCircleModal .form input[name='automationOperation_shareFendCircleCentent']").val( automationOperation_shareFendCircleCentent );    //分享视频号到朋友圈
    $("#shareVideoNumToFriendCircleModal .form textarea[name='currentDeviceListStr']").val( JSON.stringify(JSON.parse(currentDeviceListStr),null,2) );//操作设备列表
    $('#shareVideoNumToFriendCircleModal').modal({backdrop: false, keyboard: false}).modal('show');
}

/**
 * 当前设备选择事件 for inviteToJoinTheGroupModal
 * @param obj
 */
function changeCurrentDeviceForInviteToJoinTheGroupModalFun(obj){
    var deviceNameDesc = $("#inviteToJoinTheGroupModal .form select[name='currentDevice']").children('option:selected').val();
    var groupNickNameMapStr = $("#inviteToJoinTheGroupModal .form select[name='currentDevice']").children('option:selected').attr("groupNickNameMapStr");
    console.log("changeCurrentDeviceForAddOrUpdateModalFun --->>> deviceNameDesc = " + deviceNameDesc + " --->>> groupNickNameMapStr = " + groupNickNameMapStr);

    var currentDeviceList = [];
    currentDeviceList.push(deviceNameDesc);
    $("#inviteToJoinTheGroupModal .form textarea[name='currentDeviceListStr']").val( JSON.stringify(currentDeviceList,null,2) );    //操作设备列表
    $("#inviteToJoinTheGroupModal .form textarea[name='groupNickNameMapStr']").val( JSON.stringify(JSON.parse(groupNickNameMapStr),null,2) );//群昵称列表
}

/**
 * 点击 For 邀请进群
 */
function clickFunc_For_inviteToJoinTheGroup(){
    console.log(this);
    console.log($(this).find('span.info-box-number').html() + "\t" + $(this).attr('id'));
    //变更 modal-title
    var operationId = $(this).attr('id');
    var operationName = $(this).find('span.info-box-number').html();
    var operationTitleArr = $("#inviteToJoinTheGroupModal h4[class='modal-title']").html().split("-");
    var operationTitle = operationName + ' - ' + operationTitleArr[1];
    $("#inviteToJoinTheGroupModal h4[class='modal-title']").html(operationTitle);

    var paramStr = "dicType=deviceNameDescToGroupNameMapStr";
    $.post(
        I18n.system_url_pre+"/automation/dic/getDicListByConditionForAdmin",
        paramStr,
        function(res) {
            console.log("===============res==================");
            console.log(res);
            var data = res.data;
            var deviceNameDescToGroupNameMapArr = [];
            if (res.code == "0") {
                for (i = 0; i < data.length; i++) {
                    var detailObj = data[i];
                    //拆分显示 dicRemark
                    var dicRemark_jsonObj = JSON.parse(detailObj.dicRemark);
                    var deviceNameDesc = dicRemark_jsonObj.deviceNameDesc;
                    var groupNickNameMapStr = dicRemark_jsonObj.groupNickNameMapStr;
                    deviceNameDescToGroupNameMapArr[deviceNameDesc] = groupNickNameMapStr;
                }
            } else {
                layer.open({
                    title: I18n.system_tips ,
                    btn: [ I18n.system_ok ],
                    content: (data.message || I18n.system_opt_fail ),
                    icon: '2'
                });
                deviceNameDescToGroupNameMapArr["华为Mate20Pro"] = "{\"贵阳帝景26栋家园群\":\"209\"}";
            }
            console.log("===============deviceNameDescToGroupNameMapArr==================");
            console.log(deviceNameDescToGroupNameMapArr);
            //设置默认参数
            var automationOperation_name = "邀请进群";
            var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/inviteToJoinTheGroup";     //邀请进群
            var nickNameListStr = "[\n" +
                "    \"cai_hongwang\",\n" +
                "    \"cai_hong_wang\"\n" +
                "]";
            var index = 0;
            var optionHtml = "";
            var currentDeviceList = [];
            for(deviceNameDesc in deviceNameDescToGroupNameMapArr){
                if(index == 0){
                    currentDeviceList.push(deviceNameDesc);
                    $("#inviteToJoinTheGroupModal .form select[name='currentDevice']").val(deviceNameDesc);
                    optionHtml = optionHtml + "<option groupNickNameMapStr='"+deviceNameDescToGroupNameMapArr[deviceNameDesc]+"' value='"+deviceNameDesc+"' selected>"+deviceNameDesc+"</option>";
                } else {
                    optionHtml = optionHtml + "<option groupNickNameMapStr='"+deviceNameDescToGroupNameMapArr[deviceNameDesc]+"' value='"+deviceNameDesc+"'>"+deviceNameDesc+"</option>";
                }
                index++;
            }
            $("#inviteToJoinTheGroupModal .form select[name='currentDevice']").html(optionHtml);
            $("#inviteToJoinTheGroupModal .form select[name='currentDevice']").trigger("change");       //通过出发时间，初始化 操作设备列表
            $("#inviteToJoinTheGroupModal .form input[name='automationOperation_name']").val( automationOperation_name );  //操作名称
            $("#inviteToJoinTheGroupModal .form input[name='automationOperation_url']").val( automationOperation_url );    //操作地址
            $("#inviteToJoinTheGroupModal .form textarea[name='nickNameListStr']").val( JSON.stringify(JSON.parse(nickNameListStr),null,2) );//被邀请的昵称列表
            // $("#inviteToJoinTheGroupModal .form textarea[name='currentDeviceListStr']").val( JSON.stringify(currentDeviceList,null,2) );//操作设备列表
            $('#inviteToJoinTheGroupModal').modal({backdrop: false, keyboard: false}).modal('show');
        }
    );





}


/**
 * 当前设备选择事件 for inviteToJoinTheGroupModal
 * @param obj
 */
function changeCurrentDeviceForRelayTheWxMessageModalFun(obj){
    var deviceNameDesc = $("#relayTheWxMessageModal .form select[name='currentDevice']").children('option:selected').val();
    var groupNickNameMapStr = $("#relayTheWxMessageModal .form select[name='currentDevice']").children('option:selected').attr("groupNickNameMapStr");
    console.log("changeCurrentDeviceForAddOrUpdateModalFun --->>> deviceNameDesc = " + deviceNameDesc + " --->>> groupNickNameMapStr = " + groupNickNameMapStr);

    var currentDeviceList = [];
    currentDeviceList.push(deviceNameDesc);
    $("#relayTheWxMessageModal .form textarea[name='currentDeviceListStr']").val( JSON.stringify(currentDeviceList,null,2) );    //操作设备列表
    $("#relayTheWxMessageModal .form textarea[name='groupNickNameMapStr']").val( JSON.stringify(JSON.parse(groupNickNameMapStr),null,2) );//群昵称列表
}

/**
 * 点击 For 转发微信消息
 */
function clickFunc_For_relayTheWxMessage(){
    console.log(this);
    console.log($(this).find('span.info-box-number').html() + "\t" + $(this).attr('id'));
    //变更 modal-title
    var operationId = $(this).attr('id');
    var operationName = $(this).find('span.info-box-number').html();
    var operationTitleArr = $("#relayTheWxMessageModal h4[class='modal-title']").html().split("-");
    var operationTitle = operationName + ' - ' + operationTitleArr[1];
    $("#relayTheWxMessageModal h4[class='modal-title']").html(operationTitle);

    var paramStr = "dicType=deviceNameDescToGroupNameMapStr";
    $.post(
        I18n.system_url_pre+"/automation/dic/getDicListByConditionForAdmin",
        paramStr,
        function(res) {
            console.log("===============res==================");
            console.log(res);
            var data = res.data;
            var deviceNameDescToGroupNameMapArr = [];
            if (res.code == "0") {
                for (i = 0; i < data.length; i++) {
                    var detailObj = data[i];
                    //拆分显示 dicRemark
                    var dicRemark_jsonObj = JSON.parse(detailObj.dicRemark);
                    var deviceNameDesc = dicRemark_jsonObj.deviceNameDesc;
                    var groupNickNameMapStr = dicRemark_jsonObj.groupNickNameMapStr;
                    deviceNameDescToGroupNameMapArr[deviceNameDesc] = groupNickNameMapStr;
                }
            } else {
                layer.open({
                    title: I18n.system_tips ,
                    btn: [ I18n.system_ok ],
                    content: (data.message || I18n.system_opt_fail ),
                    icon: '2'
                });
                deviceNameDescToGroupNameMapArr["华为Mate20Pro_22"] = "{\"油站科技-内部交流群\":\"21\"}";
            }
            console.log("===============deviceNameDescToGroupNameMapArr==================");
            console.log(deviceNameDescToGroupNameMapArr);
            //设置默认参数
            var automationOperation_name = "转发微信消息";
            var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/relayTheWxMessage";     //邀请进群
            var index = 0;
            var optionHtml = "";
            for(deviceNameDesc in deviceNameDescToGroupNameMapArr){
                if(index == 0){
                    $("#relayTheWxMessageModal .form select[name='currentDevice']").val(deviceNameDesc);
                    optionHtml = optionHtml + "<option groupNickNameMapStr='"+deviceNameDescToGroupNameMapArr[deviceNameDesc]+"' value='"+deviceNameDesc+"' selected>"+deviceNameDesc+"</option>";
                } else {
                    optionHtml = optionHtml + "<option groupNickNameMapStr='"+deviceNameDescToGroupNameMapArr[deviceNameDesc]+"' value='"+deviceNameDesc+"'>"+deviceNameDesc+"</option>";
                }
                index++;
            }
            $("#relayTheWxMessageModal .form select[name='currentDevice']").html(optionHtml);
            $("#relayTheWxMessageModal .form select[name='currentDevice']").trigger("change");       //通过出发时间，初始化 操作设备列表
            $("#relayTheWxMessageModal .form input[name='automationOperation_name']").val( automationOperation_name );  //操作名称
            $("#relayTheWxMessageModal .form input[name='automationOperation_url']").val( automationOperation_url );    //操作地址
            $('#relayTheWxMessageModal').modal({backdrop: false, keyboard: false}).modal('show');
        }
    );





}
