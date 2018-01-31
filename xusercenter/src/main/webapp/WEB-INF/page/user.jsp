<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户中心</title>
    <!-- css -->
    <link type="text/css" rel="stylesheet" href="static/css/lib/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="static/css/lib/font-awesome.min.css">
    <link type="text/css" rel="stylesheet" href="static/css/style/user.css">
    <!-- js -->
    <script type="text/javascript" src="static/js/lib/jquery-2.2.4.min.js"></script>
    <script type="text/javascript" src="static/js/lib/bootstrap.min.js"></script>
    <script type="text/javascript" src="static/js/script/common.js"></script>
    <script type="text/javascript" src="static/js/script/user.js"></script>
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
            <div class="col-md-12">
                <div class="main-right">
                    <div id="user_info_display">
                        <h2>用户信息</h2>
                        <div class="form-group">
                            <hr/>
                        </div>
                        <div class="form-group">
                            <i class="icon-info-sign"></i>&nbsp;&nbsp;<label>姓 名: </label>&nbsp;&nbsp;<span>${user.name}</span>
                        </div>
                        <div class="form-group">
                            <i class="icon-phone-sign"></i>&nbsp;&nbsp;<label>手 机: </label>&nbsp;&nbsp;<span>${user.phone}</span>
                        </div>
                        <div class="form-group">
                            <i class="icon-envelope"></i>&nbsp;&nbsp;<label>邮 箱: </label>&nbsp;&nbsp;<span>${user.email}</span>
                        </div>
                        <button id="user_info_update_btn" type="button" class="btn btn-default"><i class="icon-pencil"></i> 修改</button>
                    </div>
                    <div id="user_info_update" style="display: none;">
                        <h2>用户信息修改</h2>
                        <div class="form-group">
                            <hr/>
                        </div>
                        <input type="hidden" id="userId" name="userId" value="${user.id}" />
                        <div class="form-group">
                            <i class="icon-info-sign"></i>&nbsp;&nbsp;<label>姓 名: </label>&nbsp;&nbsp;<input type="text" id="name" name="name" value="${user.name}" autocomplete="off" spellcheck="false" />
                        </div>
                        <div class="form-group">
                            <i class="icon-phone-sign"></i>&nbsp;&nbsp;<label>手 机: </label>&nbsp;&nbsp;<input type="text" id="phone" name="phone" value="${user.phone}" autocomplete="off" spellcheck="false" />
                        </div>
                        <div class="form-group">
                            <i class="icon-envelope"></i>&nbsp;&nbsp;<label>邮 箱: </label>&nbsp;&nbsp;<input type="text" id="email" name="name" value="${user.email}" autocomplete="off" spellcheck="false" />
                        </div>
                        <button id="user_info_save_btn" type="button" class="btn btn-success"><i class="icon-ok"></i> 保存</button>
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