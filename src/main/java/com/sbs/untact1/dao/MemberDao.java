package com.sbs.untact1.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.untact1.dto.Member;
@Mapper
public interface MemberDao {
	public void joinMember(Map<String, Object> param);

	public Member getMember(@Param("id")int id);
	
	public Member getMemberByLoginId(@Param("loginId")String loginId);

	public void modifyMember(Map<String, Object> param);
}
