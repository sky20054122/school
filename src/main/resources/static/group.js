var treeNodes;

var selectAreaId = 0;

//===========================左侧区域树的初始化（包含停用区域）=============================
var 区域初始化;

$(document).ready(function() {
	getLoginUserPerms();
	
	
	// 是否显示停用区域
    $("#displayArea").change(function() {
        if ($("#displayArea").is(":checked")) {
            //显示停用区域
            queryAreaUrl = baseUrl + "/area/getAllCurrAreaNodesByLoginUser";   
            $("#displayAreaLabel").attr("title",$.i18n.prop("view_only_enable_area"));//"只查看启用的区域"
            $("#displayAreaLabel .allArea").hide();  
            $("#displayAreaLabel .enableArea").show();         
        }else{
            //默认不显示停用区域
            queryAreaUrl = baseUrl + "/area/getCurrAreaNodesByLoginUser";
            $("#displayAreaLabel").attr("title",$.i18n.prop("view_all_area"));  //"查看所有的区域(包含停用区域)"
            $("#displayAreaLabel .allArea").show();  
            $("#displayAreaLabel .enableArea").hide();    
        }       
        areaTreeInit(); //重新初始化区域树
    });
    
});

var areaAdd = false;
var areaUpdate = false;
var areaDelete = false;
var schoolUpdate = true;
function getLoginUserPerms() {
	$.ajax({
		type : "post",
		dataType : "json",
		url : baseUrl + "/area/getLoginUserPerms",
		beforeSend : function() {
			initWaitBox();
		},
		complete : function(XMLHttpRequest, textStatus) {
			closeWaitBox();
			dealSessionStatus(XMLHttpRequest, textStatus);
		},
		success : function(result) {
			areaAdd = result.areaAdd;
			areaUpdate = result.areaUpdate;
			areaDelete = result.areaDelete;
			schoolUpdate = result.schoolUpdate;
			// 获取并显示当前用户管理所有区域，包含停用区域
			areaTreeInit();
		}
	});
}

function getAreaSetting(selectAreaId){
	var allAreaSetting = {
			async:{
				enable:true,
				dataType:"text",
				type:"post",
				url:queryAreaUrl,
				autoParam:["id","name","level"],
				otherParam:{"selectAreaId":selectAreaId},
				dataFilter:null
			},
			check : {
				enable : false //每个节点上是否显示 CheckBox
			},
			data : {
				simpleData : {
					enable : true, //数据是否采用简单 Array 格式，默认false   如果设置为 true，请务必设置 setting.data.simpleData 内的其他参数: idKey / pIdKey / rootPId，并且让数据满足父子关系。
					idKey : "id",
					pIdKey : "parentId",
					rootPId : 0
				}
			},
			view : {
				showLine : true, //是否显示节点间的连线
				dblClickExpand: false, //禁用双击
				autoCancelSelected : false, //选中一个，不允许点击当前的这个取消选择
				selectedMulti : false, //不允许多选
				addHoverDom : addHoverDom,
				removeHoverDom : removeHoverDom,
				fontCss : setFont
			},
			edit : {
				enable : true,
				editNameSelectAll : true,
				showRemoveBtn : showRemoveBtn,
				removeTitle :  $.i18n.prop("disable_or_delete_area"),//"停用或删除区域",
				showRenameBtn : showRenameBtn,
				renameTitle : $.i18n.prop("modify_area")//"修改区域"
			},
			callback : {
				beforeDrag : beforeDrag, //禁止拖拽
				beforeExpand: beforeExpand,
				onExpand: onExpand,
				onNodeCreated: zTreeOnNodeCreated,
//				onDblClick : zTreeOnDblClick,
				beforeEditName : beforeEditName, //修改节点之前
				beforeRemove : beforeRemove, //用于捕获节点被删除之前的事件回调函数，并且根据返回值确定是否允许删除操作
				onClick : zTreeOnClick, //用于捕获节点被点击的事件回调函数
				onAsyncSuccess:zTreeOnAsyncSuccess// 用于捕获异步加载正常结束的事件回调函数
			}
	};
	return allAreaSetting;
	
}


function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	// 设置树只展开第一级
	var rootNode = treeObj.getNodes()[0];
	if (rootNode.intStatus == 0) {
        $("#enabledeleterAreaDiv").show();
    } else {
        $("#enabledeleterAreaDiv").hide();
    }
	treeObj.expandNode(rootNode, true, false, true);
	// 设置树全展开
	// $.fn.zTree.getZTreeObj("areaTree").expandAll(true);
	if(selectAreaId!=null){
		selectNode = treeObj.getNodeByParam("id", selectAreaId, null);
		if(selectNode!=null){
			selectAreaId = selectNode.id;
			selectAreaName = selectNode.name;
		}else{
			selectAreaId = rootNode.id;
			selectAreaName = rootNode.name;
			selectNode = rootNode;
		}
	}else{
		selectAreaId = rootNode.id;
		selectAreaName = rootNode.name;
		selectNode = rootNode;
	}

    treeObj.selectNode(selectNode);
    getAreaDetail();
    
};



