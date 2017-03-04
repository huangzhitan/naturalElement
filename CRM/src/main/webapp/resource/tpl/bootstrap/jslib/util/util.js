
//var LAA = {
//    baseurl: "",
//    init: function(url) {
//        this.baseurl = url;
//    },
//    downloadFileUrl :"index.php?model=front&action=downloadfile",
//    getMoreItemProperty: function(itemid, typeid, count) { //获取产品更多属性，分页获取
//        var params = {itemid: itemid, typeid: typeid, count: count + 1};
//        var url = this.baseurl + "&action=getitemmorepro";
//        $.post(url, params, function(data) {
//            if (data) {
//                var moreElement = $("#moreinfo").clone();
//                $("#moreinfo").remove();
//                $(".CJ_pro_mdiv").append(data).append(moreElement);
//            }
//        });
//    },
//    showAjax: function(msg) {
//        if(!msg){
//            msg = "正在执行";
//        }
//        $(".state_tip").html("<img src=\"template/admin/images/loading2.gif\" border=\"0\" align=\"absmiddle\"/>"+msg+"").fadeIn();
//    },
//    hideAjax: function(msg) {
//        if(!msg){
//            msg = "操作完成";
//        }
//        $(".state_tip").html(msg).fadeOut('slow');
//    },
//    len: function(str) { //判断字符长度
//        return str.replace(/[^\x00-\xff]/g, "**").length;
//    },
//    checkedval: function(ele) {
//        var ret = [];
//        ele.each(function() {
//            ret.push(this.value);
//        });
//        return ret;
//    },
//    checksel: function() { //检查是否有选中的checkbox
//        if ($("#listtbody").find("input:checked").length == 0) {
//      jAlert("请选择需要操作的项", "警告");
//            return false;
//        }
//        return true;
//    },
//    selectall: function() { //全选
//        $("#listtbody").find("input[type='checkbox']").prop("checked", $("#allselect").prop("checked"));
//    },
//    openfile : function(picid){
//        var dl = this.downloadFileUrl;
//        window.open(dl + "&picid="+picid);
//    },
//    addMyItemByOne: function(id) { //添加我的资源
//      var url = this.baseurl + "&action=addmyitem";
//      $.post(url,{itemid:id},function(data){
//            if(data.status=="1"){
//        jAlert("添加成功", "提示");
//            }else{
//        jAlert(data.msg, "警告");
//            }
//      },"json");
//    },
//    delMyItem: function(id) {
//        if (this.checksel()) {
//                    var url = this.baseurl + "&action=myitem";
//                    var ids = new Array();
//                    id && (ids.push(id)) || (ids = this.checkedval($("#listtbody").find("input:checked")));
//      systemprompt("删除后你可以在回收站找到它", "确定要删除该资源吗", function(b) {
//                    if (b) {
//                        $.post(url, {opertype: "remove", idstr: ids.join(",")}, function(data) {
//                        if (data == "success") {
//              jAlert("删除成功", "提示", function() {
//                                location.reload();
//                            })
//                        }
//                    });
//                }
//
//            })
//        }
//
//    },
//    setQz : function(value,myitemid){ //设置权值
//        this.opermyitem("order",value,myitemid);
//    },
//    setStatus : function(value,myitemid){
//        if(value == 5){ //如果是转入观察区
//           var url = this.baseurl + "&action=opengcq";
//           $.tbox.popup(url,"get",{myitemid:myitemid});
//        }else{
//            this.opermyitem("status",value,myitemid);
//        }
//
//    },
//    opermyitem : function(field,value,myitemid){
//        var action = "myitem";
//        var url = this.baseurl + "&action="+action;
//        $.post(url,{opertype:"editfield",field:field,value:value,id:myitemid},function(data){
//                if(data == "success"){ //保存成功不做提示
//                }else if (data == "failure"){
//        jAlert("操作失败", "提示");
//                }else{
//                    //
//                }
//        });
//    },
//    setImport : function(myitemid,obj){
//      var value = "0";
//      if(obj.checked){
//          value = 1;
//      }
//      this.opermyitem("import",value,myitemid);
//    },
//    //添加我的资源
//    addMyRes : function(){
//        var url = this.baseurl + "&action=addmyres";
//        $.tbox.popup(url,"get");
//    },
//    impItemRes : function(){ //打开我的资源导入页面
//       var url = this.baseurl + "&action=itemimp";
//       $.tbox.popup(url,"get");
//    },
//    //恢复资源
//    recover : function (recycleid){
//        var url = this.baseurl + "&action=recover";
//        $.post(url,{id:recycleid},function(data){
//                if(data == "success"){
//        jAlert("操作成功", "提示", function() {
//                        location.reload();
//                    });
//                }else{
//        jAlert("操作失败", "提示");
//                }
//        });
//    },
//
//    //彻底删除资源
//    thoroughDel : function (recycleid){
//         var url = this.baseurl + "&action=thoroughdel";
//        $.post(url,{id:recycleid},function(data){
//                if(data == "success"){
//        jAlert("操作成功", "提示", function() {
//                        location.reload();
//                    });
//                }else{
//        jAlert("操作失败", "提示");
//                }
//        });
//    }
//}
function setCookie(name,value,days){
    var options = {expires:days,path:"/"};
    $.cookie(name, $.base64.encode(value),options);
}
//获取cookie值
function getCookieValue(name){
    var value = $.cookie(name);
    if(value != null){
        value = $.base64.decode(value);
    }
    return value;
}
//删除cookie
function deleteCookie(name,path){
    var name = escape(name);
    var expires = new Date(0);
    path = path == "" ? "" : ";path=" + path;
    document.cookie = name + "="+ ";expires=" + expires.toUTCString() + path;
}

