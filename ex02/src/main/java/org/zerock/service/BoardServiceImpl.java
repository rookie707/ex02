package org.zerock.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardAttachMapper;
import org.zerock.mapper.BoardMapper;
@Service
public class BoardServiceImpl implements BoardService {
	private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);
	@Autowired
	private BoardMapper mapper;
	
	@Autowired
	private BoardAttachMapper attachMapper;
	// 게시판 글쓰기 구현부
	@Override
	public void register(BoardVO board) {
		logger.info("register......"+board);
		// tbl_board테이블 insert
		mapper.insertSelectKey(board);
		// 사용자가 파일을 선택하지 않았을 때는 tbl_attach테이블에 insert할 필요가 없으므로 실행하지 않도록 한다
		if(board.getAttachList()==null || board.getAttachList().size() <=0) {
			return;
		}
		board.getAttachList().forEach(attach->{
			// tbl_attach테이블 insert
			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
	}
	// 게시판 상세페이지
	public BoardVO get(int bno) {
		return mapper.read(bno);
	}
	// 게시판 수정
	public boolean modify(BoardVO board) {
		return mapper.update(board); // 업데이트의 성공여부를 판별하기 위해 boolean타입으로 함
	}
	// 게시판 글삭제
	public boolean remove(int bno) {
		return mapper.delete(bno);
	}
	// 게시판 목록리스트
	public ArrayList<BoardVO> getList() {
		return mapper.getList();
	}
	// 게시판 목록리스트+페이징처리
	public ArrayList<BoardVO> getListWithPaging(Criteria cri) {
		return mapper.getListWithPaging(cri);
	}
	// 게시판 글목록 리스트+페이징처리
	public int getTotalCount(Criteria cri) {
		return mapper.getTotalCount(cri);
	}
	// 게시판 상세페이지의 파일업로드한 이미지에 대한 데이터를 처리
	public ArrayList<BoardAttachVO> getAttachList(int bno){
		logger.info("getAttachList..."+bno);
		return attachMapper.findByBno(bno);
	}
}
