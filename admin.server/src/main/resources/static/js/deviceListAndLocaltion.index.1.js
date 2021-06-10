$(function () {
    // init date tables
    var jobTable = $("#job_list").dataTable({
        "deferRender": true,
        "processing": true,
        "serverSide": true,
        "scrollX": true,
        rules : {
            dicType: {
                required: true
            },
            dicName: {
                required: true
            },
            dicCode: {
                required: true
            },
            dicStatus: {
                required: true
            },
            deviceLocaltion: {
                required: true
            },
            deviceNameList: {
                required: true
            },
            dicRemark: {
                required: true
            }
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
            deviceLocaltion : {
                required : I18n.system_please_input + '设备坐标'
            },
            deviceNameList : {
                required : I18n.system_please_input + '设备列表'
            },
            dicRemark : {
                required : I18n.system_please_input + '业务详情'
            }
        },
        "ajax": {
            url: I18n.system_url_pre + "/automation/dic/getDicListByConditionForAdmin",
            type: "post",
            data: function (d) {			//参数
                var obj = {};
                obj.start = d.start;
                obj.size = 10;
                obj.dicType = $('#dicType').children('option:selected').val();
                obj.dicCode = $('#dicCode').val();
                obj.dicName = $('#dicName').val();
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
                "width": 80,
                "render": function (data, type, row) {
                    return data;
                }
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
                "width": 100,
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
        clearFormValue();           //清空表单信息
        $("#addOrUpdateModal .form").attr("operatorType", "add");                           //操作类型，新增或者删除
        $("#addOrUpdateModal .form textarea[name='dicRemark']").attr("readonly", true);     //字典详情
        $("#addOrUpdateModal h4[class='modal-title']").html($("#addOrUpdateModal h4[class='modal-title']").html().replace("更新", "新增")); //变更 modal-title

        // show
        $('#addOrUpdateModal').modal({backdrop: false, keyboard: false}).modal('show');
    });

    // update 更新
    $("#job_list").on('click', '.update', function () {
        clearFormValue();           //清空表单信息
        $("#addOrUpdateModal .form").attr("operatorType", "update");                             //操作类型，新增或者删除
        $("#addOrUpdateModal .form input[name='dicName']").attr("readonly", true);               //业务名称
        $("#addOrUpdateModal .form input[name='dicCode']").attr("readonly", true);               //业务编码
        $("#addOrUpdateModal .form textarea[name='dicRemark']").attr("readonly", true);          //字典详情
        $("#addOrUpdateModal h4[class='modal-title']").html($("#addOrUpdateModal h4[class='modal-title']").html().replace("新增", "更新")); //变更 modal-title

        var id = $(this).parents('ul').attr("_id");
        var row = tableData['key' + id];
        console.log(row);
        console.log("row.dicType = " + row.dicType);
        console.log("row.dicName = " + row.dicName);
        console.log("row.dicStatus = " + row.dicStatus);
        console.log("row.dicRemark = " + row.dicRemark);
        $("#addOrUpdateModal .form input[name='id']").val(row.id);
        $("#addOrUpdateModal .form select[name='dicType'] option[value=" + row.dicType + "]").prop('selected', true);    //业务类型
        $("#addOrUpdateModal .form input[name='dicCode']").val(row.dicCode);                                             //业务编码
        $("#addOrUpdateModal .form input[name='dicName']").val(row.dicName);                                             //业务名称
        $("#addOrUpdateModal .form select[name='dicStatus'] option[value=" + row.dicStatus + "]").prop('selected', true);//业务状态
        // 拆分显示 dicRemark
        var dicRemark_jsonObj = JSON.parse(row.dicRemark);
        var deviceLocaltion = dicRemark_jsonObj.deviceLocaltion;
        try {
            // deviceLocaltion = deviceLocaltion.replace(/\\/g, '');
            $("#addOrUpdateModal .form textarea[name='deviceLocaltion']").val(JSON.stringify(deviceLocaltion, null, 4));//设备坐标
        } catch (e) {
            $("#addOrUpdateModal .form textarea[name='deviceLocaltion']").val(deviceLocaltion);//设备坐标
        }
        var deviceNameList = dicRemark_jsonObj.deviceNameList;
        try {
            // deviceNameList = deviceNameList.replace(/\\/g, '');
            $("#addOrUpdateModal .form textarea[name='deviceNameList']").val(JSON.stringify(deviceNameList, null, 4));//设备列表
        } catch (e) {
            $("#addOrUpdateModal .form textarea[name='deviceNameList']").val(deviceNameList);//设备列表
        }
        try {
            $("#addOrUpdateModal .form textarea[name='dicRemark']").val(JSON.stringify(JSON.parse(row.dicRemark), null, 2));//业务详情
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

        layer.confirm("确定要删除吗？一旦删除后需要手动去数据库维护!!", {
            icon: 3,
            title: I18n.system_tips,
            btn: [I18n.system_ok, I18n.system_cancel]
        }, function (index) {
            layer.close(index);
            $.post(
                I18n.system_url_pre + "/automation/dic/deleteDicForAdmin",
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

    var addOrUpdateModalValidate = $("#addOrUpdateModal .form").validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: true,
        rules : {
            dicType: {
                required: true
            },
            dicName: {
                required: true
            },
            dicCode: {
                required: true
            },
            dicStatus: {
                required: true
            },
            deviceLocaltion: {
                required: true
            },
            deviceNameList: {
                required: true
            },
            dicRemark: {
                required: true
            }
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
            deviceLocaltion : {
                required : I18n.system_please_input + '设备坐标'
            },
            deviceNameList : {
                required : I18n.system_please_input + '设备列表'
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
            var operatorType = $("#addOrUpdateModal .form").attr("operatorType");
            console.log("operatorType = " + operatorType);
            var operatorUrl;
            if ("add" == operatorType) {
                operatorUrl = I18n.system_url_pre + "/automation/dic/addDicForAdmin";
            } else if ("update" == operatorType) {
                operatorUrl = I18n.system_url_pre + "/automation/dic/updateDicForAdmin";
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
 * 清空表单信息
 */
function clearFormValue() {
    //清空信息
    $("#addOrUpdateModal .form input[name='dicType']").val("");           //字典类型
    $("#addOrUpdateModal .form input[name='dicCode']").val("");           //字典编码
    $("#addOrUpdateModal .form input[name='dicName']").val("");           //字典名称
    $("#addOrUpdateModal .form select[name='dicStatus'] option[value=0]").prop('selected', true); //业务状态，默认正常
    $("#addOrUpdateModal .form textarea[name='deviceLocaltion']").val("");//设备坐标
    $("#addOrUpdateModal .form textarea[name='deviceNameList']").val("");//设备列表
    $("#addOrUpdateModal .form textarea[name='dicRemark']").val("");       //字典详情
    //删除只读属性
    $("#addOrUpdateModal .form input[name='dicType']").removeAttr("readonly");           //字典类型
    $("#addOrUpdateModal .form input[name='dicCode']").removeAttr("readonly");           //字典编码
    $("#addOrUpdateModal .form input[name='dicName']").removeAttr("readonly");           //字典名称
    $("#addOrUpdateModal .form input[name='dicStatus']").removeAttr("readonly");         //字典状态
    $("#addOrUpdateModal .form textarea[name='deviceLocaltion']").removeAttr("readonly");//设备坐标
    $("#addOrUpdateModal .form textarea[name='deviceNameList']").removeAttr("readonly");//设备列表
    $("#addOrUpdateModal .form textarea[name='dicRemark']").removeAttr("readonly");       //字典详情
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
    //设备坐标
    var deviceLocaltion = $("#addOrUpdateModal .form textarea[name='deviceLocaltion']").val();
    if (!isNull(deviceLocaltion)) {
        try {
            dicRemark_jsonObj["deviceLocaltion"] = JSON.parse(deviceLocaltion);
        } catch (e) {
            dicRemark_jsonObj["deviceLocaltion"] = deviceLocaltion;
        }
    }
    //设备列表
    var deviceNameList = $("#addOrUpdateModal .form textarea[name='deviceNameList']").val();
    if (!isNull(deviceNameList)) {
        try {
            dicRemark_jsonObj["deviceNameList"] = JSON.parse(deviceNameList);
        } catch (e) {
            dicRemark_jsonObj["deviceNameList"] = deviceNameList;
        }
    }
    console.log("JSON.stringify(dicRemark_jsonObj, null, 4) = " + JSON.stringify(dicRemark_jsonObj, null, 4));
    console.log(dicRemark_jsonObj);
    //业务详情
    $("#addOrUpdateModal .form textarea[name='dicRemark']").val(JSON.stringify(dicRemark_jsonObj, null, 4));
}
