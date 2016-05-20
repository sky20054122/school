

$(function(){
	
	var bar = $('#uploadProgress');
	var percent = $('#uploadProgress span');
	   
	
	$('form').ajaxForm({
		 
		dataType:'json',
	    beforeSend: function() {
	        var percentVal = '0%';
	        bar.width(percentVal)
	        percent.html("上传进度: "+percentVal);
	        $("#status").html("上传文件");
	    },
	    uploadProgress: function(event, position, total, percentComplete) {
	        var percentVal = percentComplete + '%';
	        bar.width(percentVal)
	        percent.html("上传进度: "+percentVal);
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
	        percent.html("上传进度: "+percentVal);
	    },
		complete: function(xhr) {
			//status.html(xhr.responseText);
		}
	}); 
	
	$('#file').change(function(){
		var file = this.files[0];
	    name = file.name; //文件名称
	    size = file.size;  //文件大小
	    type = file.type; // audio/mpeg  判断音频格式是不是MP3
	    if(type&&type!=""){
	    	if(type=="audio/mpeg"||type=="audio/mp3"){
			    var fileExtension = getFileExtension(name);
			    if(fileExtension&&fileExtension==".mp3"){
			    	$("#fileExtension").val(fileExtension);
			    	$("#fileSize").val(size);
			    	var reader = new FileReader();
	                 reader.onload=function(){
	                 	$("#preview").html("<audio style='width:100%;' id='myAudio' src='"+this.result+"' autoplay='autoplay' controls='controls'>Your browser does not support the audio tag.</audio>");
				    		Media = document.getElementById("myAudio"); //返回文件时长 单位秒
				    		Media.oncanplay=function(){
						    	var duration = Math.ceil(Media.duration); 
						    	$("#duration").val(duration);
							    console.log("name="+name+" size="+size+" type="+type+" fileExtension="+fileExtension+" duration="+duration);
				    		};
	                 };
	                reader.readAsDataURL(file);
	                $("#status").html("计算文件MD5");
	                Md5File(file, "status", function(md5){
	                	$("#fileMD5").val(md5);
	                	
	                	$("#uploadBtn").show();
	                });
			    }else{
			    	alert("文件 后缀不是 .mp3  fileExtension="+fileExtension);
			    }
	    		
	    	}else{
	    		alert("文件 MIME类型不是 audio/mpeg或 audio/mp3  type="+type);
	    	}
	    }else{
	    	alert("unknown file MIME type");
	    	
	    }
	});
	
	
	/**
	 * 根据文件名称获取文件的后缀  包含.
	 */
	function getFileExtension(fileName){
		var fileExtension = fileName.substring(fileName.lastIndexOf('.') ).toLowerCase(); //包含.  .mp3  
		return fileExtension;
	}
	
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