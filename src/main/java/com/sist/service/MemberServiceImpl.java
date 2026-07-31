package com.sist.service;

import org.springframework.stereotype.Service;

import com.sist.mapper.MemberMapper;
import com.sist.vo.*;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

	private final MemberMapper mapper;
	
	// 회원가입
	@Override
	public void memberInsert(MemberVO vo) {
		// TODO Auto-generated method stub
		mapper.memberInsert(vo);
	}

}
