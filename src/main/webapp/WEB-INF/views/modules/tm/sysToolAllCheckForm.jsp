<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工具管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				
				submitHandler: function(form){
					$("#inputForm input").removeAttr("disabled");
					$("#inputForm select").removeAttr("disabled");
					//$("#isCycle input").attr("disabled", true)
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
			//alert($('input[type=radio][name=isCycle]').val());
			//alert($("#isCycle").val())
			if ($("#isCycleU").val() == 0) {
                $("#checkPeriod").show();
                $("#checkTime").hide();
			} else {
                $("#checkTime").show();
                $("#checkPeriod").hide();
			}
            $('input[type=radio][name=isCycle]').change(function() {
                if (this.value == '0') {
                    $("#checkPeriod").show();
                    $("#checkTime").hide();
                }
                else if (this.value == '1') {
                    $("#checkTime").show();
                    $("#checkPeriod").hide();
                }
            });
			$("#inputForm input").attr("disabled", true);
			$("#inputForm select").attr("disabled", true);
			//$("#inputForm textarea").attr("disabled", true);
			$("#btnSubmit").removeAttr("disabled");
			$("#btnCancel").removeAttr("disabled");
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/tm/sysToolAll/">工具检验</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysToolAll" action="${ctx}/tm/sysToolAll/checkSave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="toolId"/>
		<input type="hidden" name="isCycleU" id="isCycleU"  value="${sysToolAll.isCycle}"/>
		<sys:message content="${message}"/>		
		<%-- <div class="control-group">
			<label class="control-label">tool_id：</label>
			<div class="controls">
				<form:input path="toolId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">工具种类:</label>
			<div class="controls">
				<form:select path="toolName" class="input-medium">
					<form:options items="${fns:getDictList('tool_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格：</label>
			<div class="controls">
				<form:select path="specification" class="input-medium">
					<form:options items="${fns:getDictList('tool_specification')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">购买时间：</label>
			<div class="controls">
				<input name="buyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${sysToolAll.buyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否循环校验：</label>
			<div class="controls">
				<form:radiobuttons path="isCycle" items="${fns:getDictList('is_cycle')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">单次校验即校验一次后，不再校验，反之即为多次校验</font></span>
			</div>
		</div>
		<div class="control-group" id="checkTime">
			<label class="control-label">校验时间：</label>
			<div class="controls">
				<input name="checkDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required "
					   value="<fmt:formatDate value="${sysToolAll.checkDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group" id="checkPeriod">
			<label class="control-label">校验周期：</label>
			<div class="controls">
				<form:input path="checkPeriod" htmlEscape="false" maxlength="8" class="input-medium required"/>
				<span class="help-inline"><font color="red">(单位：天)工具循环校验周期，即校验过一次之后，该工具进入下一次查验循环。</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数量：</label>
			<div class="controls" >
				<form:input path="amount" htmlEscape="false" maxlength="64" class="input-medium "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">保管人：</label>
			<div class="controls">
				<form:input path="keeper" htmlEscape="false" maxlength="64" class="input-medium "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">保管人电话：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="256" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="确认校验"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>