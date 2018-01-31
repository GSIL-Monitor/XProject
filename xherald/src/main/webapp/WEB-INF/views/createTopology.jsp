<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
<title>xherald</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="./css/all.min.css" rel="stylesheet" media="screen">

<meta name="_csrf" content="${_csrf.token}" />
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}" />

<script type="text/javascript">
	$(document).ready(function() {
		load();

		$("#addBtn").click(function() {
			if (trim($("#fieldName").val()) == '') {
				alert("FieldName is null!");
			} else {
				$("#table1").append(getNewRow());
				clearText();
			}
		});

		$("#table1").on('click', ".delBtn", function() {
			$(this).parent().parent().remove();
		});

		$("#createIndexSubmit").on('click', function(e) {
			//阻止默认浏览器动作(W3C)
			e.preventDefault();

			//组装定义的字段信息
			var fields = setFields();

			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			
			//请求参数
			var requestData = {
					"userName" : $("#userName").val(),
					"password" : $("#password").val(),
					"vhost" : $("#vhost").val(),
					"exchangeName" : $("#exchangeName").val(),
					"queueName" : $("#queueName").val(),
					"routingKey" : $("#routingKey").val(),
					"indexName" : $("#indexName").val(),
					"indexType" : $("#indexType").val(),
					"shards" : $("#shards").val(),
					"replicas" : $("#replicas").val(),
					"checkFields" : $("#checkFields").is(':checked'),
					"fields" : JSON.stringify(fields)
				}

			//提交ajax请求
			$.ajax({
				type : "POST",
				/* contentType : "application/x-www-form-urlencoded", */
				contentType : "application/json",
				// url : "/xherald/mqesTopology/create",
				url : "/mqesTopology/create",
				data : JSON.stringify(requestData),
				dataType : 'json',
				beforeSend:function(xhr){
		             xhr.setRequestHeader(header, token);
		        },
				success : function(result) {
					alert("response is  : " + JSON.stringify(result));
				}
			});
		});

		$("#nextButton").on('click', function(e) {
			esShow();
		});

		$("#previousButton").on('click', function(e) {
			mqShow();
		});
		
		//checkFields复选框点击事件
		$("#checkFields").change(function() {
			//alert($("#checkFields").is(':checked'));
			if(true == $("#checkFields").is(':checked')){
				fieldsShow();
			}else{
				fieldsHide();
			}
		});

	});

	function clearmq() {
		$("#exchangeName").val("");
		$("#queueName").val("");
		$("#routingKey").val("");
	}

	function clearText() {
		$("#fieldName").val("");
		$("#dataType").val("");
		$("#store").val("");
		$("#analyzer").val("");
		$("#fieldName").focus();
	}

	//去掉字符串前后空格
	function trim(str) {
		return str.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
	}

	function mqShow() {
		$("#mqdiv").show();
		$("#esdiv").hide();
	}

	function esShow() {
		$("#mqdiv").hide();
		$("#esdiv").show();
	}

	function load() {
		mqShow();
		//clearmq();
	}
	
	function fieldsShow() {
		$("#setFields").show();
	}
	
	function fieldsHide() {
		$("#setFields").hide();
	}

	function getNewRow() {
		var btn = $("<input class='delBtn' type='button' value='Delete' />");
		var newRow = $("<tr>").append($("<td>").append($("#fieldName").val()))
				.append($("<td>").append($("#dataType").val())).append(
						$("<td>").append($("#store").val())).append(
						$("<td>").append($("#analyzer").val())).append(
						$("<td>").append(btn));
		return newRow;
	}
	var listName = [ 'fieldName', 'dataType', 'store', 'analyzer' ];

	function setFields() {
		var list = [];
		$("#table1").find("tr").each(function() {
			var i = 0, obj = {}, isNull = true;
			$(this).find("td").each(function(td) {
				if (i < listName.length) {
					obj[listName[i++]] = $(this).text();
				}
				isNull = false;
			});
			isNull === false && list.push(obj);
		});
		console.log(list);
		return list;
	}
</script>

</head>

