layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    /**
     * 加载数据表格
     */
    var tableIns = table.render({
        id:'userTable'
        // 容器元素的ID属性值
        ,elem: '#userList'
        // 容器的高度 full-差值
        ,height: 'full-125'
        // 单元格最小的宽度
        ,cellMinWidth:95
        // 访问数据的URL（后台的数据接口）
        ,url: ctx + '/user/list'
        // 开启分页
        ,page: true
        // 默认每页显示的数量
        ,limit:10
        // 每页页数的可选项
        ,limits:[10,20,30,40,50]
        // 开启头部工具栏
        ,toolbar:'#toolbarDemo'
        // 表头
        ,cols: [[
            // field：要求field属性值与返回的数据中对应的属性字段名一致
            // title：设置列的标题
            // sort：是否允许排序（默认：false）
            // fixed：固定列
            {type:'checkbox', fixed:'center'}
            ,{field: 'id', title: '编号',  sort: true, fixed: 'left'}
            ,{field: 'userName', title: '用户名称', align:'center'}
            ,{field: 'trueName', title: '真实姓名', align:'center'}
            ,{field: 'email', title: '用户邮箱', align:'center'}
            ,{field: 'phone', title: '用户号码', align:'center'}
            ,{field: 'createDate', title: '创建时间', align:'center'}
            ,{field: 'updateDate', title: '修改时间', align:'center'}
            ,{title:'操作',templet:'#userListBar', fixed: 'right', align:'center', minWidth:150}
        ]]
    });

    /**
     * 搜索按钮多点击事件
     */
    $(".search_btn").click(function (){

        /**
         * 表格重载
         * 多条件查询
         */
        tableIns.reload({
            //设置需要给后端多参数
            where: { //设定异步数据接口的额外参数，任意设
                //通过文本框的值，设置传递的参数
                userName: $("[name='userName']").val() //用户名称
                ,email: $("[name='email']").val() //邮箱
                ,phone:$("[name='phone']").val() // 手机号
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });


    /**
     * 监听头部工具栏
     */
    table.on('toolbar(users)',function (data){
        if(data.event=="add"){//添加用户

            // 打开添加/修改用户的对话框
            openAddOrUpdateUserDialog();
        }else if(data.event=="del"){

        }
    })

    /**
     * 打开添加/修改用户的对话框
     */
    function openAddOrUpdateUserDialog(id) {
        var title = "<h3>用户管理 - 添加用户</h3>";
        var url = ctx + "/user/toAddOrUpdateUserPage";
        // 判断id是否为空；如果为空，则为添加操作，否则是修改操作
        if (id != null && id != '') {
            title = "<h3>用户管理 - 更新用户</h3>";
            url+= "?id="+id; // 传递主键，查询数据
        }

        // iframe层
        layui.layer.open({
            // 类型
            type: 2,
            // 标题
            title: title,
            // 宽高
            area: ['650px', '400px'],
            // url地址
            content: url,
            // 可以最大化与最小化
            maxmin:true
        });
    }

    /**
     * 监听行工具栏
     */
    table.on('tool(users)', function (data) {

        if (data.event == "edit") { // 更新用户

            // 打开添加/修改用户的对话框
            openAddOrUpdateUserDialog(data.data.id);

        }

    });







});