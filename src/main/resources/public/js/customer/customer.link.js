layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //订单列表展示
    var  tableIns = table.render({
        elem: '#customerLinkList',
        url : ctx+"/customer_link/list?cusId="+$("input[name='id']").val(),
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "customerLinkListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: 'linkName', title: '联系人',align:"center"},
            {field: 'sex', title: '性别',align:"center"},
            {field: 'zhiwei', title: '职位',align:"center"},
            {field: 'phone', title: '联系电话',align:"center"},
            {title: '操作',fixed:"right",align:"center", minWidth:150,templet:"#customerLinkListBar"}
        ]]
    });


    /**
     * 监听头部工具栏
     */
    table.on('toolbar(customerLink)',function (data){
        if(data.event=="add"){//添加客户信息

            var cusId = $("input[name='id']").val();
            //console.log(cusId);
            // 打开添加/修改客户信息的对话框
            openAddCustomerLinkDialog(cusId);
        }
    })


    /**
     * 监听行工具栏
     */
    table.on('tool(customerLink)', function (data) {

        if (data.event == "edit") { // 更新客户信息
            // 打开添加/修改客户信息的对话框
            // console.log(data);
            openAddOrUpdateCustomerLinkDialog(data.data.cusId,data.data.id);
        }else if(data.event=="del"){ // 删除客户信息
            //删除联系人
            deleteCustomerLinkman(data.data.id)

        }
    });


    /**
     * 更新
     * @param cusId
     * @param id
     */
    function  openAddOrUpdateCustomerLinkDialog(cusId,id){
        var title = "<h3>客户管理 - 添加联系人</h3>";
        var url = ctx + "/customer_link/toAddOrUpdateCustomerLinkPage?cusId="+cusId;
        if(id!=null||id!=''){
            url+="&id="+id;
        }

        // iframe层
        layui.layer.open({
            // 类型
            type: 2,
            // 标题
            title: title,
            // 宽高
            area: ['700px', '500px'],
            // url地址
            content: url,
            // 可以最大化与最小化
            maxmin:true
        });
    }

    /**
     * 添加
     * @param cusId
     */
    function  openAddCustomerLinkDialog(cusId){
        var title = "<h3>客户管理 - 添加联系人</h3>";
        var url = ctx + "/customer_link/toAddOrUpdateCustomerLinkPage?cusId="+cusId;

        // iframe层
        layui.layer.open({
            // 类型
            type: 2,
            // 标题
            title: title,
            // 宽高
            area: ['700px', '500px'],
            // url地址
            content: url,
            // 可以最大化与最小化
            maxmin:true
        });
    }

    function deleteCustomerLinkman(id){

        // 弹出确认框，询问用户是否确认删除
        layer.confirm('确定要删除该记录吗？',{icon:3, title:"联系人管理"}, function (index) {
            // 关闭确认框
            layer.close(index);
            // 发送ajax请求，删除记录
            $.ajax({
                type:"post",
                url:ctx + "/customer_link/delete",
                data:{
                    id:id
                },
                success:function (result) {
                    // 判断删除结果
                    if (result.code == 200) {
                        // 提示成功
                        layer.msg("删除成功！",{icon:6});
                        // 刷新表格
                        tableIns.reload();
                    } else {
                        // 提示失败
                        layer.msg(result.msg, {icon:5});
                    }
                }
            });
        });
    }

});
