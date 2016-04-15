<%

local nixio = require "nixio"
local brxydispatcher = require "luci.tools.brxydispatcher"
local table = require "table"


local h = require "luci.http"


	
if h.formvalue('act') == 'queryMonitorDevice' then
	h.prepare_content("application/json")	
	local deviceID = h.formvalue('deviceID')	
	local deviceName = h.formvalue('deviceName')	
	local deviceVersion = h.formvalue('deviceVersion')	
	local deviceStatus = h.formvalue('deviceStatus')	
	local data = brxydispatcher.queryMonitorDevice(deviceID,deviceName,deviceVersion,deviceStatus)
	h.write_json({data=data})
	return
	
	
elseif h.formvalue('act') == 'getDeviceDetail' then
	h.prepare_content("application/json")
	local deviceID = h.formvalue('deviceID')	
	local data = brxydispatcher.getDeviceDetail(deviceID)
	h.write_json({data=data})
	return
	

elseif h.formvalue('act') == 'queryProgramList' then
	h.prepare_content("application/json")
	local data = brxydispatcher.queryProgramList()
	h.write_json({data=data})
	return	
	
elseif h.formvalue('act') == 'broadcast' then
	h.prepare_content("application/json")
	local deviceIDs = h.formvalue('deviceIDs')	
	local programID = h.formvalue('programID')	
	local re,msg = brxydispatcher.broadcast(deviceIDs,programID)
	h.write_json({result=re, msg=msg})
	return
		
elseif h.formvalue('act') == 'deleteDevice' then
	h.prepare_content("application/json")
	local deviceID = h.formvalue('deviceID')	
	local re,msg = brxydispatcher.deleteDevice(deviceID)
	h.write_json({result=re, msg=msg})
	return
	
elseif h.formvalue('act') == 'batchControllDevice' then
	h.prepare_content("application/json")
	local deviceIDs = h.formvalue('deviceIDs')	
	local operationCode = h.formvalue('operationCode')	
	local deviceType = h.formvalue('deviceType')	
	local re,msg = brxydispatcher.batchControllDevice(deviceType,operationCode,deviceIDs)
	h.write_json({result=re, msg=msg})
	return
	

elseif h.formvalue('act') == 'deviceControll' then
	h.prepare_content("application/json")
	local ID = h.formvalue('ID')	
	local operationCode = h.formvalue('operationCode')	
	local deviceType = h.formvalue('deviceType')	
	local operationValue = h.formvalue('operationValue')	
	local re,msg = brxydispatcher.deviceControll(deviceType,operationCode,ID,operationValue)
	h.write_json({result=re, msg=msg})
	return
	
	
	
end

%>


<%+header%>