<body>
	<div class="container-fluid" id="mqdiv">
		<div class="row-fluid">
			<div id="workspace">
				<div class="span8 center-table">
					<form class="form-horizontal" id="createIndexForm">
						<fieldset>
							<div id="legend">
								<legend>Create MQTopology</legend>
							</div>
							<div class="control-group">
								<label class="control-label" for="userName">UserName</label>
								<div class="controls">
									 <input type="text" id="userName" name="userName"
										placeholder="" class="input-large" data-error-style="inline"
										required="required">
									 <span style="color:red">*</span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="password">Password</label>
								<div class="controls">
									<input type="text" id="password" name="password"
										placeholder="" class="input-large" data-error-style="inline"
										required="required">
									<span style="color:red">*</span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="vhost">Vhost</label>
								<div class="controls">
									<input type="text" id="vhost" name="vhost"
										placeholder="" class="input-large" data-error-style="inline"
										value="/">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="exchangeName">ExchangeName</label>
								<div class="controls">
									<input type="text" id="exchangeName" name="exchangeName"
										placeholder="" class="input-large" data-error-style="inline"
										required="required">
									<span style="color:red">*</span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="queueName">QueueName</label>
								<div class="controls">
									<input type="text" id="queueName" name="queueName"
										placeholder="" class="input-large" data-error-style="inline"
										required="required">
									<span style="color:red">*</span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="routingKey">RoutingKey</label>
								<div class="controls">
									<input type="text" id="routingKey" name="routingKey"
										placeholder="" class="input-large" data-error-style="inline"
										required="required">
									<span style="color:red">*</span>
								</div>
							</div>
						</fieldset>
						<div class="control-group">
							<div class="controls">
								<button type="button" id="nextButton" class="btn btn-success">Next</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="container-fluid" id="esdiv">
		<div class="row-fluid">
			<div id="workspace">
				<div class="span8 center-table">
					<form class="form-horizontal" id="createIndexForm">
						<fieldset>
							<div id="legend">
								<legend>Create an Index</legend>
							</div>
							<div class="control-group">
								<label class="control-label" for="indexName">IndexName</label>
								<div class="controls">
									<input type="text" id="indexName" name="indexName"
										placeholder="" class="input-xlarge" data-error-style="inline"
										required="required">
									<span style="color:red">*</span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="indexType">IndexType</label>
								<div class="controls">
									<input type="text" id="indexType" name="indexType"
										placeholder="" class="input-xlarge" data-error-style="inline"
										required="required">
									<span style="color:red">*</span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="shards">Shards</label>
								<div class="controls">
									<input type="text" id="shards" name="shards" placeholder=""
										class="input-mini" data-error-style="inline" value="3">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="replicas">Replicas</label>
								<div class="controls">
									<input type="text" id="replicas" name="replicas" placeholder=""
										class="input-mini" data-error-style="inline" value="1">
								</div>
							</div>
							<div class="control-group">
								<input type="checkbox" name="checkFields" id="checkFields" /> 
								<label class="control-label" for="fields">Fields</label>
								<div class="controls">
									<table id="table1" border="1">
										<tr>
											<th>fieldName</th>
											<th>dataType</th>
											<th>store</th>
											<th>analyzer</th>
										</tr>
									</table>
								</div>
							</div>
						</fieldset>
					</form>
					<div class="control-group">
						<div class="controls">
							<button type="submit" id="createIndexSubmit"
								class="btn btn-success"
								onclick="return confirm('Really confirm to submit?')">Submit</button>
							<button type="button" id="previousButton" class="btn btn-success">Previous</button>
						</div>
					</div>
					<div class="control-group" id="setFields"  style="DISPLAY: none">
						<table border="1">
							<tr>
								<td>FieldName<input type="text" id="fieldName" value="" /></td>
								<td>DataType<select name="dataType" id="dataType">
										<option value="string">string</option>
										<option value="integer">integer</option>
										<option value="long">long</option>
										<option value="double">double</option>
										<option value="date">date</option>
										<option value="boolean">boolean</option>
										<option value="geo_point">geo_point</option>
										<option value="geo-shape">geo-shape</option>
								</select>
								</td>
								<td>Store<select name="store" id="store">
										<option value="yes">yes</option>
										<option value="no">no</option>
								</select>
								</td>
								<td>Analyzer<select name="analyzer" id="analyzer">
										<option value="not_analyzed">not_analyzed</option>
										<option value="standard">standard</option>
										<option value="keyword">keyword</option>
										<option value="ik">ik</option>
										<option value="edge_ngram">edge_ngram</option>
								</select>
								</td>
								<td><input type="button" id="addBtn" value="Add"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Footer -->
	<footer class="footer">
		<div class="container">
			<p>
				<small>Current versions can always be found running at <a
					href="https://www.elastic.co/" target="_blank">Elastic </a>.
				</small>
			</p>
		</div>
	</footer>
</body>
</html>