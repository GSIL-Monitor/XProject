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
                    <h2>公司列表</h2>
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                            <tr>
                                <th>公司ID</th>
                                <th>公司Code</th>
                                <th>公司名称</th>
                                <th>联系电话</th>
                                <th>联系地址</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="company" items="${companys}">
                                <tr>
                                    <td>${company.dc}</td>
                                    <td>${company.o}</td>
                                    <td>${company.description}</td>
                                    <td>${company.telephoneNumber}</td>
                                    <td>${company.street}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
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