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
		<link rel="stylesheet" href="${rc.contextPath}/plugins/jQueryUI/jquery-ui.min.css">
		<link rel="stylesheet" href="${rc.contextPath}/plugins/jquery-ui-timepicker/jquery-ui-timepicker-addon.min.css">
		<link rel="stylesheet" href="${rc.contextPath}/css/style.css">
		
		<!-- jQuery-->
		<script src="${rc.contextPath}/plugins/jQuery/jquery.min.js"></script>
		<script>
			$(document).ready(function() {
				$("#header").load("${rc.contextPath}/layout/header.html?baseUrl=${rc.contextPath}", function() {
					$("#tmpDeviceLi").addClass("active");
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
			table.scheduleTable{
				border: 1px solid #ddd;
				width:100%;
			}
			
			table.scheduleTable th{
				text-align:center;
				border: 1px solid #ddd;
			}
			
			table.scheduleTable td{
				text-align:center;
				border-left: 1px solid #ddd;
				border-right: 1px solid #ddd;
				border-top: none;
				border-bottom: none;
				padding: 0.2em;
				padding-bottom: 5rem;
			}
			
			table.scheduleList{
			    width: 100%;
			    margin:0;
			    padding: 0;
			    font-size: 100%; 
			    border-collapse: collapse;
			    border:none;
			}
			
			table.scheduleList td{
				text-align:left;
				border:none;		
				padding:0.2em;
				line-height: 1em;
			    vertical-align: middle;
			}
			
			table.scheduleList td.scheduleEditIcon{
				text-align:right;
			    vertical-align: top;
			}
			
			.scheduleTd{
				min-height:35rem;
				height:35rem;
				vertical-align: top;
			}
			
			.scheduleDiv{
				width:100%;
				/*height:30px;
				line-height:30px;*/
				background:#3a87ad;
				border:1px solid #3a87ad;
				margin:0.3em 0;
				overflow: hidden;
				text-align: left;
				cursor: pointer;
				border-radius: 5px;
				font-size: 12px;
			}
			
			.copyFromBefore{
				cursor: pointer;
				color: #0069d6;
				text-decoration: none;
				line-height: inherit;
				font-weight: inherit;
			}
			
			.ui-icon-newwin{
				float:right;
			}
			
			.scheduleRange1{
				background: #3a87ad;
				color: white;
			}
			
			.scheduleRange0{
				background: #e78f08;
				color: white;
			}
		</style>
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<input type="hidden" id="baseUrl" value="${rc.contextPath}" />
		<div class="wrapper">
			<div id="header">&nbsp;</div>
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper">
				<!-- Content Header (Page header) -->
				<section class="content-header">
					<h1> 计划任务 <small>计划详情</small></h1>
					<ol class="breadcrumb">
						<li>
							<a href="${rc.contextPath}"><i class="fa fa-dashboard"> </i> 首页</a>
						</li>
						<li class="active">
							计划详情
						</li>
					</ol>
				</section>

				<!-- Main content -->
				<section class="content">
					<div class="row">
						<div class="col-xs-12">						

							<div class="box">
								<div class="box-header">
									<h3 class="box-title" id="schemaName">&nbsp;</h3>
								</div><!-- /.box-header -->
								<div class="box-body">
									<input type="hidden" id="schemaID" value="${schemaID}">
									<div style="text-align:right;margin-bottom: 0.5em;">
										<input type="button" class="btn btn-primary" value="全校计划" id="scheduleRange1"/>
										<input type="button" class="btn btn-warning" value="局部计划" id="scheduleRange0"/>
									</div>
									<table class="scheduleTable">
										<tr id="weekTr">				
											<th><span data-week="1" name="MONDAY" id="monday">星期一</span></th>
											<th><span data-week="2" name="TUESDAY" class="copyFromBefore" title="双击复制前一日计划" id="tuesday">星期二</span></th>
											<th><span data-week="3" name="WEDNESDAY"  class="copyFromBefore" title="双击复制前一日计划" id="wednesday">星期三</span></th>
											<th><span data-week="4" name="THURSDAY" class="copyFromBefore" title="双击复制前一日计划" id="thursday">星期四</span></th>
											<th><span data-week="5" name="FRIDAY" class="copyFromBefore" title="双击复制前一日计划" id="friday">星期五</span></th>
											<th><span data-week="6" name="SATURDAY" class="copyFromBefore" title="双击复制前一日计划" id="saturday">星期六</span></th>
											<th><span data-week="7" name="SUNDAY" class="copyFromBefore" title="双击复制前一日计划" id="sunday">星期日</span></th>
										</tr>
										<tbody id="scheduleBody">
											<tr>					
												<td class="scheduleTd" name="MONDAY" data-week="1" id="mondayContent">&nbsp;</td>
												<td class="scheduleTd" name="TUESDAY" data-week="2" id="tuesdayContent">&nbsp;</td>
												<td class="scheduleTd" name="WEDNESDAY" data-week="3" id="wednesdayContent">&nbsp;</td>
												<td class="scheduleTd" name="THURSDAY" data-week="4" id="thursdayContent">&nbsp;</td>
												<td class="scheduleTd" name="FRIDAY" data-week="5" id="fridayContent">&nbsp;</td>
												<td class="scheduleTd" name="SATURDAY" data-week="6" id="saturdayContent">&nbsp;</td>
												<td class="scheduleTd" name="SUNDAY" data-week="7" id="sundayContent">&nbsp;</td>
											</tr>				
										</tbody>
									</table>
								
								</div>
								<!-- /.box-body -->
							</div>
							<!-- /.box -->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</section>
				<!-- /.content -->
			</div>
			<!-- /.content-wrapper -->

			<footer class="main-footer" id="footer">

			</footer>
			
			
			<!-- 模态框（Modal）  新增计划-->
			<div class="modal fade" id="addScheduleBox" tabindex="-1" role="dialog"   aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false" data-keyboard="true">
			   <div class="modal-dialog">
			      <div class="modal-content">
			         <div class="modal-header">
			            <button type="button" class="close"       data-dismiss="modal" aria-hidden="true">&times;</button>
			            <h4 class="modal-title" id="myModalLabel">新增计划</h4>
			         </div>
			         <div class="modal-body">
			         	 <form>
			         	 	<input type="hidden" name="addWeekday" value="" id="addWeekday"/>
			         	 	<div class="form-group">
					            <label for="programSelect" class="control-label">选择节目:</label>
					            <select id="programSelect" class="form-control">
									<option value="铃声.mp3">铃声.mp3</option>
									<option value="广播体操.mp3">广播体操.mp3</option>
									<option value="升旗仪式.mp3">升旗仪式.mp3</option>
									<option value="眼保健操.mp3">眼保健操.mp3</option>
									<option value="套马杆.mp3">套马杆.mp3</option>
								</select>
				          	</div>
				          	<div class="form-group">
					            <label for="addStartTime" class="control-label">开始时间:</label>
					            <input type="text" class="form-control" id="addStartTime" tabIndex="-1">
					        </div>
				          	<div class="form-group">
					            <label for="addEndTime" class="control-label">结束时间:</label>
					            <input type="text" class="form-control" id="addEndTime" readonly>
					        </div>
					        <div class="radio">
							  <label class="radio-inline">
					            <input type="radio" name="scheduleRange"  value="SCHEDULE_ALL_DEVICE" id="allSchool" checked/>
					            	全校设备 
							  </label>
							  <label class="radio-inline">
								<input type="radio" name="scheduleRange"  value="SCHEDULE_PART_DEVICE" id="selfChoiceDevice"/>自选设备
							  </label>
							</div>
							
							<div id="activeDevices" style="display:none;">
								<table id="selfDevice"  class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>设备名称</th>
											<th>固件版本</th>
											<th>设备状态</th>
										</tr>
									</thead>
									<tbody>	</tbody>
									<tfoot>
										<tr>
											<th>设备名称</th>
											<th>固件版本</th>
											<th>设备状态</th>
										</tr>
									</tfoot>
								</table>
								
							</div>
					         
				        </form>			            	
			         </div><!--	 ./modal-body	 -->
			         <div class="modal-footer">
			            <button type="button" class="btn btn-default"  data-dismiss="modal">关闭 </button>
			            <button type="button" class="btn btn-primary" id="addScheduleBtn">保存</button>
			         </div>
			      </div><!-- /.modal-content -->
				</div><!-- /.modal-dialog -->
			</div><!-- ./wrapper  模态框（Modal）  新增计划-->
		
			<!-- 模态框（Modal） 修改计划-->
			<div class="modal fade" id="updateScheduleBox" tabindex="-1" role="dialog"   aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false" data-keyboard="true">
			   <div class="modal-dialog">
			      <div class="modal-content">
			         <div class="modal-header">
			            <button type="button" class="close"       data-dismiss="modal" aria-hidden="true">&times;</button>
			            <h4 class="modal-title" id="myModalLabel">修改计划</h4>
			         </div>
			         <div class="modal-body">
			         	 <form>
			         	 	<div class="form-group">
					            <label for="updateProgramName" class="control-label">已选节目:</label>
					            <input type="text" class="form-control" id="updateProgramName" readonly>
								<input type="hidden" id="updateProgramID">
								<input type="hidden" id="updateProgramDuration">
					        </div>
					        <div class="form-group">
					            <label for="updateStartTime" class="control-label">开始时间:</label>
					            <input type="text" class="form-control" id="updateStartTime" tabIndex="-1">
					        </div>
				          	<div class="form-group">
					            <label for="updateEndTime" class="control-label">结束时间:</label>
					            <input type="text" class="form-control" id="updateEndTime" readonly>
					        </div>
					        <div class="form-group">
					            <label for="updateScheduleRangeName" class="control-label">设备范围:</label>
					            <input type="text" class="form-control" id="updateScheduleRangeName" readonly>
								<input type="hidden" id="updateScheduleRange">
					        </div>
					        <div id="updateActiveDevices" style="display:none;">
								<table id="updateSelfDevice"  class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>设备名称</th>
											<th>固件版本</th>
											<th>设备状态</th>
											<th>设备编码</th>
										</tr>
									</thead>
									<tbody id="updatedeviceBody">	</tbody>
									<tfoot>
										<tr>
											<th>设备名称</th>
											<th>固件版本</th>
											<th>设备状态</th>
											<th>设备编码</th>
										</tr>
									</tfoot>
								</table>
							</div>       	 	
			         </div><!--	 ./modal-body	 -->
			         <div class="modal-footer">
			         	<input type="hidden" name="updateScheduleID" value="" id="updateScheduleID"/>
			         	<input type="hidden" name="updateScheduleWeekday" value="" id="updateScheduleWeekday"/>
			            <button type="button" class="btn btn-default"  data-dismiss="modal">关闭 </button>
			            <button type="button" class="btn btn-default" id="deleteScheduleBtn">删除 </button>
			            <button type="button" class="btn btn-primary" id="updateScheduleBtn">修改</button>
			         </div>
			      </div><!-- /.modal-content -->
				</div><!-- /.modal-dialog -->
			</div><!-- ./wrapper 模态框（Modal） 修改计划 -->

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
		<script type="text/javascript" src="${rc.contextPath}/plugins/jQueryUI/jquery-ui.min.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/plugins/jquery-ui-timepicker/jquery-ui-timepicker-addon.min.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/plugins/jquery-ui-timepicker/jquery-ui-sliderAccess.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/plugins/jquery-ui-timepicker/jquery-ui-timepicker-zh-CN.js"></script>
		<script src="${rc.contextPath}/plugins/moment/moment.min.js"></script>

		<!-- brxy school server public js -->
		<script src="${rc.contextPath}/js/brxy.js"></script>
		<script src="${rc.contextPath}/js/schedule.js"></script>
		<!-- page script -->
			

	</body>
</html>
