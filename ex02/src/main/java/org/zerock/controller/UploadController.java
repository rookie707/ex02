package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.BoardAttachVO;

import net.coobird.thumbnailator.Thumbnailator;

@Controller
public class UploadController {
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		logger.info("fileName : " +fileName);
		File file = new File("c:\\upload\\temp\\"+fileName);
		logger.info("file : "+file);
		
		ResponseEntity<byte[]>result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@GetMapping(value="/uploadForm") // jsp와 연결
	public void uploadForm() {
		logger.info("uploadform test");
	}
	@PostMapping(value="uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile) {
		String uploadFolder="c:\\upload\\temp";
		for (MultipartFile multipartFile : uploadFile) { // 향상된 for문
			logger.info("Upload File Name : " + multipartFile.getOriginalFilename());
			logger.info("Upload File Size : " + multipartFile.getSize());
			
			File saveFile = new File(uploadFolder ,multipartFile.getOriginalFilename());
			
			try {
				multipartFile.transferTo(saveFile);
			}catch(Exception e) {
				logger.info("");
			}
		}
		
	}
	// form을 이용한 업로드 끝
	@GetMapping(value="/uploadAjax") 
	public void uploadAjax() {
		logger.info("연결성공");
	}
	// getFolder메서드 선언(page508)
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(); // 기본생성자로 매개변수가 없으면 오늘 날짜를 가져옴
		String str =  sdf.format(date); // date의 값을 yyyy-MM-dd형식으로 변환함
		// 2021-08-19의 문자열 중에서 "-"를 선택하여 2021\\08\\19로 변환시킨다
		return str.replace("-", File.separator); // separator = \\
	}
	
	// checkImage메서드 선언(page513)
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			// 업로드한 파일이 이미지파일인지 체크
			return contentType.startsWith("image");
		}catch(IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	@PostMapping(value="/uploadAjaxAction", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<ArrayList<BoardAttachVO>> uploadAjaxAction(MultipartFile[] uploadFile){
		ArrayList<BoardAttachVO> list = new ArrayList<>();
		String uploadFolder="c:\\upload\\temp";
		String uploadFolderPath = getFolder(); // 함수호출결과 2021\\08\\19
		// 폴더생성, c:\\upload\\temp\\2021\\08\\19
		File uploadpath = new File(uploadFolder, uploadFolderPath);
		if (uploadpath.exists() == false) { // 기존에 폴더가 존재하면 false
			// 폴더생성(mkdirs())
			uploadpath.mkdirs(); 
		}
		for (MultipartFile multipartFile : uploadFile) { // 향상된 for문
			logger.info("Upload File Name : " + multipartFile.getOriginalFilename());
			logger.info("Upload File Size : " + multipartFile.getSize());
			
			BoardAttachVO attachDTO = new BoardAttachVO();
			// internet explorer를 제외한 브라우저에서 파일명
			String uploadFileName=multipartFile.getOriginalFilename();
			// internet explorer에서 파일명
			uploadFileName=uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
			// vo에 파일이름을 저장
			attachDTO.setFilename(uploadFileName);
			
			UUID uuid = UUID.randomUUID();
			// 파일이름에 uuid값을 합침
			uploadFileName = uuid.toString() + "_" +uploadFileName;
			try {
				// 
				File saveFile = new File(uploadpath ,uploadFileName);
				multipartFile.transferTo(saveFile); // transferTo()파일을 전송
				// vo에 uuid와 경로를 저장, 나중에 db와 연결하기 위함
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadpath(uploadFolderPath);
				
				if(checkImageType(saveFile)) { // 업로드한 파일이 이미지파일 일경우
					attachDTO.setFiletype(true);
					// FileOutputStream 파일을 생성, 파일명에 s_를 앞에 더해서 생성함
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadpath, "s_"+uploadFileName));
					// 크기 100x100의 썸네일을 생성
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100,100);
					thumbnail.close();	
				}
				list.add(attachDTO);
			}catch(Exception e) {
				logger.info(e.getMessage());
			}
		} // end for
		// js와 연결되어서 list는 success의 매개변수 result로 값을 전송함
		return new ResponseEntity<>(list, HttpStatus.OK);
	} 
	
}
