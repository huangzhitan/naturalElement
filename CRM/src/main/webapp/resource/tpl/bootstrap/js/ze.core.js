/* ZE 框架
 * @author zhengchangliang 2013年5月5日19:23:44 
 * @description js弹窗代码，依赖于jquery 1.7.2 
 * @coypright zheng
 */
var ZE = {
    index: 1,
    Tool: {},
    Window: {},
    Cache: {},
    Form: {},
    ValidateBox: {
        required: true,
        validType: {}, //验证类型
        missingMessage: "", //为空时提示信息
        invalidMessage: ""//验证不通过是信息信息
    }, ajaxQueue: {
    },
    reg: {
        Email: /^[a-zA-Z0-9_\-]{1,}@[a-zA-Z0-9_\-]{1,}\.[a-zA-Z0-9_\-.]{1,}$/,
    }
};
//只能输入数字：“^[0-9]*$”
//只能输入n位的数字：“^d{n}$”
//只能输入至少n位数字：“^d{n,}$”
//只能输入m-n位的数字：“^d{m,n}$”
//只能输入零和非零开头的数字：“^(0|[1-9][0-9]*)$”
//只能输入有两位小数的正实数：“^[0-9]+(.[0-9]{2})?$”
//只能输入有1-3位小数的正实数：“^[0-9]+(.[0-9]{1,3})?$”
//只能输入非零的正整数：“^+?[1-9][0-9]*$”
//只能输入非零的负整数：“^-[1-9][0-9]*$”
//只能输入长度为3的字符：“^.{3}$”
//只能输入由26个英文字母组成的字符串：“^[A-Za-z]+$”
//只能输入由26个大写英文字母组成的字符串：“^[A-Z]+$”
//只能输入由26个小写英文字母组成的字符串：“^[a-z]+$”
//只能输入由数字和26个英文字母组成的字符串：“^[A-Za-z0-9]+$”
//只能输入由数字、26个英文字母或者下划线组成的字符串：“^w+$”
//验证用户密码:“^[a-zA-Z]w{5,17}$”正确格式为：以字母开头，长度在6-18之间，
//
//
//只能包含字符、数字和下划线。
//验证是否含有^%&',;=?$"等字符：“[^%&',;=?$x22]+”
//只能输入汉字：“^[u4e00-u9fa5],{0,}$”
//验证Email地址：“^w+[-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*$”
//验证InternetURL：“^http://([w-]+.)+[w-]+(/[w-./?%&=]*)?$”
//验证电话号码：“^((d{3,4})|d{3,4}-)?d{7,8}$”

ZE.Cache = function() {
    this.cacheArr = new Array();

    var obj = function(key,value) {
        this.key = key;
        this.value = value;
    };
    var getIndexArr = function(key) {
        for (var i = 0; i < cacheArr.length; i++) {
            var o = cacheArr[i];
            if (o.key === key) {
                return i;
            }
        }
        return -1;
    }
    return {
        addData: function(key, value) {
            var index = cacheArr.length;
            var o  = new obj(key,value);
            cacheArr[index] = o;
        },
        removeData: function(key) {
           cacheArr.splice(getIndexArr(key), 1);
        }, getData: function(key) {
            var o = cacheArr[getIndexArr(key)];
            if(typeof (o)!=="undefined" && o !== null)
              return o.value;
             else
                 return null;
        },clear : function(){
            cacheArr.length = 0;
        }
    };
}();

