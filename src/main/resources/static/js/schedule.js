/**
 * 
 * 计划任务 CRUD javascript文件 
 * 
 * xiaobing yang 
 * 
 * 2016-05-31
 */

// +++++++++++++++++++++++查询计划任务详情++++++++++++++++++++++++++
$(document).ready(function() {

	getSchemaDetail();
});

function getSchemaDetail() {
	var schemaID = $("#schemaID").val();
	console.log("luci page getSchemaDetail schemaID= " + schemaID);
	$.ajax({
		type : "post",
		dataType : "json",
		url : baseUrl + "/schema/getSchemaDetail",
		data : {
			schemaID : schemaID
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status + e + " "+ xhr.status);
			console.log(xhr.responseText);
			alert("getSchemaDetail 查询方案详情失败");
		},
		beforeSend : function() {
			openWaitBox();
		},
		complete : function(XMLHttpRequest, textStatus) {
			closeWaitBox();
		},
		success : function(schema) {
			$("#schemaName").html(schema.name);
			var schedules = schema.schedules;
			$(schedules).each(function(i, schedule) {
				addScheduleToPage(schedule);
				console.log(schedule.startTime + " -- "	+ schedule.endTime + "  "+ schedule.program.name + "  "+ schedule.program.id + "  " + schedule.id);
			});

			$("#scheduleBody td.scheduleTd").each(function(i, name) {
				sortSchedule($(name));
			});

		}
	});
}

function addScheduleToPage(schedule) {
	var weekday = schedule.weekday;
	var program = schedule.program;
	var scheduleTd = $("#scheduleBody td.scheduleTd[name='" + weekday + "']");

	var scheduleRange = schedule.scheduleRange;
	var classTag = "scheduleDiv";
	if (scheduleRange == "SCHEDULE_PART_DEVICE") {// 自选
		classTag = "scheduleDiv scheduleRange0";
	} else if (scheduleRange == "SCHEDULE_ALL_DEVICE") { // 全校
		classTag = "scheduleDiv scheduleRange1";
	} else {
		alert("scheduleRange error scheduleRange=" + scheduleRange);
	}
	var html = "<div class='"
			+ classTag
			+ "' index='"
			+ schedule.index
			+ "'><table class='scheduleList'><tr><td>"
			+ schedule.startTime
			+ "—"
			+ schedule.endTime
			+ "&nbsp;"
			+ program.name
			+ "</td><td class='scheduleEditIcon'><span class='fa fa-edit' data-scheduleRange='"
			+ scheduleRange
			+ "' data-startTime='"
			+ schedule.startTime
			+ "' data-id='"
			+ schedule.id
			+ "' data-endTime='"
			+ schedule.endTime
			+ "' data-program='"
			+ program.id
			+ "' data-weekday='"
			+ schedule.weekday
			+ "' data-scheduleID='"
			+ schedule.id
			+ "' onclick='openUpdateScheduleBox(this)'></td></tr></table></div>";
	$(scheduleTd).append(html);

}

function sortSchedule(ic) {
	var divs = ic.find("div.scheduleDiv");
	var arr = divs.get();
	arr.sort(function(a, b) {
		var ai = parseFloat($(a).attr("index"), 10);
		var bi = parseFloat($(b).attr("index"), 10);
		if (ai > bi) {
			return 1;
		} else if (ai < bi) {
			return -1;
		} else {
			return 0;
		}
	});
	ic.append(arr);
}

// --------------------------查询计划任务详情----------------------------------------

// ++++++++++++++++++++++++++++++++新增计划任务+++++++++++++++++++++++++++++++++

