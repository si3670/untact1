package com.sbs.untact1.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.untact1.dao.MemberDao;
import com.sbs.untact1.dto.Member;
import com.sbs.untact1.dto.ResultData;
import com.sbs.untact1.util.Util;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;
	
	public ResultData joinMember(Map<String, Object> param) {
		memberDao.joinMember(param);
		int id = Util.getAsInt(param.get("id"), 0);
		return new ResultData("S-1", String.format("%s님 환영합니다.", param.get("name")), "id", id);
	}
	
	public Member getMember(int id) {
		return memberDao.getMember(id);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}

	public ResultData modifyMember(Map<String, Object> param) {
		memberDao.modifyMember(param);
		return new ResultData("S-1","회원 정보가 수정되었습니다.");
	}

	public boolean isAdmin(int actorId) {
		return actorId == 1;
	}


}
