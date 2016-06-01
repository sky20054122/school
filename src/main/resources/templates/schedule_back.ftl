<%

local nixio = require "nixio"
local brxydispatcher = require "luci.tools.brxydispatcher"
local table = require "table"


local h = require "luci.http"


	
if h.formvalue('act') == 'querySchemaList' then
	h.prepare_content("application/json")
	local data = brxydispatcher.querySchemaList()
	h.write_json({data=data})
	return
	
elseif h.formvalue('act') == 'getSchemaDetail' then
	h.prepare_content("application/json")
	local schemaID = h.formvalue('schemaID')	
	local data = brxydispatcher.getSchemaDetail(schemaID)
	h.write_json({data=data})
	return

elseif h.formvalue('act') == 'queryProgramList' then
	h.prepare_content("application/json")
	local data = brxydispatcher.queryProgramList()
	h.write_json({data=data})
	return
	
elseif h.formvalue('act') == 'addSchema' then
	h.prepare_content("application/json")
	local schemaName = h.formvalue('schemaName')	
	local schemaComment = h.formvalue('schemaComment')
	local re,msg,schema = brxydispatcher.addSchema(schemaName,schemaComment)
	h.write_json({result=re, msg=msg,schema=schema})
	return
	
elseif h.formvalue('act') == 'deleteSchema' then
	h.prepare_content("application/json")
	local schemaID = h.formvalue('schemaID')	
	local re,msg = brxydispatcher.deleteSchema(schemaID)
	h.write_json({result=re, msg=msg})
	return
	
elseif h.formvalue('act') == 'enableSchema' then
	h.prepare_content("application/json")
	local schemaID = h.formvalue('schemaID')	
	local re,msg = brxydispatcher.enableSchema(schemaID)
	h.write_json({result=re, msg=msg})
	return
	
elseif h.formvalue('act') == 'disableSchema' then
	h.prepare_content("application/json")
	local schemaID = h.formvalue('schemaID')	
	local re,msg = brxydispatcher.disableSchema(schemaID)
	h.write_json({result=re, msg=msg})
	return	

elseif h.formvalue('act') == 'saveSchedule' then
	h.prepare_content("application/json")
	local index = h.formvalue('index')	
	local startTime = h.formvalue('startTime')
	local endTime = h.formvalue('endTime')
	local programID = h.formvalue('programID')
	local weekday = h.formvalue('weekday')
	local schemaID = h.formvalue('schemaID')	
	local re,msg = brxydispatcher.saveSchedule(index,startTime,endTime,programID,weekday,schemaID)
	h.write_json({result=re, msg=msg})
	return
	

	
elseif h.formvalue('act') == 'updateSchedule' then
	h.prepare_content("application/json")
	local index = h.formvalue('index')	
	local startTime = h.formvalue('startTime')
	local endTime = h.formvalue('endTime')
	local programID = h.formvalue('programID')
	local scheduleID = h.formvalue('scheduleID')	
	local re,msg = brxydispatcher.updateSchedule(index,startTime,endTime,programID,scheduleID)
	h.write_json({result=re, msg=msg})
	return
	
elseif h.formvalue('act') == 'copySchedule' then
	h.prepare_content("application/json")
	local sourceDay = h.formvalue('sourceDay')	
	local targetDay = h.formvalue('targetDay')
	local schemaID = h.formvalue('schemaID')		
	local re,msg = brxydispatcher.copySchedule(sourceDay,targetDay,schemaID)
	h.write_json({result=re, msg=msg})
	return
	

	
	
end

%>


<%+header%>

<input type="hidden" id="baseUrl" value="<%=resource%>" />
<input type="hidden" id="loginUrl" value="<%=luci.dispatcher.build_url()%>" />
<link rel="stylesheet" type="text/css" href="<%=resource%>/fullcalendar/fullcalendar.min.css">
<link rel="stylesheet" type="text/css" href="<%=resource%>/jquery-ui/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=resource%>/jquery-ui-timepicker/jquery-ui-timepicker-addon.min.css">