<input type="hidden" id="baseUrl" value="<%=resource%>" />
<input type="hidden" id="loginUrl" value="<%=luci.dispatcher.build_url()%>" />
<link rel="stylesheet" type="text/css" href="<%=resource%>/jquery-ui/jquery-ui.min.css">
<!--style type="text/css">@import"<%=resource%>/jquery-ui/jquery-ui.min.css"</style-->
<link rel="stylesheet" type="text/css" href="<%=resource%>/bootstrap/bootstrap-switch/bootstrap-switch.min.css">
<!--style type="text/css">@import"<%=resource%>/bootstrap/bootstrap-switch/bootstrap-switch.min.css"</style-->
<style type="text/css">
	.container{
		max-width:90%;
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
<form id="aioConfigForm">

	<div class="cbi-map">
	
		<div id="tabs">
			<ul>
				<li><a href="#deviceMonitorPanel">设备监控</a></li>
				<li><a href="#deviceDetailPanel"><span id="deviceDetailTabTitle"> </span></a></li>
			</ul>
			<div id="deviceMonitorPanel">
				<table>
					<tr>
						<td colspan="6" class="tdRight">
							<input type="text" id="queryDeviceName" placeholder="设备名称" class="remInput">&nbsp;&nbsp;
							<input type="text" id="queryDeviceID" placeholder="设备编码" class="remInput">&nbsp;&nbsp;
							<select id="queryDeviceVersion">
								<option>全部版本</option>
								<option value="DTSK3A">DTSK3</option>
								<option value="DTSK3A">DTSK3A</option>
							</select>&nbsp;&nbsp;
							<!-- <input type="text" id="queryDeviceVersion" placeholder="设备版本" class="remInput"> -->
							<select id="queryDeviceStatus">
								<option>全部状态</option>
								<option value="ONLINE">在线</option>
								<option value="OFFLINE">离线</option>
							</select>&nbsp;&nbsp;
							<!-- <input type="text" id="queryDeviceStatus" placeholder="设备状态" class="remInput">&nbsp;&nbsp; -->
							<input type="button" id="queryBtn" value="查询" class="cbi-button cbi-button-find">
						</td>
					</tr>
					<tr>
						<td>选择</td>					
						<td>设备名称</td>
						<td>设备编号</td>
						<td>设备版本</td>
						<td>设备状态</td>
						<td>操作</td>
					</tr>
					<tbody id="deviceListBody">
	
					</tbody>
				</table>
				<table> 
					<tr>
						<td class="tdRight">
							<input type="button" value="开机" class="cbi-button" id="startDevice" >
							<input type="button" value="关机" class="cbi-button" id="shutdownDevice" >
							<input type="button" value="广播" class="cbi-button" id="boardcastBtn" >
						</td>
					</tr>
				</table>
			</div><!-- deviceMonitorPanel-->
			
			<div id="deviceDetailPanel">
				<table>
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
						<td>设备编码</td><td><span id="detailDeviceID">&nbsp;</span></td>
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
				<table>
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
						<td colspan="2">&nbsp;</td>
						<td>
							<input type="button" class="cbi-button cbi-button-link" value ="返回" id="closeDeviceDetailBtn">
						</td>
					</tr>
				</table>
			</div><!-- deviceDetailPanel-->
			
		</div><!-- ./tabs-->

		

	</div><!-- ./cbi-map-->

</form>

<div id="addBroadcastBox" style="display:none;">
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
		</table>
	</div>
<script type="text/javascript" src="<%=resource%>/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=resource%>/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=resource%>/bootstrap/bootstrap-switch/bootstrap-switch.min.js"></script>
<script type="text/javascript" src="<%=resource%>/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=resource%>/jwplayer/jwplayer.js"></script>
<script type="text/javascript" src="<%=resource%>/brxyTool.js"></script>
<!--script type="text/javascript" src="<%=resource%>/deviceMonitor.js"-->
<script type="text/javascript">

//+++++++++++++++++条件查询++++++++++++++++++++++++
$(function(){
	
	$("#queryDeviceID").blur(function(){
		if($(this).val()!=""){
			blurOrClickQueryDevice();
		}
	});
	
	$("#queryDeviceName").blur(function(){
		if($(this).val()!=""){
			blurOrClickQueryDevice();
		}
	});
	
	$("#queryDeviceVersion").change(function(){
		blurOrClickQueryDevice();
	});
	
	$("#queryDeviceStatus").change(function(){
		blurOrClickQueryDevice();
	});
	$("#queryBtn").click(function(){
		blurOrClickQueryDevice();
	});
	
	
});


function blurOrClickQueryDevice(){
	var deviceID = $("#queryDeviceID").val();
	var deviceName = $("#queryDeviceName").val();
	var deviceVersion = $("#queryDeviceVersion").val();
	var deviceStatus = $("#queryDeviceStatus").val();
	queryMonitorDevice(deviceID,deviceName,deviceVersion,deviceStatus);
}


//--------------------条件查询------------------------


//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++初始化页面数据++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


$(function(){

	$( "#tabs" ).tabs({ 		
 		activate:function(event,ui){
	       var tabIndex = ui.newPanel.selector;
	       //console.log(event);
	       //console.log(JSON.stringify(ui));
           console.log("actve tabIndex="+ui.newPanel.selector);
          if (tabIndex == "#deviceMonitorPanel") {
			 closeDetailTab();
           } else if (tabIndex == "#deviceDetailPanel") {
              //查询设备详情 ，每个设备的按钮触发
           }
 		}
 	});
 	
 	closeDetailTab();
 	//queryMonitorDevice();
 	//$("#tabs").tabs("option",'active',0); //初始化默认选择设备列表，并查询数据
 	//$("#tabs").tabs( "disable", 1 );
 	//$("#deviceDetailTabTitle").html("");
 	
 	   
	
	
	console.log("page init");
	  

});


function openDetailTab(){
 	$("#tabs").tabs( "enable", 1 );
	$("#tabs").tabs("option",'active',1); //初始化默认选择设备列表，并查询数据
 	$("#deviceDetailTabTitle").html("设备详情");

}

function closeDetailTab(){
	queryMonitorDevice();
	$("#tabs").tabs("option",'active',0); //初始化默认选择设备列表，并查询数据
 	$("#tabs").tabs( "disable", 1 );
 	$("#deviceDetailTabTitle").html("");
}

function queryMonitorDevice(deviceID,deviceName,deviceVersion,deviceStatus){
	console.log("luci page queryMonitorDevice ");
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
		data : {
			act : "queryMonitorDevice",
			deviceID:deviceID,
			deviceName:deviceName,
			deviceVersion:deviceVersion,
			deviceStatus:deviceStatus
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
			console.log(xhr.responseText);
			alert("queryActiveDevice数据失败");
		},
		beforeSend : function() {
			openWaitBox();
		},
		complete : function(XMLHttpRequest, textStatus) {
			closeWaitBox();
		},
		success : function(result) {
			deviceListInit(result.data);
		}
	});

}



