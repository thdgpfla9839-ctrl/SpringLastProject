package com.sist.service;
import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.vo.*;

import lombok.RequiredArgsConstructor;

import com.sist.mapper.*;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

	// 스프링에서 주소값을 제공해주고 있음
	private final BoardMapper mapper; // datasoure.xml에서 구현이 됨

	// 목록출력
	@Override
	public List<BoardVO> boardListData(int start) {
		// TODO Auto-generated method stub
		return mapper.boardListData(start);
	}

	// 번호출력
	@Override
	public int boardRowCount() {
		// TODO Auto-generated method stub
		return mapper.boardRowCount();
	}

	// 추가
	@Override
	public void boardInsert(BoardVO vo) {
		// TODO Auto-generated method stub
		mapper.boardInsert(vo);
	}


	// 상세보기
	@Override
	public BoardVO boardDetailData(int no) {
		// TODO Auto-generated method stub
		mapper.boardHitIncrement(no);
		return mapper.boardDetailData(no);
	}

	// 답변하기
	@Override
	@Transactional // 이 부분이 AOP가 적용되는 부분 => 여기 부분 주석참고하기
	public void boardReplyInsert(int pno, BoardVO vo) {
		// TODO Auto-generated method stub
		BoardVO pvo = mapper.boardParentInfoData(pno);
		mapper.boardStepIncrement(pvo.getGroup_id(), pvo.getGroup_step());
		
		vo.setGroup_id(pvo.getGroup_id());
		vo.setGroup_step(pvo.getGroup_step()+1);
		vo.setGroup_tab(pvo.getGroup_tab()+1);
		vo.setRoot(pno);
		vo.setDepth(0);
		mapper.boardReplyInsert(vo);
		mapper.boardDepthIncrement(pno);
	}

	
}
