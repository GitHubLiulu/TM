<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>提醒周期定义管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/tm/sysRemindInfo/">提醒周期定义列表</a></li>
		<shiro:hasPermission name="tm:sysRemindInfo:edit"><li><a href="${ctx}/tm/sysRemindInfo/form">提醒周期定义添加</a></li></shiro:hasPermission>
		<li><a href="${ctx}/tm/sysRemindInfo/batchForm">批量更新周期</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="sysRemindInfo" action="${ctx}/tm/sysRemindInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		
		<ul class="ul-form">
		
			<li><label>工具ID：</label><form:input path="toolId" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>工具ID</th>
				<th>提醒时间</th>
				<shiro:hasPermission name="tm:sysRemindInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysRemindInfo">
			<tr>
				<td><a href="${ctx}/tm/sysRemindInfo/form?id=${sysRemindInfo.toolId}">
					<%-- <fmt:formatDate value="${sysRemindInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/> --%>
					${sysRemindInfo.toolIdReal}
				</a></td>
				<td>提前${sysRemindInfo.period}天提醒</td>
				<shiro:hasPermission name="tm:sysRemindInfo:edit"><td>
    				<a href="${ctx}/tm/sysRemindInfo/form?id=${sysRemindInfo.toolId}">修改</a>
					<a href="${ctx}/tm/sysRemindInfo/delete?id=${sysRemindInfo.toolId}" onclick="return confirmx('确认要删除该提醒周期定义吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>