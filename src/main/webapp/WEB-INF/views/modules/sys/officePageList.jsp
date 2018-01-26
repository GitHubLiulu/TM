<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/office/listPage");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/office/listPage">机构列表</a></li>
		<%-- <shiro:hasPermission name="sys:user:edit"><li><a href="${ctx}/sys/office/form1?parent.id=${office.id}">机构添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="office" action="${ctx}/sys/office/listPage" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>机构名称：</label>
				<form:input path="name"/>
				<label>归属区域：</label>
				<form:input path="area.name"/>
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			</li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>机构名称</th><th>归属区域</th><th>上级机构<th>机构编码</th><th>机构地址</th><th>备注</th><shiro:hasPermission name="sys:office:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="office">
			<tr>
				<td>${office.name}</td>
				<td>${office.area.name}</td>
				<td>${office.parent.name}</td>
				<td>${office.code}</td>
				<td>${office.address}</td>
				<td>${office.remarks}</td>
				<shiro:hasPermission name="sys:user:edit"><td>
    				<a href="${ctx}/sys/office/form1?id=${office.id}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>