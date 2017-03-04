(function($) {
	$.alerts = {
		verticalOffset: -75,                // vertical offset of the dialog from center screen, in pixels
		horizontalOffset: 0,                // horizontal offset of the dialog from center screen, in pixels/
		repositionOnResize: true,           // re-centers the dialog on window resize
		overlayOpacity: .01,                // transparency level of overlay
		overlayColor: '#FFF',               // base color of overlay
		draggable: true,                    // make the dialogs draggable (requires UI Draggables plugin)
		okButton: '&nbsp;确定&nbsp;',         // text for the OK button
		cancelButton: '&nbsp;取消&nbsp;', // text for the Cancel button
		dialogClass: null,                  // if specified, this class will be applied to all dialogs
		picimg:"/LAA/resource/tpl/bootstrap/jslib/jalert/",

                getStrLen: function(val) {    //传入一个字符串
                    var len = 0;
                    for (var i = 0; i < val.length; i++) {
                        if (val[i].match(/[^\x00-\xff]/ig) != null) //全角 
                            len += 2; //如果是全角，占用两个字节
                        else
                            len += 1; //半角占用一个字节
                    }
                    return len;

                },
		alert: function(message, title, callback) {
			if( title == null ) title = ' ';
			$.alerts._show(title, message, null, 'alert', function(result) {
				if( callback ) callback(result);
			});
		},
		confirm: function(message, title, callback) {
			if( title == null ) title = ' ';
			$.alerts._show(title, message, null, 'confirm', function(result) {
				if( callback ) callback(result);
			});
		},

		prompt: function(message, title, callback) {
			if( title == null ) title = ' ';
			$.alerts._show(title, message, null, 'prompt', function(result) {
				if( callback ) callback(result);
			});
		},
		_show: function(title, msg, value, type, callback) {

                    $.alerts._hide();
                    $.alerts._overlay('show');
                     $("BODY").append(
                        '<div id="popup_container" style="width:350px;height:160px; border:1px solid #ccc">'+
                        '<div class="modal-header">'+
                            '<button data-dismiss="modal" id="popup_close" class="close" type="button" style="margin-top:4px!important">X</button>'+
                            '<h3 id="popup_title"></h3>'+
                        '</div>'+
                        '<div class="modal-body align-center" style="text-align: center;background-color:#ffffff">'+
                        '<div id="popup_message" style="background-color:#f5f5f5"></div></div>'+
                        '<div id="popup_footer" class="modal-footer" style="background-color:#ffffff">'+
                        '</div></div>');

                           
			if( $.alerts.dialogClass ) $("#popup_container").addClass($.alerts.dialogClass);

			var pos =  'absolute';

			$("#popup_container").css({
				position: pos,
				zIndex: 99999,
				padding: 0,
				margin: 0
			});

			$("#popup_title").text(title);
			if(msg) {
				msg = "<table cellspacing='0' cellpadding='0' border='0' align='center'><tr><td valign='top'><img src='"+this.picimg+type+".gif' class='mid left' width='32' height='32'/></td><td align='left' style='padding-left:20px;'>" + msg + "</td></tr></table>";
			}
                        if(this.getStrLen(msg)>1000){
                            msg = msg.substring(0,1000) + "(内容太多，省略...)";
                        }
			$("#popup_message").text(msg);
			$("#popup_message").html($("#popup_message").text().replace(/\n/g, '<br />'));

			$("#popup_container").css({
				minWidth: $("#popup_container").outerWidth(),
				maxWidth: $("#popup_container").outerWidth()
			});

			$.alerts._reposition();
			$.alerts._maintainPosition(true);

			switch( type ) {
				case 'alert':
					$("#popup_footer").append('<div id="popup_panel"><button id="popup_ok" class="btn btn-info btn-mini">' + $.alerts.okButton + '</button></div>');
					$("#popup_ok").click( function() {
						$.alerts._hide();
						callback(true);
					});
					$("#popup_ok").focus().keypress( function(e) {
						if( e.keyCode == 13 || e.keyCode == 27 ) $("#popup_ok").trigger('click');
					});
                                        $("#popup_close").click( function() {
						if( callback ) callback(false);
						$.alerts._hide();
					});
				break;
				case 'confirm':
					$("#popup_footer").append('<div id="popup_panel"><button id="popup_ok" class="btn btn-info btn-mini">' + $.alerts.okButton + '</button>&nbsp;<button id="popup_cancel" class="btn btn-mini">' + $.alerts.cancelButton + '</button></div>');
					$("#popup_ok").click( function() {
						if( callback ) callback(true);
						$.alerts._hide();
					});
					$("#popup_cancel").click( function() {
						if( callback ) callback(false);
						$.alerts._hide();
					});
                                        $("#popup_close").click( function() {
						if( callback ) callback(false);
						$.alerts._hide();
					});
					$("#popup_ok").focus();
					$("#popup_ok, #popup_cancel").keypress( function(e) {
						if( e.keyCode == 13 ) $("#popup_ok").trigger('click');
						if( e.keyCode == 27 ) $("#popup_cancel").trigger('click');
					});
				break;
				case 'prompt':
					$("#popup_footer").append('<br /><input type="text" size="30" id="popup_prompt" />').after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" /> <input type="button" value="' + $.alerts.cancelButton + '" id="popup_cancel" /></div>');
					$("#popup_prompt").width( $("#popup_message").width() );
					$("#popup_ok").click( function() {
						var val = $("#popup_prompt").val();
						if( callback ) callback( val );
						$.alerts._hide();
					});
					$("#popup_cancel").click( function() {
						if( callback ) callback( null );
						$.alerts._hide();
					});
					$("#popup_prompt, #popup_ok, #popup_cancel").keypress( function(e) {
						if( e.keyCode == 13 ) $("#popup_ok").trigger('click');
						if( e.keyCode == 27 ) $("#popup_cancel").trigger('click');
					});
					if( value ) $("#popup_prompt").val(value);
					$("#popup_prompt").focus().select();
				break;
			}

			if( $.alerts.draggable ) {
				try {
					$("#popup_container").draggable({ handle: $("#popup_title") });
					$("#popup_title").css({ cursor: 'move' });
				} catch(e) { 
                                    /* requires jQuery UI draggables */ 
                                }
			}
		},

		_hide: function() {
			$("#popup_container").remove();
			$.alerts._overlay('hide');
			$.alerts._maintainPosition(false);
		},

		_overlay: function(status) {
			switch( status ) {
				case 'show':
					$.alerts._overlay('hide');
					$("BODY").append('<div id="popup_overlay" class="modal-backdrop2 fade in"></div>');
                                        $("#popup_overlay").css("z-index","99999");
				break;
				case 'hide':
					$("#popup_overlay").remove();
				break;
			}
		},

		_reposition: function() {
			var top = (($(window).height() / 2) - ($("#popup_container").outerHeight() / 2)) + $.alerts.verticalOffset;
			var left = (($(window).width() / 2) - ($("#popup_container").outerWidth() / 2)) + $.alerts.horizontalOffset;
			if( top < 0 ) top = 0;
			if( left < 0 ) left = 0;
                        var scrollTop = $(document).scrollTop();
                        if(scrollTop){
                            top += scrollTop;
                        }
			// IE6 fix
			//if( $.browser.msie && parseInt($.browser.version) <= 6 ) top = top + $(window).scrollTop();

			$("#popup_container").css({
				top: top + 'px',
				left: left + 'px'
			});
			$("#popup_overlay").height( $(document).height() );
		},

		_maintainPosition: function(status) {
			if( $.alerts.repositionOnResize ) {
				switch(status) {
					case true:
						$(window).bind('resize', $.alerts._reposition);
					break;
					case false:
						$(window).unbind('resize', $.alerts._reposition);
					break;
				}
			}
		}
	};
	jAlert = function(message, title, callback) {
		$.alerts.alert(message, title, callback);
	};
	jConfirm = function(message, title, callback) {
		$.alerts.confirm(message, title, callback);
	};
	jPrompt = function(message, title, callback) {
		$.alerts.prompt(message, title, callback);
	};
})(jQuery);