var attrManager={
    selectAll:function(){
    $("#tbody").find("input[name='checkbox']").prop("checked", $("#selectContro").prop("checked"));
    },

        del:function (name, id){
            layer.confirm("确认删除\"" + name + "\"吗？", {icon: 3, title:'提示'}, function(data){
                if (data){
                        $.post(APP_PATH+"/delAttr/" + id, {}, function(msg){
                        if (msg.status){
                            layer.msg("删除成功");
                            attrManager.refresh();
                        } 
                        else{
                            layer.msg(msg.content);
                            attrManager.refresh();
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
                content: APP_PATH+"/goAddAttr/",
                btn: ['确定', '取消'],
                yes: function (index) {
                    var typeName = layer.getChildFrame("#naviName").val();
                    var orderNo = layer.getChildFrame("#orderNo").val();
                    
                  
                    var parentTypeId = layer.getChildFrame("#parentNaviId").val();
                  
                   
                 
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
                                url :APP_PATH+ "/addAttr/",
                                type : "POST",
                                data : {
                                        'attrName':typeName,
                                        'orderNo':orderNo,
                                        
                                       
                                        
                                        'typeId':parentTypeId
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
                content: APP_PATH+"/getSingleAttr/"+nid,
                btn: ['确定', '取消'],
                yes: function (index) {
                    var naviName = layer.getChildFrame("#naviName").val();
                    var orderNo = layer.getChildFrame("#orderNo").val();
               
                   
                    var parentNaviId = layer.getChildFrame("#parentNaviId").val();
                    
                
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
                                url :APP_PATH+ "/updateAttr/",
                                type : "POST",
                                data : {
                                        'attrId':layer.getChildFrame("#naviId").val(),
                                        'attrName':naviName,
                                        'orderNo':orderNo,
                                     
                                        
                                        'typeId':parentNaviId
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