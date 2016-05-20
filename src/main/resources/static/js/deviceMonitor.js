$(function() {
	
	/**
	 * 初始化datatables
	 */
	var table = $("#deviceTable").DataTable({
		ajax : {
			url : baseUrl + "/device/queryMonitorDevice"
		},
		ordering : true, //是否启用排序
		searching : true, //是否启用搜索
		paging : true, //是否启用排序
		lengthChange : true, //是否允许用户改变表格每页显示的记录数  10 20 50
		info : true, //分页信息
		autoWidth : false, //值为false的时候 表格随着浏览器大小变化
		pageLength : 10,
		columns : [{
			"data" : "deviceName"
		}, {
			"data" : "deviceId"
		}, {
			"data" : "firmversion"
		}, {
			"data" : "deviceStatus"
		}, {
			data : null
		}],
		columnDefs : [{
			visible : true,
			orderable : false, //第四列禁止排序
			targets : 3
		}, {
			render : function(data, type, row, meta) {
				//渲染 把数据源中的标题和url组成超链接
				var url = baseUrl + "/device/detail/" + row.deviceId;
				return '<a href="' + url + '">' + row.deviceName + '</a>';
			},
			targets : 0
		}, {
			targets : 4,
			render : function(data, type, row, meta) {
				var html = "<button type='button' class='btn btn-primary btn-sm' id='" + row.deviceId + "' name='detailBtn'>详情</button>&nbsp;&nbsp;" + "<button type='button' class='btn btn-danger btn-sm' id='" + row.deviceId + "' name='delBtn'>删除</button>&nbsp;&nbsp;" + "<button type='button' class='btn btn-info btn-sm' id='" + row.deviceId + "' name='logBtn'>日志</button>";
				return html;
			}
		}],
		language : {
			"sProcessing" : "处理中...",
			"sLengthMenu" : "显示 _MENU_ 项结果",
			"sZeroRecords" : "没有匹配结果",
			"sInfo" : "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
			"sInfoEmpty" : "显示第 0 至 0 项结果，共 0 项",
			"sInfoFiltered" : "(由 _MAX_ 项结果过滤)",
			"sInfoPostFix" : "",
			"sSearch" : "搜索:",
			"sUrl" : "",
			"sEmptyTable" : "表中数据为空",
			"sLoadingRecords" : "载入中...",
			"sInfoThousands" : ",",
			"oPaginate" : {
				"sFirst" : "首页",
				"sPrevious" : "上页",
				"sNext" : "下页",
				"sLast" : "末页"
			},
			"oAria" : {
				"sSortAscending" : ": 以升序排列此列",
				"sSortDescending" : ": 以降序排列此列"
			}
		}
	});

	/**
	 * 行选择设备记录
	 */
	$('#deviceTable tbody').on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

	/**
	 * 设备刷卡日志
	 * @param e
	 */
	$("#deviceTable tbody").on("click", "button[name='logBtn']", function(e) {
		var deviceID = $(this).attr("id");
		window.location.href = baseUrl + "/swipeCardLog/main/" + deviceID + "/123";
		return false;
		//阻止触发selected事件
	});

	/**
	 * 设备详情
	 * @param e
	 */
	$("#deviceTable tbody").on("click", "button[name='detailBtn']", function(e) {
		var deviceID = $(this).attr("id");
		window.location.href = baseUrl + "/device/detail/" + deviceID;
		//getDeviceDetail(deviceID);
		return false;
		//阻止触发selected事件
	});

	/**
	 * 删除设备
	 * @param e
	 */
	$("#deviceTable tbody").on("click", "button[name='delBtn']", function(e) {
		var deviceID = $(this).attr("id");
		deleteDevice(deviceID);
		return false;
		//阻止触发selected事件
	});

	function deleteDevice(deviceID) {
		console.log("deleteDevice deviceID=" + deviceID);
		$.ajax({
			type : "post",
			dataType : "json",
			url : baseUrl + '/device/deleteDevice',
			data : {
				deviceID : deviceID
			},
			error : function(xhr, status, e) {
				console.error('JqueryAjax error invoke! status:' + status + e + " " + xhr.status);
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
				if (data.result) {
					table.ajax.reload();
					console.log("deleteDevice success" + JSON.stringify(data));
				} else {
				}
			}
		});
	}

	//++++++++++++++++++控制设备+++++++++++++++++++++++++++++++++

	$("#startDevice").click(function() {
		var operationCode = operation.start;
		batchControllDevice(operationCode);
	});

	$("#shutdownDevice").click(function() {
		var operationCode = operation.shutdown;
		batchControllDevice(operationCode);
	});

	$("#openBoardcastBtn").click(function() {		
		var deviceIDs = getDeviceListBodyChoice();
		if (deviceIDs.length == 0) {
			alert("请选择需要接受广播的设备");
		} else {
			queryProgramList();
			
		}
		//openBroadcastBox();
	});
	
	$("#boardcastBtn").click(function(){
		var deviceIDs = getDeviceListBodyChoice();
		if (deviceIDs.length == 0) {
			alert("请选择需要接受广播的设备");
		} else {
			var programID = $("#programSelect").val();
	    	broadcast(deviceIDs,programID);
		}
	});
	
	function broadcast(deviceIDs,programID){	
		$.ajax({
			type : "post",
			dataType : "json",
			url : baseUrl+"/device/broadcast",
			data : {
				act : "broadcast",
				deviceIDs:deviceIDs.toString(),
				programID:programID
			},
			error : function(xhr, status, e) {
				console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
				console.log(xhr.responseText);
				alert("broadcast 广播失败");
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
						 $('#addBroadcastBox').modal('close');
					}
			}
		});
	}
	
	function queryProgramList(){
		console.log("luci page queryProgramList ");
		$.ajax({
			type : "post",
			dataType : "json",
			url : baseUrl+"/program/findAll",
			data : {
				act : "queryProgramList"
			},
			error : function(xhr, status, e) {
				console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
				console.log(xhr.responseText);
				alert("queryProgramList 查询节目列表失败");
			},
			beforeSend : function() {
				openWaitBox();
			},
			complete : function(XMLHttpRequest, textStatus) {
				closeWaitBox();
			},
			success : function(result) {
					$('#addBroadcastBox').modal('show');
					var html = "";
					$(result).each(function(index,file){
						html+="<option value='"+file.id+"' data-duration='"+file.duration+"' data-name='"+file.name+"'>"+file.name+"</option>";
					});
					$("#programSelect").html(html);
			}
		});
	}
	
	
	
	function getDeviceListBodyChoice(){
		//获取所有的选择项
		var devices = table.rows('.selected').data();
		var deviceIDs = []; 
		$(devices).each(function(index,device){
			deviceIDs.push(device.deviceId);
		});
		return deviceIDs;
	}

	function batchControllDevice(operationCode) {
		var deviceTypeCode = deviceType.dtsHost;
		
		var deviceIDs = getDeviceListBodyChoice();
		if (deviceIDs.length == 0) {
			alert("请选择设备");
		} else {

			$.ajax({
				type : "post",
				dataType : "json",
				url : baseUrl + "/device/batchControllDevice",
				data : {
					act : "batchControllDevice",
					deviceIDs : deviceIDs.toString(),
					deviceType : deviceTypeCode,
					operationCode : operationCode
				},
				error : function(xhr, status, e) {
					console.error('JqueryAjax error invoke! status:' + status + e + " " + xhr.status);
					console.log(xhr.responseText);
					alert("batchControllDevice 批量控制设备失败 deviceTypeCode=" + deviceTypeCode + "  operationCode=" + operationCode + "  deviceIDs=" + deviceIDs.toString());
				},
				beforeSend : function() {
					openWaitBox();
				},
				complete : function(XMLHttpRequest, textStatus) {
					closeWaitBox();
				},
				success : function(result) {
					alert(result.message);
					if (result.result) {
						table.ajax.reload();
					}
				}
			});
		}

	}

});

