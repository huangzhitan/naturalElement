var userManager = {
    del: function (name, id) {
        layer.confirm("确认删除\"" + name + "\"吗？", {icon: 3, title: '提示'}, function (data) {
            if (data) {
                $.post(APP_PATH+"/delUser/" + id, {}, function (msg) {
                    if (msg.status) {
                        layer.msg("删除成功");
                        userManager.refresh();
                    } else {
                        layer.msg(msg.content);
                        userManager.refresh();
                    }
                })
            }
        });
    },
    //刷新用户
    refresh: function () {
        location.reload();
    },
    addUser: function () {
        layer.open({
            type: 2,
            title: "添加用户",
            shadeClose: true,
            area: ['600px', '475px'],
            content: APP_PATH+"/goAddUser",
            btn: ['确定', '取消'],
            yes: function (index) {
                var pass1 = layer.getChildFrame("#pass1").val();
                var pass2 = layer.getChildFrame("#pass2").val();
                var uname = layer.getChildFrame("#uname").val();
                 var phoneNo = layer.getChildFrame("#phoneNo").val();
                if (!uname) {
                    layer.msg("账号不能为空");
                    return;
                }
                if (!pass1) {
                    layer.msg("密码不能为空");
                    return;
                }
                if (!pass2) {
                    layer.msg("请再输入一次密码");
                    return;
                }
                if (pass1 != pass2) {
                    layer.msg("两次输入密码不一致");
                    return;
                }
                $.ajax({
                    url: APP_PATH+"/addUser/",
                    type: "POST",
                    data: {
                        'uName': uname,
                        'pass': pass2,
                        'sex': layer.getChildFrame("#sex").val(),
                        'name': layer.getChildFrame("#name").val(),
                        'email': layer.getChildFrame("#email").val(),
                        roleId: layer.getChildFrame("#roleId").val(),
                        phnoeNo:phoneNo
                    },
                    success: function (data) {
                        location.reload();
                    }
                });
            },
            cancel: function (index) {
                //默认关闭
            }
        });
    },
    editUser: function (uid) {
        layer.open({
            type: 2,
            title: "修改用户",
            shadeClose: true,
            area: ['600px', '375px'],
            content: APP_PATH+"/getSingleUser/" + uid,
            btn: ['确定', '取消'],
            yes: function (index) {
                var pass1 = layer.getChildFrame("#pass1").val();
                var pass2 = layer.getChildFrame("#pass2").val();
                if (pass1 != pass2) {
                    layer.msg("两次输入密码不一致");
                    return;
                }
                var loadIndex = layer.load();
                $.ajax({
                    url: APP_PATH+"/updateUser/",
                    type: "POST",
                    data: {
                        'uId': layer.getChildFrame("#uId").val(),
                        'sex': layer.getChildFrame("#sex").val(),
                        'name': layer.getChildFrame("#name").val(),
                        'email': layer.getChildFrame("#email").val(),
                        roleId: layer.getChildFrame("#roleId").val()
                    },
                    success: function (data) {
                        layer.close(loadIndex);
                        layer.msg("修改成功");
                        location.reload();
                    }
                });
            },
            cancel: function (index) {
                //默认关闭
            }
        });
    }

}