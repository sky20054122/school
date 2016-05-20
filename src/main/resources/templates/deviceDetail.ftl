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
		<link rel="stylesheet" href="${rc.contextPath}/plugins/bootstrap-switch/bootstrap-switch.min.css">
		<link rel="stylesheet" href="${rc.contextPath}/plugins/bootstrap-slider/slider.css">
		
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
			/*#GC .slider-selection {
			    background: #428041;
			}
			*/
			#green .slider-handle {
			    background: green;
			}
			
			
			.interval{
				margin-right: 5px;
			}
			
			.online{
				color: black;
			}
			
			.offline{
				color:#b4CDCD;
			}
			
			.tdRight{
				text-align: right;
			}
			
			.tdLeft{
				text-align: left;
			}
			
			.center{
				text-align: center;
			}
			.remInput{
				width:10rem;
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
					<h1> 设备详情 <small>设备详情和控制</small></h1>
					<ol class="breadcrumb">
						<li>
							<a href="${rc.contextPath}"><i class="fa fa-dashboard"> </i> 首页</a>
						</li>
						<li>
							<a href="${rc.contextPath}/device/queryMonitorDevice"><i class="fa fa-dashboard"> </i> 设备监控</a>
						</li>
						<li class="active">
							设备详情
						</li>
					</ol>
				</section>

				<!-- Main content -->
				<section class="content">
					<div class="row">
						<div class="col-xs-12">						

							<div class="box">
								<div class="box-header">
									<h3 class="box-title">设备详情</h3>
								</div>
								<!-- /.box-header -->
								<div class="box-body">
									<table id="deviceTable" class="table table-bordered table-hover">
										<tr>
											<td colspan="4" class="tdRight">
												<span class='interval' id="detailRFID">RFID</span>
												<span class='interval' id="detailScreen">Screen</span>
												<span class='interval' id="detailSensor">
													<span id="temperature">25℃</span>&nbsp;&nbsp;
													<span id="humidness">80%</span>&nbsp;&nbsp;
													<span id="illumination">1500L</span>		
												</span>
											</td>
										</tr>
										<tr><td colspan="4" class="tdLeft"><span class="ui-icon ui-icon-power"> </span><span id="detailDeviceStatus">&nbsp;</span></td></tr>
										<tr>
											<td>设备编码</td><td><span id="detailDeviceID">${deviceID}</span></td>
											<td>设备名称</td><td><span id="detailDeviceName">&nbsp;</span></td>
										</tr>
										<tr>
											<td>设备版本</td><td><span id="detailDeviceVersion">&nbsp;</span></td>
											<td>激活日期</td><td><span id="detailRecordDate">&nbsp;</span></td>
										</tr>
										<tr>
											<td colspan="2" style="width:50%;">
												<div style="margin: 10px 0px;">
													<div id="mediaA">监控摄像头A</div>
												</div>
											</td>
											<td colspan="2" style="width:50%;">
												<div style="margin: 10px 0px;">
													<div id="mediaB">监控摄像头B</div>
												</div>
											</td>
										</tr>
										<tr>
											<td>设备音量&nbsp;&nbsp;  <span id="volumeValue" style="color:#f6931f; font-weight:bold;"> </span></td>
											<td colspan="3">
												<div id="detailVolume"> </div>
											</td>
										</tr>
										<tr>
											<td>内置PC</td>
											<td class="tdRight" colspan="3"><div class="switch"><input type="checkbox" id="detailPC"/></div></td>
										</tr>	
										<tr>
											<td>显示通道</td>
											<td class="tdRight" colspan="3"><div class="switch"><input type="checkbox" id="detailChannel"/></div></td>
										</tr>	
										
										<tr>
											<td>展台</td>
											<td class="tdRight" colspan="3"><div class="switch"><input type="checkbox" id="detailBooth"/></div></td>
										</tr>	
										
										<tr>
											<td>外显</td>
											<td class="tdRight" colspan="3"><div class="switch"><input type="checkbox" id="detailDisplayer"/></div></td>
										</tr>
									</table>
									<table class="table">
										<tbody id="detailSwitchBody">
											<tr>
												<td style="width:75%;">开关1</td>
												<td class="tdRight">
													<div id="switch11" class="switch"><input type="checkbox" name="switch11"/></div>
												</td>
												<td class="tdRight">
													<div id="switch12" class="switch"><input type="checkbox" name="switch12"/></div>
												</td>
											</tr>
											<tr>
												<td style="width:75%;">开关2</td>
												<td class="tdRight">
													<div id="switch21" class="switch"><input type="checkbox" name="switch21"/></div>
												</td>
												<td class="tdRight">
													<div id="switch22" class="switch"><input type="checkbox" name="switch22"/></div>
												</td>
											</tr>
										
										</tbody>
										<tr>						
											<td colspan="3" class="tdRight">
												<input type="button" class="btn btn-sm btn-info" value ="返回" id="closeDeviceDetailBtn">
											</td>
										</tr>
									</table>
								</div>
								<!-- /.box-body -->
							</div>
							<!-- /.box -->
						</div>
						<!-- /.col -->
						
						
						
					</div><!-- /.row -->
					
					
					
					
				</section>
				<!-- /.content -->
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
		
		<script src="${rc.contextPath}/plugins/moment/moment.min.js"></script>
		
		<script src="${rc.contextPath}/plugins/jwplayer/jwplayer.js"></script>
		<!--script src="${rc.contextPath}/plugins/jQueryUI/jquery-ui.min.js"></script-->
		<script src="${rc.contextPath}/plugins/bootstrap-switch/bootstrap-switch.min.js"></script>
		<script src="${rc.contextPath}/plugins/bootstrap-slider/bootstrap-slider.js"></script>

		<!-- brxy school server public js -->
		<script src="${rc.contextPath}/js/brxy.js"></script>
		<script src="${rc.contextPath}/js/deviceCommonConstant.js"></script>
		<!-- page script -->
		<script src="${rc.contextPath}/js/deviceDetail.js"></script>
				
	</body>
</html>