var selectAreaIdKey = "areaMainSelectAreaId";
//树节点单击事件 显示右边详情
function zTreeOnClick(event, treeId, treeNode) {
    selectAreaId = treeNode.id;
    //  selectAreaName = treeNode.name;
    selectNode = treeNode;
    storgeSessionData(selectAreaIdKey,selectAreaId);
    var zTree = $.fn.zTree.getZTreeObj(treeId);
    zTree.expandNode(treeNode, true, null, null, true);
    //异步后台查询当前点击节点详细信息
    getAreaDetail();

    if (treeNode.intStatus ==0) {
        $("#enabledeleterAreaDiv").show();
    } else {
        $("#enabledeleterAreaDiv").hide();
    }
    

};



function triggerNodeClick(treeId, node) {
	$('body').trigger('treeNodeClick', [ treeId, node ]);
}

$('body').bind('treeNodeClick', function(e, treeId, node) {
	console.log(treeId);
	console.log(node);
});
  
  

var log, className = "dark";
function beforeDrag(treeId, treeNodes) {
    return false;
    //禁止拖拽，必须显示禁止，否则默认可以拖拽
}

function showRenameBtn(treeId, treeNode) {	
	var result = false;
	/** 区域类型-1总部 0行政区 1学校 2年级 3班级 4其他 5省 6市 */
	if(treeNode.areaType == 1 ){
		if(schoolUpdate&&areaUpdate){
			result =  true;
		}else{
			result = false;
		}
	}else{
		result =  areaUpdate;
	}
//	console.log("areaUpdate="+areaUpdate+" schoolUpdate="+schoolUpdate+" result="+result);
	return result;
}

function beforeEditName(treeId, treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj(treeId);
    treeObj.selectNode(treeNode);
    selectNode = treeNode;
    selectAreaId = treeNode.id;
    if (treeNode.intStatus == 0) {
        var msg = $.i18n.prop("enable_area_confirm_info");//"该区域已被停用，确定要启用该区域？";
        initConfirmBox(msg, function(userChoice) {
            if (userChoice) {
                enableArea();
            }
        });
    } else {
        readyToUpdateArea(treeNode);
    }
    return false;
}

function showRemoveBtn(treeId, treeNode) {
	// console.log("parentTId="+treeNode.parentTId+"
	// parentId="+treeNode.parentId);
	if (treeNode.intStatus == 0 || treeNode.parentTId == null || !areaDelete) {
		// 停用区域不允许直接删除，总部不允许删除，没有删除权限areaDelete不允许删除
		return false;
	} else {
		return true;
	}
}


function beforeRemove(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId);
    zTree.selectNode(treeNode);
    //获取被选中节点
    checkAreaCanDisableOrDelete(treeNode.id,treeNode.name);
    return false;
}


function addHoverDom(treeId, treeNode) {
    if (treeNode.intStatus == 1 && treeNode.areaType != 3&& areaAdd) {// 停用区域和班级不能增加子区域 并且具有areaAdd新增权限
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
            return;
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='"+$.i18n.prop("add_child_area")+"'  onfocus='this.blur();'></span>";//新增子节点
        sObj.after(addStr);
        var btn = $("#addBtn_" + treeNode.tId);
        if (btn)
            btn.bind("click", function() {
                var treeObj = $.fn.zTree.getZTreeObj(treeId);
                treeObj.selectNode(treeNode);
                selectNode = treeNode;
                selectAreaId = treeNode.id;
                readyToAddArea(treeNode);
                return false;
            });
    }
};

function removeHoverDom(treeId, treeNode) {
    $("#addBtn_" + treeNode.tId).unbind().remove();
};

function setFont(treeId, treeNode) {
    //  alert(treeNode.name+treeNode.status);
    if (treeNode.intStatus == 0) {
        return {
            color : "gray",
            "text-decoration" : "line-through"
        };
    } else {
        return {
            color : "black"
        };
    }
}

//从后台获取数据  显示树
function areaTreeInit() {
    console.info("init get area tree");
    
    selectAreaId = getSessionData(selectAreaIdKey);
	console.info("selectAreaId="+selectAreaId);
	if(selectAreaId==null){
		selectAreaId =0;
	}
	
	var treeObj = $.fn.zTree.init($("#areaTree"), getAreaSetting(selectAreaId));
//	
//    $.ajax({
//        type : "post",
//        dataType : "json",
//        url : queryAreaUrl,     
//        beforeSend : function() {
//            initWaitBox();
//        },       
//        complete : function(XMLHttpRequest, textStatus) {
//            closeWaitBox();
//            dealSessionStatus(XMLHttpRequest, textStatus);
//        },
//        success : function(result) {            
//            if (result == null || result == '') {
//                $("#areaTree").html($.i18n.prop("no_data"));
//            } else {
//                // treeNode = result;
//                var treeObj = $.fn.zTree.init($("#areaTree"), getAreaSetting(), result);
//                var rootNode = treeObj.getNodes()[0];
//                if (rootNode.intStatus == 0) {
//                    $("#enabledeleterAreaDiv").show();
//                } else {
//                    $("#enabledeleterAreaDiv").hide();
//                }
//                treeObj.expandNode(rootNode, true, false, true);
//                selectAreaId = getSessionData(selectAreaIdKey);
//                if(selectAreaId!=null){
//                	selectNode = treeObj.getNodeByParam("id", selectAreaId);
//                	if(selectNode==null){
//                		selectNode = rootNode;
//                    	selectAreaId = rootNode.id;
//                	}
//                }else{
//                	selectNode = rootNode;
//                	selectAreaId = rootNode.id;
//                }
//                treeObj.selectNode(selectNode, true);
//                getAreaDetail();
//            }
//        }
//    });
}