ZE.Tool = function() {
    return {
        isIE: function() {
            return !!window.ActiveXObject;
        },
        isFF: function() {
            return navigator.userAgent.indexOf("Firefox") > 0;
        },
        isChrome: function() {
            return navigator.userAgent.indexOf("Chrome") > 0;
        },
        getBrowserVersion: function() {
            var trim_Version = navigator.userAgent;
            if (this.isIE()) {
                if (trim_Version.indexOf("MSIE 7.0") > 0) {
                    return "IE7";
                }
                if (trim_Version.indexOf("MSIE 6.0") > 0) {
                    return "IE6";
                }
                if (trim_Version.indexOf("MSIE 8.0") > 0) {
                    return "IE8";
                }
                if (trim_Version.indexOf("MSIE 9.0") > 0) {
                    return "IE9";
                }
                if (trim_Version.indexOf("MSIE 10.0") > 0) {
                    return "IE10";
                }
                return "unkown";
            }
            if (this.isFF()) {
                return "Firefox";
            }
            if (this.isFF()) {
                return "Chrome";
            }
            return "unkown";
        },
        getStrlength: function(str) {
            var cArr = str.match(/[^x00-xff]/ig);
            return str.length + (cArr === null ? 0 : cArr.length);
        },
        testReg: function(reg, str) {
            var patten = new RegExp(reg);
            return patten.test(str);
        },
        convertJsonByFormDataSerialize: function(form) {
            var type = typeof form;
            var array;
            if(type === "string"){
                 array = $("#" + form).serializeArray();
            }
            if(type === "object"){
                array = form.serializeArray();
            }
            var serializeObj = {};
            $(array).each(function() {
                var arr = this.name.split(":");
                if (serializeObj[this.name]) {
                    if ($.isArray(serializeObj[this.name])) {
                        serializeObj[this.name].push(this.value);
                    } else {
                        serializeObj[this.name] = [serializeObj[this.name], this.value];
                    }
                } else {
                    if(arr.length >1){
                         var obj = {};
                         obj[arr[1]] = this.value;
                         serializeObj[arr[0]] = obj; //如何属性中包含对象的
                    }else{
                        serializeObj[this.name] = this.value;
                    }
                }
            });
            return serializeObj;
        },
        toString: function(object) {
            var type = typeof object;
            if ('object' === type) {
                if (Array === object.constructor)
                    type = 'array';
                else if (RegExp === object.constructor)
                    type = 'regexp';
                else
                    type = 'object';
            }
            switch (type) {
                case 'undefined':
                case 'unknown':
                    return;
                case 'function':
                case 'boolean':
                case 'regexp':
                    return object.toString();
                case 'number':
                    return isFinite(object) ? object.toString() : 'null';
                case 'string':
                    return '"' + object.replace(/(|")/g, "$1").replace(/n|r|t/g, function() {
                        var a = arguments[0];
                        return (a == 'n') ? 'n' : (a == 'r') ? 'r' : (a == 't') ? 't' : ""
                    }) + '"';
                case 'object':
                    if (object === null)
                        return 'null';
                    var results = [];
                    for (var property in object) {
                        var value = jQuery.toJSON(object[property]);
                        if (value !== undefined)
                            results.push(jQuery.toJSON(property) + ':' + value);
                    }
                    return '{' + results.join(',') + '}';
                case 'array':
                    var results = [];
                    for (var i = 0; i < object.length; i++) {
                        var value = jQuery.toJSON(object[i]);
                        if (value !== undefined)
                            results.push(value);
                    }
                    return '[' + results.join(',') + ']';
            }

        }

    }
}();
ZE.Window = function() {
    var W = "win_";
    var dialog = function(cfg) {
        cfg.id = cfg.id || "";
        var id = W + cfg.id +  ++ZE.index;
        var tool = ZE.Tool;
        cfg.content = cfg.content || "";
        var dialogHtml = "<div id='" + id + "'>" + cfg.content + "</div>";
        if (tool.getBrowserVersion() === "IE6" && cfg.href !== null) {
            dialogHtml = "<div id='" + id + "'><iframe src='" + cfg.href + "' id='iframe" + id + "' name='iframe" + id + "' width='" + cfg.width + "px' heigth='" + cfg.height + "px' frameborder='0' style='_width:99.6%'></iframe></div>";
            cfg.href = null;
        }
        if (!$("#" + id)[0]) {
            $("body").append(dialogHtml);
        }
        cfg.title = cfg.title || "弹窗";
        cfg.width = cfg.width || 200;
        cfg.height = cfg.height || 150;
        cfg.modal = cfg.modal || true;
        cfg.collapsible = cfg.collapsible || false;
        cfg.minimizable = cfg.minimizable || false;
        cfg.maximizable = cfg.maximizable || false;
        cfg.resizable = cfg.resizable || false;
        cfg.closed = cfg.closed || false;

        // cfg.width = $("#"+id).width(); //判断按钮个数自使用窗体大小
        $('#' + id).dialog({
            title: cfg.title,
            width: cfg.width,
            height: cfg.height,
            closed: cfg.closed,
            cache: cfg.cache,
            href: cfg.href,
            modal: cfg.modal,
            buttons: cfg.buttons,
            toolbar: cfg.toolbar,
            collapsible: cfg.collapsible,
            minimizable: cfg.minimizable,
            maximizable: cfg.maximizable,
            resizable: cfg.resizable,
            onClose:function(){
              if(cfg.callback){
                    var returnValue = ZE.Cache.getData(id);
                    ZE.Cache.removeData(id);
                    cfg.callback(returnValue);
               }
                $(".window").remove();
                $(".window-shadow").remove();
                $(".window-mask").remove();
            }
        });
    }
    var message = function(cfg) {
        $.messager.alert(cfg.title, cfg.content, cfg.infotype);
    };
    var tooltip = function(cfg) {
        var id = "boytooltip-" + cfg.id;
        var title = $("#" + cfg.id).attr("title") || cfg.content || "";
        $("#" + cfg.id).removeAttr("title");
        var tipHml = '<div id="' + id + '" class="tooltip tooltip-bottom" style="z-index: 9009; position:absolute;display:none">' +
                '<div class="tooltip-content">' + title + '</div>' +
                '<div class="tooltip-arrow-outer" style="border-bottom-color: rgb(149, 184, 231);"></div>' +
                '<div class="tooltip-arrow" style="border-bottom-color: rgb(255, 255, 255);"></div></div>';
        if ($("#" + id)[0]) {
            $("#" + id).remove();
        }
        $(tipHml).appendTo("body");
        var tip = $("#" + id);
        $("#" + cfg.id).bind("mouseover", function(e) {
            tip.show();
            var x = Math.ceil($("#" + cfg.id).offset().left);
            var y = Math.ceil($("#" + cfg.id).offset().top + $("#" + cfg.id).height());
            var xContentGap = Math.ceil($("#" + id + " .tooltip-content").width() / 2 - $("#" + cfg.id).width() / 2);
            x = x - xContentGap - 5; //偏差
            tip.css({
                left: x,
                top: (y + 6)
            });
        }).bind("mouseout", function() {
            tip.hide();
        });
    };
    return {
        /**
         * title :标题
         * content :内容
         * type : 窗口类型（error,question,info,warning）
         */
        alert: function(title, content, type) { //type : error,question,info,warning.
            type = type || "info";
            var cfg = {
                "title": title,
                "content": content,
                "infotype": type
            };
            message(cfg);
        },
        /**
         * title :标题
         * content :内容
         * callback ： 回调函数
         */
        confirm: function(title, content, callback) {
            $.messager.confirm(title, content, function(r) {
                if (r) {
                    callback();
                }
            }
            );
        },
        /**
         *  cfg{}  包含以下属性值
         *  id : 元素ID
         *  title : 标题
         * width :  宽度
         * height : 高度
         * closed : 关闭按钮 
         * cache :  启用缓存,  
         * href :   链接地址,  
         * modal :  是否模态对话框,
         * buttons : 按钮,
         * toolbar : 工具栏,
         * collapsible : 是否可扩展大小,
         * minimizable : 最小化,
         * maximizable :  最大化,,
         * resizable : 是否可以移动
         * 
         */
        dialog: function(cfg) {
            dialog(cfg);
        },
        /**
         * elementId :元素ID
         * tipTitle : 提示信息
         */
        tooltip: function(elementId, tipTitle) {
            var cfg = {
                content: tipTitle,
                id: elementId
            };
            tooltip(cfg);

        },
        /**
         * title :标题
         * content :内容
         * callback ： 回调函数
         */
        messageBox: function(title, content, buttons, width, height, callback) {
            var htm = '<div class="messager-icon messager-info" style="margin:10px 0px 0px 10px"></div><div style="margin:20px 0 0 47px">' + content + '</div>';
            var cfg = {
                "id": "0",
                "title": title,
                "content": htm,
                "width": width,
                "height": height,
                buttons: buttons

            };
            dialog(cfg);
        },
        close: function(id, returnValue) { //关闭窗口
            var winId = W + id + ZE.index;
            if(returnValue !== undefined && returnValue !== null && returnValue !== ''){
                ZE.Cache.removeData(winId);
                ZE.Cache.addData(winId,returnValue);
            }
            $('#' + winId ).dialog('close');

        }

    }
}();
ZE.ValidateBox = function() {
    var doValidateReg = function(exp) {
        var tool = ZE.Tool;
        var domValue = exp.domValue;
        var validType = exp.validType;//验证类型
        var validObj = exp.obj;//验证类型对象 
        if (exp.required) {
            if (domValue === null || domValue === "") {
                return false;
            }
        }
        if (validType === "required") {
            if (domValue === null || domValue === "") {
                return false;
            } else {
                return true;
            }
        }
        if (validType === "length") {
            var length = validObj;
            var min = length.minLength;
            var max = length.maxLength;
            var len = tool.getStrlength(domValue);
            if (len < min || len > max) {
                return false;
            } else {
                return true;
            }
        } else if (validType === "email") {
            return tool.testReg(ZE.reg.Email, domValue);
        } else if (validType === "regular") {
            return tool.testReg(validObj.regular, domValue);
        }
    };

    var initToolTip = function(cfg) {
        var id = "boytooltip-" + cfg.type + "-" + cfg.id;
        var title = $("#" + cfg.id).attr("title") || cfg.content || "";
        var tipHml = '<div id="' + id + '" class="tooltip tooltip-right" tabindex="-1" style="z-index: 9009; position:absolute;display:none;background-color: rgb(255, 255, 204);">' +
                '<div class="tooltip-content" >' + title + '</div>' +
                '<div class="tooltip-arrow-outer" style="border-right-color: rgb(149, 184, 231);"></div>' +
                '<div class="tooltip-arrow" style="border-right-color: rgb(255, 255, 204);"></div>\n\
                <div class="validType" style="display:none">' + ZE.Tool.toString(cfg) + '</div></div>';
        $(tipHml).appendTo("body");
    };
    var showToolTip = function(id, elementId) {
        $("#" + elementId).show();
        var x = Math.ceil($("#" + id).offset().left + $("#" + id).width());
        var y = Math.ceil($("#" + id).offset().top);
        var tip = $("#" + elementId);
        tip.css({
            left: x + 25,
            top: y
        });
    };
    return {
        validate: function(id, rule) {
            rule.required = rule.required || false;
            var missingMessage = "该字段不能为空值";
            var domValue = $("#" + id).val(); //dom值
            var cfg = {};
            if (rule.validType) {
                var obj = rule.validType;
                var invalidMessage = "";
                for (var p in obj) {
                    switch (p) {
                        case "required":
                            cfg = {
                                id: id,
                                type: "required",
                                content: missingMessage,
                                validType: {
                                    required: {
                                        domValue: domValue
                                    }
                                }
                                // tirggerEvent: rule.tirggerEvent //触发事件
                            };
                            initToolTip(cfg);
                            break;
                        case  "length" :
                            var minLength = obj[p].minLength || 0;
                            var maxLength = obj[p].maxLength || 256;
                            invalidMessage = obj[p].invalidMessage || "字符长度在" + minLength + "到" + maxLength + "之间";
                            cfg = {
                                id: id,
                                type: "length",
                                content: invalidMessage,
                                validType: {
                                    length: {
                                        minLength: minLength,
                                        maxLength: maxLength,
                                        domValue: domValue
                                    }
                                }
                                // tirggerEvent: rule.tirggerEvent //触发事件
                            };
                            initToolTip(cfg);
                            break;
                        case  "email" :
                            invalidMessage = obj[p].invalidMessage || "请输入正确的邮件地址";
                            cfg = {
                                id: id,
                                type: "email",
                                content: invalidMessage,
                                validType: {
                                    email: {
                                        domValue: domValue,
                                        reg: ZE.reg.Email
                                    }
                                }
                            };
                            initToolTip(cfg);
                            break;
                        case "regular":
                            invalidMessage = obj[p].invalidMessage;
                            cfg = {
                                id: id,
                                type: "regular",
                                content: invalidMessage,
                                validType: {
                                    regular: {
                                        domValue: domValue,
                                        regular: obj[p].regular
                                    }
                                }
                            };
                            initToolTip(cfg);
                            break;
                        case "ajax":
                            invalidMessage = obj[p].invalidMessage;
                            cfg = {
                                id: id,
                                type: "ajax",
                                required: true,
                                content: invalidMessage,
                                validType: {
                                    ajax: {
                                        validateUrl: obj[p].validateUrl,
                                        params: obj[p].params
                                    }
                                }
                            };
                            initToolTip(cfg);
                            break;

                        default :
                            break;
                    }
                }
            }

            //添加绑定事件
            var startEvent = rule.tirggerEvent || "blur"; //触发事件
            //  var endEvent = "blur";
            $("#" + id).bind(startEvent, function() {

                var obj = rule.validType;
                for (var p in obj) {
                    var elementId = "boytooltip-" + p + "-" + id;
                    var exp = {
                        required: obj[p].required || false,
                        validType: p,
                        obj: obj[p],
                        domValue: $(this).val() || ""
                    };
                    if (p === "ajax") { //ajax验证
                        var params = obj.ajax.params || {};
                        params[id] = $(this).val();
                        ZE.ajaxQueue.offer({
                            type: "get",
                            url: obj.ajax.validateUrl,
                            data: params,
                            success: function(msg, s, j) {
                                if (!msg.status) {
                                    $("#" + elementId).hide();
                                } else {
                                    showToolTip(id, elementId);
                                }
                                var index = 0;
                                $("div[id*='" + id + "']").each(function() {
                                    if ($(this).css("display") === "block") {
                                        index++;
                                    }
                                });
                                if (index === 0) {
                                    $("#" + id).removeClass("validatebox-invalid");//移除强调表单样式
                                    $("#" + id).removeAttr("rule");
                                } else {
                                    $("#" + id).addClass("validatebox-invalid"); //添加强调表单样式
                                    $("#" + id).attr("rule", "required-" + p);
                                }
                            }
                        });
                    } else {
                        if (doValidateReg(exp)) {
                            $("#" + elementId).hide();
                        } else {
                            showToolTip(id, elementId);
                        }
                        var index = 0;
                        $("div[id*='" + id + "']").each(function() {
                            if ($(this).css("display") === "block") {
                                index++;
                            }
                        });
                        if (index === 0) {
                            $("#" + id).removeClass("validatebox-invalid");//移除强调表单样式
                            $("#" + id).removeAttr("rule");
                        } else {
                            $("#" + id).addClass("validatebox-invalid"); //添加强调表单样式
                            $("#" + id).attr("rule", "required-" + p);
                        }
                    }
                }
            });
        },
        isValid: function() { //验证表达是否验证通过，表单提交时调用
            $("div[id*='boytooltip']").each(function() {
                var tipId = $(this).attr("id");
                var expArr = tipId.split("-");
                var validObj = $("#" + tipId + " .validType").html();
                if (expArr.length === 3) {
                    var cfg = $.parseJSON(validObj);
                    var obj = cfg.validType;
                    for (var p in obj) {
                        if (p === "ajax") { //ajax验证
                            if ($("#" + expArr[2]).val() === "")
                                break;
                            var params = obj.ajax.params || {};
                            params[expArr[2]] = $("#" + expArr[2]).val();
                            ZE.ajaxQueue.offer({
                                type: "get",
                                url: obj.ajax.validateUrl,
                                data: params,
                                success: function(msg) {
                                    if (!msg.status) {
                                        $("#" + tipId).hide();
                                    } else {
                                        showToolTip(expArr[2], tipId);
                                    }
                                    var index = 0;
                                    $("div[id*='" + expArr[2] + "']").each(function() {
                                        if ($(this).css("display") === "block") {
                                            index++;
                                        }
                                    });
                                    if (index === 0) {
                                        $("#" + expArr[2]).removeClass("validatebox-invalid");//移除强调表单样式
                                        $("#" + expArr[2]).removeAttr("rule");
                                    } else {
                                        $("#" + expArr[2]).addClass("validatebox-invalid"); //添加强调表单样式
                                        $("#" + expArr[2]).attr("rule", "required-" + p);
                                    }
                                }
                            });
                            //   $.ajax();
                        } else {
                            var exp = {
                                required: obj[p].required || false,
                                validType: p,
                                obj: obj[p],
                                domValue: $("#" + expArr[2]).val() || ""
                            };
                           
                            if (doValidateReg(exp)) {
                                $("#" + tipId).hide();
                            } else {
                                showToolTip(expArr[2], tipId);
                            }
                            var index = 0;
                            $("div[id*='" + expArr[2] + "']").each(function() {
                                if ($(this).css("display") === "block") {
                                    index++;
                                }
                            });
                            if (index === 0) {
                                $("#" + expArr[2]).removeClass("validatebox-invalid");//移除强调表单样式
                                $("#" + expArr[2]).removeAttr("rule");
                            } else {
                                $("#" + expArr[2]).addClass("validatebox-invalid"); //添加强调表单样式
                                $("#" + expArr[2]).attr("rule", "required-" + p);
                            }
                        }
                    }
                }
            });

            if ($(".validatebox-invalid").length > 0) {
                return false;
            } else {
                return true;
            }
        }
    };
}();
ZE.ajaxQueue = function() {
    var requests = new Array();

    return {
        offer: function(options) {
            var _self = this;
            xhrOptions = $.extend({}, options, {
                complete: function(jqXHR, textStatus) {
                    if (options.complete)
                        options.complete.call(this, jqXHR, textStatus);
                    _self.poll();
                },
                beforeSend: function(jqXHR, settings) {
                    if (options.beforeSend)
                        var ret = options.beforeSend.call(this, jqXHR, settings);
                    if (ret === false) {
                        // $('#log').append('<p>an ajax request cancelled</p>');
                        _self.poll();
                        return ret;
                    }
                }
            });

            requests.push(xhrOptions);

            if (requests.length === 1) {
                $.ajax(xhrOptions);
            }
        },
        poll: function() {
            if (this.isEmpty()) {
                return null;
            }

            var processedRequest = requests.shift();
            var nextRequest = this.peek();
            if (nextRequest !== null) {
                $.ajax(nextRequest);
            }

            return processedRequest;
        },
        peek: function() {
            if (this.isEmpty()) {
                return null;
            }

            var nextRequest = requests[0];
            return nextRequest;
        },
        isEmpty: function() {
            return requests.length === 0;
        }
    }
}();
ZE.Form = function() { //依赖于jquery.form.js 重新封装ajaxform 方法 
    // spring mvc  json 数据提交方式
    //此方法用于form 表单提交
    return {
        ajaxFormSubmit: function(options) {
            var method = options.type || "get";
            if( (options.formId === null || options.formId === "" ) && options.$form === null ){
                console.info("form为空");
                return;
            }
            var $form;
            if(options.formId !== "undefined"){
                $form = options.formId;
            }
            if($form === null )return;
            var data = ZE.Tool.convertJsonByFormDataSerialize($form);
            var opt = {
                type: method,
                dataType: options.dataType,
                url: options.url,
                data: $.toJSON(data),
                contentType: options.contentType, //application/xml
                success: options.success,
                beforeSend: function(j, s) {
                    if (options.beforeSubmit) {
                        return options.beforeSubmit();
                    }
                    return true;//默认返回true

                }
            }
            ZE.ajaxQueue.offer(opt);
            // $.ajax(opt);
        },
        ajaxSubmit: function(options) {
            var method = options.type || "get";
            var contentType = options.contentType || "application/json";
            var data = options.data || "";
            var opt = {
                type: method,
                url: options.url,
                data: $.toJSON(data),
                contentType: contentType,
                success: options.success
            }
            ZE.ajaxQueue.offer(opt);
        }
    }
}();