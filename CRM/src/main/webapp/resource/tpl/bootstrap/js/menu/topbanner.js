/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
  function editSupInfo(supid){
        var index = layer.open({
            type: 2,
            title: '用户信息',
            closeBtn: 1,
            shade: 0.3,
            skin: 'layer-seat',
            area: ['600px', '350px'],
            content: "/CRM/app/laaSup/toupdateSup/?supid="+supid         
        });
    }

    	
    function logout(){
        $.ajax({
            url: "/CRM/app/laaUser/logout",
            type: "POST",
            data: {},
            success: function (data) {
                location.href = "/CRM/app/index";
            }
        });
    }
    
    function toChangePWD(){
         var indexMain = layer.open({
            type: 2,
            title: '修改密码',
            closeBtn: 1,
            shade: 0.3,
            skin: 'layer-seat',
            area: ['600px', '300px'],
            content: "/CRM/app/laaUser/goChangePsw/<#if laaUserUid??>${laaUserUid}<#else>0</#if>",
            btn: ['确定', '取消'],
            yes: function (index) {
                var oldPass = layer.getChildFrame("#oldPass").val();
                var newPass = layer.getChildFrame("#newPass").val();
                var reNewPass = layer.getChildFrame("#reNewPass").val();
                if(oldPass == ""){
                    layer.msg("请输入原密码");return;
                }
                if(newPass == ""){
                    layer.msg("请输入新密码");return;
                }
                if(reNewPass == ""){
                    layer.msg("请确认新密码");return;
                }
                if(newPass != reNewPass){
                    layer.msg("两次输入密码不一致");return;
                }
                layer.confirm("确定修改？",  function(index){     
                    var loadIndex =layer.load();                   
                    $.ajax({
                        url : "/CRM/app/laaUser/changePsw/",
                        type : "POST",
                        data : {
                                'uId' : layer.getChildFrame("#uId").val(),
                                'oldPass':oldPass,
                                'newPass':newPass
                        },
                        success : function(data) {
                             layer.close(loadIndex);
                            if(data.status){
                                layer.msg("修改成功");
                            }else{
                                layer.alert(data.content, {icon: 2});
                            }
                        }
                    });       
                    layer.close(indexMain);

                 });

            },
            cancel: function (index) {
                //默认关闭
            }
        });
    }
    function toReviewCompany(){
        var index = layer.open({
            type: 2,
            title: '关于',
            closeBtn: 1,
            shade: 0.3,
            skin: 'layer-seat',
            area: ['500px', '250px'],
            content: "/CRM/app/laaUser/about",
            btn: ['关闭'],
            yes: function (index) {
                layer.close(index);
            }
          
        });
    }
     function toYun(){
        var uid =$("#userid_global").val();
        if($("#userlevel_global").val() ==1){
            layer.msg("对不起，您没有权限查看此内容");
            return;
        }
         window.location.href = "/CRM/app/common/toYunIndex/1";
    }

    function showdiv(){
        
       var status= $("#info").css("display");
       if(status=='none')
           $("#info").css("display","block");
        if(status=='block')
           $("#info").css("display","none");
    }


//setTimeout(showTips, 5000);//10s刷一次                     
//function showTips(){
//    var url =window.location.href;
//    if(url.indexOf("/registerManager")>=0){
//         return;
//     }
//    var nCount =$("#auditTipsCount").val();
//    var sid =$("#audit_tips_flag").val();
//    var userid =$("#userid_global").val();
//    $.ajax({
//        url:"/CRM/app/laaUser/queryInfoWait4Audit/",
//        dataType: "json",
//        type:"POST",
//        data:{uid:userid},
//        success:function(data){
//            if(data.status){
//                if(data.params.count>0){    
//                    if(sid==1 && nCount ==data.params.count){
//                        return;
//                    }
//                    nCount =data.params.count;
//                    var index =layer.open({
//                        type: 1 
//                        ,title: '友情通知'
//                        ,area: ['300px', '175px']
//                        ,shade: 0
//                        ,offset: 'rb' //右下角弹出
//                        ,shift: 2
//                        ,content: '<div style="text-align:center;margin-top:10px;">有<span style="color:red;"><strong>'+data.params.count+'</strong></span>个厂商申请待审核<br><a href="/CRM/app/laaUser/registerManager">点击进入</a></div>'
//                        ,btn: ['关闭'] //只是为了演示
//                        ,yes: function(index){
//                            setSesstion(1,nCount);
//                            layer.close(index);
//                        }
//                        ,cancel:function(index){
//                            setSesstion(1,nCount);
//                            layer.close(index);
//                        }
//                        ,zIndex: layer.zIndex //重点1
//                        ,success: function(layero){
//                            layer.setTop(layero); //重点2
//                        }
//                    });
//                }
//            }
//        }
//    });
//    }
//    
    function setSesstion(a,b){
        $.ajax({
            url:"/CRM/app/laaUser/setSessionParams/",
            dataType: "json",
            type:"POST",
            data:{flag:a,count:b},
            success:function(data){
             }
         });
    }