function deviceListInit(data){
	console.info("deviceListInit 初始化数据到页面");
	var html = "";
	for ( var i in data) {
		var device = data[i];
		html += "<tr><td><input class='cbi-input-checkbox'  type='checkbox' id="+ device.deviceID+ " name='choiceDeviceCheckbox' value='1'></td><td>" 
		+ device.deviceName + "</td><td>" 
		+ device.deviceID + "</td><td>" 
		+ device.firmversion + "</td><td>" 
		+ device.deviceStatus	+ "</td><td>"
		+"<input type='button' name='checkDeviceDetail' value='详情' id='"+device.deviceID+"' class='cbi-button cbi-button-apply'>&nbsp;&nbsp;"
		+"<input type='button' name='deleteDevice' value='删除' id='"+device.deviceID+"' class='cbi-button cbi-button-remove'>&nbsp;&nbsp;"
		+"<input type='button' name='checkdeviceLogs' value='日志' id='"+device.deviceID+"' class='cbi-button cbi-button-download'>"
		+ "</td></tr>";		
	}
	if(html==""){
		html = "<tr><td colspan='6'>暂无设备数据</td></tr>";
	}
	$("#deviceListBody").html(html);
}



//++++++++++++++++++控制设备+++++++++++++++++++++++++++++++++
$(function(){

	$("#startDevice").click(function(){
			var operationCode = operation.start;
			
			batchControllDevice(operationCode);
	
	});
	
	$("#shutdownDevice").click(function(){
		var operationCode = operation.shutdown;
		batchControllDevice(operationCode);
	});
	
	$("#boardcastBtn").click(function(){
		openBroadcastBox();
	});
	
	
	
	
	
});


function openBroadcastBox(){
	queryProgramList();
	var deviceIDs = getDeviceListBodyChoice();
	if(deviceIDs.length==0){
		alert("请选择设备");
	}else{
	
		   $("#addBroadcastBox").dialog({
		        autoOpen : true,
		        height : 350,
		        width : 500,
		        title : "广播",
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
		        	text:"广播",
		            click : function() {
		            	var programID = $("#programSelect").val();
		            	broadcast(deviceIDs,programID);
		            }
		        }]
		    });
	}
}


function broadcast(deviceIDs,programID){	
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
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
				alert(result.msg);
				if(result.result){
					 $("#addBroadcastBox").dialog("close");
				}
		}
	});
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

