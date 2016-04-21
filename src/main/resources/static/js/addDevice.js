



var baseUrl = $("#baseUrl").val();



$(function(){
	baseUrl = $("#baseUrl").val();
	queryUnActiveDevice();
	
	
	$(document).on("click",	"input[type='button'][name='activeDevice']",function() {
		var activeDeviceID = $(this).attr("id");
		console.info("active the device ID="+activeDeviceID);
		activeDevice(activeDeviceID);
	});
	
});







function queryUnActiveDevice(){
	console.log("html page queryUnActiveDevice ");
	$.ajax({
		type : "post",
		dataType : "json",
		url : baseUrl+'/tmpDevice/list',
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
			deviceListInit(result);
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
				+ " value='激活' class='btn btn-primary btn-sm'></td></tr>";
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