//===========================end 左侧区域树的初始化（包含停用区域）=============================

//===========================修改区域============================================
var 修改区域;
$(document).ready(function() {

    //更新区域数据验证
    updateAreaValidate();

    $(document).on("change", "input[id='enanbleUpdateRregion']", function() {
        if ($(this).is(":checked")) {
            $("#updateRegionSelectTd").show();
            editStation = true;

        } else {
            $("#updateRegionSelectTd").hide();
            editStation = false;
        }

        // alert(editStation);

    });

});

var editStation = false;
var provinceId = 0;
var cityId = 0;
var stationId = 0;
function readyToUpdateArea(treeNode) {
    provinceId = 0;
    cityId = 0;
    stationId = 0;
    $.ajax({
        cache : false,
        type : "post",
        url : baseUrl + '/area/getAreaDetail/' + treeNode.id,
        dataType : 'json',
        beforeSend : function() {
			initWaitBox();
		},
		complete : function(XMLHttpRequest, textStatus) {
			closeWaitBox();
			dealSessionStatus(XMLHttpRequest, textStatus);
		},
        success : function(result) {
            //更新页面数据初始化
            $("#updateParentArea").html(result.parentName);
            $("#updateParentAreaId").val(result.parentId);
            $("#updateAreaName").val(result.name);
            $("#updateOldAreaName").val(result.name);
            $("#updateAreaStatus").html(result.status);
            $("#updateComments").val(result.comments);
            //				$("#updateAreaId").attr("value",result.id);
            // 判断是否需要修改省份城市
            editStation = result.editStation;

            if (result.editStation) {//可以修改关联  总部 行政区  学校
                var html = getRegionHtml(result.station, "update");
                $("#updateRegionSelectTd").html(html);
                if (result.station == null) {//目前没有关联，则客户自己选择是否启用关联
                    $("#updateRegionSelectTd").hide();
                    $("#updateRegionSelectTdControl").show();
                    $("#enanbleUpdateRregion").attr("checked", false);
                    editStation = false;
                } else {//目前已经有关联 ，则显示select串 可以修改
                    $("#updateRegionSelectTd").show();
                    if (result.stopStation) {
                        $("#updateRegionSelectTdControl").show();
                        $("#enanbleUpdateRregion").attr("checked", true);
                        $("#enanbleUpdateRregion").prop( "checked", true ); 
                    } else {
                        $("#updateRegionSelectTdControl").hide();
                        $("#enanbleUpdateRregion").attr("checked", true);
                        $("#enanbleUpdateRregion").prop( "checked", true ); 
                    };

                }
            } else {//学校之下的区域 不能修改关联    上级区域有关联则显示 无关联则隐藏
                if (result.station != null) {
                    //					alert("显示关联");
                    var cityName = result.station.cityName;
                    var parentCityName = result.station.parentCity.cityName;
                    var provinceName = result.station.parentCity.province.provinceName;
                    $("#updateRegionSelectTd").html(provinceName + "-" + parentCityName + "-" + cityName);
                    $("#updateRegionSelectTdControl").hide();
                } else {
                    $("#updateRegionSelectTr").hide();
                }
            }
            openUpdateAreaBox();
        }
    });

}

function openUpdateAreaBox() {
    $("#updateAreaBox").dialog({
        autoOpen : true,
        height : 400,
        width : 700,
        title : $.i18n.prop("modify_area"),//"修改区域",
        modal : true,
        // position : "center",
        draggable : true,
        resizable : true,
        closeOnEscape : true, // esc退出
        close : function() {
            // document.getElementById("updateAreaForm").reset();
            // $("#updateAreaForm").resetForm();
            updateValidator.resetForm();
            // $("#updateAreaBox").dialog("destroy");
        },
        buttons : [{
        	text:$.i18n.prop("return_button_text"),
            click : function() {
                $(this).dialog("close");
            },
        },{
        	text:$.i18n.prop("save_button_text"),
            click : function() {
                // 验证数据和修改区域
                if ($("#updateAreaForm").valid()) {
                    updateArea();
                    $(this).dialog("close");
                }
            }
        }]
    });
}

function updateArea() {
    var AreaName = $.trim($("#updateAreaName").val());
    var parentId = $("#updateParentAreaId").val();
    var comments = $.trim($("#updateComments").val());
    var stationId = 0;
    if (editStation) {
        stationId = $("#updateStationSelect :selected").val();
    }
    $.ajax({
        cache : false,
        async : false,
        url : baseUrl + "/area/updateArea",
        type : "post",
        dataType : "json",
        data : {
            updateAreaName : AreaName,
            parentId : parentId,
            updateAreaId : selectAreaId,
            updateComments : comments,
            editStation : editStation,
            stationId : stationId
        },
        beforeSend : function() {
            initWaitBox();
            console.info("beforeSend updateArea");
        },       
        complete : function(XMLHttpRequest, textStatus) {
            closeWaitBox();
            console.info("complete updateArea");
            dealSessionStatus(XMLHttpRequest, textStatus);
        },
        success : function(result) {

            if (result.result) {
                initMsgBox(result.msg, function() {
                    //更新区域树，设置默认选择节点
                    areaTreeInit();
                    getAreaDetail();

                });
            } else {
                initMsgBox(result.msg);
            }
        }
    });
}

