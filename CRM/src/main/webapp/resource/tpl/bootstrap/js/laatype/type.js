var typeManager={
    selectAll:function(){
    $("#tbody").find("input[name='checkbox']").prop("checked", $("#selectContro").prop("checked"));
    },

        del:function (name, id){
            layer.confirm("确认删除\"" + name + "\"吗？", {icon: 3, title:'提示'}, function(data){
                if (data){
                        $.post(APP_PATH+"/delNavi/" + id, {}, function(msg){
                        if (msg.status){
                            layer.msg("删除成功");
                            naviManager.refresh();
                        } 
                        else{
                            layer.msg(msg.content);
                            naviManager.refresh();
                        }
                    })
                }
            });
       },
    refresh: function (){
        location.reload();
    },
            
     addNavi:function(){
           var index = layer.open({
                type: 2,
                title: '新增类别',
                closeBtn: 1,
                shade: 0.3,
                skin: 'layer-seat',
                area: ['600px', '400px'],
                content: APP_PATH+"/goAddType/",
                btn: ['确定', '取消'],
                yes: function (index) {
                    var typeName = layer.getChildFrame("#naviName").val();
                    var orderNo = layer.getChildFrame("#orderNo").val();
                    
                    
                    var isParent = layer.getChildFrame('input[name="isParent"]:checked').val();
                    var parentTypeId = layer.getChildFrame("#parentNaviId").val();
                    var img = layer.getChildFrame("#img").val();
                    if(isParent == 1 && parentTypeId != 0){
                        layer.msg("父节点不能再选择一个父节点");return;
                    }
                   
                    if(isParent == 0 && parentTypeId == 0){
                        layer.msg("必须选择一个父节点");return;
                    }
                    if(!typeName){
                        layer.msg("名字不能为空");return;
                    }
                    if(!orderNo){
                        layer.msg("排序编号不能为空");return;
                    }
                     var rule =  /^\d+$/;
                if (!rule.test(orderNo)||orderNo<=0) {
                   layer.msg("非法字符！");
                     return;
                }
                  
                    layer.confirm("确定新增？",  {icon:3,title:"提示"},function(index){     
                        var loadIndex =layer.load();
                        $.ajax({
                                url :APP_PATH+ "/addType/",
                                type : "POST",
                                data : {
                                        'typeName':typeName,
                                        'orderNo':orderNo,
                                        'img':img,
                                        'isParent':isParent,
                                        
                                        'parentId':parentTypeId
                                },
                                success : function(data) {
                                    layer.close(loadIndex);
                                    location.reload();
                                    layer.msg("新增成功");
                                }
                        });                               
                     });

                },
                cancel: function (index) {
                    //默认关闭
                }
            });

    },
    editNavi: function (nid){
           var index = layer.open({
                type: 2,
                title: '编辑类别',
                closeBtn: 1,
                shade: 0.3,
                skin: 'layer-seat',
                area: ['600px', '475px'],
                content: APP_PATH+"/getSingleType/"+nid,
                btn: ['确定', '取消'],
                yes: function (index) {
                    var naviName = layer.getChildFrame("#naviName").val();
                    var orderNo = layer.getChildFrame("#orderNo").val();
               
                    var isParent = layer.getChildFrame('input[name="isParent"]:checked').val();
                    var parentNaviId = layer.getChildFrame("#parentNaviId").val();
                    var img = layer.getChildFrame("#img").val();
                    if(isParent == 1 && parentNaviId != 0){
                        layer.msg("父节点不能再选择一个父节点");return;
                    }
                    if(isParent == 0 && parentNaviId == 0){
                        layer.msg("必须选择一个父节点");return;
                    }
                    if(!naviName){
                        layer.msg("名字不能为空");return;
                    }
                    if(!orderNo){
                        layer.msg("排序编号不能为空");return;
                    }
                     var rule =  /^\d+$/;
                if (!rule.test(orderNo)||orderNo<=0) {
                   layer.msg("非法字符！");
                     return;
                }
                  
                    layer.confirm("确定修改？", {icon: 3, title:'提示'}, function(index){          
                        var loadIndex=layer.load();
                        $.ajax({
                                url :APP_PATH+ "/updateType/",
                                type : "POST",
                                data : {
                                        'typeId':layer.getChildFrame("#naviId").val(),
                                        'typeName':naviName,
                                        'orderNo':orderNo,
                                        'img':img,
                                        'isParent':isParent,
                                        
                                        'parentId':parentNaviId
                                },
                                success : function(data) {
                                    layer.close(loadIndex);
                                    layer.msg("修改成功");
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