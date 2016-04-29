package com.brxy.school.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author xiaobing
 * @version 2016年4月28日 上午11:11:48
 */
@Controller
@RequestMapping("file")
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@RequestMapping("main")
	public String fileMain() {

		logger.info("file main");
		return "fileupload";

	}

	@ResponseBody
	@RequestMapping(value="fileUpload")//,method=RequestMethod.POST,consumes="multipart/form-data",produces={"application/json", "application/xml"}
	public Map<String, Object> fileUpload(HttpServletRequest request  ,@RequestParam("file") MultipartFile file) {
		
		Map<String, Object> result = new HashMap<>();
		logger.info("file upload");
		if (!file.isEmpty()) {
			int pre = (int) System.currentTimeMillis();
			// 取得当前上传文件的文件名称
			String myFileName = file.getOriginalFilename();
			// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
			if (myFileName.trim() != "") {
				logger.info(myFileName);
				// 重命名上传后的文件名
				String fileName = "demoUpload" + file.getOriginalFilename();
				// 定义上传路径
				String path = request.getSession().getServletContext().getRealPath("/") +"upload/"+ fileName;  
				String path2 = "E:/upload/"+fileName;
				
				File localFile = new File(path2);
				try {
					file.transferTo(localFile);
					result.put("result", true);
					result.put("message", "upload file success filepath=" + path);
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
