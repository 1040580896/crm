layui.use(['table','layer'],function(){
       var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;


       /**
        * 加载数据表格
        */
       var tableIns = table.render({
              id: 'roleTable'
              // 容器元素的ID属性值
              , elem: '#roleList'
              // 容器的高度 full-差值
              , height: 'full-125'
              // 单元格最小的宽度
              , cellMinWidth: 95
              // 访问数据的URL（后台的数据接口）
              , url: ctx + '/role/list'
              // 开启分页
              , page: true
              // 默认每页显示的数量
              , limit: 10
              // 每页页数的可选项
              , limits: [10, 20, 30, 40, 50]
              // 开启头部工具栏
              , toolbar: '#toolbarDemo'
              // 表头
              , cols: [[
                     // field：要求field属性值与返回的数据中对应的属性字段名一致
                     // title：设置列的标题
                     // sort：是否允许排序（默认：false）
                     // fixed：固定列
                     {type: 'checkbox', fixed: 'center'}
                     , {field: 'id', title: '编号', sort: true, fixed: 'left'}
                     , {field: 'roleName', title: '角色名称', align: 'center'}
                     , {field: 'roleRemark', title: '角色备注', align: 'center'}
                     , {field: 'createDate', title: '创建时间', align: 'center'}
                     , {field: 'updateDate', title: '修改时间', align: 'center'}
                     , {title: '操作', templet: '#roleListBar', fixed: 'right', align: 'center', minWidth: 150}
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
                            roleName: $("[name='roleName']").val() //用户名称
                     }
                     ,page: {
                            curr: 1 //重新从第 1 页开始
                     }
              });
       });


       /**
        * 监听头部工具栏
        */
       table.on('toolbar(roles)',function (data){
              //判断lay-event属性
              if(data.event=="add"){// 添加操作
                     //打开添加/更新角色的对话框
                     openAddOrUpdateRoleDiaLog();
              }
       });

       /**
        * 监听行工具栏
        */
       table.on('tool(roles)',function (data){
              //判断lay-event属性
              if(data.event=="edit"){// 修改角色
                     //打开添加/更新角色的对话框
                     openAddOrUpdateRoleDiaLog(data.data.id);
              }else if(data.event=="del"){
                     deleteRole(data.data.id);
              }
       });

       /**
        * 打开添加/更新角色的对话框
        */
       function openAddOrUpdateRoleDiaLog(roleID){
              //准备一个标题
              var title = "<h3>角色管理 - 角色添加</h3>"
              var url = ctx+"/role/toAddOrUpdateRolePage";

              //如果roleID不为空，表示修改角色
              if(roleID!=null&&roleID!=''){
                     title = "<h3>角色管理 - 角色更细</h3>"
                     url+="?roleId="+roleID;
              }

              layui.layer.open({
                     title:title,
                     content:url,
                     area:["500px","400px"],
                     type:2,
                     maxmin:true
              });
       }

       /**
        * 删除角色
        * @param roleId
        */
       function deleteRole(roleId){
       // 弹出确认框，询问用户是否确认删除
       layer.confirm('确定要删除该记录吗？',{icon:3, title:"角色管理"}, function (index) {
              // 关闭确认框
              layer.close(index);

              // 发送ajax请求，删除记录
              $.ajax({
                     type:"post",
                     url:ctx + "/role/delete",
                     data:{
                            roleId:roleId
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
