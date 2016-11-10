<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>系统登录</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="${base}/static/plugins/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${base}/static/plugins/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="${base}/static/plugins/ionicons/2.0.1/css/ionicons.min.css">
    <link rel="stylesheet" href="${base}/static/css/AdminLTE.min.css">
    <link rel="stylesheet" href="${base}/static/css/skins/skin-blue.min.css">
    <link rel="stylesheet" href="${base}/static/plugins/iCheck/all.css">
    <script src="${base}/static/plugins/jquery/jquery-2.2.3.min.js"></script>
    <script src="${base}/static/plugins/layer/layer.js"></script>
    <script src="${base}/static/plugins/iCheck/icheck.min.js"></script>
    <script src="${base}/static/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="${base}/static/js/app.min.js"></script>
</head>
<body class="hold-transition login-page">
<div class="login-box">
    <div class="login-logo">
        <a><b>BasisMVC</b>企业平台</a>
    </div>
    <div class="login-box-body">
        <p class="login-box-msg">系统登录</p>
        <shiro:notAuthenticated>
            <form action="${base}/account/login" method="post" id="loginFrom">
                <div class="form-group has-feedback">
                    <input type="text" name="username" id="username" class="form-control" placeholder="账户">
                    <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
                </div>
                <div class="form-group has-feedback">
                    <input type="password" class="form-control" name="password" id="password" placeholder="密码">
                    <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                </div>
                <div class="row">
                    <div class="col-xs-8">
                        <div class="checkbox icheck">
                            <label>
                                <input type="checkbox" name="rememberMe" id="rememberMe"> 记住我
                            </label>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <button type="button" class="btn btn-primary btn-block btn-flat">登录系统</button>
                    </div>
                </div>
            </form>
        </shiro:notAuthenticated>
        <shiro:authenticated>
            <c:redirect url="/main/index"/>
        </shiro:authenticated>
    </div>
</div>
<script>
    $(function () {
        var base = "${base}";
        $('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%'
        });

        document.onkeydown = function (e) {
            var ev = document.all ? window.event : e;
            if (ev.keyCode == 13) {
                doLogin();
            }
        }

        function doLogin() {
            var username = $("#username").val();
            var password = $("#password").val();
            if (username.trim() == "" || password.trim() == "") {
                layer.msg("用户名和密码不能为空！", {icon: 7, time: 2000});
                return false;
            }
            $.ajax({
                type: "post",
                url: "${base}/account/login",
                data: $("#loginFrom").serialize(),
                dataType: "json",
                success: function (data) {
                    if (data && data.ok) {
                        layer.msg('登入成功', {icon: 1});
                        setTimeout(function () {
                            window.location.href = base + "/main/index";
                        }, 1000);
                    } else {
                        $('button').attr("disabled", false);
                        layer.msg(data.msg, {icon: 7, time: 2000});
                    }
                }, error: function (status) {
                    $('button').attr("disabled", false);
                    layer.msg("网络连接出错", {icon: 5, time: 1000});
                    console.log(status);
                }
            })
        }

        $('button').click(function () {
            doLogin();
        });
    });
</script>
</body>
</html>