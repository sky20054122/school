<%

local nixio = require "nixio"
local brxydispatcher = require "luci.tools.brxydispatcher"
local table = require "table"

local http = luci.http
	
local dir, fd
dir = "/tmp/upload/"
nixio.fs.mkdir(dir)
luci.http.setfilehandler(
	function(meta, chunk, eof)
		if not fd then
			if not meta then return end
			if not meta.file then return end
			fd = nixio.open(dir .. meta.file, "w")
			if not fd then
				nixio.syslog("debug","Create upload file error.")
				--um.value = translate("Create upload file error.")
				return
			end
		end
		if chunk and fd then
			fd:write(chunk)
		end
		if eof and fd then
			fd:close()
			fd = nil
			nixio.syslog("debug","File saved to" .. ' "/tmp/upload/' .. meta.file .. '"')
			local re,msg = brxydispatcher.saveFile(dir,meta.file)
			-- um.value = translate("File saved to") .. ' "/tmp/upload/' .. meta.file .. '"'
		end
	end
)
	
if http.formvalue('act') == 'queryProgramList' then
	http.prepare_content("application/json")	
	local data = brxydispatcher.queryProgramList()
	http.write_json({data=data})
	return
	
elseif luci.http.formvalue("image")  then
		
	
end



%>


<%+header%>

<input type="hidden" id="baseUrl" value="<%=resource%>" />
<input type="hidden" id="loginUrl"	value="<%=luci.dispatcher.build_url()%>" />
<link rel="stylesheet" type="text/css"	href="<%=resource%>/webuploader/webuploader.css">
<link rel="stylesheet" type="text/css"	href="<%=resource%>/jquery-ui/jquery-ui.min.css">
<form id="aioConfigForm">

	<div class="cbi-map" id="cbi-unitmachine">


		<fieldset>
			<legend> 文件列表 </legend>
			<table>
				<tr>
					<td style="text-align: right;" colspan="6"><input
						type="button" id="addUploadBtn" value="新增上传"
						class="cbi-button cbi-button-add"></td>
				</tr>
				<tr>
					<td>文件ID</td>
					<td>文件名称</td>
					<td>持续时长</td>
					<td>上传时间</td>
					<td>备注信息</td>
					<td>操作</td>
				</tr>
				<tbody id="fileListBody">

				</tbody>
			</table>
		</fieldset>

</div>


</form>



<fieldset class="cbi-section">
	<legend>文件上传</legend>

	<form method="post" action="<%=REQUEST_URI%>" enctype="multipart/form-data">
		<div class="cbi-value-field">
			<input type="file" name="image" id="image" /> 
			<input type="submit" class="cbi-button cbi-input-apply" value="上传文件" />
		</div>
		
	</form>

</fieldset>

<script type="text/javascript" src="<%=resource%>/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="<%=resource%>/webuploader/webuploader.min.js"></script>
<script type="text/javascript"
	src="<%=resource%>/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="<%=resource%>/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=resource%>/brxyTool.js"></script>
<script type="text/javascript">
	$(function() {

		queryProgramList();
	});

	function queryProgramList() {
		console.log("luci page queryProgramList ");
		$
				.ajax({
					type : "post",
					dataType : "json",
					url : '<%=REQUEST_URI%>',
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
					success : function(result) {
						var html = "";
						$(result.data).each(function(index, el) {
							html += "<tr><td>"	+ el.id
							+ "</td><td>"+ el.name
							+ "</td><td>"+ el.duration
							+ "</td><td>"+ el.recordDate
							+ "</td><td>"+ el.comment
							+ "</td><td><input type='button' name='delete' id='"+el.id+"' value='删除' class='cbi-button cbi-button-remove'></td></tr>";
						});
						$("#fileListBody").html(html);
					}
				});
	}

	//+++++++++++++++++ 文件上传相关+++++++++++++++++++++++++++


	//-----------------------------文件上传----------------------
</script>

<%+footer%>