function pageding(pagearr,fun_name,data){
                        var html1=[];
                        var totalPage =1;
                        if(data.totaPage > 1){
                            totalPage = data.totaPage;
                        }
                        html1.push("<div>");
                        html1.push("<span>共有<b>"+totalPage+"</b>页</span> ");
                        html1.push("<span>当前"+data.currentPage+"/"+totalPage+"</span>");
                        if(data.currentPage>1){
                            var shang = data.currentPage-1;
                            html1.push("<a href='javascript:void(0)' onclick='"+fun_name+"(1)'>首页</a>");
                            html1.push("<a href='javascript:void(0)' onclick='"+fun_name+"("+shang+")'>上一页</a>");
                        }
                        if(data.currentPage<data.totaPage){
                            var xia = data.currentPage+1;
                            var mo =data.totaPage;
                            html1.push("<a href='javascript:void(0)' onclick='"+fun_name+"("+xia+")'>下一页</a>");
                            html1.push("<a href='javascript:void(0)' onclick='"+fun_name+"("+mo+")'>末页</a>"); 
                        }
                        var names=fun_name;
                        var namwes=names.split("\.");
                        if(namwes.length>0){
                            names=namwes[1];
                        }
                        html1.push("<input type=text id='tiaoz"+names+"' style='width:40px!important;' value='"+data.currentPage+"' />");
                        html1.push("<a href='javascript:void(0)' onclick=beforeJumppPage("+fun_name+","+totalPage+",'tiaoz"+names+"')>跳转</a>");
                        html1.push("</div>");
                        pagearr.html("");
                        pagearr.append(html1.join(""));
        }   
 function beforeJumppPage(fun_name,total,namest){
    var page = $("#"+namest).val();
    page =page.replace(/(\s*$)/g,"");
    if(page==null||page=="")
        page=1;
    if(!LAA.checkInt(page)||page<=0){
        layer.msg("输入格式不对");
        return;
    }
    if(page>total)
        page=total;
    fun_name(page);
}
function autocompleteArray(idName,arr){
      function split(val) {
                return val.split(/,\s*/);
            }
            function extractLast(term) {
                return split(term).pop();
             } 
            $("#"+idName) .bind("keydown", function (event) {

                 if (event.keyCode === $.ui.keyCode.TAB &&

                      $(this).data("ui-autocomplete").menu.active) {

                      event.preventDefault();

                  }

              }).autocomplete({

                 minLength: 0,

                  source: function (request, response) {
                      response($.ui.autocomplete.filter(
                       arr, extractLast(request.term)));

                  },

                  focus: function () {
                      // prevent value inserted on focus

                     return false;

                  },

                  select: function (event, ui) {

                       var terms = split(this.value);

                      // remove the current input

                      terms.pop();

                     // add the selected item

                      terms.push(ui.item.value);

                      // add placeholder to get the

                      //comma-and-space at the end

                     terms.push("");

                      this.value = terms.join(", ");

                       return false;

                   }

             });
}  
function ReplaceDot(obj)
{
var oldValue=obj.value;
while(oldValue.indexOf("，")!=-1)//寻找每一个中文逗号，并替换
{
obj.value=oldValue.replace("，",",");
oldValue=obj.value;
}
obj.value = oldValue;
}
function Trim(str,is_global)

        {

            var result;

            result = str.replace(/(^\s+)|(\s+$)/g,"");

            if(is_global.toLowerCase()=="g")

            {

                result = result.replace(/\s/g,"");

             }

            return result;

}
function ltrim(str){ //删除左边的空格
return str.replace(/(^\s*)/g,"");
}
//多选下拉框
function test(){
      var chosed=$(".bootstrap-select").attr("class");
        if(chosed.indexOf("open")>-1){
            var all=$(".filter-option").text();
            var start=all.indexOf(",",1);
           var real=all.substring(start+1,all.length);
           if(real=="请选择子库"||real=="没有子库"){
               real="";
           }
           $("#chosedzikupopsy").val(real);
        } 
    }
 
function createCen(th){
    var tag=$(th)[0].tagName;
   if(tag=="TBODY"){
     $(th).parent("table").after("<div class='in-zhezhao' ><img src='/upload/loading.gif'></img></div>");
    }
    if(tag=="DIV"){
        $(th).append("<div class='in-zhezhao' ><img src='/upload/loading.gif'></img></div>");
    }
}
function closeCen(th){
    var tag=$(th)[0].tagName;
    if(tag=="DIV"){
        $(th).empty();
    }
     if(tag=="TBODY"){
    $(th).parent("table").next().remove();
    }
    
}
function getArray(a) {
 var hash = {},
     len = a.length,
     result = [];

 for (var i = 0; i < len; i++){
     if (!hash[a[i]]){
         hash[a[i]] = true;
         result.push(a[i]);
     } 
 }
 return result;
}