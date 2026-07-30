package com.sist.mapper;
// 여기는 데이터베이스 연결
import java.util.*;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sist.vo.*;

/*  group_로 시작하는 건 => 답변 형식을 나타냄
    root, depth => 삭제와 관련
 *  group_id => 답변 모음
 *  group_step => 답변 안 출력 순서
 *  group_tab => 간격 조절
 *  
 *   root => 어느 게시물의 답변인지
 *   depth => 답변이 몇개인지 확인
 * 
 */
public interface BoardMapper {

	@Select("SELECT no,subject,name,TO_CHAR(regdate,'yyyy-mm-dd') as dbday,hit,group_tab  "
			+"FROM SpringReplyBoard "
			+"ORDER BY group_id DESC ,group_step ASC "
			+"OFFSET #{start} ROWS FETCH NEXT 10 ROWS ONLY")
	public List<BoardVO> boardListData(int start);
	
	
	@Select("SELECT COUNT(*) FROM SpringReplyBoard")
	public int boardRowCount();
	
	
	@Insert("INSERT INTO SpringReplyBoard(no,name,subject,content,pwd,group_id) "
			+"VALUES(srb_no_seq.nextval,#{name},#{subject},#{content},#{pwd},"
			+"(SELECT NVL(MAX(group_id)+1,1) FROM SpringReplyBoard))") // 새 게시글을 쓸 때 하나 증가해준다
	public void boardInsert(BoardVO vo);
	
	
	// 상세보기
	@Update("UPDATE SpringReplyBoard SET "
			+"hit=hit+1 "
			+"WHERE no=#{no}")
	public void boardHitIncrement(int no);
	
	@Select("SELECT no,name,subject,content,TO_CHAR(regdate,'yyyy-mm-dd') as dbday,hit "
			+"FROM SpringReplyBoard "
			+"WHERE no=#{no}")
	public BoardVO boardDetailData(int no);
	
	// 1. 답변하기 => 여기서 트랜젝션 사용할 예정
	// 1) 상위 데이터 읽기
	@Select("SELECT group_id,group_step,group_tab "
			 +"FROM springReplyBoard "
			 +"WHERE no=#{no}")
	  public BoardVO boardParentInfoData(int no);
	
	// 2) update
	@Update("UPDATE springReplyBoard SET "
			 +"group_step=group_step+1 "
			 +"WHERE group_id=#{group_id} AND group_step>#{group_step}")
	  public void boardStepIncrement(@Param("group_id") int group_id,
			  @Param("group_step") int group_step); // 이 안에는 vo를 쓰거나 map을 쓰거나 혹은 지금처럼 쓸 수 잇다
	
	// 3) insert
	@Insert("INSERT INTO springReplyBoard(no,name,subject,content,pwd,group_id,group_step,group_tab,root,depth) "
			 +"VALUES(srb_no_seq.nextval,#{name},#{subject},"
			 +"#{content},#{pwd},"
			 +"#{group_id},#{group_step},#{group_tab},#{root},#{depth})")
 public void boardReplyInsert(BoardVO vo);
	
	// 4) update
	 @Update("UPDATE springReplyBoard SET "
			 +"depth=depth+1 "
			 +"WHERE no=#{no}")
	  public void boardDepthIncrement(int no);
	
	// 2. 수정
	 @Update("UPDATE springReplyBoard "
	 		 +"SET subject=#{subject},content=#{content} "
			 +"WHERE no=#{no}")
	 public void boardUpdateData(BoardVO vo);
	 
	// 3. 삭제  => 여기도 트랜젝션 사용
	
}
