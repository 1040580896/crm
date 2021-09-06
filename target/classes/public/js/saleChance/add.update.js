layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    /**
     * 监听表单submit事件
     *     form.on('submit(按钮元素的lay-filter属性值)',function (data){

             });
     */
    form.on('submit(addOrUpdateSaleChance)',function (data){
        //提交数据时的加载层 （https://layer.layui.com/）
        var index = layer.msg("数据提交中,请稍后...",{ icon:16, // 图标
            time:false, // 不关闭
            shade:0.8 // 设置遮罩的透明度
        });

        //发送ajax请求
        var url = ctx+"/sale_chance/add";
        console.log(url);
        console.log(data)
        $.post(url,data.field,function (result){
            //判断操作是否执行成功 200=成功
            if(result.code==200){
                //成功
                //提示成功
                layer.msg("操作成功",{icon:6});
                //关闭加载层
               layer.close(index);
                //关闭弹出层
                layer.closeAll("iframe");
                // 刷新父页面，重新渲染表格数据
                parent.location.reload();

            } else {
                layer.msg(result.msg,{icon:5})
            }
        });

        //阻止表单提交
        return false;

    });


    /**
     * 关闭弹出层
     */
    $("#closeBtn").click(function (){
        //假设这是iframe页
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭

    });


    
});