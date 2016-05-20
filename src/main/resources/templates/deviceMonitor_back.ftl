<#assign base=rc.contextPath>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>设备监控</title>
		<script src="/jquery/jquery.min.js" type="text/javascript"></script>
		<script>
			$(document).ready(function() {
				$("#header").load("/header.html",function(){
					$("#deviceLi").addClass("active");
				});
				$("#footer").load("/footer.html");
				
			});
		</script>
		<!-- Bootstrap CSS -->
		<link rel="stylesheet" type="text/css" href="${base}/jquery-ui/jquery-ui.min.css">
		<link rel="stylesheet" type="text/css" href="${base}/bootstrap/css/bootstrap.min.css" >
		<link rel="stylesheet" type="text/css" href="${base}/bootstrap/bootstrap-switch/bootstrap-switch.min.css">
<style type="text/css">
	
	
	.btn-sm{
		font-size: 12px !important;
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
	</head>
	<body style="padding-top: 70px;">
		<input type="hidden" id="baseUrl" value="${base}" />
		
		<div id="header"> </div>
		<div class="container">
			
			<div id="tabs">
			<ul>
				<li><a href="#deviceMonitorPanel">设备监控</a></li>
				<li><a href="#deviceDetailPanel"><span id="deviceDetailTabTitle"> </span></a></li>
			</ul>
			<div id="deviceMonitorPanel">
				<table class="table">
					<tr>
						<td colspan="6" class="tdRight">
							<input type="text" id="queryDeviceName" placeholder="设备名称" class="remInput">&nbsp;&nbsp;
							<input type="text" id="queryDeviceID" placeholder="设备编码" class="remInput">&nbsp;&nbsp;
							<select id="queryDeviceVersion">
								<option value="ALL">全部版本</option>
								<option value="DTSK3A">DTSK3</option>
								<option value="DTSK3A">DTSK3A</option>
							</select>&nbsp;&nbsp;
							<!-- <input type="text" id="queryDeviceVersion" placeholder="设备版本" class="remInput"> -->
							<select id="queryDeviceStatus">
								<option value="ALL">全部状态</option>
								<option value="ONLINE">在线</option>
								<option value="OFFLINE">离线</option>
							</select>&nbsp;&nbsp;
							<!-- <input type="text" id="queryDeviceStatus" placeholder="设备状态" class="remInput">&nbsp;&nbsp; -->
							<input type="button" id="queryBtn" value="查询" class="btn btn-info btn-sm">
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
				<table class="table"> 
					<tr>
						<td class="tdRight">
							<input type="button" value="开机" class="btn btn-info" id="startDevice" >
							<input type="button" value="关机" class="btn btn-info" id="shutdownDevice" >
							<input type="button" value="广播" class="btn btn-info" id="boardcastBtn" >
						</td>
					</tr>
				</table>
			</div><!-- deviceMonitorPanel-->
			
			<div id="deviceDetailPanel">
				<table class="table">
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
				<table class="table">
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
						<td colspan="3" class="tdRight">
							<input type="button" class="btn btn-sm btn-info" value ="返回" id="closeDeviceDetailBtn">
						</td>
					</tr>
				</table>
			</div><!-- deviceDetailPanel-->
			
		</div><!-- ./tabs-->
			
		
				
		

			<div id="addBroadcastBox" style="display:none;">
				<table class="table">
					<tr>
						<td>选择节目</td>
						<td>
							<select id="programSelect" class="form-control">
								<option value="铃声.mp3">铃声.mp3</option>
								<option value="广播体操.mp3">广播体操.mp3</option>
								<option value="升旗仪式.mp3">升旗仪式.mp3</option>
								<option value="眼保健操.mp3">眼保健操.mp3</option>
								<option value="套马杆.mp3">套马杆.mp3</option>
							</select>
						</td>
					</tr>		
				</table>
			</div><!--		./addBroadcastBox			-->
			
			<div id="footer"> </div>
		</div><!-- /.container -->
	</body>
	
	
	<!-- jQuery first, then Bootstrap JS. -->
	<script type="text/javascript" src="${base}/jquery-ui/jquery-ui.min.js"></script>
	<script type="text/javascript" src="${base}/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="${base}/jquery/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${base}/bootstrap/bootstrap-switch/bootstrap-switch.min.js"></script>
	<script type="text/javascript" src="${base}/jwplayer/jwplayer.js"></script>
	<script type="text/javascript" src="${base}/brxyTool.js"></script>
	<script type="text/javascript" src="${base}/moment/moment.min.js"></script>
	<script type="text/javascript" src="${base}/js/deviceMonitor.js"></script>
</html>


