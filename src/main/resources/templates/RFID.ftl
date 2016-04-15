<%

local nixio = require "nixio"
local brxydispatcher = require "luci.tools.brxydispatcher"
local table = require "table"


local h = require "luci.http"


	
if h.formvalue('act') == 'queryActiveDevice' then
	h.prepare_content("application/json")	
	local data = brxydispatcher.queryActiveDevice()
	h.write_json({data=data})
	return
	
	
elseif h.formvalue('act') == 'saveCard' then
	h.prepare_content("application/json")
	local addCardID = h.formvalue('addCardID')	
	local addCardUsername = h.formvalue('addCardUsername')
	local deviceIDs = h.formvalue('deviceIDs')
	local re,msg = brxydispatcher.saveCard(addCardID,addCardUsername,deviceIDs)
	h.write_json({result=re, msg=msg})
	return
	
	
	
elseif h.formvalue('act') == 'deleteCard' then
	h.prepare_content("application/json")
	local deleteCardID = h.formvalue('deleteCardID')	
	local re,msg = brxydispatcher.deleteCard(deleteCardID)
	h.write_json({result=re, msg=msg})
	return
	
elseif h.formvalue('act') == 'queryRFIDCard' then
	h.prepare_content("application/json")	
	local data = brxydispatcher.queryRFIDCard()
	h.write_json({data=data})
	return
	
	
elseif h.formvalue('act') == 'getRFIDDetail' then
	h.prepare_content("application/json")	
	local cardID = h.formvalue('cardID')	
	local data = brxydispatcher.getRFIDDetail(cardID)
	h.write_json({data=data})
	return

	
end

%>


<%+header%>

<input type="hidden" id="baseUrl" value="<%=resource%>" />
<input type="hidden" id="loginUrl" value="<%=luci.dispatcher.build_url()%>" />

<form id="aioConfigForm">

	<div class="cbi-map" id="cbi-unitmachine">
		<!-- RFID列表 -->
		<fieldset id="RFIDTable">
			<legend>
				RFID卡管理
			</legend>
			<table>
				<tr>
					<td colspan="5" style="text-align:right;">
					<input type="button" id="addRFIDCardBtn" value="新增RFID卡" class="cbi-button cbi-button-add">
					</td>
				</tr>
				<tr>
					<td>卡号</td>
					<td>用户姓名</td>
					<td>电话</td>
					<td>开卡日期</td>
					<td>操作</td>
				</tr>
				<tbody id="cardListBody">

				</tbody>
			</table>
		</fieldset>

		<!-- 新增RFID和设备列表 -->
		<fieldset id="addCardTable" style="display:none;">
			<legend>
				新增RFID关联设备
			</legend>
			<table >
				<tr>
					<td colspan="2">
					<input type="text" placeholder="请输入卡号" id="addCardID">
					</td>
					<td colspan="2">
					<input type="text" placeholder="请输入姓名" id="addCardUsername">
					</td>
				</tr>
				<tbody id="deviceBody">

				</tbody>
				<tr>
					<td colspan="3">&nbsp;</td>
					<td>
					<input type="button" id="returnBtn" value="返回" class="cbi-button cbi-button-link">
					<input type="button" id="saveCard" value="开卡" class="cbi-button cbi-button-add">
					</td>
				</tr>
			</table>
		</fieldset>

		<!-- 查看RFID详情 -->
		<div id="RFIDDetailTable" style="display:none;">
			<fieldset >
				<legend>
					RFID卡基本信息
				</legend>
				<table>
					<tr>
						<td>卡号</td>
						<td><span id="detailCardID">&nbsp;</span></td>
						<td>用户姓名</td>
						<td><span id="detailUsername">&nbsp;</span></td>
					</tr>
					<tr>
						<td>电话</td>
						<td><span id="detailPhone">&nbsp;</span></td>
						<td>开卡日期</td>
						<td><span id="detailRecordDate">&nbsp;</span></td>
					</tr>
				</table>
			</fieldset>

			<fieldset >
				<legend>
					RFID管理设备
				</legend>
				<table>
					<tr>
						<td>设备编码</td><td>设备名称</td><td>设备版本</td>
					</tr>
					<tbody id="RFIDDevicesBody">

					</tbody>
				</table>
			</fieldset>

		
			<table>
				<tr>					
					<td style="text-align:right;">
						<input type="button" id="RFIDDetailReturnBtn" value="返回" class="cbi-button cbi-button-link">
					</td>
				</tr>
			</table>
		</div>

	</div>

