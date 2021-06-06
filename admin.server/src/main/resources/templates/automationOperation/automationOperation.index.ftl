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
	<@netCommon.commonLeft "automationOperation" />

	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>真机自动化操作管理</h1>
		</section>

		<!-- Main content -->
		<section class="content">

			<div class="row">
				<#-- 阅读微信文章并点击广告 -->
				<div class="col-md-4 col-sm-6 col-xs-12" id="clickArticleAd">
					<div class="info-box bg-aqua">
						<span class="info-box-icon"><i class="fa fa-flag-o"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">阅读微信文章并点击广告</span>
							<span class="info-box-number">12台在线设备</span>
							<div class="progress">
								<div class="progress-bar" style="width: 100%"></div>
							</div>
							<span class="progress-description">在定向微信回话阅读微信文章并点击广告</span>
						</div>
					</div>
				</div>
				<#-- 同意好友请求 -->
				<div class="col-md-4 col-sm-6 col-xs-12" id="agreeToFriendRequest">
					<div class="info-box bg-yellow">
						<span class="info-box-icon"><i class="fa fa-calendar"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">同意好友请求</span>
							<span class="info-box-number">12台在线设备</span>
							<div class="progress">
								<div class="progress-bar" style="width: 100%"></div>
							</div>
							<span class="progress-description">对所有微信的微信会话进行同意好友请求</span>
						</div>
					</div>
				</div>
				<#-- 将群保存到通讯录 -->
				<div class="col-md-4 col-sm-6 col-xs-12" id="saveToAddressBook">
					<div class="info-box bg-green">
						<span class="info-box-icon"><i class="fa ion-ios-settings-strong"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">将群保存到通讯录</span>
							<span class="info-box-number">12台在线设备</span>
							<div class="progress">
								<div class="progress-bar" style="width: 100%"></div>
							</div>
							<span class="progress-description">对所有微信的微信会话进行同意好友请求</span>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<#-- 点赞和评论朋友圈 -->
				<div class="col-md-4 col-sm-6 col-xs-12" id="praiseAndCommentFriendsCircle">
					<div class="info-box bg-yellow">
						<span class="info-box-icon"><i class="fa fa-calendar"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">点赞和评论朋友圈</span>
							<span class="info-box-number">12台在线设备</span>
							<div class="progress">
								<div class="progress-bar" style="width: 100%"></div>
							</div>
							<span class="progress-description">在微信朋友圈中点赞和评论朋友圈</span>
						</div>
					</div>
				</div>
				<#-- 同意进群 -->
				<div class="col-md-4 col-sm-6 col-xs-12" id="agreeToJoinTheGroup">
					<div class="info-box bg-green">
						<span class="info-box-icon"><i class="fa ion-ios-settings-strong"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">同意进群</span>
							<span class="info-box-number">12台在线设备</span>
							<div class="progress">
								<div class="progress-bar" style="width: 100%"></div>
							</div>
							<span class="progress-description">对所有微信的微信会话进行同意进群</span>
						</div>
					</div>
				</div>
				<#-- 发送朋友圈 -->
				<div class="col-md-4 col-sm-6 col-xs-12" id="sendFriendCircle">
					<div class="info-box bg-aqua">
						<span class="info-box-icon"><i class="fa fa-flag-o"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">发送朋友圈</span>
							<span class="info-box-number">12台在线设备</span>
							<div class="progress">
								<div class="progress-bar" style="width: 100%"></div>
							</div>
							<span class="progress-description">在微信中发送朋友圈</span>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<#-- 根据微信昵称进行聊天 -->
				<div class="col-md-4 col-sm-6 col-xs-12" id="chatByNickName">
					<div class="info-box bg-green">
						<span class="info-box-icon"><i class="fa ion-ios-settings-strong"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">根据微信昵称进行聊天</span>
							<span class="info-box-number">12台在线设备</span>
							<div class="progress">
								<div class="progress-bar" style="width: 100%"></div>
							</div>
							<span class="progress-description">在微信中根据微信昵称进行聊天</span>
						</div>
					</div>
				</div>
				<#-- 分享微信文章到微信朋友圈 -->
				<div class="col-md-4 col-sm-6 col-xs-12" id="shareArticleToFriendCircle">
					<div class="info-box bg-aqua">
						<span class="info-box-icon"><i class="fa fa-flag-o"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">分享微信文章到微信朋友圈</span>
							<span class="info-box-number">12台在线设备</span>
							<div class="progress">
								<div class="progress-bar" style="width: 100%"></div>
							</div>
							<span class="progress-description">在定向微信会话中分享微信文章到微信朋友圈</span>
						</div>
					</div>
				</div>
				<#-- 通过V群添加其成员为好友 -->
				<div class="col-md-4 col-sm-6 col-xs-12" id="addGroupMembersAsFriends">
					<div class="info-box bg-yellow">
						<span class="info-box-icon"><i class="fa fa-calendar"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">通过V群添加其成员为好友</span>
							<span class="info-box-number">12台在线设备</span>
							<div class="progress">
								<div class="progress-bar" style="width: 100%"></div>
							</div>
							<span class="progress-description">在定向微信群中添加其成员为好友</span>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<#-- 转发微信消息 -->
				<div class="col-md-4 col-sm-6 col-xs-12" id="relayTheWxMessage">
					<div class="info-box bg-aqua">
						<span class="info-box-icon"><i class="fa fa-flag-o"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">转发微信消息</span>
							<span class="info-box-number">12台在线设备</span>
							<div class="progress">
								<div class="progress-bar" style="width: 100%"></div>
							</div>
							<span class="progress-description">在定向微信会话中转发微信消息</span>
						</div>
					</div>
				</div>
			</div>

		</section>
	</div>
	<!-- footer -->
	<@netCommon.commonFooter />
</div>

<!-- 弹窗.模态框 -->
<div class="modal fade" id="automationOperationModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" name="modalTitle">自动化操作</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal form" role="form">

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label"> 操作名称<font color="black">*</font></label>
						<div class="col-sm-10">
							<input type="text" class="form-control"  name="automationOperation_name" placeholder="${I18n.system_please_input}操作名称" readonly="true">
						</div>
					</div>

					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label"> 操作地址<font color="black">*</font></label>
						<div class="col-sm-10">
							<input type="text" class="form-control"  name="automationOperation_url" placeholder="${I18n.system_please_input}操作地址" maxlength="1000">
						</div>
					</div>

					<div class="form-group">
						<label for="firstname" class="col-sm-2 control-label">操作参数<font color="black">*</font></label>
						<div class="col-sm-10">
                            <textarea class="textarea form-control" name="automationOperation_param" placeholder="${I18n.system_please_input}操作参数" maxlength="10240000" style="height: 250px; line-height: 1.2;"></textarea>
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
<script src="${request.contextPath}/static/js/automationOperation.index.1.js"></script>
</body>
</html>
