/**
 * author:huang@leosys.com.cn
 * date:2015.04.15
 * note:login page's js doc
 */
$(function () {
    var username = getCookieValue("uname");
    var password = getCookieValue("pass");
    if (username && password ) {
        $("#uname").val(username);
        $("#pass").val(password);
    }else{
        $('#uname,#pass,#seccode').placeholder();
    }
    $("#seccode").focus(function () {
        $(this).next().show();
    }).keyup(function () {
        var $this = $(this);
        $this.val($this.val().toUpperCase());
    }).keydown(function (event) {
        if (event.keyCode == 13) {
            dologin();
        }
    });

    function checkform() {
        if ($.trim($("#uname").val()) == "" || $.trim($("#pass").val()) == "登录账号") {
            alertTip("登录账号不能为空");
            return false;
        }
        if ($.trim($("#pass").val()) == "" || $.trim($("#pass").val()) == "***") {
            alertTip("登录密码不能为空");
            return false;
        }
        var $seccode = $("#seccode");
        if ($seccode.val() === "") {
            alertTip("验证码不能为空");
            $seccode.focus();
            return false;
        }
        return true;
    }

    function chgimg() {
        document.getElementById("seccodeimg").src = '/CRM/app/getCheckCode?'+new Date().getTime();
        return false;
    }

    function dologin() {
        $("#dologin").text("验证中...");
        if (checkform() == false) {
            $("#dologin").text("登录");
            return false;
        }
        setCookie("uname", $("#uname").val(), 3);
        setCookie("pass", $("#pass").val(), 3);
        $.post("/CRM/app/laaUser/login", $("#loginfrm").serialize(), function (data) {
            if (data.status) {
                //根据用户不同的角色跳转不同页面
               var loginName = data.params.laaUserName;
               var userId = data.params.laaUserUid;
               var rolelevel = data.params.rolelevel;
//               if(rolelevel==1){
//                   location.href = '/LAA/app/laaSup/toHomePage/?loginName='+loginName;
//               }else{
                location.href = '/CRM/app/laaUser/getAll/1';
//            }
            } else {
                $("#dologin").html("登录");
                alertTip(data.content);
                chgimg();
                $("#seccode").val("");
            }
        }, "json");
    }
    function alertTip(text){
        $("#validateText").text(text);
    }
    $("#seccodeimg").click(function(){
        //点击切换验证码图片
        chgimg();
    });
    $("#dologin").click(function(){
        dologin();
    });

});

