package org.zerock.controller;

import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.pageDTO;
import org.zerock.service.BoardService;

@Controller
@RequestMapping("board")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	@Autowired
	private BoardService service;
	// 글쓰기 화면
	@GetMapping("register")
	public void register() {
		logger.info("register");
	}
	// 글쓰기 버튼 클릭
	@PostMapping("register")
	public String registerPost(BoardVO board) {
		logger.info("registerpost : "+board);
		// 사용자가 파일선택을 클릭해서 파일업로드를 하나라도 했으면, 그 파일에 대한 정보를 for문을 이용해서 가져와라.
		if(board.getAttachList()!=null) {
			board.getAttachList().forEach(attach->logger.info(""+attach));
		}
		service.register(board);
		return "redirect:/board/list";
	}
	// 글 목록
	@GetMapping("list")
	public void list(Model model, Criteria cri) {
		int count = service.getTotalCount(cri);
		model.addAttribute("list", service.getListWithPaging(cri)); // service에 목록을 요청하여 model에 저장한 후 "list"인스턴스에 데이터를 전달 
		model.addAttribute("pageMaker", new pageDTO(cri, count)); 
		logger.info(""+service.getList());
	}
	// 글 읽기
	@GetMapping("get")
	public void get(@RequestParam("bno") int bno, Model model) { // 매개변수 bno로 db에 데이터를 요청하고 model을 통해 return된 값을 받아옴
		logger.info("get");
		model.addAttribute("board", service.get(bno)); // service에서 데이터를 받아서 board인스턴스에 전달
	}
	@PostMapping("modify")
	public String modify(BoardVO board, RedirectAttributes rttr) {
		logger.info("modify");
		if(service.modify(board)) {
			rttr.addAttribute("result", "success");
			rttr.addAttribute("bno", board.getBno());
		}
		return "redirect:/board/get";
	}
	@PostMapping("remove")
	public String remove(@RequestParam("bno") int bno) {
		service.remove(bno);
		logger.info("remove");
		return "redirect:/board/list";
	}
	
	@GetMapping(value="getAttachList" , produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<ArrayList<BoardAttachVO>> getAttachList(int bno){
		logger.info("getAttachList = "+bno);
		return new ResponseEntity<>(service.getAttachList(bno),HttpStatus.OK); // service.getAttachList(bno)의 값을 ajax으로 보낸다
	}
}
