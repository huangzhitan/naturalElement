var roleManager={
    selectAll: function (){
         $("#tbody").find("input[name='checkbox']").prop("checked", $("#selectContro").prop("checked"));
    },
            //删除
    del:function (name,id){
        layer.confirm("确认删除\""+name+"\"吗？",{icon: 3, title:'提示'},function(data){
            if(data){
                $.post(APP_PATH+"/delRole/"+id,{},function(msg){
                    if(msg.status){
                        layer.msg("删除成功");
                        roleManager.refresh();
                    }else{
                        layer.msg(msg.content);
                        roleManager.refresh();
                    }
                })
            }
        });
    },
         
    //刷新用户
    refresh:function (){
       location.reload();
    },
            
            
    editRole: function (roleId){
          var index = layer.open({
                type: 2,
                title: '编辑角色',
                closeBtn: 1,
                shade: 0.3,
                skin: 'layer-seat',
                area: ['600px', '350px'],
                content: APP_PATH+"/getSingleRole/"+roleId,
                btn: ['确定', '取消'],
                yes: function (index) {
                    var name =  layer.getChildFrame("#name").val();
                    if(!name){
                        layer.msg("名字不能为空");return;
                    }
                     if(!layer.getChildFrame("#level").val()){
                        layer.msg("请选择用户等级");return;

                    }
                     var navis=[];
                     layer.getChildFrame('input[name="navis"]:checked').each(function(){
                        navis.push($(this).val());


                    });
                    layer.confirm("确定修改？",{icon:3,title:'提示'},  function(index){     
                        $.ajax({
                                url : APP_PATH+"/updateRole/",
                                type : "POST",
                                data : {
                                        'roleId': layer.getChildFrame("#roleId").val(),
                                        'name':name,
                                        'desc': layer.getChildFrame("#desc").val(),
                                        naviIds:navis.toString(),
                                        'level': layer.getChildFrame("#level").val()
                                },
                                success : function(data) {
                                    location.reload();
                                    layer.msg("修改成功");
                                }
                        });  
                     });
                },
                 cancel: function (index) {
                    //默认关闭
                }
            });

    },
addRole: function (){
      var index = layer.open({
                type: 2,
                title: '新增角色',
                closeBtn: 1,
                shade: 0.3,
                skin: 'layer-seat',
                area: ['600px', '350px'],
                content: APP_PATH+"/goAddRole/",
                btn: ['确定', '取消'],
                yes: function (index) {
                    var name = layer.getChildFrame("#name").val();
                    if(!name){
                        layer.msg("名字不能为空");return;
                    }
                    if(!layer.getChildFrame("#leveladd").val()){
                        layer.msg("请选择用户等级");return;

                    }
                    layer.confirm("确定新增？",{icon:3,title:'提示'},   function(index){     
                        var loadIndex =layer.load();                            
                        var navis=[];
                        layer.getChildFrame('input[name="navis"]:checked').each(function(){
                            navis.push($(this).val());

                        });
                        $.ajax({
                                url : APP_PATH+"/addRole/",
                                type : "POST",
                                data : {
                                        'name':name,
                                        'desc':layer.getChildFrame("#desc").val(),
                                        naviIds:navis.toString(),
                                        'level':layer.getChildFrame("#leveladd").val()
                                },
                                success : function(data) {
                                    layer.msg("新增成功")
                                    location.reload();
                                }
                        });       
                     });

                },
                cancel: function (index) {
                    //默认关闭
                }
            });
    }
        
}