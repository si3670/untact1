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
import com.sbs.untact1.util.Util;

@Controller
public class AdmMemberController {
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/adm/member/doJoin")
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
	
	@RequestMapping("/adm/member/login")
	public String login() {
		return "adm/member/login";
	}
	
	@RequestMapping("/adm/member/doLogin")
	@ResponseBody
	public String doLogin(String loginId, String loginPw, HttpSession session) {
		if (loginId == null) {
			return Util.msgAndBack("loginId을 입력해주세요");
		}
		Member existingMember = memberService.getMemberByLoginId(loginId);
		if(existingMember == null) {
			return Util.msgAndBack("존재하지 않는 아이디입니다.");
		}
		
		if (loginPw == null) {
			return Util.msgAndBack("loginPw을 입력해주세요");
		}
		if(existingMember.getLoginPw().equals(loginPw)==false) {
			return Util.msgAndBack("비밀번호를 확인해주세요.");
		}
		if ( memberService.isAdmin(existingMember) == false ) {
			return Util.msgAndBack("관리자만 접근할 수 있는 페이지 입니다.");
		}
		session.setAttribute("loginedMemberId", existingMember.getId());
		
		String msg = String.format("%s님 환영합니다.", existingMember.getNickname());


		return Util.msgAndReplace(msg, "../home/main");
	}
	
	
	@RequestMapping("/adm/member/doLogout")
	@ResponseBody
	public String doLogout(HttpSession session) {
		session.removeAttribute("loginedMemberId");
		return Util.msgAndReplace("로그아웃 되었습니다.", "../member/login");
	}
	
	@RequestMapping("/adm/member/doModify")
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
