package com.sist.web;

import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.sist.service.*;
import com.sist.vo.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
public class GoodsController {

	private final GoodsService gService;
	
	@GetMapping("goods/detail_before.do")
	public String food_detail_before(int no,HttpServletResponse response, RedirectAttributes ra) 
	{
	
		Cookie cookie = new Cookie("goods_"+no, String.valueOf(no));
		cookie.setPath("/");
		cookie.setMaxAge(60*60*24);
		response.addCookie(cookie);
		ra.addAttribute("no", no); 
		
		return "redirect:../goods/detail.do";
		
	
	}
	
	@GetMapping("goods/detail.do")
	public String goods_detail(int no,Model model)
	{
		GoodsVO vo = gService.goodsDetailData(no);
		model.addAttribute("vo",vo);
		model.addAttribute("main_jsp", "../goods/detail.jsp");
		return "main/main";
	}
	
}