$(function() {

	// 新增计划
	$("#scheduleBody td.scheduleTd").on("dblclick",function(event) {
		var targetClassName = event.target.className;
		if (targetClassName == "scheduleTd") { // 新增
			queryProgramList();
			queryaddScheduleAllDevices();
			$("#addScheduleBox").modal("show");
			var weekday = $(this).attr("name");
			$("#addWeekday").val(weekday);
			console.log("scheduleTd dblclick add schedule weekday="+ weekday);
		} else {
			console.log(targetClassName+ " dblclick on schedule");
			event.stopPropagation();
			event.cancelBubble = true;
			return false;
		}
		
	});

	$("#addStartTime").timepicker({
		onSelect : function(datetimeText, datepickerInstance) {
			console.log(datetimeText);
			console.log(datepickerInstance);
			addTimeAction();
		},
		timeFormat : 'HH:mm:ss',
		timeInput : false,
		hourMin : 6,
		hourMax : 23,
		regional : 'zh-CN',
		addSliderAccess : true,
		sliderAccessArgs : {
			touchonly : false
		},
		showButtonPanel : false	// 是否显示按钮
	});

	$("#addScheduleBtn").click(function() {
		var addStartTime = $("#addStartTime").val();
		var addEndTime = $("#addEndTime").val();
		var programSelectID = $("#programSelect").val();
		var selectFileName = $("#programSelect").find("option:selected").attr("data-name");
		var index = getIndex(addStartTime);
		var weekday = $("#addWeekday").val();
		var scheduleTd = $("#scheduleBody td.scheduleTd[name='"+ weekday + "']");
		var conflict = findTimeConflict(null,scheduleTd, addStartTime,addEndTime);
		if (conflict) { // false 表示时间冲突
			var deviceIDs = [];
			var scheduleRange = $("input[type='radio'][name='scheduleRange']:checked").val();
			if (scheduleRange == "SCHEDULE_PART_DEVICE") { // 用户自选设备
				deviceIDs = getDeviceListBodyChoice(addScheduleDeviceTable);
				console.log("deviceIDs="+ deviceIDs);
				if (deviceIDs.length == 0) {
					alert("请选择一体机设备");
				} else {
					saveSchedule(index, addStartTime,
							addEndTime, programSelectID,
							weekday, scheduleRange, deviceIDs);
				}
	
			} else {
				saveSchedule(index, addStartTime, addEndTime,
						programSelectID, weekday,
						scheduleRange, deviceIDs);
			}
		}
	
	});

	/**
	 * scheduleRange = SCHEDULE_ALL_DEVICE 全校所有设备 
	 * 
	 * scheduleRange = SCHEDULE_PART_DEVICE 用户自选设备
	 */
	$("input[type='radio'][name='scheduleRange']").change(function() {
		var scheduleRange = $("input[type='radio'][name='scheduleRange']:checked").val();
		if (scheduleRange == "SCHEDULE_PART_DEVICE") {
			$("#activeDevices").show();
		} else if (scheduleRange == "SCHEDULE_ALL_DEVICE") {
			$("#activeDevices").hide();
		} else {
			alert("scheduleRange error");
		}
	});

	$("#programSelect").on("change",function() {
		addTimeAction();
		var programName = $("#programSelect").find("option:selected").attr("data-name");
	});
	
	$("#selfDevice").on("init.dt",function(){
		console.log("dt init");
		
	});
	
	// datatable 重新初始化  destroy时候，重新绑定点击选择事件
	$("#selfDevice").on("destroy.dt",function(){
		$('#selfDevice tbody').on('click', 'tr', function() {
			$(this).toggleClass('selected');
		});
	});
	
	// datatable 重新初始化  destroy时候，重新绑定点击选择事件
	$("#updateSelfDevice").on("destroy.dt",function(){
		$('#updateSelfDevice tbody').on('click', 'tr', function() {
			$(this).toggleClass('selected');
		});
	});

});

function getDeviceListBodyChoice(table) {
	// 获取所有的选择项
	var devices = table.rows('.selected').data();
	var deviceIDs = [];
	$(devices).each(function(index, device) {
		deviceIDs.push(device.deviceId);
	});
	return deviceIDs;
}

function getIndex(addStartTime) {
	var index = moment.duration(addStartTime).asSeconds();
	console.log("getIndex addStartTime" + addStartTime + " index=" + index);
	return index;
}

/**
 * 已知多个时间段 startTime -- endTime 要求 新加的addStartTime--addEndTime在已知时间段之外
 * 即：addStartTime>endTime 或者 addEndTime<startTime满足要求 返回 false表示不满足条件
 * 
 * scheduleID 新增计划 scheduleID=null
 */
