<#assign base=rc.contextPath>
<!DOCTYPE html>
<html lang="en">
	<head>
		<!-- Required meta tags always come first -->
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="x-ua-compatible" content="ie=edge">

		<!-- Bootstrap CSS -->
		<link rel="stylesheet" href="${base}/bootstrap/css/bootstrap.min.css" type="text/css">
	</head>
	<body style="padding-top: 70px;">
		<input type="hidden" id="baseUrl" value="${base}" />

		<div class="navbar-wrapper">
			<div class="container">

				<nav class="navbar navbar-default navbar-fixed-top">
					<div class="container">
						<div class="navbar-header">
							<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
								<span class="sr-only">Toggle navigation</span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
							</button>
							<a class="navbar-brand" href="#">School Server</a>
						</div>
						<div id="navbar" class="navbar-collapse collapse">
							<ul class="nav navbar-nav">
								<li class="active">
									<a href="#">Home</a>
								</li>
								<li>
									<a href="#about">About</a>
								</li>
								<li>
									<a href="#contact">Contact</a>
								</li>
								<li class="dropdown">
									<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
									<ul class="dropdown-menu">
										<li>
											<a href="#">Action</a>
										</li>
										<li>
											<a href="#">Another action</a>
										</li>
										<li>
											<a href="#">Something else here</a>
										</li>
										<li role="separator" class="divider"> </li>
										<li class="dropdown-header">
											Nav header
										</li>
										<li>
											<a href="#">Separated link</a>
										</li>
										<li>
											<a href="#">One more separated link</a>
										</li>
									</ul>
								</li>
							</ul>
						</div>
					</div>
				</nav>

			</div><!--  /.container -->
		</div><!--	/.	navbar-wrapper-->

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
			</div>
	
		
	
			<!-- FOOTER -->
			<footer>
				<p class="pull-right">
					<a href="#">返回顶部</a>
				</p>
				<p>
					&copy;  <a href="www.brxy-edu.com">四川博瑞星云信息技术有限公司</a> &middot; <a href="www.brxy-cloud.com">博瑞星云云平台</a> 2014-2016 版权所有&nbsp;&nbsp;  ${time?date}
				</p>
			</footer>

		</div><!-- /.container -->
	</body>
	
	<!-- jQuery first, then Bootstrap JS. -->
	<script type="text/javascript" src="${base}/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="${base}/jquery/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${base}/brxyTool.js"></script>
	<script type="text/javascript" src="${base}/js/addDevice.js"></script>
</html>