<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户中心-密码修改</title>
    <!-- css -->
    <link type="text/css" rel="stylesheet" href="static/css/lib/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="static/css/lib/font-awesome.min.css">
    <link type="text/css" rel="stylesheet" href="static/css/style/regist.css">
    <!-- js -->
    <script type="text/javascript" src="static/js/lib/jquery-2.2.4.min.js"></script>
    <script type="text/javascript" src="static/js/lib/bootstrap.min.js"></script>
    <script type="text/javascript" src="static/js/script/common.js"></script>
    <script type="text/javascript" src="static/js/script/main.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-header">
        <a class="navbar-brand" href="tomain"><i class="icon-home"></i>&nbsp;&nbsp;用户中心</a>
    </div>
    <div style="padding-right: 15px;">
        <ul class="nav navbar-nav navbar-right">
            <li>
                <a href="touser" class=" dropdown-toggle" data-toggle="dropdown"><i class="icon-user"></i>&nbsp;&nbsp;<shiro:principal/></a>
                <ul class="dropdown-menu pull-right" role="menu" aria-labelledby="dropdownMenu1">
                    <li><a tabindex="-1" href="touser"><i class="icon-pushpin"></i>&nbsp;&nbsp;用户信息</a></li>
                    <li><a tabindex="-1" href="tosecurity"><i class="icon-plus-sign-alt"></i>&nbsp;&nbsp;账号安全</a></li>
                    <li><a tabindex="-1" href="topassword"><i class="icon-pencil"></i>&nbsp;&nbsp;修改密码</a></li>
                    <li class="divider"></li>
                    <li><a tabindex="-1" href="javascript:logout();"><i class="icon-off"></i>&nbsp;&nbsp;退出</a></li>
                </ul>
            </li>
        </ul>
    </div>
</nav>
<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2 text-center">
            <div class="top">
                <h2>密码修改</h2>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6 col-md-offset-3 form-body">
            <form  role="form" action="updatePassword" method="post" onSubmit="return updatePasswordCheck();">
                <div class="form-group">
                    <i class="icon-user"></i>&nbsp;&nbsp;<label>用 户 名</label>
                    <input type="text" class="form-control" value="<shiro:principal/>" disabled>
                    <input type="hidden" class="form-control" id="username" name="username" value="<shiro:principal/>">
                </div>
                <div class="form-group">
                    <i class="icon-key"></i>&nbsp;&nbsp;<label for="opassword">原 密 码</label>
                    <input type="password" class="form-control" id="opassword" name="opassword" value="" placeholder="请输入原密码" autocomplete="off" spellcheck="false">
                </div>
                <div class="form-group">
                    <i class="icon-key"></i>&nbsp;&nbsp;<label for="npassword">新 密 码</label>
                    <input type="password" class="form-control" id="npassword" name="npassword" value="" placeholder="请输入新密码" autocomplete="off" spellcheck="false">
                </div>
                <div class="form-group">
                    <i class="icon-key"></i>&nbsp;&nbsp;<label for="repeatnpassword">确 认 新 密 码</label>
                    <input type="password" class="form-control" id="repeatnpassword" name="repeatnpassword" value="" placeholder="请再次输入新密码" autocomplete="off" spellcheck="false">
                </div>
                <input type="submit" class="btn btn-block" value="提 交" />
                <span id="password_info" style="color: red;">${param.info}</span>
            </form>
        </div>
    </div>
</div>
<!-- 弹出层 start -->
<div class="modal fade" id="usercenterModal">
    <div class="modal-dialog">
        <div class="modal-content"></div>
    </div>
</div>
<!-- 弹出层 end -->
</body>
</html>