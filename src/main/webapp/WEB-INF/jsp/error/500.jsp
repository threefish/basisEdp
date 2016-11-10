<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true"%>
<html>
<head>
    <title>服务器繁忙</title>
</head>
<body>
<div style="text-align: center">
    <H1>错误：</H1><%=exception%>
    <H2>错误内容：</H2>
    <%=exception.printStackTrace(response.getWriter())%>
</div>
</body>
</html>
