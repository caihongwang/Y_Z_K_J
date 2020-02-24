<!DOCTYPE html>
<html>
<head>
	<#import "../common/common.macro.ftl" as netCommon>
	<@netCommon.commonStyle />
	<!-- DataTables -->
	<link rel="stylesheet"
		  href="${request.contextPath}/static/adminlte/bower_components/datatables.net-bs/css/dataTables.bootstrap.min.css">
	<title>${I18n.admin_name}</title>
</head>
<body class="hold-transition skin-blue sidebar-mini <#if cookieMap?exists && cookieMap["xxljob_adminlte_settings"]?exists && "off" == cookieMap["xxljob_adminlte_settings"].value >sidebar-collapse</#if>">
<div class="wrapper">
	<!-- header -->
	<@netCommon.commonHeader />
	<!-- left -->
	<@netCommon.commonLeft "oilStationInfo" />

	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>加油站管理</h1>
		</section>

		<!-- Main content -->
		<section class="content">

			<div class="row">
				<div class="col-xs-2">
					<div class="input-group">
						<input type="text" class="form-control" id="oilStationName" autocomplete="on"
							   placeholder="${I18n.system_please_input}加油站名称">
					</div>
				</div>
				<div class="col-xs-1">
					<button class="btn btn-block btn-info" id="searchBtn">${I18n.system_search}</button>
				</div>
			</div>

			<div class="row">
				<div class="col-xs-12">
					<div class="box">
						<#--<div class="box-header hide">
                            <h3 class="box-title">调度列表</h3>
                        </div>-->
						<div class="box-body">
							<table id="job_list" class="table table-bordered table-striped" width="100%">
								<thead>
								<tr>
									<th name="id">ID</th>
									<th name="oilStationName">名称</th>
									<th name="oilStationPrice">油价</th>
									<th name="oilStationPosition">经纬度</th>
									<th name="oilStationLon">经度</th>
									<th name="oilStationLat">纬度</th>
									<th name="oilStationAdress">地址</th>
									<th name="oilStationCategory">类型</th>
									<th name="oilStationOwnerUid">站长</th>
									<th name="shareTitle">分享标题</th>
									<th name="oilStationHireTitle">招人H5标题</th>
									<th name="oilStationHireUrl">招人H5地址</th>
									<th>${I18n.system_opt}</th>
								</tr>
								</thead>
								<tbody></tbody>
								<tfoot></tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
	<!-- footer -->
	<@netCommon.commonFooter />
</div>

<!-- 更新.模态框 -->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">更新加油站</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal form" role="form">

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">加油站名称<font
									color="red">*</font></label>
						<div class="col-sm-4"><input type="text" class="form-control" name="oilStationName" readonly="readonly"
													 placeholder="${I18n.system_please_input}加油站名称"
													 maxlength="50"></div>
						<label for="lastname" class="col-sm-2 control-label">站长uid<font
									color="black">*</font></label>
						<div class="col-sm-4"><input type="text" class="form-control"  name="oilStationOwnerUid"
													 placeholder="${I18n.system_please_input}站长uid"
													 maxlength="100"></div>
					</div>

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">加油站类型<font
									color="red">*</font></label>
						<div class="col-sm-4"><input type="text" class="form-control" name="oilStationCategory"
													 placeholder="${I18n.system_please_input}加油站类型"
													 maxlength="50"></div>
						<label for="lastname" class="col-sm-2 control-label">分享标题<font
									color="black">*</font></label>
						<div class="col-sm-4"><input type="text" class="form-control"  name="shareTitle"
													 placeholder="${I18n.system_please_input}分享标题"
													 maxlength="100"></div>
					</div>

					<div class="form-group">
						<label for="firstname" class="col-sm-2 control-label">加油站油价<font
									color="black">*</font></label>
						<div class="col-sm-10">
                            <textarea class="textarea form-control" name="oilStationPrice"
									  placeholder="${I18n.system_please_input}加油站油价"
									  maxlength="102400" style="height: 68px; line-height: 1.2;"></textarea>
						</div>
					</div>

					<hr>
					<div class="form-group">
						<div class="col-sm-offset-3 col-sm-6">
							<button type="submit" class="btn btn-primary">${I18n.system_save}</button>
							<button type="button" class="btn btn-default"
									data-dismiss="modal">${I18n.system_cancel}</button>
							<input type="hidden" name="id">
						</div>
					</div>

				</form>
			</div>
		</div>
	</div>
</div>

<@netCommon.commonScript />
<!-- DataTables -->
<script src="${request.contextPath}/static/adminlte/bower_components/datatables.net/js/jquery.dataTables.min.js"></script>
<script src="${request.contextPath}/static/adminlte/bower_components/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
<!-- moment -->
<script src="${request.contextPath}/static/adminlte/bower_components/moment/moment.min.js"></script>
<#-- cronGen -->
<script src="${request.contextPath}/static/plugins/cronGen/cronGen<#if I18n.admin_i18n?default('')?length gt 0 >_${I18n.admin_i18n}</#if>.js"></script>
<script src="${request.contextPath}/static/js/oilStationInfo.index.1.js"></script>
</body>
</html>
