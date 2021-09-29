<!DOCTYPE html>
<html>
<head>
	<title>交往记录管理</title>
	<#include "../common.ftl">
</head>
<body class="childrenBody">
	<div class="layui-col-md12">
		<div class="layui-card">
			<div class="layui-card-body">
				<form class="layui-form" >
					<input name="id" type="hidden" value="${(customer.id)!}"/>

					<div class="layui-form-item layui-row">
						<div class="layui-col-xs6">
							<label class="layui-form-label">客户名</label>
							<div class="layui-input-block">
								<input type="text" class="layui-input"
									   name="customerName" id="customerName"  value="${(customer.name)!}" readonly="readonly">
							</div>
						</div>
						<div class="layui-col-xs6">
							<label class="layui-form-label">客户编号</label>
							<div class="layui-input-block">
								<input type="text" class="layui-input"
									   name="chanceSource" id="chanceSource" value="${(customer.khno)!}" readonly="readonly">
							</div>
						</div>
					</div>

				</form>
			</div>
		</div>
	</div>

	<#--头部按钮-->
	<div class="layui-col-md12">
		<table id="customerContact" class="layui-table"  lay-filter="customerContact"></table>
	</div>




		<script type="text/html" id="toolbarDemo">
			<div class="layui-btn-container">
				<a class="layui-btn layui-btn-normal addNews_btn" lay-event="add">
					<i class="layui-icon">&#xe608;</i>
					添加交往记录
				</a>
				<#--<a class="layui-btn layui-btn-normal addNews_btn" lay-event="confirm">-->
				<#--	<i class="layui-icon">&#xe608;</i>-->
				<#--	确认流失-->
				<#--</a>-->
			</div>
		</script>

		<!--行操作-->
		<script id="customerContactListBar" type="text/html">
			<a class="layui-btn layui-btn-xs" id="edit" lay-event="edit">编辑</a>
			<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
		</script>



	<script type="text/javascript" src="${ctx}/js/customerContcat/customer.contact.js"></script>
</body>
</html>