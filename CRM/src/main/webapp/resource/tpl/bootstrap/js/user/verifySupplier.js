function valid_email(email) {
    var patten = new RegExp(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$/);
    return patten.test(email);
}
function next(){
  if($("#errMsg_uname").text()!="")
        return;
  var code =$("#code").val();
  if(code==""){
      $("#errMsg_code").text("请输入验证码");
      return;
  }    
                  
  var loadIndex =layer.load();
  $.ajax({
           type:"post",
           url:"/LAA/app/index/toModifiedPWD?code="+code,
           contentType:"application/json",
           dataType:"json",
           success:function(data){
               layer.close(loadIndex);
               if(!data.status){
                   $("#errMsg_code").text("验证码错误");
                    return;
               }     
               else{
                   $("#page1").css("display","none");
                   var html=[];
                   html.push("<p class='reg-input'><span> 新密码：</span><input id='password1' name='password1' class='CJ_regTxt' type='password' placeholder='请输入新密码'> <b class='error' id='errMsg_p1' style='color: #F00'></b></p>");
                   html.push("<p class='reg-input'><span> 确认密码：</span><input id='password2' name='password2' class='CJ_regTxt' type='password' placeholder='请输入新密码'> <b class='error' id='errMsg_p2' style='color: #F00'></b></p>")
                   html.push("<div class='reg-sbt'><input class='btn btn-default btn-success control-btn' style='padding: 5px 185px !important;' onclick='commit()' value='完成' type='button'></div>");
                   $("#page2").html("");
                   $("#page2").append(html.join("")); 
                   $("#page2").css("display","block");       
                   haveRightCode=true;
               }
     }});
  
}
function commit(){
  var p1 =$("#password1").val();
  var p2 =$("#password2").val();
  if(p1==""){
    $("#errMsg_p1").text("密码不能为空");
    return;
  }
  if(p1 != p2){
    $("#errMsg_p2").text("两次的密码不一致");
    return;
  }
  $("#errMsg_p1").text("");
  $("#errMsg_p2").text("");
  if(haveRightCode==false)
      return;
  var supid =$("#supid").val();
  var uid =$("#uid").val();
  var loadIndex =layer.load();
   $.ajax({
           type:"post",
           url:"/LAA/app/index/modeifiedPWD?supid="+supid+"&uid="+uid+"&pwd="+p2,
           contentType:"application/json",
           dataType:"json",
           success:function(data){
                layer.close(loadIndex);
              if(data.status){
                  layer.alert("修改成功！", {
                                closeBtn: 0
                            }, function(){
                              window.location.href="/LAA/app/index";
                            });
              }
     }});
}
function getContacts(){
     var supLoginName = $("#uname").val();
//     var b=valid_email(supLoginName)
//     if(!b){
//         $("#errMsg_uname").text("登录账户不符合规范");
//        return;
//    }
    $.ajax({
           type:"post",
           url:"/LAA/app/index/getContacts?uname="+supLoginName,
           contentType:"application/json",
           dataType:"json",
           success:function(data){
               var list=data.emailList;
               if(data.user==null){
                  $("#errMsg_uname").text("当前账户不存在");
                   return;
               }
               else{
                   $("#errMsg_uname").text("");
                   $("#uid").val(data.user[1]);//用户ID
                   $("#supid").val("");//供应商ID
                   if(data.supid !=null){
                        $("#supid").val(data.supid[0]);
                   }
                   var html=[];
                   html.push("<option>"+data.user[0]+"</option>");
                   if(list !=null){
                        for(var i=0;i<list.length;i++){
                               html.push("<option>"+list[i]+"</option>");
                        }
                    }
                    $("#contacts").html("");
                    $("#contacts").append(html.join(""));
               }
     }});
}
 
 function sendCode(){
    var email=$("#contacts option:selected").text();
    if(email ==""){
        layer.msg("请选择邮箱用于验证");
        return;
    }
      var loadIndex =layer.load();
      $.ajax({
           type:"post",
           url:"/LAA/app/index/sendMail?email="+email,
           contentType:"application/json",
           dataType:"json",
           success:function(data){
               layer.close(loadIndex);
               layer.msg("验证码已发送,请登录邮箱<br/><div style='text-align:center;'>"+email+"</div>进行查看",{time:5000});
                var count =60;
                var countdown = setInterval(CountDown, 1000);
                function CountDown() {   
                    $("#sendBtn").removeAttr('href');     
                    $("#sendBtn").text("重新发送（" + count + "）"); 
                    if (count == 0) { 
                        $("#sendBtn").text("获取验证码"); 
                        $("#sendBtn").attr('href',"javascript:sendCode()");     
                        clearInterval(countdown); 
                    } 
                    count--; 
                } 
     }});
}