<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>提醒周期定义管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/tm/sysRemindInfo/">提醒周期定义列表</a></li>
		<li class="active"><a href="${ctx}/tm/sysRemindInfo/form?id=${sysRemindInfo.id}">提醒周期定义<shiro:hasPermission name="tm:sysRemindInfo:edit">${not empty sysRemindInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="tm:sysRemindInfo:edit">查看</shiro:lacksPermission></a></li>
		<li><a href="${ctx}/tm/sysRemindInfo/batchForm">批量更新周期</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysRemindInfo" action="${ctx}/tm/sysRemindInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">工具ID：</label>
			<div class="controls">
				<form:input path="toolIdReal" htmlEscape="false" maxlength="64" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预警时间：</label>
			<div class="controls">
				<form:input path="period" htmlEscape="false" maxlength="8" class="input-medium required"/>
				<span class="help-inline"><font color="red">定义提前提醒的时间（单位：天）</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>