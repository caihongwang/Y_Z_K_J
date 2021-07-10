$(function() {
    //点击 For 默认：阅读微信文章并点击广告 或者 同意好友请求 或者 将群保存到通讯录 或者 同意进群 或者 添加群成员为好友的V群
    $("#clickArticleAd").on('click', clickFunc_For_default);
    $("#agreeToFriendRequest").on('click', clickFunc_For_default);
    $("#saveToAddressBook").on('click', clickFunc_For_default);
    $("#agreeToJoinTheGroup").on('click', clickFunc_For_default);
    $("#addGroupMembersAsFriends").on('click', clickFunc_For_default);
    //表单提交 For 默认：阅读微信文章并点击广告 或者 同意好友请求 或者 将群保存到通讯录 或者 同意进群 或者 添加群成员为好友的V群
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
            if(!validateJsonInput("automationOperationModal")){       //对当前的Model弹窗中Json输入框进行校验
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


    //点击 For 发送朋友圈 或者 根据微信昵称进行聊天 或者 分享微信文章到微信朋友圈
    $("#chatByNickName").on('click', clickFunc_For_sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle);
    $("#sendFriendCircle").on('click', clickFunc_For_sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle);
    $("#shareArticleToFriendCircle").on('click', clickFunc_For_sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle);
    //表单提交 For 发送朋友圈 或者 根据微信昵称进行聊天 或者 分享微信文章到微信朋友圈
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
            if(!validateJsonInput("sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal")){       //对当前的Model弹窗中Json输入框进行校验
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
            if(!validateJsonInput("praiseAndCommentFriendsCircleModal")){       //对当前的Model弹窗中Json输入框进行校验
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
            if(!validateJsonInput("shareVideoNumToFriendCircleModal")){       //对当前的Model弹窗中Json输入框进行校验
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
            if(!validateJsonInput("inviteToJoinTheGroupModal")){       //对当前的Model弹窗中Json输入框进行校验
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
            if(!validateJsonInput("relayTheWxMessageModal")){       //对当前的Model弹窗中Json输入框进行校验
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

/**
 * 判断modal中的textarea输入框中的value是否符合json格式
 * @param modalId
 * @returns {boolean}
 */
function validateJsonInput(modalId) {
    var flag = true;
    var allTextareaObj = $("#"+modalId+" .form").find("textarea");
    console.log("modalId --->>> " + modalId);
    console.log("allTextareaObj");
    console.log(allTextareaObj);
    for (var index in allTextareaObj) {
        var textareaObj = allTextareaObj[index];
        var value = '';
        try{
            value = $(textareaObj).val();
        } catch (e) {
            break;
        }
        console.log("value --->>> " + value);
        console.log("name --->>> " + $(textareaObj).attr("name"));
        try {
            $(textareaObj).val(JSON.stringify(JSON.parse(value), null, 4));
        } catch (e) {
            $(textareaObj).val(value);
            console.log("error value : " + value);
            flag = false;
            break;
        }
    }
    console.log("flag = " + flag);
    return flag;
}

/**
 * 当前设备选择事件 for automationOperationModal
 * @param obj
 */
function changeCurrentDeviceForDefaultModalFun(obj){
    var opotionName = $("#automationOperationModal .form select[name='currentDevice']").children('option:selected').val();
    var deviceNameDesc = $("#automationOperationModal .form select[name='currentDevice']").children('option:selected').attr("deviceNameDesc");
    console.log("automationOperationModal --->>> opotionName = " + opotionName + "--->>> deviceNameDesc = " + deviceNameDesc);

    var currentDeviceList = [];
    if("全部" == opotionName){        //全部的时候，是数组
        currentDeviceList = new Array();
        currentDeviceList = deviceNameDesc.split(",");
    } else {
        currentDeviceList.push(deviceNameDesc);
    }
    console.log("currentDeviceList");
    console.log(currentDeviceList);
    $("#automationOperationModal .form textarea[name='currentDeviceListStr']").val( JSON.stringify(currentDeviceList,null,4) );    //操作设备列表
}

/**
 * 点击 For 默认：阅读微信文章并点击广告 或者 同意好友请求 或者 将群保存到通讯录 或者 同意进群 或者 转发微信消息 或者 添加群成员为好友的V群
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

    var paramStr = "dicType=deviceNameListAndLocaltion&dicCode=HuaWeiListAndSendFriendCircleLocaltion";
    $.post(
        I18n.system_url_pre+"/automation/dic/getDicListByConditionForAdmin",
        paramStr,
        function(res) {
            console.log("===============res==================");
            console.log(res);
            var data = res.data;
            var deviceNameDescArr = [];
            if (res.code == "0") {
                for (i = 0; i < data.length; i++) {
                    var detailObj = data[i];
                    //拆分显示 dicRemark
                    var dicRemark_jsonObj = JSON.parse(detailObj.dicRemark);
                    var deviceNameList = dicRemark_jsonObj.deviceNameList;
                    console.log("deviceNameList");
                    console.log(deviceNameList);
                    deviceNameDescArr["全部"] = [];
                    for (j = 0; j < deviceNameList.length; j++) {
                        var deviceNameMap = deviceNameList[j];
                        deviceNameDescArr[deviceNameMap.deviceNameDesc] = deviceNameMap.deviceNameDesc;
                        deviceNameDescArr["全部"].push(deviceNameMap.deviceNameDesc);
                    }
                    console.log("deviceNameDescArr");
                    console.log(deviceNameDescArr);
                }
            } else {
                layer.open({
                    title: I18n.system_tips ,
                    btn: [ I18n.system_ok ],
                    content: (data.message || I18n.system_opt_fail ),
                    icon: '2'
                });
                deviceNameDescArr["华为Mate20Pro_22"] = "华为Mate20Pro_22";
            }
            var index = 0;
            var optionHtml = "";
            for(deviceNameDesc in deviceNameDescArr){
                if(index == 0){
                    $("#relayTheWxMessageModal .form select[name='currentDevice']").val(deviceNameDesc);
                    optionHtml = optionHtml + "<option deviceNameDesc='"+deviceNameDescArr[deviceNameDesc]+"' value='"+deviceNameDesc+"' selected>"+deviceNameDesc+"</option>";
                } else {
                    optionHtml = optionHtml + "<option deviceNameDesc='"+deviceNameDescArr[deviceNameDesc]+"' value='"+deviceNameDesc+"'>"+deviceNameDesc+"</option>";
                }
                index++;
            }

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
            } else if(operationId == "addGroupMembersAsFriends"){
                var automationOperation_name = "添加群成员为好友的V群";
                var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/addGroupMembersAsFriends";      //添加群成员为好友的V群
            }

            $("#automationOperationModal .form select[name='currentDevice']").html(optionHtml);
            $("#automationOperationModal .form select[name='currentDevice']").trigger("change");       //通过触发事件，初始化 操作设备列表
            $("#automationOperationModal .form input[name='automationOperation_name']").val( automationOperation_name );  //操作名称
            $("#automationOperationModal .form input[name='automationOperation_url']").val( automationOperation_url );    //操作地址
            $('#automationOperationModal').modal({backdrop: false, keyboard: false}).modal('show');
        }
    );
}


/**
 * 当前设备选择事件 for praiseAndCommentFriendsCircleModal
 * @param obj
 */
function changeCurrentDeviceForPraiseAndCommentFriendsCircleModalFun(obj){
    var opotionName = $("#praiseAndCommentFriendsCircleModal .form select[name='currentDevice']").children('option:selected').val();
    var deviceNameDesc = $("#praiseAndCommentFriendsCircleModal .form select[name='currentDevice']").children('option:selected').attr("deviceNameDesc");
    console.log("praiseAndCommentFriendsCircleModal --->>> opotionName = " + opotionName + "--->>> deviceNameDesc = " + deviceNameDesc);

    var currentDeviceList = [];
    if("全部" == opotionName){        //全部的时候，是数组
        currentDeviceList = new Array();
        currentDeviceList = deviceNameDesc.split(",");
    } else {
        currentDeviceList.push(deviceNameDesc);
    }
    console.log("currentDeviceList");
    console.log(currentDeviceList);
    $("#praiseAndCommentFriendsCircleModal .form textarea[name='currentDeviceListStr']").val( JSON.stringify(currentDeviceList,null,4) );    //操作设备列表
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

    var paramStr = "dicType=deviceNameListAndLocaltion&dicCode=HuaWeiListAndSendFriendCircleLocaltion";
    $.post(
        I18n.system_url_pre+"/automation/dic/getDicListByConditionForAdmin",
        paramStr,
        function(res) {
            console.log("===============res==================");
            console.log(res);
            var data = res.data;
            var deviceNameDescArr = [];
            if (res.code == "0") {
                for (i = 0; i < data.length; i++) {
                    var detailObj = data[i];
                    //拆分显示 dicRemark
                    var dicRemark_jsonObj = JSON.parse(detailObj.dicRemark);
                    var deviceNameList = dicRemark_jsonObj.deviceNameList;
                    console.log("deviceNameList");
                    console.log(deviceNameList);
                    deviceNameDescArr["全部"] = [];
                    for (j = 0; j < deviceNameList.length; j++) {
                        var deviceNameMap = deviceNameList[j];
                        deviceNameDescArr[deviceNameMap.deviceNameDesc] = deviceNameMap.deviceNameDesc;
                        deviceNameDescArr["全部"].push(deviceNameMap.deviceNameDesc);
                    }
                    console.log("deviceNameDescArr");
                    console.log(deviceNameDescArr);
                }
            } else {
                layer.open({
                    title: I18n.system_tips ,
                    btn: [ I18n.system_ok ],
                    content: (data.message || I18n.system_opt_fail ),
                    icon: '2'
                });
                deviceNameDescArr["华为Mate20Pro_22"] = "华为Mate20Pro_22";
            }
            var index = 0;
            var optionHtml = "";
            for(deviceNameDesc in deviceNameDescArr){
                if(index == 0){
                    $("#relayTheWxMessageModal .form select[name='currentDevice']").val(deviceNameDesc);
                    optionHtml = optionHtml + "<option deviceNameDesc='"+deviceNameDescArr[deviceNameDesc]+"' value='"+deviceNameDesc+"' selected>"+deviceNameDesc+"</option>";
                } else {
                    optionHtml = optionHtml + "<option deviceNameDesc='"+deviceNameDescArr[deviceNameDesc]+"' value='"+deviceNameDesc+"'>"+deviceNameDesc+"</option>";
                }
                index++;
            }

            //设置默认参数
            var automationOperation_name = "点赞和评论朋友圈";
            var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/praiseAndCommentFriendsCircle";
            var nickName = "所有";
            var allSwipeNum = "11";
            var commentContent = "看着好高级啊，真棒...";

            $("#praiseAndCommentFriendsCircleModal .form select[name='currentDevice']").html(optionHtml);
            $("#praiseAndCommentFriendsCircleModal .form select[name='currentDevice']").trigger("change");       //通过触发事件，初始化 操作设备列表
            $("#praiseAndCommentFriendsCircleModal .form input[name='automationOperation_name']").val( automationOperation_name );  //操作名称
            $("#praiseAndCommentFriendsCircleModal .form input[name='automationOperation_url']").val( automationOperation_url );    //操作地址
            $("#praiseAndCommentFriendsCircleModal .form input[name='nickName']").val( nickName );                                  //操作微信号
            $("#praiseAndCommentFriendsCircleModal .form input[name='allSwipeNum']").val( allSwipeNum );                            //滑动朋友圈次数
            $("#praiseAndCommentFriendsCircleModal .form input[name='commentContent']").val( commentContent );                      //评论内容
            $('#praiseAndCommentFriendsCircleModal').modal({backdrop: false, keyboard: false}).modal('show');
        }
    );
}


/**
 * 当前设备选择事件 for sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircleModalFun
 * @param obj
 */
function changeCurrentNickName_For_sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircleModalFun(obj){
    var opotionName = $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form select[name='currentNickName']").children('option:selected').val();
    var currentNickName = $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form select[name='currentNickName']").children('option:selected').attr("currentNickName");
    console.log("sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal --->>> opotionName = " + opotionName + "--->>> currentNickName = " + currentNickName);

    var currentNickNameList = [];
    if("全部" == opotionName){        //全部的时候，是数组
        currentNickNameList = new Array();
        currentNickNameList = currentNickName.split(",");
    } else {
        currentNickNameList.push(currentNickName);
    }
    console.log("currentNickNameList");
    console.log(currentNickNameList);
    $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form textarea[name='nickNameListStr']").val( JSON.stringify(currentNickNameList,null,4) );    //昵称列表
}

/**
 * 当前设备选择事件 for sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal
 * @param obj
 */
function changeCurrentDevice_For_sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircleModalFun(obj){
    var opotionName = $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form select[name='currentDevice']").children('option:selected').val();
    var deviceNameDesc = $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form select[name='currentDevice']").children('option:selected').attr("deviceNameDesc");
    console.log("sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal --->>> opotionName = " + opotionName + "--->>> deviceNameDesc = " + deviceNameDesc);

    var currentDeviceList = [];
    if("全部" == opotionName){        //全部的时候，是数组
        currentDeviceList = new Array();
        currentDeviceList = deviceNameDesc.split(",");
    } else {
        currentDeviceList.push(deviceNameDesc);
    }
    console.log("currentDeviceList");
    console.log(currentDeviceList);
    $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form textarea[name='currentDeviceListStr']").val( JSON.stringify(currentDeviceList,null,4) );    //操作设备列表
}

/**
 * 点击 For 发送朋友圈 或者 根据微信昵称进行聊天 或者 分享微信文章到微信朋友圈
 */
function clickFunc_For_sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle(){
    console.log(this);
    console.log($(this).find('span.info-box-number').html() + "\t" + $(this).attr('id'));
    //变更 modal-title
    var operationId = $(this).attr('id');
    var operationName = $(this).find('span.info-box-number').html();
    var operationTitleArr = $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal h4[class='modal-title']").html().split("-");
    console.log(operationTitleArr);
    var operationTitle = operationName + ' - ' + operationTitleArr[1];
    $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal h4[class='modal-title']").html(operationTitle);

    var paramStr = "dicType=deviceNameListAndLocaltion&dicCode=HuaWeiListAndSendFriendCircleLocaltion";
    $.post(
        I18n.system_url_pre+"/automation/dic/getDicListByConditionForAdmin",
        paramStr,
        function(res) {
            console.log("===============res==================");
            console.log(res);
            var data = res.data;
            var deviceNameDescArr = [];
            if (res.code == "0") {
                for (i = 0; i < data.length; i++) {
                    var detailObj = data[i];
                    //拆分显示 dicRemark
                    var dicRemark_jsonObj = JSON.parse(detailObj.dicRemark);
                    var deviceNameList = dicRemark_jsonObj.deviceNameList;
                    console.log("deviceNameList");
                    console.log(deviceNameList);
                    deviceNameDescArr["全部"] = [];
                    for (j = 0; j < deviceNameList.length; j++) {
                        var deviceNameMap = deviceNameList[j];
                        deviceNameDescArr[deviceNameMap.deviceNameDesc] = deviceNameMap.deviceNameDesc;
                        deviceNameDescArr["全部"].push(deviceNameMap.deviceNameDesc);
                    }
                    console.log("deviceNameDescArr");
                    console.log(deviceNameDescArr);
                }
            } else {
                layer.open({
                    title: I18n.system_tips ,
                    btn: [ I18n.system_ok ],
                    content: (data.message || I18n.system_opt_fail ),
                    icon: '2'
                });
                deviceNameDescArr["华为Mate20Pro_22"] = "华为Mate20Pro_22";
            }
            var optionHtml_currentDevice = "";
            for(deviceNameDesc in deviceNameDescArr){
                if(deviceNameDesc == "全部"){
                    $("#relayTheWxMessageModal .form select[name='currentDevice']").val(deviceNameDesc);
                    optionHtml_currentDevice = optionHtml_currentDevice + "<option deviceNameDesc='"+deviceNameDescArr[deviceNameDesc]+"' value='"+deviceNameDesc+"' selected>"+deviceNameDesc+"</option>";
                } else {
                    optionHtml_currentDevice = optionHtml_currentDevice + "<option deviceNameDesc='"+deviceNameDescArr[deviceNameDesc]+"' value='"+deviceNameDesc+"'>"+deviceNameDesc+"</option>";
                }
            }

            //设置默认参数
            var paramStr = "dicType=sendFriendCircle&dicStatus=0";     //参数
            var automationOperation_name = "发送朋友圈";
            var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/sendFriendCircle";
            if(operationId == "sendFriendCircle"){
                paramStr = "dicType=sendFriendCircle&dicStatus=0";
                var automationOperation_name = "发送朋友圈";
                var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/sendFriendCircle";              //发送朋友圈
            } else if(operationId == "shareArticleToFriendCircle"){
                paramStr = "dicType=shareArticleToFriendCircle&dicStatus=0";
                var automationOperation_name = "分享微信文章到微信朋友圈";
                var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/shareArticleToFriendCircle";    //分享微信文章到微信朋友圈
            } else if(operationId == "chatByNickName"){
                paramStr = "dicType=chatByNickName&dicStatus=0";
                var automationOperation_name = "根据微信昵称进行聊天";
                var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/chatByNickName";                //根据微信昵称进行聊天
            }
            //获取 昵称列表
            $.post(
                I18n.system_url_pre+"/automation/dic/getDicListByConditionForAdmin",
                paramStr,
                function(res) {
                    console.log("===============昵称列表 res==================");
                    console.log(res);
                    var data = res.data;
                    var nickNameArr = [];
                    nickNameArr["全部"] = [];
                    if (res.code == "0") {
                        for (i = 0; i < data.length; i++) {
                            var detailObj = data[i];
                            //拆分显示 dicRemark
                            try{
                                var dicRemarkStr = detailObj.dicRemark.replace(/\\/g, '');
                                var dicRemark_jsonObj = JSON.parse(dicRemarkStr);
                                // console.log("dicRemark_jsonObj.nickName = " + dicRemark_jsonObj.nickName);
                            } catch (e) {
                                // console.log("error nickName : " + dicRemark_jsonObj.nickName);
                                // console.log(e);
                            }
                            nickNameArr[dicRemark_jsonObj.nickName] = dicRemark_jsonObj.nickName;
                            nickNameArr["全部"].push(dicRemark_jsonObj.nickName);
                        }
                    } else {
                        layer.open({
                            title: I18n.system_tips ,
                            btn: [ I18n.system_ok ],
                            content: (data.message || I18n.system_opt_fail ),
                            icon: '2'
                        });
                        nickNameArr["cai_hong_wang"] = "cai_hong_wang";
                    }
                    console.log("nickNameArr");
                    console.log(nickNameArr);

                    var optionHtml_currentNickName = "";
                    for(var nickName in nickNameArr){
                        console.log(nickName + "----->>>>" + nickNameArr[nickName]);
                        if(nickName == "全部"){
                            $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form select[name='currentNickName']").val(nickName);
                            optionHtml_currentNickName = optionHtml_currentNickName + "<option currentNickName='"+nickNameArr[nickName]+"' value='"+nickName+"' selected>"+nickName+"</option>";
                        } else {
                            optionHtml_currentNickName = optionHtml_currentNickName + "<option currentNickName='"+nickNameArr[nickName]+"' value='"+nickName+"'>"+nickName+"</option>";
                        }
                    }

                    $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form select[name='currentNickName']").html(optionHtml_currentNickName);
                    $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form select[name='currentNickName']").trigger("change");       //通过触发事件，初始化 昵称列表
                    $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form select[name='currentDevice']").html(optionHtml_currentDevice);
                    $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form select[name='currentDevice']").trigger("change");       //通过触发事件，初始化 操作设备列表
                    $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form input[name='automationOperation_name']").val( automationOperation_name );  //操作名称
                    $("#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal .form input[name='automationOperation_url']").val( automationOperation_url );    //操作地址
                    $('#sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal').modal({backdrop: false, keyboard: false}).modal('show');
                }
            );
        }
    );
}


/**
 * 当前设备选择事件 for shareVideoNumToFriendCircleModal
 * @param obj
 */
function changeCurrentDeviceForShareVideoNumToFriendCircleModalFun(obj){
    var opotionName = $("#shareVideoNumToFriendCircleModal .form select[name='currentDevice']").children('option:selected').val();
    var deviceNameDesc = $("#shareVideoNumToFriendCircleModal .form select[name='currentDevice']").children('option:selected').attr("deviceNameDesc");
    console.log("changeCurrentDeviceForShareVideoNumToFriendCircleModalFun --->>> opotionName = " + opotionName + "--->>> deviceNameDesc = " + deviceNameDesc);

    var currentDeviceList = [];
    if("全部" == opotionName){        //全部的时候，是数组
        currentDeviceList = new Array();
        currentDeviceList = deviceNameDesc.split(",");
    } else {
        currentDeviceList.push(deviceNameDesc);
    }
    console.log("currentDeviceList");
    console.log(currentDeviceList);
    $("#shareVideoNumToFriendCircleModal .form textarea[name='currentDeviceListStr']").val( JSON.stringify(currentDeviceList,null,4) );    //操作设备列表
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

    var paramStr = "dicType=deviceNameListAndLocaltion&dicCode=HuaWeiListAndSendFriendCircleLocaltion";
    $.post(
        I18n.system_url_pre+"/automation/dic/getDicListByConditionForAdmin",
        paramStr,
        function(res) {
            console.log("===============res==================");
            console.log(res);
            var data = res.data;
            var deviceNameDescArr = [];
            if (res.code == "0") {
                for (i = 0; i < data.length; i++) {
                    var detailObj = data[i];
                    //拆分显示 dicRemark
                    var dicRemark_jsonObj = JSON.parse(detailObj.dicRemark);
                    var deviceNameList = dicRemark_jsonObj.deviceNameList;
                    console.log("deviceNameList");
                    console.log(deviceNameList);
                    deviceNameDescArr["全部"] = [];
                    for (j = 0; j < deviceNameList.length; j++) {
                        var deviceNameMap = deviceNameList[j];
                        deviceNameDescArr[deviceNameMap.deviceNameDesc] = deviceNameMap.deviceNameDesc;
                        deviceNameDescArr["全部"].push(deviceNameMap.deviceNameDesc);
                    }
                    console.log("deviceNameDescArr");
                    console.log(deviceNameDescArr);
                }
            } else {
                layer.open({
                    title: I18n.system_tips ,
                    btn: [ I18n.system_ok ],
                    content: (data.message || I18n.system_opt_fail ),
                    icon: '2'
                });
                deviceNameDescArr["华为Mate20Pro_22"] = "华为Mate20Pro_22";
            }

            //设置默认参数
            var automationOperation_name = "分享视频号到朋友圈";
            var automationOperation_url = I18n.system_automation_url_pre + "/automation/wx/shareVideoNumToFriendCircle";     //分享视频号到朋友圈
            var shareFendCircleCentent = "恩，怎么说呢，你还是自己看吧，文字描述已无力...";
            var index = 0;
            var optionHtml = "";
            for(deviceNameDesc in deviceNameDescArr){
                if(index == 0){
                    $("#relayTheWxMessageModal .form select[name='currentDevice']").val(deviceNameDesc);
                    optionHtml = optionHtml + "<option deviceNameDesc='"+deviceNameDescArr[deviceNameDesc]+"' value='"+deviceNameDesc+"' selected>"+deviceNameDesc+"</option>";
                } else {
                    optionHtml = optionHtml + "<option deviceNameDesc='"+deviceNameDescArr[deviceNameDesc]+"' value='"+deviceNameDesc+"'>"+deviceNameDesc+"</option>";
                }
                index++;
            }

            $("#shareVideoNumToFriendCircleModal .form select[name='currentDevice']").html(optionHtml);
            $("#shareVideoNumToFriendCircleModal .form select[name='currentDevice']").trigger("change");       //通过触发事件，初始化 操作设备列表
            $("#shareVideoNumToFriendCircleModal .form input[name='automationOperation_name']").val( automationOperation_name );  //操作名称
            $("#shareVideoNumToFriendCircleModal .form input[name='automationOperation_url']").val( automationOperation_url );    //操作地址
            $("#shareVideoNumToFriendCircleModal .form input[name='shareFendCircleCentent']").val( shareFendCircleCentent );    //分享视频号到朋友圈
            $('#shareVideoNumToFriendCircleModal').modal({backdrop: false, keyboard: false}).modal('show');
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

