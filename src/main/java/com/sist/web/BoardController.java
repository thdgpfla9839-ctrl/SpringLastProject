package com.sist.web;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// 서비스에서 제공하는 결과값을 브라우저로 전송
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sist.service.BoardService;
import com.sist.vo.BoardVO;

import lombok.RequiredArgsConstructor;
@Controller
@RequiredArgsConstructor
public class BoardController {

	private final BoardService bService;

	
	@GetMapping("board/list.do")
	public String board_list(String page, Model model)
	{
		if(page==null)
			page="1";
		int curpage = Integer.parseInt(page);
		final int ROWSIZE = 10;
		int start=(ROWSIZE*curpage)-ROWSIZE;
		List<BoardVO> list = bService.boardListData(start);
		int count = bService.boardRowCount();
		int totalpage = (int)(Math.ceil(count/10.0)); // 총페이지
		count = count-((curpage*ROWSIZE)-ROWSIZE); // 페이지 넘어갈 때마다 10씩 뺴줘라
		
		
		model.addAttribute("list",list);
		model.addAttribute("curpage",curpage);
		model.addAttribute("totalpage",totalpage);
		model.addAttribute("count",count);
		model.addAttribute("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		model.addAttribute("msg","관리자가 삭제한 게시물입니다");
		model.addAttribute("main_jsp","../board/list.jsp");
		return "main/main"; // 포워드 기법 => 리퀘스트를 전송할 목족
	}
	
	@GetMapping("board/insert.do")
	public String board_insert(Model model)
	{
		model.addAttribute("main_jsp","../board/insert.jsp");
		return "main/main";
	}
	
	@PostMapping("board/insert_ok.do")
	public String board_insert_ok(BoardVO vo)
	{
		bService.boardInsert(vo);
		
		return "redirect:../board/list.do";
	}
	
	@GetMapping("board/detail.do")
	public String board_detail(int no, Model model)
	{
		BoardVO vo=bService.boardDetailData(no);
		model.addAttribute("vo",vo);
		model.addAttribute("main_jsp","../board/detail.jsp");
		return "main/main";
	}
	
	@GetMapping("board/reply.do")
	public String board_reply(int no, Model model)
	{
		model.addAttribute("no",no); // 누구 게시글에 대한 답글인지를 위해 번호 보내주는 것임
		model.addAttribute("main_jsp","../board/reply.jsp");
		return "main/main";
	}
	
	@PostMapping("board/reply_ok.do")
	public String board_reply_ok(int pno, BoardVO vo)
	{
	   bService.boardReplyInsert(pno, vo);
	   return "redirect:../board/list.do";	
	}
	
	// 게시글 수정하기
	@GetMapping("board/update.do")
	public String board_update(int no, Model model)
	{
		BoardVO vo = bService.boardDetailData(no);
		
		model.addAttribute("vo",vo);
		model.addAttribute("main_jsp","../board/update.jsp");
		return "main/main";
	}
	
	// 게시글 삭제하기
	@GetMapping("board/delete.do")
	public String board_delete(int no, Model model)
	{
		model.addAttribute("no",no); 
		model.addAttribute("main_jsp","../board/delete.jsp");
		return "main/main";
	}
	/*
	 * // 게시글 삭제완료
	 * 
	 * @PostMapping("board/delete_ok.do") public String board_delete_ok(int no,
	 * String pwd) { // 게시글 삭제가 되면 화면 출력할 거 없고 그냥 목록으로 돌아가면 되잖아 return
	 * "redirect:../board/list.do";
	 * 
	 * } 여기서 작성해버리면 jsp안에 스크립트를 보낼 수가 없대 
	 */

}
