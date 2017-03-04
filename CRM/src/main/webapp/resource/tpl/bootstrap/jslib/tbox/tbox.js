(function($) {
	$.tbox = function(data) {
		$.tbox.loading();
		$.isFunction(data) ? data.call($) : $.tbox.reveal(data);
	}
	$.tbox.popup = function(url,method,para,title) {
            $.tbox.settings.title=title;
		$.tbox.init($.tbox.settings);
		$.tbox.loading();
		method = !method ? "GET":method.toUpperCase();
		if (url.match(/#/)) {
			var urls    = url.split('#')[0];
			var target = url.replace(urls,'');
			$.tbox.reveal($(target).clone().show());
		} else if(method == "GET") {
			$.get(url,para,function(data){$.tbox.reveal(data);});
		} else if(method == "POST") {
			$.post(url,para,function(data){$.tbox.reveal(data);});
		}
	}
	$.tbox.settings = {
		tbox_html  : ' \
		<div id="tbox" style="display:none;" > \
                            <div class="modal-header">\
                              <button data-dismiss="modal" class="close" type="button">x</button>\
                              <h3 id="common_title" name="tboxTitle">%s</h3></div>\
			<table class="popup" align="center" border="0" border="0" cellpadding="0" cellspacing="0"><tr><td> \
			  <div class="body"> \
				<div id="tbox_content" class="content"></div> \
			  </div> \
			</td></tr></table> \
		</div> \
		<div id="tbox_background" class="tbox_background" style="display:none;"></div> \ ',
               title:'',
               inited:false
            
	}
        $.tbox.adjustPos = function(){
            var pageScroll = $.tbox.getPageScroll();
            $('#tbox').css({
                top: pageScroll[1] + ($.tbox.getPageHeight() / 10),
                left: pageScroll[0] + document.body.clientWidth / 2 - $('#tbox').width() / 2
            }).show();
        }
	$.tbox.loading = function() {
		if($('#tbox .loading').length == 1) return true;
		$('#tbox .content').empty().append('<div class="loading"><div class="span">页面加载中,请稍后...</div></div>');
		$('#tbox_background').css({
			height:	$(document).height()
		}).show();
                $.tbox.adjustPos();
	}
	//向tbox中填充数据
	$.tbox.reveal = function(data) {
		$('#tbox .content').empty();//.append(data)
		$("#tbox_content").html(data);
		$('#tbox .body').children().fadeIn('fast');
                $.tbox.adjustPos();
	}
	//执行关闭
	
	$.tbox.close = function() {
		$(document).trigger('close.tbox');
		return false;
	}
	//绑定关闭事件
	
	$(document).bind('close.tbox', function() {
		$('#tbox').fadeOut(function() {
			$('#tbox_background').hide();
			$('#tbox').remove();
		})
	})


  $.tbox.init = function(settings,callback) {
    if ($.tbox.settings.inited) {
       $("#tbox").remove();
       $("#tbox_background").remove();
    } else {
      $.tbox.settings.inited = true;
    }
    var tbox_html = $.tbox.settings.tbox_html.replace("%s",settings.title==""?"新窗口":settings.title);
    $('body').append(tbox_html);
	//tbox中，设置class为close的按钮为关闭;
    $('#tbox .close').click($.tbox.close);
    
  }
  // getPageScroll() by quirksmode.com
  $.tbox.getPageScroll = function() {
    var xScroll, yScroll;
    if (self.pageYOffset) {
      yScroll = self.pageYOffset;
      xScroll = self.pageXOffset;
    } else if (document.documentElement && document.documentElement.scrollTop) {	 // Explorer 6 Strict
      yScroll = document.documentElement.scrollTop;
      xScroll = document.documentElement.scrollLeft;
    } else if (document.body) {// all other Explorers
      yScroll = document.body.scrollTop;
      xScroll = document.body.scrollLeft;
    }
    return new Array(xScroll,yScroll)
  }

  // adapter from getPageSize() by quirksmode.com
  $.tbox.getPageHeight = function() {
    var windowHeight
    if (self.innerHeight) {	// all except Explorer
      windowHeight = self.innerHeight;
    } else if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode
      windowHeight = document.documentElement.clientHeight;
    } else if (document.body) { // other Explorers
      windowHeight = document.body.clientHeight;
    }
    return windowHeight
  }
})(jQuery);
