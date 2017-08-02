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
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<input type="hidden" id="baseUrl" value="${rc.contextPath}" />
		<div class="wrapper">
			<div id="header">&nbsp;</div>
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper">
				<!-- Content Header (Page header) -->
				<section class="content-header">
					<h1> 文件管理 <small>文件上传、删除</small></h1>
					<ol class="breadcrumb">
						<li>
							<a href="${rc.contextPath}"><i class="fa fa-dashboard"> </i> 首页</a>
						</li>
						<li class="active">
							文件管理
						</li>
					</ol>
				</section>

				<!-- Main content -->
				<section class="content">
					<div class="row">
						<div class="col-xs-12">						

							<div class="box">
								<div class="box-header">
									<h3 class="box-title">文件列表</h3>
								</div>
								<!-- /.box-header -->
								<div class="box-body">
									<table id="programTable" class="table table-bordered table-hover">
										<thead>
											<tr>
												<th>文件名称</th>
												<th>持续时长</th>
												<th>上传时间</th>
												<th>文件大小</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>	</tbody>
										<tfoot>
											<tr>
												<th>文件名称</th>
												<th>持续时长</th>
												<th>上传时间</th>
												<th>文件大小</th>
												<th>操作</th>
											</tr>
										</tfoot>
									</table>
								</div>
								<!-- /.box-body -->
							</div>
							<!-- /.box -->
							
							<div class="box">
								<div class="box-header">
									<h3 class="box-title">上传文件</h3>
								</div>
								<!-- /.box-header -->
								<div class="box-body">
									<form id="uploadForm" method="post" action="${rc.contextPath}/file/fileUpload" enctype="multipart/form-data">
										<div class="col-xs-6">
											<input type="file" accept="audio/mp3" class="file" name="file" id="file"  style="display: inline;"/> 
										</div>
										<div class="col-xs-6" style="text-align: right;">
											<input type="hidden" name="fileMD5" id="fileMD5"/>
											<input type="hidden" name="duration" id="duration" />
											<input type="hidden" name="fileExtension" id="fileExtension" />
											<input type="hidden" name="fileSize" value="" id="fileSize"/>
											<input type="submit" class="btn btn-info" id="uploadBtn" value="开始上传" style="display: none;"/>
										</div>
										<div class="col-xs-12" id="preview" style="margin: 1.5em auto;">
											
										</div>
									    <div id="status" class="col-xs-12"> </div>
										<div class="col-xs-12">
									        <div class="progress">
								                <div id="uploadProgress" class="progress-bar progress-bar-aqua" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
								                  <span>0% Complete</span>
								                </div>
								            </div>
									    </div>
									    
									</form>
								
								
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

		<script src="${rc.contextPath}/plugins/moment/moment.min.js"></script>
		<!-- brxy school server public js -->
		<script src="${rc.contextPath}/js/brxy.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/plugins/jQuery/jquery.form.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/plugins/fileCalculator/calculator_md5.js"></script>
		
		<script type="text/javascript" src="${rc.contextPath}/js/fileUpload.js"></script>
		<!-- page script -->
			
		<script type="text/javascript">
			$(function () {
				var table = $("#programTable").DataTable({
					ajax:{
						url:baseUrl+"/program/findAll"
						
					},
					ordering:true,  //是否启用排序
					searching: true,  //是否启用搜索
					paging: true,  //是否启用排序
					lengthChange: true,  //是否允许用户改变表格每页显示的记录数  10 20 50
					info: true,  //分页信息
					autoWidth: false,  //值为false的时候 表格随着浏览器大小变化
					pageLength:10,
					columns:[
						{data:"name"},
						{data:"duration"},
						{data:"recordDate"},
						{data:"fileSize"},					
						{data:null}						
					],
					columnDefs:[
						{
							visible:true,
							orderable:false, //第5列禁止排序
							targets:4,
							render: function (data,type,row,meta) {   
		                       return "<button type='button' class='btn btn-primary btn-sm' id='" + row.id + "' name='delete'>删除</button>";
		                    }
						},
						{
							render:function(data,type,row,meta){
								//渲染 把数据源中的标题和url组成超链接
            					return '<a href="' + data + '" target="_blank" title="点击预览">' + row.name + '</a>';
							},
							targets:0
						},
						{
							render:function(data,type,row,meta){
								var duration = row.duration;
								var durationHMS = convertDurationToHMS(duration);
								//把音频时长转换为时分秒格式  00:03:25
            					return durationHMS;
							},
							targets:1
						},
						{
							render:function(data,type,row,meta){
								//渲染 把数据源中的标题和url组成超链接
								var recordDate = moment(row.recordDate).format("YYYY-MM-DD HH:mm:ss");
            					return  recordDate;
							},
							targets:2  //时间显示格式化
						},
						{
							render:function(data,type,row,meta){
								var fileSize = row.fileSize;
								var fileSizeStr = convertFileSize(fileSize);
								//把文件大小格式化
            					return fileSizeStr;
							},
							targets:3  //文件大小格式化
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
				
				$('#programTable tbody').on( 'click', 'tr', function () {
			        $(this).toggleClass('selected');
			    } );
			 
			    $('#button').click( function () {
			        alert( table.rows('.selected').data().length +' row(s) selected' );
			    } );
			    
			    /**
			     * 把音频时长转换为时分秒格式
			     */
			    function convertDurationToHMS(duration){
			    	var hour = Math.floor (duration / 3600);
			    	if (hour<10) {
			    		hour="0"+hour;
			    	};
					var other = duration % 3600;
					var minute = Math.floor (other / 60);
					if (minute<10) {
			    		minute="0"+minute;
			    	};
					var second = (other % 60);
					if (second<10) {
			    		second="0"+second;
			    	};
					var hms = hour + ':' + minute + ':' + second;
			    	return hms; 
			    }
			    
			    /**
			     * 文件大小格式化  字节转换为MB
			     */
			    function convertFileSize(fileSize){
			    	var fileSizeStr = (fileSize/1024/1024).toFixed(2);
			    	return fileSizeStr+"MB";
			    }
			    
			    /**
			     * 删除文件
			     * @param e
			     */
			    $("#programTable tbody").on("click","button[name='delete']",function(e){
			    	var programID = $(this).attr("id");
			    	deleteProgram(programID);
			    	return false; //阻止触发selected事件
			    });
				
			    function deleteProgram(programID) {
			    	console.log("deleteProgram programID="+programID);
					$.ajax({
						type : "post",
						dataType : "json",
						url : baseUrl+'/program/deleteProgramFile',
						data : {
							programID:programID			
						},
						error : function(xhr, status, e) {
							console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
							console.log(xhr.responseText);
							alert("删除节目失败");
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
				                console.log("删除节目成功" + JSON.stringify(data));
			            	}
						}
					});
			        
			    }
				
				


});

		</script>
	</body>
</html>
