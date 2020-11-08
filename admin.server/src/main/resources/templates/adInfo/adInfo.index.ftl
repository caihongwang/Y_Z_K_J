<!DOCTYPE html>
<html>
	<head>
		<#import "../common/common.macro.ftl" as netCommon>
		<@netCommon.commonStyle />
		<!-- DataTables -->
		<link rel="stylesheet" href="${request.contextPath}/static/adminlte/bower_components/datatables.net-bs/css/dataTables.bootstrap.min.css">
		<title>${I18n.admin_name}</title>
	</head>
	<body class="hold-transition skin-blue sidebar-mini <#if cookieMap?exists && cookieMap["xxljob_adminlte_settings"]?exists && "off" == cookieMap["xxljob_adminlte_settings"].value >sidebar-collapse</#if>">
		<div class="wrapper">
			<!-- header -->
			<@netCommon.commonHeader />
			<!-- left -->
			<@netCommon.commonLeft "adInfo" />
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper">
				<!-- Content Header (Page header) -->
				<section class="content-header">
					<h1>广告管理</h1>
				</section>
				<!-- Main content -->
				<section class="content">

					<div class="row">
						<div class="col-xs-2">
							<div class="input-group" style="width: 100%;">
								<select class="form-control"  id="adInfo" name="adInfo">
									<option value="adInfo">小程序广告信息</option>
								</select>
							</div>
						</div>
						<div class="col-xs-2">
							<div class="input-group">
								<input type="text" class="form-control" id="adTitle"  name="adTitle" autocomplete="on" placeholder="${I18n.system_please_input}广告标题">
							</div>
						</div>
						<div class="col-xs-2">
							<div class="input-group">
								<input type="text" class="form-control" id="adContent" name="adContent" autocomplete="on" placeholder="${I18n.system_please_input}广告内容">
							</div>
						</div>
						<div class="col-xs-1">
							<div class="input-group">
								<select class="form-control"  id="status" name="status">
									<option value="0" selected>正常</option>
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
									<table id="job_list" class="table table-bordered table-striped" width="100%">
										<thead>
										<tr>
											<th name="id">ID</th>
											<th name="adTitle">广告标题</th>
											<th name="adImgUrl">广告图片</th>
											<th name="adContent">广告内容</th>
											<th name="adRemark">广告备注</th>
											<th name="status">业务状态</th>
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
						<h4 class="modal-title">更新【广告】信息</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal form" role="form">

							<div class="form-group">
								<label for="lastname" class="col-sm-2 control-label">广告标题<font color="red">*</font></label>
								<div class="col-sm-4">
									<input type="text" class="form-control" name="adTitle"  placeholder="${I18n.system_please_input}广告标题" maxlength="1000">
								</div>
								<label for="lastname" class="col-sm-2 control-label">广告图片<font color="black">*</font></label>
								<div class="col-sm-4">
									<input type="text" class="form-control" name="adImgUrl" placeholder="${I18n.system_please_input}广告图片" maxlength="10000">
								</div>
							</div>

							<div class="form-group">
								<label for="lastname" class="col-sm-2 control-label">广告状态<font color="black">*</font></label>
								<div class="col-sm-4">
									<select class="form-control" name="status">
										<option value="0" selected>正常</option>
										<option value="1">异常</option>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label for="firstname" class="col-sm-2 control-label">广告内容<font color="black">*</font></label>
								<div class="col-sm-10">
									<textarea class="textarea form-control" name="adContent" placeholder="${I18n.system_please_input}广告内容" maxlength="10240000" style="height: 250px; line-height: 1.2;"></textarea>
								</div>
							</div>

							<div class="form-group">
								<label for="firstname" class="col-sm-2 control-label">广告备注<font color="black">*</font></label>
								<div class="col-sm-10">
									<textarea class="textarea form-control" name="adRemark" placeholder="${I18n.system_please_input}广告备注" maxlength="10240000" style="height: 150px; line-height: 1.2;"></textarea>
								</div>
							</div>

							<hr>
							<div class="form-group">
								<div class="col-sm-offset-3 col-sm-6">
									<button type="submit" class="btn btn-primary">${I18n.system_save}</button>
									<button type="button" class="btn btn-default" data-dismiss="modal">${I18n.system_cancel}</button>
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
						<h4 class="modal-title">添加【广告】信息</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal form" role="form">

							<div class="form-group">
								<label for="lastname" class="col-sm-2 control-label">广告标题<font color="red">*</font></label>
								<div class="col-sm-4">
									<input type="text" class="form-control"  name="adTitle" placeholder="${I18n.system_please_input}广告标题" maxlength="1000">
								</div>
								<label for="lastname" class="col-sm-2 control-label">广告图片<font color="black">*</font></label>
								<div class="col-sm-4">
									<input type="text" class="form-control" name="adImgUrl" placeholder="${I18n.system_please_input}广告图片" maxlength="10000">
								</div>
							</div>

							<div class="form-group">
								<label for="lastname" class="col-sm-2 control-label">广告状态<font color="black">*</font></label>
								<div class="col-sm-4">
									<select class="form-control" name="status">
										<option value="0" selected>正常</option>
										<option value="1">异常</option>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label for="firstname" class="col-sm-2 control-label">广告内容<font color="black">*</font></label>
								<div class="col-sm-10">
									<textarea class="textarea form-control" name="adContent" placeholder="${I18n.system_please_input}广告内容" maxlength="10240000" style="height: 250px; line-height: 1.2;"></textarea>
								</div>
							</div>

							<div class="form-group">
								<label for="firstname" class="col-sm-2 control-label">广告备注<font color="black">*</font></label>
								<div class="col-sm-10">
									<textarea class="textarea form-control" name="adRemark" placeholder="${I18n.system_please_input}广告备注" maxlength="10240000" style="height: 150px; line-height: 1.2;"></textarea>
								</div>
							</div>

							<hr>
							<div class="form-group">
								<div class="col-sm-offset-3 col-sm-6">
									<button type="submit" class="btn btn-primary">${I18n.system_save}</button>
									<button type="button" class="btn btn-default" data-dismiss="modal">${I18n.system_cancel}</button>
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
		<script src="${request.contextPath}/static/js/adInfo.index.1.js"></script>
	</body>
</html>
