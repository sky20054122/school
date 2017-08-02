
//+++++++++++++++++条件查询++++++++++++++++++++++++
$(function(){
	
	var baseUrl = $("#baseUrl").val();
	
	
	
	
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
	
	queryMonitorDevice();
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
	var deviceID = $("#queryDeviceID").val();
	var deviceName = $("#queryDeviceName").val();
	var deviceVersion = $("#queryDeviceVersion").val();
	var deviceStatus = $("#queryDeviceStatus").val();
	console.log("html page queryMonitorDevice ");
	$.ajax({
		type : "post",
		dataType : "json",
		url : baseUrl+"/device/queryMonitorDevice",
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
			deviceListInit(result);
		}
	});

}



function deviceListInit(data){
	console.info("deviceListInit 初始化数据到页面");
	var html = "";
	for ( var i in data) {
		var device = data[i];
		html += "<tr><td><input class='cbi-input-checkbox'  type='checkbox' id="+ device.deviceId+ " name='choiceDeviceCheckbox' value='1'></td><td>" 
		+ device.deviceName + "</td><td>" 
		+ device.deviceId + "</td><td>" 
		+ device.firmVersion + "</td><td>"
		+ device.deviceStatus	+ "</td><td>"
		+"<input type='button' name='checkDeviceDetail' value='详情'  id='"+device.deviceId+"' class='btn btn-info btn-sm'>&nbsp;&nbsp;"
		+"<input type='button' name='deleteDevice' value='删除' id='"+device.deviceId+"' class='btn btn-info btn-sm'>&nbsp;&nbsp;"
		+"<input type='button' name='checkdeviceLogs' value='日志' id='"+device.deviceId+"' class='btn btn-info btn-sm'>"
		+ "</td></tr>";		
	}
	if(html==""){
		html = "<tr><td colspan='6'>暂无设备数据</td></tr>";
	}
	$("#deviceListBody").html(html);
}


