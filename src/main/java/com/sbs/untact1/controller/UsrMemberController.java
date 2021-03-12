package com.sbs.untact1.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact1.dto.Member;
import com.sbs.untact1.dto.ResultData;
import com.sbs.untact1.service.MemberService;

@Controller
public class UsrMemberController {
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData doJoin(@RequestParam Map<String, Object> param) {
		if (param.get("loginId") == null) {
			return new ResultData("F-1", "loginId을 입력해주세요");
		}
		//아이디 중복체크
		Member existingMember = memberService.getMemberByLoginId((String) param.get("loginId"));
		if(existingMember != null) {
			return new ResultData("F-2", "이미 사용 중인 아이디입니다.");
		}
		if (param.get("loginPw") == null) {
			return new ResultData("F-1", "loginPw을 입력해주세요");
		}
		if (param.get("name") == null) {
			return new ResultData("F-1", "name을 입력해주세요");
		}
		if (param.get("nickname") == null) {
			return new ResultData("F-1", "nickname을 입력해주세요");
		}
		if (param.get("cellphoneNo") == null) {
			return new ResultData("F-1", "cellphoneNo을 입력해주세요");
		}
		if (param.get("email") == null) {
			return new ResultData("F-1", "email을 입력해주세요");
		}
		
		return memberService.joinMember(param);

	}
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public ResultData doLogin(String loginId, String loginPw, HttpSession session) {
		
		if (loginId == null) {
			return new ResultData("F-1", "loginId을 입력해주세요");
		}
		Member existingMember = memberService.getMemberByLoginId(loginId);
		if(existingMember == null) {
			return new ResultData("F-2", "존재하지 않는 아이디입니다.");
		}
		
		if (loginPw == null) {
			return new ResultData("F-1", "loginPw을 입력해주세요");
		}
		if(existingMember.getLoginPw().equals(loginPw)==false) {
			return new ResultData("F-3", "비밀번호를 확인해주세요.");
		}
		session.setAttribute("loginedMemberId", existingMember.getId());
		return new ResultData("P-1", String.format("%s 로그인 성공", existingMember.getLoginId()));
	}
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public ResultData doLogout(HttpSession session) {
		session.removeAttribute("loginedMemberId");
		return new ResultData("P-1", "로그아웃 성공");
	}
	
	@RequestMapping("/usr/member/doModify")
	@ResponseBody
	public ResultData doModify(@RequestParam Map<String, Object> param, HttpSession session) {
		if(param.isEmpty()) {
			return new ResultData("F-2", "수정할 정보를 입력해주세요.");
		}
		
		int loginedMemberId = (int)session.getAttribute("loginedMemberId");
		param.put("id", loginedMemberId);
		
		return memberService.modifyMember(param);
	}
}