function changeRadioAreaTree() {
    radioAreaTreeInit();
    // 根据所选节点，判断能移动的范围
    if (selectNode.areaType == 0 || selectNode.areaType == 1) {// 所选节点是学校或者行政区，父节点只能是行政区
        // 寻找所有的学校节点，并隐藏
        var treeObj = $.fn.zTree.getZTreeObj("radioAreaTree");
        var nodes = treeObj.getNodesByParam("areaType", "1", null);
        for (var i = 0; i < nodes.length; i++) {
            var node = nodes[i];
            // treeObj.hideNodes(node);
            treeObj.hideNode(node);
            // alert(node.name);
        }
    } else {// 学校内节点 ，年级 班级 其他 只能在学校内移动
        var parentSchoolNode = getParentNodeSchool(selectNode);
        // 寻找父节点，找到学校位置
        $.fn.zTree.init($("#radioAreaTree"), radioAreaTreeSetting, parentSchoolNode);
        var treeObj = $.fn.zTree.getZTreeObj("radioAreaTree");
        // 设置树全展开
        treeObj.expandAll(true);
        var selfNode = treeObj.getNodeByParam("id", selectNode.id, null);
        // 隐藏或者禁用自己这个节点及其子节点
        treeObj.setChkDisabled(selfNode, true, false, true);
        // treeObj.hideNode(selectNode);
        // 禁用所有的班级节点
        var classNodes = treeObj.getNodesByParam("areaType", "3", null);
        for (var i = 0; i < classNodes.length; i++) {
            var node = classNodes[i];
            treeObj.setChkDisabled(node, true, false, true);
        }
    }
}

function getParentNodeSchool(treeNode) {
    var parentNode = treeNode.getParentNode();
    if (parentNode.areaType == 1) {
        return parentNode;
    } else {
        return getParentNodeSchool(parentNode);
    }
}

var updateValidator;
function updateAreaValidate() {
    //	alert("oldName="+$("#updateOldAreaName").val()+" newName="+$("#updateAreaName").val());
    updateValidator = $("#updateAreaForm").validate({
        rules : {
            updateAreaName : {
                required : true,
                stringCheck:true, //字母数字下划线 中文
                byteRangeLength : [3, 20],
                remote : {
                    url : baseUrl + "/area/checkAreaName",
                    type : "post",
                    dataType : "json",
                    beforeSend : function() {
                        //alert("beforeSend");
                    },
                    complete : function() {
                        //alert("complete");
                    },
                    data : {
                        id : function() {
                            return selectAreaId;
                        },
                        name : function() {
                            return $.trim($("#updateAreaName").val());
                        },
                        parentId : function() {
                            return $("#updateParentAreaId").val();
                        }
                    }
                }
            },
            updateComments:{
            	byteRangeLength : [ 0, 240 ]
            }
        },
//        messages : {
//            updateAreaName : {
//                //required : $.i18n.prop("areaName_required"),//"区域名称不能为空！",
//                required : $.i18n.prop("required"),//"区域名称不能为空！",
//                remote : $.i18n.prop("areaName_remote")//"该区域名称已被使用！"
//            }
//        },

        errorPlacement : function(error, element) {
            error.appendTo(element.parent());
        }
    });
  //初始化消息提示
	addMessages();
}

//树的基本设置
var radioAreaTreeSetting = {
    check : {
        enable : true, //每个节点上是否显示 CheckBox
        chkStyle : "radio",
        radioType : "all"
    },
    data : {
        simpleData : {
            enable : true, //数据是否采用简单 Array 格式，默认false   如果设置为 true，请务必设置 setting.data.simpleData 内的其他参数: idKey / pIdKey / rootPId，并且让数据满足父子关系。
            idKey : "id",
            pIdKey : "parentId",
            rootPId : 0
        }
    },
    view : {
    	  showLine : true, //是否显示连接线
		  selectedMulti: false,//设置是否允许同时选中多个节点。
		  autoCancelSelected: false, //点击节点时，按下 Ctrl 或 Cmd 键是否允许取消选择操作。
		  dblClickExpand: false //双击节点时，是否自动展开父节点的标识
    },
    callback : {
    	 onNodeCreated: zTreeOnNodeCreated
        //     		onClick: zTreeOnClick  //用于捕获节点被点击的事件回调函数
        //        	onCheck: radioCheck   //用于捕获cehckbox radio被选时候触发事件
    }
};



function radioAreaTreeInit() {
    $.fn.zTree.init($("#radioAreaTree"), radioAreaTreeSetting, treeNodes);
    var treeObj = $.fn.zTree.getZTreeObj("radioAreaTree");
    //设置树全展开
    treeObj.expandAll(true);
    //设置树只展开第一级
    //	var rootNode=treeObj.getNodes()[0];
    //	expandLevel(treeObj,rootNode,1);
}

