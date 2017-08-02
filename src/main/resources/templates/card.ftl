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
		
		<style type="text/css">
			.box-right{
				text-align: right;padding-bottom: 0;
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
					<h1> 一卡通管理 <small>一卡通的新增、修改、删除</small></h1>
					<ol class="breadcrumb">
						<li>
							<a href="${rc.contextPath}"><i class="fa fa-dashboard"> </i> 首页</a>
						</li>
						<li class="active">
							一卡通管理
						</li>
					</ol>
				</section>

				<!-- Main content -->
				<section class="content">
					<div class="row">
						<div class="col-xs-12">						

							<div class="box">
								<div class="box-header  box-right">
									<input type="button" value="新增" id="addScheduleBtn" class="btn btn-info">
								</div>
								<!-- /.box-header -->
								<div class="box-body">
									<table id="rfidCardTable" class="table table-bordered table-hover">
										<thead>
											<tr>
												<th>卡号</th>
												<th>用户姓名</th>
												<th>电话</th>
												<th>开卡日期</th>
												<th>修改日期</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>	</tbody>
										<tfoot>
											<tr>
												<th>卡号</th>
												<th>用户姓名</th>
												<th>电话</th>
												<th>开卡日期</th>
												<th>修改日期</th>
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
			
			<!-- 模态框（Modal） -->
			<div class="modal fade" id="addSchedmaBox" tabindex="-1" role="dialog"   aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false" data-keyboard="true">
			   <div class="modal-dialog">
			      <div class="modal-content">
			         <div class="modal-header">
			            <button type="button" class="close"       data-dismiss="modal" aria-hidden="true">&times;</button>
			            <h4 class="modal-title" id="myModalLabel">新增计划方案</h4>
			         </div>
			         <div class="modal-body">
			         	 <form>
					          <div class="form-group">
					            <label for="addSchemaName" class="control-label">计划名称:</label>
					            <input type="text" class="form-control" id="addSchemaName">
					          </div>
					          <div class="form-group">
					            <label for="addSchemaComment" class="control-label">备注信息:</label>
					            <textarea class="form-control" id="addSchemaComment">&nbsp;</textarea>
					          </div>
					        </form>			            	
			         </div><!--	 ./modal-body	 -->
			         <div class="modal-footer">
			            <button type="button" class="btn btn-default"  data-dismiss="modal">关闭 </button>
			            <button type="button" class="btn btn-primary" id="addSchemaBtn">保存</button>
			         </div>
			      </div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
			</div><!-- ./模态框（Modal） -->

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
		
		<!-- page script -->
			
		<script type="text/javascript">
			$(function () {
				var table = $("#rfidCardTable").DataTable({
					ajax:{
						url:baseUrl+"/card/findAll"
					},
					ordering:true,  //是否启用排序
					searching: true,  //是否启用搜索
					paging: true,  //是否启用排序
					lengthChange: true,  //是否允许用户改变表格每页显示的记录数  10 20 50
					info: true,  //分页信息
					autoWidth: false,  //值为false的时候 表格随着浏览器大小变化
					pageLength:10,
					columns:[
						{data:"cardId"},
						{data:"username"},
						{data:"phone"},
						{data:"recordDate"},
						{data:"updateDate"},
						{data:null}
					],
					columnDefs:[
						{
							visible:true,
							orderable:false, //第四列禁止排序
							targets:5
						},
						{
							render:function(data,type,row,meta){
								//渲染 把数据源中的标题和url组成超链接
								var recordDate = moment(row.recordDate).format("YYYY-MM-DD HH:mm:ss");
            					return  recordDate;
							},
							targets:3  //时间显示格式化
						},
						{
							render:function(data,type,row,meta){
								//渲染 把数据源中的标题和url组成超链接
								if (row.updateDate&&row.updateDate!="") {
									var updateDate = moment(row.updateDate).format("YYYY-MM-DD HH:mm:ss");
	            					return  updateDate;
								}else{
									return "";
								}
							},
							targets:4  //时间显示格式化
						},
						{
							render:function(data,type,row,meta){
								//渲染 把数据源中的标题和url组成超链接
            					return '<a href="' + baseUrl + '/card/detail/'+row.id+'">' + row.cardId + '</a>';
							},
							targets:0
						},{
							targets:5,
							render: function (data,type,row,meta) {   
									var buttonHtml = "<button type='button' class='btn btn-info btn-sm' id='" + row.id + "' name='enableBtn'>查看</button>&nbsp;";
									buttonHtml += "<button type='button' class='btn btn-warning btn-sm' id='" + row.id + "' name='updateBtn'>修改</button>&nbsp;";
									buttonHtml += "<button type='button' class='btn btn-danger btn-sm' id='" + row.id + "' name='deleteBtn'>删除</button>&nbsp;";
		                       return buttonHtml;
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
				
				// $('#rfidCardTable tbody').on( 'click', 'tr', function () {
			        // $(this).toggleClass('selected');
			    // } );
			 
			    // $('#button').click( function () {
			        // alert( table.rows('.selected').data().length +' row(s) selected' );
			    // } );
			    
			    
			    
//+++++++++++++++++++++++++++新增计划任务+++++++++++++++++++++++

		$("#addScheduleBtn").click(function(){
			$("#addSchemaName").val("");
	        $("#addSchemaComment").val("");   
			$('#addSchedmaBox').modal('show');
		});
		
	
 $("#addSchemaBtn").click(function(){
 	var addSchemaName = $("#addSchemaName").val();
	var addSchemaComment = $("#addSchemaComment").val();   
	addSchema(addSchemaName,addSchemaComment);
 });


function addSchema(addSchemaName,addSchemaComment){
	console.log("luci page addSchema ");
	$.ajax({
		type : "post",
		dataType : "json",
		url : baseUrl+"/schema/addSchema",
		data : {
			schemaName:addSchemaName,
			schemaComment:addSchemaComment
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
			console.log(xhr.responseText);
			alert("addSchema 保存计划方案失败");
		},
		beforeSend : function() {
			openWaitBox();
		},
		complete : function(XMLHttpRequest, textStatus) {
			closeWaitBox();
		},
		success : function(result) {
			alert(result.message);
			if(result.result){
			  	$('#addSchedmaBox').modal('hide');
			  	table.ajax.reload();
			}
		}
	});
}

			    
			    /**
			     * 禁用方案
			     * @param e
			     */
			    $("#rfidCardTable tbody").on("click","button[name='disableBtn']",function(e){
			    	var schemaID = $(this).attr("id");
			        disableSchema(schemaID);
			    	return false; //阻止触发selected事件
			    });
			    
			     /**
			     * 启用方案
			     * @param e
			     */
			    $("#rfidCardTable tbody").on("click","button[name='enableBtn']",function(e){
			    	var schemaID = $(this).attr("id");
			        enableSchema(schemaID);
			    	return false; //阻止触发selected事件
			    });
			    
			     /**
			     * 删除方案
			     * @param e
			     */
			    $("#rfidCardTable tbody").on("click","button[name='deleteBtn']",function(e){
			    	var schemaID = $(this).attr("id");
			        deleteSchema(schemaID);
			    	return false; //阻止触发selected事件
			    });
			    
			    /**
			     * 修改方案
			     * @param e
			     */
			    $("#rfidCardTable tbody").on("click","button[name='updateBtn']",function(e){
			    	var schemaID = $(this).attr("id");
			    	//TODO 跳转到详情页面
			    	window.location.href=baseUrl+"/schema/update/"+schemaID;
			    	return false; //阻止触发selected事件
			    });
				
			    function disableSchema(schemaID) {
			    	console.log("disableSchema");
					$.ajax({
						type : "post",
						dataType : "json",
						url : baseUrl+'/schema/disable',
						data : {
							schemaID:schemaID			
						},
						error : function(xhr, status, e) {
							console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
							console.log(xhr.responseText);
							alert("禁用计划方案失败");
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
				                console.log("禁用方案成功" + JSON.stringify(data));
			            	}
			                table.ajax.reload();
						}
					});
			        
			    }
			    
			    function enableSchema(schemaID) {
			    	console.log("enableSchema");
					$.ajax({
						type : "post",
						dataType : "json",
						url : baseUrl+'/schema/enable',
						data : {
							schemaID:schemaID			
						},
						error : function(xhr, status, e) {
							console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
							console.log(xhr.responseText);
							alert("启用计划方案失败");
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
				                console.log("启用方案成功" + JSON.stringify(data));
			            	}
			                table.ajax.reload();
						}
					});
			        
			    }
			    
			    
			    function deleteSchema(schemaID) {
			    	console.log("deleteSchema");
					$.ajax({
						type : "post",
						dataType : "json",
						url : baseUrl+'/schema/delete',
						data : {
							schemaID:schemaID			
						},
						error : function(xhr, status, e) {
							console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
							console.log(xhr.responseText);
							alert("删除计划方案失败");
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
				                console.log("删除方案成功" + JSON.stringify(data));
			            	}
			                table.ajax.reload();
						}
					});
			        
			    }
				
				
				
				
				
				
			
							
				

});

		</script>
	</body>
</html>
