/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//返回顶部
   $(document).ready(function () {
            $.goup({
                trigger: 100,
                bottomOffset: 150,
                locationOffset: 100,
                title: '返回顶部',
                titleAsText: true
            });
        });

   $(function(){
        


    });
//    function queryData(){
//        $.ajax({
//           type:"post",
//           url:"/LAA/app/index/querycompany",
//           success:function(data){
//            var companys=data.params.companys;
//            if(companys==="nulls"){
//                $("#footer").html("");
//                $("#footer").html("数字资源管理分析数据库<br />leosys Powered By leosys Co.,Ltd"
//    +"<div id=test1>1122</div>");
//            }else{
//               $("#footer").html("");
//               var htmls="<p>"+companys.ccpmcname+"</p><p>"+companys.cfooterdec+"</p><p>"+companys.cfooterqita+"</p>";
//               $("#footer").html(htmls);
//            }
//     }});
//    }
 $(function(){
        $(document.body).addClass("cbp-spmenu-push");
        $(".sidebar").addClass("cbp-spmenu cbp-spmenu-vertical cbp-spmenu-left")
                     .attr("id","cbp-spmenu-s1");
        $(".navigate").click(function(){
            $(this).attr("id","showLeftPush");
            $("#cbp-spmenu-s1").toggleClass("cbp-spmenu-open");
            $(document.body).toggleClass("cbp-spmenu-push-toright");
        }).click();
       
    });
    $(function(){
    $('#showLeftPush').click(function(){
        $(window).trigger("resize");
    });
    $(window).resize(function(){
    })
});

$(function(){
	$('#test1').on('click', function(){
    var ii = layer.load(
    	);
    //此处用setTimeout演示ajax的回调
    setTimeout(function(){
        layer.close(ii);
    }, 1000);
});
})
$('#searchTxt').typeahead({
       source: function(query, process){
       $.ajax({
       url: "<!--{url('index','mydigit','search')}-->",
               type: 'GET',
               dataType: 'JSON',
               async: true,
               data: 'word=' + query,
               success: function(data){
                var arr = [];
                for (var i in data)
                {
                   if (data[i]){
                      arr.push(data[i]['itemname']);
                   }
                }
                process(arr);
               }
       })
       },
        items: 10
    });

    
    $(document).ready(function(){
        LAA.init("<!--{url('index','$model')}-->");
    });