function openRadioAreaTree() {
    $("#radioAreaBox").dialog({
        autoOpen : true,
        height : 400,
        width : 400,
        title : $.i18n.prop("the_area_tree"),//"区域树",
        modal : true,
        // position: "center",
        draggable : true,
        resizable : true,
        closeOnEscape : true, //取消esc退出
        buttons : [{
        	text:$.i18n.prop("close_button_text"),
            click : function() {
                $(this).dialog("close");
            },
        },{
        	text:$.i18n.prop("confirm_button_text"),
            click : function() {
                //把选择数据写入
                var checkNode = getRadioTreeChecked();
                $("#updateParentArea").val(checkNode.name);
                //父区域名称
                $("#updateParentAreaId").val(checkNode.id);
                //父区域id
                $("#addAreaParentAreaName").html(checkNode.name);
                $("#addAreaParentId").val(checkNode.id);
                $(this).dialog("close");
            }
        }]
    });
}

function getRadioTreeChecked() {
    var treeObj = $.fn.zTree.getZTreeObj("radioAreaTree");
    nodes = treeObj.getCheckedNodes(true);
    if (nodes.length == 0) {
        initMsgBox($.i18n.prop("please_select_a_area")); //"你未选择任何区域！"
    } else {
        for (var i = 0; i < nodes.length; i++) {
            if (!nodes[i].getCheckStatus().half) {//去掉半选
                //            	alert(nodes[i].id+" "+nodes[i].name); //获取选中节点的值
                var checkNode = nodes[i];
                return checkNode;

            }
        }
    }
}

//===========================end 修改区域============================================

function getAreaDetail() {

    $.ajax({
        cache : false,
        async : false,
        type : "post",
        url : baseUrl + '/area/getAreaDetail/' + selectAreaId,
        dataType : 'json',
        beforeSend : function() {
            initWaitBox();
            console.info("getAreaDetail beforeSend");
        },
        complete : function(XMLHttpRequest, textStatus) {
            closeWaitBox();
            console.info("getAreaDetail complete");
            dealSessionStatus(XMLHttpRequest, textStatus);
        },
        success : function(result) {
            //查看页面
            $("#areaParentName").text(result.parentName);
            $("#areaName").text(result.name);
            $("#areaStatus").text(result.status);
            $("#areaComments").text(result.comments);
            $("#deviceCount").text(result.deviceCount);
            $("#userCount").text(result.userCount);
            var station = result.station;
            if (station != null) {
                var cityName = station.cityName;
                var parentCityName = station.parentCity.cityName;
                var provinceName = station.parentCity.province.provinceName;
                $("#stationDetail").html(provinceName + "-" + parentCityName + "-" + cityName);
            } else {
                $("#stationDetail").html($.i18n.prop("no_weather_association")); //"暂无天气关联"
            }
        }
    });
}



var queryAreaUrl = baseUrl + "/area/getCurrAreaNodesByLoginUser"; //默认只显示启用的区域


//=========================启用区域==============================
var 启用区域;
$(document).ready(function() {
    $("a[name='areaEnableBtn']").click(function() {
        enableArea();
    });
});

function enableArea() {
    var parentNode = selectNode.getParentNode();
    //	alert(parentNode.status);
    if (parentNode == null || parentNode.intStatus == 1) { //区域启用状态
        $.ajax({
            cache : false,
            async : false,
            url : baseUrl + "/area/enableArea/" + selectNode.id,
            type : "post",
            dataType : "json",
            data : {},
            beforeSend : function() {
                initWaitBox($.i18n.prop("enable_area"));//"启用区域"
                console.info("enableArea beforeSend");
            },
            complete : function(XMLHttpRequest, textStatus) {
                closeWaitBox();
                console.info("enableArea complete");
                dealSessionStatus(XMLHttpRequest, textStatus);
            },
            success : function(result) {

                if (result.result) {
                    initMsgBox(result.msg, function() {
                        $("#enabledeleterAreaDiv").css("display", "none");
                        $("#updateArea").attr("disabled", false);
                        areaTreeInit();
                        getAreaDetail();
                    });

                } else {
                    initMsgBox(result.msg);
                }
            }
        });

    } else {
        initMsgBox($.i18n.prop("parent_area_is_disable_enable_child_area_error_info")); //"该区域的父区域也处于停用状态，必须先启用父区域，才能启用本区域！"
    }
}

//=========================end 启用区域==========================

//========================addArea======================
var 新增区域;
$(document).ready(function() {
    

    //新增区域数据验证
    addAreaFormValidate();

    //关联天气预报？
    $(document).on("change", "input[id='enanbleAddRregion']", function() {
        if ($(this).is(":checked")) {
            $("#addRegionSelectTd").show();
            $("#addEditStation").val(true);
        } else {
            $("#addRegionSelectTd").hide();
            $("#addEditStation").val(false);
        }

    });
    
//    /**
//     * comments是最后一个需要验证的参数，所以填写完成时候进行全局验证
//     */
//    $("#addComments").blur(function(){
//    	$("#addAreaForm").valid();
//    });

});

