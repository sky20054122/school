<#assign base=rc.contextPath>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>设备激活</title>
		<script src="/jquery/jquery.min.js" type="text/javascript"></script>
		<script>
			$(document).ready(function() {
				$("#header").load("/header.html",function(){
					$("#tmpDeviceLi").addClass("active");
				});
				$("#footer").load("/footer.html");
				var baseUrl = $("#baseUrl").val();
				
			});
		</script>
		<!-- Bootstrap CSS -->
		<link rel="stylesheet" href="${base}/bootstrap/css/bootstrap.min.css" type="text/css">
	</head>
	<body style="padding-top: 70px;">
		<input type="hidden" id="baseUrl" value="${base}" />
		
		<div id="header"> </div>
		<div class="container">
			
			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">添加设备</h3>
				</div>
				<!--<div class="panel-body">-->
					<table class="table">				
						<tr>
							<td>设备ID</td>
							<td>版本</td>		
							<td>名称</td>				
							<td>操作</td>
						</tr>
						<tbody id="deviceListBody">
							
						</tbody>
					</table>
				<!--</div>-->
			</div><!--	panel panel-info-->

			<div id="footer"> </div>
		</div><!-- /.container -->
	</body>
	<!-- jQuery first, then Bootstrap JS. -->
	<script type="text/javascript" src="${base}/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="${base}/jquery/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${base}/brxyTool.js"></script>
	<script type="text/javascript" src="${base}/js/addDevice.js"></script>
</html>