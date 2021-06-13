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
	<@netCommon.commonLeft "wxAutomationOperation" />

	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>微信-私域流量-自动化管理</h1>
		</section>

		<!-- Main content -->
		<section class="content">

			<div class="row">
				<#-- 发送朋友圈 -->
				<div class="col-md-4 col-sm-6 col-xs-12" id="sendFriendCircle">
					<div class="info-box bg-aqua">
						<span class="info-box-icon"><i class="fa fa-rocket"></i></span>
						<div class="info-box-content">
							<span class="info-box-number">发送朋友圈</span>
							<span class="info-box-text">12台在线设备</span>
							<div class="progress">
								<div class="progress-bar" style="width: 100%"></div>
							</div>
							<span class="progress-description">在微信中发送文字或者图片朋友圈</span>
						</div>
					</div>
				</div>
				<#-- 分享微信文章到微信朋友圈 -->
				<div class="col-md-4 col-sm-6 col-xs-12" id="shareArticleToFriendCircle">
					<div class="info-box bg-yellow">
						<span class="info-box-icon"><i class="fa fa-anchor"></i></span>
						<div class="info-box-content">
							<span class="info-box-number">分享微信文章到微信朋友圈</span>
							<span class="info-box-text">12台在线设备</span>
							<div class="progress">
								<div class="progress-bar" style="width: 100%"></div>
							</div>
							<span class="progress-description">在指定群聊中分享微信文章到微信朋友圈</span>
						</div>
					</div>
				</div>
				<#-- 点赞和评论朋友圈 -->
				<div class="col-md-4 col-sm-6 col-xs-12" id="praiseAndCommentFriendsCircle">
					<div class="info-box bg-green">
						<span class="info-box-icon"><i class="fa fa-shield"></i></span>
						<div class="info-box-content">
							<span class="info-box-number">点赞和评论朋友圈</span>
							<span class="info-box-text">12台在线设备</span>
							<div class="progress">
								<div class="progress-bar" style="width: 100%"></div>
							</div>
							<span class="progress-description">在微信朋友圈中点赞和评论朋友圈</span>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<#-- 添加群成员为好友的V群 -->
				<div class="col-md-4 col-sm-6 col-xs-12" id="addGroupMembersAsFriends">
					<div class="info-box bg-yellow">
						<span class="info-box-icon"><i class="fa fa-ticket"></i></span>
						<div class="info-box-content">
							<span class="info-box-number">添加群成员为好友的V群</span>
							<span class="info-box-text">12台在线设备</span>
							<div class="progress">
								<div class="progress-bar" style="width: 100%"></div>
							</div>
							<span class="progress-description">在指定群聊中添加其群成员为好友</span>
						</div>
					</div>
				</div>
				<#-- 同意好友请求 -->
				<div class="col-md-4 col-sm-6 col-xs-12" id="agreeToFriendRequest">
					<div class="info-box bg-green">
						<span class="info-box-icon"><i class="fa fa-puzzle-piece"></i></span>
						<div class="info-box-content">
							<span class="info-box-number">同意好友请求</span>
							<span class="info-box-text">12台在线设备</span>
							<div class="progress">
								<div class="progress-bar" style="width: 100%"></div>
							</div>
							<span class="progress-description">在所有聊天会话中检测并同意好友请求</span>
						</div>
					</div>
				</div>
				<#-- 将群保存到通讯录 -->
				<div class="col-md-4 col-sm-6 col-xs-12" id="saveToAddressBook">
					<div class="info-box bg-aqua">
						<span class="info-box-icon"><i class="fa fa-microphone"></i></span>
						<div class="info-box-content">
							<span class="info-box-number">将群保存到通讯录</span>
							<span class="info-box-text">12台在线设备</span>
							<div class="progress">
								<div class="progress-bar" style="width: 100%"></div>
							</div>
							<span class="progress-description">在所有聊天会话中检测并保存群到通讯录</span>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<#-- 转发微信消息 -->
				<div class="col-md-4 col-sm-6 col-xs-12" id="relayTheWxMessage">
					<div class="info-box bg-green">
						<span class="info-box-icon"><i class="fa fa-compass"></i></span>
						<div class="info-box-content">
							<span class="info-box-number">转发微信消息</span>
							<span class="info-box-text">12台在线设备</span>
							<div class="progress">
								<div class="progress-bar" style="width: 100%"></div>
							</div>
							<span class="progress-description">只支持最后一条消息是【图片】【小程序】【微信公众号文章】</span>
						</div>
					</div>
				</div>
				<#-- 根据微信昵称进行聊天 -->
				<div class="col-md-4 col-sm-6 col-xs-12" id="chatByNickName">
					<div class="info-box bg-aqua">
						<span class="info-box-icon"><i class="fa fa-maxcdn"></i></span>
						<div class="info-box-content">
							<span class="info-box-number">根据微信昵称进行聊天</span>
							<span class="info-box-text">12台在线设备</span>
							<div class="progress">
								<div class="progress-bar" style="width: 100%"></div>
							</div>
							<span class="progress-description">在微信中根据微信昵称进行聊天</span>
						</div>
					</div>
				</div>
				<#-- 同意进群 -->
				<div class="col-md-4 col-sm-6 col-xs-12" id="agreeToJoinTheGroup">
					<div class="info-box bg-yellow">
						<span class="info-box-icon"><i class="fa fa-fire-extinguisher"></i></span>
						<div class="info-box-content">
							<span class="info-box-number">同意进群</span>
							<span class="info-box-text">12台在线设备</span>
							<div class="progress">
								<div class="progress-bar" style="width: 100%"></div>
							</div>
							<span class="progress-description">对指定聊天会话中检测并同意进群</span>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<#-- 阅读微信文章并点击广告 -->
				<div class="col-md-4 col-sm-6 col-xs-12" id="clickArticleAd">
					<div class="info-box bg-aqua">
						<span class="info-box-icon"><i class="fa fa-eraser"></i></span>
						<div class="info-box-content">
							<span class="info-box-number">阅读微信文章并点击广告</span>
							<span class="info-box-text">12台在线设备</span>
							<div class="progress">
								<div class="progress-bar" style="width: 100%"></div>
							</div>
							<span class="progress-description">在指定群聊中阅读微信文章并点击广告</span>
						</div>
					</div>
				</div>
			</div>

		</section>
	</div>
	<!-- footer -->
	<@netCommon.commonFooter />