function readyToAddArea(treeNode) {
    //必须先选择父区域，然后在新增界面弹出 //总部-1 省5  市6   学校1 年级2  班级3  其他4 行政区0
	var areaType0 = "<input type='radio' name='areaType' value='0' id='areaType0' checked/><label for='areaType0'>"+$.i18n.prop("area_type_district")+"</label><span class='errorInfo'> </span>";
	var areaType1 = "<input type='radio' name='areaType' value='1' id='areaType1' checked/><label for='areaType1'>"+$.i18n.prop("area_type_school")+"</label><span class='errorInfo'> </span>";
	var areaType2 = "<input type='radio' name='areaType' value='2' id='areaType2' checked/><label for='areaType2'>"+$.i18n.prop("area_type_grade")+"</label>&nbsp;";
	var areaType3 = "<input type='radio' name='areaType' value='3' id='areaType3' checked/><label for='areaType3'>"+$.i18n.prop("area_type_class")+"</label>&nbsp;";
	var areaType4 = "<input type='radio' name='areaType' value='4' id='areaType4' /><label for='areaType4'>"+$.i18n.prop("area_type_other")+"</label><span class='errorInfo'> </span>";
	var areaType5 = "<input type='radio' name='areaType' value='5' id='areaType5' checked/><label for='areaType5'>"+$.i18n.prop("area_type_district")+"</label><span class='errorInfo'> </span>";
	var areaType6 = "<input type='radio' name='areaType' value='6' id='areaType6' checked/><label for='areaType6'>"+$.i18n.prop("area_type_district")+"</label><span class='errorInfo'> </span>";
    if (selectNode.intStatus == 0) { //区域停用状态
        initMsgBox($.i18n.prop("add_child_area_on_disbale_area_error")); //"停用区域不能增加子区域！"
    } else if (selectNode.areaType == "3") {
        initMsgBox($.i18n.prop("add_child_area_on_class_area_error")); //"班级区域下面不能再添加子区域！"
    } else {
        //		getAreaTreeNode();//获取所有启用的区域集合
        $("#addAreaParentAreaName").html(treeNode.name);
        $("#addAreaParentId").val(treeNode.id);
        var areaType = treeNode.areaType;
        var level = treeNode.level + 1;
        //		alert(level);
        var html = "<input type='hidden' name='areaLevel' value='" + level + "'>";
        if (areaType == -1) {//总部
            html += areaType0;
        } else if (areaType == 0||areaType == 5||areaType == 6) {//行政区 省 市
            html += areaType0+areaType1;
        } else if (areaType == 1) {//学校
            html += areaType4+areaType3+areaType2;
        } else if (areaType == 2) {//年级
        	html += areaType4+areaType3;
        } else if (areaType == 3) {//班级
            //班级下面禁止新增子节点
        } else if (areaType == 4) {//其他
        	html += areaType4+areaType3+areaType2;
        }
        $("#areaTypeTd").html(html);
        checkRegion(treeNode.id);

    }
}

/**
 *检查是否需要关联天气预报城市
 * @param {Object} selectAId
 */
function checkRegion(selectAId) {
    $.ajax({
        cache : false,
        async : false,
        type : "post",
        dataType : "json",
        url : baseUrl + "/area/checkRegion",
        data : {
            areaId : selectAId
        },

        success : function(result) {
            var station = result.station;
            if (result.editStation) {//可以关联天气预报
                var html = getRegionHtml(station, "add");
                //获取天气预报select串  如果station为空 则select去默认值上海
                html+='<input type="hidden" id="addEditStation" name="editStation" value="' + result.editStation + '" />';
                $("#addRegionSelectTd").html(html);
                //初始化select串
                $('#addRegionSelectTr').show();
                //显示天气预报 行
            } else {
                $('#addRegionSelectTr').hide();
                // 隐藏天气预报 行
                $("#addRegionSelectTd").html('<input type="hidden" id="addEditStation" name="editStation" value="' + result.editStation + '" />');
            };
            $("#addRegionSelectTdControl").show();
            $("#addRegionSelectTd").hide();
            $("#addEditStation").val(false);
            openAddAreaBox();
        }
    });
}

function openAddAreaBox() {
    document.getElementById("addAreaForm").reset();
    $("#addAreaForm").resetForm();
    addValidator.resetForm();

    $("#addAreaBox").dialog({
        autoOpen : true,
        // height : 350,
        width : 720,
        title : $.i18n.prop("add_area"),//"新增区域",
        modal : true,
        //position : "center",
        draggable : true,
        resizable : true,
        closeOnEscape : true, // esc退出
        buttons :[ {
        	text:$.i18n.prop("return_button_text"),
            click : function() {
                $(this).dialog("close");
            },
        },{
        	text:$.i18n.prop("save_button_text"),
            click : function() {
            	console.warn("click save btn");
                // 验证数据和保存
            	$("#addAreaForm").valid();
                if ($("#addAreaForm").valid()) {
                    $(this).dialog("close");
                    console.info("before save  add validate");
                    ajaxSaveArea();
                }
            }
        }]
    });
}

