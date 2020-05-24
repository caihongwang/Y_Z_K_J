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
	<@netCommon.commonLeft "dicInfo" />

	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>字典管理</h1>
		</section>

		<!-- Main content -->
		<section class="content">

			<div class="row">
				<div class="col-xs-2">
					<div class="input-group">
						<input type="text" class="form-control" id="dicType" autocomplete="on"
							   placeholder="${I18n.system_please_input}字典类型">
					</div>
				</div>
				<div class="col-xs-2">
					<div class="input-group">
						<input type="text" class="form-control" id="dicCode" autocomplete="on"
							   placeholder="${I18n.system_please_input}字典编码">
					</div>
				</div>
				<div class="col-xs-2">
					<div class="input-group">
						<input type="text" class="form-control" id="dicName" autocomplete="on"
							   placeholder="${I18n.system_please_input}字典名称">
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
							<table id="job_list" class="table table-bordered table-striped" width="100%">
								<thead>
								<tr>
									<th name="id">ID</th>
									<th name="dicType">类型</th>
									<th name="dicCode">编码</th>
									<th name="dicName">名称</th>
									<th name="dicRemark">详情</th>
									<th name="dicStatus">状态</th>
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
				<h4 class="modal-title">更新字典</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal form" role="form">

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">字典类型<font
									color="red">*</font></label>
						<div class="col-sm-4"><input type="text" class="form-control" name="dicType"
													 placeholder="${I18n.system_please_input}字典类型"
													 maxlength="50"></div>
						<label for="lastname" class="col-sm-2 control-label">字典编码<font
									color="black">*</font></label>
						<div class="col-sm-4"><input type="text" class="form-control"  name="dicCode"  readonly="readonly"
													 placeholder="${I18n.system_please_input}字典编码"
													 maxlength="100"></div>
					</div>

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">字典名称<font
									color="red">*</font></label>
						<div class="col-sm-4"><input type="text" class="form-control" name="dicName"
													 placeholder="${I18n.system_please_input}字典名称"
													 maxlength="50"></div>
						<label for="lastname" class="col-sm-2 control-label">字典状态<font
									color="black">*</font></label>
						<div class="col-sm-4"><input type="text" class="form-control"  name="dicStatus"
													 placeholder="${I18n.system_please_input}字典状态"
													 maxlength="100"></div>
					</div>

					<div class="form-group">
						<label for="firstname" class="col-sm-2 control-label">字典详情<font
									color="black">*</font></label>
						<div class="col-sm-10">
                            <textarea class="textarea form-control" name="dicRemark"
									  placeholder="${I18n.system_please_input}字典详情"
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
				<h4 class="modal-title">添加字典</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal form" role="form">

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">字典类型<font
									color="red">*</font></label>
						<div class="col-sm-4"><input type="text" class="form-control" name="dicType"
													 placeholder="${I18n.system_please_input}字典类型"
													 maxlength="50"></div>
						<label for="lastname" class="col-sm-2 control-label">字典编码<font
									color="black">*</font></label>
						<div class="col-sm-4"><input type="text" class="form-control"  name="dicCode"
													 placeholder="${I18n.system_please_input}字典编码"
													 maxlength="100"></div>
					</div>

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">字典名称<font
									color="red">*</font></label>
						<div class="col-sm-4"><input type="text" class="form-control" name="dicName"
													 placeholder="${I18n.system_please_input}字典名称"
													 maxlength="50"></div>
						<label for="lastname" class="col-sm-2 control-label">字典状态<font
									color="black">*</font></label>
						<div class="col-sm-4"><input type="text" class="form-control"  name="dicStatus"
													 placeholder="${I18n.system_please_input}字典状态"
													 maxlength="100"></div>
					</div>

					<div class="form-group">
						<label for="firstname" class="col-sm-2 control-label">字典详情<font
									color="black">*</font></label>
						<div class="col-sm-10">
                            <textarea class="textarea form-control" name="dicRemark"
									  placeholder="${I18n.system_please_input}字典详情"
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
<script src="${request.contextPath}/static/js/dicInfo.index.1.js"></script>
</body>
</html>
