$(function() {
	// init date tables
	var jobTable = $("#job_list").dataTable({
		"deferRender": true,
		"processing" : true,
	    "serverSide": true,
        "scrollX": true,
		"ajax": {
			// url: "http://localhost:9030/oilStationMap/wxAdInfo/getSimpleAdInfoByConditionForAdmin",
			url: "https://www.yzkj.store/oilStationMap/wxAdInfo/getSimpleAdInfoByConditionForAdmin",
			type: "post",
	        data: function ( d ) {			//参数
	        	var obj = {};
				obj.start = d.start;
				obj.size = 10;
                obj.adInfo = $('#adInfo').children('option:selected').val();
                obj.adTitle = $('#adTitle').val();
                obj.adContent = $('#adContent').val();
                obj.status = $('#status').children('option:selected').val();
                console.log(obj);
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
            },{
                "data": 'id',
                "bSortable": false,
                "visible" : true,
                "width": 50,
                "render": function ( data, type, row ) {
                    return data;
                }
            },{
                "data": 'adTitle',
                "bSortable": false,
                "visible" : true,
                "width": 250
            },{
                "data": 'adImgUrl',
                "bSortable": false,
                "visible" : true,
                "width": 300
            },{
                "data": 'adContent',
                "bSortable": false,
                "visible" : true,
                "width": 300
            },{
                "data": 'adRemark',
                "bSortable": false,
                "visible" : true,
                "width": 150,
                "render": function ( data, type, row ) {
                    if(data.length > 50){
                        return data.substring(0, 50) + "......";
                    } else {
                        return data;
                    }
                }
            },{
                "data": 'status',
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
        $("#addModal .form input[name='adTitle']").val( "" );           //广告标题
        $("#addModal .form input[name='adImgUrl']").val( "" );          //广告图片
        $("#addModal .form textarea[name='adContent']").val( "" );      //广告内容
        $("#addModal .form textarea[name='adRemark']").val( "" );       //广告备注
        $("#addModal .form select[name='status']").val( "0" );           //广告状态
        // show
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
                // "http://localhost:9030/oilStationMap/wxAdInfo/addAdInfoForAdmin",
                "https://www.yzkj.store/oilStationMap/wxAdInfo/addAdInfoForAdmin",
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
                            content: (data.message || I18n.system_add_fail ),
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
                // "http://localhost:9030/oilStationMap/wxAdInfo/deleteAdInfoForAdmin",
                "https://www.yzkj.store/oilStationMap/wxAdInfo/deleteAdInfoForAdmin",
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
                            content: (data.message || "删除失败" ),
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
        $("#updateModal .form input[name='adTitle']").val( row.adTitle );           //广告标题
        $("#updateModal .form input[name='adImgUrl']").val( row.adImgUrl );         //广告图片
        $("#updateModal .form textarea[name='adContent']").val( row.adContent );    //广告内容
        try {
            $("#updateModal .form textarea[name='adRemark']").val( JSON.stringify(JSON.parse(row.adRemark),null,2) );//广告备注
        } catch (e) {
            $("#updateModal .form textarea[name='adRemark']").val( row.adRemark );//广告备注
        }
        $("#updateModal .form select[name='status']").val( row.status );             //广告状态
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
                // "http://localhost:9030/oilStationMap/wxAdInfo/updateAdInfoForAdmin",
                "https://www.yzkj.store/oilStationMap/wxAdInfo/updateAdInfoForAdmin",
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
                            content: (data.message || I18n.system_update_fail ),
                            icon: '2'
                        });
                    }
            });
        }
    });

    function isNull(obj) {
        return !obj && obj!==0 && typeof obj!=="boolean" ? true : false;
    }

});
