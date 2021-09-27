<!DOCTYPE html>
<html>
<head>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form" style="width:80%;">
    <input name="cusId" type="hidden" value="${(cusId)!}"/>
    <input name="id" type="hidden" value="${(customerLinkman.id)!}"/>


    <div class="layui-form-item layui-row">
        <div class="layui-col-xs6">
            <label class="layui-form-label">联系人</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input"
                       name="linkName" id="linkName"  lay-verify="required" value="${(customerLinkman.linkName)!}" placeholder="请输入联系人">
            </div>
        </div>
        <div class="layui-col-xs6">
            <label class="layui-form-label">性别</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input"
                       name="sex" id="sex" lay-verify="required" value="${(customerLinkman.sex)!}" placeholder="请输入法人">
            </div>
        </div>
    </div>

    <div class="layui-form-item layui-row">
        <div class="layui-col-xs6">
            <label class="layui-form-label">职位</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input"
                       name="zhiwei"    value="${(customerLinkman.zhiwei)!}" placeholder="请输入区域">
            </div>
        </div>
        <div class="layui-col-xs6">
            <label class="layui-form-label">公司号码</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input"
                       name="officePhone" value="${(customerLinkman.officePhone)!}" placeholder="请输入客户经理">
            </div>
        </div>
    </div>

    <div class="layui-form-item layui-row">
        <div class="layui-col-xs6">
            <label class="layui-form-label">联系人号码</label>
            <div class="layui-input-block">
                <input type="text" class="layui-input"
                       name="phone" value="${(customerLinkman.phone)!}"  placeholder="请输入客户信用级别">
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
<script type="text/javascript" src="${ctx}/js/customerLinkman/add.update.js"></script>
</body>
</html>