<!--style type="text/css">@import"<%=resource%>/jquery-ui/jquery-ui.min.css"</style-->
<style type="text/css">
	.container{
		max-width:90%;
	}
	table.scheduleTable{
		border: 1px solid #ddd;
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
	}
	
	.scheduleTd{
		min-height:25rem;
		height:25rem;
	}
	
	.scheduleDiv{
		width:100%;
		/*height:30px;
		line-height:30px;*/
		background:#3a87ad;
		border:1px solid #3a87ad;
		margin:2px 0;
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
</style>

<form id="aioConfigForm">

	<div class="cbi-map" id="cbi-unitmachine">
		<div id="tabs">
			<ul>
				<li><a href="#scheduleList">计划任务方案</a></li>
				<li><a href="#scheduleDetail"><span id="scheduleDetailTabTitle">&nbsp;</span></a></li>
			</ul>
			<div id="scheduleList">
				<table>
					<tr>
						<td colspan="6" style="text-align:right;">
							<input type="button" value="新增" id="addScheduleBtn" class="cbi-button cbi-button-add">
						</td>
					</tr>
					<tr>
						<td>名称</td>
						<td>建立时间</td>
						<td>修改时间</td>
						<td>备注</td>
						<td>状态</td>
						<td>操作</td>
					</tr>
					<tbody id="schemaListBody">
						
					</tbody>
				</table>
			</div><!--./scheduleList-->
			
			<div id="scheduleDetail">
				<input type="hidden" id="currentSchemaID">
				
				<table class="scheduleTable">
					<tr id="weekTr">				
						<th><span data-week="1" id="monday">星期一</span></th>
						<th><span data-week="2" class="copyFromBefore" title="双击复制前一日计划" id="tuesday">星期二</span></th>
						<th><span data-week="3" class="copyFromBefore" title="双击复制前一日计划" id="wednesday">星期三</span></th>
						<th><span data-week="4" class="copyFromBefore" title="双击复制前一日计划" id="thursday">星期四</span></th>
						<th><span data-week="5" class="copyFromBefore" title="双击复制前一日计划" id="friday">星期五</span></th>
						<th><span data-week="6" class="copyFromBefore" title="双击复制前一日计划" id="saturday">星期六</span></th>
						<th><span data-week="7" class="copyFromBefore" title="双击复制前一日计划" id="sunday">星期日</span></th>
					</tr>
					<tbody id="scheduleBody">
						<tr>					
							<td class="scheduleTd" data-week="1" id="mondayContent">&nbsp;</td>
							<td class="scheduleTd" data-week="2" id="tuesdayContent">&nbsp;</td>
							<td class="scheduleTd" data-week="3" id="wednesdayContent">&nbsp;</td>
							<td class="scheduleTd" data-week="4" id="thursdayContent">&nbsp;</td>
							<td class="scheduleTd" data-week="5" id="fridayContent">&nbsp;</td>
							<td class="scheduleTd" data-week="6" id="saturdayContent">&nbsp;</td>
							<td class="scheduleTd" data-week="7" id="sundayContent">&nbsp;</td>
						</tr>				
					</tbody>
				</table>	
				
			</div><!--./scheduleDetail-->
			
	
		</div><!-- ./tabs-->
		
	
	</div><!-- ./cbi-map-->
	
	<div id="addScheduleBox" style="display:none;">
		<table>
			<tr>
				<td>选择节目</td>
				<td>
					<select id="programSelect">
						<option value="铃声.mp3">铃声.mp3</option>
						<option value="广播体操.mp3">广播体操.mp3</option>
						<option value="升旗仪式.mp3">升旗仪式.mp3</option>
						<option value="眼保健操.mp3">眼保健操.mp3</option>
						<option value="套马杆.mp3">套马杆.mp3</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>开始时间</td>
				<td>
					<input type="text" id="addStartTime">
				</td>
			</tr>			
			<tr>
				<td>结束时间</td>
				<td>
					<input type="text" id="addEndTime" readonly value="09:00">
				</td>
			</tr>
		</table>
	</div>
	
	<div id="addSchedmaBox" style="display:none;">
		<table>
			<tr>
				<td>计划名称</td>
				<td><input type="text" id="addSchemaName" placeholder="请输入计划名称"></td>
			</tr>
			<tr>
				<td>备注信息</td>
				<td>
					<textarea id="addSchemaComment">&nbsp;</textarea>
				</td>
			</tr>
		</table>
	</div>

	
</form>
<script type="text/javascript" src="<%=resource%>/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=resource%>/fullcalendar/moment.min.js"></script>
<script type="text/javascript" src="<%=resource%>/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=resource%>/jquery-ui-timepicker/jquery-ui-timepicker-addon.min.js"></script>
<script type="text/javascript" src="<%=resource%>/jquery-ui-timepicker/jquery-ui-timepicker-zh-CN.js"></script>
<script type="text/javascript" src="<%=resource%>/fullcalendar/fullcalendar.min.js"></script>
<script type="text/javascript" src="<%=resource%>/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=resource%>/brxyTool.js"></script>
<script type="text/javascript">

//++++++++++++++++++++++++++查询计划任务列表++++++++++++++++++++++++++++

$(function(){
	$( "#tabs" ).tabs({ 		
 		activate:function(event,ui){
	       var tabIndex = ui.newPanel.selector;
	       //console.log(event);
	       //console.log(JSON.stringify(ui));
           console.log("actve tabIndex="+ui.newPanel.selector);
          if (tabIndex == "#scheduleList") {
			 closeDetailTab();
           } else if (tabIndex == "#scheduleDetail") {
              //查看计划任务方案 ，每个设备的按钮触发
           }
 		}
 	});
 	
 	closeDetailTab();
 	
 	$(document).on("click",	"#schemaListBody input[type='button'][name='delete']",function() {
		var schemaID = $(this).attr("id");
		console.info("delete schema schemaID="+schemaID);
		deleteSchema(schemaID);
	});
	
	
	$(document).on("click",	"#schemaListBody input[type='button'][name='enable']",function() {
		var schemaID = $(this).attr("id");
		console.info("enable schema  schemaID="+schemaID);
		enableSchema(schemaID);
	});
	
	$(document).on("click",	"#schemaListBody input[type='button'][name='disable']",function() {
		var schemaID = $(this).attr("id");
		console.info("disable schema  schemaID="+schemaID);
		disableSchema(schemaID);
	});
	
});

function openDetailTab(title){
 	$("#tabs").tabs( "enable", 1 );
	$("#tabs").tabs("option",'active',1); //初始化默认选择设备列表，并查询数据
 	$("#scheduleDetailTabTitle").html(title);

}

function closeDetailTab(){
	//查询计划任务方案
	querySchemaList();
	$("#tabs").tabs("option",'active',0); //初始化默认选择设备列表，并查询数据
 	$("#tabs").tabs( "disable", 1 );
 	$("#scheduleDetailTabTitle").html("");
}



function querySchemaList(){
	console.log("luci page querySchemaList ");
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
		data : {
			act : "querySchemaList"
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
			console.log(xhr.responseText);
			alert("querySchemaList 查询失败");
		},
		beforeSend : function() {
			openWaitBox();
		},
		complete : function(XMLHttpRequest, textStatus) {
			closeWaitBox();
		},
		success : function(result) {
			var html = "";
			var schedules = result.data;
			$(schedules).each(function(index,schedule){
				var status = schedule.status; //0表示禁用  1表示启用，有且只有一个
				html+="<tr><td>"+schedule.name+"</td><td>"
				+schedule.recordDate+"</td><td>"
				+schedule.updateDate+"</td><td>"
				+schedule.comments+"</td>";
				if(status=="0"){ //以停用的计划任务  可以编辑和删除
					html += "<td>备用计划</td><td>"
					+"<input type='button' value='修改' title='查看和修改' class='cbi-button cbi-button-edit' name='update' id='"+schedule.id+"'>&nbsp;"
					+"<input type='button' value='删除' class='cbi-button cbi-button-edit' name='delete' id='"+schedule.id+"'>&nbsp;"
					+"<input type='button' value='启用' class='cbi-button cbi-button-edit' name='enable' id='"+schedule.id+"'></td></tr>";
				}else if(status=="1"){  //已启用的计划任务 ，可以禁用，编辑
					html += "<td>当前计划</td><td>"
					//+"<input type='button' value='修改' title='查看和修改' class='cbi-button cbi-button-edit' name='update' id='"+schedule.id+"'>&nbsp;"
					+"<input type='button' value='禁用' class='cbi-button cbi-button-edit' name='disable' id='"+schedule.id+"'></td></tr>";
				}else{
					console.error("querySchemaList schedule status error(status must in 0/1) . status="+status);
					return ;
				}
				$("#schemaListBody").html(html);
			});
		}
	});

}