function findTimeConflict(scheduleID,scheduleTd, addStartTime, addEndTime) {
	var flag = false;
	var scheduleDivs = $(scheduleTd).find("div.scheduleDiv");
	if (scheduleDivs.length == 0) {
		flag = true;
	}
	$(scheduleDivs).each(function(i, scheduleDiv) {
		var icon = $(scheduleDiv).find("span.fa-edit");
		var startTime = $(icon).attr("data-startTime");
		var endTime = $(icon).attr("data-endTime");
		var scheduleID_icon = $(icon).attr("data-scheduleID");
		var as = moment.duration(addStartTime).asSeconds();
		var ae = moment.duration(addEndTime).asSeconds();

		var is = moment.duration(startTime).asSeconds();
		var ie = moment.duration(endTime).asSeconds();
		console.log("new schedule time : " + addStartTime + "--"+ addEndTime + " exsist schedule time : " + startTime+ "--" + endTime);
		if (ae < is || as > ie) {
			flag = true;
		} else {
			if(null==scheduleID||scheduleID!=scheduleID_icon){
				//如果是新增  scheduleID==null  时间冲突 返回 false  ，如果是修改  还需要排除scheduleID本身所在的计划
				flag = false;
				console.warn("new time(" + as + "-" + ae+ ") is conflict with exist time(" + is + "-" + ie+ ")");
				alert("新增时间(" + addStartTime + "-" + addEndTime+ ") 和已知计划任务时间 (" + startTime + "-" + endTime+ ")冲突");
			}else{
				flag = true;
			}
			return flag;
		}
	});
	return flag;

}

function saveSchedule(index, addStartTime, addEndTime, programSelectID,
		weekday, scheduleRange, deviceIDs) {
	var schemaID = $("#schemaID").val();
	console.log("luci page saveSchedule index=" + index + " addStartTime="
			+ addStartTime + " addEndTime=" + addEndTime + " programSelectID="
			+ programSelectID + " weekday=" + weekday + "  schemaID="
			+ schemaID);
	$.ajax({
		type : "post",
		dataType : "json",
		url : baseUrl + "/schedule/saveSchedule",
		data : {
			index : index,
			startTime : addStartTime,
			endTime : addEndTime,
			programID : programSelectID,
			weekday : weekday,
			schemaID : schemaID,
			scheduleRange : scheduleRange,
			deviceIDs : deviceIDs.toString()
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status + e + " "+ xhr.status);
			console.log(xhr.responseText);
			alert("saveSchedule 失败");
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
				if(addScheduleDeviceTable){
					addScheduleDeviceTable.destroy();
					
				}
				$("#addScheduleBox").modal("hide");
				// 保存成功 刷新页面
				window.location.reload(true);
			}
		}
	});

}

function queryProgramList() {
	console.log("luci page queryProgramList ");
	$.ajax({
		type : "post",
		dataType : "json",
		url : baseUrl + "/program/findAll",
		data : {
			act : "queryProgramList"
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status + e + " "+ xhr.status);
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
			var html = "";
			$(result.data).each(function(index, file) {
				html += "<option value='" + file.id
						+ "' data-duration='" + file.duration
						+ "' data-name='" + file.name + "'>"
						+ file.name + "</option>";
			});
			$("#programSelect").html(html);
		}
	});
}

function addTimeAction() {

	var duration = $("#programSelect").find("option:selected").attr("data-duration"); // 单位秒数值
	var start = moment("2016-03-20 " + $("#addStartTime").val(),'YYYY-MM-DD HH:mm:ss');
	var endDate = start.add(duration, "seconds");
	$("#addEndTime").val(endDate.format("HH:mm:ss"));
	console.log("addTimeAction:" + $("#addStartTime").val() + " + " + duration+ " = " + endDate.format("HH:mm:ss"));
}

