package com.acorn.basic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/*
 *  [ jsp 페이지를 사용하기 위한 설정 ]
 *  1. pom.xml에 tomcat-embed-jasper와 jstl dependency를 추가한다.
 *  2. /webapp/WEB-INF/views/ 구조로 폴더를 src/main 하위에 만든다.
 *  3. view page의 prefix와 suffix 설정을 추가한다.
 *     application.properties에 설정한다면,
 *     
 *     spring.mvc.view.prefix=/WEB-INF/views/
 *     spring.mvc.view.suffix=.jsp
 *     
 *     위 내용을 추가한다.
 */
@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home(HttpServletRequest request) {
		
		List<String> noticeList = new ArrayList<>();
		noticeList.add("Spring Boot Start");
		noticeList.add("asd");
		noticeList.add("zxc");
		
		request.setAttribute("noticeList", noticeList);
		
		return "home";
	}
	
	@PostMapping("/upload")
	public String upload(HttpServletRequest request, @RequestParam MultipartFile file) {
		//업로드된 파일의 원본파일명
		String orgFileName = file.getOriginalFilename();
		//upload 폴더에 저장할 파일명을 직접구성한다.
		// 1234123424343xxx.jpg
		String saveFileName=System.currentTimeMillis()+orgFileName;
		  
		// webapp/resources/upload/ 폴더에 파일을 저장하고 싶다면   
		String realPath=request.getServletContext().getRealPath("/resources/upload");
		// upload 폴더가 존재하지 않을경우 만들기 위한 File 객체 생성
		File upload=new File(realPath);
		if(!upload.exists()) {//만일 존재 하지 않으면
			upload.mkdir(); //만들어준다.
		}
		try {
			//파일을 저장할 전체 경로를 구성한다.  
			String savePath=realPath+File.separator+saveFileName;
			//임시폴더에 업로드된 파일을 원하는 파일을 저장할 경로에 전송한다.
			file.transferTo(new File(savePath));
		}catch(Exception e) {
			e.printStackTrace();
		}
		  
		//파일의 크기 byte
		long fileSize = file.getSize();
		  
		request.setAttribute("fileName", orgFileName);
		request.setAttribute("fileSize", fileSize);
		  
		return "upload";
	}
}
