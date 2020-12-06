$(function() {
	// init date tables
	var jobTable = $("#job_list").dataTable({
		"deferRender": true,
		"processing" : true,
	    "serverSide": true,
        "scrollX": true,
		"ajax": {
			// url: "http://localhost:9030/oilStationMap/wxOilStation/getOilStationListForAdmin",
			url: "https://www.yzkj.store/oilStationMap/wxOilStation/getOilStationListForAdmin",
			type: "post",
	        data: function ( d ) {			//参数
	        	var obj = {};
                obj.start = d.start;
				obj.size = 10;
                obj.oilStationName = $('#oilStationName').val();
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
                            '       <li><a href="javascript:void(0);" class="userCenter" >定位中心</a></li>\n' +
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
                "data": 'oilStationName',
                "bSortable": false,
                "visible" : true,
                "width": 300
            },{
                "data": 'oilStationPrice',
                "bSortable": false,
                "visible" : false,
                "width":300
            },{
                "data": 'oilStationPosition',
                "bSortable": false,
                "visible" : true,
                "width": 120
            },{
                "data": 'oilStationLon',
                "bSortable": false,
                "visible" : false,
                "width": 150
            },{
                "data": 'oilStationLat',
                "bSortable": false,
                "visible" : false,
                "width": 150
            },{
                "data": 'oilStationAdress',
                "bSortable": false,
                "visible" : true,
                "width": 300
            },{
                "data": 'oilStationCategory',
                "bSortable": false,
                "visible" : true,
                "width": 100,
                "render": function ( data, type, row ) {
                    return data+"";
                }
            },{
                "data": 'oilStationOwnerUid',
                "bSortable": false,
                "visible" : true,
                "width": 100
            },{
                "data": 'shareTitle',
                "bSortable": false,
                "visible" : true,
                "width": 300
            },{
                "data": 'shareImgUrl',
                "bSortable": false,
                "visible" : true,
                "width": 300
            },{
                "data": 'oilStationHireTitle',
                "bSortable": false,
                "visible" : true,
                "width": 300
            },{
                "data": 'oilStationHireUrl',
                "bSortable": false,
                "visible" : true,
                "width": 300
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

	// userCenter 定位中心
	$("#job_list").on('click', '.userCenter',function() {
        var id = $(this).parents('ul').attr("_id");
        var row = tableData['key'+id];
        var paramStr = "lon="+row.oilStationLon+"&lat="+row.oilStationLat+"&uid=24,1762,3347,4567";
        $.post(
            // "http://localhost:9030/oilStationMap/wxOilStation/setLocaltionByUid",
            "https://www.yzkj.store/oilStationMap/wxOilStation/setLocaltionByUid",
            paramStr,
            function(data, status) {
                if (data.code == "0") {
                    $('#updateModal').modal('hide');
                    layer.open({
                        title: I18n.system_tips ,
                        btn: [ I18n.system_ok ],
                        content: '用户定位中心已变更' ,
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
            }
        );
	});

    // update 更新
    $("#job_list").on('click', '.update',function() {

        var id = $(this).parents('ul').attr("_id");
        var row = tableData['key'+id];

        console.log("row.shareTitle = " + row.shareTitle);
        console.log("row.oilStationPrice = " + row.oilStationPrice);

        $("#updateModal .form input[name='id']").val( row.id );
        $("#updateModal .form input[name='oilStationName']").val( row.oilStationName );             //加油站名称
        $("#updateModal .form input[name='oilStationOwnerUid']").val( row.oilStationOwnerUid );     //加油站站长
        $("#updateModal .form input[name='oilStationCategory']").val( row.oilStationCategory );     //加油站站长
        $("#updateModal .form input[name='shareTitle']").val( row.shareTitle );                     //小程序-分享标题
        $("#updateModal .form input[name='shareImgUrl']").val( row.shareImgUrl );                   //小程序-分享图片
        $("#updateModal .form textarea[name='oilStationPrice']").val( row.oilStationPrice );         //加油站油价

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
                // "http://localhost:9030/oilStationMap/wxOilStation/updateOilStationForAdmin",
                "https://www.yzkj.store/oilStationMap/wxOilStation/updateOilStationForAdmin",
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
