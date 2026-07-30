package com.sist.web;

import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.sist.service.*;
import com.sist.vo.*;

import lombok.RequiredArgsConstructor;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final GoodsService gService; // 이게 DB
	
	@GetMapping("main/main.do")
	public String main_main(String page, Model model,HttpServletRequest request) // 쿠키 가져와야해서 requset를 추가해줌
	{
		if(page==null)
			page="1";
		int curpage = Integer.parseInt(page);
		final int ROWSIZE = 12;
		int start=(ROWSIZE*curpage)-(ROWSIZE-1);
		int end=ROWSIZE*curpage;
		
		List<GoodsVO> list = gService.goodsListData(start, end);
		int totalpage = gService.goodsTotalPage();
		
		// 페이지 나누기
		final int BLOCK=10;
		int startPage = ((curpage-1)/BLOCK*BLOCK)+1;
		int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		
		if(endPage>totalpage)
			endPage=totalpage;
		
		// 출력할 내용 보내기
		model.addAttribute("list",list);
		model.addAttribute("curpage",curpage);
		model.addAttribute("totalpage",totalpage);
		model.addAttribute("startPage",startPage);
		model.addAttribute("endPage",endPage);
		
		model.addAttribute("main_jsp","../main/home.jsp");
		
		List<GoodsVO> cList = new ArrayList<GoodsVO>();
		Cookie[] cookies = request.getCookies();
		if(cookies!=null)
		{
			// 방문기록 최신순으로 가져오기
			for(int i=cookies.length-1;i>=0;i--)
			{
				if(cookies[i].getName().startsWith("goods_")) // 지금은 키를 가져오기 위해 겟네임함
				{
					if(cookies[i].getName().equals("goods_null"))
						continue;
				   String no = cookies[i].getValue();
				   GoodsVO vo = gService.goodsDetailData(Integer.parseInt(no));
				   
				   cList.add(vo);
				}
			}
		}
		
	    model.addAttribute("cList", cList);
	    model.addAttribute("size",cList.size());
	    
	    /*
	     *  내장객체의 사용처 알기
	     *  request / response = > 쿠키나 파일업로드에서 많이 사용
	     *  session => 보안처리나 회원 관련에서 많이 사용
	     *  RedirectAttributes => 이미 있는 화면으로 이동할 때
	     */
		return "main/main";
	}
}