function batchControllDevice(operationCode){
	var deviceTypeCode = deviceType.dtsHost;
	var deviceIDs = getDeviceListBodyChoice();
	if(deviceIDs.length==0){
		alert("请选择设备");
	}else{
	
		$.ajax({
			type : "post",
			dataType : "json",
			url : '<%=REQUEST_URI%>',
			data : {
				act : "batchControllDevice",
				deviceIDs:deviceIDs.toString(),
				deviceType:deviceTypeCode,
				operationCode:operationCode
			},
			error : function(xhr, status, e) {
				console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
				console.log(xhr.responseText);
				alert("batchControllDevice 批量控制设备失败 deviceTypeCode="+deviceTypeCode+"  operationCode="+operationCode+"  deviceIDs="+deviceIDs.toString() );
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
					queryMonitorDevice();
				}
			}
			
		});
	}
	

}

function getDeviceListBodyChoice(){
	var deviceIDs = []; 
		$("#deviceListBody input[type='checkbox'][name='choiceDeviceCheckbox']:checked").each(function(index,el){
			var deviceID = $(el).attr("id");
			deviceIDs.push(deviceID);
		});
		console.log("getDeviceListBodyChoice deviceIDs="+deviceIDs.toString());
		return deviceIDs;

}



/**
 * 控制设备函数
 * deviceTypeCode 设备类型
 * operationCode 设备操作类型
 * ID 操作设备ID  主要用于开关控制
 * operationValue 操作值  主要音量控制的值
 */
function deviceControll(deviceTypeCode, operationCode, ID,operationValue) {
	var controllInfo = "deviceControll: deviceType=" + deviceType + "  operationCode="
	+ operationCode+ "  ID=" + ID + "  operationValue=" + operationValue;
	console.log("device controll info = "+controllInfo);
	
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
		data : {
			act : "deviceControll",
			ID:ID,
			deviceType:deviceTypeCode,
			operationCode:operationCode,
			operationValue:operationValue
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
			console.log(xhr.responseText);
			alert("deviceControll 控制设备失败 deviceTypeCode="+deviceTypeCode+"  operationCode="+operationCode+"  ID="+ID+"  operationValue="+operationValue );
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
				//TODO 控制成功 改变状态
				console.log("后台返回控制成功，回调函数改变页面状态");
			}
		}
	});
	
	
}

//------------------------控制设备-----------------------------


// ++++++++++++++++++查看设备详情+++++++++++++++++++++++++++++++++
$(function(){

	$(document).on("click",	"input[type='button'][name='checkDeviceDetail']",function() {
		var deviceID = $(this).attr("id");
		console.info("check device detail deviceID="+deviceID);
		getDeviceDetail(deviceID);
	});
	
	
	$("#closeDeviceDetailBtn").click(function(){
		closeDetailTab();
	});
});


