package com.brxy.school.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.brxy.school.service.ProgramService;

/**
 *
 * @author xiaobing
 * @version 2016年4月28日 上午11:11:48
 * 文件上传
 */
@Controller
@RequestMapping("file")
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private ProgramService programService;

	@RequestMapping("main")
	public String fileMain() {

		logger.info("file main");
		return "fileUpload";

	}

	@ResponseBody
	@RequestMapping(value="fileUpload")//,method=RequestMethod.POST,consumes="multipart/form-data",produces={"application/json", "application/xml"}
	public Map<String, Object> fileUpload(HttpServletRequest request  ,
			@RequestParam("file") MultipartFile file,
			@RequestParam("fileMD5")String fileMD5,
			@RequestParam("duration")Long duration,
			@RequestParam("fileExtension")String fileExtension,
			@RequestParam("fileSize")Long fileSize) {
		Map<String, Object> result = new HashMap<>();
		logger.info("file upload");
		if (!file.isEmpty()) {
			int pre = (int) System.currentTimeMillis();
			// 取得当前上传文件的文件名称
			String myFileName = file.getOriginalFilename();
			logger.info("myFileName="+myFileName+ " fileMD5="+fileMD5+" duration="+duration+" fileExtension="+fileExtension);
			// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
			if (myFileName.trim() != "") {
				logger.info(myFileName);
				// 重命名上传后的文件名
				String fileName = "demoUpload" + file.getOriginalFilename();
				// 定义上传路径
				String path = request.getSession().getServletContext().getRealPath("/") +"upload/"+ fileName;  
				String path2 = "E:/upload/"+fileName;
				logger.info("file save path="+path2);
				File localFile = new File(path2);
				try {
					file.transferTo(localFile);
					return this.programService.add(myFileName, fileMD5, duration, fileExtension,fileSize);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
					result.put("result", false);
					result.put("message", "upload file save fail " + e.getMessage());
					return result;
				}
			}
			// 记录上传该文件后的时间
			int finaltime = (int) System.currentTimeMillis();
			logger.info("upload cost time" + (finaltime - pre));
		} else {
			result.put("result", false);
			result.put("message", "You failed to upload  because the file was empty.");
		}
		return result;
	}

}