//-----------------------查询计划任务列表------------------------------------------


//+++++++++++++++++++++++++++新增计划任务+++++++++++++++++++++++
$(function(){
	$("#addScheduleBtn").click(function(){
		
		openAddSchemaBox();
		//TODO 跳转到新增整体方案页面，新增成功后，添加计划
		
	});
	
	
	
});

function openAddSchemaBox() {
    $("#addSchedmaBox").dialog({
        autoOpen : true,
        height : 350,
        width : 500,
        title : "新增计划方案",
        modal : true,
        //position : "center",
        draggable : true,
        resizable : true,
        closeOnEscape : true, // esc退出
        buttons :[ {
        	text:"返回",
            click : function() {
                $(this).dialog("close");
            },
        },{
        	text:"保存",
            click : function() {       
            
            	var addSchemaName = $("#addSchemaName").val();
            	var addSchemaComment = $("#addSchemaComment").val();   
            	addSchema(addSchemaName,addSchemaComment);
            	
            }
        }]
    });
}

function addSchema(addSchemaName,addSchemaComment){
	console.log("luci page addSchema ");
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
		data : {
			act : "addSchema",
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
				alert(result.msg);
				if(result.result){
					openDetailTab("新增方案");
					$("#scheduleBody td.scheduleTd").each(function(i,name){
						$(name).html("");						
					});
					$("#currentSchemaID").val(result.schema.id);  	
	                $("#addSchedmaBox").dialog("close");
				}
		}
	});
}