function getDeviceDetail(deviceID){
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
		data : {
			act : "getDeviceDetail",
			deviceID:deviceID
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
			console.log(xhr.responseText);
			alert("getDeviceDetail 数据失败 deviceID="+deviceID);
		},
		beforeSend : function() {
			openWaitBox();
		},
		complete : function(XMLHttpRequest, textStatus) {
			closeWaitBox();
		},
		success : function(result) {
			var deviceInfo = result.data;
			
			var dtsRFID = deviceInfo.dtsRFID;
			if(dtsRFID!=null){
				var status = dtsRFID.status;
				$("#detailRFID").html("RFID");
				if(status=="on"){
					$("#detailRFID").addClass("online").removeClass("offline");
				}else if(status=="off"){
					$("#detailRFID").addClass("offline").removeClass("online");
				}
				$("#detailRFID").attr("title",dtsRFID.identify);
			}else{
				$("#detailRFID").html("");
			}
			
			var dtsScreen = deviceInfo.dtsScreen;
			if(dtsScreen!=null){
				var status = dtsScreen.status;
				$("#detailScreen").html("SCREEN");
				$("#detailScreen").attr("title",dtsScreen.identify);
				if(status=="on"){
					$("#detailScreen").addClass("online").removeClass("offline");
				}else if(status=="off"){
					$("#detailScreen").addClass("offline").removeClass("online");
				}
			}else{
				$("#detailScreen").html("");
			}
			
			var dtsSensor = deviceInfo.dtsSensor;
			if(dtsSensor!=null){
				var status = dtsSensor.status;
				if(status=="on"){
					$("#temperature").html(dtsSensor.temperature+"℃");
					$("#humidness").html(dtsSensor.humidness+"%");
					$("#illumination").html(dtsSensor.illumination+"lm");
					$("#temperature").addClass("online").removeClass("offline");
					$("#humidness").addClass("online").removeClass("offline");
					$("#illumination").addClass("online").removeClass("offline");
				}else if(status=="off"){
					$("#temperature").addClass("offline").removeClass("online");
					$("#humidness").addClass("offline").removeClass("online");
					$("#illumination").addClass("offline").removeClass("online");
					$("#temperature").html("-");
					$("#humidness").html("-");
					$("#illumination").html("-");
				}
			}else{
				$("#temperature").html("");
				$("#humidness").html("");
				$("#illumination").html("");
			}
			
			$("#detailDeviceStatus").html(deviceInfo.deviceStatus);
			
			//基本信息
			$("#detailDeviceID").html(deviceInfo.deviceID);
			$("#detailDeviceName").html(deviceInfo.deviceName);
			$("#detailDeviceVersion").html(deviceInfo.deviceVersion);
			$("#detailRecordDate").html(deviceInfo.recordDate);
			
			
			var mediaURL = deviceInfo.mediaURL;
			jwplayer.key = "0L6vIHGbtCT5vpSbOllnYgdeAqmo4feWutwkZg==";
			if(mediaURL!=null){
				var mediaAInstance = jwplayer('mediaA');
				  mediaAInstance.setup({ 
				    file: mediaURL.mediaA,
				    autostart:true
				  });
				  
				  var mediaBInstance = jwplayer('mediaB');
				  mediaBInstance.setup({ 
				  	autostart:true, 
				    file: mediaURL.mediaB
				  });
				//$("#mediaA").html(mediaURL.mediaA);
				//$("#mediaB").html(mediaURL.mediaB);
			}
			
			//音量控制
			$( "#detailVolume" ).slider({
			 	min: 0,
      			max: 15,
				value:deviceInfo.dtsVolume,
				change:function(event,ui){
					console.log("slider change value = "+ui.value);
					//调用后台的change  volume value
					deviceTypeCode = deviceType.dtsVolume;
					operationCode = operation.adjust;
					deviceControll(deviceTypeCode, operationCode, null,ui.value);
				},
				start: function( event, ui ) {
					console.log("slider start value = "+ui.value);
				},
				stop: function( event, ui ) {
					console.log("slider stop value = "+ui.value);
				},
				slide: function( event, ui ) {
			        $( "#volumeValue" ).html( ui.value );
			      }
			});
			
		    $( "#volumeValue" ).html( $( "#detailVolume" ).slider( "value" ) );
			 

			
			
			var dtsPCStatus = false;
			if(deviceInfo.dtsPC.status=="on"){
				dtsPCStatus= true;
			}else if(deviceInfo.dtsPC.status=="off"){
				dtsPCStatus= false;
			}else{
				alert(" dtsPC status error :"+ deviceInfo.dtsPC.status);
			}
			$("#detailPC").bootstrapSwitch({
				state:dtsPCStatus
			});
			 $("#detailPC").on('switchChange.bootstrapSwitch', function(event, state) {
			 	var operationCode = state?operation.start:operation.shutdown;
			 	var deviceTypeCode = deviceType.dtsPC;
			 	console.log("detail PC state "+state+" operationCode="+operationCode +"  deviceTypeCode="+deviceTypeCode);
		        deviceControll(deviceTypeCode,operationCode,null,null);
		    });
						
			var dtsChannelStatus = false;
			if(deviceInfo.dtsChannel=="1"){
				dtsChannelStatus= true;
			}else if(deviceInfo.dtsChannel=="0"){
				dtsChannelStatus= false;
			}else{
				alert(" dtsChannel status error :"+ deviceInfo.dtsChannel);
			}
			$("#detailChannel").bootstrapSwitch({
				state:dtsChannelStatus
			});
			 $("#detailChannel").on('switchChange.bootstrapSwitch', function(event, state) {
		        deviceControll(deviceType.dtsChannel,state?operation.start:operation.shutdown,null,null);
		    });
			
			var dtsBoothtatus = false;
			if(deviceInfo.dtsBooth.status=="on"){
				dtsBoothtatus= true;
			}else if(deviceInfo.dtsBooth.status=="off"){
				dtsBoothtatus= false;
			}else{
				alert(" dtsBooth status error :"+ deviceInfo.dtsBooth.status);
			}
			$("#detailBooth").bootstrapSwitch({
				state:dtsBoothtatus
			});
			 $("#detailBooth").on('switchChange.bootstrapSwitch', function(event, state) {
		        deviceControll(deviceType.dtsBooth,state?operation.start:operation.shutdown,null,null);
		    });
		    
			
			var dtsDisplayerStatus = false;
			if(deviceInfo.dtsDisplayer.status=="on"){
				dtsDisplayerStatus= true;
			}else if(deviceInfo.dtsDisplayer.status=="off"){
				dtsDisplayerStatus= false;
			}else{
				alert(" dtsDisplayer status error :"+ deviceInfo.dtsDisplayer.status);
			}
			$("#detailDisplayer").bootstrapSwitch({
				state:dtsDisplayerStatus
			});
			
			$("#detailDisplayer").on('switchChange.bootstrapSwitch', function(event, state) {
		        deviceControll(deviceType.dtsDisplayer,state?operation.start:operation.shutdown,null,null);
		    });
	
			
			
			
			
			var switchHtml = "";
			var dtsSwitch = deviceInfo.dtsSwitch;
			if(dtsSwitch!=null){
				 $(dtsSwitch).each(function(index,mySwitch){  //循环取出教师有多少个开关面板
				 	var mac = mySwitch.mac;
				 	var name = mySwitch.name;
				 	var child = mySwitch.child;
				 	switchHtml+="<tr><td style='width:75%;'><span title='"+mac+"'>"+name+"</span></td>";
				 	$(child).each(function(index,children){  //循环取出开关面板里面的开关个数和详情
				 		var status = children.status;				 		
				 		var identify = children.identify;
					 	if(status=="on"){
				 			switchHtml+="<td class='tdRight'><div class='switch' title='"+identify+"'><input type='checkbox' id='"+identify+"' name='"+mac+"' checked/></div></td>";
					 	}else if(status=="off"){
					 		switchHtml+="<td class='tdRight'><div class='switch' title='"+identify+"'><input type='checkbox' id='"+identify+"' name='"+mac+"' /></div></td>";
					 	}else{
					 		alert("unkown switch status mac="+mac+" name="+name+" identify="+identify +" status ="+status);
					 	}				 		
				 	});	
				 	
				 
				 });				
			}else{
				switchHtml = "<tr><td>开关</td><td>-</td></tr>";
			}						
			
			$("#detailSwitchBody").html(switchHtml);
			$("#detailSwitchBody input[type='checkbox']").each(function(index,el){
				console.log($(el).attr("name"));
				$(el).bootstrapSwitch();
				$(el).on('switchChange.bootstrapSwitch', function(event, state) {
					var mac = $(el).attr("name");
					var identify = $(el).attr("id");
			        deviceControll(deviceType.dtsSwitch,state?operation.start:operation.shutdown,mac,identify);
			    });
			});
			//打开详情tab
			openDetailTab();
		}
	});

}
// ---------------------查看设备详情-------------------------------------



