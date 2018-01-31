<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户中心-忘记密码</title>
    <!-- css -->
    <link type="text/css" rel="stylesheet" href="static/css/lib/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="static/css/lib/font-awesome.min.css">
    <link type="text/css" rel="stylesheet" href="static/css/style/regist.css">
    <!-- js -->
    <script type="text/javascript" src="static/js/lib/jquery-2.2.4.min.js"></script>
    <script type="text/javascript" src="static/js/lib/bootstrap.min.js"></script>
    <script type="text/javascript" src="static/js/script/resetpassword.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div style="padding-right: 15px;">
        <ul class="nav navbar-nav navbar-right">
            <li><a href="tologin"><i class="icon-arrow-left"></i>&nbsp;&nbsp;登录</a></li>
        </ul>
    </div>
</nav>
<div class="container">
    <div id="first_step">
        <div class="row">
            <div class="col-md-8 col-md-offset-2 text-center">
            <div class="top">
                <h2>第一步 ( 1 / 3 ) : 输入用户名</h2>
            </div>
        </div>
        </div><div class="row">
        <div class="col-md-6 col-md-offset-3 form-body">
            <div class="form-group">
            <label>用 户 名</label>
            <input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名" autocomplete="off" spellcheck="false">
            </div>
            <input id="first_step_btn" type="button" class="btn btn-block" value="下 一 步" />
            <span id="first_step_info" style="color: red;"></span>
        </div>
        </div>
    </div>
    <div id="second_step" style="display: none;">
        <div class="row">
            <div class="col-md-8 col-md-offset-2 text-center">
                <div class="top">
                    <h2>第二步 ( 2 / 3 ) : 回答密保问题</h2>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 col-md-offset-3 form-body">
                <input type="hidden" class="form-control" id="securityId" name="securityId" />
                <div class="form-group">
                    <label>问 题 1: </label><span id="question_one"></span>
                    <input type="text" class="form-control" id="answerone" name="answerone" placeholder="请输入问题 1 答案" autocomplete="off" spellcheck="false">
                </div>
                <div class="form-group">
                    <label>问 题 2: </label><span id="question_two"></span>
                    <input type="text" class="form-control" id="answertwo" name="answertwo" placeholder="请输入问题 2 答案" autocomplete="off" spellcheck="false">
                </div>
                <input id="second_step_btn" type="button" class="btn btn-block" value="下 一 步" />
                <span id="second_step_info" style="color: red;"></span>
            </div>
        </div>
    </div>
    <div id="third_step" style="display: none;">
        <div class="row">
            <div class="col-md-8 col-md-offset-2 text-center">
                <div class="top">
                    <h2>第三步 ( 3 / 3 ) : 密码重置</h2>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 col-md-offset-3 form-body">
                <input type="hidden" class="form-control" id="userId" name="userId" />
                <div class="form-group">
                    <label>新 密 码</label>
                    <input type="password" class="form-control" id="newpassword" name="newpassword" placeholder="请输入新密码" autocomplete="off" spellcheck="false">
                </div>
                <div class="form-group">
                    <label>确 认 新 密 码</label>
                    <input type="password" class="form-control" id="repeatnewpassword" name="repeatnewpassword" placeholder="请再次输入新密码" autocomplete="off" spellcheck="false">
                </div>
                <input id="password_reset_btn" type="button" class="btn btn-block" value="重 置" />
                <span id="third_step_info" style="color: red;"></span>
            </div>
        </div>
    </div>
    <div id="reset_password_success" style="display: none;">
        <div class="row">
            <div class="col-md-8 col-md-offset-2 text-center">
                <div class="top">
                    <h2>密码重置成功</h2>
                    <p id="resetpasswd_to_login"></p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>