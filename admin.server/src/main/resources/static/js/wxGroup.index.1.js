$(function () {
    // 初始化 操作设备列表 select 的 option 选项
    loadCurrentDeviceSelect();

    // init date tables
    var jobTable = $("#job_list").dataTable({
        "deferRender": true,
        "processing": true,
        "serverSide": true,
        "scrollX": true,
        "ajax": {
            url: I18n.system_url_pre+"/automation/dic/getDicListByConditionForAdmin",
            type: "post",
            data: function (d) {			//参数
                var obj = {};
                obj.start = d.start;
                obj.size = 10;
                obj.dicType = $('#dicType').children('option:selected').val();
                obj.dicCode = $('#dicCode').val();
                obj.dicName = $('#dicName').children('option:selected').val();
                obj.dicStatus = $('#dicStatus').children('option:selected').val();
                return obj;
            }
        },
        "searching": false,
        "ordering": false,
        "columns": [
            {
                "data": I18n.system_opt,
                "bSortable": false,
                "visible": true,
                "width": 100,
                "render": function (data, type, row) {
                    return function () {
                        tableData['key' + row.id] = row;
                        var html = '<div class="btn-group">\n' +
                            '     <button type="button" class="btn btn-primary btn-sm">' + I18n.system_opt + '</button>\n' +
                            '     <button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown">\n' +
                            '       <span class="caret"></span>\n' +
                            '       <span class="sr-only">Toggle Dropdown</span>\n' +
                            '     </button>\n' +
                            '     <ul class="dropdown-menu" role="menu" _id="' + row.id + '" >\n' +
                            '       <li><a href="javascript:void(0);" class="update" >更新</a></li>\n' +
                            '       <li><a href="javascript:void(0);" class="delete" >删除</a></li>\n' +
                            '     </ul>\n' +
                            '   </div>';
                        return html;
                    };
                }
            }, {
                "data": 'id',
                "bSortable": false,
                "visible": true,
                "width": 80
            }, {
                "data": 'dicType',
                "bSortable": false,
                "visible": true,
                "width": 200
            }, {
                "data": 'dicCode',
                "bSortable": false,
                "visible": true,
                "width": 200
            }, {
                "data": 'dicName',
                "bSortable": false,
                "visible": true,
                "width": 300
            }, {
                "data": 'dicRemark',
                "bSortable": false,
                "visible": true,
                "width": 400,
                "render": function (data, type, row) {
                    if (data.length >= 300) {
                        data = data.substr(0, 300) + "...";
                        return data;
                    } else {
                        return data;
                    }
                }
            }, {
                "data": 'dicStatus',
                "bSortable": false,
                "visible": true,
                "width": 150,
                "render": function (data, type, row) {
                    if (data == "0") {
                        return "正常";
                    } else if (data == "1") {
                        return "删除";
                    } else {
                        return "未知";
                    }
                }
            }
        ],
        "language": {
            "sProcessing": I18n.dataTable_sProcessing,
            "sLengthMenu": I18n.dataTable_sLengthMenu,
            "sZeroRecords": I18n.dataTable_sZeroRecords,
            "sInfo": I18n.dataTable_sInfo,
            "sInfoEmpty": I18n.dataTable_sInfoEmpty,
            "sInfoFiltered": I18n.dataTable_sInfoFiltered,
            "sInfoPostFix": "",
            "sSearch": I18n.dataTable_sSearch,
            "sUrl": "",
            "sEmptyTable": I18n.dataTable_sEmptyTable,
            "sLoadingRecords": I18n.dataTable_sLoadingRecords,
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": I18n.dataTable_sFirst,
                "sPrevious": I18n.dataTable_sPrevious,
                "sNext": I18n.dataTable_sNext,
                "sLast": I18n.dataTable_sLast
            },
            "oAria": {
                "sSortAscending": I18n.dataTable_sSortAscending,
                "sSortDescending": I18n.dataTable_sSortDescending
            }
        }
    });

    // table data
    var tableData = {};

    // search btn 搜索
    $('#searchBtn').on('click', function () {
        jobTable.fnDraw();
    });

    // add 新增
    $(".add").click(function () {
        var operationCode = "add";
        clearFormValue(operationCode);      //清空表单信息，变更弹窗的显示内容

        // show
        $('#addOrUpdateModal').modal({backdrop: false, keyboard: false}).modal('show');
    });

    // update 更新
    $("#job_list").on('click', '.update', function () {
        var operationCode = "update";
        clearFormValue(operationCode);   //清空表单信息，变更弹窗的显示内容

        //对表单进行初始化赋值
        var id = $(this).parents('ul').attr("_id");
        var row = tableData['key' + id];
        console.log(row);
        console.log("row.dicType = " + row.dicType);
        console.log("row.dicCode = " + row.dicCode);
        console.log("row.dicName = " + row.dicName);
        console.log("row.dicStatus = " + row.dicStatus);
        console.log("row.dicRemark = " + row.dicRemark);
        $("#addOrUpdateModal .form input[name='id']").val(row.id);
        $("#addOrUpdateModal .form select[name='dicType'] option[value=" + row.dicType + "]").prop('selected', true);     //业务类型
        $("#addOrUpdateModal .form select[name='dicName'] option[value=" + row.dicName + "]").prop('selected', true);     //业务名称
        $("#addOrUpdateModal .form select[name='dicStatus'] option[value=" + row.dicStatus + "]").prop('selected', true); //业务状态
        console.log("更新中，触发 targetDeviceNameDesc .....")
        //拆分显示 dicRemark
        var dicRemark_jsonObj = JSON.parse(row.dicRemark);
        $("#addOrUpdateModal .form select[name='targetDeviceNameDesc']").val(row.dicCode);
        $("#addOrUpdateModal .form select[name='nickName']").html("<option value='"+dicRemark_jsonObj.nickName+"' selected>群名："+dicRemark_jsonObj.nickName+"</option>");
        $("#addOrUpdateModal .form select[name='nickName']").trigger("change");             //目标微信群昵称
        $("#addOrUpdateModal .form input[name='targetDeviceNameDesc']").val(dicRemark_jsonObj.targetDeviceNameDesc);    //目标设备描述
        $("#addOrUpdateModal .form input[name='startAddFrirndTotalNumStr']").val(dicRemark_jsonObj.startAddFrirndTotalNumStr);   //添加好友的起始位置
        $("#addOrUpdateModal .form input[name='addFrirndTotalNumStr']").val(dicRemark_jsonObj.addFrirndTotalNumStr);    //每次成功添加群成员数量
        // $("#addOrUpdateModal .form input[name='deviceNameDesc']").val(dicRemark_jsonObj.deviceNameDesc);                //设备描述
        var groupMembersMapStr = dicRemark_jsonObj.groupMembersMapStr;
        try {
            groupMembersMapStr = groupMembersMapStr.replace(/\\/g, '');
            $("#addOrUpdateModal .form textarea[name='groupMembersMapStr']").val(JSON.stringify(JSON.parse(groupMembersMapStr), null, 4));//群成员信息
        } catch (e) {
            $("#addOrUpdateModal .form textarea[name='groupMembersMapStr']").val(groupMembersMapStr);//群成员信息
        }
        var groupNickNameMapStr = dicRemark_jsonObj.groupNickNameMapStr;
        try {
            groupNickNameMapStr = groupNickNameMapStr.replace(/\\/g, '');
            $("#addOrUpdateModal .form textarea[name='groupNickNameMapStr']").val(JSON.stringify(JSON.parse(groupNickNameMapStr), null, 4));//群昵称列表信息
        } catch (e) {
            $("#addOrUpdateModal .form textarea[name='groupNickNameMapStr']").val(groupNickNameMapStr);//群昵称列表信息
        }

        try {
            $("#addOrUpdateModal .form textarea[name='dicRemark']").val(JSON.stringify(JSON.parse(row.dicRemark), null, 4));//业务详情
        } catch (e) {
            $("#addOrUpdateModal .form textarea[name='dicRemark']").val(row.dicRemark);//业务详情
        }

        // show
        $('#addOrUpdateModal').modal({backdrop: false, keyboard: false}).modal('show');
    });

    // delete 删除
    $("#job_list").on('click', '.delete', function () {
        var id = $(this).parents('ul').attr("_id");
        var row = tableData['key' + id];
        var paramStr = "id=" + row.id;

        layer.confirm("确定要删除吗？一旦删除后需要手动去数据库维护，再想想!!", {
            icon: 3,
            title: I18n.system_tips,
            btn: [I18n.system_ok, I18n.system_cancel]
        }, function (index) {
            layer.close(index);
            $.post(
                I18n.system_url_pre+"/automation/dic/deleteDicForAdmin",
                paramStr,
                function (data, status) {
                    if (data.code == "0") {
                        $('#addOrUpdateModal').modal('hide');
                        layer.open({
                            title: I18n.system_tips,
                            btn: [I18n.system_ok],
                            content: "删除成功",
                            icon: '1',
                            end: function (layero, index) {
                                jobTable.fnDraw();
                            }
                        });
                    } else {
                        layer.open({
                            title: I18n.system_tips,
                            btn: [I18n.system_ok],
                            content: (data.msg || "删除失败"),
                            icon: '2'
                        });
                    }
                }
            );
        });
    });

    //表单提交 For 添加群成员为好友的V群
    var addOrUpdateModalValidate = $("#addOrUpdateModal .form").validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: true,
        rules : {
            dicType : {
                required : true
            },
            dicName : {
                required : true
            },
            dicCode : {
                required : true
            },
            dicStatus : {
                required : true
            },
            targetDeviceNameDesc : {
                required : true
            },
            startAddFrirndTotalNumStr : {
                digits: true
            },
            nickName : {
                required : true
            },
            addFrirndTotalNumStr : {
                digits: true
            },
            dicRemark : {
                required : true
            },
        },
        messages : {
            dicType : {
                required : I18n.system_please_input + '业务类型'
            },
            dicName : {
                required : I18n.system_please_input + '业务名称'
            },
            dicCode : {
                required : I18n.system_please_input + '业务编码'
            },
            dicStatus : {
                required : I18n.system_please_input + '业务状态'
            },
            targetDeviceNameDesc : {
                required : I18n.system_please_input + '目标设备描述'
            },
            startAddFrirndTotalNumStr : {
                digits: I18n.system_please_input + I18n.system_digits + ' For 添加好友的起始位置'
            },
            nickName : {
                required : I18n.system_please_input + '目标微信群昵称'
            },
            addFrirndTotalNumStr : {
                digits: I18n.system_please_input + I18n.system_digits + ' For 每次成功添加群成员数量'
            },
            dicRemark : {
                required : I18n.system_please_input + '业务详情'
            }
        },
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        success: function (label) {
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        },
        errorPlacement: function (error, element) {
            element.parent('div').append(error);
        },
        submitHandler: function (form) {
            var operationCode = $("#addOrUpdateModal .form").attr("operationCode");
            console.log("operationCode = " + operationCode);
            var operatorUrl;
            if ("add" == operationCode) {
                operatorUrl = I18n.system_url_pre+"/automation/dic/addDicForAdmin";
            } else if ("update" == operationCode) {
                operatorUrl = I18n.system_url_pre+"/automation/dic/updateDicForAdmin";
            }
            if (isNull(operatorUrl)) {
                layer.open({
                    title: I18n.system_tips,
                    btn: [I18n.system_ok],
                    content: '终止操作行为，当前操作未明确是新增还是操作，请检查.',
                    icon: '2'
                });
                return;
            }
            //发起请求
            $.post(
                operatorUrl,
                $("#addOrUpdateModal .form").serialize(),
                function (data, status) {
                    if (data.code == "0") {
                        $('#addOrUpdateModal').modal('hide');
                        layer.open({
                            title: I18n.system_tips,
                            btn: [I18n.system_ok],
                            content: I18n.system_opt_suc,
                            icon: '1',
                            end: function (layero, index) {
                                jobTable.fnDraw();
                            }
                        });
                    } else {
                        layer.open({
                            title: I18n.system_tips,
                            btn: [I18n.system_ok],
                            content: (data.msg || I18n.system_opt_fail),
                            icon: '2'
                        });
                    }
                }
            );
        }
    });

});

