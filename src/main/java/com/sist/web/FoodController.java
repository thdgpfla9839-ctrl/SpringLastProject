package com.sist.web;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sist.service.*;
import com.sist.vo.*;

import lombok.RequiredArgsConstructor;
@Controller
@RequiredArgsConstructor
public class FoodController {

	private final FoodService fService;
	
	@GetMapping("food/detail_before.do")
	public String food_detail_before(int no,HttpServletResponse response, RedirectAttributes ra) // 쿠키 저장하기 위해서 response 매개변수 추가해줌
	{
	
		// 쿠키 생성 => 쿠키는 매개변수가 string만 저장할 수 있어서 우리는 no를 정수로 설정했어 그대로 넣으면 에러나
		Cookie cookie = new Cookie("food_"+no, String.valueOf(no));
		cookie.setPath("/");
		cookie.setMaxAge(60*60*24);
		response.addCookie(cookie);
		ra.addAttribute("no", no); // 이거 주면 return ~ ?no="no 이거 붙여줄거야
		
		return "redirect:../food/detail.do";
		
		// 조회수 증가, 쿠키 저장된 값 출력할 떄는 back을 하면 안 된다 그냥 링크를 걸어줘야함
	}
	
	@GetMapping("food/detail.do")
	// <form> => get이나 post 방식
	// 나머지 태그는 => 전부 get 방식
	// location.href => get 방식
	// redirect: => get 방식
	// ajax => get이나 post 방식
	// axios => axios.get( ) axios.post( ) => 함수에 따라 다르대?
	
	public String food_detail(int no,Model model)
	{
		FoodVO vo = fService.foodDetailData(no);
		model.addAttribute("vo",vo);
		model.addAttribute("main_jsp", "../food/detail.jsp");
		return "main/main";
	}
	
}
