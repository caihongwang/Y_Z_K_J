$(function() {
    // 阅读微信文章并点击广告
    $("#clickArticleAd").on('click', function(){
        //设置默认参数
        var automationOperation_name = "阅读微信文章并点击广告";
        var automationOperation_url = "http://192.168.43.181:9050/automation/wx/clickArticleAd";
        var automationOperation_param = "[\n" +
            "    \"2020-10-25 10\",\n" +
            "    \"2020-10-25 11\",\n" +
            "    \"2020-10-25 12\",\n" +
            "    \"2020-10-25 13\",\n" +
            "    \"2020-10-25 14\",\n" +
            "    \"2020-10-25 15\",\n" +
            "    \"2020-10-25 16\",\n" +
            "    \"2020-10-25 17\",\n" +
            "    \"2020-10-25 18\",\n" +
            "    \"2020-10-25 19\",\n" +
            "    \"2020-10-25 20\",\n" +
            "    \"2020-10-25 21\"\n" +
        "]";
        $("#automationOperationModal .form input[name='automationOperation_name']").val( automationOperation_name );  //操作名称
        $("#automationOperationModal .form input[name='automationOperation_url']").val( automationOperation_url );    //操作地址
        $("#automationOperationModal .form textarea[name='automationOperation_param']").val( JSON.stringify(JSON.parse(automationOperation_param),null,2) );//操作参数
        $('#automationOperationModal').modal({backdrop: false, keyboard: false}).modal('show');
    });

    // 同意好友请求
    $("#agreeToFriendRequest").on('click', function(){
        //设置默认参数
        var automationOperation_name = "同意好友请求";
        var automationOperation_url = "http://192.168.43.181:9050/automation/wx/agreeToFriendRequest";
        var automationOperation_param = "[\n" +
            "    \"2020-10-25 10\",\n" +
            "    \"2020-10-25 11\",\n" +
            "    \"2020-10-25 12\",\n" +
            "    \"2020-10-25 13\",\n" +
            "    \"2020-10-25 14\",\n" +
            "    \"2020-10-25 15\",\n" +
            "    \"2020-10-25 16\",\n" +
            "    \"2020-10-25 17\",\n" +
            "    \"2020-10-25 18\",\n" +
            "    \"2020-10-25 19\",\n" +
            "    \"2020-10-25 20\",\n" +
            "    \"2020-10-25 21\"\n" +
        "]";
        $("#automationOperationModal .form input[name='automationOperation_name']").val( automationOperation_name );  //操作名称
        $("#automationOperationModal .form input[name='automationOperation_url']").val( automationOperation_url );    //操作地址
        $("#automationOperationModal .form textarea[name='automationOperation_param']").val( JSON.stringify(JSON.parse(automationOperation_param),null,2) );//操作参数
        $('#automationOperationModal').modal({backdrop: false, keyboard: false}).modal('show');
    });

    // 将群保存到通讯录
    $("#saveToAddressBook").on('click', function(){
        //设置默认参数
        var automationOperation_name = "将群保存到通讯录";
        var automationOperation_url = "http://192.168.43.181:9050/automation/wx/saveToAddressBook";
        var automationOperation_param = "[\n" +
            "    \"2020-10-25 10\",\n" +
            "    \"2020-10-25 11\",\n" +
            "    \"2020-10-25 12\",\n" +
            "    \"2020-10-25 13\",\n" +
            "    \"2020-10-25 14\",\n" +
            "    \"2020-10-25 15\",\n" +
            "    \"2020-10-25 16\",\n" +
            "    \"2020-10-25 17\",\n" +
            "    \"2020-10-25 18\",\n" +
            "    \"2020-10-25 19\",\n" +
            "    \"2020-10-25 20\",\n" +
            "    \"2020-10-25 21\"\n" +
        "]";
        $("#automationOperationModal .form input[name='automationOperation_name']").val( automationOperation_name );  //操作名称
        $("#automationOperationModal .form input[name='automationOperation_url']").val( automationOperation_url );    //操作地址
        $("#automationOperationModal .form textarea[name='automationOperation_param']").val( JSON.stringify(JSON.parse(automationOperation_param),null,2) );//操作参数
        $('#automationOperationModal').modal({backdrop: false, keyboard: false}).modal('show');
    });

    // 点赞和评论朋友圈
    $("#praiseAndCommentFriendsCircle").on('click', function(){
        //设置默认参数
        var automationOperation_name = "点赞和评论朋友圈";
        var automationOperation_url = "http://192.168.43.181:9050/automation/wx/praiseAndCommentFriendsCircle";
        var automationOperation_param = "[\n" +
            "    \"2020-10-25 10\",\n" +
            "    \"2020-10-25 11\",\n" +
            "    \"2020-10-25 12\",\n" +
            "    \"2020-10-25 13\",\n" +
            "    \"2020-10-25 14\",\n" +
            "    \"2020-10-25 15\",\n" +
            "    \"2020-10-25 16\",\n" +
            "    \"2020-10-25 17\",\n" +
            "    \"2020-10-25 18\",\n" +
            "    \"2020-10-25 19\",\n" +
            "    \"2020-10-25 20\",\n" +
            "    \"2020-10-25 21\"\n" +
        "]";
        $("#automationOperationModal .form input[name='automationOperation_name']").val( automationOperation_name );  //操作名称
        $("#automationOperationModal .form input[name='automationOperation_url']").val( automationOperation_url );    //操作地址
        $("#automationOperationModal .form textarea[name='automationOperation_param']").val( JSON.stringify(JSON.parse(automationOperation_param),null,2) );//操作参数
        $('#automationOperationModal').modal({backdrop: false, keyboard: false}).modal('show');
    });

    // 同意进群
    $("#agreeToJoinTheGroup").on('click', function(){
        //设置默认参数
        var automationOperation_name = "同意进群";
        var automationOperation_url = "http://192.168.43.181:9050/automation/wx/agreeToJoinTheGroup";
        var automationOperation_param = "[\n" +
            "    \"2020-10-25 10\",\n" +
            "    \"2020-10-25 11\",\n" +
            "    \"2020-10-25 12\",\n" +
            "    \"2020-10-25 13\",\n" +
            "    \"2020-10-25 14\",\n" +
            "    \"2020-10-25 15\",\n" +
            "    \"2020-10-25 16\",\n" +
            "    \"2020-10-25 17\",\n" +
            "    \"2020-10-25 18\",\n" +
            "    \"2020-10-25 19\",\n" +
            "    \"2020-10-25 20\",\n" +
            "    \"2020-10-25 21\"\n" +
        "]";
        $("#automationOperationModal .form input[name='automationOperation_name']").val( automationOperation_name );  //操作名称
        $("#automationOperationModal .form input[name='automationOperation_url']").val( automationOperation_url );    //操作地址
        $("#automationOperationModal .form textarea[name='automationOperation_param']").val( JSON.stringify(JSON.parse(automationOperation_param),null,2) );//操作参数
        $('#automationOperationModal').modal({backdrop: false, keyboard: false}).modal('show');
    });

    // 发送朋友圈
    $("#sendFriendCircle").on('click', function(){
        //设置默认参数
        var automationOperation_name = "发送朋友圈";
        var automationOperation_url = "http://192.168.43.181:9050/automation/wx/sendFriendCircle";
        var automationOperation_param = "[\n" +
            "    \"2020-10-25 10\",\n" +
            "    \"2020-10-25 11\",\n" +
            "    \"2020-10-25 12\",\n" +
            "    \"2020-10-25 13\",\n" +
            "    \"2020-10-25 14\",\n" +
            "    \"2020-10-25 15\",\n" +
            "    \"2020-10-25 16\",\n" +
            "    \"2020-10-25 17\",\n" +
            "    \"2020-10-25 18\",\n" +
            "    \"2020-10-25 19\",\n" +
            "    \"2020-10-25 20\",\n" +
            "    \"2020-10-25 21\"\n" +
        "]";
        $("#automationOperationModal .form input[name='automationOperation_name']").val( automationOperation_name );  //操作名称
        $("#automationOperationModal .form input[name='automationOperation_url']").val( automationOperation_url );    //操作地址
        $("#automationOperationModal .form textarea[name='automationOperation_param']").val( JSON.stringify(JSON.parse(automationOperation_param),null,2) );//操作参数
        $('#automationOperationModal').modal({backdrop: false, keyboard: false}).modal('show');
    });

    // 根据微信昵称进行聊天
    $("#chatByNickName").on('click', function(){
        //设置默认参数
        var automationOperation_name = "根据微信昵称进行聊天";
        var automationOperation_url = "http://192.168.43.181:9050/automation/wx/chatByNickName";
        var automationOperation_param = "[\n" +
            "    \"2020-10-25 10\",\n" +
            "    \"2020-10-25 11\",\n" +
            "    \"2020-10-25 12\",\n" +
            "    \"2020-10-25 13\",\n" +
            "    \"2020-10-25 14\",\n" +
            "    \"2020-10-25 15\",\n" +
            "    \"2020-10-25 16\",\n" +
            "    \"2020-10-25 17\",\n" +
            "    \"2020-10-25 18\",\n" +
            "    \"2020-10-25 19\",\n" +
            "    \"2020-10-25 20\",\n" +
            "    \"2020-10-25 21\"\n" +
        "]";
        $("#automationOperationModal .form input[name='automationOperation_name']").val( automationOperation_name );  //操作名称
        $("#automationOperationModal .form input[name='automationOperation_url']").val( automationOperation_url );    //操作地址
        $("#automationOperationModal .form textarea[name='automationOperation_param']").val( JSON.stringify(JSON.parse(automationOperation_param),null,2) );//操作参数
        $('#automationOperationModal').modal({backdrop: false, keyboard: false}).modal('show');
    });

    // 分享微信文章到微信朋友圈
    $("#shareArticleToFriendCircle").on('click', function(){
        //设置默认参数
        var automationOperation_name = "分享微信文章到微信朋友圈";
        var automationOperation_url = "http://192.168.43.181:9050/automation/wx/shareArticleToFriendCircle";
        var automationOperation_param = "[\n" +
            "    \"2020-10-25 10\",\n" +
            "    \"2020-10-25 11\",\n" +
            "    \"2020-10-25 12\",\n" +
            "    \"2020-10-25 13\",\n" +
            "    \"2020-10-25 14\",\n" +
            "    \"2020-10-25 15\",\n" +
            "    \"2020-10-25 16\",\n" +
            "    \"2020-10-25 17\",\n" +
            "    \"2020-10-25 18\",\n" +
            "    \"2020-10-25 19\",\n" +
            "    \"2020-10-25 20\",\n" +
            "    \"2020-10-25 21\"\n" +
        "]";
        $("#automationOperationModal .form input[name='automationOperation_name']").val( automationOperation_name );  //操作名称
        $("#automationOperationModal .form input[name='automationOperation_url']").val( automationOperation_url );    //操作地址
        $("#automationOperationModal .form textarea[name='automationOperation_param']").val( JSON.stringify(JSON.parse(automationOperation_param),null,2) );//操作参数
        $('#automationOperationModal').modal({backdrop: false, keyboard: false}).modal('show');
    });

    // 通过V群添加其成员为好友
    $("#addGroupMembersAsFriends").on('click', function(){
        //设置默认参数
        var automationOperation_name = "通过V群添加其成员为好友";
        var automationOperation_url = "http://192.168.43.181:9050/automation/wx/addGroupMembersAsFriends";
        var automationOperation_param = "[\n" +
            "    \"2020-10-25 10\",\n" +
            "    \"2020-10-25 11\",\n" +
            "    \"2020-10-25 12\",\n" +
            "    \"2020-10-25 13\",\n" +
            "    \"2020-10-25 14\",\n" +
            "    \"2020-10-25 15\",\n" +
            "    \"2020-10-25 16\",\n" +
            "    \"2020-10-25 17\",\n" +
            "    \"2020-10-25 18\",\n" +
            "    \"2020-10-25 19\",\n" +
            "    \"2020-10-25 20\",\n" +
            "    \"2020-10-25 21\"\n" +
        "]";
        $("#automationOperationModal .form input[name='automationOperation_name']").val( automationOperation_name );  //操作名称
        $("#automationOperationModal .form input[name='automationOperation_url']").val( automationOperation_url );    //操作地址
        $("#automationOperationModal .form textarea[name='automationOperation_param']").val( JSON.stringify(JSON.parse(automationOperation_param),null,2) );//操作参数
        $('#automationOperationModal').modal({backdrop: false, keyboard: false}).modal('show');
    });

    // 转发微信消息
    $("#relayTheWxMessage").on('click', function(){
        //设置默认参数
        var automationOperation_name = "转发微信消息";
        var automationOperation_url = "http://192.168.43.181:9050/automation/wx/relayTheWxMessage";
        var automationOperation_param = "[\n" +
            "    \"2020-10-25 10\",\n" +
            "    \"2020-10-25 11\",\n" +
            "    \"2020-10-25 12\",\n" +
            "    \"2020-10-25 13\",\n" +
            "    \"2020-10-25 14\",\n" +
            "    \"2020-10-25 15\",\n" +
            "    \"2020-10-25 16\",\n" +
            "    \"2020-10-25 17\",\n" +
            "    \"2020-10-25 18\",\n" +
            "    \"2020-10-25 19\",\n" +
            "    \"2020-10-25 20\",\n" +
            "    \"2020-10-25 21\"\n" +
        "]";
        $("#automationOperationModal .form input[name='automationOperation_name']").val( automationOperation_name );  //操作名称
        $("#automationOperationModal .form input[name='automationOperation_url']").val( automationOperation_url );    //操作地址
        $("#automationOperationModal .form textarea[name='automationOperation_param']").val( JSON.stringify(JSON.parse(automationOperation_param),null,2) );//操作参数
        $('#automationOperationModal').modal({backdrop: false, keyboard: false}).modal('show');
    });

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
            });
        }
    });

});