//++++++++++++++++++++++ 删除设备+++++++++++++++++++++++++++++++++++
$(function(){
	
	$(document).on("click",	"input[type='button'][name='deleteDevice']",function() {
		var deviceID = $(this).attr("id");
		console.info("check device detail deviceID="+deviceID);
		deleteDevice(deviceID);
	});
	
});


function deleteDevice(deviceID){
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
		data : {
			act : "deleteDevice",
			deviceID:deviceID
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
			console.log(xhr.responseText);
			alert("deleteDevice 删除设备失败 deviceID="+deviceID);
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
				queryMonitorDevice();
			}
		}
		
	});
	
}

//----------------删除设备----------------------------------------------

//++++++++++++++++++++++ 查看设备日志+++++++++++++++++++++++++++++++++++
$(function(){
	
	$(document).on("click",	"input[type='button'][name='checkdeviceLogs']",function() {
		var deviceID = $(this).attr("id");
		console.info("check device detail deviceID="+deviceID);
		checkdeviceLogs(deviceID);
	});
	
});


function checkdeviceLogs(deviceID){
	window.location.href=loginUrl+"/admin/server/swipeCardLog?deviceID="+deviceID;
	//alert("TODO 跳转页面查看设备的刷卡日志 deviceID="+deviceID);
}

