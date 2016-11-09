<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<h1> 登录</h1>
<shiro:notAuthenticated>
    <form action="${base}/account/login" method="post">
        <table>
            <tr>
                <td>用户名：</td>
                <td><input type="text" name="username"/></td>
            </tr>
            <tr>
                <td>密码：</td>
                <td><input type="password" name="password"/></td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <label><input type="checkbox" name="rememberMe"/>记住我</label>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <button type="submit">登录</button>
                </td>
            </tr>
        </table>
    </form>
</shiro:notAuthenticated>
<shiro:authenticated>
    <c:redirect url="/main/index"/>
</shiro:authenticated>
</body>
</html>