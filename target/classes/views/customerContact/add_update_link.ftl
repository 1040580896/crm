<!DOCTYPE html>
<html>
<head>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;">
    <input name="cusId" type="hidden" value="${(cusId)!}"/>
    <input name="id" type="hidden" value="${(customerContact.id)!}"/>


    <div class="layui-form-item layui-row">
        <div class="layui-col-xs6">
            <label class="layui-form-label">地址</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input"
                       name="address" id="address"  lay-verify="required" value="${(customerContact.address)!}" placeholder="请输入联系人">
            </div>
        </div>
        <div class="layui-col-xs6">
            <label class="layui-form-label">内容</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input"
                       name="overview" id="overview" lay-verify="required" value="${(customerContact.overview)!}" placeholder="请输入法人">
            </div>
        </div>
    </div>


    <div class="layui-form-item layui-row layui-col-xs12">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-lg" lay-submit=""
                    lay-filter="addOrUpdateCustomerLinkman">确认
            </button>
            <button class="layui-btn layui-btn-lg layui-btn-normal" id="closeBtn">取消</button>
        </div>
    </div>


</form>
<script type="text/javascript" src="${ctx}/js/customerContcat/add.update.js"></script>
</body>
</html>