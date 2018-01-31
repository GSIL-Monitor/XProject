<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>用户中心</title>
        <!-- css -->
        <link type="text/css" rel="stylesheet" href="static/css/lib/bootstrap.min.css">
        <link type="text/css" rel="stylesheet" href="static/css/lib/font-awesome.min.css">
        <link type="text/css" rel="stylesheet" href="static/css/style/main.css">
        <!-- js -->
        <script type="text/javascript" src="static/js/lib/jquery-2.2.4.min.js"></script>
        <script type="text/javascript" src="static/js/lib/bootstrap.min.js"></script>
        <script type="text/javascript" src="static/js/script/common.js"></script>
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
                        <h2 style="color: #5cad2a;">用户认证成功</h2>
                        <hr/>
                        <br/>
                        <h4>上一次登录时间: ${lastLoginTime}</h4>
                        <h4>登录次数: ${loginCount}</h4>
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