function deleteSchema(schemaID){
	console.log("luci page deleteSchema ");
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
		data : {
			act : "deleteSchema",
			schemaID:schemaID
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
			console.log(xhr.responseText);
			alert("deleteSchema 删除计划任务失败");
		},
		beforeSend : function() {
			openWaitBox();
		},
		complete : function(XMLHttpRequest, textStatus) {
			closeWaitBox();
		},
		success : function(result) {
				alert(result.msg);
				if(result.result){
					querySchemaList();
				}
		}
	});
}


function enableSchema(schemaID){
	console.log("luci page enableSchema ");
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
		data : {
			act : "enableSchema",
			schemaID:schemaID
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
			console.log(xhr.responseText);
			alert("enableSchema 启用计划任务失败");
		},
		beforeSend : function() {
			openWaitBox();
		},
		complete : function(XMLHttpRequest, textStatus) {
			closeWaitBox();
		},
		success : function(result) {
				alert(result.msg);
				if(result.result){
					querySchemaList();
				}
		}
	});
}

function disableSchema(schemaID){
	console.log("luci page disableSchema ");
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
		data : {
			act : "disableSchema",
			schemaID:schemaID
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
			console.log(xhr.responseText);
			alert("disableSchema 禁用计划任务失败");
		},
		beforeSend : function() {
			openWaitBox();
		},
		complete : function(XMLHttpRequest, textStatus) {
			closeWaitBox();
		},
		success : function(result) {
				alert(result.msg);
				if(result.result){
					querySchemaList();
				}
		}
	});
}
//-----------------------------新增计划任务--------------------




$(function(){

	$(document).on("click",	"#schemaListBody input[type='button'][name='update']",function() {
		var schemaID = $(this).attr("id");
		console.info("check schema detail schemaID="+schemaID);
		getSchemaDetail(schemaID);
	});
	
	
	
	
	
	
	var scheduleDiv = "<div class='scheduleDiv'>06:00-06:40  铃</div>";
	$("#scheduleBody td.scheduleTd").on("dblclick",function(event){
		var targetClassName = event.target.className;
		if(targetClassName=="scheduleTd"){ //新增
			openAddScheduleBox(event.target);
			console.log("scheduleTd dblclick add schedule");		
		}else{		
			console.log(targetClassName+" dblclick on schedule");
			//openUpdateScheduleBox(event.target);
        	event.stopPropagation();
			event.cancelBubble = true;
			return false;
		}; 
	});
	
	
	$("#weekTr span.copyFromBefore").on("dblclick",function(){
		var id = $(this).attr("id");
		var targetDay = $(this).attr("data-week");
		var sourceDay = targetDay-1;
		var bln = window.confirm("确定要复制星期"+sourceDay+"的计划任务到星期"+targetDay+"(已有计划将被替换)?"); 
		if(bln){			
			console.log("copy sourceDay="+sourceDay+" to targetDay="+targetDay);
			var schemaID = $("#currentSchemaID").val();
			copySchedule(sourceDay,targetDay,schemaID);
					
		}
	});
	
	$('#addStartTime').val("12:00:00");
	$("#addStartTime").timepicker({
		onSelect:function(datetimeText, datepickerInstance){
			console.log(datetimeText);
			console.log(datepickerInstance);			
			addTimeAction();
		},
		timeFormat: 'HH:mm:ss',
		hourMin: 6, 
		hourMax: 23,
		regional:'zh-CN',
		showButtonPanel:false ,//是否显示按钮
	});	
	
	
	
	$("#programSelect").on("change",function(){
		
		addTimeAction();
	});

	$("span.ui-icon-newwin").on("click",function(){
		alert("icon click");
	
	});
	
});


