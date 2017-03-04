/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
(function($) {
    $.stat = {
        chartunitheight: 34, //对应图表基本单元高度
        chartunitwidth: 28,
        chartminHeight: 480, //图表最小高度
        chartminwidth: 500, //图表最小宽度
        url: "",
        swfpath: "template/front/default/swf/",
        chartType: "",
        init: function(url, charttype) { //初始化
            if (url) {
                this.url = url;
            } else {
                this.url = window.location.href + "&reqtype=1";
            }
            this.chartType = charttype;
            return this;
        },
        load: function() {
            if (this.url) {
                location.href = this.url;
            }
        },
        reqParams: function(params, url) {
            var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
            var paraObj = {}
            var j = 0;
            for (var i = 0; j = paraString[i]; i++) {
                paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
            }
            var returnValue = paraObj[params.toLowerCase()];
            if (typeof (returnValue) == "undefined") {
                return "";
            } else {
                return returnValue;
            }
        },
        renderChart: function(elementId, totalrow) {
            if (totalrow > 21)
                totalrow = 21;//不能超过21种统计项
            var height = parseInt(this.chartunitheight * totalrow);
            var width = parseInt(this.chartunitwidth * totalrow);
            if (height < this.chartminHeight) {
                height = this.chartminHeight;
            }
            if (width < this.chartminwidth) {
                width = this.chartminwidth;
            }
            var chart = new FusionCharts(this.swfpath + this.chartType + ".swf", "chartId", width, height, "0", "1");
            var stime = $("#stime").val();
            var etime = $("#etime").val();
            var queryType = $("#querytype").val();
            var params = null;
            if (queryType == "diy") {
                params = {stime: stime, etime: etime, querytype: queryType,reqtype:1,charttype:this.chartType};
            }else{
               params ={reqtype:1,charttype:this.chartType} 
            }
            $.get(this.url, params, function(data) {
                chart.setDataXML(data);
                chart.render(elementId);
            });
            var myExportComponent = new FusionChartsExportObject("fcExporter1", this.swfpath + "FCExporter.swf");
            myExportComponent.componentAttributes.btnsavetitle = "另存为";
            myExportComponent.componentAttributes.btndisabledtitle = "右键另存为图片";
            myExportComponent.Render("fcexpDiv");
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
        diyQuery: function(url,stattype, item) {
            var stime = $("#stime").val();
            var etime = $("#etime").val();
            var stimes = $("#stimes").val();
            var etimes = $("#etimes").val();
            var dateType = $("#dateType").val();
            if (dateType == "year") {
                if (!stime) {
                    jAlert("开始时间不能为空", "警告");
                    return;
                }
                if (!etime) {
                    jAlert("结束时间不能为空", "警告");
                    return;
                }
                if (stime > etime) {
                    jAlert("开始时间不能大于结束时间", "警告");
                    return;
                }
            }
            if(dateType == "month"){
                if (!stimes) {
                    jAlert("开始时间不能为空", "警告");
                    return;
                }
                if (!etimes) {
                    jAlert("结束时间不能为空", "警告");
                    return;
                }
                var ii = this.monthDiff(stimes,etimes);
                if (ii == -1) {
                    jAlert("开始时间必须小于结束时间", "警告");
                    return;
                }else if(ii == -2){
                    jAlert("输入值非法", "警告");
                    return ;
                }else if(ii>6){
                    jAlert("查询时间跨度不能超过6个月", "警告");
                    return ; 
                }
                stime = stimes;
                etime = etimes;
            }
            var _url = url + "&time=diy&stime=" + stime + "&etime=" + etime + "&stattype="+stattype+"&item="+item+"&datetype="+dateType;
            window.location.href = _url;
        },
        bindUrl: function(url, menu, item) {
            var _url = url + "&menu=" + menu + "&item=" + item;
            $("#ControlBar2 a").click(function(e) {
                var memo = $(this).attr("memo");
                var qyear = $("#DateSelectBar .cur").attr("memo");
                var queryType = $("#querytype").val();
                if (queryType == "diy") {
                    var stime = $("#stime").val();
                    var etime = $("#etime").val();
                    $(this).attr("href", _url + "&charttype=" + memo + "&qyear=diy&stime=" + stime + "&etime=" + etime + "&querytype=diy");
                } else {
                    $(this).attr("href", _url + "&charttype=" + memo + "&qyear=" + qyear);
                }
            });

            var _url = url + "&menu=" + menu + "&item=" + item;
            $("#DateSelectBar a").click(function(e) {
                var memo = $(this).attr("memo");
                var charttype = $("#ControlBar2 .cur").attr("memo");
                $(this).attr("href", _url + "&charttype=" + charttype + "&qyear=" + memo);
                
            });

        },
        nextPage: function(page, url) {
            window.open(url + "&page=" + page, "_self");
        },
        search: function(url) {
            var txt = $("#qname").val();
            if (!txt) {
                jAlert("请输入关键字", "警告");
                return;
            }
            window.open(url + "&qname=" + txt, "_self");
        },
        ryChanage: function(obj, url) {
            var txt = obj.value;
            window.open(encodeURI(url + "&ry=" + txt), "_self");
        },
        statDelRow: function(id) { //删除行
            if (this.checksel()) {
                jConfirm("确认删除吗？", "确认信息", function(confirm) {
                    if (confirm) {
                        // if ($.stat.checksel()) {
                        var ids = new Array();
                        id && (ids.push(id)) || (ids = $.stat.checkedval($("#listtbody").find("input:checked")));
                        $.stat.show();
                        $.post($.stat.url, {idstr: ids.join(",")}, function(data) {
                            $.stat.hide();
                            if (data == "success") {
                                jAlert("删除成功", "信息");
                                location.reload();
                            } else {
                                jAlert("删除失败", "警告");
                            }

                        });
                    }
                });
            }
        },
        statDetail: function(item, sids, value, field) {
            var url = "index.php?model=stat&action=openstatdetail";
            var winTitle = "";
            if(item=="js"){
                winTitle = "检索明细-Top50";
            }else if(item=="ll"){
                winTitle = "浏览明细-Top50";
            }else if(item == "xz"){
                winTitle = "下载明细-Top50";
            }else if(item == "hj"){
                winTitle = "合计明细-Top50";
            }else if(item == "xzcb"){
                winTitle = "下载成本";
            }else if(item == "jscb"){
                winTitle = "检索成本";
            }else if(item == "llcb"){
                winTitle = "浏览成本";
            }else if(item == "sycb"){
                winTitle = "使用成本";
            }else if(item == "sdid"){
                winTitle = "资源分布";
            }
            $.tbox.popup(url + "&item=" + item + "&sids=" + sids + "&value=" + value + "&field=" + field,'','',winTitle);
            return false;
        },
        subDbDetail: function(item, sids, value, field,menu) {
            var url = "index.php?model=stat&action=opensubdbdetail";
            var winTitle = "子库资源详细信息";
            $.tbox.popup(url + "&item=" + item + "&sids=" + sids + "&value=" + value + "&field=" + field+"&menu="+menu+"",'','',winTitle);
            return false;
        },
        statAddrow: function(url) {
            $.get(url, function(data) {
                if (data == "success") {
                    location.reload();
                } else {
                    jAlert("删除失败", "警告");
                }
            });
        },
        statSaveData: function() {

        },
        selectall: function() { //全选
            $("#listtbody").find("input[type='checkbox']").prop("checked", $("#allselect").prop("checked"));
        },
        checksel: function() { //检查是否有选中的checkbox
            if ($("#listtbody").find("input:checked").length == 0) {
                jAlert("请选择需要操作的项", "警告");
                return false;
            }
            return true;
        },
        checkedval: function(ele) {
            var ret = [];
            ele.each(function() {
                ret.push(this.value);
            });
            return ret;
        },
        download: function(url) {
            var qtyear = this.reqParams("qyear", url);
            var qname = this.reqParams("qname", location.href);
            if (qtyear == "diy") { //自定义查询下载
                var stime = $("#stime").val();
                var etime = $("#etime").val();
                if (!stime) {
                    jAlert("开始时间不能为空", "警告");
                    return;
                }
                if (!etime) {
                    jAlert("结束时间不能为空", "警告");
                    return;
                }
                if (stime > etime) {
                    jAlert("开始时间不能大于结束时间", "警告");
                    return;
                }
                url += "&stime=" + stime + "&etime=" + etime;
            }
            if (qname) {
                url += "&qname=" + qname;
            }
            url += "&reqtype=2";

            window.open(encodeURI(url), "_self");
        },
        show: function() {
            $(".state_tip").html("<img src=\"template/admin/images/loading2.gif\" border=\"0\" align=\"absmiddle\"/>正在执行").fadeIn();
        },
        hide: function() {
            $(".state_tip").html("操作完成").fadeOut('slow');
        }

    };
})(jQuery);
