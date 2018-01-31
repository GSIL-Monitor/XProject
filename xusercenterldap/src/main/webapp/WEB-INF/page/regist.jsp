<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
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
                <div class="form-group">
                    <i class="icon-chevron-down"></i>&nbsp;&nbsp;<span style="color: #d22b2b;">账号信息</span>
                    <hr/>
                </div>
                <div class="form-group">
                    <i class="icon-user icon-large"></i>&nbsp;&nbsp;<label for="uid">用 户 名</label>
                    <input id="uid" type="text" class="form-control" name="uid" placeholder="请输入用户名" autocomplete="off" spellcheck="false">
                </div>
                <div class="form-group">
                    <i class="icon-key icon-large"></i>&nbsp;&nbsp;<label for="userPassword">密 码</label>
                    <input id="userPassword" type="password" class="form-control" name="userPassword" value="" placeholder="请输入密码" autocomplete="off" spellcheck="false">
                </div>
                <div class="form-group">
                    <i class="icon-key icon-large"></i>&nbsp;&nbsp;<label for="repeatUserPassword">确 认 密 码</label>
                    <input id="repeatUserPassword" type="password" class="form-control" name="repeatUserPassword" value="" placeholder="请再次输入密码" autocomplete="off" spellcheck="false">
                </div>
                <br/>
                <div class="form-group">
                    <i class="icon-chevron-down"></i>&nbsp;&nbsp;<span style="color: #d22b2b;">用户基本信息</span>
                    <hr/>
                </div>
                <div class="form-group">
                    <label for="o">公 司</label>
                    <select id="o" name="o" class="form-control" style="margin-top:3px; border-radius:0; background:#f8f8f8;">
                        <option value="" selected="selected"></option>
                        <c:forEach items="${companys}" var="company">
                            <option value="${company.o}">${company.description}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="cn">姓 名</label>
                    <input id="cn" type="text" class="form-control" name="cn" placeholder="请输入姓名" autocomplete="off" spellcheck="false">
                </div>
                <div class="form-group">
                    <label for="mobile">手 机</label>
                    <input id="mobile" type="text" class="form-control" name="mobile" placeholder="请输入手机" autocomplete="off" spellcheck="false">
                </div>
                <div class="form-group">
                    <label for="mail">邮 箱</label>
                    <input id="mail" type="text" class="form-control" name="mail" placeholder="请输入邮箱" autocomplete="off" spellcheck="false">
                </div>
                <input type="button" class="btn btn-block" value="注 册" onclick="regist();" />
                <span id="regist_info" style="color: red;"></span>
            </div>
        </div>
    </div>
</body>
</html>