<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工具管理管理</title>
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
		<li class="active"><a href="${ctx}/tm/sysToolAll/">工具管理列表</a></li>
		<li><a href="${ctx}/tm/sysToolAll/form">工具管理添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="sysToolAll" action="${ctx}/tm/sysToolAll/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>工具分类：</label>
				<form:select id="toolName" path="toolName" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${tool_type}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li> 
			<li><label>规格：</label>
				<form:select id="specification" path="specification" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${tool_specification}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>

			<li><label>ID：</label>
				<input toolId="toolId" name="toolId" type="text" maxlength="50" class="input-medium" value="${toolId}"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>ID</th>
				<th>工具分类</th>
				<th>规格</th>
				<th>校验周期</th>
				<th>是否循环校验</th>
				<th>检验次数</th>
				<th>上次校验时间</th>
				<th>下次校验时间</th>

				
				<shiro:hasPermission name="tm:sysToolAll:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysToolAll">
			<tr>
				<td><a href="${ctx}/tm/sysToolAll/form?id=${sysToolAll.toolId}">
					${sysToolAll.toolId}
				</a></td>
				<td>${sysToolAll.toolName}</td>
				<td>${sysToolAll.specification}</td>
				<td>${sysToolAll.checkPeriod}</td>
				<td><c:choose>
					<c:when test="${sysToolAll.isCycle eq '0'}">
						是
					</c:when>
					<c:otherwise>
						否
					</c:otherwise>
				</c:choose></td>
				<td>${sysToolAll.checkTimes}</td>
				<td><fmt:formatDate value="${sysToolAll.preCheckDate}" pattern="yyyy/MM/dd"/> </td>
				<td><fmt:formatDate value="${sysToolAll.checkDate}" pattern="yyyy/MM/dd"/> </td>
				<shiro:hasPermission name="tm:sysToolAll:edit"><td>
    				<a href="${ctx}/tm/sysToolAll/form?id=${sysToolAll.toolId}">修改</a>
					<a href="${ctx}/tm/sysToolAll/delete?id=${sysToolAll.toolId}" onclick="return confirmx('确认要删除该工具管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>