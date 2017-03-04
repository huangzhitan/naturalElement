
$(document).ready(function () {
    searchSupplier(1,0);
});

function searchSupplier(page,status) {
    var tbody = $("#tbody");
    var pageindex = $("#pageindex");
    $("#regStatus_p").val(status);
    //var status = $("input[name='regStatus']:checked").val();
    $.ajax({
        type: "post",
        url: "/LAA/app/laaUser/queryRegisterList/",
        data: {page: page, status: status},
        success: function (data) {
            var htmldata = [];
            if (data.pageList.length > 0) {
                for (var i = 0; i < data.pageList.length; i++) {
                    var temp = data.pageList[i];
                    htmldata.push("<tr>");
                    htmldata.push("<td>" + temp.supLoginName + "</td>");
                    htmldata.push("<td>" + temp.supName + "</td>");
                    htmldata.push("<td>" + temp.supAddress + "</td>");
                    htmldata.push("<td>" + temp.supLegal + "</td>");
                    htmldata.push("<td>" + temp.supMoney + "</td>");
                   // var checkvars = $('input:radio[name="regStatus"]:checked').val();
                    var checkvars=$("#regStatus_p").val();
                    if (checkvars === '0') {
                        htmldata.push('<td style="text-align: center"><a href="javascript:" onclick="editRegister(' + temp.supId + ')">审核 </a><a href="javascript:" onclick="delRegister(' + temp.supId + ')"> 删除</a></td>');
                    } else {
                        htmldata.push('<td style="text-align: center"><a href="javascript:" onclick="editRegister(' + temp.supId + ')">查看 </a><a href="javascript:" onclick="delRegister(' + temp.supId + ')"> 删除</a></td>');
                    }

                    htmldata.push("</tr>");
                }
            }
            tbody.html("");
            tbody.html(htmldata.join(""));
            pageding(pageindex, "searchSupplier", data);
        }

    });
}
function selecttype() {
    var val = $("input[name='searchtype']:checked").val();
    // alert(val);
    //ajax后台查询
}
function editRegister(supId) {
    var id = supId;
    var checkvars = $("#regStatus_p").val();
    var entity = {
        type: 2,
        title: '审核供应商',
        closeBtn: 1,
        shade: 0.3,
        skin: 'layer-seat',
        area: ['550px', '650px'],
        content: "/LAA/app/laaUser/auditRegister?supId=" + id,
        btn: ['通过', '取消','驳回'],
        yes: function (index) {
            layer.confirm("确定通过？", function (index) {
                var loadIndex = layer.load();
                var supId = layer.getChildFrame("#supId").val();
                var content="";
                $.ajax({
                    url: "/LAA/app/laaUser/auditPass",
                    type: "POST",
                    dataType: "json",
                    data: {
                        supId: supId,
                        result: 1,
                        content: content
                    },
                    success: function (data) {
                        layer.close(loadIndex);
                        location.reload();
                        if (data.status) {
                            layer.msg("操作成功");
                        } else {
                            layer.msg("操作失败");
                        }
                    }
                });
                layer.close(index);

            });
        },
        cancel: function (index) {
            
        },btn3:function(){         
            layer.prompt({
                formType: 2,
                value: "不合格",
                title: '反馈意见'
            }, function (value, index, elem) {
                var supId = layer.getChildFrame("#supId").val();
                var loadIndex = layer.load();
                $.ajax({
                    url: "/LAA/app/laaUser/auditPass",
                    type: "POST",
                    dataType: "json",
                    data: {
                        supId: supId,
                        result: 2,
                        content: value
                    },
                    success: function (data) {
                        layer.close(loadIndex);
                        if (data.status) {
                            layer.msg("操作成功");
                            location.reload();
                        } else {
                            layer.msg("操作失败");
                        }
                    }
                });
            });
 
        }
    };
    if (checkvars === '0') {
        entity.title = "审核";
    } else {
        entity.title = "详情";
        entity.btn = [];
    }
    layer.open(entity);
}
function delRegister(supId) {
    var id = supId;
    layer.confirm("确定删除吗？",{icon:3,title:"提示"}, function (index) {
        var loadIndex = layer.load();
        $.ajax({
            url: "/LAA/app/laaUser/deleteRegister?supId=" + supId,
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                layer.close(loadIndex);
                if (data.status) {
                    layer.msg("删除成功");
                    refresh();
                } else {
                    layer.msg("删除失败");
                }
            }
        });
    });

}
function selectAll() {
    $("#tbody").find("input[name='checkbox']").prop("checked", $("#selectContro").prop("checked"));
}
//删除
function del(name, id) {
    jConfirm("确认删除" + name + "的申请吗？", "确认", function (data) {
        if (data) {
            $.post("/LAA/app/laaUser/delUser/" + id, {}, function (msg) {
                if (msg.status) {
                    jAlert("删除成功");
                    refresh();
                } else {
                    jAlert(msg.content);
                    refresh();
                }
            })
        }
    });
}
//批量删除
function delBetch() {
    if ($("#tbody").find("input[name='checkbox']:checked").length == 0) {
        jAlert("请选择需要操作的项", "警告");
    } else {
        jConfirm("确认删除" + $("input[name='checkbox']:checked").length + "个用户申请吗？", "警告", function (data) {
            if (data) {
                var result = true;
                $("#tbody").find("input[name='checkbox']").each(function () {
                    if (this.checked) {
                        $.post("/LAA/app/laaUser/delUser/" + this.value, {}, function (msg) {
                            if (msg.status) {
                                //
                            } else {
                                result = false;
                            }
                        })
                    }
                });
                if (result) {
                    jAlert("删除成功");
                } else {
                    jAlert("删除失败，请联系管理员！");
                }
                refresh();
            }
        });
    }
}
//刷新用户
function refresh() {
    location.reload();
}