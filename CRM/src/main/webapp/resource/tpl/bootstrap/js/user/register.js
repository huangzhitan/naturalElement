function uploadImg(id, pid) {
    $.upload({
        // 上传地址
        url: '/LAA/app/index/importimg',
        // 文件域名字
        fileName: 'file',
        // 其他表单数据
        params: {},
        // 上传完成后, 返回json, text
        dataType: 'json',
        // 上传之前回调,return true表示可继续上传
        onSend: function () {
            return true;
        },
        // 上传之后回调
        onComplate: function (data) {
            var p = data.params;
            if (data.status) {
                layer.msg("上传成功");
                $("#" + id).val(p.longname);
                $("#" + pid).val(p.shortname);

            } else {
                layer.msg(data.content, function () {
                });
            }
        }
    });
}
function queryData() {
    $.ajax({
        type: "post",
        url: "/LAA/app/index/querycompany",
        success: function (data) {
            var companys = data.params.companys;
            if (companys ==null) {
                $("#divfooter").html("");
                $("#divfooter").html("<a href=http://www.leosys.com.cn/ target=_blank>"
                        + " copyright Right@leosys.com.cn Powered By 利昂软件  7*24小时技术支持与需求受理 电话：153-3028-5591</a>");
            } else {
                $("#divfooter").html("");
                var htmls = "<a href=" + companys.cgswz + " target=_blank>" + companys.cqita + "</a>";
                $("#divfooter").html(htmls);
            }
        }});
} 


        $(document).ready(function () {
                addRow();
                queryData();
         });
         function addRow(){
                var newTR = document.getElementById("lxrtbody").insertRow(document.getElementById("lxrtbody").rows.length);
                var xuhao=newTR.rowIndex.toString();
                newTR.id="tr"+xuhao;
                //alert(xuhao);
                var newNameTD = newTR.insertCell(0);
                newNameTD.innerHTML = "<td><input name='person' class='lxrInp' type='text'></td>";
                var newNameTD = newTR.insertCell(1);
                newNameTD.innerHTML = "<td><input name='phone' class='lxrInp' type='text'></td>";
                var newNameTD = newTR.insertCell(2);
                newNameTD.innerHTML = "<td><input name='email' class='lxrInp' type='text'></td>";
                var newNameTD = newTR.insertCell(3);
                newNameTD.innerHTML = "<td><input name='client' class='lxrInp' type='text'></td>";
                var newNameTD = newTR.insertCell(4);
                newNameTD.innerHTML ="<a align='center' href='javascript:;' onclick=\"deleteRow('tr" + xuhao + "')\">删除</a>";
         }
         function deleteRow(trid){
                var len =document.getElementById("lxrtbody").rows.length;
                if(len<=1){
                    layer.alert("请至少输入一个联系人");
                    return;
                }
                $("#"+trid).remove();
         }

  

    function checkUname(){
         var supLoginName = $("#uname").val();
        $.ajax({
               type:"post",
               url:"/LAA/app/index/checkUname?uname="+supLoginName,
               contentType:"application/json",
               dataType:"json",
               success:function(data){
                 if(!data.status){
                      $("#unamecheck").text("当前登录用户已注册");
                  }
         }});
    }

    function submitForm(){
        if(!checklxr()){
          return;
        }

        if($("#agree").prop("checked")==false){
            layer.msg("请接收用户协议");
            return;
        }
        var supLoginName = $("#uname").val();
        var supPwd = $("#pass").val();
        var supName = $("#companyname").val();
        var supAddress = $("#zhucedi").val();
        var supLegal = $("#legal").val();
        var supMoney = $("#fund").val();
      
        var status =$("#status").val();
        var supType= $("input[name='isagent']:checked").val();
        //var supType=$("#supType").val();
        
        var supLicense =$("#supLicense").val();
        var supClicense =$("#supClicense").val();
        var supConsign =$("#supConsign").val();
        var supConsigner =$("#supConsigner").val();
        var remark =$("#remark").val();
       
        var arr=new Array();
        var trs = $("#lxrtbody").find("tr");
        trs.each(function(){
            var tds=$(this).find("td");
            var obj={};
            tds.each(function(){             
                if($(this).find("input[name='person']").val()!=null){
                    obj.person =$(this).find("input[name='person']").val();          
                }
                if($(this).find("input[name='phone']").val()!=null){
                    obj.phone =$(this).find("input[name='phone']").val();          
                }
                if($(this).find("input[name='email']").val()!=null){
                    obj.email =$(this).find("input[name='email']").val();          
                }
                if($(this).find("input[name='client']").val()!=null){
                    obj.client =$(this).find("input[name='client']").val();          
                }                              
            })
            arr.push(obj);             
            
        })

        var sup={};
        sup.supLoginName=supLoginName;
        sup.supPwd=supPwd;
        sup.supName=supName;
        sup.supAddress=supAddress;
        sup.supLegal=supLegal;
        sup.supMoney=supMoney;
        sup.supType=supType;
        sup.status=status;
        sup.supLicense=supLicense;
        sup.supClicense=supClicense;
        sup.supConsign=supConsign;
        sup.supConsigner=supConsigner;
        sup.remark=remark;
        sup.contacts=arr;
       // document.getElementById("contacts").value =JSON.stringify(arr); 
        var data =JSON.stringify(sup);  
        layer.confirm('确定提交?',  function(index){
            var loadIndex =layer.load();
             $.ajax({
                           type:"post",
                           url:"/LAA/app/index/addSup/",
                           contentType:"application/json",
                           dataType:"json",
                           data:data,
                           success:function(data){
                               layer.close(loadIndex);
                             if(!data.status)
                                   layer.alert("提交失败！",{icon:2});
                             else{
                                   layer.alert("提交成功！", {
                                        closeBtn: 0
                                    }, function(){
                                      window.location.href="/LAA/app/index";
                                    });
                                   //location.reload();
                             }
                     }});
    
            layer.close(index);
        });

    }
    
     function getCompanyInfo(){
        var companyName =$("#companyname").val();
        $.ajax({
           url:"/LAA/app/index/queryCompanyInfo/",
           dataType: "json",
           type:"POST",
                data:{  
                    name: companyName
                },
           success:function(data){
               //alert(JSON.stringify(data));
                if(data.length>0){
                    $("#zhucedi").val(data[0].sup_address);
                    $("#legal").val(data[0].sup_legal);
                    $("#fund").val(data[0].sup_money);
                    $("#supLicense").val(data[0].sup_license);
                    $("#license1").val(data[0].sup_license);
                    $("#supClicense").val(data[0].sup_clicense);
                    $("#license2").val(data[0].sup_clicense);
                    $("#supConsign").val(data[0].sup_consign);
                    $("#license4").val(data[0].sup_consign);
                    $("#supConsigner").val(data[0].sup_consigner);
                    $("#license5").val(data[0].sup_consigner);
                    $("#remark").val(data[0].remark);
                    $("#license3").val(data[0].remark);
                    var type =data[0].sup_type;   
                    $("#supType").val(type);
                    var obj={};
                    obj.value=type;
                    selectagent(obj);
                   var objs =  $('input[name="isagent"]');
                   for(var i =0;i<objs.length;i++){                    
                       if(objs[i].value == type){
                           console.log(type);
                           console.log(i);
                           console.log(objs[i]);
                            $(objs[i]).prop("checked",true);                              
                       }
                   }
                }
        }});
    }
function openPic1(){
    var val=$("#supLicense").val();
    var src ="/upload/"+val;
     if(val=="")
        return;
    //alert(src);
    var url =encodeURI(src);   
    window.open(url);
}
function openPic2(){
    var val=$("#supClicense").val();
     if(val=="")
        return;
    var src ="/upload/"+val;
    var url =encodeURI(src);   
    window.open(url);
}
function openPic4(){
    var val=$("#supConsign").val();
     if(val=="")
        return;
    var src ="/upload/"+val;
    var url =encodeURI(src);   
    window.open(url);
}
function openPic5(){
    var val=$("#supConsigner").val();
    if(val=="")
        return;
    var src ="/upload/"+val;
    var url =encodeURI(src);   
    window.open(url);
}
function openPic3(){
    var val=$("#remark").val();
     if(val=="")
        return;
    var src ="/upload/"+val;
    var url =encodeURI(src);   
    window.open(url);
}


           
           
   