/**
 * 清空表单信息，变更弹窗的显示内容
 */
function clearFormValue(operationCode) {
    //清空信息
    $("#addOrUpdateModal .form input[name='dicType']").val("");                                     //字典类型
    $("#addOrUpdateModal .form input[name='dicCode']").val("");                                     //字典编码
    $("#addOrUpdateModal .form input[name='dicName']").val("");                                     //字典名称
    $("#addOrUpdateModal .form select[name='dicStatus'] option[value=0]").prop('selected', true);   //业务状态，默认正常
    $("#addOrUpdateModal .form select[name='targetDeviceNameDesc']").val("");                       //目标设备描述
    $("#addOrUpdateModal .form input[name='startAddFrirndTotalNumStr']").val("");                   //添加好友的起始位置
    $("#addOrUpdateModal .form select[name='nickName']").val("");                                   //目标微信群昵称
    $("#addOrUpdateModal .form input[name='addFrirndTotalNumStr']").val("");                        //每次成功添加群成员数量
    $("#addOrUpdateModal .form textarea[name='groupMembersMapStr']").val("");                       //群成员信息
    $("#addOrUpdateModal .form textarea[name='groupNickNameMapStr']").val("");                      //群昵称列表信息
    $("#addOrUpdateModal .form textarea[name='dicRemark']").val("");                                //字典详情
    //删除只读属性
    $("#addOrUpdateModal .form input[name='dicType']").removeAttr("readonly");                      //字典类型
    $("#addOrUpdateModal .form input[name='dicCode']").removeAttr("readonly");                      //字典编码
    $("#addOrUpdateModal .form input[name='dicName']").removeAttr("readonly");                      //字典名称
    $("#addOrUpdateModal .form input[name='dicStatus']").removeAttr("readonly");                    //字典状态
    $("#addOrUpdateModal .form select[name='targetDeviceNameDesc']").removeAttr("disabled");        //目标设备描述
    $("#addOrUpdateModal .form input[name='startAddFrirndTotalNumStr']").removeAttr("readonly");    //添加好友的起始位置
    $("#addOrUpdateModal .form select[name='nickName']").removeAttr("disabled");                    //目标微信群昵称
    $("#addOrUpdateModal .form input[name='addFrirndTotalNumStr']").removeAttr("readonly");         //每次成功添加群成员数量
    $("#addOrUpdateModal .form textarea[name='groupMembersMapStr']").removeAttr("readonly");        //群成员信息
    $("#addOrUpdateModal .form textarea[name='groupNickNameMapStr']").removeAttr("readonly");       //群昵称列表信息
    $("#addOrUpdateModal .form textarea[name='dicRemark']").removeAttr("readonly");                 //字典详情

    //切换 添加群成员为好友的V群 或者 设备对应的所有群信息 相应的
    var dicType = $('#dicType').children('option:selected').val();
    console.log("dicType = " + dicType);
    if(dicType == "addGroupMembersAsFriends"){
        $("#addOrUpdateModal .addGroupMembersAsFriends").css("display", "block");
        $("#addOrUpdateModal .deviceNameDescToGroupNameMapStr").css("display", "none");
    } else if(dicType == "deviceNameDescToGroupNameMapStr"){
        $("#addOrUpdateModal .addGroupMembersAsFriends").css("display", "none");
        $("#addOrUpdateModal .deviceNameDescToGroupNameMapStr").css("display", "block");
    }

    //切换 弹窗中的 addOrUpdateModal 的 select
    $("#addOrUpdateModal .form select[name='dicType']").val(dicType);
    $("#addOrUpdateModal .form select[name='dicType']").trigger("change");

    //更新弹窗的Title
    var operationName = "新增";
    if(operationCode == "add"){
        operationName = "新增";
        $("#addOrUpdateModal .form").attr("operationCode", operationCode);                       //操作类型，新增或者删除
        $("#addOrUpdateModal .form input[name='dicCode']").attr("readonly", true);               //业务编码
        $("#addOrUpdateModal .form textarea[name='dicRemark']").attr("readonly", true);          //字典详情
        $("#addOrUpdateModal .form textarea[name='groupMembersMapStr']").attr("readonly", true); //群成员信息

        var targetDeviceNameDesc = $("#addOrUpdateModal .form select[name='targetDeviceNameDesc']").find("option").first().val();
        $("#addOrUpdateModal .form select[name='targetDeviceNameDesc']").val(targetDeviceNameDesc);
        $("#addOrUpdateModal .form select[name='targetDeviceNameDesc']").trigger("change");       //通过触发事件，初始化 业务编码

        $("#addOrUpdateModal .form select[name='nickName']").val(nickName);
        // changeTargetDeviceNameDescForAddOrUpdateModalFun(dicRemark_jsonObj.nickName);             //通过触发targetDeviceNameDesc的事件函数，初始化 目标设备描述，业务编码，目标微信群昵称

        $("#addOrUpdateModal h4[class='modal-title']").html($("#addOrUpdateModal h4[class='modal-title']").html().replace("更新", "新增")); //变更 modal-title
    } else if(operationCode == "update"){
        operationName = "更新";
        $("#addOrUpdateModal .form").attr("operationCode", "update");                            //操作类型，新增或者删除
        $("#addOrUpdateModal .form input[name='dicCode']").attr("readonly", true);               //业务编码
        $("#addOrUpdateModal .form select[name='targetDeviceNameDesc']").attr("disabled", true); //目标设备描述
        $("#addOrUpdateModal .form select[name='nickName']").attr("disabled", true);             //目标微信群昵称
        $("#addOrUpdateModal .form textarea[name='dicRemark']").attr("readonly", true);          //字典详情
        $("#addOrUpdateModal h4[class='modal-title']").html($("#addOrUpdateModal h4[class='modal-title']").html().replace("新增", "更新")); //变更 modal-title
    }
    var operationTitle = operationName + "【" + $('#dicType').children('option:selected').html() + "】信息";
    $("#addOrUpdateModal h4[class='modal-title']").html(operationTitle);
}

