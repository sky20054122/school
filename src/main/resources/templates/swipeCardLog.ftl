<%

local nixio = require "nixio"
local brxydispatcher = require "luci.tools.brxydispatcher"
local table = require "table"


local h = require "luci.http"


	
if h.formvalue('act') == 'querySwipeCardLog' then
	h.prepare_content("application/json")
	local deviceID = h.formvalue('deviceID')
	local cardID = h.formvalue('cardID')
	local deviceName = h.formvalue('deviceName')
	local recordDate = h.formvalue('recordDate')
	local data = brxydispatcher.querySwipeCardLog(deviceID,cardID,deviceName,recordDate)
	h.write_json({data=data})
	return
	

	
end

%>


<%+header%>
<style type="text/css">

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
<input type="hidden" id="baseUrl" value="<%=resource%>" />
<input type="hidden" id="loginUrl" value="<%=luci.dispatcher.build_url()%>" />

<form id="aioConfigForm">

	<div class="cbi-map" id="cbi-unitmachine">
		
		
		<fieldset id="swipeCardLogList">
			<legend>
				刷卡日志
			</legend>
			<table>			
				<tr>				
					<td colspan="5" class="tdRight">
						<input type="text" id="queryCardID" placeholder="卡片ID" class="remInput">&nbsp;&nbsp;
						<input type="text" id="queryDeviceID" placeholder="设备编码" class="remInput">&nbsp;&nbsp;
						<input type="text" id="queryDeviceName" placeholder="设备名称" class="remInput">&nbsp;&nbsp;
						<input type="text" id="queryRecordDate" placeholder="刷卡日期" class="remInput">&nbsp;&nbsp;
						<input type="button" id="queryBtn" value="查询" class="cbi-button cbi-button-find">
					</td>
				</tr>	
				<tr>
					<td>卡片ID</td>		
					<td>设备ID</td>
					<td>设备名称</td>				
					<td>操作类型</td>
					<td>操作日期</td>
				</tr>
				<tbody id="swipeCardLogListBody">
					
				</tbody>
			</table>
		</fieldset>
		
	</div>

	
</form>
<script type="text/javascript" src="<%=resource%>/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=resource%>/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=resource%>/brxyTool.js"></script>
<script type="text/javascript">



//+++++++++++++++++条件查询++++++++++++++++++++++++
$(function(){
	
	$("#queryCardID").blur(function(){
		if($(this).val()!=""){
			blurOrClickQueryLog();
		}
	});
	
	$("#queryDeviceID").blur(function(){
		if($(this).val()!=""){
			blurOrClickQueryLog();
		}
	});
	
	$("#queryDeviceName").blur(function(){
		if($(this).val()!=""){
			blurOrClickQueryLog();
		}
	});
	
	$("#queryRecordDate").blur(function(){
		if($(this).val()!=""){
			blurOrClickQueryLog();
		}
	});
	$("#queryBtn").click(function(){
		blurOrClickQueryLog();
	});
	
	
});


function blurOrClickQueryLog(){
	var cardID = $("#queryCardID").val();
	var deviceID = $("#queryDeviceID").val();
	var deviceName = $("#queryDeviceName").val();
	var recordDate = $("#queryRecordDate").val();
	querySwipeCardLog(deviceID,cardID,deviceName,recordDate);
}


//--------------------条件查询------------------------







//+++++++++++++++++++++++++++++++++日志查询管理+++++++++++++++++++++++++++++++++++++++++
$(function(){	
	
	initSwipeCardLog();
});


//页面初始化查询  查询条件可以只有 deviceID或者cardID之一  ，其余查询条件为空
function initSwipeCardLog(){
	
	//TODO	获取时候包含deviceID 或者cardID
	var cardID = getUrlParam("cardID");
	var deviceID = getUrlParam("deviceID");
	if(cardID!=null){
		$("#queryCardID").val(cardID);
	}else{
		cardID = $("#queryCardID").val();
		
	} 
	if(deviceID!=null){		
		$("#queryDeviceID").val(deviceID);
	}else{
		deviceID = $("#queryDeviceID").val();
	}
	
	
	var deviceName = $("#queryDeviceName").val();
	var recordDate = $("#queryRecordDate").val();
	querySwipeCardLog(deviceID,cardID,deviceName,recordDate);
}



function querySwipeCardLog(deviceID,cardID,deviceName,recordDate){
	console.log("luci page querySwipeCardLog ");
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
		data : {
			act : "querySwipeCardLog",
			deviceID:deviceID,
			cardID:cardID,
			deviceName:deviceName,
			recordDate:recordDate
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
			console.log(xhr.responseText);
			alert("querySwipeCardLog 数据失败");
		},
		beforeSend : function() {
			openWaitBox();
		},
		complete : function(XMLHttpRequest, textStatus) {
			closeWaitBox();
		},
		success : function(result) {
			logListInit(result.data);
		}
	});

}

function logListInit(data){
	console.info("logListInit 初始化数据到页面");
	var html = "";
	for ( var i in data) {
		var log = data[i];
		html += "<tr><td>" + log.cardID 
				+ "</td><td>" + log.deviceID
				+"</td><td>" + log.deviceName
				+"</td><td>" + log.operationType
				+"</td><td>" + log.recordDate+"</td></tr>";
	}
	if(html==""){
		html = "<tr><td colspan='5'>暂无数据</td></tr>";
	}
	$("#swipeCardLogListBody").html(html);
}


//-----------------------------------日志查询管理--------------------------------------------

</script>

<%+footer%>