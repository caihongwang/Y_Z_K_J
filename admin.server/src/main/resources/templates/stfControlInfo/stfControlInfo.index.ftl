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
	<@netCommon.commonLeft "stfControlInfo" />
	<div class="content-wrapper">
		<section class="content-header">
			<h1><a target="_blank" href="http://192.168.43.110:7100/">${I18n.stfControlInfo}</a>（注：请在工作室内局域网打开）</h1>
		</section>
		<section class="content">
			<iframe src="http://192.168.43.110:7100/" frameborder="0" scrolling="0" style="width:100%;height:785.15px;"></iframe>
		</section>
	</div>
	<@netCommon.commonFooter />
</div>
<@netCommon.commonScript />
</body>
</html>