//----------/.初始化页面数据------------------------------------


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
		        	id:"addBroadcastBoxReturnID",
		        	class:"btn btn-warning",
		            click : function() {
		                $(this).dialog("close");
		            },
		        },{
		        	text:"广播",
		        	id:"addBroadcastBoxBoradcastID",
		        	class:"btn btn-warning",
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
				var html = "";
				$(result).each(function(index,file){
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
			url : baseUrl+"/device/batchControllDevice",
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
				alert(result.message);
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
 * deviceID 设备的id 用于区分控制那个设备
 * deviceTypeCode 设备类型
 * operationCode 设备操作类型
 * ID 操作设备ID  主要用于开关控制
 * operationValue 操作值  主要音量控制的值
 */
function deviceControll(deviceID,deviceTypeCode, operationCode, ID,operationValue) {
	var controllInfo = "deviceControll: deviceType=" + deviceType + "  operationCode="
	+ operationCode+ "  ID=" + ID + "  operationValue=" + operationValue;
	console.log("device controll info = "+controllInfo);
	
	$.ajax({
		type : "post",
		dataType : "json",
		url : baseUrl+"/device/deviceControll",
		data : {
			act : "deviceControll",
			deviceID:deviceID,
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
			alert(result.message);
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
		url : baseUrl+"/device/getDeviceDetail",
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
			if(!result.result){
				alert(result.message);
				return false;
			}
			
			var deviceInfo = result.device;
			
			var dtsRFID = deviceInfo.rfid;
			if(dtsRFID!=null){
				var status = dtsRFID.status;
				$("#detailRFID").html("RFID");
				if(status==deviceStatus.onLine){
					$("#detailRFID").addClass("online").removeClass("offline");
				}else if(status==deviceStatus.offLine){
					$("#detailRFID").addClass("offline").removeClass("online");
				}
				$("#detailRFID").attr("title",dtsRFID.identify);
			}else{
				$("#detailRFID").html("");
			}
			
			var dtsScreen = deviceInfo.screen;
			if(dtsScreen!=null){
				var status = dtsScreen.status;
				$("#detailScreen").html("SCREEN");
				$("#detailScreen").attr("title",dtsScreen.identify);
				if(status==deviceStatus.onLine){
					$("#detailScreen").addClass("online").removeClass("offline");
				}else if(status==deviceStatus.offLine){
					$("#detailScreen").addClass("offline").removeClass("online");
				}
			}else{
				$("#detailScreen").html("");
			}
			
			var dtsSensor = deviceInfo.sensor;
			if(dtsSensor!=null){
				var status = dtsSensor.status;
				if(status==deviceStatus.onLine){
					$("#temperature").html(dtsSensor.temperature+"℃");
					$("#humidness").html(dtsSensor.humidness+"%");
					$("#illumination").html(dtsSensor.illumination+"lm");
					$("#temperature").addClass("online").removeClass("offline");
					$("#humidness").addClass("online").removeClass("offline");
					$("#illumination").addClass("online").removeClass("offline");
				}else if(status==deviceStatus.offLine){
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
			$("#detailDeviceVersion").html(deviceInfo.firmVersion);
			var recordDate = moment(deviceInfo.recordDate).format("YYYY-MM-DD HH:mm:ss");
			$("#detailRecordDate").html(recordDate);
			
			
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
				value:deviceInfo.volume,
				change:function(event,ui){
					console.log("slider change value = "+ui.value);
				},
				start: function( event, ui ) {
					console.log("slider start value = "+ui.value);
				},
				stop: function( event, ui ) {
					console.log("slider stop value = "+ui.value);
					//调用后台的change  volume value
					deviceTypeCode = deviceType.dtsVolume;
					operationCode = operation.adjust;
					deviceControll(deviceID,deviceTypeCode, operationCode, null,ui.value);
				},
				slide: function( event, ui ) {
			        $( "#volumeValue" ).html( ui.value );
			      }
			});
			
		    $( "#volumeValue" ).html( $( "#detailVolume" ).slider( "value" ) );
			 

			
			
			var dtsPCStatus = false;
			var dtsPC = deviceInfo.pc;
			if(dtsPC!=null){
				if(deviceInfo.pc.status==deviceStatus.onLine){
					dtsPCStatus= true;
				}else if(deviceInfo.pc.status==deviceStatus.offLine){
					dtsPCStatus= false;
				}else{
					alert(" dtsPC status error :"+ deviceInfo.pc.status);
				}
			}else{
				
			}
			$("#detailPC").bootstrapSwitch({
				state:dtsPCStatus
			});
			 $("#detailPC").on('switchChange.bootstrapSwitch', function(event, state) {
			 	var operationCode = state?operation.start:operation.shutdown;
			 	var deviceTypeCode = deviceType.dtsPC;
			 	console.log("detail PC state "+state+" operationCode="+operationCode +"  deviceTypeCode="+deviceTypeCode);
		        deviceControll(deviceID,deviceTypeCode,operationCode,null,null);
		    });
						
			var dtsChannelStatus = false;
			if(deviceInfo.channel=="1"){
				dtsChannelStatus= true;
			}else if(deviceInfo.channel=="0"){
				dtsChannelStatus= false;
			}else{
				alert(" dtsChannel status error :"+ deviceInfo.channel);
			}
			$("#detailChannel").bootstrapSwitch({
				state:dtsChannelStatus
			});
			 $("#detailChannel").on('switchChange.bootstrapSwitch', function(event, state) {
		        deviceControll(deviceID,deviceType.dtsChannel,state?operation.start:operation.shutdown,null,null);
		    });
			
			var dtsBoothtatus = false;
			var dtsBooth = deviceInfo.booth;
			if(dtsBooth!=null){
				if(deviceInfo.booth.status==deviceStatus.onLine){
					dtsBoothtatus= true;
				}else if(deviceInfo.booth.status==deviceStatus.offLine){
					dtsBoothtatus= false;
				}else{
					alert(" dtsBooth status error :"+ deviceInfo.booth.status);
				}
				
			}else{
				
			}
			$("#detailBooth").bootstrapSwitch({
				state:dtsBoothtatus
			});
			 $("#detailBooth").on('switchChange.bootstrapSwitch', function(event, state) {
		        deviceControll(deviceID,deviceType.dtsBooth,state?operation.start:operation.shutdown,null,null);
		    });
		    
			
			var dtsDisplayerStatus = false;
			var dtsDisplayer = deviceInfo.displayer;
			if (dtsDisplayer!=null) {
				if(deviceInfo.displayer.status==deviceStatus.onLine){
					dtsDisplayerStatus= true;
				}else if(deviceInfo.displayer.status==deviceStatus.offLine){
					dtsDisplayerStatus= false;
				}else{
					alert(" dtsDisplayer status error :"+ deviceInfo.displayer.status);
				}
				
			} else{
				
			};
			$("#detailDisplayer").bootstrapSwitch({
				state:dtsDisplayerStatus
			});
			
			$("#detailDisplayer").on('switchChange.bootstrapSwitch', function(event, state) {
		        deviceControll(deviceID,deviceType.dtsDisplayer,state?operation.start:operation.shutdown,null,null);
		    });
	
			
			
			
			
			var switchHtml = "";
			var dtsSwitch = deviceInfo.dtsSwitches;
			if(dtsSwitch!=null){
				 $(dtsSwitch).each(function(index,mySwitch){  //循环取出教师有多少个开关面板
				 	var mac = mySwitch.mac;
				 	var name = mySwitch.name;
				 	var child = mySwitch.child;
				 	switchHtml+="<tr><td style='width:75%;'><span title='"+mac+"'>"+name+"</span></td>";
				 	$(child).each(function(index,children){  //循环取出开关面板里面的开关个数和详情
				 		var status = children.status;				 		
				 		var identify = children.identify;
					 	if(status==deviceStatus.onLine){
				 			switchHtml+="<td class='tdRight'><div class='switch' title='"+identify+"'><input type='checkbox' id='"+identify+"' name='"+mac+"' checked/></div></td>";
					 	}else if(status==deviceStatus.offLine){
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
			        deviceControll(deviceID,deviceType.dtsSwitch,state?operation.start:operation.shutdown,mac,identify);
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
		url : baseUrl+"/device/deleteDevice",
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
			alert(result.message);
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
	this.onLine = "ONLINE";//在线状态   待机状态和运行状态都是在线状态的一种
	this.standby = "STANDBY";//待机状态
	this.working = "WORKING";//运行状态
	this.offLine = "OFFLINE";//离线状态
	this.inside = "0";//内显
	this.outside = "1";//外显
}
var deviceStatus = new DeviceStatus();