<%-- 
    Document   : 500
    Created on : 2013-5-8, 16:00:59
    Author     : Sam.Zheng <zcl1866@sina.com>
--%>

<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"  isErrorPage="true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>出错啦</title>
        <style>
            h1{
                color: red;
            }
            a{
                font-size: 14px;
            }
        </style>
    </head>
    <body>

        <table style="width: 100%"  border ="0">
       <tr>
           <td style="width:20%"><h1>系统发生错误啦</h1>
        </td>
        <td style="text-align:left">
            <a href="javascript:history.back(-1)">单击返回</a>
        </td>
        </tr>
    <tr>
        <td colspan="2">
            <a href="javascript:showDetial();">错误详细信息</a>
        </td>          
    </tr>
    <tr style="display: none" id="exc">
        <td colspan="2">
            <div>
                <%=exception.getMessage()%>
                <%
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    exception.printStackTrace(pw);
                    out.println(sw);
                %>
            </div>
        </td>
    </tr>
</table>
</body>
<script>
    function showDetial(){
        document.getElementById("exc").style.display = "";
    }
</script>    
</html>
