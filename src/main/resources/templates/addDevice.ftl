<%

local nixio = require "nixio"
local brxydispatcher = require "luci.tools.brxydispatcher"
local table = require "table"


local h = require "luci.http"


	
if h.formvalue('act') == 'getRFIDConfig' then
	h.prepare_content("application/json")
	local data = brxydispatcher.getRFIDConfig()
	h.write_json({data=data})
	return

	
elseif h.formvalue('act') == 'saveCard' then
	h.prepare_content("application/json")
	local addCardID = h.formvalue('addCardID')	
	local addCardUsername = h.formvalue('addCardUsername')
	local re,msg = brxydispatcher.saveCard(addCardID,addCardUsername)
	h.write_json({result=re, msg=msg})
	return
	
	
elseif h.formvalue('act') == 'readCard' then
	h.prepare_content("application/json")
	local re,msg,cardID = brxydispatcher.readCard()
	h.write_json({result=re, msg=msg,cardID=cardID})
	return
	
elseif h.formvalue('act') == 'activeDevice' then
	h.prepare_content("application/json")
	local activeDeviceID = h.formvalue('activeDeviceID')	
	local re,msg = brxydispatcher.activeDevice(activeDeviceID)
	h.write_json({result=re, msg=msg})
	return
	
elseif h.formvalue('act') == 'queryUnActiveDevice' then
	h.prepare_content("application/json")	
	local data = brxydispatcher.queryUnActiveDevice()
	h.write_json({data=data})
	return
	
elseif h.formvalue('act') == 'saveRFIDConfig' then
	h.prepare_content("application/json")	
	local RFIDWorkMode = h.formvalue('RFIDWorkMode')	
	local RFIDWorkTime = h.formvalue('RFIDWorkTime')
	
	local re,msg = brxydispatcher.saveRFIDConfig(RFIDWorkMode,RFIDWorkTime)
	h.write_json({result=re, msg=msg})
	return
	
end

%>


<%+header%>

<input type="hidden" id="baseUrl" value="<%=resource%>" />
<input type="hidden" id="loginUrl" value="<%=luci.dispatcher.build_url()%>" />

<form id="aioConfigForm">

	<div class="cbi-map" id="cbi-unitmachine">
		
		
		<fieldset id="deviceList">
			<legend>
				未激活设备管理
			</legend>
			<table>				
				<tr>
					<td>设备ID</td>
					<td>版本</td>		
					<td>名称</td>				
					<td>操作</td>
				</tr>
				<tbody id="deviceListBody">
					
				</tbody>
			</table>
		</fieldset>
		
	</div>

	
</form>
<script type="text/javascript" src="<%=resource%>/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=resource%>/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=resource%>/brxyTool.js"></script>
<script type="text/javascript">











//+++++++++++++++++++++++++++++++++RFID卡管理+++++++++++++++++++++++++++++++++++++++++
$(function(){

	queryUnActiveDevice();
	
	
	$(document).on("click",	"input[type='button'][name='activeDevice']",function() {
		var activeDeviceID = $(this).attr("id");
		console.info("active the device ID="+activeDeviceID);
		activeDevice(activeDeviceID);
	});
	
});







function queryUnActiveDevice(){
	console.log("luci page queryUnActiveDevice ");
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
		data : {
			act : "queryUnActiveDevice"
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
			console.log(xhr.responseText);
			alert("queryUnActiveDevice数据失败");
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
		html += "<tr><td>" + device.deviceID + "</td><td>" + device.firmversion
				+"<td>" + device.deviceName
				+ "</td><td><input type='button' name='activeDevice' id="
				+ device.deviceID
				+ " value='激活' class='cbi-button cbi-button-add'></td></tr>";
	}
	if(html==""){
		html = "<tr><td colspan='4'>暂无数据</td></tr>";
	}
	$("#deviceListBody").html(html);
}





function activeDevice(activeDeviceID){
	console.log("activeDevice");
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
		data : {
			act : "activeDevice",
			activeDeviceID:activeDeviceID			
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
			console.log(xhr.responseText);
			alert("激活失败");
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
				
			}
		}
	});
}

//-----------------------------------RFID卡管理--------------------------------------------

</script>

<%+footer%>