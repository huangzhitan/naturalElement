/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function getMeun(){
    $.ajax({
        url: "/CRM/app/laaNavi/getLeftMenus/",
        type: "GET",
        data: {},
        success: function (data) {
            var parentNavis = data.params.parentNavis;
            var childNavis = data.params.childNavis;
            $.each(parentNavis, function (index) {
                $("#rootul").append("<li class='submenu ' id = 'p"+parentNavis[index].naviId+"'>" +
                        "<a href='" + parentNavis[index].naviUrl + "' >" +
                        "<i class='icon "+parentNavis[index].naviIcon+"'></i> " +
                        "<span>" + parentNavis[index].naviName + "</span>" +
                        "<span class='label'></span>" +
                        "</a>" +
                        "<ul id='pul" + parentNavis[index].naviId + "'></ul>" +
                        "</li>");
                $.each(childNavis, function (i) {
                    if (parentNavis[index].naviId === childNavis[i].parentNaviId) {
                        if((childNavis[i].naviUrl).length > 1){
                            $("#pul" + parentNavis[index].naviId).append("<li class='' id = 'c"+childNavis[i].naviId+"'><a href='" + childNavis[i].naviUrl + "'>" + childNavis[i].naviName + "</a></li>");
                        }else{
                            $("#pul" + parentNavis[index].naviId).append("<li class='' id = 'c"+childNavis[i].naviId+"'><a class='noPermission'>" + childNavis[i].naviName + "</a></li>");
                        }
                    }
                });

            });
            //展开菜单
            var isParent = true;
            //当访问的是子菜单：
            $.each(childNavis, function (i) {
                if(childNavis[i].naviUrl.indexOf(urls) >= 0) {
                    isParent = false;
                    $("#p" + childNavis[i].parentNaviId).addClass('open');
                    //当前的父菜单
                    var pmenu = $("#p" + childNavis[i].parentNaviId);
                    pmenu.addClass('active');
                    //当前的子菜单
                    var cmenu = $("#c" + childNavis[i].naviId);
                    cmenu.addClass('active');
                }else{
//                    if((URL.indexOf("LAA/app/myitem")>=0)||(URL.indexOf("LAA/app/laaSup")>=0)){
//                      if((childNavis[i].naviUrl.indexOf("/LAA/app/laaSup/toHomePage/")>=0)){
                          isParent = false;
                    if(urls.indexOf("LAA/app/myitem/"))    
                    $("#p" + childNavis[i].parentNaviId).addClass('open');
                    //当前的父菜单
                    var pmenu = $("#p" + childNavis[i].parentNaviId);
                    pmenu.addClass('active');
                    //当前的子菜单
                    var cmenu = $("#c" + childNavis[i].naviId);
                    cmenu.addClass('active');
                     
                    
                    
                }
            });
            //当访问的是父菜单：
            if(isParent){
                $.each(parentNavis, function (index) {
                    if(parentNavis[index].naviUrl.indexOf(urls) >= 0) {
                        //当前的父菜单
                        var pmenu = $("#p" + parentNavis[index].naviId);
                        pmenu.addClass('active');
                    }
                });
            };
            if(!isParent){
              var url=urls;
           
            }
            $(".submenu>a").on("click", function (e) {
                var url = $(this).attr('href');
                if (url != '#' && url != '') {
                    location.href = url;
                } else {
                    e.preventDefault();
                    var submenu = $(this).siblings('ul');
                    var li = $(this).parents('li');
                    var submenus = $('#sidebar li.submenu ul');
                    var submenus_parents = $('#sidebar li.submenu');
                    if (li.hasClass('open'))
                    {
                        if (($(window).width() > 768) || ($(window).width() < 479)) {
                            submenu.slideUp();
                        } else {
                            submenu.fadeOut(250);
                        }
                        li.removeClass('open');
                    } else
                    {
                        if (($(window).width() > 768) || ($(window).width() < 479)) {
                            submenus.slideUp();
                            submenu.slideDown();
                        } else {
                            submenus.fadeOut(250);
                            submenu.fadeIn(250);
                        }
                        submenus_parents.removeClass('open');
                        li.addClass('open');
                    }
                }
            });
            
//            $(".noPermission").on("click", function (e) {
//                layer.alert("您尚未购买该功能");
//            });
        }
    });}
getMeun()
// 
//   queryData();
//   function queryData(){
//       $("#applogo").css("width","0px").css("height","0px").css("margin","0px");
//        $.ajax({
//           type:"post",
//           url:"/LAA/app/index/querycompany",
//           success:function(data){
//            var companys=data.params.companys;
//            if(companys==null){
//                $("#imgs").attr("src","/LAA/resource/tpl/bootstrap/images/logo.png");
//                $("#footer").html("");
//                $("#footer").html("数字资源管理分析数据库<br />leosys Powered By leosys Co.,Ltd <div id=test1>1122</div>");
//                
//                $("#defaultImg").val("");//默认图片
//                var urlss=RESOURCE+"/bootstrap/images/noimg.jpg_100x100.jpg";
//                $("#defaultImg").val(urlss);
//                
//            }else{
//               $("#footer").html("");
//               var htmls="<p>"+companys.ccpmcname+"</p><p>"+companys.cfooterdec+"</p><p>"+companys.cfooterqita+"</p>";
//               $("#footer").html(htmls);
//               
//                var durl=companys.cdefaultimg;
//                $("#defaultImg").val("");//默认图片
//               var urls=RESOURCE+"/bootstrap/images/noimg.jpg_100x100.jpg";
//                if(durl==null||durl==''){
//                }else{
//                    var Imgs = new Image();
//                    Imgs.src ="/upload/"+durl;
//                    if (Imgs.fileSize > 0 || (Imgs.width > 0 && Imgs.height > 0)) {  
//                    urls="/upload/"+durl;
//                    }
//                }
//                $("#defaultImg").val(urls); 
//                           
//                var srcdurl=companys.cimagesrc;
//                if(srcdurl==null||srcdurl==''){
//                    $("#imgs").attr("src","/LAA/resource/tpl/bootstrap/images/logo.png");
//                }else{
//                   $("#imgs").attr("src","/upload/"+srcdurl);
//                }
//            }
//     }});
//    }
//    function FundefaultImg(vals){
//       var defimg=$("#defaultImg").val();
//       vals.src=defimg;
//    }

