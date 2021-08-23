package org.zerock.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {
	public ArrayList<BoardVO> getList(); // 게시판 목록을 불러옴
	public ArrayList<BoardVO> getListWithPaging(Criteria cri); // 게시판 목록을 불러옴
	public int getTotalCount(Criteria cri);
	public void insert(BoardVO board); // 게시물 작성
	public void insertSelectKey(BoardVO board); // insert문이 실행되고 생성되는 primarykey 값을 알아야 하는 경우
	public BoardVO read(int bno); // 게시판의 번호가 매개변수 bno와 동일한 게시글을 읽기
	public boolean delete(int bno); // pk값을 이용해 처리하며 정상적으로 게시물 삭제가 진행될 경우를 판별하기 위해 boolean타입을 사용
	public boolean update(BoardVO board); // update성공여부를 판별하기 위해 boolean을 사용
	// 댓글건수 update
	public void updateReplycnt(@Param("bno")int bno, @Param("amount")int amount);
}
