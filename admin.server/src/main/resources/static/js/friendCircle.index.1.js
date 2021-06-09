$(function() {
	// init date tables
	var jobTable = $("#job_list").dataTable({
		"deferRender": true,
		"processing" : true,
	    "serverSide": true,
        "scrollX": true,
		"ajax": {
			url: I18n.system_url_pre+"/automation/dic/getDicListByConditionForAdmin",
			type: "post",
	        data: function ( d ) {			//参数
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
                "visible" : true,
                "width": 100,
                "render": function (data, type, row) {
                    return function () {
                        tableData['key'+row.id] = row;
                        var html = '<div class="btn-group">\n' +
                            '     <button type="button" class="btn btn-primary btn-sm">'+ I18n.system_opt +'</button>\n' +
                            '     <button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown">\n' +
                            '       <span class="caret"></span>\n' +
                            '       <span class="sr-only">Toggle Dropdown</span>\n' +
                            '     </button>\n' +
                            '     <ul class="dropdown-menu" role="menu" _id="'+ row.id +'" >\n' +
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
                "visible" : true,
                "width": 80,
                "render": function ( data, type, row ) {
                    return data;
                }
            },{
                "data": 'dicType',
                "bSortable": false,
                "visible" : true,
                "width": 200
            },{
                "data": 'dicCode',
                "bSortable": false,
                "visible" : true,
                "width": 200
            },{
                "data": 'dicName',
                "bSortable": false,
                "visible" : true,
                "width": 300
            },{
                "data": 'dicRemark',
                "bSortable": false,
                "visible" : true,
                "width": 400
            },{
                "data": 'dicStatus',
                "bSortable": false,
                "visible" : true,
                "width": 100,
                "render": function ( data, type, row ) {
                    if(data == "0"){
                        return "正常";
                    } else if(data == "1"){
                        return "删除";
                    } else {
                        return "未知";
                    }
                }
            }
        ],
		"language" : {
			"sProcessing" : I18n.dataTable_sProcessing ,
			"sLengthMenu" : I18n.dataTable_sLengthMenu ,
			"sZeroRecords" : I18n.dataTable_sZeroRecords ,
			"sInfo" : I18n.dataTable_sInfo ,
			"sInfoEmpty" : I18n.dataTable_sInfoEmpty ,
			"sInfoFiltered" : I18n.dataTable_sInfoFiltered ,
			"sInfoPostFix" : "",
			"sSearch" : I18n.dataTable_sSearch ,
			"sUrl" : "",
			"sEmptyTable" : I18n.dataTable_sEmptyTable ,
			"sLoadingRecords" : I18n.dataTable_sLoadingRecords ,
			"sInfoThousands" : ",",
			"oPaginate" : {
				"sFirst" : I18n.dataTable_sFirst ,
				"sPrevious" : I18n.dataTable_sPrevious ,
				"sNext" : I18n.dataTable_sNext ,
				"sLast" : I18n.dataTable_sLast
			},
			"oAria" : {
				"sSortAscending" : I18n.dataTable_sSortAscending ,
				"sSortDescending" : I18n.dataTable_sSortDescending
			}
		}
	});

    // table data
    var tableData = {};

	// search btn 搜索
	$('#searchBtn').on('click', function(){
		jobTable.fnDraw();
	});

    // add 新增
    $(".add").click(function(){
        clearFormValue();           //清空表单信息
        var dicType = $('#dicType').children('option:selected').val();          //切换 发送朋友圈 或者 分享文章链接到朋友圈
        console.log("dicType = " + dicType);
        if(dicType == "sendFriendCircle"){
            $("#addOrupdateModal .sendFriendCircle").css("display", "block");
            $("#addOrupdateModal .shareArticleToFriendCircle").css("display", "none");
        } else if(dicType == "shareArticleToFriendCircle"){
            $("#addOrupdateModal .sendFriendCircle").css("display", "none");
            $("#addOrupdateModal .shareArticleToFriendCircle").css("display", "block");
        }
        $("#addOrupdateModal .form").attr("operatorType", "add");                           //操作类型，新增或者删除
        $("#addOrupdateModal .form input[name='dicCode']").attr("readonly", true);          //业务编码
        $("#addOrupdateModal .form textarea[name='dicRemark']").attr("readonly", true);     //字典详情
        $("#addOrupdateModal h4[class='modal-title']").html($("#addOrupdateModal h4[class='modal-title']").html().replace("更新", "新增")); //变更 modal-title

        $('#addOrupdateModal').modal({backdrop: false, keyboard: false}).modal('show');
    });
    // update 更新
    $("#job_list").on('click', '.update',function() {
        clearFormValue();           //清空表单信息
        $("#addOrupdateModal .form").attr("operatorType", "update");                             //操作类型，新增或者删除
        $("#addOrupdateModal .form input[name='dicCode']").attr("readonly", true);               //业务编码
        $("#addOrupdateModal .form input[name='nickName']").attr("readonly", true);              //目标微信群昵称
        $("#addOrupdateModal .form textarea[name='dicRemark']").attr("readonly", true);          //字典详情
        $("#addOrupdateModal h4[class='modal-title']").html($("#addOrupdateModal h4[class='modal-title']").html().replace("新增", "更新")); //变更 modal-title

        var id = $(this).parents('ul').attr("_id");
        var row = tableData['key'+id];
        console.log(row);
        console.log("row.dicType = " + row.dicType);
        console.log("row.dicName = " + row.dicName);
        console.log("row.dicStatus = " + row.dicStatus);
        console.log("row.dicRemark = " + row.dicRemark);
        if(row.dicType == "sendFriendCircle"){
            $("#addOrupdateModal .sendFriendCircle").css("display", "block");
            $("#addOrupdateModal .shareArticleToFriendCircle").css("display", "none");
        } else if(row.dicType == "shareArticleToFriendCircle"){
            $("#addOrupdateModal .sendFriendCircle").css("display", "none");
            $("#addOrupdateModal .shareArticleToFriendCircle").css("display", "block");
        }
        $("#addOrupdateModal .form input[name='id']").val( row.id );
        $("#addOrupdateModal .form select[name='dicType'] option[value=" + row.dicType + "]").prop('selected', true);    //业务类型
        $("#addOrupdateModal .form input[name='dicCode']").val( row.dicCode );                                           //微信昵称
        $("#addOrupdateModal .form select[name='dicName'] option[value="+ row.dicName + "]").prop('selected', true);     //业务方式
        $("#addOrupdateModal .form select[name='dicStatus'] option[value="+ row.dicStatus + "]").prop('selected', true); //业务状态
        //拆分显示 dicRemark
        var dicRemark_jsonObj = JSON.parse(row.dicRemark);
        $("#addOrupdateModal .form input[name='startTime']").val(dicRemark_jsonObj.startTime);                      //开始时间
        $("#addOrupdateModal .form input[name='endTime']").val(dicRemark_jsonObj.endTime);                          //结束时间
        $("#addOrupdateModal .form select[name='action'] option[value=" + dicRemark_jsonObj.action + "]").prop('selected', true);//发圈类别
        $("#addOrupdateModal .form input[name='imgDirPath']").val(dicRemark_jsonObj.imgDirPath);                    //图片路径
        $("#addOrupdateModal .form input[name='nickName']").val(row.dicCode);                                       //发圈后通知微信号
        $("#addOrupdateModal .form textarea[name='textMessage']").val(dicRemark_jsonObj.textMessage);               //发圈内容
        $("#addOrupdateModal .form input[name='shareArticleUrl']").val(dicRemark_jsonObj.shareArticleUrl);          //转发文章Url
        $("#addOrupdateModal .form input[name='shareArticleTitle']").val(dicRemark_jsonObj.shareArticleTitle);      //转发文章标题
        try {
            $("#addOrupdateModal .form textarea[name='dicRemark']").val( JSON.stringify(JSON.parse(row.dicRemark),null,2) );//业务详情
        } catch (e) {
            $("#addOrupdateModal .form textarea[name='dicRemark']").val( row.dicRemark );//业务详情
        }

        // show
        $('#addOrupdateModal').modal({backdrop: false, keyboard: false}).modal('show');
    });

    // delete 删除
    $("#job_list").on('click', '.delete',function() {
        var id = $(this).parents('ul').attr("_id");
        var row = tableData['key'+id];
        var paramStr = "id="+row.id;

        layer.confirm( "确定要删除吗？一旦删除后需要手动去数据库维护!!" , {
            icon: 3,
            title: I18n.system_tips ,
            btn: [ I18n.system_ok, I18n.system_cancel ]
        }, function(index){
            layer.close(index);
            $.post(
                I18n.system_url_pre+"/automation/dic/deleteDicForAdmin",
                paramStr,
                function(data, status) {
                    if (data.code == "0") {
                        $('#addOrupdateModal').modal('hide');
                        layer.open({
                            title: I18n.system_tips ,
                            btn: [ I18n.system_ok ],
                            content: "删除成功" ,
                            icon: '1',
                            end: function(layero, index){
                                jobTable.fnDraw();
                            }
                        });
                    } else {
                        layer.open({
                            title: I18n.system_tips ,
                            btn: [ I18n.system_ok ],
                            content: (data.msg || "删除失败" ),
                            icon: '2'
                        });
                    }
                }
            );
        });

    });

    var addOrupdateModalValidate = $("#addOrupdateModal .form").validate({
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
            var operatorType = $("#addOrupdateModal .form").attr("operatorType");
            console.log("operatorType = " + operatorType);
            var operatorUrl;
            if ("add" == operatorType) {
                operatorUrl = I18n.system_url_pre+"/automation/dic/addDicForAdmin";
            } else if ("update" == operatorType) {
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
                $("#addOrupdateModal .form").serialize(),
                function(data, status) {
                    if (data.code == "0") {
                        $('#addOrupdateModal').modal('hide');
                        layer.open({
                            title: I18n.system_tips ,
                            btn: [ I18n.system_ok ],
                            content: I18n.system_opt_suc ,
                            icon: '1',
                            end: function(layero, index){
                                jobTable.fnDraw();
                            }
                        });
                    } else {
                        layer.open({
                            title: I18n.system_tips ,
                            btn: [ I18n.system_ok ],
                            content: (data.msg || I18n.system_opt_fail ),
                            icon: '2'
                        });
                    }
                }
            );
        }
    });
});

/**
 * 朋友圈类别选择时间
 * @param obj
 */
function changeForDicTypeFun(obj){
    console.log("changeForDicTypeFun 被点击了....");
    console.log(obj);
    var dicType = $("#addOrupdateModal .form select[name='dicType']").children('option:selected').val();
    console.log("dicType = " + dicType);
    if(dicType == "sendFriendCircle"){
        $("#addOrupdateModal .sendFriendCircle").css("display", "block");
        $("#addOrupdateModal .shareArticleToFriendCircle").css("display", "none");
    } else if(dicType == "shareArticleToFriendCircle"){
        $("#addOrupdateModal .sendFriendCircle").css("display", "none");
        $("#addOrupdateModal .shareArticleToFriendCircle").css("display", "block");
    }
}

/**
 * 清空表单信息
 */
function clearFormValue() {
    //清空信息
    $("#addOrupdateModal .form input[name='dicType']").val("");           //字典类型
    $("#addOrupdateModal .form input[name='dicCode']").val("");           //字典编码
    $("#addOrupdateModal .form input[name='dicName']").val("");           //字典名称
    $("#addOrupdateModal .form select[name='dicStatus'] option[value=0]").prop('selected', true); //业务状态，默认正常
    $("#addOrupdateModal .form input[name='startTime']").val("");         //开始时间
    $("#addOrupdateModal .form input[name='endTime']").val("");           //结束时间
    $("#addOrupdateModal .form select[name='action'] option[value='imgMessageFriendCircle']").prop('selected', true); //发圈类别
    $("#addOrupdateModal .form input[name='imgDirPath']").val("");        //图片路径
    $("#addOrupdateModal .form input[name='nickName']").val("");          //发圈后通知微信号
    $("#addOrupdateModal .form input[name='shareArticleUrl']").val("");   //发送朋友圈
    $("#addOrupdateModal .form input[name='shareArticleTitle']").val(""); //分享文章链接到朋友圈
    $("#addOrupdateModal .form textarea[name='textMessage']").val("");    //发圈内容
    $("#addOrupdateModal .form textarea[name='dicRemark']").val("");      //字典详情
    //删除只读属性
    $("#addOrupdateModal .form input[name='dicType']").removeAttr("readonly");           //字典类型
    $("#addOrupdateModal .form input[name='dicCode']").removeAttr("readonly");           //字典编码
    $("#addOrupdateModal .form input[name='dicName']").removeAttr("readonly");           //字典名称
    $("#addOrupdateModal .form input[name='dicStatus']").removeAttr("readonly");         //字典状态
    $("#addOrupdateModal .form input[name='startTime']").removeAttr("readonly");         //开始时间
    $("#addOrupdateModal .form input[name='endTime']").removeAttr("readonly");           //结束时间
    $("#addOrupdateModal .form input[name='action']").removeAttr("readonly");            //发圈类别
    $("#addOrupdateModal .form input[name='nickName']").removeAttr("readonly");          //发圈后通知微信号
    $("#addOrupdateModal .form input[name='imgDirPath']").removeAttr("readonly");        //图片路径
    $("#addOrupdateModal .form input[name='shareArticleUrl']").removeAttr("readonly");   //发送朋友圈
    $("#addOrupdateModal .form input[name='shareArticleTitle']").removeAttr("readonly"); //分享文章链接到朋友圈
    $("#addOrupdateModal .form textarea[name='textMessage']").removeAttr("readonly");    //发圈内容
    $("#addOrupdateModal .form textarea[name='dicRemark']").removeAttr("readonly");      //字典详情
}

/**
 * 整理 dicRemark 的 val
 */
function changeDicRemark() {
    console.log("changeDicRemark 被执行了...");
    //整理 dicRemark
    var dicRemark_jsonObj = {};
    try {
        dicRemark_jsonObj = JSON.parse($("#addOrupdateModal .form textarea[name='dicRemark']").val());
    } catch (e) {
        dicRemark_jsonObj = dicRemark_jsonObj;
    }
    console.log(dicRemark_jsonObj);
    //开始时间
    var startTime = $("#addOrupdateModal .form input[name='startTime']").val();
    if (!isNull(startTime)) {
        dicRemark_jsonObj["startTime"] = startTime;
    }
    //结束时间
    var endTime = $("#addOrupdateModal .form input[name='endTime']").val();
    if (!isNull(endTime)) {
        dicRemark_jsonObj["endTime"] = endTime;
    }
    //发圈类别
    var action = $("#addOrupdateModal .form select[name='action']").children('option:selected').val();
    if (!isNull(action)) {
        dicRemark_jsonObj["action"] = action;
    }
    //图片路径
    var imgDirPath = $("#addOrupdateModal .form input[name='imgDirPath']").val();
    if (!isNull(imgDirPath)) {
        dicRemark_jsonObj["imgDirPath"] = imgDirPath;
    }
    //发圈后通知微信号
    var nickName = $("#addOrupdateModal .form input[name='nickName']").val();
    if (!isNull(nickName)) {
        dicRemark_jsonObj["nickName"] = nickName;
    }
    //发圈内容
    var textMessage = $("#addOrupdateModal .form textarea[name='textMessage']").val();
    if (!isNull(textMessage)) {
        dicRemark_jsonObj["textMessage"] = textMessage;
    }
    //转发文章Url
    var shareArticleUrl = $("#addOrupdateModal .form input[name='shareArticleUrl']").val();
    if (!isNull(shareArticleUrl)) {
        dicRemark_jsonObj["shareArticleUrl"] = shareArticleUrl;
    }
    //转发文章标题
    var shareArticleTitle = $("#addOrupdateModal .form input[name='shareArticleTitle']").val();
    if (!isNull(shareArticleUrl)) {
        dicRemark_jsonObj["shareArticleTitle"] = shareArticleTitle;
    }
    //业务编码
    $("#addOrupdateModal .form input[name='dicCode']").val(nickName);
    //业务详情
    $("#addOrupdateModal .form textarea[name='dicRemark']").val(JSON.stringify(dicRemark_jsonObj, null, 4));
}