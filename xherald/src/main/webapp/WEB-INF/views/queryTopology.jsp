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

		$("#queryIndexSubmit").on('click', function(e) {
			//阻止默认浏览器动作(W3C)
			e.preventDefault();

			//组装定义的字段信息
			var fields = setFields();

			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");

			//提交ajax请求
			$.ajax({
				type : "POST",
				contentType : "application/x-www-form-urlencoded",
				// url : "/xherald/mqesTopology/query",
				url : "/mqesTopology/query",
				data : {
					"createUser" : "admin",
					"startTime" : "2016-09-01",
					"endTime" : "2018-12-31"
				},
				dataType : 'json',
				beforeSend:function(xhr){
		             xhr.setRequestHeader(header, token);
		        },
				success : function(result) {
					 //1,获取上面id为cloneTr的tr元素  
                     var tr = $("#cloneTr");
					 var data = $.parseJSON(result)
	                 $.each(data, function(index,item){                              
	                       //克隆tr，每次遍历都可以产生新的tr                              
	                       var clonedTr = tr.clone();  
	                       var _index = index;

	                       var n=item.split("\t");
	                       clonedTr.children("td").each(function(inner_index){  
                               //根据索引为每一个td赋值  
                               switch(inner_index){  
                                     case(0):   
                                        $(this).html(_index + 1);  
                                        break;  
                                     case(1):  
                                        $(this).html(n[0]);
                                        break;  
                                    case(2):  
                                        $(this).html(n[1]);  
                                        break;  
                                    case(3):  
                                        $(this).html(n[2]);  
                                        break;  
                                    case(4):  
                                        $(this).html(n[3]);  
                                        break; 
                                    case(5):  
                                        $(this).html(n[4]);  
                                        break;
                                    case(6):  
                                        $(this).html(n[5]);  
                                        break; 
                               }//end switch                          
                     		});//end children.each
	                    
	                       //把克隆好的tr追加原来的tr后面  
	                       clonedTr.insertAfter(tr);  
	                  });//end $each  
                  $("#cloneTr").hide();//隐藏id=clone的tr，因为该tr中的td没有数据，不隐藏起来会在生成的table第一行显示一个空行  
                  $("#generatedTable").show();
				}
			});
		});
	});

	//去掉字符串前后空格
	function trim(str) {
		return str.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
	}
	
</script>

</head>

<body>
	<div class="container-fluid" id="div">
		<div class="row-fluid">
			<div id="workspace">
				<div class="span8 center-table">
					<form class="form-horizontal" id="queryIndexForm">
						<fieldset>
							<div id="legend">
								<legend>Query MQTopology</legend>
							</div>
						</fieldset>
						<div class="control-group">
							<div class="controls">
								<button type="submit" id="queryIndexSubmit"
								class="btn btn-success"
								onclick="return confirm('Really confirm to submit?')">查询</button>
							</div>
						</div>
					</form>
					<div>
						<table id="generatedTable"  border="2" style="display: none;">  
				            <thead>  
				                <tr>  
				                    <th style='width:3%;'>序号</th>
				                    <th style='width:35%;'>拓扑</th>  
				                    <th style='width:8%;'>创建者</th>
				                    <th style='width:22%;'>MQ信息</th>
				                    <th style='width:16%;'>创建时间</th>                               
				                    <th style='width:4%;'>状态</th>
				                    <th style='width:12%;'>说明</th>                     
				                </tr>  
				            </thead>  
				            <tbody>
				                <tr id="cloneTr">  
				                   <td></td> 
				                   <td></td>  
				                   <td></td>  
				                   <td></td>  
				                   <td></td>
				                   <td></td>
				                   <td></td>            
				                 </tr>  
				             </tbody>  
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