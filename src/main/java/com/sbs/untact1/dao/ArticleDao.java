package com.sbs.untact1.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.untact1.dto.Article;
import com.sbs.untact1.dto.ResultData;
//DB접근 
//마이바티스 -> 알아서 삭제, 수정, 추가 등 
@Mapper
public interface ArticleDao {
	public Article getArticle(@Param(value="id")int id);
	public List<Article> getArticles(@Param(value="searchKeyword")String searchKeyword, @Param(value="searchKeywordType")String searchKeywordType);
	public void addArticle(Map<String, Object> param);
	public void deleteArticle(@Param(value="id")int id);
	public void modifyArticle(@Param(value="id")int id, @Param(value="title")String title, @Param(value="body")String body);
	public Article getForPrintArticle(@Param(value="id")int id);
	public List<Article> getForPrintArticles(@Param(value="searchKeyword")String searchKeyword, @Param(value="searchKeywordType")String searchKeywordType);
	public void increaseArticleHit(@Param(value="id")int id);
	
	
}