</form>
<script type="text/javascript" src="<%=resource%>/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=resource%>/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=resource%>/brxyTool.js"></script>
<script type="text/javascript">

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++初始化页面数据++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

$(function(){
	
	queryRFIDCard();

});

function queryRFIDCard(){
	console.log("luci page queryRFIDCard ");
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
		data : {
			act : "queryRFIDCard"
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
			console.log(xhr.responseText);
			alert("queryRFIDCard数据失败");
		},
		beforeSend : function() {
			openWaitBox();
		},
		complete : function(XMLHttpRequest, textStatus) {
			closeWaitBox();
		},
		success : function(result) {
			cardListInit(result.data);
		}
	});

}

function cardListInit(data){
	console.info("dataInit 初始化数据到页面");
	var html = "";
	for ( var i in data) {
		var card = data[i];
		html += "<tr><td>" + card.cardID 
		+ "</td><td>" + card.username	
		+ "</td><td>" + card.phone 
		+ "</td><td>" + card.recordDate 
		+ "</td><td>"
		+"<input type='button' title='RFID卡信息,管理设备,刷卡记录' name='checkManageDevice' id="+ card.cardID	+ " value='详情' class='cbi-button cbi-button-apply'>&nbsp;&nbsp;"
		+"<input type='button' name='deleteCard' id="+ card.cardID	+ " value='删除' class='cbi-button cbi-button-remove'>&nbsp;&nbsp;"
		+"<input type='button' name='checkRFIDLog' id="+ card.cardID	+ " value='日志' class='cbi-button cbi-button-link'>&nbsp;&nbsp;"
		+"</td></tr>";
	}
	if(html==""){
		html = "<tr><td colspan='4'>暂无数据</td></tr>";
	}
	$("#cardListBody").html(html);
}


function deleteCard(deleteCardID){
	console.log("deleteCard");
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
		data : {
			act : "deleteCard",
			deleteCardID:deleteCardID			
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
			console.log(xhr.responseText);
			alert("删除失败");
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
				queryRFIDCard();
			}
		}
	});
}


//++++++++++++++++++++++ 查看RFID的刷卡日志+++++++++++++++++++++++++++++++++++
$(function(){
	
		
	$(document).on("click",	"input[type='button'][name='checkRFIDLog']",function() {
		var cardID = $(this).attr("id");
		console.info("checkRFIDLog RFID card cardID="+cardID);
		checkRFIDLogs(cardID);
	});
	
});


function checkRFIDLogs(cardID){
	window.location.href=loginUrl+"/admin/server/swipeCardLog?cardID="+cardID;
	//alert("TODO 跳转页面查看设备的刷卡日志 deviceID="+deviceID);
}

//----------------查看RFID的刷卡日志----------------------------------------------

//++++++++++++++++++++++++新增RFID card++++++++++++++++++++++++++++
$(function(){

	$("#addRFIDCardBtn").click(function(){
		$("#RFIDTable").hide();
		$("#addCardTable").show();
		$("#RFIDDetailTable").hide();
		queryActiveDevice();
	});
});




function queryActiveDevice(){
	console.log("luci page queryActiveDevice ");
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
		data : {
			act : "queryActiveDevice"
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
		+ device.deviceID + "</td><td>" 
		+ device.firmversion + "</td><td>" 
		+ device.deviceName	+ "</td></tr>";	
	}
	if(html==""){
		html = "<tr><td colspan='4'>暂无设备数据</td></tr>";
	}
	$("#deviceBody").html(html);
}





