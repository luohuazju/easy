package com.sillycat.easytalker.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class FileController {

	private final Logger logger = Logger.getLogger(FileController.class);

	@RequestMapping("/ajaxfileupload.do")
	public void ajaxfileupload(MultipartHttpServletRequest request,
			HttpServletResponse response) throws IOException {
		MultipartFile multipartFile = request.getFile("filemaps");
		if (null != multipartFile
				&& null != multipartFile.getOriginalFilename()) {
			logger.info("ajax orginal file name = "
					+ multipartFile.getOriginalFilename());
			// OutputStream os = null;
			// InputStream is = null;
			// try {
			// is = multipartFile.getInputStream();
			// File file = new File("D:/"
			// + multipartFile.getOriginalFilename());
			// os = new FileOutputStream(file);
			//
			// byte[] b = new byte[1024];
			// int len = 0;
			// while ((len = is.read(b)) != -1) {
			// os.write(b, 0, len);
			// }
			// } catch (Exception e) {
			// logger.error("Error Message:" + e);
			// } finally {
			// os.close();
			// is.close();
			// }
		}
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		out.write("{success:true}");
		out.close();
	}

	@RequestMapping(value = "/swfupload.do", method = RequestMethod.POST)
	public void swfupload(
			@RequestParam(value = "fileName", required = false) String fileName,
			@RequestParam(value = "Filedata") MultipartFile file,
			HttpServletResponse response) throws IOException {
		logger.info("swf orginal file name = " + file.getOriginalFilename()
				+ " fileName = " + fileName);
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		out.write("{success:true}");
		out.close();
	}
}
