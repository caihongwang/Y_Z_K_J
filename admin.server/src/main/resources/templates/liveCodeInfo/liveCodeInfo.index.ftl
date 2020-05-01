<!DOCTYPE html>
<html>
<head>
	<#import "../common/common.macro.ftl" as netCommon>
	<@netCommon.commonStyle />
	<title>${I18n.admin_name}</title>
</head>
<body class="hold-transition skin-blue sidebar-mini <#if cookieMap?exists && cookieMap["xxljob_adminlte_settings"]?exists && "off" == cookieMap["xxljob_adminlte_settings"].value >sidebar-collapse</#if> ">
<div class="wrapper">
	<!-- header -->
	<@netCommon.commonHeader />
	<!-- left -->
	<@netCommon.commonLeft "liveCodeInfo" />
	<div class="content-wrapper">
		<section class="content-header">
			<h1>${I18n.liveCodeInfo}</h1>
		</section>
		<section class="content">
			<div class="callout callout-info">
				<p>
					点击 <a target="_blank" href="http://www.91caihongwang.com/livecode">我的活码</a> 管理
				</p>
			</div>
		</section>
	</div>
	<@netCommon.commonFooter />
</div>
<@netCommon.commonScript />
</body>
</html>
