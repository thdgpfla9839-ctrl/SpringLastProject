package com.sist.service;
// 여기는 sql 문장을 조합해서 결과물 => BI(기능 통합)
import java.util.List;



import com.sist.vo.BoardVO;

public interface BoardService {

	
	public List<BoardVO> boardListData(int start);
	public int boardRowCount();
	public void boardInsert(BoardVO vo);
		// 상세보기
    public BoardVO boardDetailData(int no);
		// 답변하기
	public void boardReplyInsert(int pno, BoardVO vo);
	// 수정하기
	 public void boardUpdateData(BoardVO vo);
		
	 //게시글 삭제하기(게시물 비번이 맞는지 틀린지 판단해야해서 불린)
	 public boolean boardDelete(int no, String pwd);
 
		
		
}