function copySchedule(sourceDay,targetDay,schemaID){
	console.log("luci page copySchedule schemaID= "+schemaID+"  sourceDay="+sourceDay+"  targetDay="+targetDay);
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
		data : {
			act : "copySchedule",
			sourceDay:sourceDay,
			targetDay:targetDay,
			schemaID:schemaID
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
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
			alert(result.msg);
			if(result.result){
				getSchemaDetail(schemaID);
				//TODO 页面测试使用正式使用后删除复制假效果 
				//var source,target = getSourceAndTarget(id);	
			}
		}
	});
	
}


function getSchemaDetail(schemaID){
	openDetailTab("计划详情");
	console.log("luci page getSchemaDetail schemaID= "+schemaID);
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
		data : {
			act : "getSchemaDetail",
			schemaID:schemaID
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
			console.log(xhr.responseText);
			alert("getSchemaDetail 查询方案详情失败");
		},
		beforeSend : function() {
			openWaitBox();
		},
		complete : function(XMLHttpRequest, textStatus) {
			closeWaitBox();
		},
		success : function(result) {
			$("#scheduleBody td.scheduleTd").each(function(i,name){
				$(name).html("");
				
			});
			var schema = result.data;
			$(schema.week).each(function(index,week){
				var scheduleTd = null;
				$("#scheduleBody td.scheduleTd").each(function(i,name){
					if($(name).attr("data-week")==week.weekday){
						scheduleTd = $(name);
					}
				});
				console.log("weekday = "+week.weekday);
				$(week.schedules).each(function(i,schedule){
					addScheduleToPage (scheduleTd,schedule);
					console.log(schedule.startTime+" -- "+schedule.endTime+"  "+schedule.fileName+"  "+schedule.programID+"  "+schedule.id);
				});
			});
			
			$("#currentSchemaID").val(schemaID);
			
		}
	});
}

function addScheduleToPage (scheduleTd,schedule){	
	var html = "<div class='scheduleDiv' index='"+schedule.index+"'><span class='startTime'>"
	+schedule.startTime+"</span><span>—</span><span class='endTime'>"
	+schedule.endTime+"</span><span class='program'>&nbsp;"
	+schedule.fileName+"</span><span class='ui-icon ui-icon-newwin' data-startTime='"+schedule.startTime+"' data-id='"+schedule.id+"' data-endTime='"+schedule.endTime+"' data-program='"+schedule.programID+"' onclick='openUpdateScheduleBox(this)'></span></div>";
	
    $(scheduleTd).append(html);
    sortSchedule($(scheduleTd));
}




function addTimeAction(interval){
	//var startTime = $("#addStartTime").val();
	//var times = startTime.split(":");
	//var start = parseFloat(times[0],10)*60+parseFloat(times[1],10)+parseFloat(interval,10);
	//var hour = Math.floor(start/60);
	//var endTime = hour<10?"0"+hour:hour;
	//endTime+=":";
	//var minute = start%60;
	//endTime+= minute<10?"0"+minute:minute;	
	
	//$("#addEndTime").val(endTime);
	
	var durationText = $("#programSelect").find("option:selected").attr("data-duration");
    var start = moment("2016-03-20 "+$("#addStartTime").val(),'YYYY-MM-DD HH:mm:ss');
    var duration = moment.duration(durationText);
    var endDate = start.add(duration);
    $("#addEndTime").val(endDate.format("HH:mm:ss"));
	console.log("addTimeAction:"+$("#addStartTime").val()+" + "+durationText+" = "+endDate.format("HH:mm:ss"));
}

