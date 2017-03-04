
function queryApplyItem() {
    $.ajax({
        type: "POST",
        url: "/LAA/app/audit/queryApplyList/",
        data: {
            page: 1,
            type: 0
        },
        success: function (data) {
            var count = data.count;
            var plan = data.pageList;
            var innerHtml1 = []
            if (count == null) {
                count = 0
            }
            if (plan != null && plan.length > 0) {
                for (var i = 0; i < plan.length; i++) {
                    innerHtml1.push("<tr>");
                    innerHtml1.push("<td>" + plan[i].sup + "</td>");
                    innerHtml1.push("<td>" + plan[i].supItem + "</td>");
                    innerHtml1.push("<td>" + plan[i].applyTime + "</td>");
                    var applytype = "整库申请";
                    if (plan[i].isunit == 1)
                        applytype = "子库单元申请";
                    if (plan[i].isunit == 2)
                        applytype = "学科单元申请";
                    if (plan[i].isunit == 3)
                        applytype = "类别单元申请";
                    innerHtml1.push("<td>" + applytype + "</td>");
                    innerHtml1.push("<td><a href='/LAA/app/applicationFormCtroller/showdetail?supApplyid=" + plan[i].applyId + "'>审核</a></td></tr>");

                }

            }
            $("#daishenheData").html(innerHtml1.join(""));
            $("#daishenheTitle").html("共有待审核申请" + "<span>" + count + "</span>" + "个");

        }

    })
}
function queryHexinUse() {
    $.ajax({
        type: "POST",
        url: "/LAA/app/report/queryHexin/",
        data: {
            timeType: 1
        },
        success: function (data) {
            var plan = data.use;
            var innerHtml1 = []

            if (plan != null && plan.length > 0) {
                for (var i = 0; i < plan.length; i++) {
                    innerHtml1.push("<tr>");
                    innerHtml1.push("<td>" + plan[i].itemname + "</td>");
                    innerHtml1.push("<td>" + plan[i].tjs + "</td>");
                    innerHtml1.push("<td>" + plan[i].tll + "</td>");
                    innerHtml1.push("<td>" + plan[i].txz + "</td>");
                    innerHtml1.push("<td>" + plan[i].thj + "</td></tr>");

                }

            }
            $("#hexinData").html(innerHtml1.join(""));

        }

    })
}

function queryGaiLanHomePage() {
    $.ajax({
        type: "POST",
        url: "/LAA/app/report/queryGaiLan/",
        data: {
            timeType: 1
        },
        success: function (data) {
            $("#xzzy").html(data.xzCount);
            $("#xzzj").html(data.xzMoney.toFixed(4));
            $("#xdzy").html(data.xdCount);
            $("#xdzj").html((data.xdMoney).toFixed(4));
            $("#zzy").html(data.all);
            $("#zzj").html((data.xzMoney + data.xdMoney).toFixed(4));
            var char1 = chartpie;
            char1.title = "资源数量分布";
            char1.series[0].data = [['新增资源数量', data.xzCount], ['续订资源数量', data.xdCount]];
            //                            chartpie.series[1].name="资源数量分布";
            //                            chartpie.series[1].data=[['新增资源资金',data.xzMoney],['续订资源资金',data.xdMoney]];
            $("#chartzy").highcharts(char1);
        }

    })
}

function queryJiJiangDaoQi() {
    $.ajax({
        type: "POST",
        url: "/LAA/app/report/queryJiJiangDaoQi/",
        data: {
        },
        success: function (data) {
            var count = 0;

            var innerHtml1 = []

            if (data != null && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    if (i >= 10)
                        break;
                    innerHtml1.push("<tr>");
                    innerHtml1.push("<td>" + data[i].itemname + "</td>");
                    innerHtml1.push("<td>" + data[i].stime + "</td>");
                    innerHtml1.push("<td>" + data[i].etime + "</td>");
                    innerHtml1.push("<td>" + data[i].emonth + "</td>");
                    innerHtml1.push("<td><a href='/LAA/app/myitem/showdetail/?itemId=" + data[i].myitemid + "&tab=3'>查看</a></td></tr>");

                }
                count = data.length;

            }
            $("#daoqiItemData").html(innerHtml1.join(""));
            $("#daoqiItemTitle").html("共有即将到期资源" + "<span>" + count + "</span>" + "个");

        }

    })
}
$(function () {
    queryApplyItem();
    queryHexinUse();
    queryGaiLanHomePage();
    queryJiJiangDaoQi();
})