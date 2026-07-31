package com.sist.web;

import java.util.*;
import com.sist.vo.*;

import lombok.RequiredArgsConstructor;

import com.sist.service.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

// rest API라 연결을 할 데 레스트컨트롤러를 사용한다
@RestController // 얘는 화면을 이동하는게 아니라 데이터를 전송헤주는 역할을 한다, 여기는 문자열이랑 json을 전송하는 곳이라 sendRedirect가 없다
@RequiredArgsConstructor
public class BoardRestController {

	private final BoardService bService; // 싱글턴
	

	// 게시글 삭제완료
	@PostMapping(value="board/delete_ok.do",produces = "text/html;charset=UTF-8") // 자바스크립트를 보낼 때는 한긓이 꺠질 수 있어서 추가해준다
	public String board_delete_ok(int no, String pwd)
	{
		
		   String result ="";
		   boolean bCheck = bService.boardDelete(no, pwd);
		   if(bCheck==true)
		   {
			   result="<script>"
					   +"location.href=\"../board/list.do\""
					   +"</script>";
		   }
		   else
		   {
			   result ="<script>"
					   +"alert(\"비밀번호 틀림\");"
					   + "history.back();"
			           +"</script>";
		   }
		   return result;	

	}
}
