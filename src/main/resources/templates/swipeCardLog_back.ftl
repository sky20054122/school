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
								<div class="box-header">
									<h3 class="box-title">未激活设备列表</h3>
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
					</div>
					<!-- /.row -->
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

		<!-- brxy school server public js -->
		<script src="${rc.contextPath}/js/brxy.js"></script>
		<!-- page script -->
		
		
		<script type="text/javascript">
			$(function () {
				var table = $("#deviceTable").DataTable({
					ajax:{
						url:baseUrl+"/device/queryMonitorDevice"
					},
					ordering:true,  //是否启用排序
					searching: true,  //是否启用搜索
					paging: true,  //是否启用排序
					lengthChange: true,  //是否允许用户改变表格每页显示的记录数  10 20 50
					info: true,  //分页信息
					autoWidth: false,  //值为false的时候 表格随着浏览器大小变化
					pageLength:10,
					columns:[
						{"data":"deviceName"},
						{"data":"deviceId"},
						{"data":"firmversion"},
						{"data":"deviceStatus"},
						{data:null}
					],
					columnDefs:[
						{
							visible:true,
							orderable:false, //第四列禁止排序
							targets:3
						},
						{
							render:function(data,type,row,meta){
								//渲染 把数据源中的标题和url组成超链接
            					return '<a href="' + data + '" target="_blank">' + row.deviceName + '</a>';
							},
							targets:0
						},{
							targets:4,
							render: function (data,type,row,meta) {   
		                       var html = "<button type='button' class='btn btn-primary btn-sm' id='" + row.deviceId + "' name='detailBtn'>详情</button>&nbsp;&nbsp;"
		                       +"<button type='button' class='btn btn-danger btn-sm' id='" + row.deviceId + "' name='delBtn'>删除</button>&nbsp;&nbsp;"
		                       +"<button type='button' class='btn btn-info btn-sm' id='" + row.deviceId + "' name='logBtn'>日志</button>";
		                    	return html;
		                    }
						}
					],
					language: {
				        "sProcessing": "处理中...",
				        "sLengthMenu": "显示 _MENU_ 项结果",
				        "sZeroRecords": "没有匹配结果",
				        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
				        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
				        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
				        "sInfoPostFix": "",
				        "sSearch": "搜索:",
				        "sUrl": "",
				        "sEmptyTable": "表中数据为空",
				        "sLoadingRecords": "载入中...",
				        "sInfoThousands": ",",
				        "oPaginate": {
				            "sFirst": "首页",
				            "sPrevious": "上页",
				            "sNext": "下页",
				            "sLast": "末页"
				        },
				        "oAria": {
				            "sSortAscending": ": 以升序排列此列",
				            "sSortDescending": ": 以降序排列此列"
				        }
				    }					
				});
				
				$('#deviceTable tbody').on( 'click', 'tr', function () {
			        $(this).toggleClass('selected');
			    } );
			 
			 //获取所有的选择项
			    $('#button').click( function () {
			        alert( table.rows('.selected').data().length +' row(s) selected' );
			    } );
			    
			    
			    /**
			     * 设备刷卡日志
			     * @param e
			     */
			    $("#deviceTable tbody").on("click","button[name='logBtn']",function(e){
			    	var deviceID = $(this).attr("id");
			    	window.location.href=baseUrl+"/log/main?deviceID="+deviceID+"&cardID=123";
			    	return false; //阻止触发selected事件
			    });
			    
			    /**
			     * 设备详情
			     * @param e
			     */
			    $("#deviceTable tbody").on("click","button[name='detailBtn']",function(e){
			    	var deviceID = $(this).attr("id");
			    	getDeviceDetail(deviceID);
			    	return false; //阻止触发selected事件
			    });
				
			    function getDeviceDetail(deviceID) {
			    	console.log("getDeviceDetail deviceID="+deviceID);
					$.ajax({
						type : "post",
						dataType : "json",
						url : baseUrl+'/device/getDeviceDetail',
						data : {
							deviceID:deviceID			
						},
						error : function(xhr, status, e) {
							console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
							console.log(xhr.responseText);
							alert("getDeviceDetail fail ");
						},
						beforeSend : function() {
							openWaitBox();
						},
						complete : function(XMLHttpRequest, textStatus) {
							closeWaitBox();
						},
						success : function(data) {
			            	if(data.result){
				                table.ajax.reload();
				                console.log("getDeviceDetail success" + JSON.stringify(data));
			            	}else{
								alert(data.message);
			            	}
						}
					});
			    }
			    
			    
			    /**
			     * 删除设备
			     * @param e
			     */
			    $("#deviceTable tbody").on("click","button[name='delBtn']",function(e){
			    	var deviceID = $(this).attr("id");
			    	deleteDevice(deviceID);
			    	return false; //阻止触发selected事件
			    });
				
			    function deleteDevice(deviceID) {
			    	console.log("deleteDevice deviceID="+deviceID);
					$.ajax({
						type : "post",
						dataType : "json",
						url : baseUrl+'/device/deleteDevice',
						data : {
							deviceID:deviceID			
						},
						error : function(xhr, status, e) {
							console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
							console.log(xhr.responseText);
							alert("deleteDevice fail ");
						},
						beforeSend : function() {
							openWaitBox();
						},
						complete : function(XMLHttpRequest, textStatus) {
							closeWaitBox();
						},
						success : function(data) {
							alert(data.message);
			            	if(data.result){
				                table.ajax.reload();
				                console.log("deleteDevice success" + JSON.stringify(data));
			            	}else{
			            	}
						}
					});
			    }
				
				
// $('#example2').DataTable({
// "paging": true,
// "lengthChange": false,
// "searching": false,
// "ordering": true,
// "info": true,
// "autoWidth": false
// });

});

		</script>
	</body>
</html>
