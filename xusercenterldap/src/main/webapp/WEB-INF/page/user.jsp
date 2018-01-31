<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
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
                        <c:if test="${user.uid == 'admin'}">
                            <li><a tabindex="-1" href="tocompany"><i class="icon-reorder"></i>&nbsp;&nbsp;公司管理</a></li>
                            <li class="divider"></li>
                        </c:if>
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
                            <i class="icon-info-sign"></i>&nbsp;&nbsp;<label>姓 名: </label>&nbsp;&nbsp;<span id="display_cn">${user.cn}</span>
                        </div>
                        <div class="form-group">
                            <i class="icon-phone-sign"></i>&nbsp;&nbsp;<label>手 机: </label>&nbsp;&nbsp;<span id="display_mobile">${user.mobile}</span>
                        </div>
                        <div class="form-group">
                            <i class="icon-envelope"></i>&nbsp;&nbsp;<label>邮 箱: </label>&nbsp;&nbsp;<span  id="display_mail">${user.mail}</span>
                        </div>
                        <button id="user_info_update_btn" type="button" class="btn btn-default"><i class="icon-pencil"></i> 修改</button>
                    </div>
                    <div id="user_info_update" style="display: none;">
                        <h2>用户信息修改</h2>
                        <div class="form-group">
                            <hr/>
                        </div>
                        <input id="uid" type="hidden" name="uid" value="${user.uid}" />
                        <input id="o" type="hidden" name="o" value="${user.o}" />
                        <div class="form-group">
                            <i class="icon-info-sign"></i>&nbsp;&nbsp;<label>姓 名: </label>&nbsp;&nbsp;<input id="cn" type="text" name="cn" value="${user.cn}" autocomplete="off" spellcheck="false" />
                        </div>
                        <div class="form-group">
                            <i class="icon-phone-sign"></i>&nbsp;&nbsp;<label>手 机: </label>&nbsp;&nbsp;<input id="mobile" type="text" name="mobile" value="${user.mobile}" autocomplete="off" spellcheck="false" />
                        </div>
                        <div class="form-group">
                            <i class="icon-envelope"></i>&nbsp;&nbsp;<label>邮 箱: </label>&nbsp;&nbsp;<input id="mail" type="text" name="mail" value="${user.mail}" autocomplete="off" spellcheck="false" />
                        </div>
                        <button id="user_info_save_btn" type="button" class="btn btn-success"><i class="icon-ok"></i> 保存</button>&nbsp;&nbsp;<span id="update_user_info" style="color: red;"></span>
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