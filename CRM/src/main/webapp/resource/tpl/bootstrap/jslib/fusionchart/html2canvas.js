window.registNS("Alienfe.demo");
Alienfe.demo.html2canvas = (function() {
	var a = (function() {
		var c = function(i) {
				i = i.toLowerCase().replace(/jpg/i, "jpeg");
				var j = i.match(/png|jpeg|bmp|gif/)[0];
				return "image/" + j
			};
		var e = function(l, i) {
				var j = document.createElementNS("http://www.w3.org/1999/xhtml", "a");
				j.href = l;
				j.download = i;
				var k = document.createEvent("MouseEvents");
				k.initMouseEvent("click", true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
				j.dispatchEvent(k)
			};
		var g = function(i, m) {
				var j = "png";
				var l = i.toDataURL(j);
				if (m == 0) {
					var k = window.open();
					k.document.write('<img src="' + l + '" />');
					k.document.close()
				} else {
					if ($.browser.webkit) {
						l = l.replace(c(j), "image/octet-stream");
						e(l, "baidufe_" + (new Date()).getTime() + "." + j)
					} else {
						alert("暂时只支持chrome！")
					}
				}
			};
		var h = function(j) {
				var i = $(this);
				if (i[0].disabled) {
					return
				}
				i[0].disabled = true;
				i.attr({
					"data-text": i.html()
				});
				i.html("正在生成数据，请稍后。。。");
				$("html").html2canvas({
					onrendered: function(k) {
						var l = $("input[name=createtype]:checked").val() || 0;
						g(k, l);
						i[0].disabled = false;
						i.html(i.attr("data-text"))
					}
				});
				j.preventDefault()
			};
		var d = {
			click: {
				"btn-exportasimg": h
			}
		};
		var f = function() {
				window.addEventMap($("body"), ["click"], d)
			};
		return {
			run: f
		}
	})();
	var b = function() {
			$(function() {
				a.run()
			})
		};
	return {
		init: b
	}
})();
Alienfe.demo.html2canvas.init();