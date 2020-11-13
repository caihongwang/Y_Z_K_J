$(function() {
	// init date tables
	var jobTable = $("#job_list").dataTable({
		"deferRender": true,
		"processing" : true,
	    "serverSide": true,
        "scrollX": true,
		"ajax": {
			// url: "http://localhost:9030/oilStationMap/wxDic/getDicListByConditionForAdmin",
			url: "https://www.yzkj.store/oilStationMap/wxDic/getDicListByConditionForAdmin",
			type: "post",
	        data: function ( d ) {			//参数
	        	var obj = {};
				obj.start = d.start;
				obj.size = 10;
                obj.dicType = $('#dicType').children('option:selected').val();
                obj.dicCode = $('#dicCode').val();
                obj.dicName = $('#dicName').children('option:selected').val();
                obj.dicStatus = $('#dicStatus').children('option:selected').val();
                console.log($('#dicName'));
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
                "width": 80,
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
                "width": 50,
                "render": function ( data, type, row ) {
                    return data;
                }
            },{
                "data": 'dicType',
                "bSortable": false,
                "visible" : true,
                "width": 150
            },{
                "data": 'dicCode',
                "bSortable": false,
                "visible" : true,
                "width": 150
            },{
                "data": 'dicName',
                "bSortable": false,
                "visible" : true,
                "width": 150
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
                    } if(data == "1"){
                        return "禁用";
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

        $("#addModal .form input[name='dicType']").val( "" );           //字典类型
        $("#addModal .form input[name='dicCode']").val( "" );           //字典编码
        $("#addModal .form input[name='dicName']").val( "" );           //字典名称
        $("#addModal .form input[name='dicStatus']").val( "0" );       //字典状态
        $("#addModal .form textarea[name='dicRemark']").val( "" );    //字典详情

        $('#addModal').modal({backdrop: false, keyboard: false}).modal('show');
    });
    var addModalValidate = $("#addModal .form").validate({
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
            $.post(
                // "http://localhost:9030/oilStationMap/wxDic/addDicForAdmin",
                "https://www.yzkj.store/oilStationMap/wxDic/addDicForAdmin",
                $("#addModal .form").serialize(),
                function(data, status) {
                    if (data.code == "0") {
                        $('#addModal').modal('hide');
                        layer.open({
                            title: I18n.system_tips ,
                            btn: [ I18n.system_ok ],
                            content: I18n.system_add_suc ,
                            icon: '1',
                            end: function(layero, index){
                                jobTable.fnDraw();
                            }
                        });
                    } else {
                        layer.open({
                            title: I18n.system_tips ,
                            btn: [ I18n.system_ok ],
                            content: (data.msg || I18n.system_add_fail ),
                            icon: '2'
                        });
                    }
                });
        }
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
                // "http://localhost:9030/oilStationMap/wxDic/deleteDicForAdmin",
                "https://www.yzkj.store/oilStationMap/wxDic/deleteDicForAdmin",
                paramStr,
                function(data, status) {
                    if (data.code == "0") {
                        $('#updateModal').modal('hide');
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

    // update 更新
    $("#job_list").on('click', '.update',function() {

        var id = $(this).parents('ul').attr("_id");
        var row = tableData['key'+id];

        $("#updateModal .form input[name='id']").val( row.id );
        $("#updateModal .form input[name='dicType']").val( row.dicType );           //字典类型
        $("#updateModal .form input[name='dicCode']").val( row.dicCode );           //字典编码
        $("#updateModal .form input[name='dicName']").val( row.dicName );           //字典名称
        $("#updateModal .form input[name='dicStatus']").val( row.dicStatus );       //字典状态
        $("#updateModal .form textarea[name='dicRemark']").val( row.dicRemark );    //字典详情

        // show
        $('#updateModal').modal({backdrop: false, keyboard: false}).modal('show');
    });
    var updateModalValidate = $("#updateModal .form").validate({
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
            $.post(
                // "http://localhost:9030/oilStationMap/wxDic/updateDicForAdmin",
                "https://www.yzkj.store/oilStationMap/wxDic/updateDicForAdmin",
                $("#updateModal .form").serialize(),
                function(data, status) {
                    if (data.code == "0") {
                        $('#updateModal').modal('hide');
                        layer.open({
                            title: I18n.system_tips ,
                            btn: [ I18n.system_ok ],
                            content: I18n.system_update_suc ,
                            icon: '1',
                            end: function(layero, index){
                                jobTable.fnDraw();
                            }
                        });
                    } else {
                        layer.open({
                            title: I18n.system_tips ,
                            btn: [ I18n.system_ok ],
                            content: (data.msg || I18n.system_update_fail ),
                            icon: '2'
                        });
                    }
            });
        }
    });

});
