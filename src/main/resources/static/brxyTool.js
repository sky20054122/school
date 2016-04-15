/**
 * 博瑞星云
 * 
 * luci相关工具js集合
 * 
 * xiaobing yang
 * 
 * 2015-07-08
 */

var 基础路径;
var baseUrl = $("#baseUrl").val();
var loginUrl = $("#loginUrl").val();
$(function() {
	baseUrl = $("#baseUrl").val();
	loginUrl = $("#loginUrl").val();
});

var 等待弹窗;
var overCss = {
	"display" : "none",
	"position" : "fixed",
	"top" : "0",
	"left" : "0",
	"width" : "100%",
	"height" : "100%",
	"background-color" : "#B7B7B7",
	"opacity" : "0.5",
	"z-index" : "1000"
};

var layoutCss = {
	"display" : "none",
	"position" : "absolute",
	"top" : "40%",
	"left" : "40%",
	"width" : "20%",
	"height" : "20%",
	"z-index" : "1001",
	"text-align" : "center"
};

function openWaitBox(msg) {

	if (document.getElementById("over") == null) {
		$("body").append("<div id='over' class='over'> </div>");
	}

	if (document.getElementById("layout") == null) {
		$("body")
				.append(
						"<div id='layout' class='layout'><img src='"
								+ baseUrl
								+ "/loading.GIF'><div id='contentMsg' style='font-weight: bold;font-size: 18px;color:#0069D6;padding:10px;'></div></div>");
	}
	if (msg && msg != "") {
		$("#contentMsg").html(msg);
	}
	$('#over').css(overCss);
	$('#layout').css(layoutCss);
	$('#over').show();
	$('#layout').show();
	setTimeout(function() {
		$('#over').hide();
		$('#layout').hide();
	}, 180000);
}

function closeWaitBox() {
	// $("#waitBox").dialog("close");
	// console.info("close waitBox");

	// 延时关闭
	setTimeout(function() {
		$('#over').hide();
		$('#layout').hide();
	}, 500);
}

var 验证方法;

/** 16进制代码 */
jQuery.validator.addMethod("hexadecimal", function(value, element) {
	return this.optional(element)
			|| /^[0-9a-fA-F]{2}([,][0-9a-fA-F]{2})*$/.test(value);
}, "请输入正确的16进制代码，每两位逗号隔开");

/**mac地址*/
jQuery.validator.addMethod("macaddr", function(value, element) {
	return this.optional(element) || /^([0-9a-fA-F]{2})(([/\s:-][0-9a-fA-F]{2}){5})$/.test(value);
}, "请输入正确的mac地址");
	
/**RFID工作时间验证  0:无限制, 定时范围5～240分钟*/
jQuery.validator.addMethod("workTime",  function(value, element) {   
	return /^[5-9]$|^[1-9][0-9]$|^[1][0-9]\d$|[2][0-3]\d$|^240$|^0$/.test( $.trim( value ) );	
}, "0或者5-240"); 

// 如果rpc登录过期，ajax请求会被拒绝，范湖你登录页面重新登录
function ajaxErrorDeal(xhr, status, e) {
	console.error('JqueryAjax error invoke! status:' + status + e + " "
			+ xhr.status);
	// alert(status+" "+e+" "+xhr.status);
	if (xhr.status == 403) {
		console.error("ajax 403 error request forbidden , re login");
		var mainUrl = loginUrl; 
		var urls = mainUrl.split(";");
		var url = urls[0];
		window.location.href = url;
	}
}


function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}