//----------------查看设备日志----------------------------------------------

/**
 * 数字教学一体机类型 deviceType
 * 
 * DTS_HOST 数字教学一体机 – 主机 DTS_PC 数字教学一体机 – 内嵌 PC DTS_DISPLAYER 连接 DTS 的显示设备
 * DTS_SWITCH 开关 DTS_VOLUME 音量 DTS_RFID RFID读卡器 DTS_SENSOR 温度、湿度、亮度传感器
 * DTS_CHANNEL 显示通道 DTS_BOOTH 展台
 */
function DeviceType(){
    this.dtsHost = "DTS_HOST";  //教学一体机主机
    this.dtsPC = "DTS_PC";  //内置电脑
    this.dtsDisplayer = "DTS_DISPLAYER";  //外显
    this.dtsSwitch = "DTS_SWITCH";  //开关
    this.dtsVolume = "DTS_VOLUME";  //音量
    this.dtsRfid = "DTS_RFID";  //RFID刷卡器
    this.dtsSensor = "DTS_SENSOR";  //温度湿度亮度传感器
    this.dtsChannel = "DTS_CHANNEL";  //显示通道
    this.dtsBooth = "DTS_BOOTH";  //展台
    this.dtsScreen = "DTS_SCREEN"; //荧幕控制器 同步外显  打开外显同时打开荧幕控制器  ，关闭外显，关闭荧幕控制器
}
var deviceType = new DeviceType();


/**
 * 数字教学一体机操作 operationCode
 * 
 * START 打开设备 SHUTDOWN 关闭设备 RESTART 重启设备 ADJUST 设备在打开状态下 调节设备 READ 设备在打开状态下 读取值
 * ENABLE 启用设备 DISABLE 禁用设备
 */
function Operation(){
	this.start = "START";  //打开设备  主要针对 DTS_HOST、DTS_PC、DTS_DISPLAYER、DTS_SWITCH、DTS_BOOTH
	this.shutdown = "SHUTDOWN"; //关闭设备   主要针对 DTS_HOST、DTS_PC、DTS_DISPLAYER、DTS_SWITCH、DTS_BOOTH
	this.restart = "RESTART"; //重启设备
	this.adjust = "ADJUST"; //设备在打开状态下 调节设备  主要针对DTS_VOLUME DTS_CHANNEL
	this.read = "READ";  //设备在打开状态下 读取值
	this.enable = "ENABLE";  //启用设备
	this.disable = "DISABLE";  // 禁用设备
}
var operation = new Operation();


/**
 * 设备运行状态 deviceStatus 
 */
function DeviceStatus(){
	this.standby = "STANDBY";//待机状态
	this.working = "WORKING";//运行状态
	this.offLine = "OFFLINE";//离线状态
	this.inside = "0";//内显
	this.outside = "1";//外显
}
var deviceStatus = new DeviceStatus();

</script>

<%+footer%>