//+++++++++++++++++++++++++++++++++RFID卡管理+++++++++++++++++++++++++++++++++++++++++
$(function(){

	$("#saveCard").click(function(){
		var addCardUsername = $("#addCardUsername").val();
		var addCardID = $("#addCardID").val();
		if(addCardID&&addCardID!=""){
			if(addCardUsername&&addCardUsername!=""){	
				var deviceIDs = [];
				$("input[type='checkbox'][name='choiceDeviceCheckbox']:checked").each(function(index,e){
					var deviceID = $(e).attr("id");
					console.log("deviceID="+deviceID+" index="+index );
					deviceIDs.push(deviceID);
				});	
				console.log("deviceIDs="+deviceIDs );
				if(deviceIDs.length==0){
					alert("请选择一体机设备");
				}else{
					saveCard(addCardID,addCardUsername,deviceIDs);
				
				}
			}else{
				alert("用户姓名不能为空");
			}
		}else{
			alert("卡号不能为空");
		}
	});
	
	
	
	$(document).on("click",	"input[type='button'][name='deleteCard']",function() {
		var deleteCardID = $(this).attr("id");
		console.info("delete RFID card cardID="+deleteCardID);
		deleteCard(deleteCardID);
	});
	
	
	$("#returnBtn").click(function(){
		$("#RFIDTable").show();
		$("#addCardTable").hide();
		$("#RFIDDetailTable").hide();
	});
	
	 $("#RFIDDetailReturnBtn").click(function(){
	 	$("#RFIDTable").show();
		$("#addCardTable").hide();
		$("#RFIDDetailTable").hide();
	 
	 });
	
	$(document).on("click",	"input[type='button'][name='checkManageDevice']",function() {
		var cardID = $(this).attr("id");
		console.info("delete RFID card cardID="+cardID);
		$("#RFIDTable").hide();
		$("#addCardTable").hide();
		$("#RFIDDetailTable").show();
		getRFIDDetail(cardID);
	});
	
});


function getRFIDDetail(cardID){
	console.log("luci page getRFIDDetail RFID cardID="+cardID);
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
		data : {
			act : "getRFIDDetail",
			cardID:cardID
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
			console.log(xhr.responseText);
			alert("getRFIDDetail数据失败 cardID="+cardID);
		},
		beforeSend : function() {
			openWaitBox();
		},
		complete : function(XMLHttpRequest, textStatus) {
			closeWaitBox();
		},
		success : function(result) {
			var data =result.data;
			var cardInfo = data.cardInfo; 
			$("#detailCardID").html(cardInfo.cardID);
			$("#detailUsername").html(cardInfo.username);
			$("#detailPhone").html(cardInfo.phone);
			$("#detailRecordDate").html(cardInfo.recordDate);
			var deviceHtml = "";
			var devices = data.devices;
			for ( var i in devices) {
				var device = devices[i];
				deviceHtml += "<tr><td>" 
				+ device.deviceID + "</td><td>" 
				+ device.deviceName + "</td><td>" 
				+ device.firmversion	+ "</td></tr>";	
			}
			if(deviceHtml==""){
				deviceHtml = "<tr><td colspan='4'>暂无关联设备数据</td></tr>";
			}
			$("#RFIDDevicesBody").html(deviceHtml);
			
			
			
		}
	});

}



function saveCard(addCardID,addCardUsername,deviceIDs){
	console.log("新增RFID卡addRFIDCard");
	$.ajax({
		type : "post",
		dataType : "json",
		url : '<%=REQUEST_URI%>',
		data : {
			act : "saveCard",
			addCardID:addCardID,
			addCardUsername:addCardUsername	,
			deviceIDs:deviceIDs.toString()		
		},
		error : function(xhr, status, e) {
			console.error('JqueryAjax error invoke! status:' + status+ e + " " + xhr.status);
			console.log(xhr.responseText);
			alert("开卡失败");
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
				$("#RFIDTable").show();
				$("#addCardTable").hide();
				$("#RFIDDetailTable").hide();
				queryRFIDCard();
			}
		}
	});
}









//-----------------------------------RFID卡管理--------------------------------------------

</script>

<%+footer%>