var addScheduleDeviceTable=null;
function queryaddScheduleAllDevices() {
	// 添加计划任务时候 jquey datatables查询所有以激活设备
	
	addScheduleDeviceTable = $("#selfDevice").DataTable({
		ajax : {
			url : baseUrl + "/device/queryMonitorDevice"
		},
		destroy: true,
		ordering : true, // 是否启用排序
		searching : true, // 是否启用搜索
		paging : true, // 是否启用排序
		lengthChange : true, // 是否允许用户改变表格每页显示的记录数 10 20 50
		info : true, // 分页信息
		autoWidth : false, // 值为false的时候 表格随着浏览器大小变化
		pageLength : 10,
		columns : [ {
			"data" : "deviceName"
		}, {
			"data" : "firmVersion"
		}, {
			data : "deviceStatus"
		} ],
		columnDefs : [{
			visible : true,
			orderable : true, // 第四列禁止排序
			targets : 2
		}],
	    initComplete:function(e, settings, json){
	    	
	    	console.log("initComplete");
	    },
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

	$('#selfDevice tbody').on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

}

// -----------------------------------新增计划任务--------------------------------

// ++++++++++++++++++++++++++++++++++修改计划任务++++++++++++++++++++++++++++++++++

function getScheduleDetail(scheduleID) {
	$.ajax({
		type : "post",
		dataType : "json",
		url : baseUrl + "/schedule/getScheduleDetail",
		data : {
			scheduleID : scheduleID
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status + e + " "+ xhr.status);
			console.log(xhr.responseText);
			alert("getScheduleDetail 失败");
		},
		beforeSend : function() {
			openWaitBox();
		},
		complete : function(XMLHttpRequest, textStatus) {
			closeWaitBox();
		},
		success : function(result) {
			// alert(result.msg);
			var data = result.schedule;
			if (result.result) {
				$("#updateProgramName").val(data.program.name);
				$("#updateProgramID").val(data.program.id);
				$("#updateProgramDuration").val(data.program.duration);
				$("#updateStartTime").val(data.startTime);
				$("#updateEndTime").val(data.endTime);
				$("#updateScheduleID").val(scheduleID);
				$("#updateScheduleWeekday").val(data.weekday);
				updateStartTimeInit();
				var scheduleRange = data.scheduleRange;
				$("#updateScheduleRange").val(scheduleRange);
				var scheduleRangeHtml = "";
				if (scheduleRange == "SCHEDULE_PART_DEVICE") { // 自选
					$("#updateScheduleRangeName").val("局部计划");
					var selectDevices = data.devices;
					var deviceIDs = [];
					$(selectDevices).each(function(index, device) {
						deviceIDs.push(device.deviceId);
					});
					queryUpdateScheduleAllDevices(deviceIDs);
					$("#updateActiveDevices").show();

				} else if (scheduleRange == "SCHEDULE_ALL_DEVICE") { // 全校
					$("#updateScheduleRangeName").val("全校计划");
					$("#updateActiveDevices").hide();
				}

			}
		}// ./success
	});// ./ajax
}

// 修改计划任务时候 查询所有设备的信息
var updateScheduleDeviceTable =null;
function queryUpdateScheduleAllDevices(deviceIDs) {

	updateScheduleDeviceTable = $("#updateSelfDevice").DataTable({
		ajax : {
			url : baseUrl + "/device/queryMonitorDevice"
		},
		destroy: true,
		ordering : true, // 是否启用排序
		searching : true, // 是否启用搜索
		paging : true, // 是否启用排序
		lengthChange : true, // 是否允许用户改变表格每页显示的记录数 10 20 50
		info : true, // 分页信息
		autoWidth : false, // 值为false的时候 表格随着浏览器大小变化
		pageLength : 10,
		columns : [ {
			data : "deviceName"
		}, {
			data : "firmVersion"
		}, {
			data : "deviceStatus"
		}, {
			data : "deviceId"
		} ],
		columnDefs : [ {
			visible : false,
			orderable : true,
			targets : 3
		}

		],
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
		},
		"rowCallback" : function(row, data, index) {

			var deviceID = data.deviceId;

			var result = $.inArray(deviceID, deviceIDs);
			console.log("deviceIDs= " + deviceIDs);
			console.log("deviceID=" + deviceID+ " is in deviceIDs result=" + result);

			if (result != "-1") {
				$(row).addClass("selected");
			}
		}
	});

	$('#updateSelfDevice tbody').on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

}

function updateTimeAction() {

	var duration = $("#updateProgramDuration").val(); // 单位秒数值
	var start = moment("2016-03-20 " + $("#updateStartTime").val(),'YYYY-MM-DD HH:mm:ss');
	var endDate = start.add(duration, "seconds");
	$("#updateEndTime").val(endDate.format("HH:mm:ss"));
	console.log("updateTimeAction:" + $("#updateStartTime").val() + " + "+ duration + " = " + endDate.format("HH:mm:ss"));
}

function updateStartTimeInit() {
	$("#updateStartTime").timepicker({
		onSelect : function(datetimeText, datepickerInstance) {
			console.log(datetimeText);
			console.log(datepickerInstance);
			updateTimeAction();
		},
		timeFormat : 'HH:mm:ss',
		timeInput : false,
		hourMin : 6,
		hourMax : 23,
		regional : 'zh-CN',
		addSliderAccess : true,
		sliderAccessArgs : {
			touchonly : false
		},
		showButtonPanel : false	// 是否显示按钮
	});
}

function openUpdateScheduleBox(icon) {

	$("#updateScheduleBox").modal("show");
	var scheduleID = $(icon).attr("data-id");
	var scheduleRange = $(icon).attr("data-scheduleRange");
	var weekday = $(icon).attr("data-weekday");
	console.log("openUpdateScheduleBox  scheduleID=" + scheduleID+ " scheduleRange=" + scheduleRange+" weekday="+weekday);
	getScheduleDetail(scheduleID);

	
}

