package com.sist.mapper;
import org.apache.ibatis.annotations.Insert;

import com.sist.vo.*;
public interface MemberMapper {

	@Insert("INSERT INTO springMember VALUES("
			+"#{userid},#{username},#{userpwd},1,#{sex})")
	public void memberInsert(MemberVO vo);
}