/**
 * 微信群类别选择事件 for QueryCondition
 * @param obj
 */
function changeDicTypeForQueryConditionFun(obj){
    console.log("changeDicTypeForQueryConditionFun --->>> 被点击...");
    var dicType = $("#dicType").children('option:selected').val();
    console.log("dicType = " + dicType);
    if(dicType == "addGroupMembersAsFriends"){
        $("#dicName").html("<option id='addGroupMembersAsFriends' value='添加群成员为好友的V群' selected>添加群成员为好友的V群</option>");
    } else if(dicType == "deviceNameDescToGroupNameMapStr"){
        $("#dicName").html("<option id='deviceNameDescToGroupNameMapStr' value='设备对应的所有群信息' selected>设备对应的所有群信息</option>");
    }
}

/**
 * 微信群类别选择事件 for addOrUpdateModal
 * @param obj
 */
function changeDicTypeForAddOrUpdateModalFun(obj){
    console.log("changeDicTypeForAddOrUpdateModalFun --->>> 被点击...");
    var dicType = $("#addOrUpdateModal .form select[name='dicType']").children('option:selected').val();
    console.log("dicType = " + dicType);
    if(dicType == "addGroupMembersAsFriends"){
        $("#addOrUpdateModal .addGroupMembersAsFriends").css("display", "block");
        $("#addOrUpdateModal .deviceNameDescToGroupNameMapStr").css("display", "none");
        $("#addOrUpdateModal .form select[name='dicName']").html("<option id='addGroupMembersAsFriends' value='添加群成员为好友的V群' selected>添加群成员为好友的V群</option>");
    } else if(dicType == "deviceNameDescToGroupNameMapStr"){
        $("#addOrUpdateModal .addGroupMembersAsFriends").css("display", "none");
        $("#addOrUpdateModal .deviceNameDescToGroupNameMapStr").css("display", "block");
        $("#addOrUpdateModal .form select[name='dicName']").html("<option value='设备对应的所有群信息' selected>设备对应的所有群信息</option>");
    }
}

