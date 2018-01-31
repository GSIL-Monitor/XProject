<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>Home Page</title>

<script type="text/javascript" src="./js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="./js/jquery-ui-1.10.4.min.js"></script>
<link href="./css/jquery-ui-1.10.4.min.css" rel="stylesheet"
	media="screen">
	
<meta name="_csrf" content="${_csrf.token}" />
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}" />

<script>
	$(function() {
		$("#tabs").tabs();
	});
</script>
</head>
<body>
	<div>
		<div style="text-align: right">
			<!-- <a href="/xherald/login?logout" class="btn btn-success">Log Out</a> -->
			<a href="/login?logout" class="btn btn-success">Log Out</a>
		</div>
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1">创建Topology</a></li>
				<li><a href="#tabs-2">查询Topology</a></li>
			</ul>
			<div id="tabs-1">
				<p><span style="color:red">* 必填项</span></p>
				<jsp:include page="createTopology.jsp"></jsp:include>
			</div>
			<div id="tabs-2">
				<p><span style="color:red">* 必填项</span></p>
				<jsp:include page="queryTopology.jsp"></jsp:include>
			</div>
		</div>
	</div>
</body>
</html>