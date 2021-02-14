package com.sbs.untact1.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact1.dto.Article;
import com.sbs.untact1.dto.ResultData;
import com.sbs.untact1.service.ArticleService;
import com.sbs.untact1.util.Util;

//사용자 게시물 추가, 삭제, 목록, 상세보기
@Controller
public class UsrArticleController {
	@Autowired
	private ArticleService articleService;

	@RequestMapping("/usr/article/detail")
	@ResponseBody
	public ResultData showDetail(Integer id) {
		if(id==null) {
			return new ResultData("F-1", "id를 입력해주세요");
		}
		//getForFrintArticle = 출력을 위한 게시물
		Article article = articleService.getForPrintArticle(id);
		if(article==null) {
			return new ResultData("F-2", "존재하지 않는 게시물입니다.");
		}
		return new ResultData("S-1", "성공", "article", article);
	}

	@RequestMapping("/usr/article/list")
	@ResponseBody
	public List<Article> showList(String searchKeyword, String searchKeywordType) {
		if(searchKeywordType != null) {
			searchKeywordType = searchKeywordType.trim();
		}
		if(searchKeywordType == null || searchKeywordType.length() == 0) {
			searchKeywordType = "titleAndbody";
		}
		if(searchKeyword != null) {
			searchKeyword = searchKeyword.trim();
		}
		//searchKeyword가 null은 아닌데("공백") 길이가 0과 같다
		if(searchKeyword != null && searchKeyword.length() == 0) {
			searchKeyword = null;
		}
		
		return articleService.getArticles(searchKeyword,searchKeywordType);
	}

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData doAdd(@RequestParam Map<String, Object> param, HttpSession session) {
		int loginedMemberId = Util.getAsInt(session.getAttribute("loginedMemberId"), 0);
		
		if (param.get("title") == null) {
			return new ResultData("F-1", "title을 입력해주세요");
		}
		if (param.get("body") == null) {
			return new ResultData("F-1", "body을 입력해주세요");
		}
		param.put("memberId", loginedMemberId);
		
		return articleService.addArticle(param);

	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData doDelete(Integer id, HttpSession session) {
		int loginedMemberId = Util.getAsInt(session.getAttribute("loginedMemberId"), 0);

		Article article = articleService.getArticle(id); // service한테 id 게시물이 있는지 물어보기

		if (article == null) {
			return new ResultData("F-1", "해당 게시물은 존재하지 않습니다.");
		}
		
		ResultData actorCanDeleteRd = articleService.getActorCanDelete(article, loginedMemberId);
		if(actorCanDeleteRd.isFail()) {
			return actorCanDeleteRd;
		}

		return articleService.deleteArticle(id);
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData doModify(int id, String title, String body, HttpSession session) {
		int loginedMemberId = Util.getAsInt(session.getAttribute("loginedMemberId"), 0);

		
		Article article = articleService.getArticle(id); // service한테 id 게시물이 있는지 물어보기

		if (article == null) {
			return new ResultData("F-1", "해당 게시물은 존재하지 않습니다.");
		}
		//actorCan = 현재 로그인한 사람
		ResultData actorCanModifyRd = articleService.getActorCanModify(article, loginedMemberId);
		if(actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}
		return articleService.modifyArticle(id, title, body);
	}

}
