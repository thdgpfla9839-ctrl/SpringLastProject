package com.sist.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.sist.service.*;
import com.sist.vo.*;

import lombok.RequiredArgsConstructor;

@Aspect // 공통 모듈 => 이 함수는 메모리할당은 못하고 여기가 공통으로 수행되는 위치라는 것만 알려줌
@Component // 여기가 메모리할당을 하는 부분
@RequiredArgsConstructor // 서비스를 받아와야 해서 추가

/*
 *  1. 메소드 어느 위치에서 호출할지 =>JoinPoint
 *  2. 어떤 메소드인지 => PoinCut => *패키지.클래스.메소드(매개변수) => 맨앞은 리턴형인데 그 위치에 *을 주면 상관이 없어진다
 *  3. 언제 합쳐지는지(통합) => Weaving
 *  
 *  조인포인트 + 포인트컷 => "Advice"라고 함 
 */
public class FooterCommonsAspect {
	
	private final FoodService fService;
	
	// execution : '이 메소드가 호출되면' 이라는 의미 
	@After("execution(* com.sist.web.*Controller.*(..))") // 근데 브라우저로 전송해야하는데 이 떄는 HttpServletRequest(request)나 model을 이용해야하는데 HttpServletRequest는 디스패쳐서블릿이 올려주는데 
	                                                             // 그렇게 되면 @controller랑 @RestController는 서블릿이랑 연결이 아 안돼서 값을 받을 수 없어
	public void sendData()
	{
		//  그래서 현재 사용중인 리퀘스트를 얻어와야한다(디스패쳐서블릿이 가지고 있는 리퀘스트를 가져오는 방법)
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		List<FoodVO> fList = fService.foodHit7Data();
		request.setAttribute("fList", fList);
	}
	
	@Around("execution(* com.sist.web.*Controller.*(..))")
	public Object log(ProceedingJoinPoint jp)
	throws Throwable
	{
		// 로그 파일 => 어디서  조회수가 많은지
		Object obj = null;
		System.out.println("사용자의 요청: "+jp.getSignature().getName());
		obj=jp.proceed();
		System.out.println("사용자의 요청 완료: "+jp.getSignature().getName());
		return obj;
	}


}
