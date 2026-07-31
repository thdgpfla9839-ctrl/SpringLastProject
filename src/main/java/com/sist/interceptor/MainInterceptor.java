package com.sist.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/*
 *  진입하는 과정 :
 *               사용자 => main.do 라고 날림
 *                |
 *                디스패쳐서블릿이 받음
 *                | -> 핸들러맵핑 찾기 전에 인터셉터 찾기 먼저함 => 프리핸들 => 보통 자동로그인이나 아이디 저장할 댸 사용됨
 *                핸들러맵핑을 찾아달라고 요청하
 *                |                                                 @GetMapping(main.do)
 *                @getMapping을 찾음 => 이 안에 main.do가 들어가 잇음,  => public String main() 이거 찾을 거야 
 *                |
 *                viewResolver(return 시 뷰리졸버가 전송되는것)
 *                |-> 그 전에 afterCompletion
 *                jsp
 */ 

public class MainInterceptor extends HandlerInterceptorAdapter
{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
        System.out.println("🔔pretHandle() 호출");
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("🔔postHandle() 호출");
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("🔔afterCompletion() 호출");
		super.afterCompletion(request, response, handler, ex);
	}
  
	
}
