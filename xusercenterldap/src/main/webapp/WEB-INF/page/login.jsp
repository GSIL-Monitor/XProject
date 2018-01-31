<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
    <title>用户中心登录</title>
    <!-- css -->
    <link type="text/css" rel="stylesheet" href="static/css/lib/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="static/css/lib/font-awesome.min.css">
    <link type="text/css" rel="stylesheet" href="static/css/style/login.css">
    <!-- js -->
    <script type="text/javascript" src="static/js/lib/jquery-2.2.4.min.js"></script>
    <script type="text/javascript" src="static/js/lib/bootstrap.min.js"></script>
    <script type="text/javascript" src="static/js/script/util.js"></script>
    <script type="text/javascript" src="static/js/script/login.js"></script>
  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div style="padding-right: 15px;">
        <ul class="nav navbar-nav navbar-right">
          <li><a href="toregist"><i class="icon-plus"></i>&nbsp;&nbsp;注册</a></li>
        </ul>
      </div>
    </nav>
    <div class="container">
      <div class="row">
        <div class="col-md-8 col-md-offset-2 text-center">
          <div class="top">
            <h2>用户中心登录</h2>
            <div class="desc">
              <p style="color: #d22b2b;">SSO 认证</p>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6 col-md-offset-3 form-body">
      	    <div class="form-group">
      	      <i class="icon-user icon-large"></i>&nbsp;&nbsp;<label for="uid">用 户 名</label>
      	      <input id="uid" type="text" class="form-control" name="uid" value="${param.uid}" placeholder="请输入用户名" autocomplete="off" spellcheck="false">
      	    </div>
      	    <div class="form-group">
      	      <i class="icon-key icon-large"></i>&nbsp;&nbsp;<label for="userPassword">密 码</label>
      	      <input id="userPassword" type="password" class="form-control" name="userPassword" value="" placeholder="请输入密码">
      	    </div>
      	    <input id="lt" type="hidden" name="lt" />
      	    <input type="button" class="btn btn-block" value="登 录" onclick="login();" />
            <span id="login_info" style="color: red;">${param.info}</span>
            <div class="form-group">
                <p style="margin-top: 30px;"><i class="icon-hand-right icon-large"></i>&nbsp;&nbsp;<a href="toforgetpassword">忘记密码？</a></p>
            </div>
        </div>
      </div>
    </div>
  </body>
</html>