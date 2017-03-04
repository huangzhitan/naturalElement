/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function(){
   $('#itemId').parent().addClass('tab_wh');
   $('#zikudiv').parent().addClass('tab_wh');
   $('nounit').next().css('width','470px');
   $('#cbktypes').parent().css('margin-left','120px');
   $('#qkform .huis table tr td:first').css('width','30px');
   $('righttop .righttopsub>div>span').eq(1).css('font-size','60px');

});

$(function(){
    $(".userinfo").hover(function(){
       $(this).find("#info").css("display","block");
    },function(){
        $(this).find("#info").css("display","none");
    });
});