function getSourceAndTarget(id){
	console.log("click day is "+id);
	var source = "";
	var target = "";
	if(id=="tuesday"){
		source = $("#mondayContent");
		target = $("#tuesdayContent");
	}else if(id=="wednesday"){
		source = $("#tuesdayContent");
		target = $("#wednesdayContent");
	}else if(id=="thursday"){
		source = $("#wednesdayContent");
		target = $("#thursdayContent");
	}else if(id=="friday"){
		source = $("#thursdayContent");
		target = $("#fridayContent");
	}else if(id=="saturday"){
		source = $("#fridayContent");
		target = $("#saturdayContent");
	}else if(id=="sunday"){
		source = $("#saturdayContent");
		target = $("#sundayContent");
	}
	$(target).html($(source).html());
	//return {source:source,target:source};
}


function queryProgramList(){
	console.log("luci page queryProgramList ");
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
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
				var html = "";
				$(result.data).each(function(index,file){
					html+="<option value='"+file.id+"' data-duration='"+file.duration+"' data-name='"+file.name+"'>"+file.name+"</option>";
				});
				$("#programSelect").html(html);
		}
	});
}

function openAddScheduleBox(scheduleTd) {
	queryProgramList();	
	$("#programSelect").attr("disabled",false);
    $("#addScheduleBox").dialog({
        autoOpen : true,
        height : 350,
        width : 500,
        title : "新增计划任务",
        modal : true,
        //position : "center",
        draggable : true,
        resizable : true,
        closeOnEscape : true, // esc退出
        buttons :[ {
        	text:"返回",
            click : function() {
                $(this).dialog("close");
            },
        },{
        	text:"保存",
            click : function() {
            	console.warn("click save btn schedule");
            	var addStartTime = $("#addStartTime").val();
            	var addEndTime = $("#addEndTime").val();
            	var programSelectID = $("#programSelect").val();
            	var selectFileName = $("#programSelect").find("option:selected").attr("data-name");
            	var index = getIndex(addStartTime);
            	var weekday = $(scheduleTd).attr("data-week");
            	//var html = "<div class='scheduleDiv' index='"+index+"'><span class='startTime'>"
            	//+addStartTime+"</span><span>—</span><span class='endTime'>"
            	//+addEndTime+"</span><span class='program'>&nbsp;"
            	//+selectFileName+"</span><span class='ui-icon ui-icon-newwin' data-startTime='"+addStartTime+"' data-endTime='"+addEndTime+"' data-program='"+programSelectID+"' onclick='openUpdateScheduleBox(this)'></span></div>";
            	
               // $(scheduleTd).append(html);
               // sortSchedule($(scheduleTd));
               var conflict = findTimeConflict(scheduleTd,addStartTime,addEndTime);
               if(conflict){ //false 表示时间冲突
	               saveSchedule(index,addStartTime,addEndTime,programSelectID,weekday);
	                $(this).dialog("close");
               }
            }
        }]
    });
}


/**
 * 已知多个时间段 startTime -- endTime
 * 要求 新加的addStartTime--addEndTime在已知时间段之外   
 * 即：addStartTime>endTime 或者 addEndTime<startTime满足要求
 * 返回 false表示不满足条件
 */
function findTimeConflict(scheduleTd,addStartTime,addEndTime){
	 var flag =false;
	$(scheduleTd).find("div.scheduleDiv").each(function(i,scheduleDiv){
		var icon = $(scheduleDiv).find("span.ui-icon-newwin");
		var startTime = $(icon).attr("data-startTime");
		var endTime = $(icon).attr("data-endTime");
		var as = moment.duration(addStartTime).asSeconds();
		var ae = moment.duration(addEndTime).asSeconds();
		
		var is = moment.duration(startTime).asSeconds();
		var ie = moment.duration(endTime).asSeconds();
		console.log("new schedule time : "+addStartTime+"--"+addEndTime+" exsist schedule time : "+startTime+"--"+endTime);
		if(ae<is||as>ie){
			flag = true;
		}else{
			flag = false;
			console.warn("new time("+as+"-"+ae+") is conflict with exist time("+is+"-"+ie+")");
			alert("新增时间("+addStartTime+"-"+addEndTime+") 和已知计划任务时间 ("+startTime+"-"+endTime+")冲突");
			return flag;
		}
	});
	return flag;
	
}