$("#updateScheduleBtn").click(function() {
	var scheduleID = $("#updateScheduleID").val();
	var addStartTime = $("#updateStartTime").val();
	var addEndTime = $("#updateEndTime").val();
	var weekday = $("#updateScheduleWeekday").val();
	var scheduleTd = $("#scheduleBody td.scheduleTd[name='" + weekday + "']");
	var index = getIndex(addStartTime);
	var scheduleRange = $("#updateScheduleRange").val();
	// TODO 时间变化 结尾时间自动变化
	var conflict = findTimeConflict(scheduleID,scheduleTd,addStartTime, addEndTime);
	if (conflict) {// conflict false 表示时间冲突

		var deviceIDs = [];
		if (scheduleRange == "SCHEDULE_PART_DEVICE") {// 用户自选设备
			deviceIDs = getDeviceListBodyChoice(updateScheduleDeviceTable);

			console.log(" updateSchedule deviceIDs=" + deviceIDs);
			if (deviceIDs.length == 0) {
				alert("请选择一体机设备");
			} else {
				updateSchedule(index, addStartTime, addEndTime,
						scheduleID, scheduleRange, deviceIDs);
			}

		} else {
			updateSchedule(index, addStartTime, addEndTime, scheduleID,
					scheduleRange, deviceIDs);
		}

	} else {
		console.warn("时间冲突");
	}
});

function updateSchedule(index, addStartTime, addEndTime, scheduleID,
		scheduleRange, deviceIDs) {
	$.ajax({
		type : "post",
		dataType : "json",
		url : baseUrl + "/schedule/update",
		data : {
			act : "updateSchedule",
			index : index,
			startTime : addStartTime,
			endTime : addEndTime,
			scheduleID : scheduleID,
			scheduleRange : scheduleRange,
			deviceIDs : deviceIDs.toString()
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status + e + " "+ xhr.status);
			console.log(xhr.responseText);
			alert("updateSchedule 失败");
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
				//  更新schedule成功 刷新页面
				$("#updateScheduleBox").modal("hide");
				window.location.reload(true);
			}
		}
	});

}

// ---------------------------------修改计划任务-------------------------------------

// +++++++++++++++++++++++++++++++++删除计划任务++++++++++++++++++++++++++++++++++++++++++
$(function() {

	$("#deleteScheduleBtn").click(function() {
		var scheduleID = $("#updateScheduleID").val();
		deleteSchedule(scheduleID);
	});

});

function deleteSchedule(scheduleID) {
	$.ajax({
		type : "post",
		dataType : "json",
		url : baseUrl + "/schedule/delete",
		data : {
			scheduleID : scheduleID
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status + e + " "+ xhr.status);
			console.log(xhr.responseText);
			alert("deleteSchedule 失败");
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
				if(updateScheduleDeviceTable){
					updateScheduleDeviceTable.destroy();
				}
				$("#updateScheduleBox").modal("hide");
				// 更新schedule成功 刷新页面
				window.location.reload(true);
			}
		}
	});
}
// -------------------------------------删除计划任务-------------------------------------------------



//++++++++++++++++++++++++++++++++++++++复制前一天的计划++++++++++++++++++++++++++++++++++
$(function() {
	
	$("#weekTr span.copyFromBefore").on("dblclick",function() {
		var id = $(this).attr("id");
		var targetDay = $(this).attr("data-week");
		var sourceDay = targetDay - 1;
		var bln = window.confirm("确定要复制星期" + sourceDay + "的计划任务到星期"
				+ targetDay + "(已有计划将被替换)?");
		if (bln) {
			console.log("copy sourceDay=" + sourceDay
					+ " to targetDay=" + targetDay);
			var schemaID = $("#schemaID").val();
			copySchedule(sourceDay, targetDay, schemaID);

		}
	});
	
});

function copySchedule(sourceDay, targetDay, schemaID) {
	console.log(" page copySchedule schemaID= " + schemaID + "  sourceDay="
			+ sourceDay + "  targetDay=" + targetDay);
	$.ajax({
		type : "post",
		dataType : "json",
		url : baseUrl+"/schedule/copySchedule",
		data : {
			act : "copySchedule",
			sourceDay : sourceDay,
			targetDay : targetDay,
			schemaID : schemaID
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status + e + " "+ xhr.status);
			console.log(xhr.responseText);
			alert("copySchedule 复制计划任务失败");
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
				//  页面测试使用正式使用后删除复制假效果
				window.location.reload(true);
			}
		}
	});

}



//-------------------------------------复制前一天的计划--------------------------------------

