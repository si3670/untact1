package com.sbs.untact1.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sbs.untact1.dto.Member;
import com.sbs.untact1.service.MemberService;
import com.sbs.untact1.util.Util;

@Component("beforeActionInterceptor") // 컴포넌트 이름 설정
public class BeforeActionInterceptor implements HandlerInterceptor {
	@Autowired
	private MemberService memberService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		int loginedMemberId = 0;
		Member loginedMember = null;
		String authKey = request.getParameter("authKey");
		if (authKey != null && authKey.length() > 0) {
			loginedMember = memberService.getMemberByAuthKey(authKey);
			if (loginedMember == null) {
				request.setAttribute("authKeyStatus", "invalid");
			} else {
				request.setAttribute("authKeyStatus", "valid");
				loginedMemberId = loginedMember.getId();
			}
		} else {
			HttpSession session = request.getSession();
			request.setAttribute("authKeyStatus", "none");
			if (session.getAttribute("loginedMemberId") != null) {
				loginedMemberId = (int) session.getAttribute("loginedMemberId");
				loginedMember = memberService.getMember(loginedMemberId);
			}
		}
		
		// 로그인 여부에 관련된 정보를 request에 담는다.
		boolean isLogined = false;
		boolean isAdmin = false;
		if (loginedMember != null) {
			isLogined = true;
			isAdmin = memberService.isAdmin(loginedMemberId);
		}
		request.setAttribute("loginedMemberId", loginedMemberId);
		request.setAttribute("isLogined", isLogined);
		request.setAttribute("isAdmin", isAdmin);
		request.setAttribute("loginedMember", loginedMember);
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}