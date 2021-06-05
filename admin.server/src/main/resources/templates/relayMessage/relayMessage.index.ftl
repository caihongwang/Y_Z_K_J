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
	<@netCommon.commonLeft "relayMessage" />

	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>转发消息管理</h1>
		</section>

		<!-- Main content -->
		<section class="content">

			<div class="row">
				<div class="col-xs-2">
					<div class="input-group">
						<select class="form-control"  id="dicType" name="dicType">
							<option value="relayTheWxMessage" selected>转发消息</option>
						</select>
					</div>
				</div>
				<div class="col-xs-2">
					<div class="input-group">
						<input type="text" class="form-control" id="dicName" autocomplete="on"
							   placeholder="${I18n.system_please_input}业务名称">
					</div>
				</div>
				<div class="col-xs-1">
					<div class="input-group">
						<select class="form-control"  id="dicStatus" name="dicStatus">
							<option value="0"  selected>正常</option>
							<option value="1">禁用</option>
						</select>
					</div>
				</div>
				<div class="col-xs-1">
					<button class="btn btn-block btn-info" id="searchBtn">${I18n.system_search}</button>
				</div>
				<div class="col-xs-1">
					<button class="btn btn-block btn-success add" type="button">${I18n.jobinfo_field_add}</button>
				</div>
			</div>

			<div class="row">
				<div class="col-xs-12">
					<div class="box">
						<div class="box-body">
							<table id="job_list" class="table table-bordered table-striped">
								<thead>
								<tr>
									<th>${I18n.system_opt}</th>
									<th name="id">ID</th>
									<th name="dicType">业务类型</th>
									<th name="dicCode">业务编码</th>
									<th name="dicName">业务名称</th>
									<th name="dicRemark">业务详情</th>
									<th name="dicStatus">业务状态</th>
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
				<h4 class="modal-title">更新【转发消息】信息</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal form" role="form">

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">业务类型<font
									color="red">*</font></label>
						<div class="col-sm-4">
							<select class="form-control" name="dicType">
								<option value="relayTheWxMessage" selected>添加设备列表与坐标</option>
							</select>
						</div>
						<label for="lastname" class="col-sm-2 control-label">业务编码<font
									color="black">*</font></label>
						<div class="col-sm-4"><input type="text" class="form-control"  name="dicCode"  readonly="readonly"
													 placeholder="${I18n.system_please_input}业务编码"
													 maxlength="100"></div>
					</div>

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">业务名称<font
									color="red">*</font></label>
						<div class="col-sm-4"><input type="text" class="form-control"  name="dicName"  readonly="readonly"
													 placeholder="${I18n.system_please_input}业务名称"
													 maxlength="100"></div>
						<label for="lastname" class="col-sm-2 control-label">业务状态<font
									color="black">*</font></label>
						<div class="col-sm-4">
							<select class="form-control" name="dicStatus">
								<option value="0" selected>正常</option>
								<option value="1">异常</option>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label for="firstname" class="col-sm-2 control-label">业务详情<font
									color="black">*</font></label>
						<div class="col-sm-10">
                            <textarea class="textarea form-control" name="dicRemark"
									  placeholder="${I18n.system_please_input}业务详情"
									  maxlength="10240000" style="height: 250px; line-height: 1.2;"></textarea>
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


<!-- 新增.模态框 -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">添加【转发消息】信息</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal form" role="form">

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">业务类型<font
									color="red">*</font></label>
						<div class="col-sm-4">
							<select class="form-control" name="dicType">
								<option value="relayTheWxMessage" selected>转发消息</option>
							</select>
						</div>

						<label for="lastname" class="col-sm-2 control-label">业务编码<font
									color="black">*</font></label>
						<div class="col-sm-4"><input type="text" class="form-control"  name="dicCode"
													 placeholder="${I18n.system_please_input}业务编码"
													 maxlength="100"></div>
					</div>

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">业务方式<font
									color="red">*</font></label>
						<div class="col-sm-4"><input type="text" class="form-control"  name="dicName"
													 placeholder="${I18n.system_please_input}业务名称"
													 maxlength="100"></div>
						<label for="lastname" class="col-sm-2 control-label">业务状态<font
									color="black">*</font></label>
						<div class="col-sm-4">
							<select class="form-control" name="dicStatus">
								<option value="0" selected>正常</option>
								<option value="1">异常</option>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label for="firstname" class="col-sm-2 control-label">业务详情<font
									color="black">*</font></label>
						<div class="col-sm-10">
                            <textarea class="textarea form-control" name="dicRemark"
									  placeholder="${I18n.system_please_input}业务详情"
									  maxlength="10240000" style="height: 250px; line-height: 1.2;"></textarea>
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
<script src="${request.contextPath}/static/js/relayMessage.index.1.js"></script>
</body>
</html>
