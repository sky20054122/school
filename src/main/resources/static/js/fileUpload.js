

$(function(){
	
	var bar = $('.bar');
	var percent = $('.percent');
	var status = $('#status');
	   
	$('form').ajaxForm({
		dataType:'json',
	    beforeSend: function() {
	        status.empty();
	        var percentVal = '0%';
	        bar.width(percentVal)
	        percent.html(percentVal);
	    },
	    uploadProgress: function(event, position, total, percentComplete) {
	        var percentVal = percentComplete + '%';
	        bar.width(percentVal)
	        percent.html(percentVal);
	    },
	    success: function(data) {
	    	console.log(data)
	    	alert(data.message);
	    	if(data.result){
	    		//$('#uploadForm').resetForm();
	    		//queryProgramList();
	    		window.location.reload(true);
	    	}
	        var percentVal = '100%';
	        bar.width(percentVal)
	        percent.html(percentVal);
	    },
		complete: function(xhr) {
			//status.html(xhr.responseText);
		}
	}); 
	
	$('#file').change(function(){
	    var file = this.files[0];
	    name = file.name;
	    size = file.size;
	    type = file.type;
	    //your validation
	    console.log("name="+name+" size="+size+" type="+type);
	});
	
	
	
	
});



//+++++++++++++++++ 文件上传相关+++++++++++++++++++++++++++
	$(function() {

		queryProgramList();
	});

	function queryProgramList() {
		console.log("luci page queryProgramList ");
		$.ajax({
			type : "post",
			dataType : "json",
			url : baseUrl+'/program/findAll',
			data : {
				act : "queryProgramList"
			},
			error : function(xhr, status, e) {
				console.error('JqueryAjax error invoke! status:'
						+ status + e + " " + xhr.status);
				console.log(xhr.responseText);
				alert("queryProgramList 查询节目列表失败");
			},
			beforeSend : function() {
				openWaitBox();
			},
			complete : function(XMLHttpRequest, textStatus) {
				closeWaitBox();
			},
			success : function(data) {
				if(data){
					var html = "";
					var hasProp = false;
					for(var prop in data){
						var program = data[prop];
						var recordDate = moment(program.recordDate).format("YYYY-MM-DD HH:mm:ss");
						hasProp = true;  
						html += "<tr><td>"+ program.name
								+ "</td><td>"+ program.duration
								+ "</td><td>"+ recordDate
								+ "</td><td>"+ program.comment
								+ "</td><td><input type='button' name='delete' id='"+program.id+"' value='删除' class='btn btn-info btn-sm'></td></tr>";
					}
					if (!hasProp){  
						html = "<tr><td colspan=5 style='text-align:center;'>暂无数据</td></tr>";							
					}
					$("#fileListBody").html(html);
				}
				   
			}
		});
	}

	


	//-----------------------------文件上传----------------------
	
	
	//+++++++++++++++++++++++删除文件+++++++++++++++++++++++++
	$(function(){
		$(document).on("click",	"input[type='button'][name='delete']",function() {
			var programID = $(this).attr("id");
			console.info("delete program ID="+programID);
			deleteProgramFile(programID);
		});
	});
	
	
	function deleteProgramFile(programID){
		console.log("html page deleteProgramFile programID="+programID);
		$.ajax({
			type : "post",
			dataType : "json",
			url : baseUrl+'/program/deleteProgramFile',
			data : {
				act : "deleteProgramFile",
				programID:programID
			},
			error : function(xhr, status, e) {
				console.error('JqueryAjax error invoke! status:'
						+ status + e + " " + xhr.status);
				console.log(xhr.responseText);
				alert("deleteProgramFile 失败");
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
					queryProgramList();
				}
			}
		});
		
	}
	
	
	//----------------------------删除文件-------------------