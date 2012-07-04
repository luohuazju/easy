package com.sillycat.easytalker.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class FileController {

	private static final Logger logger = Logger.getLogger(FileController.class);

	@RequestMapping("/fileupload.do")
	public void uploadFile(MultipartHttpServletRequest request,
			HttpServletResponse response) throws IOException {
		MultipartFile multipartFile = request.getFile("filemaps");
		if (null != multipartFile
				&& null != multipartFile.getOriginalFilename()) {
			logger.info("orginal file name = "
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
	}

}
