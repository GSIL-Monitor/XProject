<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户中心注册</title>
    <!-- css -->
    <link type="text/css" rel="stylesheet" href="static/css/lib/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="static/css/lib/font-awesome.min.css">
    <link type="text/css" rel="stylesheet" href="static/css/style/regist.css">
    <!-- js -->
    <script type="text/javascript" src="static/js/lib/jquery-2.2.4.min.js"></script>
    <script type="text/javascript" src="static/js/lib/bootstrap.min.js"></script>
    <script type="text/javascript" src="static/js/script/regist.js"></script>
</head>
<body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div style="padding-right: 15px;">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="tologin"><i class=" icon-arrow-left"></i>&nbsp;&nbsp;登录</a></li>
            </ul>
        </div>
    </nav>
    <div class="container">
        <div class="row">
            <div class="col-md-8 col-md-offset-2 text-center">
                <div class="top">
                    <h2>注 册</h2>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 col-md-offset-3 form-body">
                <form role="form" action="regist" method="post" onSubmit="return registCheck();">
                    <div class="form-group">
                        <i class="icon-chevron-down"></i>&nbsp;&nbsp;<span style="color: #d22b2b;">账号信息</span>
                        <hr/>
                    </div>
                    <div class="form-group">
                        <i class="icon-user icon-large"></i>&nbsp;&nbsp;<label for="username">用 户 名</label>
                        <input type="text" class="form-control" id="username" name="username" value="${param.username}" placeholder="请输入用户名" autocomplete="off" spellcheck="false">
                    </div>
                    <div class="form-group">
                        <i class="icon-key icon-large"></i>&nbsp;&nbsp;<label for="password">密 码</label>
                        <input type="password" class="form-control" id="password" name="password" value="" placeholder="请输入密码" autocomplete="off" spellcheck="false">
                    </div>
                    <div class="form-group">
                        <i class="icon-key icon-large"></i>&nbsp;&nbsp;<label for="repeatpassword">确 认 密 码</label>
                        <input type="password" class="form-control" id="repeatpassword" name="repeatpassword" value="" placeholder="请再次输入密码" autocomplete="off" spellcheck="false">
                    </div>
                    <br/>
                    <div class="form-group">
                        <i class="icon-chevron-down"></i>&nbsp;&nbsp;<span style="color: #25a517;">用户基本信息</span>
                        <hr/>
                    </div>
                    <div class="form-group">
                        <label for="name">姓 名</label>
                        <input type="text" class="form-control" id="name" name="name" value="${param.name}" placeholder="请输入姓名" autocomplete="off" spellcheck="false">
                    </div>
                    <div class="form-group">
                        <label for="phone">手 机</label>
                        <input type="text" class="form-control" id="phone" name="phone" value="${param.phone}" placeholder="请输入手机" autocomplete="off" spellcheck="false">
                    </div>
                    <div class="form-group">
                        <label for="email">邮 箱</label>
                        <input type="text" class="form-control" id="email" name="email" value="${param.email}" placeholder="请输入邮箱" autocomplete="off" spellcheck="false">
                    </div>
                    <input type="submit" class="btn btn-block" value="注 册" />
                    <span id="regist_info" style="color: red;">${param.info}</span>
                </form>
            </div>
        </div>
    </div>
</body>
</html>