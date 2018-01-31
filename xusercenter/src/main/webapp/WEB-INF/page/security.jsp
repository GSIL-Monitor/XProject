<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户中心</title>
    <!-- css -->
    <link type="text/css" rel="stylesheet" href="static/css/lib/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="static/css/lib/font-awesome.min.css">
    <link type="text/css" rel="stylesheet" href="static/css/style/security.css">
    <!-- js -->
    <script type="text/javascript" src="static/js/lib/jquery-2.2.4.min.js"></script>
    <script type="text/javascript" src="static/js/lib/bootstrap.min.js"></script>
    <script type="text/javascript" src="static/js/script/common.js"></script>
    <script type="text/javascript" src="static/js/script/security.js"></script>
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
        <div class="col-md-2">
            <div class="main-left">
                <ul class="nav nav-list" id="security_menu">
                    <li class="active" name="acount_info_menu"><a href="javascript:;"><i class="icon-info-sign"></i>&nbsp;&nbsp;账号信息</a></li>
                    <li name="set_password_question_menu"><a href="javascript:;"><i class="icon-lock"></i>&nbsp;&nbsp;设置密保</a></li>
                </ul>
            </div>
        </div>
        <div class="col-md-10">
            <div class="main-right">
                <div id="security_account_info">
                    <div class="form-group">
                        <i class="icon-user"></i>&nbsp;&nbsp;<label>用 户 名: </label>&nbsp;&nbsp;<span>${user.username}</span>
                    </div>
                    <div class="form-group">
                        <i class="icon-key"></i>&nbsp;&nbsp;<label>密 码: </label>&nbsp;&nbsp;<span>******</span>
                    </div>
                    <hr/>
                    <div class="form-group">
                        <i class="icon-phone-sign"></i>&nbsp;&nbsp;<label>绑 定 手 机: </label>&nbsp;&nbsp;<span id="bind_phone"></span>
                    </div>
                    <div class="form-group">
                        <i class="icon-envelope"></i>&nbsp;&nbsp;<label>绑 定 邮 箱: </label>&nbsp;&nbsp;<span id="bind_email"></span>
                    </div>
                </div>
                <div id="security_set_password_question" style="display: none;">
                    <input type="hidden" class="form-control" id="userId" name="userId" value="${user.id}"/>
                    <i class="icon-question-sign"></i>&nbsp;&nbsp;<label>问 题 1: </label>
                    <div class="form-group">
                        <select class="form-control" id="questiononeId" name="questiononeId" style="border-radius: 0; outline: none;"></select>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" id="answerone" name="answerone" placeholder="请输入答案" autocomplete="off" spellcheck="false">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" id="questiononePrompt" name="questiononePrompt" placeholder="请输入答案提示" autocomplete="off" spellcheck="false">
                    </div>
                    <i class="icon-question-sign"></i>&nbsp;&nbsp;<label>问 题 2: </label>
                    <div class="form-group">
                        <select class="form-control" id="questiontwoId" name="questiontwoId" style="border-radius: 0; outline: none;"></select>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" id="answertwo" name="answertwo" placeholder="请输入答案" autocomplete="off" spellcheck="false">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" id="questiontwoPrompt" name="questiontwoPrompt" placeholder="请输入答案提示" autocomplete="off" spellcheck="false">
                    </div>
                    <input id="set_password_question_btn" type="button" value="设置" /><span id="set_password_question_info"></span>
                </div>
            </div>
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