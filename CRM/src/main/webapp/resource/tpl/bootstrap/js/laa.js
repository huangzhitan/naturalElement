
var LAA = {
    baseurl: "",
    init: function (url) {
        this.baseurl = url;
    },
    downloadUrl:"index.php?model=front&action=downloadfile",
    deletefileUrl:"index.php?model=front&action=delfile",
    chartswfpath :"template/jslib/fusionchart/swf/",
    showfjaction :"",
    dialogTemlate:'<div id="dialog%ID"  class="modal fade" style="left:40%;top:100px" ><div class="modal-header"><button type="button" class="close" onclick="LAA.hideDialog(\'%ID\')">x</button><h3>%TITLE</h3></div><div class="modal-body align-center"></div></div>',
    getUrlParamValue: function (p) { //获取参数值
        var result = {};
        var params = (window.location.search.split('?')[1] || '').split('&');
        for (var param in params) {
            if (params.hasOwnProperty(param)) {
                paramParts = params[param].split('=');
                result[paramParts[0]] = decodeURIComponent(paramParts[1] || "");
            }
        }
        if(typeof(result[p]) != "undefined"){
            return result[p];
        }
        return null;  
    },
    downloadFileUrl: "index.php?model=front&action=downloadfile",
    getMoreItemProperty: function (itemid, typeid, count) { //获取产品更多属性，分页获取
        var params = {itemid: itemid, typeid: typeid, count: count + 1};
        var url = this.baseurl + "&action=getitemmorepro";
        $.post(url, params, function (data) {
            if (data) {
                var moreElement = $("#moreinfo").clone();
                $("#moreinfo").remove();
                $(".CJ_pro_mdiv").append(data).append(moreElement);
            }
        });
    },
    showAjax: function (msg) {
        if (!msg) {
            msg = "正在执行";
        }
        $(".state_tip").html("<img src=\"template/admin/images/loading2.gif\" border=\"0\" align=\"absmiddle\"/>" + msg + "").fadeIn();
    },
    hideAjax: function (msg) {
        if (!msg) {
            msg = "操作完成";
        }
        $(".state_tip").html(msg).fadeOut('slow');
    },
    len: function (str) { //判断字符长度
        return str.replace(/[^\x00-\xff]/g, "**").length;
    },
    removeAt:function(array,val){
        var index = array.indexOf(val);
        if (index > -1) {
            array.splice(index, 1);
        }
    },
    checkIP : function(ip){
         var rule = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
        return rule.test(ip);
    },
    checkedval: function (ele) {
        var ret = [];
        ele.each(function () {
            ret.push(this.value);
        });
        return ret;
    },
    checksel: function (name) { //检查是否有选中的checkbox
        if (name == null) {
            name = "checkbox";
        }
        if ($("input[name='" + name + "']:checked").length == 0) {
            jAlert("请选择需要操作的项", "警告");
            return false;
        }
        return true;
    },
    checkInt : function(value){
        var rule =  /^\d+$/;
        return rule.test(value);
    },
    checkFloat : function(value){
      var rule = /^-?\d+\.?\d{0,1}$|^\d+$/;
      return rule.test(value);
    },
    selectall: function () { //全选
        $("#listtbody").find("input[name='checkbox']").prop("checked", $("#allselect").prop("checked"));
    },
    openfile: function (picid) {
        var dl = this.downloadFileUrl;
        window.open(dl + "&picid=" + picid);
    },
    addMyDigitByOne: function (id) { //添加我的资源
        var url = this.baseurl + "&action=openaddmyitem&itemid="+id;
        $.tbox.popup(url,"GET",{},"添加到我的资源");
    },
    removeCondition : function(catid,selected,action){ //移除中心库查询条件
        var newSelected = new Array();
        var arr = selected.split("-");
        for (var i = 0; i < arr.length; i++) {
            if(arr[i] != catid){
                newSelected.push(arr[i]);
            }
        }
        selected = newSelected.join("-");
        location.href = this.baseurl + "&action="+action+"&selected="+selected;
    },
    removeMyCondition : function(catid,selected,action,type){ //移除我的资源查询条件
        var newSelected = new Array();
        var oldSelected = selected;
        var arr = selected.split("-");
        for (var i = 0; i < arr.length; i++) {
            if(arr[i] != catid){
                newSelected.push(arr[i]);
            }
        }
        selected = newSelected.join("-");
        var url = location.href ;
        if(type==""){
             var select = this.getUrlParamValue("selected");
             url = url.replace("&selected="+select,"&selected="+selected);
             url = url.replace("&catid="+catid,"");
        }
        if(type == "itemtype"){
            var itemtype = this.getUrlParamValue("itemtype");
            if(itemtype){
                url = url.replace("&itemtype="+itemtype,"");
            }
        }
        if(type=="status"){
            var status = this.getUrlParamValue("status");
            if(status){
                url = url.replace("&status="+status,"");
            }
        }
        location.href = url;
    },
    delMyItem: function (id) {
        if (this.checksel()) {
            var url = this.baseurl + "&action=opermydigit";
            var ids = new Array();
            id && (ids.push(id)) || (ids = this.checkedval($("input[name='checkbox']:checked")));
            jConfirm("删除后你可以在回收站找到它", "确定要删除该资源吗", function (b) {
                if (b) {
                    $.post(url, {opertype: "remove", idstr: ids.join(",")}, function (data) {
                        if (data == "success") {
                            jAlert("删除成功", "提示", function () {
                                location.reload();
                            })
                        }
                    });
                }

            });
        }

    },
    opermyitem : function(field,value,myitemid){
        console.log('进入opermyitem');
        var action = "opermydigit";
        console.log('this.baseurl:'+this.baseurl);
        var url = this.baseurl + "&action="+action;
        console.log('url:'+url);
        $.post(url,{opertype:"editfield",field:field,value:value,id:myitemid},function(data){
            if(data == "success"){ //保存成功不做提示
                console.log("成功");
            }else if (data == "failure"){
                jAlert("操作失败", "提示");
            }else{
                console.log('data:'+data);
            }
        });
    },
    setQz: function (value, myitemid) { //设置权值
        this.opermyitem("order", value, myitemid);
    },
    setStatus: function (value, myitemid) {
        if (value == 5) { //如果是转入观察区
            var url = this.baseurl + "&action=opengcq";
            $.tbox.popup(url + "&myitemid=" + myitemid, '', '', '转入观察区');
        } else {
            this.opermyDigit("status", value, myitemid);
        }

    },
    opermyDigit: function (field, value, myitemid) {
        var action = "opermydigit";
        var url = this.baseurl + "&action=" + action;
        $.post(url, {opertype: "editfield", field: field, value: value, id: myitemid}, function (data) {
            if (data == "success") { //保存成功不做提示
            } else if (data == "failure") {
                jAlert("操作失败", "提示");
            } else {
                //
            }
        });
    },
    setImport: function (myitemid, obj) {
        var value = "0";
        if (obj.checked) {
            value = 1;
        }
        this.opermyDigit("import", value, myitemid);
    },
    //添加我的资源
    addMyRes: function () {
        var url = this.baseurl + "&action=addmyres";
        $.tbox.popup(url, "get");
    },
    impItemRes: function () { //打开我的资源导入页面
        var url = this.baseurl + "&action=itemimp";
        $.tbox.popup(url, "get");
    },
    //恢复资源
    recover: function (recycleid) {
        var url = this.baseurl + "&action=recover";
        $.post(url, {id: recycleid}, function (data) {
            if (data == "success") {
                jAlert("操作成功", "提示", function () {
                    location.reload();
                });
            } else {
                jAlert("操作失败", "提示");
            }
        });
    },
    //彻底删除资源
    thoroughDel: function (recycleid) {
        var url = this.baseurl + "&action=thoroughdel";
        $.post(url, {id: recycleid}, function (data) {
            if (data == "success") {
                jAlert("操作成功", "提示", function () {
                    location.reload();
                });
            } else {
                jAlert("操作失败", "提示");
            }
        });
    },
    showDialog: function(id,url,title,height,width,model){
        title = title || "";
        model = model || true;
        var diaTpl = this.dialogTemlate.replace("%ID",id);
            diaTpl = diaTpl.replace("%ID",id);
            diaTpl = diaTpl.replace("%TITLE",title);
        if($('#dialog' + id)[0]){
           $('#dialog' + id).remove();
        }
        $("body").append(diaTpl);
        $('#dialog' + id).modal({remote: url});
        $('#dialog' + id).on('shown', function () {
            if(!width){
              $("#dialog" +id).width($('#dialog' + id + " .modal-body").children().innerWidth());
            }else{
              $("#dialog" +id).width(width);
            }
            if(!height){
              var modalheight = $('#dialog' + id + " .modal-body").children().innerHeight();
              $("#dialog" +id+ " .modal-body").height(modalheight);
            }else{
              var iframe = $('#dialog' + id + " .modal-body").find("iframe");
              if(iframe[0]){
                    $(iframe).on("load", function () {
                       $('#dialog' + id + " .modal-body").css("height",$(this).height()+20);
                       $('#dialog' + id + " .modal-body").css("max-height",$(this).height()+20);             
                    });
              }else{
                $("#dialog" +id).height(height);
              }
            }
        });
    },
    hideDialog: function(id){
//        $('#dialog' + id).modal("hide");
        $("#dialog" + id).remove();
    },
    showitemfj: function (picid) {
        var url = this.downloadUrl + "&picid="+picid;
        window.open(url);
    },
    openPic: function (obj, evt) { //打开附件或图片
        var e = evt || window.event; //判断浏览器的类型
        if (e) {
            e.cancelBubble = true; //IE
        } else {
            e.stopPropagation();
        }
        this.showitemfj($(obj).attr("dd"));
    },
    delAttach:function(id,evt,picid,srcid){
        $("#"+id).remove();
        $("#"+srcid).val("");
        if(picid){//是否删除服务器上文件
           $.get(this.deletefileUrl,{picid:picid});
        }
        var e = evt || window.event; //判断浏览器的类型
        if (e) {
            e.cancelBubble = true; //IE
        } else {
            e.stopPropagation();
        }
    },  
     parseDate :function (strDateStart) {
         var strSeparator = "-"; 
         var oDate1 = strDateStart.split(strSeparator); 
         return  new Date(oDate1[0], oDate1[1]-1); 
    },
    monthDiff: function(startdate, stopdate) {
            startdate = new Date(startdate);
            stopdate = new Date(stopdate);
            if (typeof (startdate) != 'object' || typeof (stopdate) != 'object'){
                return -2;
            }            
            if (startdate > stopdate){
                return -1;
            }
            //自已判断输入值合法性
            var y = stopdate.getYear() - startdate.getYear();
            var m = stopdate.getMonth() - startdate.getMonth();
            if (y == 0 && m < 0){
                return -1;
            }
            if (m > 0) {
                return 12 * y + m;
            }
            if (m < 0) {
                return (12 + m) + (y - 1) * 12;
            }
            return 0;
        },
        renderChartByStat: function(elementId,chartType,itemids) {
           var url = location.href;
           url = url.replace("action=index","action=tochart");
           var field = $("#field").val();
           this.renderChart(elementId,"showchart",chartType,url,{itemids:itemids,field:field});
        },
        renderChart : function (eId,parentEid,chartType,url,params){
              $("#"+eId).append(' <div class="ajaxloading"><span>图表加载中...</span></div>');
              var width = $("#"+parentEid).innerWidth();
              var height = $("#"+parentEid).innerHeight();
              var chart = new FusionCharts(this.chartswfpath + chartType + ".swf", "chartId"+eId, width, height, "0", "1");
              chart.addParam("wmode","Opaque");
              if(!params){
                  params = {};
              }
              params.chartid = eId;
              $.ajax({url:url,data:params,async:true,type:'GET',success:function(data){
                 $("#"+eId).html("");
                 chart.setDataXML(data);
                 chart.render(eId);
            }});
        },
        crateChartExport:function(elementExportId){
            var myExportComponent = new FusionChartsExportObject("fcExporter"+elementExportId, this.chartswfpath + "FCExporter.swf");
            myExportComponent.componentAttributes.btnsavetitle = "另存为";
            myExportComponent.componentAttributes.btndisabledtitle = "右键另存为图片";
            myExportComponent.Render(elementExportId+"export");
            return myExportComponent;
        },
        renderAjaxPage : function(elementId,url,params,async){
            $("#"+elementId).html("");
            $("#"+elementId).append(' <div class="ajaxloading"><span>数据加载中...</span></div>');
            async = async || true;
            $.ajax({url:url,data:params,async:async,type:'post',success:function(data){
                $("#"+elementId).html("");   
                $("#"+elementId).append(data);
            }});
        },reportProgress: function (progress) {
            var url = this.baseurl + "&action=reportexport";
            $("#proBar").css("width", "100%");
            $("#proBar").html("100%");
            $.ajax({url:url,data:{ccount:progress},dataType:"json",async:false,type:'post',success:function(data){
                    if(data.status == "success"){
                        $("#myModalProgress").modal("toggle");
                        $("#dliframe").attr("src",encodeURI("index.php?model=report&action=reportdownload&url="+data.url));
                    }
            }}); 
        },openChartParams:function(itemids,type,stattype){
            var field = $("#field").val();
            var url = this.baseurl + "&action=openchartparams&itemids="+itemids+"&field="+field+"&type="+type+"&stattype="+stattype;
            this.showDialog("chartParams",url,"设置显示参数",380,550);
            
        }
    
};

function setCookie(name, value, days) {
    var options = {expires: days, path: "/"};
    $.cookie(name, $.base64.encode(value), options);
}
//获取cookie值
function getCookieValue(name) {
    var value = $.cookie(name);
    if (value != null) {
        value = $.base64.decode(value);
    }
    return value;
}
//删除cookie
function deleteCookie(name, path) {
    var name = escape(name);
    var expires = new Date(0);
    path = path == "" ? "" : ";path=" + path;
    document.cookie = name + "=" + ";expires=" + expires.toUTCString() + path;
}

function sleep(numberMillis) {
    var now = new Date();
    var exitTime = now.getTime() + numberMillis;
    while (true) {
        now = new Date();
        if (now.getTime() > exitTime)
            return;
    }
}

// 针对IE8，9不支持indexof de 解决方案
if (!Array.prototype.indexOf)
{
  Array.prototype.indexOf = function(elt /*, from*/)
  {
    var len = this.length >>> 0;

    var from = Number(arguments[1]) || 0;
    from = (from < 0)
         ? Math.ceil(from)
         : Math.floor(from);
    if (from < 0)
      from += len;

    for (; from < len; from++)
    {
      if (from in this &&
          this[from] === elt)
        return from;
    }
    return -1;
  };
}