/**
 * 目标设备描述 选择时间 for addOrUpdateModal
 */
function changeTargetDeviceNameDescForAddOrUpdateModalFun(nickName) {
    console.log("changeTargetDeviceNameDescForAddOrUpdateModalFun --->>> 被点击...");
    console.log("nickName = " + nickName);
    var targetDeviceNameDesc = $("#addOrUpdateModal .form select[name='targetDeviceNameDesc']").children('option:selected').val();
    console.log("targetDeviceNameDesc = " + targetDeviceNameDesc);
    $("#addOrUpdateModal .form input[name='dicCode']").val(targetDeviceNameDesc);

    //设备对应的所有群信息 无需 再次根据targetDeviceNameDesc获取对应的群信息
    var dicType = $("#addOrUpdateModal .form select[name='dicType']").children('option:selected').val();
    if(dicType == "deviceNameDescToGroupNameMapStr"){
        return;
    }
    var paramStr = "deviceNameDesc="+targetDeviceNameDesc;      //暂时使用 添加群成员为好友的V群 获取 所有设备列表
    $.post(
        I18n.system_url_pre+"/automation/dic/getGroupNickNameListByDeviceNameDescForAdmin",
        paramStr,
        function(res) {
            console.log("===============res==================");
            console.log(res);
            var groupNickNameArr = res.data;
            if (res.code == "0") {

            } else {
                groupNickNameArr["华为Mate20Pro"] = "群名：油站科技-内部交流群   ------默认------>>>   群成员总数：24";
            }
            console.log("===============groupNickNameArr==================");
            console.log(groupNickNameArr);
            var index = 0;
            var optionHtml = "";
            for(groupNickName in groupNickNameArr){
                var optionValue =  "群名：" + groupNickName + "   ------>>>   群成员总数：" + groupNickNameArr[groupNickName];
                if(index == 0){
                    optionHtml = optionHtml + "<option value='"+groupNickName+"' selected>"+optionValue+"</option>";
                } else {
                    optionHtml = optionHtml + "<option value='"+groupNickName+"'>"+optionValue+"</option>";
                }
                index++;
            }
            $("#addOrUpdateModal .form select[name='nickName']").html(optionHtml);
            if(!isNull(nickName)){
                $("#addOrUpdateModal .form select[name='nickName']").val(nickName);
                $("#addOrUpdateModal .form select[name='nickName']").trigger("change");
            }
        }
    );
}

