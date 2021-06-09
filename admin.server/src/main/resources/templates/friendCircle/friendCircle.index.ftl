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
	<@netCommon.commonLeft "friendCircle" />

	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>朋友圈管理</h1>
		</section>

		<!-- Main content -->
		<section class="content">
			<div class="row">
				<div class="col-xs-2">
					<div class="input-group">
						<select class="form-control" id="dicType" name="dicType">
							<option value="sendFriendCircle" selected>发布图片/文字朋友圈</option>
							<option value="shareArticleToFriendCircle">分享文章链接到朋友圈</option>
						</select>
					</div>
				</div>
				<div class="col-xs-2">
					<div class="input-group">
						<select class="form-control" id="dicName" name="dicName">
							<option id="imgMessageFriendCircle" value="发布图片内容到朋友圈" selected>发布图片内容到朋友圈</option>
							<option id="textMessageFriendCircle" value="发布文字内容到朋友圈">发布文字内容到朋友圈</option>
							<option id="shareArticleToFriendCircle" value="分享文章链接到朋友圈">分享文章链接到朋友圈</option>
						</select>
					</div>
				</div>
				<div class="col-xs-2">
					<div class="input-group">
						<input type="text" class="form-control" id="dicCode" autocomplete="on"
							   placeholder="${I18n.system_please_input}业务编码">
					</div>
				</div>
				<div class="col-xs-1">
					<div class="input-group">
						<select class="form-control"  id="dicStatus" name="dicStatus">
							<option value="0"  selected>正常</option>
							<option value="1">删除</option>
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

<!-- 模态框 -->
<div class="modal fade" id="addOrupdateModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">更新【朋友圈】信息</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal form" role="form">

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">业务类型<font color="red">*</font></label>
						<div class="col-sm-4">
							<select class="form-control" name="dicType" onchange="changeForDicTypeFun(this)">
								<option value="sendFriendCircle" selected>发送朋友圈</option>
								<option value="shareArticleToFriendCircle">分享文章链接到朋友圈</option>
							</select>
						</div>
						<label for="lastname" class="col-sm-2 control-label">业务名称<font
									color="red">*</font></label>
						<div class="col-sm-4">
							<select class="form-control" name="dicName">
								<option value="发布图片内容到朋友圈" selected>发布图片内容到朋友圈</option>
								<option value="发布文字内容到朋友圈">发布文字内容到朋友圈</option>
								<option value="分享文章链接到朋友圈">分享文章链接到朋友圈</option>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">业务编码<font
									color="black">*</font></label>
						<div class="col-sm-4"><input type="text" class="form-control"  name="dicCode"  readonly="readonly"
													 placeholder="${I18n.system_please_input}业务编码"
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
						<label for="lastname" class="col-sm-2 control-label">开始时间<font
									color="black">*</font></label>
						<div class="col-sm-4">
							<input type="text" class="form-control"  name="startTime"
								   placeholder="${I18n.system_please_input}开始时间"
								   maxlength="100" onchange="changeDicRemark()">
						</div>
						<label for="lastname" class="col-sm-2 control-label">结束时间<font
									color="black">*</font></label>
						<div class="col-sm-4">
							<input type="text" class="form-control"  name="endTime"
								   placeholder="${I18n.system_please_input}结束时间"
								   maxlength="100" onchange="changeDicRemark()">
						</div>
					</div>

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">发圈类别<font
									color="black">*</font></label>
						<div class="col-sm-4">
							<select class="form-control" name="action" onchange="changeDicRemark()">
								<option value="imgMessageFriendCircle" selected>发布图片内容到朋友圈</option>
								<option value="textMessageFriendCircle">发布文字内容到朋友圈</option>
								<option value="shareArticleToFriendCircle">分享文章链接到朋友圈</option>
							</select>
						</div>
						<label for="lastname" class="col-sm-2 control-label">发圈后通知微信号<font
									color="black">*</font></label>
						<div class="col-sm-4">
							<input type="text" class="form-control"  name="nickName"
								   placeholder="${I18n.system_please_input}发圈后通知微信号"
								   maxlength="100" onchange="changeDicRemark()">
						</div>
					</div>

					<div class="form-group sendFriendCircle">
						<label for="lastname" class="col-sm-2 control-label">图片路径<font
									color="black">*</font></label>
						<div class="col-sm-10">
							<input type="text" class="form-control"  name="imgDirPath"
								   placeholder="${I18n.system_please_input}图片路径"
								   maxlength="100" onchange="changeDicRemark()">
						</div>
					</div>

					<div class="form-group sendFriendCircle">
						<label for="firstname" class="col-sm-2 control-label">发圈内容<font
									color="black">*</font></label>
						<div class="col-sm-10">
                            <textarea class="textarea form-control" name="textMessage"
									  placeholder="${I18n.system_please_input}发圈内容"
									  maxlength="10240000" style="height: 250px; line-height: 1.2;" onchange="changeDicRemark()"></textarea>
						</div>
					</div>

					<div class="form-group shareArticleToFriendCircle">
						<label for="lastname" class="col-sm-2 control-label">转发文章Url<font
									color="black">*</font></label>
						<div class="col-sm-4">
							<input type="text" class="form-control"  name="shareArticleUrl"
								   placeholder="${I18n.system_please_input}文章地址Url"
								   maxlength="100" onchange="changeDicRemark()">
						</div>
						<label for="lastname" class="col-sm-2 control-label">转发文章标题<font
									color="black">*</font></label>
						<div class="col-sm-4">
							<input type="text" class="form-control"  name="shareArticleTitle"
								   placeholder="${I18n.system_please_input}转发文章标题"
								   maxlength="100" onchange="changeDicRemark()">
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
<script src="${request.contextPath}/static/js/friendCircle.index.1.js"></script>
</body>
</html>
