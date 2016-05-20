$(function(){
	var deviceID = $("#detailDeviceID").html();
	
	getDeviceDetail(deviceID);
	
	
	
	$("#closeDeviceDetailBtn").click(function(){
		openWaitBox();
		window.location.href=baseUrl+"/device/main";
	});
});



function getDeviceDetail(deviceID) {
		console.log("getDeviceDetail deviceID=" + deviceID);
		$.ajax({
			type : "post",
			dataType : "json",
			url : baseUrl + '/device/getDeviceDetail',
			data : {
				deviceID : deviceID
			},
			error : function(xhr, status, e) {
				console.error('JqueryAjax error invoke! status:' + status + e + " " + xhr.status);
				console.log(xhr.responseText);
				alert("getDeviceDetail fail ");
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
				detailInit(result,deviceID);
			}
		});
	}


function detailInit(result,deviceID){
	
	
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
	$("#detailDeviceVersion").html(deviceInfo.firmversion);
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
	
	//音量控制bootstrap slider
	var volume = deviceInfo.volume;
	$( "#volumeValue" ).html(volume);
	var volumeHtml = "<input id='volume' data-slider-id='green' type='text' data-slider-min='0' data-slider-max='15' data-slider-step='1' data-slider-handle='round' data-slider-value='"+volume+"'/>";
	$( "#detailVolume" ).html(volumeHtml);
	var volumeSlider = $('#volume').slider({
		formatter: function(value) {
			$( "#volumeValue" ).html(value);
			return 'Current value: ' + value;
		},
		change:function(two){
			//alert();
		},
		slideStop:function(value){
			alert("slide stop value ="+ value);
		}
	});
	
	 $('#volume').on('slideStop', function (ui) {
		 console.log("slider stop value = "+ui.value);
			//调用后台的change  volume value
			deviceTypeCode = deviceType.dtsVolume;
			operationCode = operation.adjust;
			deviceControll(deviceID,deviceTypeCode, operationCode, null,ui.value);
    });
	
	//音量控制  jquery ui slider
	/*
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
	 */

	
	
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


