<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>博瑞星云 | 校园服务器</title>
		<!-- Tell the browser to be responsive to screen width -->
		<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
		<!-- Bootstrap 3.3.6 -->
		<link rel="stylesheet" href="${rc.contextPath}/plugins/bootstrap/css/bootstrap.min.css">
		<!-- Font Awesome -->
		<link rel="stylesheet" href="${rc.contextPath}/plugins/Font-Awesome/css/font-awesome.min.css">
		<!-- Ionicons -->
		<link rel="stylesheet" href="${rc.contextPath}/plugins/ionicons/css/ionicons.min.css">
		<!-- DataTables -->
		<link rel="stylesheet" href="${rc.contextPath}/plugins/datatables/dataTables.bootstrap.css">
		<!--link rel="stylesheet" href="${rc.contextPath}/plugins/datatables/jquery.dataTables.css"影响和adminLTE的整合效果-->
		<!-- Theme style -->
		<link rel="stylesheet" href="${rc.contextPath}/plugins/adminlte/css/AdminLTE.css">
		<!-- AdminLTE Skins. Choose a skin from the css/skins
		folder instead of downloading all of them to reduce the load. -->
		<link rel="stylesheet" href="${rc.contextPath}/plugins/adminlte/css/skins/_all-skins.min.css">
		<link rel="stylesheet" href="${rc.contextPath}/css/style.css">
		
		<!-- jQuery-->
		<script src="${rc.contextPath}/plugins/jQuery/jquery.min.js"></script>
		<script>
			$(document).ready(function() {
				$("#header").load("${rc.contextPath}/layout/header.html", function() {
					$("#deviceLi").addClass("active");
				});
				$("#footer").load("${rc.contextPath}/layout/footer.html");
				//var baseUrl = $("#baseUrl").val();
				
			});
		</script>
		<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
		<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->
		<style type="text/css">
			.box-right-controll{
				text-align: right;padding-bottom: 0;
			}
		</style>
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<input type="hidden" value="${rc.contextPath}" id="baseUrl"/>
		<div class="wrapper">
			<div id="header">&nbsp;</div>
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper">
				<!-- Content Header (Page header) -->
				<section class="content-header">
					<h1> 设备管理 <small>已激活设备管理</small></h1>
					<ol class="breadcrumb">
						<li>
							<a href="${rc.contextPath}"><i class="fa fa-dashboard"> </i> 首页</a>
						</li>
						<li class="active">
							设备管理
						</li>
					</ol>
				</section>

				<!-- Main content -->
				<section class="content">
					<div class="row">
						<div class="col-xs-12">						

							<div class="box">
								<!--div class="box-header">
									<h3 class="box-title">未激活设备列表</h3>
								</div-->
								<div class="box-header box-right-controll">
									<input type="button" value="开机" class="btn btn-info" id="startDevice" >
									<input type="button" value="关机" class="btn btn-info" id="shutdownDevice" >
									<input type="button" value="广播" class="btn btn-info" id="openBoardcastBtn">
								</div>
								<!-- /.box-header -->
								<div class="box-body">
									<table id="deviceTable" class="table table-bordered table-hover">
										<thead>
											<tr>
												<th>设备名称</th>
												<th>设备ID</th>
												<th>硬件版本</th>
												<th>硬件状态</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>	</tbody>
										<tfoot>
											<tr>
												<th>设备名称</th>
												<th>设备ID</th>
												<th>硬件版本</th>
												<th>硬件状态</th>
												<th>操作</th>
											</tr>
										</tfoot>
									</table>
								</div>
								<!-- /.box-body -->
							</div>
							<!-- /.box -->
						</div>
						<!-- /.col -->
						
						
						<!-- 模态框（Modal） -->
						<div class="modal fade" id="addBroadcastBox" tabindex="-1" role="dialog"   aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false" data-keyboard="true">
						   <div class="modal-dialog">
						      <div class="modal-content">
						         <div class="modal-header">
						            <button type="button" class="close"       data-dismiss="modal" aria-hidden="true">&times;</button>
						            <h4 class="modal-title" id="myModalLabel">新增广播</h4>
						         </div>
						         <div class="modal-body">
						            	<table class="table">
											<tr>
												<td>选择节目</td>
												<td>
													<select id="programSelect" class="form-control">
														<option value="铃声.mp3">铃声.mp3</option>
														<option value="广播体操.mp3">广播体操.mp3</option>
														<option value="升旗仪式.mp3">升旗仪式.mp3</option>
														<option value="眼保健操.mp3">眼保健操.mp3</option>
														<option value="套马杆.mp3">套马杆.mp3</option>
													</select>
												</td>
											</tr>		
										</table>	
						         </div><!--	 ./modal-body	 -->
						         <div class="modal-footer">
						            <button type="button" class="btn btn-default"  data-dismiss="modal">关闭 </button>
						            <button type="button" class="btn btn-primary" id="boardcastBtn">广播</button>
						         </div>
						      </div><!-- /.modal-content -->
						</div><!-- /.modal -->
						
						
						
					</div><!-- /.row -->
					
					
					
					
				</section><!-- /.content -->
				
			</div>
			<!-- /.content-wrapper -->

			<footer class="main-footer" id="footer">

			</footer>

		</div>
		<!-- ./wrapper -->

		<!-- Bootstrap 3.3.6 -->
		<script src="${rc.contextPath}/plugins/bootstrap/js/bootstrap.min.js"></script>
		<!-- DataTables -->
		<script src="${rc.contextPath}/plugins/datatables/jquery.dataTables.min.js"></script>
		<script src="${rc.contextPath}/plugins/datatables/dataTables.bootstrap.min.js"></script>
		<!-- SlimScroll -->
		<script src="${rc.contextPath}/plugins/slimScroll/jquery.slimscroll.min.js"></script>
		<!-- FastClick -->
		<script src="${rc.contextPath}/plugins/fastclick/fastclick.js"></script>
		<!-- AdminLTE App -->
		<script src="${rc.contextPath}/plugins/adminlte/js/app.min.js"></script>
		
		<script src="${rc.contextPath}/plugins/jQueryUI/jquery-ui.min.js"></script>

		<!-- brxy school server public js -->
		<script src="${rc.contextPath}/js/brxy.js"></script>
		<!-- page script -->
		<script src="${rc.contextPath}/js/deviceCommonConstant.js"></script>
		<script src="${rc.contextPath}/js/deviceMonitor.js"></script>
		
		
	</body>
</html>
