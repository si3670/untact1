package com.sbs.untact1.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.untact1.dao.ArticleDao;
import com.sbs.untact1.dto.Article;
import com.sbs.untact1.dto.ResultData;
import com.sbs.untact1.util.Util;

@Service
public class ArticleService {
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private MemberService memberService;

	public Article getArticle(int id) {
		return articleDao.getArticle(id);
	}

	public List<Article> getArticles(String searchKeyword, String searchKeywordType) {
		return articleDao.getArticles(searchKeyword, searchKeywordType);
	}

	public ResultData addArticle(Map<String, Object> param) {
		articleDao.addArticle(param);
		int id = Util.getAsInt(param.get("id"), 0);
		return new ResultData("S-1", "성공", "id", id);
	}

	public ResultData deleteArticle(int id) {
		articleDao.deleteArticle(id);
		
		return new ResultData("S-1", "성공", "id", id);
	}

	public ResultData modifyArticle(int id, String title, String body) {
		articleDao.modifyArticle(id, title, body);
		return new ResultData("S-1", "성공", "id", id);
	}

	public ResultData getActorCanModify(Article article, int actorId) {
		if(article.getMemberId() == actorId) {
			return new ResultData("S-1", "가능");
		}
		if(memberService.isAdmin(actorId)) {
			return new ResultData("S-1", "가능");
		}
		return new ResultData("F-1", "권한 없음");
	}

	public ResultData getActorCanDelete(Article article, int actorId) {
		return getActorCanModify(article, actorId);
	}

	public Article getForPrintArticle(int id) {
		return articleDao.getForPrintArticle(id);
	}

	public List<Article> getForPrintArticles(String searchKeyword, String searchKeywordType) {
		return articleDao.getForPrintArticles(searchKeyword, searchKeywordType);
	}

	public void  increaseArticleHit(Integer id) {
		articleDao.increaseArticleHit(id);
		
	}
	
}
