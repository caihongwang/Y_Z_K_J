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
	<@netCommon.commonLeft "webDiskInfo" />
	<div class="content-wrapper">
		<section class="content-header">
			<h1><a target="_blank" href="http://nextcloud.yzkj.store/index.php">${I18n.webDiskInfo}</a></h1>
		</section>
		<section class="content">
			<iframe src="http://nextcloud.yzkj.store/index.php" frameborder="0" scrolling="0" style="width:100%;height:785.15px;"></iframe>
		</section>
	</div>
	<@netCommon.commonFooter />
</div>
<@netCommon.commonScript />
</body>
</html>
