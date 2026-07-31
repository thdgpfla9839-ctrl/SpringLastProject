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

	// 수정하기
	@Override
	public void boardUpdateData(BoardVO vo) {
		// TODO Auto-generated method stub
		mapper.boardUpdateData(vo);
		
		
	}

	// 삭제하기
	@Override
	@Transactional
	public boolean boardDelete(int no, String pwd) {
		
		boolean bCheck = false; // 비번틀리면 여기가 수행되므로 if문에 false 내용 쓸 필요 없음
		BoardVO vo =mapper.boardInfoData(no);
		String db_pwd = mapper.boardGetPassword(no);
		
		if(db_pwd.equals(pwd))
		{
			bCheck=true;
			if(vo.getDepth()==0) // 게시글에 답변이 달려있는데 게시글을 삭제하면 답변만 남아있잖아 그게 문제임 depth=0이라는 건 답변이 없다는 소리, 대댓글은 해당게시글의 depth로 안 봄
			{
			  mapper.boardDelete(no);	
			}
			else
			{
			   BoardVO bvo = new BoardVO();
			   bvo.setContent("관리자가 삭제한 게시물입니다.");
			   bvo.setSubject("관리자가 삭제한 게시물입니다");
			   bvo.setNo(no);
			   
			   mapper.boardMsgUpdate(bvo); // 이걸 수정해줄거야
			}
			
			// 비밀번호가 같으면 루트에 대해서 뎁스를 감소해줘야함
			//게시글에 대한 댓글이 두개야 그중 한개를 지웠으면 게시글이 갖고 잇던 댓글 개수도 하나 지워져야지 그 소리야
			mapper.boardDepthDecrement(vo.getRoot());
		}
		return bCheck;
	}

	
}