var 保存区域;
function ajaxSaveArea() {
    console.info("begin saving area");
    $.ajax({
        type : "post",
        url : baseUrl + "/area/addArea",
        dataType : "json",
        data : $("#addAreaForm").serialize(),
        beforeSend : function() {
            initWaitBox($.i18n.prop("save_area")); //"保存区域"
            console.info("ajaxSaveArea beforeSend");
        },
        complete : function(XMLHttpRequest, textStatus) {
            closeWaitBox();
            console.info("ajaxSaveArea complete");
            dealSessionStatus(XMLHttpRequest, textStatus);
        },
        success : function(result) {
            if (result.result) {
                console.info("saving area success");
                initMsgBox(result.msg, function() {
                    // window.location.reload(true);
                    // 根据返回的区域id重新选择刚才新增的区域 刷新区域树数据
                    selectAreaId = result.areaId;
                    storgeSessionData(selectAreaIdKey,selectAreaId);
                    areaTreeInit();
                });
            } else {
                console.info("saving area fail");
                console.info("save area fail" + result.msg);
                initMsgBox(result.msg);
            }
        }
    });
}

var addValidator;
function addAreaFormValidate() {
    addValidator = $("#addAreaForm").validate({
        rules : {
            name : {
                required : true,
                byteRangeLength : [3, 20],
                stringCheck:true, //字母数字下划线 中文
                remote : {
                    url : baseUrl + "/area/checkAreaName",
                    type : "post",
                    dataType : "json",
                    async : false,
                    beforeSend : function() {
                        //alert("beforeSend");
                        console.info("check area name :beforeSend");
                    },
                    complete : function() {
                        //alert("complete");
                        console.info("check area name :complete");
                    },
                    data : {
                        id : function() {
                            return -1;
                            // 新增时候还没有areaId
                        },
                        name : function() {
                            return $.trim($("#name").val());
                        },
                        parentId : function() {
                            return $("#addAreaParentId").val();
                        }
                    }
                }
            },
            areaType :{
            	required:true
            },
            comments:{
            	byteRangeLength : [ 0, 240 ]
            }
        },
        errorPlacement : function(error, element) {
            error.appendTo(element.parent());
        }
    });
    
  //初始化消息提示
	addMessages();

  

}

// ========================end addArea======================

//==================删除区域=====================
var 删除区域;

function checkAreaCanDisableOrDelete(areaId,areaName){
	$.ajax({
		cache:false,
		asunc:false,
		type:"post",
		dataType:"json",
		url:baseUrl+"/area/checkAreaCanDisableOrDelete",
		data:{
			areaId:areaId
		},
		success:function(result){
			if(result.result){
				$("#disableOrDeleteConfirmBox").dialog({
					autoOpen : false,
			        //height : 300,
			        width : 400,
			        title : $.i18n.prop("confirm_button_text"),
			        modal : true,
			        draggable : true,
			        resizable : true,
			        closeOnEscape : true, // esc退出
			        close : function() {     },
			        buttons : [{
			        	text:$.i18n.prop("disable_button_text"),
			            click : function() {
			            	disableArea(areaId);
			                $(this).dialog("close");
			            },
			        },{
			        	text:$.i18n.prop("cancel_button_text"),
			            click : function() {
			                    $(this).dialog("close");
			            }
			        }]
				});
				$("#disableOrDeleteMsgDiv").html($.i18n.prop("confirm_disable_area_info",areaName));//"确认停用父节点 -- " + treeNode.name + " 及其子节点吗？";
				if(result.deleteAble){
					$("#disableOrDeleteMsgDiv").html($.i18n.prop("confirm_disable_or_delete_area_info",areaName));  //确认要删除或者停用所选节点 XXX
					$("#disableOrDeleteConfirmBox").dialog({
				        buttons : [{
				        	text:$.i18n.prop("delete_button_text"),
				            click : function() {
				            	deleteArea(areaId);
				                $(this).dialog("close");
				            },
				        },{
				        	text:$.i18n.prop("disable_button_text"),
				            click : function() {
				            	disableArea(areaId);
				                $(this).dialog("close");
				            },
				        },{
				        	text:$.i18n.prop("cancel_button_text"),
				            click : function() {
				                    $(this).dialog("close");
				            }
				        }]
					});
				}
				$("#disableOrDeleteConfirmBox").dialog("open");
			}else{
				initMsgBox(result.msg)
			}
		}
	});
}


function deleteArea(areaId) {
        $.ajax({
            cache : false,
            async : false,
            url : baseUrl + "/area/deleteArea",
            type : "post",
            dataType : "json",
            data : {
                areaId : areaId
            },
            beforeSend : function() {
                initWaitBox($.i18n.prop("delete_area")); //"删除区域"
                console.info("deleteArea beforeSend");
            },
            complete : function(XMLHttpRequest, textStatus) {
                closeWaitBox();
                console.info("deleteArea complete");
                dealSessionStatus(XMLHttpRequest, textStatus);
            },
            success : function(result) {
                if (result.result) {
                    initMsgBox(result.msg, function() {
                        //关闭弹窗，刷新区域管理所有区域树的数据
                    	var treeObj = $.fn.zTree.getZTreeObj("areaTree");
                    	selectNode = treeObj.getNodeByParam("id", areaId, null);
                    	if(selectNode!=null){
                    		var parentNode = selectNode.getParentNode();
                    		selectAreaId = parentNode.id;
                    	}
                        storgeSessionData(selectAreaIdKey,selectAreaId);
                        areaTreeInit();
                    });
                } else {
                    if (result.disable) {
                        initConfirmBox(result.msg + $.i18n.prop("need_disable_area"), function(f) {  //" 需要停用该区域吗？"
                            if (f) {
                                disableArea(areaId);
                            };
                        });
                    } else {
                        initMsgBox(result.msg);
                    };
                }
            }
        });
}

