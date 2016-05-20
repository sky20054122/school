<#assign base=rc.contextPath>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>file manage</title>
		<script src="/jquery/jquery.min.js" type="text/javascript"></script>
		<script>
			$(document).ready(function() {
				$("#header").load("/header.html",function(){
					$("#fileUploadLi").addClass("active");
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
			
			
			<table style=" width: 100%;">						
				<tr>
					<td colspan="6" style="text-align: right;">
						<form id="uploadForm" method="post" action="${base}/file/fileUpload" enctype="multipart/form-data">
							<input type="file" accept="audio/mp3" class="file" name="file" id="file"  style="display: inline;"/> 
							<input type="submit" class="btn btn-info" id="uploadBtn" value="上传文件" />
						</form>
						<div class="progress">
					        <div class="bar"> </div >
					        <div class="percent">0%</div >
					    </div>
					    
					    <div id="status"> </div>
					</td> 
				</tr>
			</table>
			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">文件列表</h3>
				</div>
				<!--<div class="panel-body">-->
					<table class="table">						
						<tr>
							<td>文件名称</td>
							<td>持续时长</td>
							<td>上传时间</td>
							<td>备注信息</td>
							<td>操作</td>
						</tr>
						<tbody id="fileListBody">
		
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
	<script type="text/javascript" src="${base}/moment/moment.min.js"></script>
	<script type="text/javascript" src="${base}/jquery/jquery.form.js"></script>
	<script type="text/javascript" src="${base}/js/fileUpload.js"></script>
</html>