/**
 * 整理 dicRemark 的 val
 */
function changeDicRemark() {
    console.log("changeDicRemark 被执行了...");
    //整理 dicRemark
    var dicRemark_jsonObj = {};
    try {
        dicRemark_jsonObj = JSON.parse($("#addOrUpdateModal .form textarea[name='dicRemark']").val());
    } catch (e) {
        dicRemark_jsonObj = dicRemark_jsonObj;
    }
    console.log(dicRemark_jsonObj);
    //-------------------添加群成员为好友的V群-------------------
    //添加好友的起始位置
    var startAddFrirndTotalNumStr = $("#addOrUpdateModal .form input[name='startAddFrirndTotalNumStr']").val();
    if (!isNull(startAddFrirndTotalNumStr)) {
        dicRemark_jsonObj["startAddFrirndTotalNumStr"] = startAddFrirndTotalNumStr;
    }
    //每次成功添加群成员数量
    var addFrirndTotalNumStr = $("#addOrUpdateModal .form input[name='addFrirndTotalNumStr']").val();
    if (!isNull(addFrirndTotalNumStr)) {
        dicRemark_jsonObj["addFrirndTotalNumStr"] = addFrirndTotalNumStr;
    }
    //群成员信息
    var groupMembersMapStr = $("#addOrUpdateModal .form textarea[name='groupMembersMapStr']").val();
    if (!isNull(groupMembersMapStr)) {
        dicRemark_jsonObj["groupMembersMapStr"] = groupMembersMapStr;
        //重新格式化显示 群成员信息
        try {
            groupMembersMapStr = groupMembersMapStr.replace(/\\/g, '');
            $("#addOrUpdateModal .form textarea[name='groupMembersMapStr']").val(JSON.stringify(JSON.parse(groupMembersMapStr), null, 4));
        } catch (e) {
            $("#addOrUpdateModal .form textarea[name='groupMembersMapStr']").val(groupMembersMapStr);
        }
    }
    //-------------------设备对应的所有群信息-------------------
    //目标微信群昵称
    var nickName = $("#addOrUpdateModal .form select[name='nickName']").children('option:selected').val();
    if (!isNull(nickName)) {
        dicRemark_jsonObj["nickName"] = nickName;
    }
    //群昵称列表信息
    var groupNickNameMapStr = $("#addOrUpdateModal .form textarea[name='groupNickNameMapStr']").val();
    if (!isNull(groupNickNameMapStr)) {
        dicRemark_jsonObj["groupNickNameMapStr"] = groupNickNameMapStr;
        //重新格式化显示 群昵称列表信息
        try {
            groupNickNameMapStr = groupNickNameMapStr.replace(/\\/g, '');
            $("#addOrUpdateModal .form textarea[name='groupNickNameMapStr']").val(JSON.stringify(JSON.parse(groupNickNameMapStr), null, 4));
        } catch (e) {
            $("#addOrUpdateModal .form textarea[name='groupNickNameMapStr']").val(groupMembersMapStr);
        }
    }
    //业务详情
    $("#addOrUpdateModal .form textarea[name='dicRemark']").val(JSON.stringify(dicRemark_jsonObj, null, 4));
}