</div>

<!-- 模态框 For 默认：阅读微信文章并点击广告 或者 同意好友请求 或者 将群保存到通讯录 或者 同意进群 或者 转发微信消息 -->
<div class="modal fade" id="automationOperationModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" name="modalTitle">转发微信消息-自动化操作</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal form" role="form">

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">操作名称<font color="black">*</font></label>
						<div class="col-sm-10">
							<input type="text" class="form-control"  name="automationOperation_name" placeholder="${I18n.system_please_input}操作名称" readonly="true">
						</div>
					</div>

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">操作地址<font color="black">*</font></label>
						<div class="col-sm-10">
							<input type="text" class="form-control"  name="automationOperation_url" placeholder="${I18n.system_please_input}操作地址" maxlength="1000">
						</div>
					</div>

					<div class="form-group">
						<label for="firstname" class="col-sm-2 control-label">操作设备列表<font color="black">*</font></label>
						<div class="col-sm-10">
                            <textarea class="textarea form-control" name="currentDeviceListStr" placeholder="${I18n.system_please_input}操作参数" maxlength="10240000" style="height: 250px; line-height: 1.2;"></textarea>
						</div>
					</div>

					<hr>
					<div class="form-group">
						<div class="col-sm-offset-3 col-sm-6">
							<button type="submit" class="btn btn-primary">${I18n.system_opt}</button>
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