function saveSchedule(index,addStartTime,addEndTime,programSelectID,weekday){
	var schemaID = $("#currentSchemaID").val();
	console.log("luci page saveSchedule index="+index+" addStartTime="+addStartTime+" addEndTime="+addEndTime+" programSelectID="+programSelectID+" weekday="+weekday+"  schemaID="+schemaID);
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
		data : {
			act : "saveSchedule",
			index:index,
			startTime:addStartTime,
			endTime:addEndTime,
			programID:programSelectID,
			weekday:weekday,
			schemaID:schemaID
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
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
				alert(result.msg);
				if(result.result){
					//TODO 保存成功 刷新页面
					getSchemaDetail(schemaID);
				}
		}
	});

}

function sortSchedule(ic){
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

function getIndex(addStartTime){
	//var times = addStartTime.split(":");
	//var result = parseFloat(times[0], 10)*6+times[1];
	var index = moment.duration(addStartTime).asSeconds();
	console.log("getIndex addStartTime"+addStartTime+" index="+index);
	return index;
}



function openUpdateScheduleBox(icon){
	queryProgramList();
	var startTime = $(icon).attr("data-startTime");
	var endTime = $(icon).attr("data-endTime");
	var programID = $(icon).attr("data-program");
	var scheduleID = $(icon).attr("data-id");
	console.log("update schedule startTime="+startTime+" endTime="+endTime +"  programID="+programID+" scheduleID="+scheduleID);
	$("#addStartTime").val(startTime);
	$("#addEndTime").val(endTime);
	$("#programSelect").val(programID);
	$("#programSelect").attr("disabled",true);
	$("#addScheduleBox").dialog({
        autoOpen : true,
        height : 350,
        width : 500,
        title : "更新计划任务",
        modal : true,
        //position : "center",
        draggable : true,
        resizable : true,
        closeOnEscape : true, // esc退出
        buttons :[ {
        	text:"返回",
            click : function() {
                $(this).dialog("close");
            },
        },{
        	text:"删除",
            click : function() {
            	$(icon).parent().remove();
                $(this).dialog("close");
            },
        },{
        	text:"更新",
            click : function() {
            	console.warn("click update schedule btn");
            	//var addStartTime = $("#addStartTime").val();
            	//var addEndTime = $("#addEndTime").val();
            	//var programSelect = $("#programSelect").val();    
            	//$(icon).attr("data-startTime",addStartTime);
				//$(icon).attr("data-endTime",addEndTime);
				//$(icon).attr("data-program",programSelect);      	
               // $(icon).parent().find("span.startTime").html(addStartTime);
				//$(icon).parent().find("span.endTime").html(addEndTime);
				//$(icon).parent().find("span.program").html(programSelect);
				
				var addStartTime = $("#addStartTime").val();
            	var addEndTime = $("#addEndTime").val();
            	var programSelectID = $("#programSelect").val();
            	var selectFileName = $("#programSelect").find("option:selected").attr("data-name");
            	var index = getIndex(addStartTime); 
            	
            	 var conflict = findTimeConflict($(icon).parent().parent(),addStartTime,addEndTime);
                 if(conflict){ // conflict false 表示时间冲突
					updateSchedule(index,addStartTime,addEndTime,programSelectID,scheduleID);					
	                $(this).dialog("close");
                 }
            }
        }]
    });
}

function updateSchedule(index,addStartTime,addEndTime,programSelectID,scheduleID){
	var schemaID = $("#currentSchemaID").val();
	console.log("luci page updateSchedule ");
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
		data : {
			act : "updateSchedule",
			index:index,
			startTime:addStartTime,
			endTime:addEndTime,
			programID:programSelectID,
			scheduleID:scheduleID
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
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
				alert(result.msg);
				if(result.result){
					//TODO 更新schedule成功 刷新页面
					getSchemaDetail(schemaID);
				}
		}
	});

}

//+++++++++++++++++++++++++++++++++RFID卡管理+++++++++++++++++++++++++++++++++++++++++



</script>

<%+footer%>