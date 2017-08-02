/**
 * 博瑞星云
 * 
 * brxy school server 工具js集合
 * 
 * xiaobing yang
 * 
 * 2016-05-10
 */


var 基础路径;
var baseUrl = $("#baseUrl").val();
$(function() {
	baseUrl = $("#baseUrl").val();
	console.log("brxy.js baseUrl="+baseUrl);
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

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}