<!-- 模态框 For 点赞和评论朋友圈 -->
<div class="modal fade" id="praiseAndCommentFriendsCircleModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" name="modalTitle">点赞和评论朋友圈-自动化操作</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal form" role="form">

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">操作名称<font color="black">*</font></label>
						<div class="col-sm-10">
							<input type="text" class="form-control"  name="automationOperation_name" placeholder="${I18n.system_please_input}操作名称" readonly="true">
						</div>
					</div>

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">操作地址<font color="black">*</font></label>
						<div class="col-sm-10">
							<input type="text" class="form-control"  name="automationOperation_url" placeholder="${I18n.system_please_input}操作地址" maxlength="1000">
						</div>
					</div>

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">操作微信号<font color="black">*</font></label>
						<div class="col-sm-4">
							<input type="text" class="form-control"  name="nickName" placeholder="${I18n.system_please_input}所有、广告或者微信号" maxlength="1000">
						</div>
						<label for="lastname" class="col-sm-2 control-label">滑动朋友圈次数<font color="black">*</font></label>
						<div class="col-sm-4">
							<input type="text" class="form-control"  name="allSwipeNum" placeholder="${I18n.system_please_input}滑动朋友圈次数" maxlength="1000">
						</div>
					</div>

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">评论内容<font color="black">*</font></label>
						<div class="col-sm-10">
							<input type="text" class="form-control"  name="commentContent" placeholder="${I18n.system_please_input}评论内容" maxlength="1000">
						</div>
					</div>

					<div class="form-group">
						<label for="firstname" class="col-sm-2 control-label">操作设备列表<font color="black">*</font></label>
						<div class="col-sm-10">
							<textarea class="textarea form-control" name="currentDeviceListStr" placeholder="${I18n.system_please_input}操作参数" maxlength="10240000" style="height: 250px; line-height: 1.2;"></textarea>
						</div>
					</div>

					<hr>
					<div class="form-group">
						<div class="col-sm-offset-3 col-sm-6">
							<button type="submit" class="btn btn-primary">${I18n.system_opt}</button>
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

<!-- 模态框 For 发送朋友圈 或者 根据微信昵称进行聊天 或者 分享微信文章到微信朋友圈 或者 添加群成员为好友的V群 -->
<div class="modal fade" id="sendFriendCircle_or_chatByNickName_or_shareArticleToFriendCircle_or_addGroupMembersAsFriends_Modal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" name="modalTitle">发送朋友圈-自动化操作</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal form" role="form">

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">操作名称<font color="black">*</font></label>
						<div class="col-sm-10">
							<input type="text" class="form-control"  name="automationOperation_name" placeholder="${I18n.system_please_input}操作名称" readonly="true">
						</div>
					</div>

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">操作地址<font color="black">*</font></label>
						<div class="col-sm-10">
							<input type="text" class="form-control"  name="automationOperation_url" placeholder="${I18n.system_please_input}操作地址" maxlength="1000">
						</div>
					</div>

					<div class="form-group">
						<label for="firstname" class="col-sm-2 control-label">微信号列表<font color="black">*</font></label>
						<div class="col-sm-10">
							<textarea class="textarea form-control" name="nickNameListStr" placeholder="${I18n.system_please_input}微信号列表" maxlength="10240000" style="height: 250px; line-height: 1.2;"></textarea>
						</div>
					</div>

					<div class="form-group">
						<label for="firstname" class="col-sm-2 control-label">操作设备列表<font color="black">*</font></label>
						<div class="col-sm-10">
							<textarea class="textarea form-control" name="currentDeviceListStr" placeholder="${I18n.system_please_input}操作参数" maxlength="10240000" style="height: 250px; line-height: 1.2;"></textarea>
						</div>
					</div>

					<hr>
					<div class="form-group">
						<div class="col-sm-offset-3 col-sm-6">
							<button type="submit" class="btn btn-primary">${I18n.system_opt}</button>
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
<script src="${request.contextPath}/static/js/wxAutomationOperation.index.1.js"></script>
</body>
</html>