/**
 * 初始化 目标设备描述 select 的 option 选项
 */
function loadCurrentDeviceSelect() {
    var paramStr = "dicType=deviceNameListAndLocaltion&dicCode=HuaWeiListAndSaveToAddressBookLocaltion";      //暂时使用 添加群成员为好友的V群 获取 所有设备列表
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
                    for (j = 0; j < deviceNameList.length; j++) {
                        var deviceNameObj = deviceNameList[j];
                        var deviceNameDesc = deviceNameObj.deviceNameDesc;
                        var deviceName = deviceNameObj.deviceName;
                        deviceNameDescArr[deviceNameDesc] = deviceName;
                    }
                }
            } else {
                layer.open({
                    title: I18n.system_tips ,
                    btn: [ I18n.system_ok ],
                    content: (data.message || I18n.system_opt_fail ),
                    icon: '2'
                });
                deviceNameDescArr["华为Mate20Pro"] = "S2D0219423001056";
            }
            console.log("===============deviceNameDescArr==================");
            console.log(deviceNameDescArr);
            var index = 0;
            var optionHtml = "";
            for(deviceNameDesc in deviceNameDescArr){
                if(index == 0){
                    optionHtml = optionHtml + "<option value='"+deviceNameDesc+"' selected>"+deviceNameDesc+"</option>";
                } else {
                    optionHtml = optionHtml + "<option value='"+deviceNameDesc+"'>"+deviceNameDesc+"</option>";
                }
                index++;
            }
            $("#addOrUpdateModal .form select[name='targetDeviceNameDesc']").html(optionHtml);
        }
    );
}