function disableArea(areaId) {
        console.info("disabled area id=" + areaId);
        $.ajax({
            cache : false,
            async : false,
            url : baseUrl + "/area/disableArea",
            type : "post",
            dataType : "json",
            data : {
                areaId : areaId
            },
            beforeSend : function() {
                initWaitBox($.i18n.prop("disable_area")); //"停用区域"
                console.info("disableArea beforeSend");
            },
            complete : function(XMLHttpRequest, textStatus) {
                closeWaitBox();
                console.info("disableArea complete");
                dealSessionStatus(XMLHttpRequest, textStatus);
            },
            success : function(result) {
                if (result.result) {
                    initMsgBox(result.msg, function() {
                        //关闭弹窗，刷新区域管理所有区域树的数据
                    	var queryallAreaUrl = baseUrl + "/area/getCurrUserAllArea";
                    	if(queryAreaUrl == queryallAreaUrl){ //查询所有区域，包含停用区域
                    		selectAreaId = areaId;
                    	}else{ //只查看启用区域
                    		var treeObj = $.fn.zTree.getZTreeObj("areaTree");
                    		selectNode = treeObj.getNodeByParam("id", areaId, null);
                    		if(selectNode!=null){
                    			var parentNode = selectNode.getParentNode();
                    			selectAreaId = parentNode.id;
                    		}
                    		storgeSessionData(selectAreaIdKey,selectAreaId);
                    	}
                        areaTreeInit();
                    });
                } else {
                    initMsgBox(result.msg);
                }
            }
        });

}

//==================/.删除区域=========================

//============================关联地区   省市区===============================
var 关联天气;

$(document).ready(function() {
    $(document).on("change", ".provinceSelect", function() {
        var provinceId = $(this).val();
        var html = getCityOptionHtml(provinceId, 0);
        $(this).parent().find(".citySelect").html(html);
        var cId = $(this).parent().find(".citySelect").val();
        var html = getStationOptionHtml(cId, 0);
        $(this).parent().find(".stationSelect").html(html);
    });

    $(document).on("change", ".citySelect", function() {
        var cityId = $(this).val();
        var html = getStationOptionHtml(cityId, 0);
        $(this).parent().find(".stationSelect").html(html);
    });
});

function getRegionHtml(station, name) {
    var pId = 10102;
    var cId = 1010200;
    var sId = 101020001;
    if (station != null) {
        pId = station.parentCity.province.provinceId;
        cId = station.parentCity.cityId;
        sId = station.cityId;
    }

    var html = "<select id='" + name + "ProvinceSelect' class='provinceSelect'>";
    html += getProvinceOPtion(pId);
    html += "</select>&nbsp;&nbsp;";

    html += "<select id='" + name + "CitySelect' class='citySelect'>";
    html += getCityOptionHtml(pId, cId);
    html += "</select>&nbsp;&nbsp;";

    html += "<select id='" + name + "StationSelect' class='stationSelect' name='stationId'>";
    html += getStationOptionHtml(cId, sId);
    html += "</select>&nbsp;&nbsp;";

    return html;

}

function getProvinceOPtion(provinceId) {
    var html = "";
    $.ajax({
        cache : false,
        async : false,
        dataType : "json",
        type : "post",
        url : baseUrl + "/region/findAllProvinces",

        success : function(result) {
            $(result).each(function(index, province) {
                if (province.provinceId == provinceId) {
                    html += "<option value='" + province.provinceId + "' selected>" + province.provinceName + "</option>";
                } else {
                    html += "<option value='" + province.provinceId + "'>" + province.provinceName + "</option>";
                };
            });
        }
    });
    return html;
}

function getCityOptionHtml(provinceId, cityId) {
    var cityHtml = "";
    $.ajax({
        cache : false,
        async : false,
        dataType : "json",
        type : "post",
        url : baseUrl + "/region/findCitys/" + provinceId,

        success : function(result) {
            $(result).each(function(index, city) {
                if (city.cityId == cityId) {
                    cityHtml += "<option value='" + city.cityId + "' selected>" + city.cityName + "</option>";
                } else {
                    cityHtml += "<option value='" + city.cityId + "'>" + city.cityName + "</option>";
                };
            });
        }
    });
    return cityHtml;
}

function getStationOptionHtml(cityId, stationId) {
    var stationHtml = "";
    $.ajax({
        cache : false,
        async : false,
        dataType : "json",
        type : "post",
        url : baseUrl + "/region/findStations/" + cityId,

        success : function(result) {
            $(result).each(function(index, station) {
                if (station.cityId == stationId) {
                    stationHtml += "<option value='" + station.cityId + "' selected>" + station.cityName + "</option>";
                } else {
                    stationHtml += "<option value='" + station.cityId + "'>" + station.cityName + "</option>";
                };

            });
        }
    });
    return stationHtml;
}

//============================end 关联地区  省市区===========================



