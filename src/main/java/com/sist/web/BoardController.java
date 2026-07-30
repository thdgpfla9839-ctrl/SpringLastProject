package com.sist.web;
// 서비스에서 제공하는 결과값을 브라우저로 전송
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.*;
import com.sist.service.*;
import com.sist.vo.*;

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
		model.addAttribute("today",new SimpleDateFormat("yyyy-mm-dd").format(new Date()));
		
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
		BoardVO vo =bService.boardDetailData(no);
		model.addAttribute("vo",vo);
		model.addAttribute("main_jsp","../board/detail.jsp");
		return "main/main";
	}
	
}
